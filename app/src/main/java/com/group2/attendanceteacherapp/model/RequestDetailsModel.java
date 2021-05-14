package com.group2.attendanceteacherapp.model;

public class RequestDetailsModel {
    String periodID;
    String studentID;
    String teacherID;
    String subject;
    String time;
    String date;
    String attendanceMark;
    String message;
    String status;

    public RequestDetailsModel() {
    }

    public RequestDetailsModel(String periodID, String studentID, String teacherID, String subject, String time, String date, String attendanceMark, String message, String status) {
        this.periodID = periodID;
        this.studentID = studentID;
        this.teacherID = teacherID;
        this.subject = subject;
        this.time = time;
        this.date = date;
        this.attendanceMark = attendanceMark;
        this.message = message;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPeriodID() {
        return periodID;
    }

    public void setPeriodID(String periodID) {
        this.periodID = periodID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAttendanceMark() {
        return attendanceMark;
    }

    public void setAttendanceMark(String attendanceMark) {
        this.attendanceMark = attendanceMark;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
