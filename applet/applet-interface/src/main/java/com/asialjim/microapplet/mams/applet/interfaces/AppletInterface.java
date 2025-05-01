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

package com.asialjim.microapplet.mams.applet.interfaces;

import com.asialjim.microapplet.mams.applet.pojo.Applet;
import com.asialjim.microapplet.mams.applet.pojo.ChlApplet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface AppletInterface {
    String PATH = "/applet";

    /**
     * Fetches an Applet by its ID.
     *
     * @param id the Applet ID
     * @return the Applet or null if not found
     */
    @GetMapping("/{id}")
    Applet getAppletById(@PathVariable("id") String id);

    /**
     * Fetches a ChlApplet by its channel App ID.
     *
     * @param chlAppId the channel App ID
     * @return the ChlApplet or null if not found
     */
    @GetMapping("/{id}/chl/{chlAppId}")
    ChlApplet getChlAppletByChlAppId(@PathVariable("id") String id, @PathVariable("chlAppId") String chlAppId);
}