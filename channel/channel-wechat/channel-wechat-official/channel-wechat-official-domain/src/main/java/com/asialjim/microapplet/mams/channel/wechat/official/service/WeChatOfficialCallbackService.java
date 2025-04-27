package com.asialjim.microapplet.mams.channel.wechat.official.service;

import com.asialjim.microapplet.mams.channel.wechat.official.cors.WeChatOfficialCallbackUrlCheckCmd;
import org.springframework.stereotype.Component;

/**
 * 微信公众号回调应用服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/27, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class WeChatOfficialCallbackService {

    /**
     * 开发者服务器验证你
     *
     * @param appid     {@link String appid}
     * @param signature {@link String signature}
     * @param timestamp {@link String timestamp}
     * @param nonce     {@link String nonce}
     * @param echostr   {@link String echostr}
     * @return {@link String }
     * @since 2025/4/27
     */
    public String get(String appid, String signature, String timestamp, String nonce, String echostr) {
        WeChatOfficialCallbackUrlCheckCmd cmd = WeChatOfficialCallbackUrlCheckCmd.cmd(appid, signature, timestamp, nonce, echostr);
        return cmd.execute();
    }
}