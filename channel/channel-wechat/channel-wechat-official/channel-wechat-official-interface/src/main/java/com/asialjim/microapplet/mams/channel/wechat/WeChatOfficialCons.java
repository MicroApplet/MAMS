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

package com.asialjim.microapplet.mams.channel.wechat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 微信公众号常量池
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
public interface WeChatOfficialCons {

    /**
     * 消息常量池
     */
    interface XmlMsgTag {
        /**
         * 接收方账号
         */
        String toUserName = "ToUserName";
        /**
         * 发送方账号
         */
        String fromUserName = "FromUserName";
        /**
         * 消息时间
         */
        String createTime = "CreateTime";
        /**
         * 消息类型
         */
        String msgType = "MsgType";
        /**
         * 文本消息内容
         */
        String content = "Content";
        /**
         * 消息编号
         */
        String msgId = "MsgId";
        /**
         * 消息的数据ID（消息如果来自文章时才有）
         */
        String msgDataId = "MsgDataId";
        /**
         * 多图文时第几篇文章，从1开始（消息如果来自文章时才有）
         */
        String idx = "Idx";
        /**
         * 图片链接
         */
        String picUrl = "PicUrl";
        /**
         * 图片消息媒体编号:可以调用获取临时素材接口拉取数据
         */
        String mediaId = "MediaId";
        /**
         * 语音格式：AMR/SPEEX...
         */
        String format = "Format";
        /**
         * 16K采样率语音消息媒体id，可以调用获取临时素材接口拉取数据，返回16K采样率amr/speex语音。
         */
        String mediaId16k = "MediaId16K";
        /**
         * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
         */
        String thumbMediaId = "ThumbMediaId";
        /**
         * 纬度
         */
        String locationX = "Location_X";
        /**
         * 经度
         */
        String locationY = "Location_Y";
        /**
         * 地图缩放大小
         */
        String scale = "Scale";
        /**
         * 地理位置信息
         */
        String label = "Label";
        /**
         * 消息标题
         */
        String title = "Title";
        /**
         * 消息描述
         */
        String description = "Description";
        /**
         * 消息链接
         */
        String url = "Url";
        /**
         * 事件
         */
        String event = "Event";
        /**
         * 事件编号
         */
        String eventKey = "EventKey";
        /**
         * 二维码编号
         */
        String ticket = "Ticket";
        /**
         * 纬度
         */
        String latitude = "Latitude";
        /**
         * 经度
         */
        String longitude = "Longitude";
        /**
         * 地理位置精度
         */
        String precision = "Precision";
    }

    @Getter
    @AllArgsConstructor
    enum XmlMsgType {
        Text("text", "文本消息"),
        Image("image", "图片消息"),
        Voice("voice", "语音消息"),
        Video("video", "视频消息"),
        ShortVideo("shortvideo", "小视频消息"),
        Location("location", "地理位置消息"),
        Link("link", "链接消息"),
        Event("event", "事件消息");
        private final String code;
        private final String desc;

        public static XmlMsgType codeOf(String code) {
            return Arrays.stream(values())
                    .filter(item -> StringUtils.equals(item.getCode(), code))
                    .findFirst()
                    .orElseThrow(WeChatOfficialMsgResCode.UnSupportWeChatMsgType::ex);
        }
    }

    @Getter
    @AllArgsConstructor
    enum XmlEventType {
        Empty("","空，非事件消息适用"),
        Subscribe("subscribe", "关注事件"),
        Unsubscribe("unsubscribe", "取关事件"),
        Scan("SCAN", "扫码事件"),
        Location("LOCATION", "上报地理位置事件"),
        Click("CLICK", "自定义菜单事件"),
        View("VIEW", "点击菜单跳转链接事件");
        private final String code;
        private final String desc;

        public static XmlEventType codeOf(String text) {
            return Arrays.stream(values()).filter(item -> StringUtils.equals(item.getCode(),text)).findFirst().orElse(Empty);
        }
    }
}