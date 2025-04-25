package com.asialjim.microapplet.mams.channel.wechat.constant;

/**
 * 微信常量
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 2.0
 * @since 2025/4/25, &nbsp;&nbsp; <em>version:2.0</em>
 */
public interface WeChatCons {

    interface Api {
        String DefaultSchema = "https";
        String DefaultWeChatHost = "api.weixin.qq.com";
        int DefaultWeChatPort = 443;
    }


    interface Namespace {

        String WeChat = "wechat";
    }

    interface Supplier {

        String Tencent = "tencent";
    }
}