package com.song.assitant.config;

import com.song.assitant.ai.AiCodeHelperService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author huang
 * @version 1.0
 * @description AiService服务的测试
 * @date 2026/5/21
 */

@SpringBootTest
class AiCodeHelperServiceFactoryTest {
    @Resource
    private AiCodeHelperService aiCodeHelperService;

    @Test
    void chat() {
        String result = aiCodeHelperService.chat("你好，我是刘备");
        System.out.println(result);
    }
}