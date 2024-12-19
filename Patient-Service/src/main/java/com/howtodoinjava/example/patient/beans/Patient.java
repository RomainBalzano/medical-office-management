package com.howtodoinjava.example.patient.beans;


public class Patient {

    private int id;
    private String name;

    public Patient() {
        this.id = 0;
        this.name = "";
    }
    public Patient(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public void setEmployeeId(int id) {
        this.id = id;
    }
    public void setEmployeeName(String name) {
        this.name = name;
    }
    public int getEmployeeId() {
        return this.id;
    }
    public String getEmployeeName() {
        return this.name;
    }


}
