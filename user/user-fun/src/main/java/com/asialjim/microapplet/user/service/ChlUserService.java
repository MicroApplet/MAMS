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

import com.asialjim.microapplet.commons.chl.PlatformType;
import com.asialjim.microapplet.session.Session;
import com.asialjim.microapplet.session.SessionCtx;
import com.asialjim.microapplet.user.entity.vo.ChlUserVo;
import com.asialjim.microapplet.user.entity.vo.UserVo;
import com.asialjim.microapplet.user.entity.web.code.CustomerCode;
import com.asialjim.microapplet.user.infrastructure.repository.ChlUserRepository;
import com.asialjim.microapplet.user.infrastructure.repository.UserRepository;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 渠道用户服务
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/6, &nbsp;&nbsp; <em>version:1.0</em>
 */
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
        if (StringUtils.isBlank(body.getOpenid()))
            CustomerCode.RegisterChlUserParamErr.thr("未指定openid");

        String platformTypeCode = body.getPlatformType();
        String appId = body.getAppId();
        String openid = body.getOpenid();
        // 查询是否已经注册过了
        ChlUserVo target = this.chlUserRepository.queryByPlatformTypeAndAppidAndOpenid(
                platformTypeCode, appId, openid
        );
        // 数据已存在
        // 用户已存在，执行用户数据合并与更新
        if (Objects.nonNull(target))
            return this.updateWhenRegister(body, target);

        PlatformType platformType = PlatformType.of(body.getPlatformType());

        // 定位该渠道该用户是否已经在其他的应用中注册过，如果注册过则需要合并主用户号
        List<ChlUserVo> otherAppUsers = this.chlUserRepository.queryByPlatformTypeAndAppidAndUnionId(platformType.getCode(), body.getAppId(), body.getUnionid());
        AtomicReference<String> useridRef = new AtomicReference<>();
        Set<String> useridSet = otherAppUsers.stream().map(ChlUserVo::getUserId).peek(useridRef::set).collect(Collectors.toSet());
        if (useridSet.size() > 1)
            throw CustomerCode.ExistMoreThan1MainUserId.ex();

        String userid = useridRef.get();
        if (StringUtils.isNotBlank(userid)) {
            // 用户已经注册过,合并用户
            body.setUserId(userid);
        } else {
            // 存在unionid时才创建主用户
            if (StringUtils.isNotBlank(body.getUnionid())) {
                UserVo userVo = this.userRepository.queryByPlatformAndUnionid(platformType.getCode(), body.getPlatformId(), body.getUnionid());
                if (Objects.isNull(userVo)) {
                    // 创建主用户号
                    userVo = new UserVo();
                    userVo.setPlatformType(platformType.getCode());
                    userVo.setPlatformId(body.getPlatformId());
                    userVo.setUnionid(body.getUnionid());
                    userVo.setNickname(platformType.getName() + "用户");
                    userVo = this.userRepository.save(userVo);
                }

                body.setUserId(userVo.getId());
            }
        }

        return this.chlUserRepository.save(body);
    }

    @SuppressWarnings("CommentedOutCode")
    public ChlUserVo updateWhenRegister(ChlUserVo source, ChlUserVo target) {
       /*
        // 以下这些数据不允许在注册的时候更新
        target.setId(source.getId());
        target.setUserId(source.getUserId());
        target.setChlType(source.getChlType());
        target.setAppId(source.getAppId());
        target.setAppType(source.getAppType());
        target.setOpenid(source.getOpenid());
        */

        target.setUnionid(source.getUnionid()); // 可能之前没有绑定开放平台，获取不到unionid，但之后能够获取到了，需要更新
        target.setUserCode(source.getUserCode());// 用户授权码，随时变更
        target.setUserToken(source.getUserToken());// 一般为用户的access_token

        return this.chlUserRepository.updateById(target);
    }


    public ChlUserVo queryById(String id) {
        return this.chlUserRepository.queryById(id);
    }

    public ChlUserVo queryByChlTypeAndOpenid(String chlType, String openid) {
        return this.chlUserRepository.queryByChlTypeAndOpenid(chlType, openid);
    }
}