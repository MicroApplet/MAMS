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

import com.asialjim.microapplet.common.application.App;
import com.asialjim.microapplet.mams.applet.infrastructure.repository.datasource.po.ChlAppletPo;
import com.asialjim.microapplet.mams.applet.infrastructure.repository.datasource.service.ChlAppletMapperService;
import com.asialjim.microapplet.mams.applet.pojo.ChlApplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
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
    @Resource private ChlAppletMapperService chlAppletMapperService;

    /**
     * 获取指定应用编号的渠道应用列表信息,即： queryBy {@link ChlApplet#getAppletId()}
     *
     * @param appletId {@link String appletId}
     * @return {@link List<ChlApplet> }
     * @since 2025/4/10
     */
    public List<ChlApplet> listByAppletId(String appletId){
        List<ChlAppletPo> list = this.chlAppletMapperService.listByAppletId(appletId);
        return list.stream().map(ChlAppletPo::fromPo).collect(Collectors.toList());
    }
}