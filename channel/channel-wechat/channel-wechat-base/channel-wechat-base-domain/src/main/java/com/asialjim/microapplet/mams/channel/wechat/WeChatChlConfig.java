package com.asialjim.microapplet.mams.channel.wechat;

import com.asialjim.microapplet.remote.spring.RemoteScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 微信渠道配置
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/27, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Configuration
@ComponentScan
@RemoteScan("com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting")
public class WeChatChlConfig {
}