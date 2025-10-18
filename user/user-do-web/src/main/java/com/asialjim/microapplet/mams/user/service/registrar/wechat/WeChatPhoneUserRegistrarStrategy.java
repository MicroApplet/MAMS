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

package com.asialjim.microapplet.mams.user.service.registrar.wechat;


import com.asialjim.microapplet.common.context.Res;
import com.asialjim.microapplet.common.security.MamsSession;
import com.asialjim.microapplet.common.security.MamsSessionAttribute;
import com.asialjim.microapplet.commons.security.Role;
import com.asialjim.microapplet.mams.app.api.AppApi;
import com.asialjim.microapplet.mams.app.cons.ChannelAppType;
import com.asialjim.microapplet.mams.app.cons.ChannelType;
import com.asialjim.microapplet.mams.app.context.AppRs;
import com.asialjim.microapplet.mams.app.vo.AppVo;
import com.asialjim.microapplet.mams.user.context.UserRs;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.po.ChlUserPo;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.po.UserPo;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.repository.ChlUserRepository;
import com.asialjim.microapplet.mams.user.infrastructure.datasource.repository.UserRepository;
import com.asialjim.microapplet.mams.user.service.registrar.ChlUserRegistrarStrategy;
import com.asialjim.microapplet.mams.user.vo.ChlUserVo;
import com.asialjim.microapplet.mams.wx.applet.api.WxAppletUserInfoApi;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;

/**
 * 微信手机号注册
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/10/15, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
@Component
public class WeChatPhoneUserRegistrarStrategy implements ChlUserRegistrarStrategy {
    @Resource
    private MamsSessionAttribute mamsSessionAttribute;
    @Resource
    private ChlUserRepository chlUserRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private AppApi appApi;
    @Resource
    private WxAppletUserInfoApi wxAppletUserInfoApi;

    @Override
    public ChlUserVo register(ChlUserVo user) {
        MamsSession mamsSession = mamsSessionAttribute.currentSession();
        if (Objects.isNull(mamsSession))
            Res.UserAuthFailure401Thr.thr();
        String userid = mamsSession.getUserid();
        if (StringUtils.isBlank(userid))
            Res.UserAuthFailure401Thr.thr(Collections.singletonList("客户号为空"));

        log.info("收到微信手机号注册请求: {}", user);
        String appid = user.getAppid();
        String wxAppid = user.getChlAppId();
        // code 可以调用微信后台接口：https://api.weixin.qq.com/wxa/business/getuserphonenumber
        // 文档： https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-info/phone-number/getPhoneNumber.html
        String code = user.getChlUserCode();
        // notice: 可以附加业务：解密手机号与微信服务器返回的手机号进行匹配等其他操作
        // 手机号密文信息
        String encryptedData = user.getChlUserId();
        // 手机号密文向量
        String iv = user.getChlUserToken();

        String phone = this.wxAppletUserInfoApi.userPurePhoneNumber(wxAppid,code,encryptedData,iv);
        if (StringUtils.isBlank(phone))
            UserRs.UserRegisterFailure.thr(Collections.singletonList("用户微信手机号为空"));

        AppVo appVo = this.appApi.queryById(appid);
        if (Objects.isNull(appVo))
            AppRs.NoSuchApp.thr();

        UserPo mainUser = this.userRepository.queryById(userid);
        if (Objects.isNull(mainUser))
            Res.UserAuthFailureThr.thr(Collections.singletonList("没有该主用户"));

        user.setChlType(ChannelType.WeChat.getCode());
        user.setChlAppId(wxAppid);
        user.setChlAppType(ChannelAppType.WeChatPhone.getCode());
        user.setChlUserId(phone);
        // 用户已存在
        ChlUserPo exist = this.chlUserRepository.queryByChlTypeChlAppidChlAppTypeAndChlUserId(user.getChlType(), user.getChlAppId(), user.getChlAppType(), user.getChlUserId());
        if (Objects.nonNull(exist))
            UserRs.UserExistWithoutThr.thr();

        user.setUserid(userid);
        user.setAppid(appVo.getId());
        user.setChlUnionId(StringUtils.EMPTY);
        user.setRoleBit(Role.Phone.getBit());
        user.setChlUserCode(encryptedData);
        user.setChlUserToken(iv);// 双因素认证
        return this.chlUserRepository.save(user);
    }

    @Override
    public ChannelType registerChannel() {
        return ChannelType.WeChat;
    }

    @Override
    public ChannelAppType chlAppType() {
        return ChannelAppType.WeChatPhone;
    }
}