package com.steve.steveaiagent.rag;

import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;

/**
 * 创建上下文查询增强器工厂
 */
public class LoveAppContextualQueryArgumentFactory {

    public static ContextualQueryAugmenter createInstance() {
        PromptTemplate empetyContextPromptTemplate = new PromptTemplate("""
                你应该输出下面的内容：
                抱歉，我只能回答恋爱相关的问题，别的没办法帮到您哦，
                有问题可以联系客服
                """);
        return ContextualQueryAugmenter.builder()
                .allowEmptyContext(false)
                .emptyContextPromptTemplate(empetyContextPromptTemplate)
                .build();
    }
}
