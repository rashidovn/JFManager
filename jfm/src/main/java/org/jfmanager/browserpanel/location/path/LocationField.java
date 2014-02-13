package org.jfmanager.browserpanel.location.path;

import org.jfmanager.IJfmComponent;
import org.jfmanager.browserpanel.location.Location;
import org.jfmanager.resources.Config;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.file.Paths;

/**
 * User: kvych
 * Date: 1/8/14
 * Time: 11:50 PM
 */
public class LocationField extends JTextField implements KeyListener, IJfmComponent {

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
        addKeyListener(this);
        setDocument(new LocationFieldModel());
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

    @Override
    public void keyTyped(KeyEvent event) {
        // do nothing
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_ENTER) {
            int length = getLocationFieldModel().getLength();
            try {
                String locationText = getLocationFieldModel().getText(0, length);
                Location location = getLocationFieldModel().getLocation();
                location.setPath(Paths.get(locationText));
                getLocationFieldModel().setLocation(location);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        // do nothing
    }

    public LocationFieldModel getLocationFieldModel() {
        return (LocationFieldModel) super.getDocument();
    }
}
