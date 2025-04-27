package com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.meta.accesstoken;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ComponentAccessTokenReq implements Serializable {
    private static final long serialVersionUID = -5465930084313569642L;

    private String component_appid;
    private String component_appsecret;
    private String component_verify_ticket;
}