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

package com.asialjim.microapplet.mams.gateway.web;

import com.asialjim.microapplet.mams.gateway.service.RoutePublisher;
import com.asialjim.microapplet.mams.gateway.service.RouteService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

/**
 * 启动时从 Nacos 加载路由配置
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/12/31, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Configuration
public class RouteBootstrapConfig {

    private final RouteService routeService;
    private final RoutePublisher routePublisher;

    public RouteBootstrapConfig(RouteService routeService, RoutePublisher routePublisher) {
        this.routeService = routeService;
        this.routePublisher = routePublisher;
    }

    @PostConstruct
    public void init() {
        routeService.loadRoutes(routePublisher.loadFromNacos());
    }
}
