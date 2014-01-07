package org.jfmanager;

import org.jfmanager.resources.Config;

/**
 * User: kvych
 * Date: 1/7/14
 * Time: 6:20 PM
 */
public interface IJfmContainer {

    void configure(Config config);

    void saveConfig(Config config);

}
