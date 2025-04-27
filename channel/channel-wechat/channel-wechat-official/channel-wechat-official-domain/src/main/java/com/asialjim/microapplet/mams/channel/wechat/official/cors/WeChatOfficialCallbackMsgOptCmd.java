package com.asialjim.microapplet.mams.channel.wechat.official.cors;

import com.asialjim.microapplet.common.application.App;
import com.asialjim.microapplet.common.cors.Cmd;
import com.asialjim.microapplet.mams.channel.wechat.official.domain.WeChatOfficialAppAgg;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信公众号回调结果
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/27, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Getter
@RequiredArgsConstructor
public class WeChatOfficialCallbackMsgOptCmd implements Cmd<String> {
    private final String appid;
    private final String signature;
    private final String timestamp;
    private final String nonce;
    private final String openid;
    private final String encrypt_type;
    private final String msg_signature;
    private final HttpServletRequest request;

    @Setter
    private WeChatOfficialAppAgg weChatOfficialAppAgg;
    private JsonNode msg;

    public static WeChatOfficialCallbackMsgOptCmd cmd(
            String appid,
            String signature,
            String timestamp,
            String nonce,
            String openid,
            String encrypt_type,
            String msg_signature,
            HttpServletRequest request
    ) {
        WeChatOfficialCallbackMsgOptCmd cmd = new WeChatOfficialCallbackMsgOptCmd(appid, signature, timestamp, nonce, openid, encrypt_type, msg_signature, request);
        App.beanOpt(WeChatOfficialAppAgg.class).ifPresent(item -> cmd.setWeChatOfficialAppAgg(item.withAppid(appid)));
        return cmd;
    }

    @Override
    public String execute() {
        return this.weChatOfficialAppAgg.callback(this);
    }


}