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

package com.asialjim.microapplet.mams.user.infrastructure.datasource.repository;

import com.asialjim.microapplet.mams.user.infrastructure.datasource.po.IdCardUserPo;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.service.IdCardUserMapperService;
import com.asialjim.microapplet.mams.user.vo.IdCardUserVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 证件用户表数仓
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/22, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
@RequiredArgsConstructor
public class IdCardUserRepository {
    private final IdCardUserMapperService idCardUserMapperService;


    public List<IdCardUserVo> queryByUserid(String userid) {
        List<IdCardUserPo> pos = this.idCardUserMapperService.queryByUserid(userid);
        if (CollectionUtils.isEmpty(pos))
            return Collections.emptyList();
        return pos.stream()
                .map(IdCardUserPo::toVo)
                .filter(Objects::nonNull)
                .toList();
    }
}