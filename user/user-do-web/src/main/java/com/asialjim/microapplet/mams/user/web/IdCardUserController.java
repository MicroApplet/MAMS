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

package com.asialjim.microapplet.mams.user.web;

import com.asialjim.microapplet.mams.user.api.IdCardUserApi;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.repository.IdCardUserRepository;
import com.asialjim.microapplet.mams.user.service.IdCardUserService;
import com.asialjim.microapplet.mams.user.vo.IdCardUserVo;
import com.asialjim.microapplet.mams.user.vo.UserIdCardAuthenticateReq;
import com.asialjim.microapplet.mams.user.vo.UserIdCardSensitiveVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 证件用户API接口
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/10/13, &nbsp;&nbsp; <em>version:1.0</em>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(IdCardUserApi.path)
public class IdCardUserController implements IdCardUserApi {
    private final IdCardUserRepository idCardUserRepository;
    private final IdCardUserService idCardUserService;

    @Override
    public List<IdCardUserVo> queryByUserid(@PathVariable("id") String userid) {
        return this.idCardUserRepository.queryByUserid(userid);
    }

    @Override
    public UserIdCardSensitiveVo status(@RequestParam(required = false) String idType) {
        return this.idCardUserService.current(idType);
    }

    @Override
    public UserIdCardSensitiveVo authenticate(@RequestBody UserIdCardAuthenticateReq req) {
        return this.idCardUserService.authenticate(req);
    }

    @Override
    public List<String> queryUseridByNameOfIdNoForAppid(@RequestParam(required = false) String name,
                                                        @RequestParam(required = false) String idNo,
                                                        @RequestParam String appid) {
        return this.idCardUserService.queryUseridByNameOfIdNoForAppid(name, idNo, appid);
    }
}