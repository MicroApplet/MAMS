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

package com.asialjim.microapplet.user.service;

import com.asialjim.microapplet.commons.chl.PlatformAppType;
import com.asialjim.microapplet.commons.chl.PlatformType;
import com.asialjim.microapplet.session.Session;
import com.asialjim.microapplet.session.SessionCtx;
import com.asialjim.microapplet.user.entity.vo.ChlUserVo;
import com.asialjim.microapplet.user.entity.vo.UserVo;
import com.asialjim.microapplet.user.entity.web.code.CustomerCode;
import com.asialjim.microapplet.user.infrastructure.repository.ChlUserRepository;
import com.asialjim.microapplet.user.infrastructure.repository.UserRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 渠道用户服务
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/6, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
@Service
public class ChlUserService {
    @Resource
    private ChlUserRepository chlUserRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private SessionCtx sessionCtx;


    public ChlUserVo current() {
        Session userSession = this.sessionCtx.currentSession();

        if (Objects.isNull(userSession))
            return null;

        String platformType = userSession.platformAppType().getPlatformType().getCode();

        String appid = userSession.getAppid();
        String openid = userSession.getOpenid();
        return this.chlUserRepository.queryByPlatformTypeAndAppidAndOpenid(platformType, appid, openid);
    }

    /**
     * 注册渠道用户，调用时机为：用户在渠道应用登录后
     *
     * @param body {@link ChlUserVo body}
     * @return {@link ChlUserVo }
     * @since {@code }
     */
    public ChlUserVo register(ChlUserVo body) {
        if (Objects.isNull(body))
            return body;
        if (StringUtils.isBlank(body.getOpenid()))
            CustomerCode.RegisterChlUserParamErr.thr("未指定openid");

        PlatformAppType platformAppType = PlatformAppType.codeOf(body.getPlatformType(), body.getAppType());
        PlatformType platformType = platformAppType.getPlatformType();

        String appid = body.getAppId();
        String openid = body.getOpenid();
        String unionid = StringUtils.isNotBlank(body.getUnionid()) ? body.getUnionid() : openid;
        // 查询是否已经注册过了
        ChlUserVo target = this.chlUserRepository.queryByPlatformTypeAndAppidAndOpenid(platformType.getCode(), appid, openid);
        log.info("{} 平台 {} 应用 {} 用户 已保存数据信息：{}", platformType.getCode(), appid, openid, target);
        if (Objects.isNull(target))
            target = body;

        target.setUnionid(body.getUnionid()); // 可能之前没有绑定开放平台，获取不到unionid，但之后能够获取到了，需要更新
        target.setUserCode(body.getUserCode());// 用户授权码，随时变更
        target.setUserToken(body.getUserToken());// 一般为用户的access_token

        // 主用户编号
        String userId = target.getUserId();
        // 主用户
        UserVo userVo = StringUtils.isNotBlank(userId) ? this.userRepository.getById(userId) : null;
        log.info("{} 平台 {} 应用 {} 用户 主用户编号：{}， 主用户信息：{}", platformType.getCode(), appid, openid, userId, userVo);

        if (Objects.isNull(userVo)) {
            userVo = new UserVo();
            userVo.setPlatformType(platformType.getCode());
            userVo.setPlatformId(body.getPlatformId());
            userVo.setUnionid(unionid);
            userVo.setNickname(platformType.getName() + "用户");
            userVo = this.userRepository.save(userVo);

            log.info("注册新的主用户信息：{}", userVo);
            target.setUserId(userVo.getId());
            return this.chlUserRepository.save(target);
        }

        // 主用户信息 与 渠道用户平台信息一致
        if (Strings.CS.equals(userVo.getUnionid(), unionid)
                && Strings.CS.equals(userVo.getPlatformId(), body.getPlatformId())
                && Strings.CS.equals(userVo.getPlatformType(), platformType.getCode())) {

            log.info("主用户信息 与 渠道用户平台信息一致");
            return target;
        }

        // 主用户信息 与 渠道用户平台不一致
        UserVo another = this.userRepository.queryByPlatformAndUnionid(platformType.getCode(), target.getPlatformId(), unionid);
        log.info("{} 平台 {} platform-id: {} 用户 主用户信息：{}",
                platformType.getCode(), target.getPlatformId(), unionid, another);

        if (Objects.nonNull(another)) {
            log.info("主用户信息不一致，切换主用户到：{}", userVo.getId());
            // 切换主用户
            target.setUserId(userVo.getId());
            return this.chlUserRepository.save(target);
        } else {
            userVo.setPlatformType(platformType.getCode());
            userVo.setPlatformId(target.getPlatformId());
            userVo.setUnionid(unionid);
            userVo.setNickname(platformType.getName() + "用户");
            this.userRepository.updateById(userVo);
            log.info("主用户信息不一致，修改主用户：{}", userVo);
            return this.chlUserRepository.save(target);
        }
    }

    public ChlUserVo queryById(String id) {
        return this.chlUserRepository.queryById(id);
    }

    public ChlUserVo queryByChlTypeAndOpenid(String chlType, String openid) {
        return this.chlUserRepository.queryByChlTypeAndOpenid(chlType, openid);
    }
}