/*
 * Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
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

package io.github.microapplet.mams.user.config;

import io.github.microapplet.web.mvc.config.GlobalResponseAspect;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.SpringDocConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * WebMVC 下，将 HTTP Header 存储到 Reactor.context 中
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/4, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class SkipParseRes2ResultFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(SkipParseRes2ResultFilter.class);
    private ServerProperties serverProperties;
    private SpringDocConfigProperties springDocConfigProperties;

    @Autowired(required = false)
    public void setServerProperties(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @Autowired(required = false)
    public void setSpringDocConfigProperties(SpringDocConfigProperties springDocConfigProperties) {
        this.springDocConfigProperties = springDocConfigProperties;
    }

    private String contextPath() {
        return Optional.ofNullable(this.serverProperties)
                .map(ServerProperties::getServlet)
                .map(ServerProperties.Servlet::getContextPath)
                .orElse(StringUtils.EMPTY);
    }

    private String docPathRoot() {
        return Optional.ofNullable(this.springDocConfigProperties)
                .map(SpringDocConfigProperties::getApiDocs)
                .map(SpringDocConfigProperties.ApiDocs::getPath)
                .orElse(StringUtils.EMPTY);
    }

    private String rootDocPath() {
        return contextPath() + docPathRoot();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String asciiString = httpRequest.getRequestURI();
        String rootDocPath = rootDocPath();
        boolean skip = StringUtils.startsWith(asciiString, rootDocPath);
        if (log.isDebugEnabled())
            log.debug("{}   ==>  {}  == {}", rootDocPath, asciiString, skip);

        GlobalResponseAspect.skip(httpRequest, skip, asciiString);
        filterChain.doFilter(httpRequest, httpResponse);
        GlobalResponseAspect.clean(asciiString);
    }
}