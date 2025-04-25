package com.asialjim.microapplet.mams.channel.wechat.infrastructure.repository;

import com.asialjim.microapplet.mams.channel.base.ChlAppType;
import com.asialjim.microapplet.mams.channel.wechat.pojo.WeChatApp;

/**
 * 微信应用数据仓库
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 3.0
 * @since 2025/4/25, &nbsp;&nbsp; <em>version:3.0</em>
 */
public interface WeChatAppRepository {

    /**
     * 主键查询
     *
     * @param id {@link WeChatApp#getId()}
     * @return {@link WeChatApp }
     * @since 2025/4/25
     */
    WeChatApp queryById(String id);

    /**
     * 微信应用编号与类型查询
     *
     * @param appid   {@link String appid}
     * @param appType {@link ChlAppType appType}
     * @return {@link WeChatApp }
     * @since 2025/4/25
     */
    WeChatApp queryByAppidAndType(String appid, ChlAppType appType);

    /**
     * 微信应用微信号类型查询
     *
     * @param subjectId {@link String subjectId}
     * @param appType   {@link ChlAppType appType}
     * @return {@link WeChatApp }
     * @since 2025/4/25
     */
    WeChatApp queryBySubjectIdAndType(String subjectId, ChlAppType appType);
}