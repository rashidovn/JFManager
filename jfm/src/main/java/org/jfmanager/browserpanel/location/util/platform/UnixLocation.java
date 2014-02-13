package org.jfmanager.browserpanel.location.util.platform;

import org.apache.commons.exec.*;
import org.apache.commons.lang3.StringUtils;
import org.jfmanager.JfmException;
import org.jfmanager.browserpanel.location.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * User: kvych
 * Date: 1/8/14
 * Time: 8:18 PM
 */
public class UnixLocation extends Location {

    private static final Logger log = LoggerFactory.getLogger(UnixLocation.class);

    private static final String EXECUTABLE = "mount";

    private static final String DEVICE_KEY = "device";
    private static final String PATH_KEY = "path";
    private static final String TYPE_KEY = "type";
    private static final String ATTR_KEY = "attr";

    private Map<String, String> attr = new HashMap<>();

    private String device = StringUtils.EMPTY;

    private String type = StringUtils.EMPTY;

    public Map<String, String> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static Location[] getFileSystemRoots() {
        Location[] locations;
        List<Location> roots = new ArrayList<>();
        try {
            List<Map<String, String>> mounts = runMount();
            for (Map<String, String> rootMap : mounts) {
                UnixLocation fileSystemRoot = new UnixLocation();
                fileSystemRoot.setRoot(Paths.get(rootMap.get(PATH_KEY)));
                fileSystemRoot.setDevice(rootMap.get(DEVICE_KEY));
                fileSystemRoot.setType(rootMap.get(TYPE_KEY));
                for (String attr : rootMap.get(ATTR_KEY).split(",")) {
                    String[] keyValue = attr.split("=");
                    fileSystemRoot.getAttr().put(
                            keyValue[0],
                            keyValue.length > 1 ? keyValue[1] : null
                    );
                }
                roots.add(fileSystemRoot);
            }
        } catch (IOException | JfmException e) {
            log.warn("Exception while reading mount points", e);
            for (Path root : FileSystems.getDefault().getRootDirectories()) {
                UnixLocation fileSystemRoot = new UnixLocation();
                fileSystemRoot.setRoot(root);
                Iterator<FileStore> fileStoreIterator = root.getFileSystem().getFileStores().iterator();
                if (fileStoreIterator.hasNext()) {
                    FileStore fileStore = fileStoreIterator.next();
                    fileSystemRoot.setDevice(fileStore.name());
                    fileSystemRoot.setType(fileStore.type());
                }
                roots.add(fileSystemRoot);
            }
        }
        locations = roots.toArray(new Location[roots.size()]);
        return locations;
    }

    private static List<Map<String, String>> runMount() throws IOException {
        List<Map<String, String>> result = new ArrayList<>();

        CommandLine cmdLine = new CommandLine(EXECUTABLE);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        ExecuteStreamHandler streamHandler = new PumpStreamHandler(out, err);
        ExecuteWatchdog watchdog = new ExecuteWatchdog(5 * 1000);
        Executor executor = new DefaultExecutor();
        executor.setStreamHandler(streamHandler);
        executor.setWatchdog(watchdog);

        if (executor.execute(cmdLine) == 0) {
            Scanner scanner = new Scanner(out.toString());
            while (scanner.hasNextLine()) {

                // output format:
                // <DEVICE> on <PATH> type <TYPE> (<ATTR>)
                String line = scanner.nextLine();

                int index_on_ = line.indexOf(" on ");
                int index_type_ = line.indexOf(" type ", index_on_);
                int index_left_bracket = line.indexOf(" (", index_type_);
                int index_right_bracket = line.lastIndexOf(")");

                Map<String, String> data = new HashMap<>();
                data.put(DEVICE_KEY, line.substring(0, index_on_));
                data.put(PATH_KEY, line.substring(index_on_ + 4, index_type_));
                data.put(TYPE_KEY, line.substring(index_type_ + 6, index_left_bracket));
                data.put(ATTR_KEY, line.substring(index_left_bracket + 2, index_right_bracket));
                result.add(data);
            }
        } else {
            throw new JfmException("Fail run '" + EXECUTABLE + "': " + err.toString());
        }

        return result;
    }
}
