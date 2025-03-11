package com.langchain.util;

import dev.langchain4j.service.TokenStream;

/**
 * @author ：Qiao Yansong
 * @date ：Created in 2025/3/11 16:24
 * description：
 */
public interface StreamAssistant {
    TokenStream chat(String message);
}
