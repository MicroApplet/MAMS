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
import com.asialjim.microapplet.mams.user.vo.ChlUserVo;
import com.asialjim.microapplet.mams.user.vo.IdCardUserVo;
import com.asialjim.microapplet.mams.user.vo.UserVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Builder
@AllArgsConstructor
public class MamsSessionUserDetails implements UserDetails {
    private final MamsSession session;
    private final UserVo userVo;
    private final List<IdCardUserVo> idCardUserVos;
    private final List<ChlUserVo> chlUserVos;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<MamsGrantedAuthority> set = new HashSet<>();
        idCardUserVos.stream().map(MamsGrantedAuthority::new).forEach(set::add);
        chlUserVos.stream().map(MamsGrantedAuthority::new).forEach(set::add);
        return set;
    }

    @Override
    public String getPassword() {
        return session.getChlUserCode();
    }

    @Override
    public String getUsername() {
        return userVo.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Optional.ofNullable(this.userVo).map(UserVo::getDeleted).orElse(false);
    }
}