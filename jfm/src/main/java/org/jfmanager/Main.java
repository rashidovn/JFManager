package org.jfmanager;

import org.jfmanager.resources.Config;
import org.jfmanager.resources.I18n;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

/**
 * User: kvych
 * Date: 1/5/14
 * Time: 2:55 PM
 */
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) throws Exception {
        start(args);
    }

    public static void start(String... args) throws Exception {

        log.info("Starting JFManager...");

        I18n.init();
        Config.getInstance().init();
        ComponentRegistry.getInstance().setConfig(Config.getInstance());

        final MainFrame mainFrame = new MainFrame();
        mainFrame.setName(ComponentRegistry.MAIN_FRAME_NAME);
        mainFrame.addWindowListener(new ApplicationCloser());
        SwingUtilities.invokeLater(new ApplicationRunner(mainFrame));
    }

}
