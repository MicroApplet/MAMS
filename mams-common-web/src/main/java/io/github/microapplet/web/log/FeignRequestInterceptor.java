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
import jakarta.annotation.Resource;
import org.slf4j.MDC;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Feign Request Interceptor
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0.0
 * @since 2024/2/18, &nbsp;&nbsp; <em>version:1.0.0</em>
 */
@Component
public class FeignRequestInterceptor implements RequestInterceptor{
    @Resource private TrackerKeyConfiguration trackerKeyConfiguration;
    @Override
    public void apply(RequestTemplate template) {
        String requestId = MDC.get(this.trackerKeyConfiguration.getTrackerKey());
        for (String trackerHeader : this.trackerKeyConfiguration.getTrackerHeaders()) {
            template.header(trackerHeader,requestId);
        }

        template.header("X-Request-Channel","FEIGN");
    }
}