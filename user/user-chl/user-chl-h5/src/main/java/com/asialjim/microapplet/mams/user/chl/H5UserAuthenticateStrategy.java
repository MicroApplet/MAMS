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

package com.asialjim.microapplet.mams.user.chl;

import com.asialjim.microapplet.common.utils.PasswordStorage;
import com.asialjim.microapplet.mams.channel.base.ChlAppType;
import com.asialjim.microapplet.mams.channel.base.ChlType;
import com.asialjim.microapplet.mams.user.command.UserLoginCommand;
import com.asialjim.microapplet.mams.user.command.UserRegCommand;
import com.asialjim.microapplet.mams.user.domain.agg.SessionUser;
import com.asialjim.microapplet.mams.user.domain.agg.UserAggRoot;
import com.asialjim.microapplet.mams.user.domain.strategy.BaseUserAuthenticateStrategy;
import com.asialjim.microapplet.mams.user.infrastructure.repository.UserChlRepository;
import com.asialjim.microapplet.mams.user.infrastructure.repository.UserMainRepository;
import com.asialjim.microapplet.mams.user.pojo.UserChl;
import com.asialjim.microapplet.mams.user.res.UserResCode;
import com.asialjim.microapplet.mams.user.vo.UserRegOrLoginReq;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * H5渠道用户认证策略
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class H5UserAuthenticateStrategy extends BaseUserAuthenticateStrategy {

    public H5UserAuthenticateStrategy(UserMainRepository userMainRepository, UserChlRepository userChlRepository) {
        super(userMainRepository, userChlRepository);
    }

    @NonNull
    @Override
    public ChlType supportType() {
        return ChlType.H5;
    }

    @Override
    public void doRegistration(UserRegCommand command) {
        UserAggRoot userAgg = command.getUserAgg();
        // 当前会话
        SessionUser sessionUser = userAgg.getSessionUser();
        // 当前会话用户编号
        String userid = sessionUser.getUserid();
        // 注册参数
        UserRegOrLoginReq req = command.getReq();
        String username = req.getUsername();
        UserChl exist = this.userChlRepository.queryChlUser(supportType(), ChlType.H5.getCode(), ChlAppType.H5, username);
        if (Objects.nonNull(exist))
            UserResCode.UsernameUnavailable.throwBiz();

        UserChl target = new UserChl();
        //target.setId("");//数据库自动生成
        // 合并都主用户
        target.setUserid(userid);
        target.setAppletId(req.getAppletId());
        target.setChlType(supportType());
        target.setChlAppType(ChlAppType.H5);
        target.setChlAppId(ChlAppType.H5.getCode());
        target.setChlUserId(username);
        target.setChlUnionId(target.getUserid());
        target.setChlUserCode(PasswordStorage.createHash(req.getCode()));

        // 注册渠道用户
        String id = this.userChlRepository.saveAndGetId(target);
        target.setId(id);
    }

    @Override
    public SessionUser login(UserLoginCommand command) {
        UserRegOrLoginReq loginParameter = command.getReq();
        String username = loginParameter.getUsername();
        String password = loginParameter.getCode();

        UserChl user = this.userChlRepository.queryChlUser(supportType(), ChlAppType.H5.getCode(), ChlAppType.H5, username);
        if (Objects.isNull(user))
            UserResCode.UserNameOrPasswordErr.throwBiz();

        boolean passwordCheck = PasswordStorage.verifyPassword(password, user.getChlUserCode());
        if (!passwordCheck)
            UserResCode.UserNameOrPasswordErr.throwBiz();
        UserAggRoot userAgg = command.getUserAggWithUserid(user.getUserid());
        SessionUser sessionUser = userAgg.getSessionUser();
        sessionUser.setUserid(user.getUserid());
        sessionUser.setUsername(user.getChlUserId());
        sessionUser.setChlType(ChlAppType.H5.getCode());
        sessionUser.setChlAppid(ChlAppType.H5.getCode());
        sessionUser.setChlAppType(ChlAppType.H5.getCode());
        return sessionUser;
    }
}