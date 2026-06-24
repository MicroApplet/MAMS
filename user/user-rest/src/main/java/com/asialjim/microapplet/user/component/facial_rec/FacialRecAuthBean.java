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

package com.asialjim.microapplet.user.component.facial_rec;

import com.asialjim.microapplet.commons.chl.PlatformType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 人脸认证组件
 *
 * @author Asial Jim
 * @version 1.0
 * @since 2026/3/9, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
@Component
@AllArgsConstructor
public class FacialRecAuthBean {
    private static final Map<String, FacialRecAuthenticator> FACIAL_REC_AUTHENTICATOR_MAP = new HashMap<>();
    private final List<FacialRecAuthenticator> facialRecAuthenticators;

    public void facialMediaCheckByScene(String scene, String subScene, Consumer<Boolean> setImageNeed, Consumer<Boolean> setVideoNeed) {
        // TODO
        log.info("根据人脸核身场景：{}:{} 判断是否需要人脸图片与视频到本地保存", scene, subScene);
        setImageNeed.accept(true);//TODO, 默认需要保存图片
        setVideoNeed.accept(false);//TODO
    }


    public FacialRecAuthenticator authenticator(PlatformType platformType,
                                                boolean facialImageRequire,
                                                boolean facialVideoRequire) {

        String key = platformType.getCode() + ":" + facialImageRequire + ":" + facialVideoRequire;
        return FACIAL_REC_AUTHENTICATOR_MAP
                .computeIfAbsent(key,
                        tag -> facialRecAuthenticators
                                .stream()
                                .filter(item -> item.support(platformType))
                                .filter(item -> item.imageSupport() == facialImageRequire)
                                .filter(item -> item.videoSupport() == facialVideoRequire)
                                .findFirst()
                                .orElse(FacialRecAuthenticator.UnSupport.INSTANCE)
                );
    }
}