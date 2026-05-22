package com.song.assitant.config;

import com.song.assitant.ai.AiCodeHelperService;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huang
 * @version 1.0
 * @description AiService创建的工厂类
 * @date 2026/5/21
 */
//@Configuration
public class AiCodeHelperServiceFactory {

    @Resource
    private ChatModel qwenChatModel;

    @Bean
    public AiCodeHelperService aiCodeHelperService() {
        // create方法提供了创建AiService的类别 原理是通过java的反射和动态代理机制完成AiService的创建
        return AiServices.create(AiCodeHelperService.class, qwenChatModel);
    }
}