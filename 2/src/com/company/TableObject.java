package com.company;

import java.io.Serializable;

public class TableObject{
        /*public TableObject(String name,int age){
            this.age=age;
            this.name=name;
        }*/
       private   String name;

    public int getGroup() {
        return group;
    }

    public void setGroup(int age) {
        this.group = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   private  int group;
    private int[] workInSemestr = new int[10];
    public int getWorksInSemestr(int semestr){
        return  this.workInSemestr[semestr];
    }

    public void setWorksInSemestr(int o, int column) {
        this.workInSemestr[column-2]=o;
    }

    public void setWorkInSemestr(int[] work){
        this.workInSemestr=work;
    }

    public int[] getWorkInSemestr(){
        return this.workInSemestr;
    }

}
