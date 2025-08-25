package com.steve.steveaiagent.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebScrapingToolTest {

    @Test
    void scrapeWebPage() {
        WebScrapingTool webScrapingTool = new WebScrapingTool();
        String url = "https://java2ai.com/";
        String result = webScrapingTool.scrapeWebPage(url);
        assertNotNull(result);
    }
}