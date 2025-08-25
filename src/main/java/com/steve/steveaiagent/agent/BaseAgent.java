package com.steve.steveaiagent.agent;

import cn.hutool.core.util.StrUtil;
import com.steve.steveaiagent.agent.model.AgentState;
import com.steve.steveaiagent.exception.BusinessException;
import com.steve.steveaiagent.exception.ErrorCode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 抽象基础代理类，用于管理代理状态和执行流程
 * 提供状态转换，内存管理和基于步骤的执行循环的基础功能
 * 子类必须实现step方法
 */
@Data
@Slf4j
public abstract class BaseAgent {

    private String name;

    private String systemPrompt;

    private String nextStepPrompt;

    // 代理状态
    private AgentState state = AgentState.IDLE;

    // 执行步骤控制
    private int currentStep = 0;
    private int maxSteps = 10;

    // LLM大模型
    private ChatClient chatClient;

    // Memory 记忆（自主维护上下文）
    private List<Message> messageList = new ArrayList<>();

    /**
     * 运行代理
     *
     * @param userPrompt 用户提示词
     * @return 执行结果
     */
    public String run(String userPrompt) {
        if (!this.state.equals(AgentState.IDLE)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "Cannot run agent in state: " + this.state);
        }
        if (StrUtil.isBlank(userPrompt)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "User prompt cannot be blank");
        }
        this.state = AgentState.RUNNING;
        messageList.add(new UserMessage(userPrompt));
        // 保存结果列表
        List<String> results = new ArrayList<>();
        try{
            // 执行循环
            for (int i = 0; i < maxSteps && state != AgentState.FINISHED; i++) {
                currentStep = i+1;
                log.info("Executing step: {} / {}", currentStep, maxSteps);
                // 单步执行
                String stepResult = step();
                String result = "Step " + currentStep + ": " + stepResult;
                results.add(result);
            }
            // 检查是否超出步数限制
            if (currentStep >= maxSteps) {
                state = AgentState.FINISHED;
                results.add("Terminated: Max steps reached (" + maxSteps + ")");
            }
            return StrUtil.join("\n", results);
        }catch (Exception e){
            state= AgentState.ERROR;
            log.error("Agent execution error", e);
            return "Agent execution error: " + e.getMessage();
        }finally {
            this.cleanup();
        }
    }

    /**
     * 运行代理（流式输出）
     *
     * @param userPrompt 用户提示词
     * @return 执行结果
     */
    public SseEmitter runStream(String userPrompt) {
        // 创建一个 SseEmitter 对象，设置超时时间为 300 秒
        SseEmitter sseEmitter = new SseEmitter(300000L);
        // 使用线程异步处理，避免阻塞主线程
        CompletableFuture.runAsync(() -> {
            try {
                if (!this.state.equals(AgentState.IDLE)) {
                    sseEmitter.send("错误，无法在状态: " + this.state + " 运行代理");
                    sseEmitter.complete();
                    return;
                }
                if (StrUtil.isBlank(userPrompt)) {
                    sseEmitter.send("错误，用户提示词不能为空");
                    sseEmitter.complete();
                    return;
                }
            }catch (Exception e){
                sseEmitter.completeWithError(e);
            }
            this.state = AgentState.RUNNING;
            messageList.add(new UserMessage(userPrompt));
            // 保存结果列表
            List<String> results = new ArrayList<>();
            try{
                // 执行循环
                for (int i = 0; i < maxSteps && state != AgentState.FINISHED; i++) {
                    currentStep = i+1;
                    log.info("Executing step: {} / {}", currentStep, maxSteps);
                    // 单步执行
                    String stepResult = step();
                    String result = "Step " + currentStep + ": " + stepResult;
                    results.add(result);
                    // 输出当前每一步的结果到 SSE
                    sseEmitter.send(result);
                }
                // 检查是否超出步数限制
                if (currentStep >= maxSteps) {
                    state = AgentState.FINISHED;
                    results.add("Terminated: Max steps reached (" + maxSteps + ")");
                    sseEmitter.send("执行完成，已达到最大步数限制: " + maxSteps);
                }
                // 正常完成
                sseEmitter.complete();
            }catch (Exception e){
                state= AgentState.ERROR;
                log.error("Agent execution error", e);
                try {
                    sseEmitter.send("执行错误r: " + e.getMessage());
                    sseEmitter.complete();
                } catch (IOException ex) {
                    sseEmitter.completeWithError(ex);
                }
            }finally {
                this.cleanup();
            }
        });

        // 设置超时回调
        sseEmitter.onTimeout(() -> {
            this.state = AgentState.ERROR;
            this.cleanup();
            log.warn("SSE connection timeout");
        });
        // 设置完成回调
        sseEmitter.onCompletion(() -> {
            if (this.state.equals(AgentState.RUNNING)) {
                this.state = AgentState.FINISHED;
            }
            this.cleanup();
            log.info("SSE connection completed");
        });

        return sseEmitter;
    }

    /**
     * 定义单个步骤
     *
     * @return
     */
    public abstract String step();

    /**
     * 清理资源
     */
    protected void cleanup() {
        // 子类可以重写来清理资源
    }
}
