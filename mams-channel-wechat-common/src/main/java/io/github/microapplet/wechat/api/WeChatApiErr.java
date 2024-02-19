/*
 * Copyright 2014-2024 <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
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

package io.github.microapplet.wechat.api;

import io.github.microapplet.web.error.ResCode;
import io.github.microapplet.wechat.remoting.context.WeChatApiResultEnumeration;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * WeChat Common Api Err
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0.0
 * @since 2024/2/19, &nbsp;&nbsp; <em>version:1.0.0</em>
 */
@Getter
@AllArgsConstructor
public enum WeChatApiErr implements ResCode {
    CODE__1(WeChatApiResultEnumeration.CODE__1),
    CODE_0(WeChatApiResultEnumeration.CODE_0),

    CODE_20001(WeChatApiResultEnumeration.CODE_20001),


    CODE_41040(WeChatApiResultEnumeration.CODE_41040),
    CODE_45062(WeChatApiResultEnumeration.CODE_45062),
    CODE_45083(WeChatApiResultEnumeration.CODE_45083),
    CODE_45084(WeChatApiResultEnumeration.CODE_45084),
    CODE_89505(WeChatApiResultEnumeration.CODE_89505),
    CODE_89504(WeChatApiResultEnumeration.CODE_89504),
    CODE_40249(WeChatApiResultEnumeration.CODE_40249),
    CODE_40250(WeChatApiResultEnumeration.CODE_40250),
    CODE_40251(WeChatApiResultEnumeration.CODE_40251),
    CODE_40252(WeChatApiResultEnumeration.CODE_40252),

    CODE_40202(WeChatApiResultEnumeration.CODE_40202),

    CODE_40203(WeChatApiResultEnumeration.CODE_40203),
    CODE_40201(WeChatApiResultEnumeration.CODE_40201),
    CODE_40002(WeChatApiResultEnumeration.CODE_40002),
    CODE_40013(WeChatApiResultEnumeration.CODE_40013),
    CODE_40125(WeChatApiResultEnumeration.CODE_40125),
    CODE_40164(WeChatApiResultEnumeration.CODE_40164),
    CODE_41002(WeChatApiResultEnumeration.CODE_41002),
    CODE_41004(WeChatApiResultEnumeration.CODE_41004),
    CODE_43002(WeChatApiResultEnumeration.CODE_43002),
    CODE_45009(WeChatApiResultEnumeration.CODE_45009),
    CODE_45011(WeChatApiResultEnumeration.CODE_45011),


    CODE_40001(WeChatApiResultEnumeration.CODE_40001),
    CODE_40003(WeChatApiResultEnumeration.CODE_40003),
    CODE_40004(WeChatApiResultEnumeration.CODE_40004),
    CODE_40005(WeChatApiResultEnumeration.CODE_40005),
    CODE_40006(WeChatApiResultEnumeration.CODE_40006),
    CODE_40007(WeChatApiResultEnumeration.CODE_40007),
    CODE_40008(WeChatApiResultEnumeration.CODE_40008),
    CODE_40009(WeChatApiResultEnumeration.CODE_40009),
    CODE_40010(WeChatApiResultEnumeration.CODE_40010),
    CODE_40011(WeChatApiResultEnumeration.CODE_40011),
    CODE_40012(WeChatApiResultEnumeration.CODE_40012),


    CODE_40014(WeChatApiResultEnumeration.CODE_40014),
    CODE_40015(WeChatApiResultEnumeration.CODE_40015),
    CODE_40016(WeChatApiResultEnumeration.CODE_40016),
    CODE_40017(WeChatApiResultEnumeration.CODE_40017),
    CODE_40018(WeChatApiResultEnumeration.CODE_40018),
    CODE_40019(WeChatApiResultEnumeration.CODE_40019),
    CODE_40020(WeChatApiResultEnumeration.CODE_40020),

