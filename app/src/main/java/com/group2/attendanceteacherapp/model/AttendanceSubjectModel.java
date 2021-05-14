package com.group2.attendanceteacherapp.model;

import java.util.List;

public class AttendanceSubjectModel {
    String subjectUniqueID;
    List<AttendanceStudentModel> attendanceStudentModelList;

    public AttendanceSubjectModel(String subjectUniqueID, List<AttendanceStudentModel> attendanceStudentModelList) {
        this.subjectUniqueID = subjectUniqueID;
        this.attendanceStudentModelList = attendanceStudentModelList;
    }

    public String getSubjectUniqueID() {
        return subjectUniqueID;
    }

    public void setSubjectUniqueID(String subjectUniqueID) {
        this.subjectUniqueID = subjectUniqueID;
    }

    public List<AttendanceStudentModel> getAttendanceStudentModelList() {
        return attendanceStudentModelList;
    }

    public void setAttendanceStudentModelList(List<AttendanceStudentModel> attendanceStudentModelList) {
        this.attendanceStudentModelList = attendanceStudentModelList;
    }
}
