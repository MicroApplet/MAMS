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

package com.asialjim.microapplet.mams.user.service.session;

import com.asialjim.microapplet.common.context.Res;
import com.asialjim.microapplet.common.security.MamsSession;
import com.asialjim.microapplet.commons.security.Role;
import com.asialjim.microapplet.mams.app.api.AppApi;
import com.asialjim.microapplet.mams.app.api.ChlAppApi;
import com.asialjim.microapplet.mams.app.context.ChlRs;
import com.asialjim.microapplet.mams.app.vo.AppVo;
import com.asialjim.microapplet.mams.app.vo.ChlAppVo;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.po.ChlUserPo;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.po.UserPo;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.repository.ChlUserRepository;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.repository.UserRepository;
import com.asialjim.microapplet.mams.user.valid.UserLoginGroup;
import com.asialjim.microapplet.mams.user.vo.ChlUserVo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 用户会话服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/22, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserSessionService {
    private final ChlUserRepository chlUserRepository;
    private final UserRepository userRepository;
    private final ChlAppApi chlAppApi;
    private final AppApi appApi;

    /**
     * 用户登录
     *
     * @param chlUser {@link ChlUserVo vo}
     * @return {@link MamsSession }
     * @since 2025/9/22
     */
    public MamsSession login(@Validated(UserLoginGroup.class)
                             @NotNull(message = "USER:AUTH:FAILURE|登录参数不能为空")
                             @Valid ChlUserVo chlUser) {

        ChlAppVo chlAppVo = this.chlAppApi.queryByAppidAndChlAndChlAppid(chlUser.getAppid(), chlUser.getChlType(), chlUser.getChlAppId());
        if (Objects.isNull(chlAppVo))
            ChlRs.ChlTypeHasNoAppType.thr();

        UserPo mainUser;
        ChlUserPo chlUserPo = this.chlUserRepository.queryByChlTypeChlAppidChlAppTypeAndChlUserId(chlUser.getChlType(), chlUser.getChlAppId(), chlUser.getChlAppType(), chlUser.getChlUserId());
        log.info("当前渠道用户：{} 查询结果：{}", chlUser.getChlUserId(), chlUserPo);

        // 创建渠道用户
        if (Objects.isNull(chlUserPo)) {
            boolean createIfAbsent = chlUser.createIfAbsent();
            if (!createIfAbsent)
                Res.UsernameOfPasswordIllegal.thr();

            chlUserPo = new ChlUserPo();
            // 创建主用户
            mainUser = createNewMainUser(chlUser.getAppid());
            chlUserPo.setUserid(mainUser.getId());
            chlUserPo.setAppid(chlUser.getAppid());
            chlUserPo.setChlType(chlUser.getChlType());
            chlUserPo.setChlAppid(chlUser.getChlAppId());
            chlUserPo.setChlAppType(chlUser.getChlAppType());
            chlUserPo.setChlUserid(chlUser.getChlUserId());
            chlUserPo.setChlUnionid(chlUser.getChlUnionId());
            chlUserPo.setChlUserCode(chlUserPo.getChlUserCode());
            chlUserPo.setChlUserToken(chlUserPo.getChlUserToken());
            this.chlUserRepository.save(chlUserPo);
        }
        // 渠道用户已存在
        else {
            String userid = chlUserPo.getUserid();
            // 渠道用户没有客户号
            if (StringUtils.isBlank(userid)) {
                // 创建主用户
                mainUser = createNewMainUser(chlUser.getAppid());
                chlUserPo.setUserid(mainUser.getId());
                // 更新客户号
                this.chlUserRepository.update(chlUserPo);
            } else {
                mainUser = this.userRepository.queryById(userid);
                if (Objects.isNull(mainUser)) {
                    // 创建主用户
                    mainUser = createNewMainUser(chlUser.getAppid());
                    chlUserPo.setUserid(mainUser.getId());
                    // 更新客户号
                    this.chlUserRepository.update(chlUserPo);
                }
            }
        }
        MamsSession usr = new MamsSession();
        usr.addRole(Role.Authenticated.getBit());
        usr.setAppid(chlAppVo.getAppid());
        usr.setUserid(mainUser.getId());
        usr.setNickname(mainUser.getNickname());
        usr.setChl(chlUserPo.getChlType());
        usr.setChlAppid(chlUserPo.getChlAppid());
        usr.setChlAppType(chlUserPo.getChlAppType());
        usr.setChlUserid(chlUserPo.getChlUserid());
        usr.setChlUnionid(chlUserPo.getChlUnionid());
        usr.setChlUserCode(chlUserPo.getChlUserCode());
        usr.setChlUserToken(chlUserPo.getChlUserToken());
        usr.setLoginTime(LocalDateTime.now());
        return usr;
    }

    /**
     * 创建新的主用户
     *
     * @param appid appid
     * @return {@link UserPo}
     */
    private UserPo createNewMainUser(String appid) {
        AppVo vo = this.appApi.queryById(appid);
        return this.userRepository.createOnApp(vo);
    }
}