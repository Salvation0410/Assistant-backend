package com.song.assitant.ai;

import dev.langchain4j.service.SystemMessage;

/**
 * @author huang
 * @version 1.0
 * @description AiService功能使用
 * @date 2026/5/21
 */
public interface AiCodeHelperService {
    @SystemMessage(fromResource = "system-prompt.txt")
    String chat(String userMessage);
}
