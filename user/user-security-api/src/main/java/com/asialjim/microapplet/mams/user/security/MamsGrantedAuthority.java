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

import com.asialjim.microapplet.commons.security.Role;
import com.asialjim.microapplet.mams.app.cons.ChannelAppType;
import com.asialjim.microapplet.mams.app.cons.ChannelCons;
import com.asialjim.microapplet.mams.app.cons.ChannelType;
import com.asialjim.microapplet.mams.user.vo.ChlUserVo;
import com.asialjim.microapplet.mams.user.vo.IdCardUserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;
import java.util.stream.Stream;


/**
 * MAMS 用户已得到角色包装
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/30, &nbsp;&nbsp; <em>version:1.0</em>
 */
public class MamsGrantedAuthority implements GrantedAuthority {
    private final IdCardUserVo idCardUserVo;
    private final ChlUserVo chlUserVo;

    public MamsGrantedAuthority(IdCardUserVo idCardUserVo) {
        this.idCardUserVo = idCardUserVo;
        this.chlUserVo = null;
    }

    public MamsGrantedAuthority(ChlUserVo chlUserVo) {
        this.idCardUserVo = null;
        this.chlUserVo = chlUserVo;
    }

    @Override
    @SuppressWarnings({"ConstantValue", "SwitchStatementWithTooFewBranches"})
    public String getAuthority() {
        if (Objects.isNull(idCardUserVo) && Objects.isNull(chlUserVo))
            return StringUtils.EMPTY;

        if (Objects.nonNull(idCardUserVo))
            return Role.IdCardUser.code;

        if (Objects.nonNull(chlUserVo)) {
            switch (chlUserVo.getChlType()) {
                // PC 用户
                case ChannelCons.PC -> {
                    switch (chlUserVo.getChlAppType()) {
                        // CMS 用户
                        case ChannelCons.CMS -> {
                            return Role.CMSUser.getCode();
                        }

                        default -> {
                            return StringUtils.EMPTY;
                        }
                    }
                }

                // 移动端用户
                case ChannelCons.MOBILE -> {
                    switch (chlUserVo.getChlAppType()) {
                        // 手机号用户
                        case ChannelCons.PHONE -> {
                            return Role.Phone.getCode();
                        }

                        default -> {
                            return StringUtils.EMPTY;
                        }
                    }
                }

                // 微信用户
                case ChannelCons.WECHAT -> {
                    switch (chlUserVo.getChlAppType()) {
                        // 小程序用户
                        case ChannelCons.APPLET -> {
                            return Role.WeChatApplet.getCode();
                        }

                        // 公众号用户
                        case ChannelCons.OFFICIAL -> {
                            return Role.WeChatMp.getCode();
                        }

                        default -> {
                            return Role.WeChatUser.getCode();
                        }
                    }
                }
            }



            // 超管用户
            if (isRoot(chlUserVo))
                return Role.Root.getCode();
        }

        return StringUtils.EMPTY;
    }

    private boolean isRoot(ChlUserVo chlUserVo) {
        String id = chlUserVo.getId();
        String appid = chlUserVo.getAppid();
        String userid = chlUserVo.getUserid();
        String chl = chlUserVo.getChlType();
        String chlAppId = chlUserVo.getChlAppId();
        String chlAppType = chlUserVo.getChlAppType();
        String chlUserid = chlUserVo.getChlUserId();
        String chlUnionId = chlUserVo.getChlUnionId();

        boolean rootId = Stream.of(id, appid, userid, chlAppId, chlUserid, chlUnionId).allMatch(item -> StringUtils.equals(item, "root"));
        return rootId && StringUtils.equals(ChannelType.PC.getCode(), chl) && StringUtils.equals(chlAppType, ChannelAppType.CMS.getCode());
    }
}