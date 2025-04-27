package com.asialjim.microapplet.mams.channel.wechat.infrastructure.adaptor;

import com.asialjim.microapplet.common.context.ResCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 微信API网络客户端响应结果枚举
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 3.0
 * @since 2025/4/27, &nbsp;&nbsp; <em>version:3.0</em>
 */
@Getter
@AllArgsConstructor
public enum WeChatApiResultEnumeration implements ResCode {
    CODE__1(-1, "System is Busy", "系统繁忙，此时请开发者稍候再试"),
    CODE_0(0, "Request Success", "请求成功"){
        @Override
        public boolean isSuccess() {
            return true;
        }
    },

    CODE_20001(20001, "System Error", "系统错误"),

    CODE_40001(40001, "The AppSecret is incorrect or does not belong to this official account. Please confirm the correctness of the AppSecret.", "AppSecret错误或者AppSecret不属于这个公众号，请开发者确认AppSecret的正确性"),
    CODE_40002(40002, "Invalid credential type", "不合法的凭证类型"),
    CODE_40003(40003, "Invalid OpenID. Please confirm if the model has followed the official account or if the OpenID belongs to another official account.", "不合法的 OpenID ，请开发者确认 OpenID （该用户）是否已关注公众号，或是否是其他公众号的 OpenID"),
    CODE_40004(40004, "Invalid media file type", "不合法的媒体文件类型"),
    CODE_40005(40005, "Invalid file type", "不合法的文件类型"),
    CODE_40006(40006, "Invalid file size", "不合法的文件大小"),
    CODE_40007(40007, "Invalid media file id", "不合法的媒体文件 id"),
    CODE_40008(40008, "Invalid message type", "不合法的消息类型"),
    CODE_40009(40009, "Invalid file size", "不合法的媒体文件大小"),
    CODE_40010(40010, "Invalid voice file size", "不合法的语音文件大小"),
    CODE_40011(40011, "Invalid video file size", "不合法的视频文件大小"),
    CODE_40012(40012, "Invalid thumbnail file size", "不合法的缩略图文件大小"),
    CODE_40013(40013, "Invalid AppID. Please check the correctness of the AppID, avoid special characters, and pay attention to case sensitivity.", "不合法的 AppID ，请开发者检查 AppID 的正确性，避免异常字符，注意大小写"),
    CODE_40014(40014, "Invalid access_token. Please carefully check the validity of the access_token (e.g., if it has expired) or verify that you are making the request for the appropriate official account.", "不合法的 access_token ，请开发者认真比对 access_token 的有效性（如是否过期），或查看是否正在为恰当的公众号调用接口"),
    CODE_40015(40015, "Invalid menu type", "不合法的菜单类型"),
    CODE_40016(40016, "Invalid number of buttons", "不合法的按钮个数"),
    CODE_40017(40017, "Invalid button type", "不合法的按钮类型"),
    CODE_40018(40018, "Invalid button name length", "不合法的按钮名字长度"),
    CODE_40019(40019, "Invalid button KEY length", "不合法的按钮 KEY 长度"),
    CODE_40020(40020, "Invalid button URL length", "不合法的按钮 URL 长度"),
    CODE_40021(40021, "Invalid menu version number", "不合法的菜单版本号"),
    CODE_40022(40022, "Invalid submenu level", "不合法的子菜单级数"),
    CODE_40023(40023, "Invalid number of buttons in submenu", "不合法的子菜单按钮个数"),
    CODE_40024(40024, "Invalid button type in submenu", "不合法的子菜单按钮类型"),
    CODE_40025(40025, "Invalid button name length in submenu", "不合法的子菜单按钮名字长度"),
    CODE_40026(40026, "Invalid button KEY length in submenu", "不合法的子菜单按钮 KEY 长度"),
    CODE_40027(40027, "Invalid button URL length in submenu", "不合法的子菜单按钮 URL 长度"),
    CODE_40028(40028, "Invalid model for custom menu", "不合法的自定义菜单使用用户"),
    CODE_40029(40029, "Invalid oauth_code", "无效的 oauth_code"),
    CODE_40030(40030, "Invalid refresh_token", "不合法的 refresh_token"),
    CODE_40031(40031, "Invalid openid list", "不合法的 openid 列表"),
    CODE_40032(40032, "Invalid openid list length", "不合法的 openid 列表长度"),
    CODE_40033(40033, "Invalid request characters, cannot contain \\uxxxx format", "不合法的请求字符，不能包含 \\uxxxx 格式的字符"),
    CODE_40035(40035, "Invalid parameter", "不合法的参数"),
    CODE_40038(40038, "Invalid request format", "不合法的请求格式"),
    CODE_40039(40039, "Invalid URL length", "不合法的 URL 长度"),
    CODE_40048(40048, "Invalid URL", "无效的url"),
    CODE_40050(40050, "Invalid group ID", "不合法的分组 id"),
    CODE_40051(40051, "Invalid group name", "分组名字不合法"),
    CODE_40060(40060, "Invalid article_idx when deleting a single article", "删除单篇图文时，指定的 article_idx 不合法"),

