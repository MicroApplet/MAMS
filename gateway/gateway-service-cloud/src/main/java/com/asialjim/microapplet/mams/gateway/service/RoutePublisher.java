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

import com.asialjim.microapplet.common.utils.JsonUtil;
import com.asialjim.microapplet.mams.gateway.route.RouteConfigProperty;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 路由发布器 — 将路由配置推送到 Nacos
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/12/31, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class RoutePublisher {
    private static final Logger log = LoggerFactory.getLogger(RoutePublisher.class);
    private static final String DATA_ID = "route.yaml";
    private static final JsonUtil<YAMLMapper, YAMLMapper.Builder> yamlUtil = JsonUtil.instance(YAMLMapper.builder());

    @Value("${spring.cloud.nacos.discovery.group}")
    private String group;

    private final com.alibaba.cloud.nacos.NacosConfigManager nacosConfigManager;

    public RoutePublisher(com.alibaba.cloud.nacos.NacosConfigManager nacosConfigManager) {
        this.nacosConfigManager = nacosConfigManager;
    }

    /**
     * 发布路由配置到 Nacos
     */
    public void publishToNacos(RouteConfigProperty config) {
        try {
            String yamlContent = yamlUtil.writeValueAsString(new GatewayRouteWrapper(config));
            nacosConfigManager.getConfigService().publishConfig(DATA_ID, group, yamlContent);
            log.info("路由配置已发布到 Nacos: dataId={}, group={}, routes={}",
                    DATA_ID, group, config.getRoutes().size());
        } catch (Exception e) {
            throw new RuntimeException("发布路由配置到 Nacos 失败", e);
        }
    }

    /**
     * 从 Nacos 加载路由配置
     */
    public RouteConfigProperty loadFromNacos() {
        try {
            String configContent = nacosConfigManager.getConfigService().getConfig(DATA_ID, group, 5000);
            if (configContent == null || configContent.isEmpty()) {
                log.warn("Nacos 中未找到路由配置 dataId={}, group={}", DATA_ID, group);
                return new RouteConfigProperty();
            }

            GatewayRouteWrapper wrapper = yamlUtil.toBean(configContent, GatewayRouteWrapper.class);
            if (wrapper != null && wrapper.getGateway() != null) {
                log.info("从 Nacos 加载路由配置: {} 条", wrapper.getGateway().getRoutes().size());
                return wrapper.getGateway();
            }
            return new RouteConfigProperty();
        } catch (Exception e) {
            log.error("从 Nacos 加载路由配置失败: {}", e.getMessage(), e);
            return new RouteConfigProperty();
        }
    }

    private record GatewayRouteWrapper(RouteConfigProperty gateway) {}
}
