package org.jfmanager;

import org.jfmanager.resources.Config;

import java.beans.PropertyChangeListener;

/**
 * User: kvych
 * Date: 1/7/14
 * Time: 6:20 PM
 */
public interface IJfmComponent {

    void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

    void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);

    void setName(String name);

    String getName();

    void registerComponents();

    void configure(Config config);

    void saveConfig(Config config);

}
