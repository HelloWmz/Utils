package com.wmz.utils.bean;

import java.util.List;

/**
 * Created by wmz on 2019/4/18.
 */

public class Contact {
    public String address;
    public String name;
    public String phone;
    public String company;
    public List<String> datas;

    @Override
    public String toString() {
        return "Contact{" +
                "address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
