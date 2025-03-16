/*
 *  Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.asialjim.microapplet.mams.file.cloud;

import com.asialjim.microapplet.common.context.Result;
import com.asialjim.microapplet.commons.web.feign.CommonsFeignConfig;
import com.asialjim.microapplet.commons.web.feign.CommonsFeignFallback;
import com.asialjim.microapplet.mams.file.api.FileApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务器
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/14, &nbsp;&nbsp; <em>version:1.0</em>
 */
@FeignClient(
        value = FileServerProperty.fileServerName,
        path = FileServerProperty.fileServerContext + FileApi.PATH,
        configuration = CommonsFeignConfig.class,
        fallback = FileCloud.Fallback.class)
public interface FileCloud extends FileApi {


    /**
     * 奖及策略
     *
     * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
     * @version 1.0
     * @since 2025/3/14, &nbsp;&nbsp; <em>version:1.0</em>
     */
    @Component
    class Fallback extends CommonsFeignFallback implements FileCloud {

        @Override
        public Result<String> upload(@RequestPart MultipartFile file) {
            return super.serviceUnavailable().create();
        }
    }
}