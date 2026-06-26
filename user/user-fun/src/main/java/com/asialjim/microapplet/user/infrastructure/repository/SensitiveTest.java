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

package com.asialjim.microapplet.user.infrastructure.repository;

import com.asialjim.microapplet.user.infrastructure.repository.datasource.po.IdCardUserPo;
import com.asialjim.microapplet.user.infrastructure.repository.datasource.service.IdCardUserMapperService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static com.asialjim.microapplet.sensitive.log.SensitiveLog.fmt;

@Slf4j
@Component
public class SensitiveTest implements CommandLineRunner {
    @Resource
    private IdCardUserMapperService idCardUserMapperService;


    @Override
    public void run(String... args) throws Exception {
         idCardUserMapperService.removeById("1111");
        IdCardUserPo po = new IdCardUserPo();
        po.setId("1111");
        po.setName("刘强");
        po.setIdNumber("511021198801250458");
        po.setPhone("17311111111");

//        log.info(fmt("敏感数据日志fmt：{}", po));
//        log.info("敏感数据日志1：{}",po);
        idCardUserMapperService.save(po);
        IdCardUserPo byId = idCardUserMapperService.getById("1111");
        System.out.println(byId);
//        log.info("敏感数据日志2：{}",byId);
    }
}