    CODE_40021(WeChatApiResultEnumeration.CODE_40021),
    CODE_40022(WeChatApiResultEnumeration.CODE_40022),
    CODE_40023(WeChatApiResultEnumeration.CODE_40023),
    CODE_40024(WeChatApiResultEnumeration.CODE_40024),
    CODE_40025(WeChatApiResultEnumeration.CODE_40025),
    CODE_40026(WeChatApiResultEnumeration.CODE_40026),
    CODE_40027(WeChatApiResultEnumeration.CODE_40027),
    CODE_40028(WeChatApiResultEnumeration.CODE_40028),
    CODE_40029(WeChatApiResultEnumeration.CODE_40029),
    CODE_40030(WeChatApiResultEnumeration.CODE_40030),
    CODE_40031(WeChatApiResultEnumeration.CODE_40031),
    CODE_40032(WeChatApiResultEnumeration.CODE_40032),
    CODE_40033(WeChatApiResultEnumeration.CODE_40033),
    CODE_40035(WeChatApiResultEnumeration.CODE_40035),
    CODE_40038(WeChatApiResultEnumeration.CODE_40038),
    CODE_40039(WeChatApiResultEnumeration.CODE_40039),
    CODE_40048(WeChatApiResultEnumeration.CODE_40048),
    CODE_40050(WeChatApiResultEnumeration.CODE_40050),
    CODE_40051(WeChatApiResultEnumeration.CODE_40051),
    CODE_40060(WeChatApiResultEnumeration.CODE_40060),


    CODE_40117(WeChatApiResultEnumeration.CODE_40117),
    CODE_40118(WeChatApiResultEnumeration.CODE_40118),
    CODE_40119(WeChatApiResultEnumeration.CODE_40119),
    CODE_40120(WeChatApiResultEnumeration.CODE_40120),
    CODE_40121(WeChatApiResultEnumeration.CODE_40121),
    CODE_40132(WeChatApiResultEnumeration.CODE_40132),
    CODE_40137(WeChatApiResultEnumeration.CODE_40137),
    CODE_40155(WeChatApiResultEnumeration.CODE_40155),
    CODE_40163(WeChatApiResultEnumeration.CODE_40163),
    CODE_40227(WeChatApiResultEnumeration.CODE_40227),

    CODE_41001(WeChatApiResultEnumeration.CODE_41001),
    CODE_41003(WeChatApiResultEnumeration.CODE_41003),
    CODE_41005(WeChatApiResultEnumeration.CODE_41005),
    CODE_41006(WeChatApiResultEnumeration.CODE_41006),
    CODE_41007(WeChatApiResultEnumeration.CODE_41007),
    CODE_41008(WeChatApiResultEnumeration.CODE_41008),
    CODE_41009(WeChatApiResultEnumeration.CODE_41009),

    CODE_42001(WeChatApiResultEnumeration.CODE_42001),
    CODE_42002(WeChatApiResultEnumeration.CODE_42002),
    CODE_42003(WeChatApiResultEnumeration.CODE_42003),
    CODE_42007(WeChatApiResultEnumeration.CODE_42007),
    CODE_42010(WeChatApiResultEnumeration.CODE_42010),

    CODE_43001(WeChatApiResultEnumeration.CODE_43001),
    CODE_43003(WeChatApiResultEnumeration.CODE_43003),
    CODE_43004(WeChatApiResultEnumeration.CODE_43004),
    CODE_43005(WeChatApiResultEnumeration.CODE_43005),
    CODE_43019(WeChatApiResultEnumeration.CODE_43019),
    CODE_43116(WeChatApiResultEnumeration.CODE_43116),

    CODE_44001(WeChatApiResultEnumeration.CODE_44001),
    CODE_44002(WeChatApiResultEnumeration.CODE_44002),
    CODE_44003(WeChatApiResultEnumeration.CODE_44003),
    CODE_44004(WeChatApiResultEnumeration.CODE_44004),
    CODE_44008(WeChatApiResultEnumeration.CODE_44008),
    CODE_44009(WeChatApiResultEnumeration.CODE_44009),

    CODE_45001(WeChatApiResultEnumeration.CODE_45001),
    CODE_45002(WeChatApiResultEnumeration.CODE_45002),
    CODE_45003(WeChatApiResultEnumeration.CODE_45003),
    CODE_45004(WeChatApiResultEnumeration.CODE_45004),
    CODE_45005(WeChatApiResultEnumeration.CODE_45005),
    CODE_45006(WeChatApiResultEnumeration.CODE_45006),
    CODE_45007(WeChatApiResultEnumeration.CODE_45007),
    CODE_45008(WeChatApiResultEnumeration.CODE_45008),

