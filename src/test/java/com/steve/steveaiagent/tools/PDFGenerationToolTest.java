package com.steve.steveaiagent.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PDFGenerationToolTest {

    @Test
    void generatePDF() {
        PDFGenerationTool pdfGenerationTool = new PDFGenerationTool();
        String fileName = "test.pdf";
        String content = "PDF测试，test";
        String result = pdfGenerationTool.generatePDF(fileName, content);
        assertNotNull(result);

    }
}