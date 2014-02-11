package org.jfmanager.location;

import java.util.EventListener;

/**
 * User: kvych
 * Date: 1/8/14
 * Time: 12:53 PM
 */
public interface LocationListener extends EventListener {

    void locationChanged(LocationEvent event);
}
