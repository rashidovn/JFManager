package org.jfmanager.browserpanel.location.util;

import org.apache.commons.exec.OS;
import org.apache.commons.lang3.SystemUtils;
import org.jfmanager.JfmException;
import org.jfmanager.browserpanel.location.Location;
import org.jfmanager.browserpanel.location.util.platform.UnixLocation;
import org.jfmanager.browserpanel.location.util.platform.WindowsLocation;

import java.text.MessageFormat;

/**
 * User: kvych
 * Date: 1/8/14
 * Time: 8:24 PM
 */
public class Locations {

    private Locations() {
    }

    public static Location[] getFileSystemRoots() {
        if (OS.isFamilyUnix()) {
            return UnixLocation.getFileSystemRoots();
        } else if (OS.isFamilyWindows()) {
            return WindowsLocation.getFileSystemRoots();
        } else {
            throw new JfmException(MessageFormat.format("Unsupported OS: {0} {1} {2}", SystemUtils.OS_NAME, SystemUtils.OS_VERSION, SystemUtils.OS_ARCH));
        }
    }

}
