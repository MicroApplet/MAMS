package com.asialjim.microapplet.mams.channel.wechat.official.controller;

import com.asialjim.microapplet.mams.channel.wechat.official.cors.cmd.WeChatOfficialCallbackMsgOptCmd;
import com.asialjim.microapplet.mams.channel.wechat.official.cors.cmd.WeChatOfficialCallbackUrlCheckCmd;
import com.asialjim.microapplet.mams.channel.wechat.official.cors.cmd.WeChatOfficialPageUrlQryCmd;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;


/**
 * 微信公众号消息/事件回调服务器
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/27, &nbsp;&nbsp; <em>version:1.0</em>
 */
@RestController
@RequestMapping
public class WeChatOfficialCallBackController {

    /**
     * 开发者服务器验证
     *
     * @param appid     {@link String appid}
     * @param signature {@link String signature}
     * @param timestamp {@link String timestamp}
     * @param nonce     {@link String nonce}
     * @param echostr   {@link String echostr}
     * @since 2025/4/27
     */
    @GetMapping("/{appid}/callback")
    public String get(@PathVariable("appid") String appid,
                      @RequestParam String signature,
                      @RequestParam String timestamp,
                      @RequestParam String nonce,
                      @RequestParam String echostr) {

        WeChatOfficialCallbackUrlCheckCmd cmd = WeChatOfficialCallbackUrlCheckCmd.cmd(appid, signature, timestamp, nonce, echostr);
        return cmd.execute();
    }

    /**
     * 公众号消息/事件回调
     *
     * @param appid         {@link String appid}
     * @param signature     {@link String signature}
     * @param timestamp     {@link String timestamp}
     * @param nonce         {@link String nonce}
     * @param openid        {@link String openid}
     * @param encrypt_type  {@link String encrypt_type}
     * @param msg_signature {@link String msg_signature}
     * @param request       {@link HttpServletRequest request}
     * @return {@link String }
     * @since 2025/4/29
     */
    @PostMapping("/{appid}/callback")
    public String post(@PathVariable("appid") String appid,
                       @RequestParam String signature,
                       @RequestParam String timestamp,
                       @RequestParam String nonce,
                       @RequestParam String openid,
                       @RequestParam(required = false) String encrypt_type,
                       @RequestParam(required = false) String msg_signature,
                       HttpServletRequest request) {
        WeChatOfficialCallbackMsgOptCmd cmd = WeChatOfficialCallbackMsgOptCmd.cmd(appid, signature, timestamp, nonce, openid, encrypt_type, msg_signature, request);
        return cmd.execute();
    }

    /**
     * 授权网页链接处理
     *
     * @param appid {@link String appid}
     * @param scene {@link String scene}
     * @param code  {@link String code}
     * @param state {@link String state}
     * @return {@link String }
     * @since 2025/4/29
     */
    @GetMapping("/{appid}/page/{scene}")
    public String page(@PathVariable("appid") String appid,
                       @PathVariable("scene") String scene,
                       @RequestParam String code,
                       @RequestParam String state) {

        return WeChatOfficialPageUrlQryCmd.cmd(appid, scene, code, state).execute();
    }
}