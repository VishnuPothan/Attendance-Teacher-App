package com.group2.attendanceteacherapp.model;

public class AttendancePeriodModel {
    int hour;
    String attendanceMark;

    public AttendancePeriodModel(int hour, String attendanceMark) {
        this.hour = hour;
        this.attendanceMark = attendanceMark;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getAttendanceMark() {
        return attendanceMark;
    }

    public void setAttendanceMark(String attendanceMark) {
        this.attendanceMark = attendanceMark;
    }
}
