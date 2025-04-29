package com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.callback;

import com.asialjim.microapplet.mams.channel.wechat.WeChatOfficialCons;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 消息处理器
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CallMsgHandler {
    /**
     * 消息类型
     */
    WeChatOfficialCons.XmlMsgType msgType();

    /**
     * 事件类型
     */
    WeChatOfficialCons.XmlEventType eventType() default WeChatOfficialCons.XmlEventType.Empty;
}