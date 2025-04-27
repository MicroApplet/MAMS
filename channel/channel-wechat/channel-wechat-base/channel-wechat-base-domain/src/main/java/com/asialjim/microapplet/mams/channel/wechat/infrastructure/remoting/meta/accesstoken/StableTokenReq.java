package com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.meta.accesstoken;

import lombok.Data;

import java.io.Serializable;

@Data
public class StableTokenReq implements Serializable {
    private static final long serialVersionUID = 8374085616461379633L;

    private String grant_type;
    private String appid;
    private String secret;
    private Boolean force_refresh;
}