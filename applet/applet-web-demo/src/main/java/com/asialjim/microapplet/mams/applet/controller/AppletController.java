package com.asialjim.microapplet.mams.applet.controller;

import com.asialjim.microapplet.mams.applet.application.AppletAppService;
import com.asialjim.microapplet.mams.applet.pojo.Applet;
import com.asialjim.microapplet.mams.applet.pojo.ChlApplet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;

/**
 * REST controller for Applet operations.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/applet")
public class AppletController {

    private final AppletAppService appletAppService;

    /**
     * Fetches an Applet by its ID.
     *
     * @param id the Applet ID
     * @return the Applet or null if not found
     */
    @GetMapping("/{id}")
    public Applet getAppletById(@PathVariable("id") String id) {
        return appletAppService.getAppletById(id);
    }

    /**
     * Fetches a ChlApplet by its channel App ID.
     *
     * @param chlAppId the channel App ID
     * @return the ChlApplet or null if not found
     */
    @GetMapping("/chl/{chlAppId}")
    public ChlApplet getChlAppletByChlAppId(@PathVariable("chlAppId") String chlAppId) {
        return appletAppService.getChlAppletByChlAppId(chlAppId);
    }
}