    CODE_45010(WeChatApiResultEnumeration.CODE_45010),
    CODE_45015(WeChatApiResultEnumeration.CODE_45015),
    CODE_45016(WeChatApiResultEnumeration.CODE_45016),
    CODE_45017(WeChatApiResultEnumeration.CODE_45017),
    CODE_45018(WeChatApiResultEnumeration.CODE_45018),
    CODE_45047(WeChatApiResultEnumeration.CODE_45047),
    CODE_45064(WeChatApiResultEnumeration.CODE_45064),
    CODE_45065(WeChatApiResultEnumeration.CODE_45065),
    CODE_45066(WeChatApiResultEnumeration.CODE_45066),
    CODE_45067(WeChatApiResultEnumeration.CODE_45067),
    CODE_45110(WeChatApiResultEnumeration.CODE_45110),

    CODE_46001(WeChatApiResultEnumeration.CODE_46001),
    CODE_46002(WeChatApiResultEnumeration.CODE_46002),
    CODE_46003(WeChatApiResultEnumeration.CODE_46003),
    CODE_46004(WeChatApiResultEnumeration.CODE_46004),

    CODE_47001(WeChatApiResultEnumeration.CODE_47001),
    CODE_47003(WeChatApiResultEnumeration.CODE_47003),

    CODE_48001(WeChatApiResultEnumeration.CODE_48001),
    CODE_48002(WeChatApiResultEnumeration.CODE_48002),
    CODE_48004(WeChatApiResultEnumeration.CODE_48004),
    CODE_48005(WeChatApiResultEnumeration.CODE_48005),
    CODE_48006(WeChatApiResultEnumeration.CODE_48006),
    CODE_48008(WeChatApiResultEnumeration.CODE_48008),
    CODE_48021(WeChatApiResultEnumeration.CODE_48021),

    CODE_50001(WeChatApiResultEnumeration.CODE_50001),
    CODE_50002(WeChatApiResultEnumeration.CODE_50002),
    CODE_50005(WeChatApiResultEnumeration.CODE_50005),

    CODE_53500(WeChatApiResultEnumeration.CODE_53500),
    CODE_53501(WeChatApiResultEnumeration.CODE_53501),
    CODE_53502(WeChatApiResultEnumeration.CODE_53502),
    CODE_53600(WeChatApiResultEnumeration.CODE_53600),

    CODE_61450(WeChatApiResultEnumeration.CODE_61450),
    CODE_61451(WeChatApiResultEnumeration.CODE_61451),
    CODE_61452(WeChatApiResultEnumeration.CODE_61452),
    CODE_61453(WeChatApiResultEnumeration.CODE_61453),
    CODE_61454(WeChatApiResultEnumeration.CODE_61454),
    CODE_61455(WeChatApiResultEnumeration.CODE_61455),
    CODE_61456(WeChatApiResultEnumeration.CODE_61456),
    CODE_61457(WeChatApiResultEnumeration.CODE_61457),
    CODE_61500(WeChatApiResultEnumeration.CODE_61500),

    CODE_63001(WeChatApiResultEnumeration.CODE_63001),
    CODE_63002(WeChatApiResultEnumeration.CODE_63002),

    CODE_65301(WeChatApiResultEnumeration.CODE_65301),
    CODE_65302(WeChatApiResultEnumeration.CODE_65302),
    CODE_65303(WeChatApiResultEnumeration.CODE_65303),
    CODE_65304(WeChatApiResultEnumeration.CODE_65304),
    CODE_65305(WeChatApiResultEnumeration.CODE_65305),
    CODE_65306(WeChatApiResultEnumeration.CODE_65306),
    CODE_65307(WeChatApiResultEnumeration.CODE_65307),
    CODE_65308(WeChatApiResultEnumeration.CODE_65308),
    CODE_65309(WeChatApiResultEnumeration.CODE_65309),
    CODE_65310(WeChatApiResultEnumeration.CODE_65310),
    CODE_65311(WeChatApiResultEnumeration.CODE_65311),
    CODE_65312(WeChatApiResultEnumeration.CODE_65312),
    CODE_65313(WeChatApiResultEnumeration.CODE_65313),
    CODE_65314(WeChatApiResultEnumeration.CODE_65314),
    CODE_65316(WeChatApiResultEnumeration.CODE_65316),
    CODE_65317(WeChatApiResultEnumeration.CODE_65317),

