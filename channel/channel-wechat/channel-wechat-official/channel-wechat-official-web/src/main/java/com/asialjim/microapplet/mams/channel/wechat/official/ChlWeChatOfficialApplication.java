package com.asialjim.microapplet.mams.channel.wechat.official;

import com.asialjim.microapplet.common.application.App;
import com.asialjim.microapplet.mams.channel.wechat.WeChatOfficialChlCons;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 微信公众号渠道应用启动器
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/27, &nbsp;&nbsp; <em>version:1.0</em>
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class ChlWeChatOfficialApplication {
    public static void main(String[] args) {
        App.voidStart(WeChatOfficialChlCons.appName, WeChatOfficialChlCons.contextPath, ChlWeChatOfficialApplication.class, args);
    }
}