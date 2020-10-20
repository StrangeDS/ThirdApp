package com.swufe.thirdapp;

public class RateItem {
    private String name;
    private String rate;
    private int ID;

    public RateItem(){
        this.ID = -1;
        this.name = "";
        this.rate = "";
    }

    public RateItem(String name, String rate){
        this.ID = -1;
        this.name = name;
        this.rate = rate;
    }

    public RateItem(int ID, String name, String rate){
        this.ID = ID;
        this.name = name;
        this.rate = rate;
    }

    public String getName(){
        return this.name;
    }

    public String getRate(){
        return this.rate;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