    CODE_87009(WeChatApiResultEnumeration.CODE_87009),
    CODE_89503(WeChatApiResultEnumeration.CODE_89503),
    CODE_89501(WeChatApiResultEnumeration.CODE_89501),
    CODE_89506(WeChatApiResultEnumeration.CODE_89506),
    CODE_89507(WeChatApiResultEnumeration.CODE_89507),

    CODE_200014(WeChatApiResultEnumeration.CODE_200014),
    CODE_200016(WeChatApiResultEnumeration.CODE_200016),
    CODE_200017(WeChatApiResultEnumeration.CODE_200017),
    CODE_200018(WeChatApiResultEnumeration.CODE_200018),
    CODE_200019(WeChatApiResultEnumeration.CODE_200019),

    CODE_200020(WeChatApiResultEnumeration.CODE_200020),
    CODE_200021(WeChatApiResultEnumeration.CODE_200021),
    CODE_200011(WeChatApiResultEnumeration.CODE_200011),
    CODE_200013(WeChatApiResultEnumeration.CODE_200013),
    CODE_200012(WeChatApiResultEnumeration.CODE_200012),

    CODE_9001001(WeChatApiResultEnumeration.CODE_9001001),
    CODE_9001002(WeChatApiResultEnumeration.CODE_9001002),
    CODE_9001003(WeChatApiResultEnumeration.CODE_9001003),
    CODE_9001004(WeChatApiResultEnumeration.CODE_9001004),
    CODE_9001005(WeChatApiResultEnumeration.CODE_9001005),
    CODE_9001006(WeChatApiResultEnumeration.CODE_9001006),
    CODE_9001007(WeChatApiResultEnumeration.CODE_9001007),
    CODE_9001009(WeChatApiResultEnumeration.CODE_9001009),
    CODE_9001010(WeChatApiResultEnumeration.CODE_9001010),
    CODE_9001020(WeChatApiResultEnumeration.CODE_9001020),
    CODE_9001021(WeChatApiResultEnumeration.CODE_9001021),
    CODE_9001022(WeChatApiResultEnumeration.CODE_9001022),
    CODE_9001023(WeChatApiResultEnumeration.CODE_9001023),
    CODE_9001024(WeChatApiResultEnumeration.CODE_9001024),
    CODE_9001025(WeChatApiResultEnumeration.CODE_9001025),
    CODE_9001026(WeChatApiResultEnumeration.CODE_9001026),
    CODE_9001027(WeChatApiResultEnumeration.CODE_9001027),
    CODE_9001028(WeChatApiResultEnumeration.CODE_9001028),
    CODE_9001029(WeChatApiResultEnumeration.CODE_9001029),
    CODE_9001030(WeChatApiResultEnumeration.CODE_9001030),
    CODE_9001031(WeChatApiResultEnumeration.CODE_9001031),
    CODE_9001032(WeChatApiResultEnumeration.CODE_9001032),
    CODE_9001033(WeChatApiResultEnumeration.CODE_9001033),
    CODE_9001034(WeChatApiResultEnumeration.CODE_9001034),
    CODE_9001035(WeChatApiResultEnumeration.CODE_9001035),
    CODE_9001036(WeChatApiResultEnumeration.CODE_9001036),

    UNKNOWN(WeChatApiResultEnumeration.UNKNOWN);

    private final String code, msg, msgCn;

    WeChatApiErr(WeChatApiResultEnumeration enumeration) {
        this(String.valueOf(enumeration.getErrcode()), enumeration.getErrmsg(), enumeration.getCnMsg());
    }
}
