package com.asialjim.microapplet.mams.applet.infrastructure.adaptor;

import com.asialjim.microapplet.mams.applet.cons.AppletConstant;
import com.asialjim.microapplet.mams.applet.interfaces.AppletInterface;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * 小微应用云服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/4/27, &nbsp;&nbsp; <em>version:1.0</em>
 */
@FeignClient(value = AppletConstant.appName, path = AppletConstant.contextPath + AppletInterface.PATH)
public interface AppletCloud extends AppletInterface {
}