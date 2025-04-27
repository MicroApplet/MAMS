package com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.meta.accesstoken;

import com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.meta.BaseWeChatApiRes;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AuthorizerAccessTokenRes extends BaseWeChatApiRes {
    private static final long serialVersionUID = 3394274962193627071L;

    private String authorizer_access_token;
    private Integer expires_in;
    private String authorizer_refresh_token;
}