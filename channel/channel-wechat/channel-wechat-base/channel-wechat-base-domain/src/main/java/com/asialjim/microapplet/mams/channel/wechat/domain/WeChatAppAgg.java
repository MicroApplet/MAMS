package com.asialjim.microapplet.mams.channel.wechat.domain;

import com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.accesstokken.WeChatAccessTokenCache;
import com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.accesstokken.WeChatAccessTokenRemoting;
import com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.meta.accesstoken.WeChatAccessTokenRes;
import com.asialjim.microapplet.mams.channel.wechat.infrastructure.repository.WeChatAppRepository;
import com.asialjim.microapplet.mams.channel.wechat.pojo.WeChatApp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    public String accessToken() {
        WeChatApp app = this.weChatApp();
        String appid = app.getAppid();
        String secret = app.getAppSecret();
        String accessToken = accessTokenCache.get(appid);
        if (StringUtils.isNotBlank(accessToken))
            return accessToken;

        // 获取锁
        String tag;
        String lockTag;
        do {
            tag = UUID.randomUUID().toString();
            lockTag = accessTokenCache.getLockTag(appid, tag);
            if (StringUtils.equals(tag, lockTag))
                break;
            //ThreadUtils.sleep(Duration.ofMillis(500));
        } while (!StringUtils.equals(tag, lockTag));

        // 执行网络查询
        WeChatAccessTokenRes weChatAccessTokenRes = accessTokenRemoting.accessToken(appid, secret);
        String token;
        int index = 1;
        do {
            token = Optional.ofNullable(weChatAccessTokenRes)
                    .map(WeChatAccessTokenRes::getAccess_token)
                    .orElse(StringUtils.EMPTY);
            if (StringUtils.isNotBlank(token)) {
                accessTokenCache.set(appid, token);
                accessTokenCache.cached(appid);
            }
            index++;
        } while (StringUtils.isBlank(token) && index <= 3);

        return token;
    }

    public void refreshAccessToken() {
        WeChatApp app = this.weChatApp();
        String appid = app.getAppid();
        accessTokenCache.cached(appid);
    }
}