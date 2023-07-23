package com.example.dailyhealth;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainExercise {
    private String id;
    private int duration;
    private int difficulty;
    private String name;
    private String time;
    private String kcal;
    private ArrayList<SmallExercise> smallExercises;

    // Constructor mặc định
    public MainExercise() {
        smallExercises = new ArrayList<>();
    }

    // Constructor với tham số
    public MainExercise(String id, int duration, int difficulty, String name, ArrayList<SmallExercise> smallExercises) {
        this.id = id;
        this.duration = duration;
        this.difficulty = difficulty;
        this.name = name;
        this.smallExercises = smallExercises;
    }


    // Các phương thức getter và setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public ArrayList<SmallExercise> getSmallExercises() {
        return smallExercises;
    }

    public void setSmallExercises(ArrayList<SmallExercise> smallExercises) {
        this.smallExercises = smallExercises;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKcal() {
        return kcal;
    }

    public void setKcal(String kcal) {
        this.kcal = kcal;
    }

}
