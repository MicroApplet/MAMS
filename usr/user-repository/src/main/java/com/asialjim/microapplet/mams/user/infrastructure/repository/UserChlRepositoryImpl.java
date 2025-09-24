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

import com.asialjim.microapplet.mams.channel.base.ChlAppType;
import com.asialjim.microapplet.mams.channel.base.ChlType;
import com.asialjim.microapplet.mams.user.infrastructure.repository.datasource.po.UserChlPo;
import com.asialjim.microapplet.mams.user.infrastructure.repository.datasource.service.UserChlMapperService;
import com.asialjim.microapplet.mams.user.pojo.UserChl;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 渠道用户存储
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/11, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class UserChlRepositoryImpl implements UserChlRepository {
    @Resource
    private UserChlMapperService userChlMapperService;

    @Override
    public UserChl queryChlUser(ChlType chlType, String chlAppId, ChlAppType chlAppType, String userCode) {
        UserChlPo userChlPo = this.userChlMapperService.queryChlUserByChlUserIdOfChlAppOnChlType(userCode, chlAppId, chlAppType, chlType);
        return UserChlPo.fromPo(userChlPo);
    }

    @Override
    public String saveAndGetId(UserChl target) {
        UserChlPo po = UserChlPo.toPo(target);
        if (Objects.isNull(po))
            return "";
        this.userChlMapperService.save(po);
        return po.getId();
    }

    @Override
    public List<UserChl> queryByUseridOfApplet(String userid, String appletId) {
        List<UserChlPo> list = this.userChlMapperService.listByUseridOfApplet(userid,appletId);
        return list.stream().map(UserChlPo::fromPo).collect(Collectors.toList());
    }
}