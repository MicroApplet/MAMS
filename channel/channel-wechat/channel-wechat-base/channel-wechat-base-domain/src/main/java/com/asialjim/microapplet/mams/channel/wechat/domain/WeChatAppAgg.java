package com.asialjim.microapplet.mams.channel.wechat.domain;

import com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.accesstokken.WeChatAccessTokenCache;
import com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.accesstokken.WeChatAccessTokenRemoting;
import com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.meta.accesstoken.WeChatAccessTokenRes;
import com.asialjim.microapplet.mams.channel.wechat.infrastructure.repository.WeChatAppRepository;
import com.asialjim.microapplet.mams.channel.wechat.pojo.WeChatApp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.util.Optional;
import java.util.UUID;

/**
 * 微信应用聚合根
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 3.0
 * @since 2025/4/25, &nbsp;&nbsp; <em>version:3.0</em>
 */
@Component
@Scope("request")
public class WeChatAppAgg {
    @Resource
    private WeChatAccessTokenRemoting accessTokenRemoting;
    @Resource
    private WeChatAccessTokenCache accessTokenCache;
    @Resource
    private WeChatAppRepository weChatAppRepository;

    private WeChatApp weChatApp;

    public WeChatAppAgg withId(String id) {
        return withIndex(id);
    }

    public WeChatAppAgg withIndex(String index) {
        this.weChatApp = this.weChatAppRepository.queryByIndex(index);
        return this;
    }

    public WeChatApp weChatApp() {
        return this.weChatApp;
    }

}