package com.steve.steveaiagent.rag;


import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoveAppDocumentLoaderTest {

    @Resource
    private LoveAppDocumentLoader loveAppDocumentLoader;


    @Test
    void loadMarkdownDocuments() {
        loveAppDocumentLoader.loadMarkdownDocuments();
    }

}