    CODE_40117(40117, "Invalid group name", "分组名字不合法"),
    CODE_40118(40118, "Invalid media_id size", "media_id 大小不合法"),
    CODE_40119(40119, "Invalid button type", "button 类型错误"),
    CODE_40120(40120, "Invalid sub-button type", "子 button 类型错误"),
    CODE_40121(40121, "Invalid media_id type", "不合法的 media_id 类型"),
    CODE_40125(40125, "Invalid appsecret. Please check the correctness of the appsecret.", "无效的appsecret，请检查appsecret的正确性"),
    CODE_40132(40132, "Invalid WeChat ID", "微信号不合法"),
    CODE_40137(40137, "Unsupported image format", "不支持的图片格式"),
    CODE_40155(40155, "Do not add other public official account's homepage link", "请勿添加其他公众号的主页链接"),
    CODE_40163(40163, "The oauth_code has been used", "oauth_code已使用"),
    CODE_40164(40164, "Add the IP to the IP whitelist.", "将ip添加到ip白名单列表即可"),
    CODE_40200(40200, "Invalid WeChat Account Type, Applet or Service App Need","账号非小程序或已认证的服务号"),
    CODE_40201(40201, "Incorrect URL, generally caused by the developer not setting a callback URL.", "不正确的URL，一般是开发者未设置回调URL。"),
    CODE_40202(40202, "Incorrect action", "不正确的action"),
    CODE_40203(40203, "Incorrect check_operator", "不正确的check_operator"),
    CODE_40227(40227, "Title is empty", "标题为空"),
    CODE_40249(40249, "Does not support sending marketing/promotional message content", "不支持下发营销/推广类的消息内容"),
    CODE_40250(40250, "The content of the message sent is not standardized (contains empty values, etc.). It is recommended to check the content standardization before sending again", "下发消息内容不规范（包含空值等），建议检查内容规范性后再下发"),
    CODE_40251(40251, "Due to historical violations, the platform has limited the number of account calls, and the current limit has been reached", "因历史违规导致平台限制账号调用上限，当前已到达下发上限"),
    CODE_40252(40252, "Part of the content sent by the template being called has entered the platform review process. Before the review is completed, the relevant content cannot be sent temporarily", "正在调用的模板下发的部分内容已进入平台审核流程，在审核完成前，相关内容暂时无法下发"),

    CODE_41001(41001, "Missing access_token parameter", "缺少 access_token 参数"),
    CODE_41002(41002, "Missing appid parameter", "缺少 appid 参数"),
    CODE_41003(41003, "Missing refresh_token parameter", "缺少 refresh_token 参数"),
    CODE_41004(41004, "Missing secret parameter", "缺少 secret 参数"),
    CODE_41005(41005, "Missing multimedia file data", "缺少多媒体文件数据"),
    CODE_41006(41006, "Missing media_id parameter", "缺少 media_id 参数"),
    CODE_41007(41007, "Missing submenu data", "缺少子菜单数据"),
    CODE_41008(41008, "Missing oauth code", "缺少 oauth code"),
    CODE_41009(41009, "Missing openid", "缺少 openid"),
    CODE_41040(41040, "Does not meet the requirements for declaring original text", "不符合声明文字原创的要求"),

