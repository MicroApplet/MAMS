/*
 * Copyright 2014-2024 <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.microapplet.web.log;

import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 基于日志的链路追踪名配置
 * Key Configuration for Tracker by Log
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0.0
 * @since 2024/2/18, &nbsp;&nbsp; <em>version:1.0.0</em>
 */
@ToString
@Configuration
@ConfigurationProperties(value = "mams.log")
public class TrackerKeyConfiguration {

    /**
     * 链路追踪名， 作用于 MDC.put(this.trackerKey, "aaa"), 默认值为：trace_id
     */
    private String trackerKey;

    /**
     * 基于 http request 的请求头，配置此属性后，系统将从请求头中获取响应的值，作为链路追踪名称, 默认值为 trace_id
     */
    private List<String> trackerHeaders;

    public String getTrackerKey() {
        return StringUtils.isNotBlank(trackerKey) ? trackerKey : "trace_id";
    }

    public void setTrackerKey(String trackerKey) {
        this.trackerKey = StringUtils.isNotBlank(trackerKey) ? trackerKey : "trace_id";
    }

    public List<String> getTrackerHeaders() {
        if (CollectionUtils.isNotEmpty(this.trackerHeaders))
            return trackerHeaders;
        return Collections.singletonList("trace_id");
    }

    public void setTrackerHeaders(List<String> trackerHeaders) {
        this.trackerHeaders
                = CollectionUtils.isNotEmpty(trackerHeaders)
                ? trackerHeaders.stream().filter(StringUtils::isNotBlank).collect(Collectors.toList())
                : Collections.singletonList("trace_id");
    }
}