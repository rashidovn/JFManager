package org.jfmanager;

import org.jfmanager.resources.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

/**
 * User: kvych
 * Date: 1/7/14
 * Time: 7:09 PM
 */
class ApplicationCloser extends WindowAdapter {

    private static final Logger log = LoggerFactory.getLogger(ApplicationCloser.class);

    @Override
    public void windowClosing(WindowEvent e) {
        log.info("Unregistering main frame");
        ComponentRegistry.getInstance().unregisterMainFrame();

        log.info("Saving configuration");
        Config.getInstance().save();
    }

}
