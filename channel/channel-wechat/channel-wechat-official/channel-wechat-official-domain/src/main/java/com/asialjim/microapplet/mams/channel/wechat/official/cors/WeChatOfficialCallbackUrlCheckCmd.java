package com.asialjim.microapplet.mams.channel.wechat.official.cors;

import com.asialjim.microapplet.common.application.App;
import com.asialjim.microapplet.common.cors.Cmd;
import com.asialjim.microapplet.mams.channel.wechat.official.domain.WeChatOfficialAppAgg;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


/**
 * 微信公众号开发者url验证命令
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/27, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Getter
@RequiredArgsConstructor
public class WeChatOfficialCallbackUrlCheckCmd implements Cmd<String> {
    @Setter
    private WeChatOfficialAppAgg weChatOfficialAppAgg;
    private final String appid;
    private final String signature;
    private final String timestamp;
    private final String nonce;
    private final String echostr;


    public static WeChatOfficialCallbackUrlCheckCmd cmd(String appid, String signature, String timestamp,String nonce,String echostr){
        WeChatOfficialCallbackUrlCheckCmd cmd = new WeChatOfficialCallbackUrlCheckCmd(appid, signature, timestamp, nonce, echostr);
        App.beanOpt(WeChatOfficialAppAgg.class).ifPresent(item -> cmd.setWeChatOfficialAppAgg(item.withAppid(appid)));
        return cmd;
    }


    @Override
    public String execute() {
        return this.weChatOfficialAppAgg.callbackUrlCheck(this);
    }
}