package com.steve.steveaiagent.app;

import cn.hutool.core.lang.UUID;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoveAppWithOutPromptTest {

    @Resource
    private LoveAppWithOutPrompt loveAppWithOutPrompt;

    @Test
    void doChat() {
        String chatId = UUID.randomUUID().toString();
        String event = "我对约会感到失望";
        String emotion = "悲伤";
        String answer = loveAppWithOutPrompt.doChat(event, emotion, chatId);
        Assertions.assertNotNull(answer);
    }

}