package com.one.carealot;

public class cart_extra_stationery {
    private  String stationery_Type;
    private String brand;



    public cart_extra_stationery() {
    }
    public cart_extra_stationery(String stationery_type,String brand,String Imaged) {

        this.stationery_Type = stationery_type;
        this.brand = brand;


    }


        public String getStationery_Type() {
        return stationery_Type;
    }

    public void setStationery_Type(String stationery_Type) {
        this.stationery_Type = stationery_Type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }



    public String toString(){
        return  "Stationery"+"\n"+"Type"+" - "+ stationery_Type+"\n"+"Brand"+" - "+brand;
    }

}
