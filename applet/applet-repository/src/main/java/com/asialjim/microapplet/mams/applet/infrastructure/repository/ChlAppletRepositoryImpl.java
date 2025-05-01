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

package com.asialjim.microapplet.mams.applet.infrastructure.repository;

import com.asialjim.microapplet.mams.applet.infrastructure.repository.datasource.po.ChlAppletPo;
import com.asialjim.microapplet.mams.applet.infrastructure.repository.datasource.service.ChlAppletMapperService;
import com.asialjim.microapplet.mams.applet.pojo.ChlApplet;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 渠道应用数据存储
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/10, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class ChlAppletRepositoryImpl implements ChlAppletRepository {
    @Resource
    private ChlAppletMapperService chlAppletMapperService;

    /**
     * 获取指定应用编号的渠道应用列表信息,即： queryBy {@link ChlApplet#getAppletId()}
     *
     * @param appletId {@link String appletId}
     * @return {@link List<ChlApplet> }
     * @since 2025/4/10
     */
    public List<ChlApplet> listByAppletId(String appletId) {
        List<ChlAppletPo> list = this.chlAppletMapperService.listByAppletId(appletId);
        return list.stream().map(ChlAppletPo::fromPo).collect(Collectors.toList());
    }

    @Override
    public ChlApplet queryByChlAppId(String chlAppId) {
        ChlAppletPo po = this.chlAppletMapperService.queryByChlAppId(chlAppId);
        return ChlAppletPo.fromPo(po);
    }

    @Override
    public ChlApplet save(ChlApplet body) {
        ChlAppletPo po = ChlAppletPo.toPo(body);
        this.chlAppletMapperService.save(po);
        return ChlAppletPo.fromPo(po);
    }

    @Override
    public ChlApplet queryById(String id) {
        ChlAppletPo po = this.chlAppletMapperService.queryById(id);
        return ChlAppletPo.fromPo(po);
    }

    @Override
    public ChlApplet queryByIndex(String index) {
        ChlAppletPo po = this.chlAppletMapperService.queryById(index);
        if(Objects.isNull(po))
            po = this.chlAppletMapperService.queryByChlAppId(index);

        if(Objects.isNull(po))
            po = this.chlAppletMapperService.queryBySubjectId(index);
        return ChlAppletPo.fromPo(po);
    }

    @Override
    public ChlApplet queryByAppidAndType(String appid, String appTypeCode) {
        ChlAppletPo po = this.chlAppletMapperService.queryByChlAppidAndType(appid, appTypeCode);
        return ChlAppletPo.fromPo(po);
    }
}