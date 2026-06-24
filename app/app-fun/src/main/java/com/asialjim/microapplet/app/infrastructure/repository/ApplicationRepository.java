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

package com.asialjim.microapplet.app.infrastructure.repository;

import com.asialjim.microapplet.app.entity.web.AppVo;
import com.asialjim.microapplet.app.infrastructure.repository.po.AppPo;
import com.asialjim.microapplet.app.infrastructure.repository.service.AppMapperService;
import com.asialjim.microapplet.commons.standard.page.Page;
import com.asialjim.microapplet.commons.standard.page.Pageable;
import io.github.linpeilie.Converter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@AllArgsConstructor
public class ApplicationRepository {
    private final AppMapperService appMapperService;
    private final Converter converter;


    public AppVo queryByPlatformTypeAndAppid(String platformTypeCode, String appid) {
        AppPo po = this.appMapperService.queryByPlatformTypeAndAppid(platformTypeCode,appid);
        log.info("查询开放平台：{} appid: {}, 结果： {}",platformTypeCode,appid,po);
        if (Objects.isNull(po))
            return null;

        return this.converter.convert(po, AppVo.class);
    }

    public List<AppVo> queryByAppid(String appid) {
        List<AppPo> pos = this.appMapperService.queryByAppid(appid);
        if (CollectionUtils.isEmpty(pos))
            return Collections.emptyList();

        return pos.stream().filter(Objects::nonNull)
                .map(item -> this.converter.convert(item, AppVo.class))
                .toList();
    }

    public Page<AppVo> list(Integer page, Integer size) {
        com.mybatisflex.core.paginate.Page<AppPo> apps = this.appMapperService.page(page, size);

        List<AppVo> list = Collections.emptyList();
        if (CollectionUtils.isNotEmpty(apps.getRecords())){
            list = apps.getRecords()
                    .stream()
                    .map(item -> converter.convert(item,AppVo.class))
                    .toList();
        }
        return Pageable.paginate(list, apps.getPageNumber(), apps.getPageSize(), apps.getTotalPage(), apps.getTotalRow());
    }
}