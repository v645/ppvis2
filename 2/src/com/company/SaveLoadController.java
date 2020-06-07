package com.company;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveLoadController{

public static List<TableObject> Load() {

    JFileChooser jFileChooser = new JFileChooser();
    if (jFileChooser.showDialog(null, "Use this file") == JFileChooser.APPROVE_OPTION) {
        File file = jFileChooser.getSelectedFile();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            XMLDecoder xmlDecoder = new XMLDecoder(fileInputStream);
            Object readObj = xmlDecoder.readObject();
            xmlDecoder.close();
            fileInputStream.close();
            TableInfo newInfo = (TableInfo) readObj;
            return newInfo.getTable();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    return new ArrayList<TableObject>();
}



public static void Save( List<TableObject> tableObjects) {
    JFileChooser jFileChooser = new JFileChooser();
    if (jFileChooser.showDialog(null, "Use this file") == JFileChooser.APPROVE_OPTION) {
        File file = jFileChooser.getSelectedFile();
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file, false);
            XMLEncoder xmlEncoder = new XMLEncoder(fileOutputStream);
            TableInfo tableInfo = new TableInfo();
            tableInfo.setTable(tableObjects);
            xmlEncoder.writeObject(tableInfo);

            xmlEncoder.flush();
            xmlEncoder.close();
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

}
