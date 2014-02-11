package org.jfmanager.location;

import org.jfmanager.IJfmComponent;
import org.jfmanager.resources.Config;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * User: kvych
 * Date: 1/8/14
 * Time: 12:21 PM
 */
public class LocationComboBox extends JComboBox<Location> implements IJfmComponent {

    private boolean layingOut = false;

    public LocationComboBox(LocationComboBoxModel aModel) {
        super(aModel);
        init();
    }

    public LocationComboBox(Location[] items) {
        super(items);
        init();
    }

    public LocationComboBox(Vector<Location> items) {
        super(items);
        init();
    }

    public LocationComboBox() {
        init();
    }

    private void init() {
        setModel(new LocationComboBoxModel());
    }

    @Override
    public void registerComponents() {
        // do nothing
    }

    @Override
    public void configure(Config config) {
        LocationComboBoxModel model = (LocationComboBoxModel) getModel();
        for (Location location : Locations.getFileSystemRoots()) {
            model.addElement(location);
        }
    }

    @Override
    public void saveConfig(Config config) {

    }

    @Override
    public void doLayout() {
        try {
            layingOut = true;
            super.doLayout();
        } finally {
            layingOut = false;
        }
    }

    /**
     * Solution which allow to keep the combobox size and the popup size different.
     *
     * @return
     */
    @Override
    public Dimension getSize() {
        Dimension dim = super.getSize();
        if (!layingOut)
            dim.width = Math.max(dim.width, getPreferredSize().width);
        return dim;
    }
}