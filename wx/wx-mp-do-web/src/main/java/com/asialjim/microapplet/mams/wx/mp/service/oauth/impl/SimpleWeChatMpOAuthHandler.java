/*
 *    Copyright 2014-$year.today <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.asialjim.microapplet.mams.wx.mp.service.oauth.impl;

import cn.hutool.core.net.url.UrlBuilder;
import com.asialjim.microapplet.wechat.official.context.WeChatMpCode;
import com.asialjim.microapplet.mams.wx.mp.infrastructure.WxStateUtil;
import com.asialjim.microapplet.mams.wx.mp.infrastructure.repository.po.OAuthPagePo;
import com.asialjim.microapplet.mams.wx.mp.infrastructure.repository.service.OAuthPageMapperService;
import com.asialjim.microapplet.mams.wx.mp.service.oauth.WeChatMpOAuthHandler;
import com.asialjim.microapplet.mams.wx.mp.vo.oauth.OAuthPageVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 简单授权网页链接处理器
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/5, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
//@Component
public class SimpleWeChatMpOAuthHandler implements WeChatMpOAuthHandler {
    @Resource
    private OAuthPageMapperService oAuthPageMapperService;

    @Override
    public String handler() {
        return "simple";
    }

    @Override
    public String description() {
        return "简单授权网页处理器";
    }

    @Override
    public OAuthPageVo create(OAuthPageVo vo) {
        boolean b = this.oAuthPageMapperService.create(vo);
        log.info("创建授权链接：{} 结果：{}", vo, b);
        String state = vo.getState();

        boolean verify = WxStateUtil.verify(state);
        if (!verify)
            WeChatMpCode.OAuthPageStateIllegal.thr();

        OAuthPagePo po = this.oAuthPageMapperService.pageOf(vo.getAppid(), state);
        return OAuthPagePo.toVo(po);
    }


    @Override
    public URI page(String appid, String state) {
        boolean verify = WxStateUtil.verify(state);
        if (!verify)
            WeChatMpCode.OAuthPageStateIllegal.thr();

        OAuthPagePo po = this.oAuthPageMapperService.pageOf(appid, state);
        String url = po.getUrl();
        return UrlBuilder.of(url).toURI();
    }

    @Override
    public String build(String appid, String url, Map<String, String> parameter, Duration duration, Long maxClickTimes) {
        OAuthPageVo vo = new OAuthPageVo();
        vo.setAppid(appid);
        vo.setUrl(url);
        vo.setParameters(parameter);
        if (Objects.nonNull(duration)) {
            vo.setTimeout(duration.toSeconds());
            vo.setTimeunit(TimeUnit.SECONDS.name());
        }

        vo.setMaxClickTimes(maxClickTimes);
        vo = create(vo);
        return vo.getState();
    }
}