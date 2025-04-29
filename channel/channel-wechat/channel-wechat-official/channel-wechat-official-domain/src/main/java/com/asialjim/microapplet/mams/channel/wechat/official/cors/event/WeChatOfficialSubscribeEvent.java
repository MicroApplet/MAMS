package com.asialjim.microapplet.mams.channel.wechat.official.cors.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 微信公众号用户关注事件
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WeChatOfficialSubscribeEvent implements Serializable {
    private static final long serialVersionUID = 5662297765758008379L;

    /**
     * 关注用户
     */
    private String openid;
    /**
     * 关注应用编号
     */
    private String subjectId;
    /**
     * 关注时间
     */
    private Long subTime;
    /**
     * 关注业务场景值
     */
    private String scene;
    /**
     * 关注二维码编号
     */
    private String ticket;
}