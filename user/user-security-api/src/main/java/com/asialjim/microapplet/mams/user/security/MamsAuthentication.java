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

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Objects;

@RequiredArgsConstructor
public class MamsAuthentication implements Authentication {
    private final MamsSessionUserDetails mamsSessionUserDetails;
    private boolean authenticated;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.mamsSessionUserDetails.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return getAuthorities();
    }

    @Override
    public Object getDetails() {
        return this.mamsSessionUserDetails;
    }

    @Override
    public Object getPrincipal() {
        return this.mamsSessionUserDetails.getSession();
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated && Objects.nonNull(this.mamsSessionUserDetails);
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return this.mamsSessionUserDetails.getUsername();
    }
}