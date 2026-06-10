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

package com.asialjim.microapplet.mams.gateway.service;

import com.asialjim.microapplet.mams.gateway.route.RouteApi;
import com.asialjim.microapplet.mams.gateway.route.RouteConfigProperty;
import com.asialjim.microapplet.mams.gateway.route.RouteNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 路由管理服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/12/31, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Service
public class RouteService implements RouteApi {
    private static final Logger log = LoggerFactory.getLogger(RouteService.class);
    private final List<RouteNode> routes = new CopyOnWriteArrayList<>();
    private final RoutePublisher routePublisher;

    public RouteService(RoutePublisher routePublisher) {
        this.routePublisher = routePublisher;
    }

    @Override
    public RouteConfigProperty listRoutes() {
        RouteConfigProperty config = new RouteConfigProperty();
        config.setRoutes(new ArrayList<>(this.routes));
        return config;
    }

    @Override
    public void addRoute(RouteNode routeNode) {
        Objects.requireNonNull(routeNode, "路由节点不能为空");
        Objects.requireNonNull(routeNode.getName(), "路由名称不能为空");

        // 检查是否已存在同名路由
        for (RouteNode existing : this.routes) {
            if (Objects.equals(existing.getName(), routeNode.getName())) {
                throw new IllegalArgumentException("路由 '" + routeNode.getName() + "' 已存在");
            }
        }

        this.routes.add(routeNode);
        log.info("添加路由: {}", routeNode.getName());
        publishCurrentRoutes();
    }

    @Override
    public void updateRoute(RouteNode routeNode) {
        Objects.requireNonNull(routeNode, "路由节点不能为空");
        Objects.requireNonNull(routeNode.getName(), "路由名称不能为空");

        boolean removed = this.routes.removeIf(r -> Objects.equals(r.getName(), routeNode.getName()));
        if (!removed) {
            throw new IllegalArgumentException("路由 '" + routeNode.getName() + "' 不存在");
        }

        this.routes.add(routeNode);
        log.info("更新路由: {}", routeNode.getName());
        publishCurrentRoutes();
    }

    @Override
    public void deleteRoute(String name) {
        Objects.requireNonNull(name, "路由名称不能为空");

        boolean removed = this.routes.removeIf(r -> Objects.equals(r.getName(), name));
        if (!removed) {
            throw new IllegalArgumentException("路由 '" + name + "' 不存在");
        }

        log.info("删除路由: {}", name);
        publishCurrentRoutes();
    }

    @Override
    public void publishRoutes(RouteConfigProperty config) {
        Objects.requireNonNull(config, "路由配置不能为空");

        this.routes.clear();
        if (config.getRoutes() != null) {
            this.routes.addAll(config.getRoutes());
        }

        log.info("全量替换路由，共 {} 条", this.routes.size());
        publishCurrentRoutes();
    }

    /**
     * 将当前路由发布到 Nacos
     */
    private void publishCurrentRoutes() {
        try {
            RouteConfigProperty config = new RouteConfigProperty();
            config.setRoutes(new ArrayList<>(this.routes));
            routePublisher.publishToNacos(config);
        } catch (Exception e) {
            log.error("发布路由到 Nacos 失败: {}", e.getMessage(), e);
        }
    }

    /**
     * 初始化加载已有路由
     */
    public void loadRoutes(RouteConfigProperty config) {
        if (config == null || config.getRoutes() == null) {
            return;
        }
        this.routes.clear();
        this.routes.addAll(config.getRoutes());
        log.info("已加载 {} 条路由", this.routes.size());
    }
}
