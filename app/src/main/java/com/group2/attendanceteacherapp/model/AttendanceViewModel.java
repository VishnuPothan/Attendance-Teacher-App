package com.group2.attendanceteacherapp.model;

import java.util.List;

public class AttendanceViewModel {
    String ID;
    String name;
    String time;
    List<AttendancePeriodModel> attendancePeriodModelList;

    public AttendanceViewModel(String ID, String name, String time, List<AttendancePeriodModel> attendancePeriodModelList) {
        this.ID = ID;
        this.name = name;
        this.time = time;
        this.attendancePeriodModelList = attendancePeriodModelList;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public List<AttendancePeriodModel> getAttendancePeriodModelList() {
        return attendancePeriodModelList;
    }

    public void setAttendancePeriodModelList(List<AttendancePeriodModel> attendancePeriodModelList) {
        this.attendancePeriodModelList = attendancePeriodModelList;
    }
}
