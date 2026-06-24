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

package com.asialjim.microapplet.app.cloud;

import com.asialjim.microapplet.app.code.AppFunctionCons;
import com.asialjim.microapplet.app.api.ApplicationApi;
import com.asialjim.microapplet.app.code.AppCode;
import com.asialjim.microapplet.app.entity.web.AppVo;
import com.asialjim.microapplet.web.client.annotation.HttpExchangeClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;
import java.util.Objects;

@HttpExchange(ApplicationApi.path)
@HttpExchangeClient(AppFunctionCons.serverName)
public interface ApplicationCloud extends ApplicationApi {
    Logger log = LoggerFactory.getLogger(ApplicationCloud.class);

    @GetExchange("/query-by-host")
    AppVo queryByHostAndAppid(@RequestParam String host, @RequestParam String appid);

    @GetExchange("/query-by-platform-type")
    AppVo queryByPlatformTypeAndAppid(@RequestParam String platformType, @RequestParam String appid);

    @GetExchange("/query-by-appid")
    List<AppVo> queryByAppid(@RequestParam String appid);


    default AppVo queryAppByAppidAndHostOrPlatformType(String host,
                                                       String requestChannel,
                                                       String appid,
                                                       String emptyMsg,
                                                       String multiMsg) {
        log.info("开始查询应用信息，host={},requestChannel={},appid={}", host, requestChannel, appid);
        AppVo appVo;
        if (StringUtils.isNotBlank(requestChannel)){
            appVo = queryByPlatformTypeAndAppid(requestChannel, appid);
        } else if (StringUtils.isNotBlank(host)){
            appVo = queryByHostAndAppid(host, appid);
        } else {
            List<AppVo> appList = queryByAppid(appid);
            if (Objects.isNull(appList) || appList.isEmpty())
                throw AppCode.NoSuchAppidErr.exWithData(emptyMsg);
            else if (appList.size() > 1) {
                throw AppCode.SameAppidErr.exWithData(multiMsg);
            } else
                appVo = appList.getFirst();
        }

        return appVo;
    }
}