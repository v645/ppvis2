package com.company;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.List;

class NewTableModel implements TableModel {

    public List<TableObject> rows;
    public int CurrentPage = 0;
    public int AtOnePage = 20;
    private int currentCount = 0;

    public NewTableModel(List<TableObject> objects) {
        this.rows = objects;
        currentCount = objects.size();
    }

    @Override
    public int getRowCount() {
        return currentCount;
    }

    @Override
    public int getColumnCount() {
        return 12;
    }

    @Override
    public String getColumnName(int i) {
        switch (i) {
            case 0:
                return "name";
            case 1:
                return "group";
            default: break;
        }
        return  "work in "+(i-2)+"th sem";
        //return null;
    }

    @Override
    public Class<?> getColumnClass(int i) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
       if(column>=2) return true;
       return  false;
    }

    @Override
    public Object getValueAt(int row, int column) {
        TableObject obj = rows.get(row);
        switch (column) {
            case 0:
                return obj.getName();
            case 1:
                return obj.getGroup();
            default: return  obj.getWorksInSemestr(column-2);
        }

       //return null;
    }

    @Override
    public void setValueAt(Object o, int row, int column) {
        TableObject obj = rows.get(row);
        switch (column) {
            case 0: break;
            case 1:break;
            default: obj.setWorksInSemestr(Integer.parseInt(o.toString()),column);
        }
    }

    @Override
    public void addTableModelListener(TableModelListener tableModelListener) {

    }

    @Override
    public void removeTableModelListener(TableModelListener tableModelListener) {

    }
}
