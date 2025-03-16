/*
 *  Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.asialjim.microapplet.mams.user.repository;

import com.asialjim.microapplet.mams.user.model.IdentificationUser;
import com.asialjim.microapplet.mams.user.model.User;
import com.asialjim.microapplet.mams.user.po.IdentificationUserPo;
import com.asialjim.microapplet.mams.user.session.CurrentUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 用户证件缓存
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/13, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class IdentificationUserCacheImpl implements IdentificationUserCache {
    private final Map<String, IdentificationUserPo> cache = new HashMap<>();
    private final ScheduledExecutorService scheduledExecutorService;

    public IdentificationUserCacheImpl(@Autowired(required = false) ScheduledExecutorService scheduledExecutorService) {
        this.scheduledExecutorService = Optional.ofNullable(scheduledExecutorService).orElseGet(() -> Executors.newScheduledThreadPool(1));
    }

    @Override
    public void merge(IdentificationUser user) {
        if (Objects.isNull(user))
            return;

        User current = CurrentUserBean.current(null);
        String sessionId = current.getSessionId();
        this.scheduledExecutorService.schedule(() -> cache.remove(sessionId),1, TimeUnit.DAYS);

        IdentificationUserPo po = IdentificationUserPo.toPo(user);
        if (Objects.isNull(po))
            return;
        IdentificationUserPo exist = this.cache.get(sessionId);
        if (Objects.nonNull(exist))
            po.merge(exist);

        IdentificationUser another = IdentificationUserPo.fromPo(po);
        if (Objects.nonNull(another))
            user.merge(another);

        cache.put(sessionId, po);
    }
}