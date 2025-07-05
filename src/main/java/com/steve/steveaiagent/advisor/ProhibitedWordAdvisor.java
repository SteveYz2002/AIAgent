package com.steve.steveaiagent.advisor;

import com.steve.steveaiagent.exception.BusinessException;
import com.steve.steveaiagent.exception.ErrorCode;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.api.*;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Slf4j
public class ProhibitedWordAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {

    private static final String DEFAULT_BANNED_WORDS_FILE = "src/main/resources/bannedword/bannedword.txt";

    private final List<String> bannedWords;

    /**
     * 创建默认违禁词Advisor，从默认文件读取违禁词列表
     */
    public ProhibitedWordAdvisor() {
        this.bannedWords = getBannedWords(DEFAULT_BANNED_WORDS_FILE);
        log.info("初始化违禁词Advisor，违禁词数量: {}", bannedWords.size());
    }

    public ProhibitedWordAdvisor(String bannedWordsFile) {
        this.bannedWords = getBannedWords(bannedWordsFile);
    }

    /**
     * 从指定文件中读取违禁词列表，并解码Base64格式的数据
     * @param filePath 违禁词文件路径
     * @return 违禁词列表
     */
    private List<String> getBannedWords(String filePath) {
        List<String> bannedWords = new ArrayList<>();
        try {
            File file = new File(filePath);
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    // 忽略空行和以#开头的注释行
                    if (!line.trim().isEmpty() && !line.trim().startsWith("#")) {
                        try {
                            // 解码Base64数据
                            String trimmedLine = line.trim();
                            byte[] decodedBytes = Base64.getDecoder().decode(trimmedLine);
                            String decodedWord = new String(decodedBytes, StandardCharsets.UTF_8);
                            // 去除换行符
                            String cleanedWord = decodedWord.replace("\n", "").replace("\r", "");
                            log.debug("解码Base64数据: {} -> {}", trimmedLine, cleanedWord);
                            bannedWords.add(cleanedWord);
                        } catch (IllegalArgumentException e) {
                            // 如果解码失败，记录错误并跳过该行
                            log.warn("Base64解码失败，可能不是有效的Base64格式: {}", line.trim(), e);
                        }
                    }
                }
                reader.close();
            } else {
                log.warn("警告: 违禁词文件不存在 - {}", filePath);
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

    private AdvisedRequest checkAdvisedRequest(AdvisedRequest advisedRequest) {
        String text = advisedRequest.userText();
        for (String bannedWord : this.bannedWords) {
            if (text.contains(bannedWord)) {
                log.error("检测到违禁词 - " + bannedWord);
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "检测到违禁词 - " + bannedWord);
            }
        }
        return advisedRequest;
    }


    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        return chain.nextAroundCall(checkAdvisedRequest(advisedRequest));
    }

    @Override
    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
        return chain.nextAroundStream(checkAdvisedRequest(advisedRequest));
    }
}
