package com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.qrcode;

import com.asialjim.microapplet.mams.channel.wechat.official.cors.event.WeChatOfficialScanEvent;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 微信公众号带有业务场景的二维码扫码处理器
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface SceneQrCodeHandler {

    static String nameFormat(String scene){
        return "inner" + scene + "WxOfficialQrCodeProcessor";
    }

    static String beanName(WeChatOfficialScanEvent msg) {
        String scene = msg.getScene();
        return nameFormat(scene);
    }

    /**
     *
	 * @param event {@link WeChatOfficialScanEvent event}
     * @return {@link ObjectNode }
     * @since 2025/4/29
     */
    ObjectNode execute(WeChatOfficialScanEvent event);

    @QrCodeScene("default")
    class Default implements SceneQrCodeHandler{

        @Override
        public ObjectNode execute(WeChatOfficialScanEvent event) {
            // 默认不做任何处理
            return null;
        }
    }
}