    CODE_42001(42001, "Access token expired", "access_token 超时"),
    CODE_42002(42002, "Refresh token expired", "refresh_token 超时"),
    CODE_42003(42003, "Oauth code expired", "oauth_code 超时"),
    CODE_42007(42007, "User has changed their WeChat password, access token and refresh token are invalid. Re-authorization is required.", "用户修改微信密码，accesstoken 和 refreshtoken 失效，需要重新授权"),
    CODE_42010(42010, "Identical media_id sent too frequently, please try again later.", "相同 media_id 群发过快，请重试"),

    CODE_43001(43001, "GET request required", "需要 GET 请求"),
    CODE_43002(43002, "Requires a POST request", "需要 POST 请求"),
    CODE_43003(43003, "HTTPS request required", "需要 HTTPS 请求"),
    CODE_43004(43004, "Receiver must be a follower", "需要接收者关注"),
    CODE_43005(43005, "Friendship required", "需要好友关系"),
    CODE_43019(43019, "Receiver must be removed from blacklist", "需要将接收者从黑名单中移除"),
    CODE_43116(43116, "This template has been restricted due to excessive abuse.", "该模板因滥用被滥用过多，已被限制下发"),

    CODE_44001(44001, "Empty multimedia file", "多媒体文件为空"),
    CODE_44002(44002, "Empty POST data packet", "POST 的数据包为空"),
    CODE_44003(44003, "Empty article content", "图文消息内容为空"),
    CODE_44004(44004, "Empty text message content", "文本消息内容为空"),
    CODE_44008(44008, "Audio content review failed", "音频内容审核失败"),
    CODE_44009(44009, "Audio content in article review failed", "图文中的音频内容审核失败"),

    CODE_45001(45001, "Multimedia file size exceeds limit", "多媒体文件大小超过限制"),
    CODE_45002(45002, "Message content exceeds limit", "消息内容超过限制"),
    CODE_45003(45003, "Title field exceeds limit", "标题字段超过限制"),
    CODE_45004(45004, "Description field exceeds limit", "描述字段超过限制"),
    CODE_45005(45005, "Link field exceeds limit", "链接字段超过限制"),
    CODE_45006(45006, "Image link field exceeds limit", "图片链接字段超过限制"),
    CODE_45007(45007, "Voice playback time exceeds limit", "语音播放时间超过限制"),
    CODE_45008(45008, "Article message count exceeds limit", "图文消息超过限制"),
    CODE_45009(45009, "Exceeds the daily frequency limit for calls. The clear_quota interface can be called to restore the quota.", "调用超过天级别频率限制。可调用clear_quota接口恢复调用额度。"),
    CODE_45010(45010, "Maximum number of created menus exceeded", "创建菜单个数超过限制"),
    CODE_45011(45011, "API calls are too frequent. Please try again later.", "API 调用太频繁，请稍候再试"),
    CODE_45015(45015, "Response time exceeds limit", "回复时间超过限制"),
    CODE_45016(45016, "System group, modifications not allowed", "系统分组，不允许修改"),
    CODE_45017(45017, "Group name is too long", "分组名字过长"),
    CODE_45018(45018, "Maximum number of groups exceeded", "分组数量超过上限"),
    CODE_45047(45047, "Maximum number of customer service interface messages exceeded", "客服接口下行条数超过上限"),
    CODE_45062(45062, "Advertisements have been accepted, and API mass messaging is not supported", "已接广告，不支持api群发"),
    CODE_45064(45064, "Created menu contains unassociated mini-program", "创建菜单包含未关联的小程序"),
    CODE_45065(45065, "Same clientmsgid has existing mass message record, msgid of existing task included in response data", "相同 clientmsgid 已存在群发记录，返回数据中带有已存在的群发任务的 msgid"),
    CODE_45066(45066, "Same clientmsgid retry too fast, please wait for 1 minute and try again", "相同 clientmsgid 重试速度过快，请间隔1分钟重试"),
    CODE_45067(45067, "clientmsgid length exceeds limit", "clientmsgid 长度超过限制"),
    CODE_45072(45072,"Invalid Command Value","command字段取值不对"),
    CODE_45080(45080,"Send Typing Status","下发输入状态，需要之前30秒之前有过用户交互"),
    CODE_45081(45081,"Typing Status Had Send","已经在输入状态，不可重复下发"),

