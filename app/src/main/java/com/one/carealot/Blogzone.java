package com.one.carealot;

public class Blogzone {
    private String title, desc, imageUrl, username;

    public Blogzone(String title, String desc, String uid) {
        this.title = title;
        this.desc = desc;
        this.username = uid;

    }

    public Blogzone() {
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
    public String toString(){
        return this.getUsername()+"\n"+"Tiltle: "+getTitle()+"\n"+"Discription: "+getDesc();
    }





    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
