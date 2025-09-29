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
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 授权网页链接创建参数
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/5, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Accessors(chain = true)
public class BuildWeChatOAuthPageVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 5931602449624768677L;

    private String handler;
    private String appid;
    private String url;
    /**
     * 是否显式授权
     */
    private Boolean manual;
    /**
     * 查询参数
     */
    private Map<String, String> parameter;
    private Long timeout;
    private String timeUnit;
    private Long maxClickTimes;

    public Duration duration() {
        if (Objects.isNull(this.timeout))
            return null;
        if (StringUtils.isBlank(this.timeUnit))
            return null;

        TimeUnit unit = Arrays.stream(TimeUnit.values())
                .filter(item -> StringUtils.equalsIgnoreCase(this.timeUnit, item.name()))
                .findAny()
                .orElse(null);

        if (Objects.isNull(unit))
            return null;

        return Duration.of(this.timeout, unit.toChronoUnit());
    }
}