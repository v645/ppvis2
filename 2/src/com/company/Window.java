package com.company;

import javax.swing.*;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static com.company.SaveLoadController.Load;
import static com.company.SaveLoadController.Save;

class Window{
    public int CurrentPage = 0;
    public int ObjectsAtPage = 5;
    public JTable table;

    public List<TableObject> tableObjects,foundObjs ;

    public  void CreateInitWindow(List<TableObject> initTableObjects) {
        tableObjects=initTableObjects;
        JFrame frame = new JFrame("Main frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        table = new JTable(setTableRange(tableObjects));
        table.setAutoscrolls(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setCellSelectionEnabled(true);

        JPanel panel = new JPanel();

        JTextField jTextField = new JTextField("f");
        JButton button1 = new JButton("Add");
        JButton button2 = new JButton("Load");
        JButton button3 = new JButton("Save");
       // JButton button4 = new JButton("Refresh");
        JButton button5 = new JButton("Find");
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
       // panel.add(button4);
        panel.add(button5);

        button1.addActionListener(e -> {
            AddElementInterface();
            table.updateUI();
        });
        button2.addActionListener(e -> {
            tableObjects = Load();
            table.updateUI();

        });
        button3.addActionListener(e -> {
            Save(tableObjects);

        });
//        button4.addActionListener(e -> {
//            table.updateUI();
//        });

        button5.addActionListener(e -> {
            FindObjects();
        });


        JPanel pageButtonsPanel = setControlPanelForPages(tableObjects, table);

        frame.getContentPane().add(BorderLayout.NORTH, panel);
        frame.getContentPane().add(BorderLayout.CENTER, table);
        frame.getContentPane().add(BorderLayout.PAGE_END, pageButtonsPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, jTextField);

        frame.setVisible(true);
    }

    public void AddElementInterface() {
        JFrame frame = new JFrame("Add items");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(400, 400);
        JPanel panel = new JPanel();

        JTextField jTextField = new JTextField("name");
        JButton button1 = new JButton("Add");
        JSlider slider1 = new JSlider();
        slider1.setMinimum(0);  slider1.setMaximum(99);
        panel.add(slider1);
        panel.add(jTextField);

        button1.addActionListener(e -> {
            TableObject newObj = new TableObject();
            newObj.setName(jTextField.getText());
            newObj.setGroup(slider1.getValue());
            tableObjects.add(newObj);
            table.setModel(setTableRange(tableObjects));
            frame.setVisible(false);
        });


        frame.getContentPane().add(BorderLayout.NORTH, panel);
        //frame.getContentPane().add(BorderLayout.CENTER, table);
        frame.getContentPane().add(BorderLayout.SOUTH, button1);

        frame.setVisible(true);
    }


    public  NewTableModel setTableRange(List<TableObject> tableObjects) {
        return (new NewTableModel(getTableObjectsRange(tableObjects)));
    }

    public  List<TableObject> getTableObjectsRange(List<TableObject> tableObjects) {
        int firstIndex = CurrentPage * ObjectsAtPage;
        int seconlIndex = Math.min(tableObjects.size(), (CurrentPage + 1) * (ObjectsAtPage));
        List<TableObject> sublist = new ArrayList<>(tableObjects.subList(firstIndex, seconlIndex));
        System.out.println(sublist.size() + " " + firstIndex + " : " + seconlIndex);
        return sublist;

        //(CurrentPage+1)*ObjectsAtPage)

    }

    public  JPanel setControlPanelForPages(List<TableObject> tableObjects, JTable table) {
        //int maxPage=tableObjects.size()/ObjectsAtPage;
        JPanel pageButtonsPanel = new JPanel();
        JButton pageButton1 = new JButton("<<");
        JButton pageButton2 = new JButton("<");
        JButton pageButton3 = new JButton("0/" + tableObjects.size() / ObjectsAtPage + ":" + ObjectsAtPage);
        JButton pageButton4 = new JButton(">");
        JButton pageButton5 = new JButton(">>");
        JButton pageButton6add = new JButton("+");
        JButton pageButton6dec = new JButton("-");
        pageButtonsPanel.add(pageButton1);
        pageButton1.addActionListener(e -> {
            CurrentPage = 0;
            pageButton3.setText(CurrentPage + "/" + tableObjects.size() / ObjectsAtPage);
            table.setModel(setTableRange(tableObjects));
        });
        pageButtonsPanel.add(pageButton2);
        pageButton2.addActionListener(e -> {
            if (CurrentPage > 0) CurrentPage--;
            pageButton3.setText(CurrentPage + "/" + tableObjects.size() / ObjectsAtPage);
            table.setModel(setTableRange(tableObjects));
        });
        pageButtonsPanel.add(pageButton3);
        pageButton3.addActionListener(e -> {
            pageButton3.setText(CurrentPage + "/" + tableObjects.size() / ObjectsAtPage);
            table.setModel(setTableRange(tableObjects));
        });
        pageButtonsPanel.add(pageButton4);
        pageButton4.addActionListener(e -> {
            if (CurrentPage < tableObjects.size() / ObjectsAtPage) CurrentPage++;
            pageButton3.setText(CurrentPage + "/" + tableObjects.size() / ObjectsAtPage);
            table.setModel(setTableRange(tableObjects));
        });
        pageButtonsPanel.add(pageButton5);
        pageButton5.addActionListener(e -> {
            CurrentPage = tableObjects.size() / ObjectsAtPage;
            pageButton3.setText(CurrentPage + "/" + tableObjects.size() / ObjectsAtPage);
            table.setModel(setTableRange(tableObjects));
        });
        pageButtonsPanel.add(pageButton6dec);
        pageButton6add.addActionListener(e -> {
            if (ObjectsAtPage < tableObjects.size()) ObjectsAtPage++;

            table.setModel(setTableRange(tableObjects));
        });
        pageButtonsPanel.add(pageButton6add);
        pageButton6dec.addActionListener(e -> {
            if (ObjectsAtPage > 1) {
                ObjectsAtPage--;
                if(tableObjects.size()<CurrentPage*ObjectsAtPage)CurrentPage--;
                table.setModel(setTableRange(tableObjects));
            }
        });

        return pageButtonsPanel;
    }

    public List<TableObject> FindObjects() {
        JFrame frame = new JFrame("Find");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(900, 400);


        JTextField jTextField = new JTextField("name");
        JButton button1 = new JButton("Add");
        JComboBox<Integer> jComboBox1 = new JComboBox<>();
        jComboBox1.setModel(new DefaultComboBoxModel(getAllGroups(tableObjects).toArray()));

        JPanel sliderPanel = new JPanel();
        JSlider minSlider= new JSlider();
        minSlider.addChangeListener(e -> {

        });

        minSlider.setMaximum(getMaxGroupNumber(tableObjects));
        minSlider.setMinimum(0);
        minSlider.setValue(0);

        JSlider maxSlider= new JSlider();
        maxSlider.addChangeListener(e -> {

        });
        maxSlider.setMaximum(getMaxGroupNumber(tableObjects));
        maxSlider.setMinimum(0);
        maxSlider.setValue(getMaxGroupNumber(tableObjects));

        JSlider semestrSlider= new JSlider();
        semestrSlider.addChangeListener(e -> {

        });

        semestrSlider.setMinimum(0);
        semestrSlider.setMaximum(9);

        sliderPanel.add(minSlider);
        sliderPanel.add(maxSlider);
        sliderPanel.add(semestrSlider);


        foundObjs = FindSomeObjects(tableObjects, (int)jComboBox1.getSelectedItem(),semestrSlider.getValue(),maxSlider.getValue(),minSlider.getValue(),jTextField.getText());
        JTable tableFind = new JTable(setTableRange(foundObjs));
        JPanel panel = new JPanel();
        JPanel pageButtonsPanel = setControlPanelForPages(foundObjs, tableFind);

        JButton button41 = new JButton("Delete");

        panel.add(sliderPanel);
        panel.add(jComboBox1);
        panel.add(jTextField);
        panel.add(button1);
        panel.add(button41);

        button1.addActionListener(e -> {
            minSlider.setMaximum(getMaxGroupNumber(tableObjects));
            maxSlider.setMaximum(getMaxGroupNumber(tableObjects));

            foundObjs = FindSomeObjects(tableObjects, (int)jComboBox1.getSelectedItem(),semestrSlider.getValue(),maxSlider.getValue(),minSlider.getValue(),jTextField.getText());
            tableFind.setModel(setTableRange(foundObjs));
        });

        button41.addActionListener(e -> {
            int startC=tableObjects.size();
            if(tableObjects.removeAll(foundObjs)){
                table.setModel(setTableRange(tableObjects));
                JOptionPane.showMessageDialog(frame,"del "+(startC-tableObjects.size()));
            }
        });

        frame.getContentPane().add(BorderLayout.NORTH, panel);
        frame.getContentPane().add(BorderLayout.CENTER, tableFind);
        frame.getContentPane().add(BorderLayout.PAGE_END, pageButtonsPanel);

        frame.setVisible(true);
        return foundObjs;
    }

    public   List<Integer> getAllGroups(List<TableObject> students){

        List<Integer> groups= new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            if (!groups.contains(students.get(i).getGroup())) groups.add(students.get(i).getGroup());
        }
        return  groups;
    }

    public  int getMaxGroupNumber(List<TableObject> students){
        int max=0;

        for (int i = 0; i < students.size(); i++) {
            for (int j = 0; j < students.get(i).getWorkInSemestr().length; j++) {
                if(students.get(i).getWorksInSemestr(j)>max) {
                    max=students.get(i).getWorksInSemestr(j);
                }
            }
        }

        return  max;
    }

    public List<TableObject> FindSomeObjects(List<TableObject> initialList, int group,int sem,int maxWork,int minWork,String namepart) {
        List<TableObject> foundObjects = new ArrayList<>();
        for (int i = 0; i < initialList.size(); i++) {
            if (initialList.get(i).getGroup() == group) {
                if (initialList.get(i).getWorksInSemestr(sem) >= maxWork && initialList.get(i).getWorksInSemestr(sem) <= minWork)

                  if(namepart!="" && initialList.get(i).getName().contains((namepart)))  foundObjects.add(initialList.get(i));
            }
        }
        System.out.println("Found " + foundObjects.size() + "");
        return foundObjects;
    }


}
