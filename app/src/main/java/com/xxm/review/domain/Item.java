package com.xxm.review.domain;


public class Item {

    private String title; //标题
    private String desc; //描述
    private Class clazz; //Class


    public Item(String title, String desc, Class clazz) {
        this.title = title;
        this.desc = desc;
        this.clazz = clazz;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }


}
