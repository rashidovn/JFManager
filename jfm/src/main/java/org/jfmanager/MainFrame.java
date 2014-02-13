package org.jfmanager;

import org.jfmanager.browserpanel.BrowserPanel;
import org.jfmanager.resources.Config;
import org.jfmanager.resources.I18n;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;

/**
 * User: kvych
 * Date: 1/7/14
 * Time: 12:21 PM
 */
public class MainFrame extends JFrame implements IJfmComponent {

    private JPanel rootPanel;
    private JToolBar toolbar;
    private JSplitPane mainSplitPanel;
    private JPanel rootPanelLeft;
    private JPanel rootPanelRight;
    private BrowserPanel browserPanelRight;
    private BrowserPanel browserPanelLeft;

    public MainFrame() throws HeadlessException {
        init();
    }

    private void init() {
        setTitle(MessageFormat.format("{0} - {1}", I18n.get("mainFrame.title"), I18n.get("mainFrame.version")));

        getRootPane().setContentPane(rootPanel);
    }

    @Override
    public void registerComponents() {
        browserPanelLeft.setName(ComponentRegistry.BROWSER_PANEL_LEFT_NAME);
        ComponentRegistry.getInstance().register(this, browserPanelLeft);

        browserPanelRight.setName(ComponentRegistry.BROWSER_PANEL_RIGHT_NAME);
        ComponentRegistry.getInstance().register(this, browserPanelRight);
    }

    public void configure(Config config) {
        int width = config.getInt("mainFrame.width");
        int height = config.getInt("mainFrame.height");

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        int x = "center".equalsIgnoreCase(config.getString("mainFrame.x"))
                ? (screen.width - width) / 2
                : config.getInt("mainFrame.x");

        int y = "center".equalsIgnoreCase(config.getString("mainFrame.y"))
                ? (screen.height - height) / 2
                : config.getInt("mainFrame.y");

        mainSplitPanel.setResizeWeight(
                "center".equalsIgnoreCase(config.getString("mainFrame.mainSplitPanel.resizeWeight"))
                        ? 0.5d
                        : config.getDouble("mainFrame.mainSplitPanel.resizeWeight")
        );

        setBounds(x, y, width, height);

        setExtendedState(config.getInt("mainFrame.windowState"));

        // TODO: extract to config
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void saveConfig(Config config) {
        Rectangle bounds = getBounds();

        config.set("mainFrame.width", bounds.width);
        config.set("mainFrame.height", bounds.height);
        config.set("mainFrame.x", bounds.x);
        config.set("mainFrame.y", bounds.y);

        int leftWidth = mainSplitPanel.getLeftComponent().getWidth();
        int rightWidth = mainSplitPanel.getRightComponent().getWidth();
        config.set("mainFrame.mainSplitPanel.resizeWeight", leftWidth / ((double) leftWidth + rightWidth));

        config.set("mainFrame.windowState", getExtendedState());
    }
}
