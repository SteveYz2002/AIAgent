package com.steve.steveaiagent.agent.model;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SteveManusTest {

    @Resource
    private SteveManus steveManus;

    @Test
    public void test() {
        String userPrompt = """
                我的另一半居住在北京市朝阳区，请帮我找到5公里内合适的约会地点，
                并结合一些网络图片，制定一份详细的约会计划，
                并以 PDF 格式输出""";
        String result = steveManus.run(userPrompt);
        assertNotNull(result);
    }

}