package com.langchain.rag;

import com.langchain.util.Assistant;
import com.langchain.util.LangchainUtil;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：Qiao Yansong
 * @date ：Created in 2025/3/10 19:34
 * description：
 */
public class OpenAIModelRagTest {

    public static void main(String[] args) {
        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("X-Luban-LLM-Service-APPId", "k8s-sv0-mhkfwm-1737533899353");
        customHeaders.put("X-ModelHub-Token", "NJZTFIV7Q5AWSZIJ2AGF26PMTMTRISI3NN3QZSVQBDW7ZYEFSEY7V43L4T2RVIX6CWAVMVROO2WJ7RCX4ZBY5MEI3A4CHAKHCZBE22NV7YLCWXWA3U27JZPVJEL3MM2CZNJ43OAVG3UGASISFVL66WK2QQ======");
        ChatLanguageModel model = OpenAiChatModel.builder()
                .baseUrl("http://luban-llm.intra.xiaojukeji.com/v1")
                .apiKey("k8s-sv0-mhkfwm-1737533899353")
                .customHeaders(customHeaders)
                .modelName("/mnt/mhdata/models/public/Qwen2-VL-7B-Instruct")
                .logRequests(true)
                .maxTokens(512)
                .build();

        Document documents = ClassPathDocumentLoader.loadDocument("story.txt");

        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        EmbeddingStoreIngestor.ingest(documents, embeddingStore);

        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(model)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore))
                .build();

        LangchainUtil.startConversationWith(assistant);
    }

}
