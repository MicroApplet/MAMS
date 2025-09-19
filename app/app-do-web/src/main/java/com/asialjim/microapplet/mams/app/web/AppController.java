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

import com.asialjim.microapplet.mams.app.api.AppApi;
import com.asialjim.microapplet.mams.app.infrastructure.datasource.repository.AppRepository;
import com.asialjim.microapplet.mams.app.vo.AppVo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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