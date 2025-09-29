/*
 *    Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
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

package com.asialjim.microapplet.mams.wx.mp.vo.oauth;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import static com.asialjim.microapplet.mams.wx.mp.cons.WeChatMpOAuthCons.BASE_FORMAT;
import static com.asialjim.microapplet.mams.wx.mp.cons.WeChatMpOAuthCons.USER_FORMAT;


/**
 * 授权网页信息
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/5, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Accessors(chain = true)
public class OAuthPageVo implements Serializable {
    @Serial
    private static final long serialVersionUID = -5410471457319992786L;


    /**
     * 业务编号
     */
    private String state;

    /**
     * 所属微信应用编号
     */
    private String appid;
    private String description;

    /**
     * 目标链接
     */
    private String url;
    /**
     * 参数
     */
    private Map<String, String> parameters;

    /**
     * 是否显式授权登录
     */
    private Boolean manual;

    /**
     * 有效期至
     */
    private LocalDateTime expiresAt;

    /**
     * 有效期
     */
    private Long timeout;

    /**
     * 有效期时间单位
     */
    private String timeunit;

    /**
     * 最大允许点击次数
     */
    private Long maxClickTimes;

    /**
     * 组装授权链接
     *
     * @param handlerUrl {@link String handlerUrl}
     * @return {@link String }
     * @since 2025/9/11
     */
    public String oauthUrl(String handlerUrl) {

        String format;
        if (Boolean.TRUE.equals(this.manual)) {
            format = USER_FORMAT;
        } else {
            format = BASE_FORMAT;
        }
        return String.format(format, this.appid, handlerUrl, this.state);
    }
}