    CODE_45083(45083, "The set speed parameter is not within the range of 0 to 4", "设置的 speed 参数不在0到4的范围内"),
    CODE_45084(45084, "No speed parameter has been set", "没有设置 speed 参数"),
    CODE_45110(45110, "Author word count exceeds limit", "作者字数超出限制"),

    CODE_46001(46001, "No media data exists", "不存在媒体数据"),
    CODE_46002(46002, "Menu version does not exist", "不存在的菜单版本"),
    CODE_46003(46003, "Menu data does not exist", "不存在的菜单数据"),
    CODE_46004(46004, "User does not exist", "不存在的用户"),

    CODE_47001(47001, "Error parsing JSON/XML content", "解析 JSON/XML 内容错误"),
    CODE_47003(47003, "Parameter value does not meet restrictions, please refer to parameter value content restrictions for details", "参数值不符合限制要求，详情可参考参数值内容限制说明"),

    CODE_48001(48001, "API function is not authorized. Please confirm that the official account has been granted access to this interface. You can check interface permissions in the Developer Center on the official public platform website.", "api 功能未授权，请确认公众号已获得该接口，可以在公众平台官网 - 开发者中心页中查看接口权限"),
    CODE_48002(48002, "Fan has rejected messages (fan has turned off 'Receive Messages' in the official account settings).", "粉丝拒收消息（粉丝在公众号选项中，关闭了 “ 接收消息 ” ）"),
    CODE_48004(48004, "API interface has been banned. Please log in to mp.weixin.qq.com for more details.", "api 接口被封禁，请登录 mp.weixin.qq.com 查看详情"),
    CODE_48005(48005, "API prohibits deletion of material referenced by automatic replies and custom menus.", "api 禁止删除被自动回复和自定义菜单引用的素材"),
    CODE_48006(48006, "API prohibits resetting call counts because the reset limit has been reached.", "api 禁止清零调用次数，因为清零次数达到上限"),
    CODE_48008(48008, "No permission to send this type of message.", "没有该类型消息的发送权限"),
    CODE_48021(48021, "Draft saved automatically cannot be previewed/sent. Please save the draft manually first.", "自动保存的草稿无法预览/发送，请先手动保存草稿"),

    CODE_50001(50001, "User has not authorized this API.", "用户未授权该 api"),
    CODE_50002(50002, "User is restricted. The interface may be banned due to violations.", "用户受限，可能是违规后接口被封禁"),
    CODE_50005(50005, "User has not subscribed to the official account.", "用户未关注公众号"),

    CODE_53500(53500, "Publishing function is banned.", "发布功能被封禁"),
    CODE_53501(53501, "Frequent requests to publish.", "频繁请求发布"),
    CODE_53502(53502, "Invalid Publish ID.", "Publish ID 无效"),
    CODE_53503(53503, "The draft did not pass the publishing check","该草稿未通过发布检查"),
    CODE_53504(53504, "Need to go to the official website of the public platform to use the draft","需要前往公众平台官网使用草稿"),
    CODE_53505(53505, "Please manually save successfully before publishing","请手动保存成功后再发表"),
    CODE_53600(53600, "Invalid Article ID.", "Article ID 无效"),

    CODE_61450(61450, "System error.", "系统错误"),
    CODE_61451(61451, "Invalid parameter.", "参数错误"),
    CODE_61452(61452, "Invalid customer service account.", "无效客服账号"),
    CODE_61453(61453, "Customer service account already exists.", "客服账号已存在"),
    CODE_61454(61454, "The customer service account name exceeds the length limit (only 10 English characters are allowed, excluding @ and the official account's WeChat ID after @).", "客服账号名长度超过限制 ( 仅允许 10 个英文字符，不包括 @ 及 @ 后的公众号的微信号 )"),
    CODE_61455(61455, "The customer service account name contains invalid characters (only English letters and numbers are allowed).", "客服账号名包含非法字符 ( 仅允许英文 + 数字 )"),
    CODE_61456(61456, "The number of customer service accounts exceeds the limit (10 customer service accounts allowed).", "客服账号个数超过限制 (10 个客服账号 )"),
    CODE_61457(61457, "Invalid avatar file type.", "无效头像文件类型"),
    CODE_61500(61500, "Incorrect date format.", "日期格式错误"),

