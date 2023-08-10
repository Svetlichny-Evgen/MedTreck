package com.example.myapplication.patient;

public class Ward {
    private int id;
    private int maxCapacity;
    private int currentCapacity;

    public Ward(){}
    public Ward(int id, int maxCapacity, int currentCapacity) {
        this.id = id;
        this.maxCapacity = maxCapacity;
        this.currentCapacity = currentCapacity;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }


    public int getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }
    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }
}
