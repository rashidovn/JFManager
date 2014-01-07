package org.jfmanager;

import org.jfmanager.resources.Config;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

/**
 * User: kvych
 * Date: 1/7/14
 * Time: 7:09 PM
 */
class ApplicationCloser extends WindowAdapter {

    @Override
    public void windowClosing(WindowEvent e) {
        for (Map.Entry<Integer, IJfmContainer> frameEntry : ComponentRegistry.getAllFrames().entrySet()) {
            frameEntry.getValue().saveConfig(Config.getInstance());
        }
        Config.getInstance().save();
    }

}
