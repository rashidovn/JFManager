package org.jfmanager;

import java.util.HashMap;
import java.util.Map;

/**
 * User: kvych
 * Date: 1/7/14
 * Time: 12:24 PM
 */
public class ComponentRegistry {

    private static Map<Integer, IJfmContainer> componentsMap = new HashMap<>();

    private static Integer mainFrameKey;

    private ComponentRegistry() {
    }

    public static int register(IJfmContainer frame) {
        int key;
        componentsMap.put(
                key = frame.hashCode(),
                frame
        );
        return key;
    }

    static int registerMainFrame(IJfmContainer frame) {
        componentsMap.put(
                mainFrameKey = frame.hashCode(),
                frame
        );
        return mainFrameKey;
    }

    public static IJfmContainer getComponent(int key) {
        return componentsMap.get(key);
    }

    public static IJfmContainer getMainFrame() {
        return componentsMap.get(mainFrameKey);
    }

    public static Map<Integer, IJfmContainer> getAllFrames() {
        return new HashMap<>(componentsMap);
    }
}
