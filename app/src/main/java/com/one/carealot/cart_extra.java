package com.one.carealot;

import com.google.firebase.database.DatabaseReference;

public class cart_extra {


    private String size;
    private String type;


    public cart_extra(){

    }

    public cart_extra(String imageid , String size , String type,String stationery_type,String brand,String Imaged){

        this.size=size;
        this.type=type;



    }





    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString(){
        return  "Cloths"+"\n"+"Type"+" - "+ type+"\n"+"Size"+" - "+size;
    }


    }




