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

package com.asialjim.microapplet.mams.user.domain.agg;

import com.asialjim.microapplet.common.human.IdCardType;
import com.asialjim.microapplet.mams.user.infrastructure.adapter.CurrentUserAdapter;
import com.asialjim.microapplet.mams.user.infrastructure.repository.UserChlRepository;
import com.asialjim.microapplet.mams.user.infrastructure.repository.UserIdCardRepository;
import com.asialjim.microapplet.mams.user.infrastructure.repository.UserMainRepository;
import com.asialjim.microapplet.mams.user.pojo.UserChl;
import com.asialjim.microapplet.mams.user.pojo.UserIdCard;
import com.asialjim.microapplet.mams.user.pojo.UserMain;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 用户聚合根
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
@Scope("request")
public class UserAgg {
    /**
     * 主应用存储
     */
    private final UserMainRepository userMainRepository;

    /**
     * 渠道用户存储
     */
    private final UserChlRepository userChlRepository;

    /**
     * 用户证件存储
     */
    private final UserIdCardRepository userIdCardRepository;
    /**
     * 会话
     */
    private final SessionUser sessionUser;

    public UserAgg(UserMainRepository userMainRepository,
                   UserChlRepository userChlRepository,
                   UserIdCardRepository userIdCardRepository) {

        this.userMainRepository = userMainRepository;
        this.userChlRepository = userChlRepository;
        this.userIdCardRepository = userIdCardRepository;
        this.sessionUser = CurrentUserAdapter.current(null,null);
    }

    /**
     * 主用户信息
     */
    private UserMain userMain;
    /**
     * 用户三方渠道用户信息
     */
    private List<UserChl> userChlList;

    /**
     * 用户证件信息:允许用户添加多类型证件，但是同一证件仅允许有一个证件信息
     */
    private Map<IdCardType, UserIdCard> userIdCardMap;


    public UserMain userMain() {
        if (Objects.nonNull(this.userMain))
            return this.userMain;

        this.userMain = this.userMainRepository.queryBySession(sessionUser);
        return this.userMain;
    }

    public SessionUser sessionUser() {
        return this.sessionUser;
    }
}