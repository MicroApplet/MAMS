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

package io.github.microapplet.router;

import io.github.microapplet.handler.IndexHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

/**
 * Index
 * 配置类，通过 URI，找到对应的 Handler
 * 匹配 URI 规则，请求方法
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0.0
 * @since 2024/2/6, &nbsp;&nbsp; <em>version:1.0.0</em>
 */
@Configuration
public class IndexRouterConfiguration {

    /**
     * Route ~ @Controller + @RequestMapping
     * 每个 route 对应一个 handler
     * 每一个 handler 代表一个处理器 ->  @**Mapping
     */
    @Bean
    public RouterFunction<ServerResponse> indexRouter(IndexHandler handler){
        return RouterFunctions.route(RequestPredicates.GET("/index"), handler::index);
    }
    @Bean
    public RouterFunction<ServerResponse> healthRouter(IndexHandler handler){
        return RouterFunctions.route(RequestPredicates.GET("/health"), handler::health);
    }

    @Bean
    public RouterFunction<ServerResponse> mixRouter(IndexHandler handler){
        return RouterFunctions
                .route(RequestPredicates.GET("/index"), handler::index)
                .andRoute(RequestPredicates.GET("/health"), handler::health);
    }
}