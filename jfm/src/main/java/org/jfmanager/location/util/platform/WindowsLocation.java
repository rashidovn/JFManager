package org.jfmanager.location.util.platform;

import org.jfmanager.JfmException;
import org.jfmanager.location.Location;

/**
 * User: kvych
 * Date: 1/8/14
 * Time: 8:18 PM
 */
public class WindowsLocation extends Location {

    public static Location[] getFileSystemRoots() {
        throw new JfmException("Windows file system not implemented yet");
    }
}
