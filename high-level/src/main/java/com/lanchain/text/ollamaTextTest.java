package com.lanchain.text;

import com.lanchain.util.TitleAssistant;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;

/**
 * @author ：Yunchenyan
 * @date ：Created in 2025/3/23 11:18
 * description：
 */
public class ollamaTextTest {

    public static void main(String[] args) {
        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434") // 本地ollama url
                .modelName("qwen2.5:3b") // 模型名称
                .build();

        TitleAssistant titleAssistant = AiServices.builder(TitleAssistant.class)
                .chatLanguageModel(model)
                .build();

        System.out.println(titleAssistant.answer("特朗普就任新的美国总统，要强占加拿大", "特朗普"));
    }

}