    CODE_63001(63001, "Some parameters are empty.", "部分参数为空"),
    CODE_63002(63002, "Invalid signature.", "无效的签名"),

    CODE_65301(65301, "The personalized menu corresponding to this menuid does not exist.", "不存在此 menuid 对应的个性化菜单"),
    CODE_65302(65302, "There is no corresponding model.", "没有相应的用户"),
    CODE_65303(65303, "There is no default menu, so a personalized menu cannot be created.", "没有默认菜单，不能创建个性化菜单"),
    CODE_65304(65304, "The MatchRule information is empty.", "MatchRule 信息为空"),
    CODE_65305(65305, "The number of personalized menus is limited.", "个性化菜单数量受限"),
    CODE_65306(65306, "This account does not support personalized menus.", "不支持个性化菜单的账号"),
    CODE_65307(65307, "The personalized menu information is empty.", "个性化菜单信息为空"),
    CODE_65308(65308, "Contains a button without a response type.", "包含没有响应类型的 button"),
    CODE_65309(65309, "The personalized menu switch is turned off.", "个性化菜单开关处于关闭状态"),
    CODE_65310(65310, "If province or city information is filled in, the country information cannot be empty.", "填写了省份或城市信息，国家信息不能为空"),
    CODE_65311(65311, "If city information is filled in, the province information cannot be empty.", "填写了城市信息，省份信息不能为空"),
    CODE_65312(65312, "Invalid country information.", "不合法的国家信息"),
    CODE_65313(65313, "Invalid province information.", "不合法的省份信息"),
    CODE_65314(65314, "Invalid city information.", "不合法的城市信息"),
    CODE_65316(65316, "The public official account has set too many external domain jumps for its menu (a maximum of 3 domain links can be jumped to).", "该公众号的菜单设置了过多的域名外跳（最多跳转到 3 个域名的链接）"),
    CODE_65317(65317, "Invalid URL.", "不合法的 URL"),

    CODE_65400(65400,"API Disabled, New Customer Service not Open or Update","API不可用，即没有开通/升级到新客服"),
    CODE_65401(65401,"Invalid Customer Service Account","无效客服账号"),
    CODE_65403(65403,"Illegal Customer Service Nickname","客服昵称不合法"),
    CODE_65407(65407,"User Invited has been being Customer Service User","邀请对象已经是本公众号客服"),
    CODE_65408(65408,"Invite Msg had send to The User","本公众号已发送邀请给该微信号"),
    CODE_65409(65409,"Invalid WeChat Account","无效的微信号"),
    CODE_65410(65410,"Bind Customer Service No. Limited, Current Number 5/User","邀请对象绑定公众号客服数量达到上线（目前每个微信号最多可以绑定5个公众号客服账号）"),
    CODE_65411(65411,"This account already has an invitation waiting for confirmation and cannot be duplicated","该账号已经有一个等待确认的邀请，不能重复邀请"),
    CODE_65412(65412,"This account has already been linked to WeChat and cannot be invited","该账号已经绑定微信号，不能进行邀请"),


    CODE_87009(87009, "Invalid signature.", "无效的签名"),
    CODE_89501(89501, "This IP is waiting for administrator confirmation. Please contact the administrator.", "此IP正在等待管理员确认，请联系管理员"),
    CODE_89503(89503, "This IP call requires administrator confirmation. Please contact the administrator.", "此IP调用需要管理员确认，请联系管理员"),
    CODE_89504(89504, "Mass messaging is still in the approval process", "群发仍在审批流程中"),
    CODE_89505(89505, "Mass messaging has entered the administrator confirmation process", "群发进入管理员确认流程"),
    CODE_89506(89506, "This IP has been rejected by the administrator twice within 24 hours. It cannot be used for calls within 24 hours.", "24小时内该IP被管理员拒绝调用两次，24小时内不可再使用该IP调用"),
    CODE_89507(89507, "This IP has been rejected by the administrator once within 1 hour. It cannot be used for calls within 1 hour.", "1小时内该IP被管理员拒绝调用一次，1小时内不可再使用该IP调用"),

