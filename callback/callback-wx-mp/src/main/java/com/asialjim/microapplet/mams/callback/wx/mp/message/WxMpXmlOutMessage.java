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

package com.asialjim.microapplet.mams.callback.wx.mp.message;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 微信公众号被动回复消息
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/6/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Accessors(chain = true)
public class WxMpXmlOutMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 6920442425097742418L;

    private String toUserName;
    private String fromUserName;
    private Long createTime;
    private String msgType;
    private String content;

    public static WxMpXmlOutMessage text(String toUserName, String fromUserName, String content) {
        return new WxMpXmlOutMessage()
                .setToUserName(toUserName)
                .setFromUserName(fromUserName)
                .setCreateTime(System.currentTimeMillis() / 1000)
                .setMsgType("text")
                .setContent(content);
    }

    public String toXml() {
        return """
                <xml>
                    <ToUserName><![CDATA[%s]]></ToUserName>
                    <FromUserName><![CDATA[%s]]></FromUserName>
                    <CreateTime>%d</CreateTime>
                    <MsgType><![CDATA[%s]]></MsgType>
                    <Content><![CDATA[%s]]></Content>
                </xml>
                """.formatted(toUserName, fromUserName, createTime, msgType, content);
    }
}
