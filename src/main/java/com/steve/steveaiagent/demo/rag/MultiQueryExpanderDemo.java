package com.steve.steveaiagent.demo.rag;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 查询扩展器 Demo
 */
@Component
public class MultiQueryExpanderDemo {

    @Resource
    private ChatClient.Builder chatClientBuilder;

    public List<Query> expand(String query) {
        MultiQueryExpander multiQueryExpander = MultiQueryExpander.builder()
                .chatClientBuilder(chatClientBuilder)
                .numberOfQueries(3)
                .build();
        List<Query> queries = multiQueryExpander.expand(new Query("你是谁"));
        return queries;
    }

}
