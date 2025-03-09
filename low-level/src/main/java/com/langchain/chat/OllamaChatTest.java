package com.langchain.chat;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author ：Yunchenyan
 * @date ：Created in 2025/3/9 22:19
 * description：
 */
public class OllamaChatTest {

    public static void main(String[] args) {
//        without_memory();
        with_memory();
    }

    /**
     * 你好！很高兴能够帮助你。请问你需要什么样的帮助？
     * 我叫乔岩松
     * 您好，乔岩松。有什么我可以帮你的吗？
     * 我叫什么名字
     * 您好，乔岩松。您的姓名是乔岩松。有什么我可以帮你的吗？
     * 你好，我现在改名叫qys了
     * 您好，乔岩松。您的姓名是乔岩松。您好，qys。您好！有什么我可以帮你的吗？
     * 我现在叫什么名字
     * 您好，qys。您好！有什么我可以帮你的吗？
     */
    private static void with_memory() {
        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434") // 本地ollama url
                .modelName("qwen:4b") // 模型名称
                .build();

        Scanner scanner = new Scanner(System.in);
        String message;
        UserMessage userMessage;
        AiMessage aiMessage;
        List<ChatMessage> messages = new ArrayList<>();
        while (true) {
            message = scanner.next();
            if (message.equalsIgnoreCase("-1")) {
                break;
            }
            userMessage = UserMessage.userMessage(message);
            messages.add(userMessage);
            aiMessage = model.chat(messages).aiMessage();
            messages.add(aiMessage);
            System.out.println(aiMessage.text());
        }
    }

    /**
     * 使用ollama模仿一次多轮对话, 发现其并没有记忆功能
     * 你好！有什么可以帮助您的吗？
     * 你是谁
     * 我是通义千问，由阿里云开发。我是一个能够回答问题、创作文字，还能表达观点、撰写代码的超大规模语言模型。如果您有任何问题或需要帮助，请随时告诉我，我会尽力提供支持和解答。
     * 我叫乔岩松，很高兴认识你
     * 你好，乔岩松。我也很高兴见到您。在我们交往的过程中，如果有任何需要帮助的事情，请随时告诉我。我相信，只要我们一起努力，我们就一定能够取得成功。再次欢迎您来到我的世界里。期待着与您的交流和合作。谢谢。
     * 我叫什么
     * 你好！你可能是想问“你好吗？”或者“很高兴见到你！”如果你有其他问题，也欢迎随时提问哦！
     */
    private static void without_memory() {
        ChatLanguageModel model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434") // 本地ollama url
                .modelName("qwen:4b") // 模型名称
                .build();

        Scanner scanner = new Scanner(System.in);
        String message;
        UserMessage userMessage;
        while (true) {
            message = scanner.next();
            if (message.equalsIgnoreCase("-1")) {
                break;
            }
            userMessage = UserMessage.userMessage(message);
            System.out.println(model.chat(userMessage).aiMessage().text());
        }
    }

}
