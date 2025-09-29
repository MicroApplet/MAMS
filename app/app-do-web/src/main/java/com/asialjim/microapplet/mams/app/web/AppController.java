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

package com.asialjim.microapplet.mams.app.web;

import com.asialjim.microapplet.common.utils.JsonUtil;
import com.asialjim.microapplet.mams.app.api.AppApi;
import com.asialjim.microapplet.mams.app.context.AppRs;
import com.asialjim.microapplet.mams.app.infrastructure.datasource.repository.AppRepository;
import com.asialjim.microapplet.mams.app.vo.AppVo;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * APP 服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/18, &nbsp;&nbsp; <em>version:1.0</em>
 */
@RestController
@RequestMapping(AppApi.path)
public class AppController implements AppApi {
    @Resource
    private AppRepository appRepository;

    /**
     * 按id查询
     *
     * @param id id
     * @return {@link AppVo}
     */
    @Override
    public AppVo queryById(String id) {
        return this.appRepository.queryVoById(id);
    }


    @Override
    public AppVo getRootApp() {
        //String json = this.appRepository.queryVoByName("root");
        //List<AppVo> roots = null;
        //if (StringUtils.isNotBlank(json))
        //    roots = JsonUtil.instance.toList(json, AppVo.class);
        //if (Objects.isNull(roots))
        //    roots = new ArrayList<>();

        List<AppVo> roots = this.appRepository.queryVoByName("root");
        if (CollectionUtils.size(roots) > 1)
            AppRs.RootExistMoreThanOne.thr();

        return roots.stream()
                .filter(Objects::nonNull)
                .findAny()
                .orElseGet(() -> {
                    AppVo root = new AppVo();
                    root.setId("root");
                    root.setName("root");
                    root.setOrgId("root");
                    root.setStatus("ON");
                    root.setDeleted(false);
                    root.setCreateTime(LocalDateTime.now());
                    root.setUpdateTime(LocalDateTime.now());
                    return appRepository.create(root);
                });
    }

    /**
     * 按组织id查询
     *
     * @param id id
     * @return {@link List<AppVo>}
     */
    @Override
    public List<AppVo> queryByOrgId(String id) {
        return this.appRepository.queryVoByOrgId(id);
    }
}