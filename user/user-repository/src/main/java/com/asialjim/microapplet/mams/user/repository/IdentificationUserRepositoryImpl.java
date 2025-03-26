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

import com.asialjim.microapplet.mams.user.mapper_service.IdentificationUserMapperService;
import com.asialjim.microapplet.mams.user.model.IdentificationUser;
import com.asialjim.microapplet.mams.user.po.IdentificationUserPo;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 证件用户数据仓库
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/13, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
@AllArgsConstructor
public class IdentificationUserRepositoryImpl implements IdentificationUserRepository {
    private final IdentificationUserMapperService identificationUserMapperService;

    @Override
    public void save(IdentificationUser user) {
        IdentificationUserPo po = IdentificationUserPo.toPo(user);
        this.identificationUserMapperService.save(po);
    }

    @Override
    public List<IdentificationUser> queryByUserid(String userId) {
        List<IdentificationUserPo> po = this.identificationUserMapperService.queryByUserid(userId);
        if (CollectionUtils.isEmpty(po))
            return Collections.emptyList();
        return po.stream().map(IdentificationUserPo::fromPo).collect(Collectors.toList());
    }

    @Override
    public IdentificationUser queryByUseridAndIdType(String userId, String idType) {
        IdentificationUserPo po = this.identificationUserMapperService.queryByUseridAndIdType(userId,idType);
        return IdentificationUserPo.fromPo(po);
    }

    @Override
    public void updateById(IdentificationUser user) {
        IdentificationUserPo po = IdentificationUserPo.toPo(user);
        this.identificationUserMapperService.updateById(po);
    }
}