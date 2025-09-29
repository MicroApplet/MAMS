/*
 *    Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
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

package com.asialjim.microapplet.mams.app.infrastructure.datasource.repository;

import com.asialjim.microapplet.common.utils.JsonUtil;
import com.asialjim.microapplet.mams.app.infrastructure.cache.AppletCache;
import com.asialjim.microapplet.mams.app.infrastructure.datasource.po.AppPo;
import com.asialjim.microapplet.mams.app.infrastructure.datasource.service.AppMapperService;
import com.asialjim.microapplet.mams.app.vo.AppVo;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * APP 数仓
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/19, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class AppRepository {
    @Resource
    private AppMapperService appMapperService;

    /**
     * 按id查询vo
     *
     * @param id id
     * @return {@link AppVo}
     */
    public AppVo queryVoById(String id) {
        return AppPo.toVo(appMapperService.queryById(id));
    }

    /**
     * 按组织id查询vo
     *
     * @param id id
     * @return {@link List<AppVo>}
     */
    public List<AppVo> queryVoByOrgId(String id) {
        List<AppPo> pos = appMapperService.queryByOrgId(id);
        return Optional.ofNullable(pos)
                .stream()
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .map(AppPo::toVo)
                .filter(Objects::nonNull)
                .toList();
    }

    @Cacheable(value = AppletCache.Name.appVosByName, key = "#name")
    //public String queryVoByName(String name) {
    public List<AppVo> queryVoByName(String name) {
        List<AppPo> pos = appMapperService.queryByName(name);
        //List<AppVo> list = Optional.ofNullable(pos)
        return Optional.ofNullable(pos)
                .stream()
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .map(AppPo::toVo)
                .filter(Objects::nonNull)
                .toList();
        //return JsonUtil.instance.toStr(list);
    }

    public AppVo create(AppVo vo) {
        // TODO 此处应有锁
        AppPo po = AppPo.fromVo(vo);
        if (Objects.isNull(po))
            return null;
        appMapperService.save(po);
        vo.setId(po.getId());
        return AppPo.toVo(po);
    }
}