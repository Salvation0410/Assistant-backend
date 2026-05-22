package com.song.assitant.ai;

import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.service.Result;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author huang
 * @version 1.0
 * @description
 * @date 2026/5/21
 */


@SpringBootTest
class AiCodeHelperTest {

    @Resource
    private AiCodeHelper aiCodeHelper;

    @Resource
    private AiCodeHelperService aiCodeHelperService;

    @Test
    void chat() {
        aiCodeHelper.chat("你好，我是关羽");
    }
    // 多模态测试
    @Test
    void chatWithMessage() {
        UserMessage userMessage = UserMessage.from(
                TextContent.from("描述图片"),
                ImageContent.from("https://www.codefather.cn/logo.png")
        );
        aiCodeHelper.chatWithMessage(userMessage);
    }
    //会话记忆测试
    @Test
    void chatWithMemory() {
        String result = aiCodeHelperService.chat("你好，我是程序员鱼皮");
        System.out.println(result);
        result = aiCodeHelperService.chat("你好，我是谁来着？");
        System.out.println(result);
    }
    //Rag测试
    @Test
    void chatWithRag() {
        String result = aiCodeHelperService.chat("怎么学习 Java？有哪些常见面试题？");
        System.out.println(result);
    }


}