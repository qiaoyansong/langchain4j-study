package com.lanchain.util;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * @author ：Yunchenyan
 * @date ：Created in 2025/3/10 23:56
 * description：
 */
public interface TitleAssistant {

    @UserMessage("根据用户输入的 {{title}} 以及 {{keyword}} 帮用户生成具有吸引力的文章标题")
    String answer(@V("title") String title, @V("keyword") String keyword);

}
