package com.wmz.utils.bean;

/**
 * Created by Admin on 2018/9/19.
 */

public class DiffUtilBean {
   public String name;
   public int age;
   public String sex;
   public int  itemType;

  public DiffUtilBean(String name, int age, String sex) {
    this.name = name;
    this.age = age;
    this.sex = sex;
  }

    public DiffUtilBean(String name, int  itemType) {
        this.name = name;
        this.itemType = itemType;
    }
}
