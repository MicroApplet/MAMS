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

import com.asialjim.microapplet.app.code.AgreementType;
import com.asialjim.microapplet.app.infrastructure.repository.po.AppAgreementPo;
import com.asialjim.microapplet.app.infrastructure.repository.service.AppAgreementMapperService;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Slf4j
@Component
public class AgreementInit implements CommandLineRunner {
    @Resource
    private AppAgreementMapperService appAgreementMapperService;

    @Override
    public void run(String... args) {
        List<AppAgreementPo> list = new ArrayList<>();
        StringJoiner sj = new StringJoiner("\r\n\t");
        for (AgreementType value : AgreementType.values()) {
            AppAgreementPo po = AppAgreementPo.create(value);
            QueryWrapper wrapper = new QueryWrapper()
                    .where(AppAgreementPo::getAgreementCode).eq(po.getAgreementCode())
                    .where(AppAgreementPo::getAppid).eq(AppAgreementPo.TEMPLATE);
            if (!this.appAgreementMapperService.exists(wrapper)) {
                list.add(po);
                sj.add(po.getAgreementName());
            }
        }

        if (CollectionUtils.isEmpty(list))
            return;

        boolean b = this.appAgreementMapperService.saveBatch(list);
        log.info("\r\n初始化应用服务协议数据到数据库：\r\n{}\r\n结果:\r\n\t{}", sj, b);
    }
}