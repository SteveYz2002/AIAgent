package com.steve.steveaiagent.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceDownloadToolTest {

    @Test
    void downloadResource() {
        ResourceDownloadTool resourceDownloadTool = new ResourceDownloadTool();
        String url = "https://stevecp1-1364043722.cos.ap-beijing.myqcloud.com//public/1932727584688029697/2025-06-25_k9fJgx1NhOv7HLUi.webp";
        String fileName = "图片.webp";
        String result = resourceDownloadTool.downloadResource(url, fileName);
        assertNotNull(result);
    }
}