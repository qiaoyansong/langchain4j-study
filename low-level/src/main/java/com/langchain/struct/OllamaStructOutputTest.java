package com.langchain.struct;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.request.ChatRequestParameters;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.chat.request.json.JsonSchema;
import dev.langchain4j.model.ollama.OllamaChatModel;

import java.util.ArrayList;
import java.util.List;

import static dev.langchain4j.model.chat.request.ResponseFormatType.JSON;

/**
 * @author ：Qiao Yansong
 * @date ：Created in 2025/3/25 19:13
 * description：使用struct output
 */
public class OllamaStructOutputTest {
    public static void main(String[] args) {
        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434") // 本地ollama url
                .modelName("qwen:4b") // 模型名称
                .build();

        ChatRequestParameters parameters = ChatRequestParameters.builder()
                .responseFormat(ResponseFormat.builder()
                        .type(JSON)
                        .jsonSchema(JsonSchema.builder()
                                .rootElement(JsonObjectSchema.builder()
                                        .addIntegerProperty("age", "这个人的年龄")
                                        .addBooleanProperty("isMale", "这个人是否男性")
                                        .build())
                                .build())
                        .build())
                .build();

        List<ChatMessage> messages = new ArrayList<>();
        messages.add(UserMessage.from("我叫 xxx，我今年33岁 我是个女生 很高兴认识你"));

        ChatRequest chatRequest = ChatRequest.builder()
                .messages(messages)
                .parameters(parameters)
                .build();

        System.out.println(model.chat(chatRequest)
                .aiMessage()
                .text());
    }
}
