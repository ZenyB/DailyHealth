package com.example.dailyhealth;

public class ScheduleEvent {
    private String title, id;
    private int day, month, year, hours, totalMinutes;
    private String time;
    private String detail;
    private String location;

    public ScheduleEvent(String id, String title, String detail, String location, int day, int month, int year, int hours, int totalMinutes)
    {
        this.id = id;
        this.title = title;
        //this.time = time;
        this.detail = detail;
        this.location = location;
        this.day = day;
        this.month = month;
        this.year = year;
        this.hours = hours;
        this.totalMinutes = totalMinutes;

        this.time = (hours < 10 ? "0" + Integer.toString(hours): Integer.toString(hours))+ ":" + ((totalMinutes % 60) < 10 ? "0" + Integer.toString(totalMinutes % 60): Integer.toString(totalMinutes % 60));
    }

    public String getId() {return id;}

    public String getTitle() {return title;}

    public String getTime() {return time;}

    public String getDetail() {return detail;}

    public String getLocation() {return location;}

    public int getDay() {return day;}

    public int getMonth() {return month;}

    public int getYear() {return year;}

    public int getHours() {return hours;}

    public int getTotalMinutes() {return totalMinutes;}

    //public void setTime(String time) {this.time = time;}


    public void setId(String id) {this.id = id;}

    public void setDetail(String detail) {this.detail = detail;}

    public void setLocation(String location) {this.location = location;}

    public void setTitle(String title) {this.title = title;}

    public void setDay(int day) {this.day = day;}

    public void setMonth(int month) {this.month = month;}

    public void setYear(int year) {this.year = year;}

    public void setHours(int hours) {this.hours = hours;}

    public void setTotalMinutes(int totalMinutes) {this.totalMinutes = totalMinutes;}
}
