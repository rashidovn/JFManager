package org.jfmanager.browserpanel;

import org.jfmanager.ComponentRegistry;
import org.jfmanager.IJfmComponent;
import org.jfmanager.browserpanel.filetable.FileTable;
import org.jfmanager.browserpanel.filetable.FileTableModel;
import org.jfmanager.browserpanel.location.root.RootComboBox;
import org.jfmanager.browserpanel.location.root.RootComboBoxModel;
import org.jfmanager.browserpanel.location.path.LocationField;
import org.jfmanager.browserpanel.location.path.LocationFieldModel;
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

    private JPanel rootPanel;
    private RootComboBox locationComboBox;
    private LocationField locationField;
    private FileTable fileTable;

    @Override
    public void registerComponents() {
        locationComboBox.setName(getName() + ComponentRegistry.COMPONENT_NAME_DELIMITER + ComponentRegistry.FILE_SYSTEM_COMBO_BOX_NAME_SUFFIX);
        ComponentRegistry.getInstance().register(this, locationComboBox);

        locationField.setName(getName() + ComponentRegistry.COMPONENT_NAME_DELIMITER + ComponentRegistry.LOCATION_NAME_SUFFIX);
        ComponentRegistry.getInstance().register(this, locationField);

        fileTable.setName(getName() + ComponentRegistry.COMPONENT_NAME_DELIMITER + ComponentRegistry.FILE_TABLE_NAME_SUFFIX);
        ComponentRegistry.getInstance().register(this, fileTable);

        RootComboBoxModel locationComboBoxModel = locationComboBox.getLocationComboBoxModel();
        LocationFieldModel locationFieldModel = locationField.getLocationFieldModel();
        FileTableModel fileTableModel = fileTable.getFileTableModel();

        locationComboBoxModel.addLocationListener(locationFieldModel);
        locationFieldModel.addLocationListener(fileTableModel);

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
