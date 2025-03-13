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

package io.github.microapplet.mams.user.service;

import io.github.microapplet.mams.user.model.IdentificationUser;
import io.github.microapplet.mams.user.model.User;
import io.github.microapplet.mams.user.repository.IdentificationUserCache;
import io.github.microapplet.mams.user.repository.IdentificationUserRepository;
import io.github.microapplet.mams.user.session.CurrentUserBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

@Component
public class IdentificationUserCacheImpl implements IdentificationUserCache {
    @Resource
    private IdentificationUserRepository identificationUserRepository;

    @Override
    public void merge(IdentificationUser user) {
        User current = CurrentUserBean.current(null);
        String sessionId = current.getSessionId();
        IdentificationUser exist =  this.identificationUserRepository.cache(sessionId);
        if (Objects.nonNull(exist)){
            user.merge(exist);
        }
        this.identificationUserRepository.cache(sessionId,user);
    }
}