package org.jfmanager.filetable;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.Vector;

/**
 * User: kvych
 * Date: 2/12/14
 * Time: 9:10 AM
 */
public class VirtualFile {

    private Type type;

    private File file;

    private Path path;

    public VirtualFile(File file) {
        setFile(file);
    }

    public VirtualFile(Path path) {
        setPath(path);
    }

    public File getFile() {
        switch (type) {
            case FILE:
                return file;
            case PATH:
                return path.toFile();
            default:
                return null;
        }
    }

    private void setFile(File file) {
        this.file = file;
        this.type = Type.FILE;
    }

    public Path getPath() {
        switch (type) {
            case FILE:
                return file.toPath();
            case PATH:
                return path;
            default:
                return null;
        }
    }

    private void setPath(Path path) {
        this.path = path;
        this.type = Type.PATH;
    }

    public Vector<String> toVector(Vector<String> identifiers) {
        Vector<String> vector = new Vector<>();
        for (String identifier : identifiers) {
            File file = getFile();
            switch (identifier) {
                case ColumnNames.NAME:
                    vector.add(file.getName());
                    break;
                case ColumnNames.SIZE:
                    if (file.isDirectory()) {
                        vector.add("DIR");
                    } else {
                        vector.add(String.valueOf(file.length()));
                    }
                    break;
                default:
                    vector.add(StringUtils.EMPTY);
                    break;
            }
        }
        return vector;
    }

    public static enum Type {
        FILE,
        PATH
    }
}
