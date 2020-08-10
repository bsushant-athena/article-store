package com.article.entity;

public class TimetoRead {

    private int days;
    private int hours;
    private int minutes;
    private int seconds;

    public int getDays ( ) {
        return days;
    }

    public void setDays ( int days ) {
        this.days = days;
    }

    public int getHours ( ) {
        return hours;
    }

    public void setHours ( int hours ) {
        this.hours = hours;
    }

    public int getMins() {
        return minutes;
    }

    public void setMins(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}