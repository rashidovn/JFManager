package org.jfmanager.filetable;

import org.jfmanager.location.Location;
import org.jfmanager.location.LocationEvent;
import org.jfmanager.location.LocationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * User: kvych
 * Date: 2/12/14
 * Time: 8:07 AM
 */
public class FileTableModel extends DefaultTableModel implements LocationListener {

    private static final Logger log = LoggerFactory.getLogger(FileTableModel.class);

    @Override
    public void locationChanged(LocationEvent event) {
        Location location = event.getNewLocation();
        Path currentPath = location.getRoot().resolve(location.getPath());
        log.info("Current location: " + currentPath.toString());
        List<VirtualFile> virtualFiles = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(currentPath)) {
            for (Path path : directoryStream) {
                virtualFiles.add(new VirtualFile(path));
            }
        } catch (IOException e) {
            log.error("Exception while reading directory.", e);
        }
        setVirtualFiles(virtualFiles);
    }

    private void setVirtualFiles(List<VirtualFile> virtualFiles) {
        Vector<Vector<String>> data = new Vector<>();
        Vector<String> identifiers = new Vector<>();
        identifiers.add(ColumnNames.NAME);
        identifiers.add(ColumnNames.SIZE);

        for (VirtualFile virtualFile : virtualFiles) {
            data.add(virtualFile.toVector(identifiers));
        }

        setDataVector(data, identifiers);
    }
}
