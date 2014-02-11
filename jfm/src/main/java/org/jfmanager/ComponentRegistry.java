package org.jfmanager;

import org.apache.commons.lang3.StringUtils;
import org.jfmanager.resources.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * User: kvych
 * Date: 1/7/14
 * Time: 12:24 PM
 */
public class ComponentRegistry implements PropertyChangeListener {

    private static final Logger log = LoggerFactory.getLogger(ComponentRegistry.class);

    public static final String MAIN_FRAME_NAME = "jfmMainFrame";

    public static final String COMPONENT_NAME_DELIMITER = ".";

    public static final String BROWSER_PANEL_LEFT_NAME = "browserPanelLeft";
    public static final String BROWSER_PANEL_RIGHT_NAME = "browserPanelRight";

    public static final String FILE_SYSTEM_COMBO_BOX_NAME_SUFFIX = "fileSystemRoot";

    private static final ComponentRegistry INSTANCE = new ComponentRegistry();

    private Map<Class<? extends IJfmComponent>, Map<String, Entry>> componentsMap = new HashMap<>();

    private Class<? extends IJfmComponent> mainFrameClass;

    private String mainFrameName;

    private Config config;

    private ComponentRegistry() {
    }

    public static ComponentRegistry getInstance() {
        return INSTANCE;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public void register(IJfmComponent parent, IJfmComponent component) {
        validate(parent);
        validate(component);

        Entry parentEntry = getComponentEntry(parent.getName(), parent.getClass());
        Entry componentEntry = new Entry(parentEntry, component);
        parentEntry.addChild(componentEntry);

        Map<String, Entry> map = componentsMap.get(component.getClass());
        if (map == null) {
            map = new HashMap<>();
            componentsMap.put(
                    component.getClass(),
                    map
            );
        }
        map.put(component.getName(), componentEntry);

        component.addPropertyChangeListener("name", this);

        component.registerComponents();
        component.configure(config);

        log.info("Component '" + component.getName() + "' registered");
    }

    public void unregister(IJfmComponent component) {
        validate(component);

        Entry componentEntry = getComponentEntry(component.getName(), component.getClass());
        if (componentEntry.hasParent()) {
            componentEntry.getParent().removeChild(componentEntry);
            componentEntry.unsetParent();
        }
        if (componentsMap.get(component.getClass()).isEmpty()) {
            componentsMap.remove(component.getClass());
        }

        for (Entry children : componentEntry.getChildren()) {
            unregister(children.getComponent());
        }

        component.removePropertyChangeListener("name", this);

        component.saveConfig(config);

        log.info("Component '" + component.getName() + "' unregistered");
    }

    void registerMainFrame(IJfmComponent mainFrame) {
        validate(mainFrame);

        Entry mainEntry = new Entry(mainFrame);
        Map<String, Entry> map = new HashMap<>();
        map.put(
                mainFrameName = mainFrame.getName(),
                mainEntry
        );
        // TODO: need to add check if mainFrame already exists
        componentsMap.put(
                mainFrameClass = mainFrame.getClass(),
                map
        );

        mainFrame.addPropertyChangeListener("name", this);

        mainFrame.registerComponents();
        mainFrame.configure(config);

        log.info("Main frame '" + mainFrameName + "' registered");
    }

    void unregisterMainFrame() {
        unregister(getMainFrame());
    }

    public IJfmComponent getMainFrame() {
        return getComponent(mainFrameName, mainFrameClass);
    }

    public IJfmComponent getComponent(String name, Class<? extends IJfmComponent> clazz) {
        return getComponentEntry(name, clazz).getComponent();
    }

    private Entry getComponentEntry(String name, Class<? extends IJfmComponent> clazz) {
        Entry entry;
        try {
            entry = componentsMap.get(clazz).get(name);
            if (entry == null) {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            throw new JfmException("Component with name '" + name + "' is not registered!", e);
        }
        return entry;
    }

    private void validate(IJfmComponent component) {
        if (component == null) throw new JfmException("Component is null!");
        if (StringUtils.isBlank(component.getName())) throw new JfmException("Component name is blank!");
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        String oldName = (String) event.getOldValue();
        String newName = (String) event.getNewValue();
        IJfmComponent component = (IJfmComponent) event.getSource();

        if (component.getClass() == mainFrameClass && mainFrameName.equals(oldName)) {
            mainFrameName = newName;
        }

        componentsMap.get(component.getClass()).put(
                newName,
                componentsMap.get(component.getClass()).remove(oldName)
        );

        log.info("Component '" + oldName + "' renamed to '" + newName + "' registered");
    }

    private static class Entry {

        private Entry parent;

        private IJfmComponent component;

        private Map<String, Entry> children = new HashMap<>();

        public Entry(IJfmComponent component) {
            this.component = component;
        }

        public Entry(Entry parent, IJfmComponent component) {
            this.parent = parent;
            this.component = component;
        }

        public boolean hasParent() {
            return parent != null;
        }

        public Entry getParent() {
            return parent;
        }

        public void unsetParent() {
            parent = null;
        }

        public String getName() {
            return component.getName();
        }

        public IJfmComponent getComponent() {
            return component;
        }

        public Collection<Entry> getChildren() {
            return new ArrayList<>(children.values());
        }

        public void addChild(Entry child) {
            children.put(child.getName(), child);
        }

        public void removeChild(Entry child) {
            children.remove(child.getName());
        }
    }
}
