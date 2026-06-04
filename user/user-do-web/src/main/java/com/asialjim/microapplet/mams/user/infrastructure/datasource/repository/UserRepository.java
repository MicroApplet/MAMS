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

package com.asialjim.microapplet.mams.user.infrastructure.datasource.repository;

import com.asialjim.microapplet.mams.app.vo.AppVo;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.po.UserPo;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.service.UserMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 主用户表数仓
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/22, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
@RequiredArgsConstructor
public class UserRepository {
    private final UserMapperService userMapperService;

    /**
     * 创建应用
     *
     * @param vo 签证官
     * @return {@link UserPo}
     */
    public UserPo createOnApp(AppVo vo) {
        final UserPo po = new UserPo();
        po.setAppid(vo.getId());
        po.setOrgId(vo.getOrgId());
        this.userMapperService.save(po);
        return po;
    }

    public UserPo create(UserPo po) {
        this.userMapperService.save(po);
        return po;
    }

    /**
     * 按id查询
     *
     * @param userid 用户标识
     * @return {@link UserPo}
     */
    public UserPo queryById(String userid) {
        return this.userMapperService.queryById(userid);
    }
}