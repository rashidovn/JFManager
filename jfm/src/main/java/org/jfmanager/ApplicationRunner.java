package org.jfmanager;

/**
 * User: kvych
 * Date: 1/7/14
 * Time: 7:08 PM
 */
class ApplicationRunner implements Runnable {

    private final MainFrame mainFrame;

    public ApplicationRunner(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void run() {
        mainFrame.setVisible(true);
    }
}
