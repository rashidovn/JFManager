package org.jfmanager.location.root;

import org.jfmanager.location.Location;
import org.jfmanager.location.LocationEvent;
import org.jfmanager.location.LocationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.Vector;

/**
 * User: kvych
 * Date: 1/8/14
 * Time: 12:22 PM
 */
public class LocationComboBoxModel extends DefaultComboBoxModel<Location> {

    private static final Logger log = LoggerFactory.getLogger(LocationComboBoxModel.class);

    public LocationComboBoxModel() {
    }

    public LocationComboBoxModel(Location[] items) {
        super(items);
    }

    public LocationComboBoxModel(Vector<Location> v) {
        super(v);
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

    protected void fireLocationChanged(Object source, Location oldFSR, Location newFSR) {
        Object[] listeners = listenerList.getListenerList();
        LocationEvent e = null;

        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == LocationListener.class) {
                if (e == null) {
                    e = new LocationEvent(source, LocationEvent.LOCATION_ROOT_CHANGED, oldFSR, newFSR);
                }
                ((LocationListener) listeners[i + 1]).locationChanged(e);
            }
        }
    }

    @Override
    public void setSelectedItem(Object item) {
        Location previousFSR = getSelectedItem();
        Location newFSR = (Location) item;
        super.setSelectedItem(newFSR);
        if (newFSR != null && !newFSR.equals(previousFSR)) {
            log.info("New root: " + newFSR.getRoot().toString());
            fireLocationChanged(this, previousFSR, newFSR);
        }
    }

    @Override
    public Location getSelectedItem() {
        return (Location) super.getSelectedItem();
    }
}
