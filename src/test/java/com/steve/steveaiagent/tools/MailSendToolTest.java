package com.steve.steveaiagent.tools;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailSendToolTest {

    @Resource
    private MailSendTool mailSendTool;

    @Test
    void sendTextEmail() {
        String to = "2398424607@qq.com";
        String subject = "测试";
        String content = "测试AI邮件发送工具";
        String result = mailSendTool.sendTextEmail(to, subject, content);
        System.out.println(result);
        assertNotNull(result);
    }

    @Test
    void sendHtmlEmail() {
        String to = "2398424607@qq.com";
        String subject = "测试";
        String content = "<h1>测试AI邮件发送工具</h1>";
        String result = mailSendTool.sendHtmlEmail(to, subject, content);
        System.out.println(result);
        assertNotNull(result);
    }

    @Test
    void sendTextEmailWithAttachment() {
        String to = "2398424607@qq.com";
        String subject = "测试";
        String content = "测试AI邮件发送工具";
        String attachmentPath = "https://stevecp1-1364043722.cos.ap-beijing.myqcloud.com//public/1932727584688029697/2025-06-25_k9fJgx1NhOv7HLUi.webp";
        String result = mailSendTool.sendTextEmailWithAttachment(to, subject, content, attachmentPath);
        System.out.println(result);
        assertNotNull(result);
    }

    @Test
    void sendHtmlEmailWithAttachment() {
        String to = "2398424607@qq.com";
        String subject = "测试";
        String content = "<h1>测试AI邮件发送工具</h1>";
        String attachmentPath = "https://stevecp1-1364043722.cos.ap-beijing.myqcloud.com//public/1932727584688029697/2025-06-25_k9fJgx1NhOv7HLUi.webp";
        String result = mailSendTool.sendHtmlEmailWithAttachment(to, subject, content, attachmentPath);
        System.out.println(result);
        assertNotNull(result);
    }

    @Test
    void sendTextEmailWithMultipleAttachments() {
        String to = "2398424607@qq.com";
        String subject = "测试";
        String content = "测试AI邮件发送工具";
        List<String> attachmentPaths = new ArrayList<>();
        attachmentPaths.add("https://stevecp1-1364043722.cos.ap-beijing.myqcloud.com//public/1932727584688029697/2025-06-25_k9fJgx1NhOv7HLUi.webp");
        attachmentPaths.add("https://spring.io/");
        String result = mailSendTool.sendTextEmailWithMultipleAttachments(to, subject, content, attachmentPaths);
        System.out.println(result);
        assertNotNull(result);
    }

    @Test
    void sendHtmlEmailWithMultipleAttachments() {
    }
}