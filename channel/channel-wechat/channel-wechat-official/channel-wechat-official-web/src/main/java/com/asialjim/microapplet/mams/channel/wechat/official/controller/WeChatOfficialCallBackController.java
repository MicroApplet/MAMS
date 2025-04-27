package com.asialjim.microapplet.mams.channel.wechat.official.controller;

import com.asialjim.microapplet.mams.channel.wechat.official.service.WeChatOfficialCallbackService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    @Resource private WeChatOfficialCallbackService weChatOfficialCallbackService;

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

        return this.weChatOfficialCallbackService.get(appid,signature,timestamp,nonce,echostr);
    }

    @PostMapping("/{appid}/callback")
    public String post(@PathVariable("appid") String appid,
                       @RequestParam String signature,
                       @RequestParam String timestamp,
                       @RequestParam String nonce,
                       @RequestParam String openid,
                       @RequestParam String encrypt_type,
                       @RequestParam String msg_signature,
                       HttpServletRequest request) {

        return "success";
    }

    @GetMapping("/{appid}/page/{scene}")
    public void page(@PathVariable("appid") String appid,
                     @PathVariable("scene") String scene,
                     @RequestParam String code,
                     @RequestParam String state,
                     HttpServletRequest request) {


    }
}