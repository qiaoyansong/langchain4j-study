package com.langchain.helloworld;


import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

/**
 * @author ：Yunchenyan
 * @date ：Created in 2025/3/9 21:49
 * description：
 */
public class OllamaModelTest {

    /**
     * 使用ollama进行一次本地测试
     */
    public static void main(String[] args)
    {
        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434") // 本地ollama url
                .modelName("qwen:4b") // 模型名称
                .build();
        System.out.println(model.chat(UserMessage.from("你是谁？")).aiMessage().text());
    }

}
