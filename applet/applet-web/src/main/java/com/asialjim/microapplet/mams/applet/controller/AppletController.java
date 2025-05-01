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

package com.asialjim.microapplet.mams.applet.controller;

import com.asialjim.microapplet.common.page.PageData;
import com.asialjim.microapplet.mams.applet.interfaces.AppletInterface;
import com.asialjim.microapplet.mams.applet.interfaces.endpoint.AppletEndpoint;
import com.asialjim.microapplet.mams.applet.interfaces.endpoint.ChlAppletEndpoint;
import com.asialjim.microapplet.mams.applet.pojo.Applet;
import com.asialjim.microapplet.mams.applet.pojo.ChlApplet;
import com.asialjim.microapplet.mams.applet.vo.AppletVo;
import com.asialjim.microapplet.mams.applet.vo.ChlAppletVo;
import com.asialjim.microapplet.web.mvc.page.MVCPageParameterFun;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for Applet operations.
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppletInterface.PATH)
public class AppletController implements AppletInterface {
    private final ChlAppletEndpoint chlAppletEndpoint;
    private final AppletEndpoint appletEndpoint;

    /**
     * 保存应用
     *
     * @param applet {@link Applet applet}
     * @return {@link Applet }
     * @since 2025/4/24
     */
    @PostMapping
    public AppletVo save(@RequestBody AppletVo applet) {
        return this.appletEndpoint.save(applet);
    }

    /**
     * 分页查询
     *
     * @param condition {@link Applet condition}
     * @return {@link PageData <Applet> }
     * @since 2025/4/24
     */
    @PostMapping("/query")
    public PageData<Applet> query(@RequestBody AppletVo condition) {
        return this.appletEndpoint.query(condition, MVCPageParameterFun.INSTANCE);
    }

    /**
     * Fetches an Applet by its ID.
     *
     * @param id the Applet ID
     * @return the Applet or null if not found
     */
    @GetMapping("/{id}")
    public Applet getAppletById(@PathVariable("id") String id) {
        return appletEndpoint.getAppletById(id);
    }

    @GetMapping("/{id}/chl/applet/list")
    public List<ChlApplet> getChlAppletListByAppletId(@PathVariable("id") String appletId) {
        return appletEndpoint.getChlAppletListByAppletId(appletId);
    }

    @PostMapping("/{id}/chl/applet/save")
    public ChlApplet saveChlApplet(@PathVariable("id") String appletId, @RequestBody ChlAppletVo body) {
        body.setAppletId(appletId);
        return chlAppletEndpoint.save(ChlAppletVo.from(body));
    }

    /**
     * Fetches a ChlApplet by its channel App ID.
     *
     * @param chlAppId the channel App ID
     * @return the ChlApplet or null if not found
     */
    @GetMapping("/{id}/chl/{chlAppId}")
    public ChlApplet getChlAppletByChlAppId(@PathVariable("id") String id, @PathVariable("chlAppId") String chlAppId) {
        return appletEndpoint.getChlAppletByChlAppId(chlAppId);
    }

    @Override
    public ChlApplet getChlAppletById(String id) {
        return chlAppletEndpoint.getChlAppletById(id);
    }

    @Override
    public ChlApplet getChlAppletByIndex(String index) {
        return chlAppletEndpoint.getChlAppletByIndex(index);
    }

    @Override
    public ChlApplet getChlAppletByAppidAndType(String appid, String appTypeCode) {
        return chlAppletEndpoint.getChlAppletByAppidAndType(appid,appTypeCode);
    }
}