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

import com.asialjim.microapplet.common.concurrent.ConcurrentRunner;
import com.asialjim.microapplet.common.security.MamsSession;
import com.asialjim.microapplet.mams.user.api.ChlUserApi;
import com.asialjim.microapplet.mams.user.api.IdCardUserApi;
import com.asialjim.microapplet.mams.user.api.UserApi;
import com.asialjim.microapplet.mams.user.infrastructure.repository.SessionRepository;
import com.asialjim.microapplet.mams.user.vo.ChlUserVo;
import com.asialjim.microapplet.mams.user.vo.IdCardUserVo;
import com.asialjim.microapplet.mams.user.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MamsUserDetailsService implements UserDetailsService {
    private final SessionRepository sessionRepository;
    private final UserApi userApi;
    private final ChlUserApi chlUserApi;
    private final IdCardUserApi idCardUserApi;

    @Override
    public UserDetails loadUserByUsername(String jwtToken) throws UsernameNotFoundException {
        MamsSessionUserDetails.MamsSessionUserDetailsBuilder builder = MamsSessionUserDetails.builder();
        MamsSession session = this.sessionRepository.getCache(jwtToken);
        builder.session(session);
        ConcurrentRunner.runAllTask(
                () -> {
                    UserVo userVo = userApi.queryByUserid(session.getUserid());
                    builder.userVo(userVo);
                },
                () -> {
                    List<IdCardUserVo> idCardUserVos = idCardUserApi.queryByUserid(session.getUserid());
                    builder.idCardUserVos(idCardUserVos);
                },
                () -> {
                    List<ChlUserVo> chlUserVos = chlUserApi.queryByUserid(session.getUserid());
                    builder.chlUserVos(chlUserVos);
                }
        );

        return builder.build();
    }
}