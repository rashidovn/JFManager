package org.jfmanager.browserpanel.location.path;

import org.jfmanager.browserpanel.location.Location;
import org.jfmanager.browserpanel.location.LocationEvent;
import org.jfmanager.browserpanel.location.LocationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * User: kvych
 * Date: 1/8/14
 * Time: 11:52 PM
 */
public class LocationFieldModel extends PlainDocument implements LocationListener {

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
                    e = new LocationEvent(source, LocationEvent.LOCATION_PATH_CHANGED, null, newFSR);
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
            log.info("New location: " + location.getPath().toString());
            int length = getLength();
            if (length > 0) {
                remove(0, length);
            }
            insertString(0, this.location.getPath().toString(), null);
            fireLocationChanged(this, location);
        } catch (BadLocationException e) {
            log.error("Exception while changing location.", e);
        }
    }

    @Override
    public void locationChanged(LocationEvent event) {
        int length = getLength();
        Location newLocation = event.getNewLocation();

        try {
            remove(0, length);
            insertString(0, newLocation.getPath().toString(), null);
        } catch (BadLocationException e) {
            log.error("Exception in LocationListener.locationChanged(...).", e);
        }

        setLocation(newLocation);
    }

}
