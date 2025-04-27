package com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.meta.accesstoken;

import com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.meta.BaseWeChatApiRes;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * API access-token 获取结果
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 3.0
 * @since 2025/4/27, &nbsp;&nbsp; <em>version:3.0</em>
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class WeChatAccessTokenRes extends BaseWeChatApiRes {
    private static final long serialVersionUID = 6912919504306371557L;

    private String access_token;
    private Integer expires_in;
}