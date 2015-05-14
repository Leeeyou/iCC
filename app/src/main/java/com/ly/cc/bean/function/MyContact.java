package com.ly.cc.bean.function;

import java.util.Comparator;

/**
 * Created by xzzz on 2015/5/13.
 */
public class MyContact {
    private String name;
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return name+" --  "+phone;
    }

}
