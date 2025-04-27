package com.asialjim.microapplet.mams.channel.wechat.official.domain;

import com.asialjim.microapplet.common.application.App;
import com.asialjim.microapplet.mams.channel.base.ChlAppType;
import com.asialjim.microapplet.mams.channel.base.NormalChlAppType;
import com.asialjim.microapplet.mams.channel.wechat.domain.WeChatAppAgg;
import com.asialjim.microapplet.mams.channel.wechat.infrastructure.adaptor.WeChatApiResultEnumeration;
import com.asialjim.microapplet.mams.channel.wechat.official.cors.WeChatOfficialCallbackMsgOptCmd;
import com.asialjim.microapplet.mams.channel.wechat.official.cors.WeChatOfficialCallbackUrlCheckCmd;
import com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.aes.WeChatOfficialMsgCrypt;
import com.asialjim.microapplet.mams.channel.wechat.pojo.WeChatApp;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * 微信公众号应用聚合根
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/27, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Setter
@Component
@Scope("request")
public class WeChatOfficialAppAgg {
    private transient WeChatAppAgg weChatAppAgg;
    private transient WeChatOfficialMsgCrypt msgCrypt;

    public WeChatOfficialAppAgg withAppid(String appid) {
        Optional<WeChatAppAgg> opt = App.beanOpt(WeChatAppAgg.class);
        opt.ifPresent(item -> setWeChatAppAgg(item.withId(appid)));
        return this;
    }

    public WeChatApp weChatApp() {
        WeChatApp weChatApp = this.weChatAppAgg.weChatApp();
        if (Objects.isNull(weChatApp))
            WeChatApiResultEnumeration.CODE_40013.throwBiz();
        ChlAppType appType = weChatApp.getAppType();

        if (!NormalChlAppType.WeChatOfficial.equals(appType))
            WeChatApiResultEnumeration.CODE_40013.throwBiz();

        return weChatApp;
    }

    public WeChatOfficialMsgCrypt msgCrypt() {
        if (Objects.nonNull(msgCrypt))
            return msgCrypt;

        try {
            WeChatApp weChatApp = weChatApp();
            this.msgCrypt = new WeChatOfficialMsgCrypt(weChatApp.getToken(), weChatApp.getAesKey(), weChatApp.getAppid());
            return this.msgCrypt;
        } catch (Throwable t) {
            throw WeChatApiResultEnumeration.CODE_40013.sysException();
        }
    }

    public String callbackUrlCheck(WeChatOfficialCallbackUrlCheckCmd cmd) {
        try {
            msgCrypt().verifyUrl(cmd.getSignature(),cmd.getTimestamp(),cmd.getNonce(),cmd.getEchostr());
            return cmd.getEchostr();
        } catch (Throwable t) {
            return "false";
        }
    }

    public String callback(WeChatOfficialCallbackMsgOptCmd cmd) {
        // TODO
        return "success";
    }
}