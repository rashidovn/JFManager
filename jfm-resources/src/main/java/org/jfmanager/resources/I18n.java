package org.jfmanager.resources;

import java.util.ResourceBundle;

/**
 * User: kvych
 * Date: 1/7/14
 * Time: 2:07 PM
 */
public class I18n {

    private static ResourceBundle bundle;

    private I18n() {
    }

    public static void init() {
        bundle = ResourceBundle.getBundle("i18n.i18n");
    }

    public static String get(String key) {
        return bundle.getString(key);
    }
}
