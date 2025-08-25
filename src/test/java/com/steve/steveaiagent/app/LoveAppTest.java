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

    @Test
    void doChatWithRag() {
        String chatId = UUID.randomUUID().toString();
        String message = "我已经结婚了，但是婚后关系不太好，怎么办？";
        String answer = loveApp.doChatWithRag(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithTools() {
        // 测试联网搜索问题的答案
        testMessage("周末想带女朋友去上海约会，推荐几个适合情侣的小众打卡地？");

        // 测试网页抓取：恋爱案例分析
        testMessage("最近和对象吵架了，看看编程导航网站（codefather.cn）的其他情侣是怎么解决矛盾的？");

        // 测试资源下载：图片下载
        testMessage("直接下载一张适合做手机壁纸的星空情侣图片为文件");

        // 测试终端操作：执行代码
        testMessage("执行 Python3 脚本来生成数据分析报告");

        // 测试文件操作：保存用户档案
        testMessage("保存我的恋爱档案为文件");

        // 测试 PDF 生成
        testMessage("生成一份‘七夕约会计划’PDF，包含餐厅预订、活动流程和礼物清单");
    }

    private void testMessage(String message) {
        String chatId = UUID.randomUUID().toString();
        String answer = loveApp.doChatWithTools(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithMcp() {
        String chatId = UUID.randomUUID().toString();
        // 测试高德地图mcp
//        String message = "我的另一半居住在北京朝阳区，请帮我找到一个5公里内适合约会的地方";
//        String answer = loveApp.doChatWithMcp(message, chatId);
//        Assertions.assertNotNull(answer);
        // 测试图片搜索mcp
        String message = "帮我找一张适合情侣的图片";
        String answer = loveApp.doChatWithMcp(message, chatId);
        Assertions.assertNotNull(answer);
    }


////    @Test
////    void loveAdvice() {
////        String chatId = UUID.randomUUID().toString();
////        String anwser = loveApp.doChat("约会失望","悲伤", chatId);
////        Assertions.assertNotNull(anwser);
////
////    }
}

