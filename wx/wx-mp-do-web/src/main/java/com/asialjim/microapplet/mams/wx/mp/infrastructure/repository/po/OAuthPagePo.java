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

package com.asialjim.microapplet.mams.wx.mp.infrastructure.repository.po;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.net.url.UrlBuilder;
import com.asialjim.microapplet.mams.wx.mp.vo.oauth.OAuthPageVo;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 简单授权网页信息
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/11, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Accessors(chain = true)
@Table("wx_mp_oauth_page_simple")
public class OAuthPagePo implements Serializable {
    @Serial
    private static final long serialVersionUID = -2738536838618249801L;

    /**
     * 业务编号
     */
    @Id(keyType = KeyType.None)
    private String state;

    /**
     * 所属微信应用编号
     */
    private String appid;

    /**
     * 描述信息
     */
    private String description;

    /**
     * 目标链接
     */
    private String url;

    /**
     * 是否显式授权登录
     */
    private Boolean manual;

    /**
     * 有效期至
     */
    private LocalDateTime expiresAt;

    /**
     * 最大允许点击次数
     */
    private Long maxClickTimes;

    /**
     * 创建时间
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(onInsertValue = "now()", onUpdateValue = "now()")
    private LocalDateTime updateTime;

    public static OAuthPagePo voOf(OAuthPageVo vo) {
        OAuthPagePo po = new OAuthPagePo();
        po.setState(vo.getState());
        po.setAppid(vo.getAppid());
        po.setDescription(vo.getDescription());

        UrlBuilder builder = UrlBuilder.of(vo.getUrl());
        Map<String, String> parameters = vo.getParameters();
        if (MapUtil.isNotEmpty(parameters)) {
            parameters.forEach(builder::addQuery);
        }

        po.setUrl(builder.build());
        po.setManual(vo.getManual());
        // 设置有效期
        if (Objects.nonNull(vo.getExpiresAt())) {
            po.setExpiresAt(vo.getExpiresAt());
        }
        // 根据过期时间设置有效期
        else if (Objects.nonNull(vo.getTimeout()) && Objects.nonNull(vo.getTimeunit())) {
            TimeUnit timeUnit = Optional.of(TimeUnit.valueOf(vo.getTimeunit())).orElse(TimeUnit.MINUTES);
            LocalDateTime localDateTime = LocalDateTime.now().plusSeconds(timeUnit.convert(vo.getTimeout(), TimeUnit.MILLISECONDS));
            po.setExpiresAt(localDateTime);
        }
        // 永不过期
        else {
            po.setExpiresAt(LocalDateTime.of(3099, Month.DECEMBER, 31, 0, 0, 0));
        }

        po.setMaxClickTimes(vo.getMaxClickTimes());

        return po;
    }

    public static OAuthPageVo toVo(OAuthPagePo po) {
        OAuthPageVo vo = new OAuthPageVo();
        vo.setState(po.getState());
        vo.setAppid(po.getAppid());
        vo.setDescription(po.getDescription());
        vo.setUrl(po.getUrl());
        vo.setManual(po.getManual());
        vo.setExpiresAt(po.getExpiresAt());
        vo.setMaxClickTimes(po.getMaxClickTimes());

        String oauthUrl;
        return vo;
    }
}