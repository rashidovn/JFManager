package org.jfmanager.browserpanel.location.root;

import org.jfmanager.IJfmComponent;
import org.jfmanager.browserpanel.location.Location;
import org.jfmanager.browserpanel.location.util.Locations;
import org.jfmanager.resources.Config;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * User: kvych
 * Date: 1/8/14
 * Time: 12:21 PM
 */
public class RootComboBox extends JComboBox<Location> implements IJfmComponent {

    private boolean layingOut = false;

    public RootComboBox(RootComboBoxModel aModel) {
        super(aModel);
        init();
    }

    public RootComboBox(Location[] items) {
        super(items);
        init();
    }

    public RootComboBox(Vector<Location> items) {
        super(items);
        init();
    }

    public RootComboBox() {
        init();
    }

    private void init() {
        setModel(new RootComboBoxModel());
    }

    @Override
    public void registerComponents() {
        // do nothing
    }

    @Override
    public void configure(Config config) {
        RootComboBoxModel model = (RootComboBoxModel) getModel();
        for (Location location : Locations.getFileSystemRoots()) {
            model.addElement(location);
        }
    }

    @Override
    public void saveConfig(Config config) {

    }

    public RootComboBoxModel getLocationComboBoxModel() {
        return (RootComboBoxModel) dataModel;
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