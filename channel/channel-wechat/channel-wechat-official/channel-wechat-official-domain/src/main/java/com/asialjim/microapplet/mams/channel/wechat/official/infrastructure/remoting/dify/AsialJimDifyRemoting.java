/*
 * Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.dify;

import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.dify.meta.FunReq;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.dify.meta.FunRes;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.dify.meta.Message;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.dify.meta.MessageRes;
import com.asialjim.microapplet.remote.http.annotation.HttpHeader;
import com.asialjim.microapplet.remote.http.annotation.HttpMapping;
import com.asialjim.microapplet.remote.http.annotation.HttpMethod;
import com.asialjim.microapplet.remote.http.annotation.body.JsonBody;
import com.asialjim.microapplet.remote.net.annotation.Server;
import com.asialjim.microapplet.remote.net.jackson.AbstractJacksonUtil;
import com.asialjim.microapplet.remote.net.lifecycle.callback.TextEventStreamResFun;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * AI智能体客户端
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/5/7, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Server(schema = "https", host = "ai.api.asialjim.cn", port = 443, timeout = 200000)
public interface AsialJimDifyRemoting {

    @HttpMapping(
            method = HttpMethod.POST,
            uri = "/v1/chat-messages",
            headers = {
                    @HttpHeader(name = "Authorization", value = "Bearer app-1ogvjtTJB5Em7dlTHin3AIKv")
            })
    MessageRes chat(@JsonBody Message body);

    @HttpMapping(
            method = HttpMethod.POST,
            uri = "/v1/workflows/run",
            headers = {
                    @HttpHeader(name = "Authorization", value = "Bearer app-bbIi25bvOGKHr46CPWGLF3Qn")
            })
    FunRes chat(@JsonBody FunReq body);


    final class DifyResFun implements TextEventStreamResFun {
        private final StringBuilder sb = new StringBuilder();
        private final Set<String> conversation = new HashSet<>();

        @Override
        public void process(BufferedReader reader, Charset charset) {
            Stream<String> lines = reader.lines();
            lines.filter(StringUtils::isNotBlank)
                    .map(item -> StringUtils.replaceOnce(item, "data: ", StringUtils.EMPTY))
                    .map(item -> AbstractJacksonUtil.json2Object(item, MessageRes.class))
                    .filter(Objects::nonNull)
                    .peek(item -> conversation.add(item.getConversation_id()))
                    .map(MessageRes::getAnswer)
                    .peek(System.out::println)
                    .forEach(sb::append);
        }

        public String answer(){
            return sb.toString();
        }

        public String conversation(){
            if (CollectionUtils.isNotEmpty(conversation))
                return conversation.stream().filter(Objects::nonNull).findAny().orElse("");
            return "";
        }
    }
}