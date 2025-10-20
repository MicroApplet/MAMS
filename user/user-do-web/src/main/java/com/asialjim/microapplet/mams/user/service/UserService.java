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

package com.asialjim.microapplet.mams.user.service;

import com.asialjim.microapplet.common.security.MamsSession;
import com.asialjim.microapplet.common.security.MamsSessionAttribute;
import com.asialjim.microapplet.common.utils.SensitiveUtils;
import com.asialjim.microapplet.mams.app.cons.ChannelAppType;
import com.asialjim.microapplet.mams.app.cons.ChannelType;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.po.ChlUserPo;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.po.UserPo;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.repository.ChlUserRepository;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.repository.UserRepository;
import com.asialjim.microapplet.mams.user.vo.UserVo;
import com.asialjim.microapplet.mams.wx.common.api.WeChatUserApi;
import com.asialjim.microapplet.wechat.user.WeChatUserVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 用户服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/10/16, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
@Service
public class UserService {
    @Resource
    private MamsSessionAttribute mamsSessionAttribute;
    @Resource
    private ChlUserRepository chlUserRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private WeChatUserApi weChatUserApi;


    public String currentAvatar() {
        MamsSession mamsSession = this.mamsSessionAttribute.currentLoginSession();
        ChannelType channelType = ChannelType.codeOf(mamsSession.getChl());
        switch (channelType) {
            case WeChat -> {
                WeChatUserVo weChatUserVo = weChatUserApi.queryByOpenid(mamsSession.getChlUserid());
                return Optional.ofNullable(weChatUserVo).map(WeChatUserVo::getAvatar).orElse(StringUtils.EMPTY);
            }
            default -> {

            }
        }
        return StringUtils.EMPTY;
    }


    public String currentNickname() {
        MamsSession mamsSession = this.mamsSessionAttribute.currentLoginSession();
        ChannelType channelType = ChannelType.codeOf(mamsSession.getChl());
        switch (channelType) {
            case WeChat -> {
                WeChatUserVo weChatUserVo = weChatUserApi.queryByOpenid(mamsSession.getChlUserid());
                return Optional.ofNullable(weChatUserVo).map(WeChatUserVo::getNickname).orElse(StringUtils.EMPTY);
            }
            default -> {

            }
        }
        UserPo userPo = this.userRepository.queryById(mamsSession.getUserid());
        return Optional.ofNullable(userPo).map(UserPo::getNickname).orElse(StringUtils.EMPTY);
    }

    public String currentUserPhone() {
        MamsSession mamsSession = this.mamsSessionAttribute.currentLoginSession();
        String userid = mamsSession.getUserid();
        String chl = mamsSession.getChl();
        String chlAppid = mamsSession.getChlAppid();
        ChannelType channelType = ChannelType.codeOf(chl);
        String chlAppType = StringUtils.EMPTY;
        switch (channelType) {
            case WeChat -> chlAppType = ChannelAppType.WeChatPhone.getCode();
            case Mobile -> chlAppType = ChannelAppType.Phone.getCode();
        }
        log.info("获取用户：{}  {} 渠道 {} 应用下的 {} 手机号", userid, chl, chlAppid, chlAppType);
        if (StringUtils.isBlank(chlAppType))
            return StringUtils.EMPTY;

        ChlUserPo chlUserPo = this.chlUserRepository.queryByUserIdAndChlAndChlAppidAndChlAppType(userid, chl, chlAppid, chlAppType);
        log.info("获取用户：{}  {} 渠道 {} 应用下的 {} 渠道用户信息：{}", userid, chl, chlAppid, chlAppType, chlUserPo);

        return Optional.ofNullable(chlUserPo)
                .map(ChlUserPo::getChlUserid)
                .map(SensitiveUtils::chinaMobilePhone)
                .orElse(StringUtils.EMPTY);
    }

    public UserVo queryByUserid(String userid) {
        UserPo userPo = this.userRepository.queryById(userid);
        return UserPo.toVo(userPo);
    }

}