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

        Config.getInstance().init();
        I18n.init();

        final MainFrame mainFrame = new MainFrame();
        mainFrame.configure(Config.getInstance());
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.addWindowListener(new ApplicationCloser());

        ComponentRegistry.registerMainFrame(mainFrame);

        SwingUtilities.invokeLater(new ApplicationRunner(mainFrame));
    }

}
