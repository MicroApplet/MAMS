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

package com.asialjim.microapplet.mams.gateway.route;

import com.asialjim.microapplet.mams.gateway.route.RouteConfigProperty;
import com.asialjim.microapplet.mams.gateway.route.RouteNode;

import java.util.List;

/**
 * 路由管理 API 接口
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/12/31, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface RouteApi {

    /**
     * 获取所有路由
     */
    RouteConfigProperty listRoutes();

    /**
     * 添加路由
     */
    void addRoute(RouteNode routeNode);

    /**
     * 更新路由
     */
    void updateRoute(RouteNode routeNode);

    /**
     * 删除路由
     */
    void deleteRoute(String name);

    /**
     * 发布路由配置到 Nacos
     */
    void publishRoutes(RouteConfigProperty config);
}
