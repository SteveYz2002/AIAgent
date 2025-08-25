package com.steve.steveaiagent.tools;

import jakarta.annotation.Resource;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Component;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.UrlResource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MailSendTool {

    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Data
    @Builder
    private static class EmailResult {
        private boolean success;
        private String message;
        private String recipient;
    }

    /**
     * 发送文本邮件
     * @param to
     * @param subject
     * @param content
     * @return
     */
    @Tool(description = "Send a simple text email")
    public String sendTextEmail(@ToolParam(description = "Email address of the recipient") String to,
                                      @ToolParam(description = "subject of the Email") String subject,
                                      @ToolParam(description = "content of the Email") String content) {
        return sendEmail(to, subject, content, false).getMessage();
    }

    /**
     * 发送HTML邮件
     * @param to
     * @param subject
     * @param content
     * @return
     */
    @Tool(description = "Send a HTML email")
    public String sendHtmlEmail(@ToolParam(description = "Email address of the recipient") String to,
                                      @ToolParam(description = "subject of the Email") String subject,
                                      @ToolParam(description = "content of the Email") String content) {
        return sendEmail(to, subject, content, true).getMessage();
    }
    
    /**
     * 发送带网络附件的纯文本邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param attachmentUrl 附件URL
     * @return 发送结果
     */
    @Tool(description = "Send a text email with a network attachment")
    public String sendTextEmailWithAttachment(
            @ToolParam(description = "Email address of the recipient") String to,
            @ToolParam(description = "subject of the Email") String subject,
            @ToolParam(description = "content of the Email") String content,
            @ToolParam(description = "URL of the attachment file") String attachmentUrl) {
        return sendEmailWithAttachmentImpl(to, subject, content, attachmentUrl, false).getMessage();
    }
    
    /**
     * 发送带网络附件的HTML邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param attachmentUrl 附件URL
     * @return 发送结果
     */
    @Tool(description = "Send a HTML email with a network attachment")
    public String sendHtmlEmailWithAttachment(
            @ToolParam(description = "Email address of the recipient") String to,
            @ToolParam(description = "subject of the Email") String subject,
            @ToolParam(description = "content of the Email") String content,
            @ToolParam(description = "URL of the attachment file") String attachmentUrl) {
        return sendEmailWithAttachmentImpl(to, subject, content, attachmentUrl, true).getMessage();
    }
    
    /**
     * 发送带多个网络附件的纯文本邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param attachmentUrls 多个附件URL列表
     * @return 发送结果
     */
    @Tool(description = "Send a text email with multiple network attachments")
    public String sendTextEmailWithMultipleAttachments(
            @ToolParam(description = "Email address of the recipient") String to,
            @ToolParam(description = "subject of the Email") String subject,
            @ToolParam(description = "content of the Email") String content,
            @ToolParam(description = "URLs of the attachment files, separated by comma") List<String> attachmentUrls) {
        return sendEmailWithMultipleAttachmentsImpl(to, subject, content, attachmentUrls, false).getMessage();
    }
    
    /**
     * 发送带多个网络附件的HTML邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param attachmentUrls 多个附件URL列表
     * @return 发送结果
     */
    @Tool(description = "Send a HTML email with multiple network attachments")
    public String sendHtmlEmailWithMultipleAttachments(
            @ToolParam(description = "Email address of the recipient") String to,
            @ToolParam(description = "subject of the Email") String subject,
            @ToolParam(description = "content of the Email") String content,
            @ToolParam(description = "URLs of the attachment files, separated by comma") List<String> attachmentUrls) {
        return sendEmailWithMultipleAttachmentsImpl(to, subject, content, attachmentUrls, true).getMessage();
    }

    /**
     * 发送邮件的核心方法
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param isHtml 是否为HTML格式
     * @return 发送结果
     */
    private EmailResult sendEmail(String to, String subject, String content, boolean isHtml) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);


            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, isHtml);

            // 发送邮件
            mailSender.send(message);

            return EmailResult.builder()
                    .success(true)
                    .message(String.format("邮件发送成功：%s", to))
                    .recipient(to)
                    .build();

        } catch (MessagingException e) {
            log.error("邮件发送失败: {}", e.getMessage(), e);
            return EmailResult.builder()
                    .success(false)
                    .message(String.format("邮件发送失败：%s，原因：%s", to, e.getMessage()))
                    .recipient(to)
                    .build();
        }
    }
    
    /**
     * 发送带网络附件的邮件的核心方法
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param attachmentUrl 附件URL
     * @param isHtml 是否为HTML格式
     * @return 发送结果
     */
    private EmailResult sendEmailWithAttachmentImpl(String to, String subject, String content, String attachmentUrl, boolean isHtml) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            // 第二个参数true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, isHtml);

            // 添加网络附件
            URI uri = new URI(attachmentUrl);
            URL url = uri.toURL();
            UrlResource urlResource = new UrlResource(url);
            
            // 从URL中提取文件名
            String fileName = extractFileNameFromUrl(attachmentUrl);
            helper.addAttachment(fileName, urlResource);

            // 发送邮件
            mailSender.send(message);

            return EmailResult.builder()
                    .success(true)
                    .message(String.format("带网络附件的邮件发送成功：%s，附件：%s", to, fileName))
                    .recipient(to)
                    .build();

        } catch (MessagingException e) {
            log.error("带网络附件的邮件发送失败: {}", e.getMessage(), e);
            return EmailResult.builder()
                    .success(false)
                    .message(String.format("带网络附件的邮件发送失败：%s，原因：%s", to, e.getMessage()))
                    .recipient(to)
                    .build();
        } catch (MalformedURLException e) {
            log.error("无效的URL: {}", e.getMessage(), e);
            return EmailResult.builder()
                    .success(false)
                    .message(String.format("无效的URL：%s，原因：%s", to, e.getMessage()))
                    .recipient(to)
                    .build();
        } catch (Exception e) {
            log.error("处理网络附件时出错: {}", e.getMessage(), e);
            return EmailResult.builder()
                    .success(false)
                    .message(String.format("处理网络附件时出错：%s，原因：%s", to, e.getMessage()))
                    .recipient(to)
                    .build();
        }
    }
    
    /**
     * 从URL中提取文件名
     * @param url URL字符串
     * @return 文件名
     */
    private String extractFileNameFromUrl(String url) {
        String fileName = url.substring(url.lastIndexOf('/') + 1);
        // 处理查询参数
        if (fileName.contains("?")) {
            fileName = fileName.substring(0, fileName.indexOf('?'));
        }
        // 如果没有文件名，使用默认名称
        if (fileName.isEmpty()) {
            fileName = "attachment_" + System.currentTimeMillis();
        }
        return fileName;
    }
    
    /**
     * 发送带多个网络附件的邮件的核心方法
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param attachmentUrls 多个附件URL列表
     * @param isHtml 是否为HTML格式
     * @return 发送结果
     */
    private EmailResult sendEmailWithMultipleAttachmentsImpl(String to, String subject, String content, List<String> attachmentUrls, boolean isHtml) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            // 第二个参数true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, isHtml);

            // 添加多个网络附件
            StringBuilder attachmentNames = new StringBuilder();
            for (String attachmentUrl : attachmentUrls) {
                attachmentUrl = attachmentUrl.trim(); // 去除可能的空格
                if (attachmentUrl.isEmpty()) {
                    continue; // 跳过空URL
                }
                
                try {
                    URL url = new URL(attachmentUrl);
                    UrlResource urlResource = new UrlResource(url);
                    
                    // 从URL中提取文件名
                    String fileName = extractFileNameFromUrl(attachmentUrl);
                    helper.addAttachment(fileName, urlResource);
                    
                    if (attachmentNames.length() > 0) {
                        attachmentNames.append(", ");
                    }
                    attachmentNames.append(fileName);
                } catch (MalformedURLException e) {
                    log.error("无效的URL: {}", attachmentUrl, e);
                    // 继续处理其他附件
                }
            }

            // 发送邮件
            mailSender.send(message);

            return EmailResult.builder()
                    .success(true)
                    .message(String.format("带多个网络附件的邮件发送成功：%s，附件：%s", to, attachmentNames))
                    .recipient(to)
                    .build();

        } catch (MessagingException e) {
            log.error("带多个网络附件的邮件发送失败: {}", e.getMessage(), e);
            return EmailResult.builder()
                    .success(false)
                    .message(String.format("带多个网络附件的邮件发送失败：%s，原因：%s", to, e.getMessage()))
                    .recipient(to)
                    .build();
        } catch (Exception e) {
            log.error("处理多个网络附件时出错: {}", e.getMessage(), e);
            return EmailResult.builder()
                    .success(false)
                    .message(String.format("处理多个网络附件时出错：%s，原因：%s", to, e.getMessage()))
                    .recipient(to)
                    .build();
        }
    }


}
