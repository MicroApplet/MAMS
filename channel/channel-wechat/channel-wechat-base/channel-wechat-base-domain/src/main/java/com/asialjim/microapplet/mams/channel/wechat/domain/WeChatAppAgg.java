package com.asialjim.microapplet.mams.channel.wechat.domain;

import com.asialjim.microapplet.mams.channel.base.ChlAppType;
import com.asialjim.microapplet.mams.channel.wechat.infrastructure.repository.WeChatAppRepository;
import com.asialjim.microapplet.mams.channel.wechat.pojo.WeChatApp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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
    private transient WeChatApp weChatApp;
    @Resource
    private WeChatAppRepository weChatAppRepository;

    public WeChatApp weChatApp(){
        return this.weChatApp;
    }

    public WeChatAppAgg withId(String id) {
        this.weChatApp = this.weChatAppRepository.queryById(id);
        return this;
    }

    public WeChatAppAgg withAppidAndType(String appid, ChlAppType appType) {
        this.weChatApp = this.weChatAppRepository.queryByAppidAndType(appid, appType);
        return this;
    }

    public WeChatAppAgg withSubjectIdAndType(String subjectId, ChlAppType appType) {
        this.weChatApp = this.weChatAppRepository.queryBySubjectIdAndType(subjectId, appType);
        return this;
    }

    public String accessToken(){
        // TODO 获取微信API令牌
        return StringUtils.EMPTY;
    }

    public WeChatAppAgg refreshAccessToken(){
        // TODO 刷新微信API令牌
        return this;
    }
}