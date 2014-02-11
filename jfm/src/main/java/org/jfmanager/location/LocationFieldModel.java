package org.jfmanager.location;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.BadLocationException;
import javax.swing.text.GapContent;
import javax.swing.text.PlainDocument;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.file.Paths;

/**
 * User: kvych
 * Date: 1/8/14
 * Time: 11:52 PM
 */
public class LocationFieldModel extends PlainDocument {

    private static final Logger log = LoggerFactory.getLogger(LocationFieldModel.class);

    private Location location;

    public LocationFieldModel() {
    }

    public LocationFieldModel(Content c) {
        super(c);
    }

    public void addLocationListener(LocationListener l) {
        listenerList.add(LocationListener.class, l);
    }

    public void removeLocationListener(LocationListener l) {
        listenerList.remove(LocationListener.class, l);
    }

    public LocationListener[] getLocationListener() {
        return listenerList.getListeners(LocationListener.class);
    }

    protected void fireLocationChanged(Object source, Location newFSR) {
        Object[] listeners = listenerList.getListenerList();
        LocationEvent e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == LocationListener.class) {
                if (e == null) {
                    e = new LocationEvent(source, LocationEvent.LOCATION_CURRENT_CHANGED, null, newFSR);
                }
                ((LocationListener) listeners[i + 1]).locationChanged(e);
            }
        }
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
        try {
            log.info("New location: " + location.getCurrentPath().toString());
            getContent().remove(0, getContent().length());
            getContent().insertString(0, this.location.getCurrentPath().toString());
            fireLocationChanged(this, location);
        } catch (BadLocationException e) {
            // do nothing
        }
    }

}
