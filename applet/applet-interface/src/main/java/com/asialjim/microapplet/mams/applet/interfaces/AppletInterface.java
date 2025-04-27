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
    @GetMapping("/chl/{chlAppId}")
    ChlApplet getChlAppletByChlAppId(@PathVariable("chlAppId") String chlAppId);
}