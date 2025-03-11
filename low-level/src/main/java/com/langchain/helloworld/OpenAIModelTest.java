package com.langchain.helloworld;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：Qiao Yansong
 * @date ：Created in 2025/3/10 16:45
 * description：
 */
public class OpenAIModelTest {

    public static void main(String[] args) {
        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("X-Luban-LLM-Service-APPId", "k8s-sv0-a5x164-1741241568836");
        customHeaders.put("X-ModelHub-Token", "NJZTFIV7Q5AWSZIJ2AGF26PMTMTRISI3NN3QZSVQBDW7ZYEFSEY7V43L4T2RVIX6CWAVMVROO2WJ7RCX4ZBY5MEI3A4CHAKHCZBE22NV7YLCWXWA3U27JZPVJEL3MM2CZNJ43OAVG3UGASISFVL66WK2QQ======");
        ChatLanguageModel model = OpenAiChatModel.builder()
                .baseUrl("http://luban-llm.intra.xiaojukeji.com/v1")
                .apiKey("k8s-sv0-a5x164-1741241568836")
                .customHeaders(customHeaders)
                .maxTokens(512)
                .logRequests(Boolean.TRUE)
                .logResponses(Boolean.TRUE)
                .build();

        System.out.println(model.chat(UserMessage.from("你是谁")).aiMessage().text());
    }
}
