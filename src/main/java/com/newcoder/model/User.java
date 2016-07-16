package com.newcoder.model;

/**
 * Created by WXY on 2016/7/16.
 */
public class User {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    public User(String name){
        this.name = name;
    }
}
