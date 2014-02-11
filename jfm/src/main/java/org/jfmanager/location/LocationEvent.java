package org.jfmanager.location;

import java.awt.*;

/**
 * User: kvych
 * Date: 1/8/14
 * Time: 12:54 PM
 */
public class LocationEvent extends AWTEvent {

    public static final int LOCATION_ROOT_CHANGED = 0;
    public static final int LOCATION_CURRENT_CHANGED = 1;

    private Location oldLocation;

    private Location newLocation;

    public LocationEvent(Event event) {
        super(event);
    }

    public LocationEvent(Object source, int id) {
        super(source, id);
    }

    public LocationEvent(Object source, int id, Location oldLocation, Location newLocation) {
        this(source, id);
        this.oldLocation = oldLocation;
        this.newLocation = newLocation;
    }

    public Location getNewLocation() {
        return newLocation;
    }

    public Location getOldLocation() {
        return oldLocation;
    }

    @Override
    public String paramString() {
        String typeStr;
        switch (id) {
            case LOCATION_ROOT_CHANGED:
                typeStr = "LOCATION_ROOT_CHANGED";
                break;
            case LOCATION_CURRENT_CHANGED:
                typeStr = "LOCATION_CURRENT_CHANGED";
                break;
            default:
                typeStr = "unknown type";
        }
        return typeStr;
    }
}
