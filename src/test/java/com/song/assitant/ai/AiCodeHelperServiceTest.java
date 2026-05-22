package com.song.assitant.ai;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author huang
 * @version 1.0
 * @description
 * @date 2026/5/22
 */


@SpringBootTest
class AiCodeHelperServiceTest {

    @Resource
    private AiCodeHelperService aiCodeHelperService;

    // 结构化输出测试
    @Test
    void chatForReport() {
        String userMessage = "你好，我是程序员鱼皮，学编程两年半，请帮我制定学习报告";
        AiCodeHelperService.Record report = aiCodeHelperService.chatForReport(userMessage);
        System.out.println(report);
    }
}