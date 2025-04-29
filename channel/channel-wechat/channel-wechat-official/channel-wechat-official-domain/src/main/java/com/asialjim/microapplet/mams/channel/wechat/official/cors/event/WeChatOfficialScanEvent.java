package com.asialjim.microapplet.mams.channel.wechat.official.cors.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 微信公众号扫码事件
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WeChatOfficialScanEvent implements Serializable {
    private static final long serialVersionUID = -7455682359310768750L;

    /**
     * 扫码用户
     */
    private String openid;
    /**
     * 扫码应用编号
     */
    private String appid;
    /**
     * 扫码时间
     */
    private Long subTime;
    /**
     * 扫码业务场景值
     */
    private String scene;
    /**
     * 扫码二维码编号
     */
    private String ticket;
}