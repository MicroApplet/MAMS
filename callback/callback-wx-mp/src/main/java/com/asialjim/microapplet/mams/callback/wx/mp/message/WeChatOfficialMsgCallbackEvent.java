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

import com.asialjim.microapplet.commons.standard.utils.XmlUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.NullNode;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

/**
 * 微信公众号消息回调事件
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/6/24, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Accessors(chain = true)
public class WeChatOfficialMsgCallbackEvent implements Serializable {
    @Serial
    private static final long serialVersionUID = -2944465643702015617L;

    private String appid;
    private String signature;
    private String timestamp;
    private String nonce;
    private String openid;
    private String encryptType;
    private String msgSignature;
    private String sourceXml;
    private String decryptXml;
    private JsonNode msgNode;
    private String msgType;
    private String event;
    private String eventKey;

    public WeChatOfficialMsgCallbackEvent init() {
        String xml = StringUtils.defaultIfBlank(decryptXml, sourceXml);
        this.msgNode = parse(xml);
        this.msgType = nodeTextValue("MsgType");
        this.event = nodeTextValue("Event");
        this.eventKey = nodeTextValue("EventKey");
        return this;
    }

    public boolean isEvent() {
        return StringUtils.isNotBlank(this.event);
    }

    public String nodeTextValue(String key) {
        return Optional.ofNullable(this.msgNode)
                .filter(item -> item.hasNonNull(key))
                .map(item -> item.get(key))
                .map(JsonNode::asString)
                .orElse(StringUtils.EMPTY);
    }

    public JsonNode nodeOf(String key) {
        return Optional.ofNullable(this.msgNode)
                .filter(item -> item.hasNonNull(key))
                .map(item -> item.get(key))
                .orElse(NullNode.instance);
    }

    private JsonNode parse(String xml) {
        try {
            return XmlUtil.instance.toTree(xml);
        } catch (Exception e) {
            throw new IllegalArgumentException("微信公众号回调 XML 解析失败", e);
        }
    }
}
