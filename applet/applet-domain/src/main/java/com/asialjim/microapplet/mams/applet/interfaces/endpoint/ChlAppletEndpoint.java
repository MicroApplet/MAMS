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

package com.asialjim.microapplet.mams.applet.interfaces.endpoint;

import com.asialjim.microapplet.mams.applet.application.ChlAppletAppService;
import com.asialjim.microapplet.mams.applet.pojo.ChlApplet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 渠道应用接口
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025 05 01, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
@AllArgsConstructor
public class ChlAppletEndpoint {
    private final ChlAppletAppService chlAppletAppService;

    public ChlApplet save(ChlApplet body) {
        return chlAppletAppService.save(body);
    }

    public ChlApplet getChlAppletById(String id) {
        return this.chlAppletAppService.queryById(id);
    }

    public ChlApplet getChlAppletByIndex(String index) {
        return this.chlAppletAppService.queryByIndex(index);
    }

    public ChlApplet getChlAppletByAppidAndType(String appid, String appTypeCode) {
        return this.chlAppletAppService.queryByAppidAndType(appid,appTypeCode);
    }
}