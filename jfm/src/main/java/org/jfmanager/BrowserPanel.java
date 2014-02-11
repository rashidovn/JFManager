package org.jfmanager;

import org.jfmanager.location.LocationComboBox;
import org.jfmanager.resources.Config;

import javax.swing.*;

/**
 * User: kvych
 * Date: 1/7/14
 * Time: 10:27 PM
 */
public class BrowserPanel extends JComponent implements IJfmComponent {

    private JTable contentTable;
    private JPanel rootPanel;
    private LocationComboBox locationComboBox;
    private JTextField pathField;

    @Override
    public void registerComponents() {
        locationComboBox.setName(getName() + ComponentRegistry.COMPONENT_NAME_DELIMITER + ComponentRegistry.FILE_SYSTEM_COMBO_BOX_NAME_SUFFIX);
        ComponentRegistry.getInstance().register(this, locationComboBox);
    }

    @Override
    public void configure(Config config) {

    }

    @Override
    public void saveConfig(Config config) {

    }
}
