package org.jfmanager.location;

import javax.swing.*;
import java.util.Vector;

/**
 * User: kvych
 * Date: 1/8/14
 * Time: 12:22 PM
 */
public class LocationComboBoxModel extends DefaultComboBoxModel<Location> {

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
    public void setSelectedItem(Object anObject) {
        Location previousFSR = (Location) getSelectedItem();
        Location newFSR = (Location) anObject;
        super.setSelectedItem(newFSR);
        if (newFSR != null && !newFSR.equals(previousFSR)) {
            fireLocationChanged(this, previousFSR, newFSR);
        }
    }

}
