package com.steve.steveaiagent.advisor;

import com.steve.steveaiagent.exception.BusinessException;
import com.steve.steveaiagent.exception.ErrorCode;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Slf4j
public class ProhibitedWordAdvisor implements CallAdvisor, StreamAdvisor {

    private static final String DEFAULT_BANNED_WORDS_FILE = "src/main/resources/bannedword/bannedword.txt";

    private final List<String> bannedWords;

    /**
     * 创建默认违禁词Advisor，从默认文件读取违禁词列表
     */
    public ProhibitedWordAdvisor() {
        this.bannedWords = loadBannedWords(DEFAULT_BANNED_WORDS_FILE);
        log.info("初始化违禁词Advisor，违禁词数量: {}", bannedWords.size());
    }

    /**
     * 创建违禁词Advisor，从指定文件读取违禁词列表
     * @param bannedWordsFile 违禁词文件路径
     */
    public ProhibitedWordAdvisor(String bannedWordsFile) {
        this.bannedWords = loadBannedWords(bannedWordsFile);
        log.info("初始化违禁词Advisor，违禁词数量: {}", bannedWords.size());
    }

    /**
     * 从指定文件中读取违禁词列表，并解码Base64格式的数据
     * @param filePath 违禁词文件路径
     * @return 违禁词列表
     */
    private List<String> loadBannedWords(String filePath) {
        List<String> bannedWords = new ArrayList<>();
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            log.warn("警告: 违禁词文件不存在 - {}", filePath);
            return bannedWords;
        }

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String trimmedLine = line.trim();

                // 忽略空行和以#开头的注释行
                if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
                    continue;
                }

                try {
                    // 解码Base64数据
                    byte[] decodedBytes = Base64.getDecoder().decode(trimmedLine);
                    String decodedWord = new String(decodedBytes, StandardCharsets.UTF_8);
                    // 去除换行符和回车符
                    String cleanedWord = decodedWord.replace("\n", "").replace("\r", "").trim();

                    if (!cleanedWord.isEmpty()) {
                        bannedWords.add(cleanedWord);
                        log.debug("解码Base64数据 (行{}): {} -> {}", lineNumber, trimmedLine, cleanedWord);
                    }
                } catch (IllegalArgumentException e) {
                    // 如果解码失败，记录错误并跳过该行
                    log.warn("Base64解码失败，可能不是有效的Base64格式 (行{}): {}", lineNumber, trimmedLine, e);
                }
            }
        } catch (IOException e) {
            log.error("读取违禁词文件时发生错误: {}", e.getMessage(), e);
        }

        return bannedWords;
    }


    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return -100;
    }

    private ChatClientRequest checkChatClientRequest(ChatClientRequest chatClientRequest) {
        String text = chatClientRequest.prompt().getUserMessage().getText();
        for (String bannedWord : this.bannedWords) {
            if (text.contains(bannedWord)) {
                log.error("检测到违禁词 - " + bannedWord);
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "检测到违禁词 - " + bannedWord);
            }
        }
        return chatClientRequest;
    }


    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain chain) {
        return chain.nextCall(checkChatClientRequest(chatClientRequest));
    }

    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest, StreamAdvisorChain chain) {
        return chain.nextStream(checkChatClientRequest(chatClientRequest));
    }
}
