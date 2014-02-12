package org.jfmanager.location;

import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * User: kvych
 * Date: 1/8/14
 * Time: 12:52 PM
 */
public class Location {

    private Path root;

    private Path path = Paths.get(StringUtils.EMPTY);

    private String label = StringUtils.EMPTY;

    public Path getRoot() {
        return root;
    }

    public void setRoot(Path root) {
        this.root = root;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return root.toString();
    }
}
