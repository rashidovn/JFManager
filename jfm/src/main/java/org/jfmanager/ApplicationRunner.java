package org.jfmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: kvych
 * Date: 1/7/14
 * Time: 7:08 PM
 */
class ApplicationRunner implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ApplicationRunner.class);

    private final MainFrame mainFrame;

    public ApplicationRunner(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void run() {
        log.info("Registering main frame");
        ComponentRegistry.getInstance().registerMainFrame(mainFrame);

        log.info("Showing main frame");
        mainFrame.setVisible(true);
    }
}
