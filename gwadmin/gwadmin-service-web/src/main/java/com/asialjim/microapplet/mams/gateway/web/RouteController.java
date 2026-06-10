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

import com.asialjim.microapplet.mams.gateway.route.RouteApi;
import com.asialjim.microapplet.mams.gateway.route.RouteConfigProperty;
import com.asialjim.microapplet.mams.gateway.route.RouteNode;
import org.springframework.web.bind.annotation.*;

/**
 * 路由管理 REST API
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/12/31, &nbsp;&nbsp; <em>version:1.0</em>
 */
@RestController
@RequestMapping("/api/admin/gateway/route")
public class RouteController {

    private final RouteApi routeApi;

    public RouteController(RouteApi routeApi) {
        this.routeApi = routeApi;
    }

    /** 获取所有路由 */
    @GetMapping
    public RouteConfigProperty listRoutes() {
        return routeApi.listRoutes();
    }

    /** 添加路由 */
    @PostMapping
    public void addRoute(@RequestBody RouteNode routeNode) {
        routeApi.addRoute(routeNode);
    }

    /** 更新路由 */
    @PutMapping
    public void updateRoute(@RequestBody RouteNode routeNode) {
        routeApi.updateRoute(routeNode);
    }

    /** 删除路由 */
    @DeleteMapping("/{name}")
    public void deleteRoute(@PathVariable String name) {
        routeApi.deleteRoute(name);
    }

    /** 全量发布路由 */
    @PostMapping("/publish")
    public void publishRoutes(@RequestBody RouteConfigProperty config) {
        routeApi.publishRoutes(config);
    }
}
