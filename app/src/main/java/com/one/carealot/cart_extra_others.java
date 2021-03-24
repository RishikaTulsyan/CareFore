package com.one.carealot;

public class cart_extra_others {
    private  String Name;
    private String Description;


    public cart_extra_others() {
    }

    public cart_extra_others(String Name,String Description) {

        this.Name = Name;
        this.Description = Description;


    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String toString(){
        return  "Others"+"\n"+"Name"+" - "+ Name+"\n"+"Description"+" - "+Description;
    }
}
