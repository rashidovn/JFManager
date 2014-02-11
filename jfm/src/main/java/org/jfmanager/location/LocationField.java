package org.jfmanager.location;

import org.jfmanager.IJfmComponent;
import org.jfmanager.resources.Config;

import javax.swing.*;
import javax.swing.text.Document;

/**
 * User: kvych
 * Date: 1/8/14
 * Time: 11:50 PM
 */
public class LocationField extends JTextField implements IJfmComponent {

    public LocationField() {
        init();
    }

    public LocationField(String text) {
        super(text);
        init();
    }

    public LocationField(int columns) {
        super(columns);
        init();
    }

    public LocationField(String text, int columns) {
        super(text, columns);
        init();
    }

    public LocationField(Document doc, String text, int columns) {
        super(doc, text, columns);
        init();
    }

    private void init() {
        LocationFieldModel model = new LocationFieldModel();
        addKeyListener(model);
        setDocument(model);
    }

    @Override
    public void registerComponents() {

    }

    @Override
    public void configure(Config config) {

    }

    @Override
    public void saveConfig(Config config) {

    }
}
