package com.asialjim.microapplet.mams.user.service.registrar.wechat;


import com.asialjim.microapplet.mams.app.cons.ChannelAppType;
import com.asialjim.microapplet.mams.app.cons.ChannelType;
import com.asialjim.microapplet.mams.user.service.registrar.ChlUserRegistrarStrategy;
import com.asialjim.microapplet.mams.user.vo.ChlUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
// 微信手机号注册
@Component
public class WeChatPhoneUserRegistrarStrategy implements ChlUserRegistrarStrategy {
    @Override
    public ChannelType registerChannel() {
        return ChannelType.WeChat;
    }

    @Override
    public ChannelAppType chlAppType() {
        return ChannelAppType.WeChatPhone;
    }

    @Override
    public ChlUserVo register(ChlUserVo user) {
        log.info("收到微信手机号注册请求: {}",user);
        // TODO 解密微信用户手机号
        String appid = user.getAppid();
        String wxAppid = user.getChlAppId();
        // code 可以调用微信后台接口：https://api.weixin.qq.com/wxa/business/getuserphonenumber
        // 文档： https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-info/phone-number/getPhoneNumber.html
        String code = user.getChlUserCode();
        String encryptedData = user.getChlUserId();
        String iv = user.getChlUserToken();
        return user;
    }
}