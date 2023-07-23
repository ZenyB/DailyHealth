package com.example.dailyhealth;

public class ScheduleEvent {
    private String title;
    private String time;
    private String detail;
    private String location;

    public ScheduleEvent(String title, String time, String detail, String location)
    {
        this.title = title;
        this.time = time;
        this.detail = detail;
        this.location = location;
    }

    public String getTitle() {return title;}

    public String getTime() {return time;}

    public String getDetail() {return detail;}

    public String getLocation() {return location;}

    public void setTime(String time) {this.time = time;}

    public void setDetail(String detail) {this.detail = detail;}

    public void setLocation(String location) {this.location = location;}

    public void setTitle(String title) {this.title = title;}
}
