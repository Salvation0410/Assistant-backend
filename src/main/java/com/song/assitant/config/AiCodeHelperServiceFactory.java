package com.song.assitant.config;

import com.song.assitant.ai.AiCodeHelperService;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.spring.AiService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huang
 * @version 1.0
 * @description AiService创建的工厂类
 * @date 2026/5/21
 */
@AiService
public class AiCodeHelperServiceFactory {

    @Resource
    private ChatModel qwenChatModel;


}