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

import com.asialjim.microapplet.user.entity.vo.UserVo;
import com.asialjim.microapplet.user.entity.web.code.CustomerCode;
import com.asialjim.microapplet.user.infrastructure.repository.datasource.po.UserPo;
import com.asialjim.microapplet.user.infrastructure.repository.datasource.service.UserMapperService;
import io.github.linpeilie.Converter;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.Objects;

/**
 * 主用户数仓
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/6, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
@Component
@AllArgsConstructor
public class UserRepository {
    private final UserMapperService userMapperService;
    private final Converter converter;

    public UserVo queryByPlatformAndUnionid(String platformType,String platformId,String unionid){
        UserPo exist = this.userMapperService.queryByPlatformAndUnionid(platformType,platformId,unionid);
        if (Objects.isNull(exist))
            return null;
         return this.converter.convert(exist, UserVo.class);
    }

    public UserVo save(@Validated @NotNull(message = "用户信息不能为空") UserVo vo) {
        log.info("主用户注册，参数：{}", vo);
        //if (StringUtils.isAnyBlank(vo.getUnionId(), vo.getOpenPlatformId(), vo.getOpenPlatformType()))
        if (StringUtils.isAnyBlank(vo.getUnionid(), vo.getPlatformType()))
            throw CustomerCode.RegisterMainUserParamErr.ex(Collections.singletonList("未指定Unionid,开放平台类型或者开放平台账号"));


        UserPo po = converter.convert(vo, UserPo.class);
        boolean save = this.userMapperService.save(po);
        if (!save)
            throw CustomerCode.RegisterMainUserFailure.ex(Collections.singletonList("主用户信息数据保存失败"));

        // 此处必须转换，因为需要获取主键
        UserVo res = converter.convert(po, UserVo.class);
        log.info("主用户注册成功：{}", res);
        // todo 发布主用户注册成功事件
        return res;
    }
}