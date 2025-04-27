package com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.meta.accesstoken;

import com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.meta.BaseWeChatApiRes;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ComponentAccessTokenRes extends BaseWeChatApiRes {
    private static final long serialVersionUID = -1496983050795657912L;

    private String component_access_token;
    private Integer expires_in;
}