/*
 * Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.asialjim.microapplet.mams.channel.wechat.official.infrastructure.remoting.qrcode.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * 公众号二维码属性
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class QrCodeProperty implements Serializable {
    private static final long serialVersionUID = -6689573068419329004L;

    @JsonProperty("expire_seconds")
    private Integer expireSeconds;

    @JsonProperty("action_name")
    private String actionName;

    @JsonProperty("scene_id")
    private Integer sceneId;

    @JsonProperty("scene_str")
    private String sceneStr;

    public CreateQrCodeRequest createApiReqParam(){
        CreateQrCodeRequest bodyParam = new CreateQrCodeRequest();

        if (Objects.nonNull(expireSeconds)){
            bodyParam.setExpireSeconds(expireSeconds);
        }

        bodyParam.setActionName(actionName);

        CreateQrCodeRequest.Scene scene = new CreateQrCodeRequest.Scene();
        if (Objects.nonNull(sceneId)){
            scene.setSceneId(sceneId);
        }

        if (Objects.nonNull(sceneStr)){
            sceneStr += "-" + (int)(Math.random() * 1000000);
            scene.setSceneStr(sceneStr);
        }

        CreateQrCodeRequest.ActionInfo actionInfo = new CreateQrCodeRequest.ActionInfo(scene);
        bodyParam.setActionInfo(actionInfo);
        return bodyParam;
    }

    public enum QrCodeActionName{
        /**
         * <h3>FIELD DESCRIPTION</h3>
         * 为临时的整型参数值a
         */
        QR_SCENE,
        /**
         * <h3>FIELD DESCRIPTION</h3>
         * 为临时的字符串参数值
         */
        QR_STR_SCENE,
        /**
         * <h3>FIELD DESCRIPTION</h3>
         * 为永久的整型参数值
         */
        QR_LIMIT_SCENE,
        /**
         * <h3>FIELD DESCRIPTION</h3>
         * 为永久的字符串参数值
         */
        QR_LIMIT_STR_SCENE
    }
}