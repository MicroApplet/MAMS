package com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.meta.accesstoken;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class AuthorizerAccessTokenReq implements Serializable {
    private static final long serialVersionUID = 2835803804815490312L;

    private String component_appid;
    private String authorizer_appid;
    private String authorizer_refresh_token;
}