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

package com.asialjim.microapplet.mams.wx.mp.web;

import com.asialjim.microapplet.common.context.Res;
import com.asialjim.microapplet.common.context.Result;
import com.asialjim.microapplet.wechat.official.module.menu.WeChatPaMenu;
import com.asialjim.microapplet.wechat.official.remoting.menu.meta.create.WeChatMenuButtonType;
import com.asialjim.microapplet.wechat.official.remoting.menu.meta.create.WeChatMenuMatchRule;
import com.asialjim.microapplet.wechat.official.service.menu.WeChatPaMenuService;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wx/{appid}/menu")
public class WxMpMenuController {
    private final WeChatPaMenuService menuService;

    /**
     * 获取微信公众号支持的菜单类型列表
     *
     * @since 2023/2/8
     */
    @GetMapping("/types")
    public Result<List<WeChatMenuButtonTypeVO>> types(@SuppressWarnings("unused") @PathVariable String appid) {
        List<WeChatMenuButtonType> types = menuService.menuButtonTypes();
        List<WeChatMenuButtonTypeVO> vos =
                Optional.ofNullable(types)
                        .orElse(Collections.emptyList())
                        .stream()
                        .filter(item -> !StringUtils.equals(item.getCode(), WeChatMenuButtonType.ILLEGAL.getCode()))
                        .map(item -> new WeChatMenuButtonTypeVO(item.getCode(), item.getName(), item.getDesc()))
                        .toList();
        return Res.OK.create(vos);
    }

    @Data
    @AllArgsConstructor
    public static final class WeChatMenuButtonTypeVO {
        private String code, name, desc;
    }

    /**
     * 查询指定微信公众号，指定匹配规则的微信公众号菜单列表
     *
     * @param appid     {@link String 公众号索引}
     * @param matchRule {@link String 匹配规则索引}
     * @since 2023/2/8
     */
    @GetMapping
    public Result<List<WeChatPaMenu>> findWeChatPaMenus(@PathVariable String appid,
                                                        @RequestParam(required = false) String matchRule) {
        List<WeChatPaMenu> menus = menuService.findWeChatMenus(appid, matchRule);
        return Res.OK.create(menus);
    }

    /**
     * 查询指定微信公众号的所有个性化菜单匹配规则列表
     *
     * @param appid {@link String 公众号索引}
     * @since 2023/2/8
     */
    @GetMapping("/matchRule")
    public Result<List<WeChatMenuMatchRule>> findWeChatMatchRules(@PathVariable String appid) {
        List<WeChatMenuMatchRule> rules = menuService.findWeChatMatchRules(appid);
        return Res.OK.create(rules);
    }

    /**
     * 声明微信公众号菜单
     *
     * @param appid     {@link String 公众号索引}
     * @param matchRule {@link String 匹配规则编号}
     * @param menus     {@link List<WeChatPaMenu> 菜单}
     * @since 2023/2/8
     */
    @PostMapping
    public Result<List<WeChatPaMenu>> statementWeChatMenus(@PathVariable String appid, @RequestParam(required = false) String matchRule, @RequestBody List<WeChatPaMenu> menus) {
        List<WeChatPaMenu> weChatPaMenus = menuService.statementMenus(appid, matchRule, menus);
        return Res.OK.create(weChatPaMenus);
    }

    /**
     * 声明个性化微信公众号菜单匹配规则
     *
     * @param appid {@link String 公众号索引}
     * @param vo    {@link WeChatMenuMatchRule 匹配规则}
     * @since 2023/2/8
     */
    @PostMapping("/matchRule")
    public Result<List<WeChatMenuMatchRule>> statementWeChatMatchRules(@PathVariable String appid, @RequestBody WeChatMenuMatchRuleVO vo) {
        List<WeChatMenuMatchRule> rules = menuService.statementMatchRule(appid, vo.getName(), vo.weChatMenuMatchRule());
        return Res.OK.create(rules);
    }

    @Data
    public static class WeChatMenuMatchRuleVO implements Serializable {
        @Serial
        private static final long serialVersionUID = -5897713462466382909L;

        private String name;

        /**
         * 客户标签id
         */
        private String tag_id;

        /**
         * 性别1男2女，空则不匹配
         */
        private String sex;

        /**
         * 国籍
         */
        private String country;

        /**
         * 省份
         */
        private String province;

        /**
         * 城市
         */
        private String city;

        /**
         * 客户端版本，当前只具体到系统型号：IOS(1), Android(2),Others(3)，不填则不做匹配
         */
        private String client_platform_type;

        /**
         * 语言
         */
        private String language;

        public WeChatMenuMatchRule weChatMenuMatchRule() {
            WeChatMenuMatchRule rule = new WeChatMenuMatchRule();
            rule.setTag_id(this.tag_id);
            rule.setSex(this.sex);
            rule.setCountry(this.country);
            rule.setProvince(this.province);
            rule.setCity(this.city);
            rule.setClient_platform_type(this.client_platform_type);
            rule.setLanguage(this.language);
            return rule;
        }
    }

    /**
     * 创建通用微信公众号菜单
     *
     * @param appid {@link String 公众号索引}
     * @since 2023/2/8
     */
    @GetMapping("/create")
    public Result<Void> create(@PathVariable String appid) {
        menuService.create(appid, null);
        return Res.OK.create();
    }

    /**
     * 创建微信公众号个性化菜单
     *
     * @param appid  {@link String 公众号索引}
     * @param matchRule {@link String 个性化菜单匹配规则编号}
     * @since 2023/2/8
     */
    @GetMapping("/create/{matchRule}")
    public Result<Void> createAdditional(@PathVariable String appid, @PathVariable String matchRule) {
        menuService.create(appid, matchRule);
        return Res.OK.create();
    }
}