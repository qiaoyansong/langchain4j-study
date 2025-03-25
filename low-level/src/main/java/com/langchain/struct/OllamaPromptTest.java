package com.langchain.struct;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.output.ServiceOutputParser;

/**
 * @author ：Qiao Yansong
 * @date ：Created in 2025/3/25 19:57
 * description：
 */
public class OllamaPromptTest {

    private static ServiceOutputParser serviceOutputParser = new ServiceOutputParser();
    public static void main(String[] args) throws NoSuchMethodException {
        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434") // 本地ollama url
                .modelName("qwen:4b") // 模型名称
                .build();

        System.out.println(model.chat(UserMessage.from("我叫 xxx，我今年33岁 我是个女生 很高兴认识你 " + serviceOutputParser.outputFormatInstructions(OllamaJsonModeTest.Person.class))).aiMessage().text());
    }

    public class Person {

        private String name;

        private int age;

        private Boolean isMale;
    }
}
