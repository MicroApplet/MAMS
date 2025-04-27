package com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.meta;

import lombok.Data;

/**
 * 微信公众平台通用响应结果 *
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/27, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
public class BaseWeChatApiRes implements WeChatApiRes {
    private static final long serialVersionUID = -2952067351260912092L;

    protected Integer errcode;
    protected String errmsg;
}