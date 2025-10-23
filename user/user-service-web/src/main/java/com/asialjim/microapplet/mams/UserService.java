package com.asialjim.microapplet.mams;

import com.asialjim.microapplet.common.application.App;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 用户服务
 *
 * @author <a href="mailto:asialjim@hotmail.com">Asial Jim</a>
 * @version 1.0
 * @since 2025/10/23, &nbsp;&nbsp; <em>version:1.0</em>
 */
@SpringBootApplication
public class UserService {
    public static void main(String[] args) {
        App.voidStart(UserService.class,args);
    }
}