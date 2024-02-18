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

package io.github.microapplet.web.filter;

import io.github.microapplet.web.error.DefaultSuccessResCode;
import io.github.microapplet.web.log.TrackerKeyConfiguration;
import jakarta.annotation.Resource;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static io.github.microapplet.web.error.MamsBizHttpResExceptionAdvice.*;

/**
 * MamsBusinessCodeFilter
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0.0
 * @since 2024/2/6, &nbsp;&nbsp; <em>version:1.0.0</em>
 */
@Component
@Order(Integer.MIN_VALUE)
@WebFilter(urlPatterns = "/**")
public class MamsBusinessCodeFilter implements Filter {
    @Resource
    private TrackerKeyConfiguration trackerKeyConfiguration;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        try {
            // 链路追踪
            MDC.put(this.trackerKeyConfiguration.getTrackerKey(), parseTraceId(httpServletRequest));
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            String code = httpServletResponse.getHeader(CODE_HEADER_NAME);
            if (StringUtils.isNotBlank(code))
                return;
            // 添加默认响应
            httpServletResponse.setHeader(CODE_HEADER_NAME, DefaultSuccessResCode.SUCCESS.getCode());
            httpServletResponse.setHeader(MESSAGE_HEADER_NAME, DefaultSuccessResCode.SUCCESS.getMsg());
            httpServletResponse.setHeader(CN_MESSAGE_HEADER_NAME, DefaultSuccessResCode.SUCCESS.getMsgCn());
        } finally {
            // 清理MDC，防止OOM
            MDC.remove(this.trackerKeyConfiguration.getTrackerKey());
        }
    }

    private String parseTraceId(HttpServletRequest httpServletRequest) {
        List<String> trackerHeaders = this.trackerKeyConfiguration.getTrackerHeaders();
        for (String trackerHeader : trackerHeaders) {
            String header = httpServletRequest.getHeader(trackerHeader);
            if (StringUtils.isNotBlank(header))
                return header;
        }
        return UUID.randomUUID().toString();
    }
}