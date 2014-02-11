package org.jfmanager;

import org.jfmanager.location.LocationComboBox;
import org.jfmanager.location.LocationComboBoxModel;
import org.jfmanager.location.LocationField;
import org.jfmanager.location.LocationFieldModel;
import org.jfmanager.resources.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

/**
 * User: kvych
 * Date: 1/7/14
 * Time: 10:27 PM
 */
public class BrowserPanel extends JComponent implements IJfmComponent {

    private static final Logger log = LoggerFactory.getLogger(BrowserPanel.class);

    private JTable contentTable;
    private JPanel rootPanel;
    private LocationComboBox locationComboBox;
    private LocationField locationField;

    @Override
    public void registerComponents() {
        locationComboBox.setName(getName() + ComponentRegistry.COMPONENT_NAME_DELIMITER + ComponentRegistry.FILE_SYSTEM_COMBO_BOX_NAME_SUFFIX);
        ComponentRegistry.getInstance().register(this, locationComboBox);

        locationField.setName(getName() + ComponentRegistry.COMPONENT_NAME_DELIMITER + ComponentRegistry.LOCATION_NAME_SUFFIX);
        ComponentRegistry.getInstance().register(this, locationField);

        LocationComboBoxModel locationComboBoxModel = locationComboBox.getLocationComboBoxModel();
        LocationFieldModel locationFieldModel = locationField.getLocationFieldModel();
        locationComboBoxModel.addLocationListener(locationField);

        // init starting location
        locationFieldModel.setLocation(locationComboBoxModel.getSelectedItem());
    }

    @Override
    public void configure(Config config) {

    }

    @Override
    public void saveConfig(Config config) {

    }
}
