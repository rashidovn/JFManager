package org.jfmanager.filetable;

import org.jfmanager.IJfmComponent;
import org.jfmanager.resources.Config;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.util.Vector;

/**
 * User: kvych
 * Date: 2/12/14
 * Time: 8:06 AM
 */
public class FileTable extends JTable implements IJfmComponent {

    public FileTable() {
        init();
    }

    public FileTable(TableModel dm) {
        super(dm);
        init();
    }

    public FileTable(TableModel dm, TableColumnModel cm) {
        super(dm, cm);
        init();
    }

    public FileTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
        super(dm, cm, sm);
        init();
    }

    public FileTable(int numRows, int numColumns) {
        super(numRows, numColumns);
        init();
    }

    public FileTable(Vector rowData, Vector columnNames) {
        super(rowData, columnNames);
        init();
    }

    public FileTable(Object[][] rowData, Object[] columnNames) {
        super(rowData, columnNames);
        init();
    }

    private void init() {
        setModel(new FileTableModel());
    }

    public FileTableModel getFileTableModel() {
        return (FileTableModel) dataModel;
    }

    @Override
    public void registerComponents() {

    }

    @Override
    public void configure(Config config) {

    }

    @Override
    public void saveConfig(Config config) {

    }
}
