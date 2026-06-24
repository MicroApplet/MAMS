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

package com.asialjim.microapplet.user.event;

import com.asialjim.microapplet.app.entity.web.AppVo;
import com.asialjim.microapplet.session.LoginReqVo;
import com.asialjim.microapplet.session.Session;
import org.springframework.context.ApplicationEvent;

public class ChlUserLoginEvent extends ApplicationEvent {


    private final LoginReqVo req;
    private final AppVo appVo;
    public ChlUserLoginEvent(Session source, LoginReqVo req, AppVo appVo) {
        super(source);
        this.req = req;
        this.appVo = appVo;
    }

    public Session session(){
        return (Session) this.source;
    }

    public LoginReqVo req(){
        return this.req;
    }

    public AppVo app(){
        return this.appVo;
    }
}