    CODE_200011(200011, "This account has been banned and cannot be operated", "此账号已被封禁，无法操作"),
    CODE_200012(200012, "The number of private templates has reached the maximum limit of 50", "私有模板数已达上限，上限 50 个"),
    CODE_200013(200013, "This template has been banned and cannot be selected", "此模版已被封禁，无法选用"),
    CODE_200014(200014, "Incorrect template tid parameter", "模版 tid 参数错误"),
    CODE_200016(200016, "Incorrect start parameter", "start 参数错误"),
    CODE_200017(200017, "Incorrect limit parameter", "limit 参数错误"),
    CODE_200018(200018, "Missing category ids", "类目 ids 缺失"),
    CODE_200019(200019, "Invalid category ids", "类目 ids 不合法"),
    CODE_200020(200020, "Incorrect keyword list kidList parameter", "关键词列表 kidList 参数错误"),
    CODE_200021(200021, "Incorrect scene description sceneDesc parameter", "场景描述 sceneDesc 参数错误"),

    CODE_9001001(9001001, "Invalid POST data parameters.", "POST 数据参数不合法"),
    CODE_9001002(9001002, "Remote service is not available.", "远端服务不可用"),
    CODE_9001003(9001003, "Invalid ticket.", "Ticket 不合法"),
    CODE_9001004(9001004, "Failed to obtain shaking peripheral model information.", "获取摇周边用户信息失败"),
    CODE_9001005(9001005, "Failed to obtain merchant information.", "获取商户信息失败"),
    CODE_9001006(9001006, "Failed to obtain OpenID.", "获取 OpenID 失败"),
    CODE_9001007(9001007, "Missing uploaded file.", "上传文件缺失"),
    CODE_9001009(9001009, "The uploaded material's file size is invalid.", "上传素材的文件尺寸不合法"),
    CODE_9001010(9001010, "Upload failed.", "上传失败"),
    CODE_9001020(9001020, "Invalid account.", "账号不合法"),
    CODE_9001021(9001021, "The existing device activation rate is less than 50%, and no new devices can be added.", "已有设备激活率低于 50% ，不能新增设备"),
    CODE_9001022(9001022, "The number of device applications is invalid, it must be a number greater than 0.", "设备申请数不合法，必须为大于 0 的数字"),
    CODE_9001023(9001023, "An existing device ID application under review already exists.", "已存在审核中的设备 ID 申请"),
    CODE_9001024(9001024, "The number of device IDs queried at one time cannot exceed 50.", "一次查询设备 ID 数量不能超过 50"),
    CODE_9001025(9001025, "Invalid device ID.", "设备 ID 不合法"),
    CODE_9001026(9001026, "Invalid page ID.", "页面 ID 不合法"),
    CODE_9001027(9001027, "Invalid page parameters.", "页面参数不合法"),
    CODE_9001028(9001028, "The number of page IDs deleted at one time cannot exceed 10.", "一次删除页面 ID 数量不能超过 10"),
    CODE_9001029(9001029, "The page is already applied to a device. Please remove the application relationship before deleting.", "页面已应用在设备中，请先解除应用关系再删除"),
    CODE_9001030(9001030, "The number of page IDs queried at one time cannot exceed 50.", "一次查询页面 ID 数量不能超过 50"),
    CODE_9001031(9001031, "Invalid time interval.", "时间区间不合法"),
    CODE_9001032(9001032, "Error saving the binding relationship parameters between the device and the page.", "保存设备与页面的绑定关系参数错误"),
    CODE_9001033(9001033, "Invalid store ID.", "门店 ID 不合法"),
    CODE_9001034(9001034, "The device remarks are too long.", "设备备注信息过长"),
    CODE_9001035(9001035, "Invalid device application parameters.", "设备申请参数不合法"),
    CODE_9001036(9001036, "Invalid query start value (begin).", "查询起始值 begin 不合法"),

    UNKNOWN(-999, "Unknown error", "未知错误");

    /**
     * 微信服务器响应业务代码
     */
    private final Integer errcode;
    /**
     * 微信服务器响应业务消息
     */
    private final String errmsg, cnMsg;

    public static WeChatApiResultEnumeration codeOf(int code) {
        return Arrays.stream(values()).filter(item -> Objects.equals(code, item.getErrcode())).findFirst().orElse(UNKNOWN);
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public String getCode() {
        return getErrcode() + "";
    }

    @Override
    public String getMsg() {
        return getCnMsg();
    }
}