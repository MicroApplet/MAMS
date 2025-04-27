package com.asialjim.microapplet.mams.channel.wechat.infrastructure.remoting.meta;

import com.asialjim.microapplet.mams.channel.wechat.infrastructure.adaptor.WeChatApiResultEnumeration;

import java.io.Serializable;
import java.util.Objects;

/**
 * 微信API响应结果
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 3.0
 * @since 2025/4/27, &nbsp;&nbsp; <em>version:3.0</em>
 */
public interface WeChatApiRes extends Serializable {
    /**
     * 业务代码
     *
     * @return {@link Integer }
     * @since 2025/4/27
     */
    Integer getErrcode();

    /**
     * 错误信息
     *
     * @return {@link String }
     * @since 2025/4/27
     */
    String getErrmsg();

    /**
     * 请求成功
     *
     * @return {@link Boolean}
     * @since 2025/4/27
     */
    default Boolean success() {
        return Objects.isNull(getErrcode()) || 0 == getErrcode();
    }

    /**
     * 请求失败
     *
     * @return {@link Boolean }
     * @since 2025/4/27
     */
    default Boolean notSuccess() {
        return !success();
    }

    /**
     * API 响应结果枚举
     *
     * @return {@link WeChatApiResultEnumeration }
     * @since 2025/4/27
     */
    default WeChatApiResultEnumeration apiResultEnumeration() {
        return WeChatApiResultEnumeration.codeOf(getErrcode());
    }

    /**
     * API请求成功判定
     *
     * @param apiRes {@link WeChatApiRes apiRes}
     * @return {@link Boolean }
     * @since 2025/4/27
     */
    static boolean success(WeChatApiRes apiRes) {
        return Objects.nonNull(apiRes) && apiRes.success();
    }

    /**
     * API请求失败判定
     *
     * @param apiRes {@link WeChatApiRes apiRes}
     * @return {@link Boolean }
     * @since 2025/4/27
     */
    static boolean notSuccess(WeChatApiRes apiRes) {
        return !success(apiRes);
    }
}