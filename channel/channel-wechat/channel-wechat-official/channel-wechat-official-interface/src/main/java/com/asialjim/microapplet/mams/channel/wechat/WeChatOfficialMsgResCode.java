package com.asialjim.microapplet.mams.channel.wechat;

import com.asialjim.microapplet.common.context.ResCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 微信公众号消息响应结果
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Getter
@AllArgsConstructor
public enum WeChatOfficialMsgResCode implements ResCode {
    UnSupportWeChatMsgType(400,false,"WX:MSG:TYPE:UnSupport", "不支持的公众号消息类型");
    private final int status;
    private final boolean thr;
    private final String code;
    private final String msg;

}