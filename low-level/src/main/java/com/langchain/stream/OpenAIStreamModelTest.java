package com.langchain.stream;

import com.langchain.util.StreamAssistant;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolExecution;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：Qiao Yansong
 * @date ：Created in 2025/3/11 16:21
 * description：
 */
public class OpenAIStreamModelTest {

    public static void main(String[] args) {
        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("X-Luban-LLM-Service-APPId", "k8s-sv0-mhkfwm-1737533899353");
        customHeaders.put("X-ModelHub-Token", "NJZTFIV7Q5AWSZIJ2AGF26PMTMTRISI3NN3QZSVQBDW7ZYEFSEY7V43L4T2RVIX6CWAVMVROO2WJ7RCX4ZBY5MEI3A4CHAKHCZBE22NV7YLCWXWA3U27JZPVJEL3MM2CZNJ43OAVG3UGASISFVL66WK2QQ======");
        OpenAiStreamingChatModel model = OpenAiStreamingChatModel.builder()
                .baseUrl("http://luban-llm.intra.xiaojukeji.com/v1")
                .apiKey("k8s-sv0-mhkfwm-1737533899353")
                .customHeaders(customHeaders)
                .modelName("/mnt/mhdata/models/public/Qwen2-VL-7B-Instruct")
                .logRequests(true)
                .maxTokens(512)
                .build();

        StreamAssistant streamAssistant = AiServices.create(StreamAssistant.class, model);

        streamAssistant.chat("你好")
                .onPartialResponse((String token) -> System.out.println("onPartialResponse" + token))
                .onRetrieved(contents -> System.out.println("onRetrieved" + contents))
                .onToolExecuted((ToolExecution toolExecution) -> System.out.println("onToolExecuted" + toolExecution))
                .onCompleteResponse(response -> System.out.println("onCompleteResponse" + response))
                .onError((Throwable error) -> error.printStackTrace())
                .start();
    }
}
