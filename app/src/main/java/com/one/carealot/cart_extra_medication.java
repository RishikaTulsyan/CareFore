package com.one.carealot;

public class cart_extra_medication {
        private  String is_used_for;
        private String company_name;

    public cart_extra_medication() {
    }

    public cart_extra_medication(String is_used_for, String company_name) {

            this.is_used_for = is_used_for;
            this.company_name = company_name;


        }

    public String getIs_used_for() {
        return is_used_for;
    }

    public void setIs_used_for(String is_used_for) {
        this.is_used_for = is_used_for;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String toString(){
        return  "Medication"+"\n"+"Company Name"+" - "+ company_name +"\n"+"Use"+" - "+ is_used_for;
    }



}
