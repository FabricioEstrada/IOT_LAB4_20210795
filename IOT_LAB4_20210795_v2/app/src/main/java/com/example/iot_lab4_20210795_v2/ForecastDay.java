package com.example.iot_lab4_20210795_v2;

import com.example.iot_lab4_20210795_v2.Location.Location;

import java.util.List;

public class ForecastDay {
    private String date;
    private Day day;
    private List<Hour> hour;

    // Getter y Setter para date
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Getter y Setter para day
    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    // Getter y Setter para hour
    public List<Hour> getHour() {
        return hour;
    }

    public void setHour(List<Hour> hour) {
        this.hour = hour;
    }
}
