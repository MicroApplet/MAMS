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


import com.asialjim.microapplet.common.context.Result;
import com.asialjim.microapplet.mams.app.vo.AppVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * APP api
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/18, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface AppApi {
    String path = StringUtils.EMPTY;

    /**
     * 按id查询
     *
     * @param id id
     * @return {@link AppVo}
     */
    @GetMapping("/{id}")
    AppVo queryById(@PathVariable("id") String id);


    @GetMapping("/name/root")
    AppVo getRootApp();


    /**
     * 按组织id查询
     *
     * @param id id
     * @return {@link List<AppVo>}
     */
    @GetMapping("/by-org-id/{id}")
    List<AppVo> queryByOrgId(@PathVariable("id") String id);

    @GetMapping("/list")
    Result<List<AppVo>> list(@RequestParam Long page,@RequestParam Long size);
}