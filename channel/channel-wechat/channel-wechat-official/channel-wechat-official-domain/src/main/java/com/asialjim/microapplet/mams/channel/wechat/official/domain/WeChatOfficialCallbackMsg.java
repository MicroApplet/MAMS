package com.asialjim.microapplet.mams.channel.wechat.official.domain;

import com.asialjim.microapplet.mams.channel.wechat.WeChatOfficialCons;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.TextNode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Objects;

/**
 * 微信公众号消息/事件
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Getter
public class WeChatOfficialCallbackMsg {
    private final WeChatOfficialCons.XmlMsgType msgType;
    private final WeChatOfficialCons.XmlEventType eventType;
    private final JsonNode msg;

    public WeChatOfficialCallbackMsg(JsonNode msg) {
        this.msg = msg;
        JsonNode msgTypeNode = msg.get(WeChatOfficialCons.XmlMsgTag.msgType);
        String msgTypeStr = msgTypeNode.asText();
        this.msgType = WeChatOfficialCons.XmlMsgType.codeOf(msgTypeStr);
        // 事件
        if (WeChatOfficialCons.XmlMsgType.Event.equals(this.msgType)) {
            JsonNode eventTypeNode = msg.get(WeChatOfficialCons.XmlMsgTag.event);
            this.eventType = WeChatOfficialCons.XmlEventType.codeOf(eventTypeNode.asText());
        } else {
            this.eventType = WeChatOfficialCons.XmlEventType.Empty;
        }
    }

    public TextNode toUserName() {
        return (TextNode) msg.get(WeChatOfficialCons.XmlMsgTag.toUserName);
    }

    public TextNode fromUserName() {
        return (TextNode) msg.get(WeChatOfficialCons.XmlMsgTag.fromUserName);
    }

    public LongNode createTime() {
        String createTime = textNodeValue(WeChatOfficialCons.XmlMsgTag.createTime);
        long aLong = NumberUtils.toLong(createTime);
        return LongNode.valueOf(aLong);
    }

    public TextNode msgType() {
        return (TextNode) msg.get(WeChatOfficialCons.XmlMsgTag.msgType);
    }

    public TextNode msgId() {
        return (TextNode) msg.get(WeChatOfficialCons.XmlMsgTag.msgId);
    }

    public TextNode msgDataId() {
        return (TextNode) msg.get(WeChatOfficialCons.XmlMsgTag.msgDataId);
    }

    public IntNode idx() {
        String idx = textNodeValue(WeChatOfficialCons.XmlMsgTag.idx);
        return IntNode.valueOf(NumberUtils.toInt(idx));
    }

    public String textNodeValue(String tag){
        JsonNode jsonNode = msg.get(tag);
        if (Objects.isNull(jsonNode))
            return StringUtils.EMPTY;
        return jsonNode.asText();
    }

}