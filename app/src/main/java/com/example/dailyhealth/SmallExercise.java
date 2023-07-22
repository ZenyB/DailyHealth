package com.example.dailyhealth;

public class SmallExercise {
    private String name;
    private String duration;
    private int exerciseCode;
    private String exerciseName;
    private int exerciseType;
    private String imageFileName;
    private Integer exerciseDuration;
    private Integer exerciseRepetitions;

    public SmallExercise(String name, String time) {
        this.name = name;
        this.duration = time;
    }

    // Constructor mặc định
    public SmallExercise() {
    }

    // Constructor với tham số
    // Constructor với tham số
    public SmallExercise(int exerciseCode, String exerciseName, int exerciseType,
                         String imageFileName, Integer exerciseDuration, Integer exerciseRepetitions) {
        this.exerciseCode = exerciseCode;
        this.exerciseName = exerciseName;
        this.exerciseType = exerciseType;
        this.imageFileName = imageFileName;
        this.exerciseDuration = exerciseDuration;
        this.exerciseRepetitions = exerciseRepetitions;
    }

    // Các phương thức getter và setter
    public int getExerciseCode() {
        return exerciseCode;
    }

    public void setExerciseCode(int exerciseCode) {
        this.exerciseCode = exerciseCode;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(int exerciseType) {
        this.exerciseType = exerciseType;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public Integer getExerciseDuration() {
        return exerciseDuration;
    }

    public void setExerciseDuration(Integer exerciseDuration) {
        this.exerciseDuration = exerciseDuration;
    }

    public Integer getExerciseRepetitions() {
        return exerciseRepetitions;
    }

    public void setExerciseRepetitions(Integer exerciseRepetitions) {
        this.exerciseRepetitions = exerciseRepetitions;
    }
    public String getName() {
        return name;
    }

    public String getDuration() {
        return duration;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
