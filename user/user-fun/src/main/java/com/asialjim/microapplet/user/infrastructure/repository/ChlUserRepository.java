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

import com.asialjim.microapplet.commons.chl.PlatformType;
import com.asialjim.microapplet.user.entity.vo.ChlUserVo;
import com.asialjim.microapplet.user.entity.web.code.CustomerCode;
import com.asialjim.microapplet.user.infrastructure.repository.datasource.po.ChlUserPo;
import com.asialjim.microapplet.user.infrastructure.repository.datasource.service.ChlUserMapperService;
import io.github.linpeilie.Converter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 渠道用户数仓
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/6, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
@Component
@AllArgsConstructor
public class ChlUserRepository {
    private final ChlUserMapperService chlUserMapperService;
    private final Converter converter;

    public Optional<ChlUserVo> queryByIdOpt(String id) {
        ChlUserPo po = this.chlUserMapperService.queryById(id);
        return Optional.ofNullable(po).map(item -> this.converter.convert(item, ChlUserVo.class));
    }

    @Transactional
    public ChlUserVo updateById(ChlUserVo body) {
        if (Objects.isNull(body))
            return null;

        ChlUserPo source = converter.convert(body, ChlUserPo.class);
        boolean b = this.chlUserMapperService.updateById(source);
        if (b) {
            // todo 发布渠道用户信息更新成功事件
            log.info("更新渠道用户信息成功，用户ID：{}", body.getId());
        } else {
            log.info("更新渠道用户信息失败，用户ID：{}", body.getId());
        }
        return queryByIdOpt(body.getId()).orElse(null);
    }

    public List<ChlUserVo> queryByPlatformTypeAndAppidAndUnionId(String chlType, String appId, String unionid) {
        if (StringUtils.isAnyBlank(chlType, appId, unionid))
            return List.of();

        List<ChlUserPo> list = this.chlUserMapperService.queryByPlatformTypeAndAppidAndUnionId(PlatformType.of(chlType).getCode(), appId, unionid);
        return list.stream()
                .filter(Objects::nonNull)
                .map(item -> converter.convert(item, ChlUserVo.class))
                .toList();
    }


    public ChlUserVo save(ChlUserVo body) {
        if (Objects.isNull(body))
            return null;

        ChlUserPo po = converter.convert(body, ChlUserPo.class);
        if (ChlUserPo.cannotSave(po))
            throw CustomerCode.RegisterChlUserParamErr.ex(List.of("缺少以下参数之一或全部", "开放渠道类型", "应用编号", "openid"));

        String id = po.getId();
        if (StringUtils.isNotBlank(id)){
            ChlUserPo exist = this.chlUserMapperService.queryById(id);
            if (Objects.nonNull(exist)) {
                exist.update(po);
                this.chlUserMapperService.updateById(exist);
                return converter.convert(exist, ChlUserVo.class);
            }
        }

        boolean save = this.chlUserMapperService.save(po);
        if (!save)
            throw CustomerCode.RegisterChlUserFailure.ex(Collections.singletonList("数据保存失败"));


        // 此处必须要转换，不能直接返回body，因为需要返回主键
        ChlUserVo convert = converter.convert(po, ChlUserVo.class);
        log.info("成功保存渠道用户信息:{}", convert);
        // TODO 发送事件
        return convert;
    }

    public ChlUserVo queryById(String id) {
        ChlUserPo chlUserPo = this.chlUserMapperService.queryById(id);
        return Optional.ofNullable(chlUserPo)
                .map(item -> converter.convert(item, ChlUserVo.class))
                .orElse(null);
    }

    public ChlUserVo queryByPlatformTypeAndAppidAndOpenid(String platformType, String appid, String openid) {
        ChlUserPo chlUserPo = this.chlUserMapperService.queryByPlatformTypeAndAppidAndOpenid(platformType,appid,openid);
        return Optional.ofNullable(chlUserPo)
                .map(item -> converter.convert(item, ChlUserVo.class))
                .orElse(null);
    }

    public ChlUserVo queryByChlTypeAndOpenid(String chlType, String openid) {
        ChlUserPo chlUserPo = this.chlUserMapperService.queryByChlTypeAndOpenid(chlType,openid);
        return Optional.ofNullable(chlUserPo)
                .map(item -> converter.convert(item, ChlUserVo.class))
                .orElse(null);
    }
}