package com.howtodoinjava.example.praticien.beans;

public class Praticien {

    private int id;
    private String name;

    public Praticien() {
        this.id = 0;
        this.name = "";
    }
    public Praticien(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public void setPraticienId(int id) {
        this.id = id;
    }
    public void setPraticienName(String name) {
        this.name = name;
    }
    public int getPraticienId() {
        return this.id;
    }
    public String getPraticienName() {
        return this.name;
    }


}
