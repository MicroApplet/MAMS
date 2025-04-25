package com.asialjim.microapplet.mams.channel.wechat.pojo;

import com.asialjim.microapplet.mams.channel.base.ChlAppType;
import com.asialjim.microapplet.mams.channel.base.ChlEncType;
import com.asialjim.microapplet.mams.channel.base.ChlType;
import com.asialjim.microapplet.mams.channel.base.NormalChlType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 微信应用
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 3.0
 * @since 2025/4/25, &nbsp;&nbsp; <em>version:3.0</em>
 */
@Data
@Accessors(chain = true)
public class WeChatApp implements Serializable {
    private static final long serialVersionUID = -4318694778345128619L;
    /**
     * 主键
     */
    private String id;

    /**
     * 渠道应用编号
     */
    private String appid;
    /**
     * 渠道应用类型
     */
    private ChlAppType appType;
    /**
     * 渠道应用密钥
     */
    private String appSecret;

    /**
     * 渠道应用名称
     */
    private String name;

    /**
     * 同微信公众平台subjectId
     */
    private String subjectId;
    /**
     * 同企业微信:   agentId
     */
    private String agentId;

    /**
     * 开发者配置的令牌
     */
    private String token;

    /**
     * 加密密钥:aesKey,rsaKey,M2Key..
     */
    private String aesKey;

    /**
     * 加密方式：
     */
    private ChlEncType encType;

    /**
     * 处理器链接, 即： redirectUrl
     */
    private String url;

    /**
     * 管理者 用户编号 列表, 以 ‘，’ 分隔
     */
    private String manager;

    /**
     * 描述
     */
    private String description;

    /**
     * 逻辑删除标识
     */
    private Boolean deleted;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    public final ChlType getChlType() {
        return NormalChlType.WeChat;
    }
}