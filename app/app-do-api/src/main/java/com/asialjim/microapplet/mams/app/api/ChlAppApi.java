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

    /**
     * 按id查询
     *
     * @param id id
     * @return {@link ChlAppVo}
     */
    @GetMapping("/{id}")
    ChlAppVo queryById(@PathVariable("id") String id);

    /**
     * 按appid查询
     *
     * @param appid appid
     * @return {@link List<ChlAppVo>}
     */
    @GetMapping("/app/{id}")
    List<ChlAppVo> queryByAppid(@PathVariable("id") String appid);

    /**
     * 按appid和CHL查询
     *
     * @param appid appid
     * @param chl   的背影
     * @return {@link List<ChlAppVo>}
     */
    @GetMapping("/app/{id}/chl/{chl}")
    List<ChlAppVo> queryByAppidAndChl(@PathVariable("id") String appid, @PathVariable("chl") String chl);

    /**
     * 按appid、CHL和CHL appid查询
     *
     * @param appid    appid
     * @param chl      的背影
     * @param chlAppid 的背影appid
     * @return {@link ChlAppVo}
     */
    @GetMapping("/app/{id}/chl/{chl}/chl-appid/{chlAppid}")
    ChlAppVo queryByAppidAndChlAndChlAppid(@PathVariable("id") String appid, @PathVariable("chl") String chl, @PathVariable("chlAppid") String chlAppid);

    @GetMapping("/app/{id}/chl/{chl}/chl-app-type/{chlAppType}")
    ChlAppVo queryByAppidAndChlAndChlAppType(@PathVariable("id") String appid,
                                             @PathVariable("chl") String chl,
                                             @PathVariable("chlAppType") String chlAppType);


    @GetMapping("/app/{id}/root")
    ChlAppVo queryRootByAppid(@PathVariable("id")String appid);

    /**
     * 按组织id查询
     *
     * @param orgId org id
     * @return {@link List<ChlAppVo>}
     */
    @GetMapping("/org/{id}")
    List<ChlAppVo> queryByOrgId(@PathVariable("id") String orgId);

    /**
     * 按CHL和CHL appid查询
     *
     * @param chl   的背影
     * @param appid appid
     * @return {@link ChlAppVo}
     */
    @GetMapping("/chl/{chl}/appid/{appid}")
    ChlAppVo queryByChlAndChlAppid(@PathVariable("chl") String chl, @PathVariable("appid") String appid);

    /**
     * 按CHL和CHL索引查询
     *
     * @param chl   的背影
     * @param appid appid
     * @return {@link ChlAppVo}
     */
    @GetMapping("/chl/{chl}/index/{index}")
    ChlAppVo queryByChlAndChlIndex(@PathVariable("chl") String chl, @PathVariable("index") String appid);

    /**
     * 按CHL查询
     *
     * @param code 代码
     * @return {@link List<ChlAppVo>}
     */
    @GetMapping("/chl/{chl}")
    List<ChlAppVo> queryByChl(@PathVariable("chl") String code);
}