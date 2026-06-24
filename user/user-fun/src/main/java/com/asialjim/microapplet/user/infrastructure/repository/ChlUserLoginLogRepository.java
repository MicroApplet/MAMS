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

package com.asialjim.microapplet.user.infrastructure.repository;

import com.asialjim.microapplet.user.infrastructure.repository.datasource.po.ChlUserLoginLogPo;
import com.asialjim.microapplet.user.infrastructure.repository.datasource.service.ChlUserLoginLogMapperService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
public class ChlUserLoginLogRepository {
    @Resource
    private ChlUserLoginLogMapperService chlUserLoginLogMapperService;

    public String lastLoginTime(ChlUserLoginLogPo loginLog) {
        if (Objects.isNull(loginLog))
            return StringUtils.EMPTY;
        if (StringUtils.isAnyBlank(loginLog.getPlatformType(), loginLog.getAppid(), loginLog.getOpenid()))
            return StringUtils.EMPTY;
        LocalDateTime lastLoginLogTime = chlUserLoginLogMapperService.lastLoginTime(loginLog);
        loginLog.setLoginTime(LocalDateTime.now());
        chlUserLoginLogMapperService.save(loginLog);
        if (Objects.nonNull(lastLoginLogTime))
            return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(lastLoginLogTime);

        return StringUtils.EMPTY;
    }
}
