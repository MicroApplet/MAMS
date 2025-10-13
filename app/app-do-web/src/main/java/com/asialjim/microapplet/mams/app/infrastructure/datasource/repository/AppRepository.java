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

import com.asialjim.microapplet.common.context.Res;
import com.asialjim.microapplet.common.context.Result;
import com.asialjim.microapplet.common.page.PageParameter;
import com.asialjim.microapplet.mams.app.context.AppRs;
import com.asialjim.microapplet.mams.app.infrastructure.datasource.po.AppPo;
import com.asialjim.microapplet.mams.app.valid.AppCreateGroup;
import com.asialjim.microapplet.mams.app.valid.AppUpdateGroup;
import com.asialjim.microapplet.mams.app.infrastructure.datasource.service.AppMapperService;
import com.asialjim.microapplet.mams.app.vo.AppVo;
import com.asialjim.microapplet.mybatis.flex.page.MyBatisFlexPageFun;
import com.mybatisflex.core.paginate.Page;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

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

    public List<AppVo> queryVoByName(String name) {
        List<AppPo> pos = appMapperService.queryByName(name);
        return Optional.ofNullable(pos)
                .stream()
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .map(AppPo::toVo)
                .filter(Objects::nonNull)
                .toList();
    }

    public AppVo create(@Validated(AppCreateGroup.class)
                        @NotNull(message = "APP-UPDATE:FAILURE:EMPTY-PARAMETER|应用信息不能为空")
                        AppVo vo) {

        AppPo po = AppPo.fromVo(vo);
        if (Objects.isNull(po))
            return null;
        appMapperService.save(po);
        vo.setId(po.getId());
        return AppPo.toVo(po);
    }


    public AppVo updateById(@Validated(AppUpdateGroup.class)
                            @NotNull(message = "APP-UPDATE:FAILURE:EMPTY-PARAMETER|应用信息不能为空")
                            AppVo vo) {

        String id = vo.getId();
        AppVo exist = this.queryVoById(id);
        if (Objects.isNull(exist))
            AppRs.NoSuchApp.thr();
        AppPo appPo = AppPo.fromVo(vo);
        this.appMapperService.updateById(appPo);
        return AppPo.toVo(appPo);
    }

    public Result<List<AppVo>> list(Long page, Long size) {
        Page<AppPo> condition = MyBatisFlexPageFun.<AppPo>of().apply(PageParameter.pageOf(page, size));
        Page<AppPo> result = appMapperService.page(condition);
        List<AppVo> list = result.getRecords().stream().map(AppPo::toVo).toList();
        return Res.OK.page(result.getPageNumber(), result.getPageSize(), result.getTotalPage(), result.getTotalRow(), list);
    }
}