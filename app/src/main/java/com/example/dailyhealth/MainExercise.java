package com.example.dailyhealth;

public class MainExercise {
    private String name;
    private String time;
    private String kcal;

    public MainExercise(String name, String time, String kcal) {
        this.name = name;
        this.time = time;
        this.kcal = kcal;
    }

    public String getName() {
        return name;
    }

    public String getTime() { return time; }

    public String getKcal() {
        return kcal;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setKcal(String kcal) {
        this.kcal = kcal;
    }
}
