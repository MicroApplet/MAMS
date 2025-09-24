/*
 * Copyright 2014-2025 <a href="mailto:asialjim@qq.com">Asial Jim</a>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.asialjim.microapplet.mams.applet.infrastructure.repository;

import com.asialjim.microapplet.common.page.PageData;
import com.asialjim.microapplet.common.page.PageParameter;
import com.asialjim.microapplet.common.page.Pageable;
import com.asialjim.microapplet.mams.applet.infrastructure.repository.datasource.po.AppletPo;
import com.asialjim.microapplet.mams.applet.infrastructure.repository.datasource.service.AppletMapperService;
import com.asialjim.microapplet.mams.applet.pojo.Applet;
import com.mybatisflex.core.paginate.Page;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 应用数据存储
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Slf4j
@Component
public class AppletRepositoryImpl implements AppletRepository {
    @Resource
    private AppletMapperService appletMapperService;

    /**
     * 根据应用编号，查询应用
     *
     * @param appletId {@link String appletId}
     * @return {@link Applet }
     * @since 2025/4/10
     */
    @Override
    public Applet queryById(String appletId) {
        AppletPo po = this.appletMapperService.queryById(appletId);
        return AppletPo.fromPo(po);
    }

    /**
     * 保存应用
     *
     * @param applet {@link Applet applet}
     * @return {@link Applet }
     * @since 2025/4/24
     */
    @Override
    public Applet save(Applet applet) {
        AppletPo po = AppletPo.toPo(applet);
        boolean save = this.appletMapperService.save(po);
        if (log.isDebugEnabled())
            log.debug("保存应用：{} 结果：{}", applet, save);
        return AppletPo.fromPo(po);
    }

    @Override
    public PageData<Applet> query(PageParameter page, Applet condition) {
        AppletPo po = AppletPo.toPo(condition);
        Page<AppletPo> appletPage = Pageable.ofPage(page, item -> Page.of(item.getPage(), item.getSize()));
        Page<AppletPo> res = this.appletMapperService.pageOf(appletPage,po);
        List<AppletPo> records = res.getRecords();
        List<Applet> list = records.stream().map(AppletPo::fromPo).collect(Collectors.toList());
        PageData<Applet> targetRes = new PageData<>(list);
        targetRes.setPage(res.getPageNumber());
        targetRes.setSize(res.getPageSize());
        targetRes.setPages(res.getTotalPage());
        targetRes.setTotal(res.getTotalRow());
        return targetRes.setRecords(list);
    }
}