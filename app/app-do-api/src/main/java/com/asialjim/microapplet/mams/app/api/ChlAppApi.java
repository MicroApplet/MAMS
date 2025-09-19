/*
 *    Copyright 2014-$year.today <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.asialjim.microapplet.mams.app.api;

import com.asialjim.microapplet.mams.app.vo.ChlAppVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 渠道应用API
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/18, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface ChlAppApi {
    String path = "/chl";

    @GetMapping("/{id}")
    ChlAppVo queryById(@PathVariable("id") String id);

    @GetMapping("/app/{id}")
    List<ChlAppVo> queryByAppid(@PathVariable("id") String appid);

    @GetMapping("/app/{id}/chl/{chl}")
    List<ChlAppVo> queryByAppidAndChl(@PathVariable("id") String appid, @PathVariable("chl") String chl);

    @GetMapping("/org/{id}")
    List<ChlAppVo> queryByOrgId(@PathVariable("id") String orgId);

    @GetMapping("/chl/{chl}/appid/{appid}")
    ChlAppVo queryByChlAndChlAppid(@PathVariable("chl") String chl, @PathVariable("appid") String appid);

    @GetMapping("/chl/{chl}/index/{index}")
    ChlAppVo queryByChlAndChlIndex(@PathVariable("chl") String chl, @PathVariable("index") String appid);
}