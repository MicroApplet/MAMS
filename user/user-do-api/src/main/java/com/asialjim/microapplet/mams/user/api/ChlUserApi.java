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

package com.asialjim.microapplet.mams.user.api;

import com.asialjim.microapplet.mams.user.vo.ChlUserVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 渠道用户API
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/9/30, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface ChlUserApi {
    String path = "/chl-user";


    @GetMapping("/user/{userid}")
    List<ChlUserVo> queryByUserid(@PathVariable("userid") String userid);

    @GetMapping("/user/{userid}/chl/{chl}/appid/{chlAppid}/app-type/{chlAppType}")
    ChlUserVo queryByUseridAndChlAndChlAppidAndChlAppType(@PathVariable("userid") String userid,
                                                          @PathVariable("chl") String chl,
                                                          @PathVariable("chlAppid") String chlAppid,
                                                          @PathVariable("chlAppType") String chlAppType);

    @GetMapping("/userid")
    List<String> queryUseridByChlAppidTypeForAppid(@RequestParam String chl,
                                                   @RequestParam String chlAppid,
                                                   @RequestParam String chlAppType,
                                                   @RequestParam String appid);
}