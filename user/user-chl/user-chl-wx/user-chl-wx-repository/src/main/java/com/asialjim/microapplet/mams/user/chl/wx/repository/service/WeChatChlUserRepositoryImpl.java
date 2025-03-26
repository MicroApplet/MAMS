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

package com.asialjim.microapplet.mams.user.chl.wx.repository.service;

import com.asialjim.microapplet.mams.user.chl.wx.model.WeChatChlUser;
import com.asialjim.microapplet.mams.user.chl.wx.repository.WeChatChlUserRepository;
import com.asialjim.microapplet.mams.user.chl.wx.repository.mapper_service.WeChatChlUserMapperService;
import com.asialjim.microapplet.mams.user.chl.wx.repository.po.WeChatChlUserPo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 微信渠道用户数据仓库
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/3/26, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Component
public class WeChatChlUserRepositoryImpl implements WeChatChlUserRepository {
    @Resource
    private WeChatChlUserMapperService weChatChlUserMapperService;

    @Override
    public WeChatChlUser register(WeChatChlUser condition) {
        WeChatChlUserPo po = WeChatChlUserPo.toPo(condition);
        this.weChatChlUserMapperService.save(po);
        return condition;
    }

    @Override
    public WeChatChlUser queryByOpenid(String openid) {
        WeChatChlUserPo po = this.weChatChlUserMapperService.getById(openid);
        return WeChatChlUserPo.fromPo(po);
    }

    @Override
    public void update(WeChatChlUser wxChlUser) {
        WeChatChlUserPo po = WeChatChlUserPo.toPo(wxChlUser);
        this.weChatChlUserMapperService.updateById(po);
    }

    @Override
    public List<WeChatChlUser> queryByUserid(String id) {
        List<WeChatChlUserPo> pos = this.weChatChlUserMapperService.queryByUserid(id);
        if (CollectionUtils.isEmpty(pos))
            return Collections.emptyList();

        return pos.stream().map(WeChatChlUserPo::fromPo).collect(Collectors.toList());
    }
}