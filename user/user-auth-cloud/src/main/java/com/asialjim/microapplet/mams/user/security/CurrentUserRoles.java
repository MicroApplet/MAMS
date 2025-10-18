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

package com.asialjim.microapplet.mams.user.security;

import com.asialjim.microapplet.common.security.MamsSession;
import com.asialjim.microapplet.commons.security.CurrentRoles;
import com.asialjim.microapplet.commons.security.Role;
import com.asialjim.microapplet.mams.user.api.ChlUserApi;
import com.asialjim.microapplet.mams.user.api.IdCardUserApi;
import com.asialjim.microapplet.mams.user.vo.ChlUserVo;
import com.asialjim.microapplet.mams.user.vo.IdCardUserVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import com.asialjim.microapplet.common.security.MamsSessionAttribute;

import java.util.List;
import java.util.Objects;

/**
 * 获取当前用户角色代码
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/10/13, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
@RequiredArgsConstructor
public class CurrentUserRoles implements CurrentRoles {
    private final ChlUserApi chlUserApi;
    private final IdCardUserApi idCardUserApi;
    private final MamsSessionAttribute mamsSessionAttribute;

    @Override
    public long hasRole() {
        long bit = 0;
        MamsSession session = this.mamsSessionAttribute.currentSession();
        if (Objects.isNull(session) || StringUtils.isBlank(session.getUserid()))
            return Role.Tourist.getBit();
        bit |= Role.AUTHENTICATED_BIT;

        String userid = session.getUserid();
        List<ChlUserVo> chlUserVos = this.chlUserApi.queryByUserid(userid);

        // 添加渠道用户角色表
        if (CollectionUtils.isNotEmpty(chlUserVos)) {
            for (ChlUserVo chlUserVo : chlUserVos) {
                if (Objects.isNull(chlUserVo))
                    continue;
                Long roleBit = chlUserVo.getRoleBit();
                if (Objects.isNull(roleBit))
                    continue;
                bit |= roleBit;
            }
        }

        // 判定证件用户角色
        List<IdCardUserVo> idCardUserVos = this.idCardUserApi.queryByUserid(userid);
        if (CollectionUtils.isNotEmpty(idCardUserVos))
            bit |= Role.ID_CARD_USER_BIT;
        return bit;
    }
}