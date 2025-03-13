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

package io.github.microapplet.mams.user.repository;

import io.github.microapplet.mams.user.mapper_service.IdentificationUserMapperService;
import io.github.microapplet.mams.user.model.IdentificationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 证件用户数据仓库
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/13, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class IdentificationUserRepositoryImpl implements IdentificationUserRepository {
    private final Map<String, IdentificationUser> map = new HashMap<>();
    private final IdentificationUserMapperService identificationUserMapperService;
    private final ScheduledExecutorService scheduledExecutorService;

    public IdentificationUserRepositoryImpl(
            @Autowired IdentificationUserMapperService identificationUserMapperService,
            @Autowired(required = false) ScheduledExecutorService scheduledExecutorService) {
        this.identificationUserMapperService = identificationUserMapperService;
        this.scheduledExecutorService = Optional.ofNullable(scheduledExecutorService).orElseGet(() -> Executors.newScheduledThreadPool(1));
    }


    @Override
    public IdentificationUser cache(String sessionId) {
        this.scheduledExecutorService.schedule(() -> map.remove(sessionId), 3, TimeUnit.HOURS);
        return map.get(sessionId);
    }

    @Override
    public void cache(String sessionId, IdentificationUser user) {
        map.put(sessionId, user);
    }
}