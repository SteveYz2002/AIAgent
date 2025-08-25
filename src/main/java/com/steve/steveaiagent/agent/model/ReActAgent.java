package com.steve.steveaiagent.agent.model;

import com.steve.steveaiagent.agent.BaseAgent;
import com.steve.steveaiagent.exception.BusinessException;
import com.steve.steveaiagent.exception.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * ReAct(Reasoning and Acting)模式的代理抽象类 实现了思考—行动的循环模式
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public abstract class ReActAgent extends BaseAgent {

    /**
     * 处理当前状态并决定下一步行动
     *
     * @return 是否继续执行
     */
    public abstract boolean think();

    /**
     * 执行当前行动
     *
     * @return 行动执行结果
     */
    public abstract String act();

    @Override
    public String step() {
        try {
            boolean shouldAct = think();
            if (!shouldAct) {
                return "思考完成，无需行动";
            }
            // 再行动
            return act();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "ReActAgent error: " + e.getMessage());
        }
    }
}
