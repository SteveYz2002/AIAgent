package com.steve.steveaiagent.app;

import com.steve.steveaiagent.advisor.ProhibitedWordAdvisor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@Component
@Slf4j
public class LoveAppWithOutPrompt {

    private final ChatClient chatClient;

    @Value("file:E:/code/steve-ai-agent/src/main/resources/prompt/love_advisor.st")
    private Resource systemResource;

    public LoveAppWithOutPrompt(ChatModel dashscopeChatModel) {
//        // 初始化基于文件的对话记忆
//        String fileDir = System.getProperty("user.dir") + "/tmp/chat-memory";
//        ChatMemory chatMemory = new FileBasedChatMemory(fileDir);
        // 初始化基于内存的对话记忆
        ChatMemory chatMemory = new InMemoryChatMemory();
        chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory),
                        // 自定义日志 Advisor，可按需开启
//                        new MyLoggerAdvisor()
//                        // 自定义推理增强 Advisor，可按需开启
//                       ,new ReReadingAdvisor()
                        new ProhibitedWordAdvisor()
                )
                .build();
    }

    public String loveAdvice(String event, String emotion, String chatId) {
        PromptTemplate template = new PromptTemplate(systemResource);
        Map<String, Object> variables = Map.of(
                "event", event,
                "emotion", emotion,
                "questions", List.of(
                        "这件事发生时，TA的第一反应是什么？",
                        "你内心最希望TA改变的是什么？",
                        "如果用一个词形容你们现在的状态，会是什么？"
                ),
                "action", "先深呼吸3次，然后用'我句式'表达感受（例如：'我感到...当你...'）"
        );
        String result = chatClient.prompt()
                .system(template.render(variables))
                .user( event + "，我现在心情很"+ emotion + "，你能帮帮我吗")// 注入模板
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .call()
                .chatResponse()
                .getResult()
                .getOutput()
                .getText();
        log.info("result: {}", result);
        // 带记忆的对话调用
        return  result;
    }

    record LoveReport(String title, List<String> suggestions) {

    }

    // 原有方法改造（支持模板+记忆）
    public String doChat(String message,String emotion, String chatId) {
        return loveAdvice(message, emotion, chatId); // 自动处理无情绪参数情况
    }
    // 增强版报告生成（适配模板结构）
    public LoveReport doChatWithReport(String message, String emotion, String chatId) {
        String advice = loveAdvice(message, emotion, chatId);
        return new LoveReport(
                "恋爱分析报告",
                List.of(advice.split("\n")) // 将建议按行拆分
        );
    }
}
