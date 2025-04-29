package com.asialjim.microapplet.mams.channel.wechat.official.cors.cmd;

import com.asialjim.microapplet.common.application.App;
import com.asialjim.microapplet.common.concurrent.ConcurrentRunner;
import com.asialjim.microapplet.common.cors.Cmd;
import com.asialjim.microapplet.mams.channel.wechat.official.domain.WeChatOfficialAppAgg;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 公众号授权网页连接查询命令
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/29, &nbsp;&nbsp; <em>version:1.0</em>
 */
@Getter
@RequiredArgsConstructor
public class WeChatOfficialPageUrlQryCmd implements Cmd<String> {
    private final String appid;
    private final String scene;
    private final String code;
    private final String state;

    @Setter
    private WeChatOfficialAppAgg weChatOfficialAppAgg;
    @Setter
    private String token;
    @Setter
    private String url;

    public static WeChatOfficialPageUrlQryCmd cmd(String appid, String scene, String code, String state) {
        WeChatOfficialPageUrlQryCmd cmd = new WeChatOfficialPageUrlQryCmd(appid, scene, code, state);
        App.beanOpt(WeChatOfficialAppAgg.class).ifPresent(item -> cmd.setWeChatOfficialAppAgg(item.withAppid(appid)));
        return cmd;
    }

    @Override
    public String execute() {
        // 并行执行用户登录与跳转链接获取，执行完成之后，执行重定向
        ConcurrentRunner.runAllTask(
                // 用户登录,获取登录令牌
                () -> setToken("token=" + weChatOfficialAppAgg.pageLogin(code)),
                // 执行业务场景任务，获取跳转连接
                () -> setUrl(weChatOfficialAppAgg.page(scene,state))
        );
        String url = getUrl();
        StringBuilder stringBuilder = new StringBuilder(url);
        char tokenizer = StringUtils.contains(url,"?") ? '&' : '?';
        stringBuilder.append(tokenizer).append(getToken());
        return "redirect:" + stringBuilder;
    }
}