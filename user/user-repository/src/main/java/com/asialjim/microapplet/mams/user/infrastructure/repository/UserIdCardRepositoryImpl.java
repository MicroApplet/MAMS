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

package com.asialjim.microapplet.mams.user.infrastructure.repository;

import com.asialjim.microapplet.common.human.IdCardType;
import com.asialjim.microapplet.mams.user.domain.agg.SessionUser;
import com.asialjim.microapplet.mams.user.infrastructure.adapter.CurrentUserAdapter;
import com.asialjim.microapplet.mams.user.infrastructure.cache.UserIdCardCache;
import com.asialjim.microapplet.mams.user.infrastructure.repository.datasource.po.UserIdCardPo;
import com.asialjim.microapplet.mams.user.infrastructure.repository.datasource.service.UserIdCardMapperService;
import com.asialjim.microapplet.mams.user.pojo.UserIdCard;
import com.asialjim.microapplet.mams.user.res.UserResCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 用户证件存储
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/11, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
@Component
public class UserIdCardRepositoryImpl implements UserIdCardRepository {
    @Resource
    private UserIdCardMapperService userIdCardMapperService;
    @Resource
    private UserIdCardCache userIdCardCache;

    @Override
    public void mergeAndSave(UserIdCard user) {
        SessionUser session = CurrentUserAdapter.current(null, UserResCode.UserNotLogin::bizException);
        String sessionId = session.getId();
        UserIdCard exist = this.userIdCardCache.cachedBySessionId(sessionId);
        if (Objects.isNull(exist))
            exist = user;
        else {
            exist.merge(user);
            this.userIdCardCache.cache(sessionId,exist);
        }

        if (exist.idCardSaveAvailable()) {
            UserIdCardPo po = UserIdCardPo.toPo(exist);
            boolean save = this.userIdCardMapperService.save(po);
            log.info("用户证件信息：{} 保存结果：{}", user, save);
        }
    }

    @Override
    public UserIdCard queryByIdNoOfTypeForApplet(String idNo, IdCardType idCardType, String appletId) {
        UserIdCardPo po = this.userIdCardMapperService.queryByIdNoOfTypeForApplet(idNo, idCardType, appletId);
        return UserIdCardPo.fromPo(po);
    }

    @Override
    public UserIdCard queryByUseridOfIdTypeForApplet(String id, IdCardType idCardType, String appletId) {
        UserIdCardPo po = this.userIdCardMapperService.queryByIdOfTypeForApplet(id, idCardType, appletId);
        return UserIdCardPo.fromPo(po);
    }
}