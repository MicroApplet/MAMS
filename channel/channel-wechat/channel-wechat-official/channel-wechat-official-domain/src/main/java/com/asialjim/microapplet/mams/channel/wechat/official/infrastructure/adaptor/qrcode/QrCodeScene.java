package com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.adaptor.qrcode;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 二维码业务场景
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface QrCodeScene {

    /**
     * 业务场景
     */
    String value();

    /**
     * 二维码业务场景描述
     */
    String desc() default "";

    @Data
    @Accessors(chain = true)
    class Meta {
        /**
         * 业务场景名称
         */
        private String name;
        /**
         * 业务场景描述
         */
        private String desc;
    }
}