package com.langchain.function;

import com.langchain.function.function.Tools;
import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ToolExecutionResultMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.request.ChatRequestParameters;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.tool.DefaultToolExecutor;
import dev.langchain4j.service.tool.ToolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * @author ：Yunchenyan
 * @date ：Created in 2025/3/15 16:18
 * description：
 */
public class OllamaFunctionCallingTest {

    private static Logger log = LoggerFactory.getLogger(OllamaFunctionCallingTest.class);

    public static void main(String[] args) {
        Tools tools = new Tools();
        ToolSpecification toolSpecification = ToolSpecification.builder()
                .name("add")
                .description("为两个数字求和 并且这两个数字必须为用户主动输入，如果用户没有输入，请通过提问的方式确定这两个数字")
                .parameters(JsonObjectSchema.builder()
                        .addIntegerProperty("arg0", "第一个参数")
                        .addIntegerProperty("arg1", "第二个参数")
                        .required("arg0,arg1") // 必须明确指定必填属性
                        .build())
                .build();

        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434") // 本地ollama url
                .modelName("qwen2.5:3b") // 模型名称
                .logRequests(true)
                .build();

        List<ChatMessage> chatMessages = new ArrayList<>();
        ChatRequest chatRequest;
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                log.info("==================================================");
                log.info("User: ");
                String userQuery = scanner.nextLine();
                UserMessage userMessage = UserMessage.from(userQuery);
                chatMessages.add(userMessage);
                log.info("==================================================");

                if ("exit".equalsIgnoreCase(userQuery)) {
                    break;
                }

                chatRequest = ChatRequest.builder()
                        .messages(chatMessages)
                        .parameters(ChatRequestParameters.builder()
                                .toolSpecifications(toolSpecification)
                                .build())
                        .build();
                AiMessage aiMessage = model.chat(chatRequest).aiMessage();
                chatMessages.add(aiMessage);
                log.info("==================================================");
                if (!aiMessage.hasToolExecutionRequests()) {
                    log.info("Assistant: " + aiMessage.text());
                } else {
                    List<ToolExecutionRequest> toolExecutionRequests = aiMessage.toolExecutionRequests();
                    toolExecutionRequests.forEach(toolExecutionRequest -> {
                        ToolExecutor toolExecutor = new DefaultToolExecutor(tools, toolExecutionRequest);
                        String result = toolExecutor.execute(toolExecutionRequest, UUID.randomUUID().toString());
                        ToolExecutionResultMessage toolExecutionResultMessages = ToolExecutionResultMessage.from(toolExecutionRequest, result);
                        chatMessages.add(toolExecutionResultMessages);
                    });
                    chatRequest = ChatRequest.builder()
                            .messages(chatMessages)
                            .parameters(ChatRequestParameters.builder()
                                    .toolSpecifications(toolSpecification)
                                    .build())
                            .build();
                    aiMessage = model.chat(chatRequest).aiMessage();
                    log.info("Assistant: " + aiMessage.text());
                }
            }
        }

    }
}
