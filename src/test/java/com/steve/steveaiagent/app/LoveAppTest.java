package com.steve.steveaiagent.app;

import cn.hutool.core.lang.UUID;
import com.steve.steveaiagent.exception.BusinessException;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoveAppTest {

    @Resource
    private LoveApp loveApp;

    @Test
    void testChat() {
        String chatId = UUID.randomUUID().toString();
        // 正常输入测试
        String normalMessage = "你好，我是steve";
        String answer = loveApp.doChat(normalMessage, chatId);
        Assertions.assertNotNull(answer);

        // 包含违禁词输入测试，验证是否抛出异常
        String prohibitedMessage = "成人偷拍";
        Assertions.assertThrows(BusinessException.class, () -> {
            loveApp.doChat(prohibitedMessage, chatId);

        }, "检测到违禁词");

        // 对话记忆相关测试
        String recallMessage = "我之前说的内容，你能再帮我想想吗？";
        answer = loveApp.doChat(recallMessage, chatId);
        Assertions.assertNotNull(answer);
    }


    @Test
    void doChatWithReport() {
        String chatId = UUID.randomUUID().toString();
        String message = "你好，我是Steve，我想让另一半更爱我，我该怎么做";
        LoveApp.LoveReport loveReport = loveApp.doChatWithReport(message, chatId);
        Assertions.assertNotNull(loveReport);
    }


////    @Test
////    void loveAdvice() {
////        String chatId = UUID.randomUUID().toString();
////        String anwser = loveApp.doChat("约会失望","悲伤", chatId);
////        Assertions.assertNotNull(anwser);
////
////    }
}

