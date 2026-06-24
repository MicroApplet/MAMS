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

import com.asialjim.microapplet.user.code.IdCardUserResCode;
import com.asialjim.microapplet.user.entity.vo.IdCardUserVo;
import com.asialjim.microapplet.user.infrastructure.repository.datasource.po.IdCardUserPo;
import com.asialjim.microapplet.user.infrastructure.repository.datasource.service.IdCardUserMapperService;
import io.github.linpeilie.Converter;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class IdCardUserRepository {
    private final IdCardUserMapperService idCardUserMapperService;
    private final Converter converter;


    public IdCardUserVo queryByPlatformTypeAndAppidAndOpenidAndIdType(String platformType, String appId, String openid, String idType) {
        IdCardUserPo po = this.idCardUserMapperService.queryByPlatformTypeAndAppidAndOpenidAndIdType(platformType, appId, openid, idType);
        if (Objects.isNull(po)) return null;
        return this.converter.convert(po, IdCardUserVo.class);
    }

    public List<IdCardUserVo> queryByPlatformTypeAndAppidAndOpenid(String platformType, String appid, String openid) {
        List<IdCardUserPo> pos = this.idCardUserMapperService.queryByPlatformTypeAndAppidAndOpenid(platformType,appid,openid);
        if (CollectionUtils.isEmpty(pos)) return Collections.emptyList();

        return pos.stream().filter(Objects::nonNull).map(item -> converter.convert(item, IdCardUserVo.class)).toList();
    }



    public IdCardUserVo linkIdCard(IdCardUserVo body) {
        IdCardUserPo po = this.converter.convert(body, IdCardUserPo.class);
        boolean save = this.idCardUserMapperService.save(po);
        if (save) {
            String platformType = body.getPlatformType();
            String appId = body.getAppId();
            String openid = body.getOpenid();
            String idType = body.getIdType();
            return this.queryByPlatformTypeAndAppidAndOpenidAndIdType(platformType, appId, openid, idType);
        }

        throw IdCardUserResCode.AddIdCardUserFailed.ex("证件类型", body.getIdType());
    }

}
