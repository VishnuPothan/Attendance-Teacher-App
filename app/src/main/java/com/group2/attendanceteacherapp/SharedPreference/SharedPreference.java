package com.group2.attendanceteacherapp.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreference {

    //shared preference declare variables
    public static final String USER_DETAILS = "userVerificationDetails";
    public static final String USER_VERIFIED = "userVerified";
    public static final String USER_ID = "userID";
    public static final String USER_NAME = "userName";
    public static final String USER_SUBJECT = "userSubject";
    public static final String USER_PHONE = "userPhone";

    //user details get
    public static Boolean getUserVerified(Context context) {
        SharedPreferences SPUserDetails = context.getSharedPreferences(USER_DETAILS, MODE_PRIVATE);
        return SPUserDetails.getBoolean(USER_VERIFIED, false);
    }

    public static String getUserName(Context context) {
        SharedPreferences SPUserDetails = context.getSharedPreferences(USER_DETAILS, MODE_PRIVATE);
        return SPUserDetails.getString(USER_NAME, "");
    }

    public static String getUserID(Context context) {
        SharedPreferences SPUserDetails = context.getSharedPreferences(USER_DETAILS, MODE_PRIVATE);
        return SPUserDetails.getString(USER_ID, "");
    }

    public static String getUserSubject(Context context) {
        SharedPreferences SPUserDetails = context.getSharedPreferences(USER_DETAILS, MODE_PRIVATE);
        return SPUserDetails.getString(USER_SUBJECT, "");
    }

    public static String getUserPhone(Context context) {
        SharedPreferences SPUserDetails = context.getSharedPreferences(USER_DETAILS, MODE_PRIVATE);
        return SPUserDetails.getString(USER_PHONE, "");
    }

    //user details set
    public static void setUserVerified(Context context, Boolean verified) {
        SharedPreferences SPUserDetails = context.getSharedPreferences(USER_DETAILS, MODE_PRIVATE);
        SharedPreferences.Editor editor = SPUserDetails.edit();
        editor.putBoolean(USER_VERIFIED, verified);
        editor.apply();
    }

    public static void setUserName(Context context, String name) {
        SharedPreferences SPUserDetails = context.getSharedPreferences(USER_DETAILS, MODE_PRIVATE);
        SharedPreferences.Editor editor = SPUserDetails.edit();
        editor.putString(USER_NAME, name);
        editor.apply();
    }

    public static void setUserID(Context context, String ID) {
        SharedPreferences SPUserDetails = context.getSharedPreferences(USER_DETAILS, MODE_PRIVATE);
        SharedPreferences.Editor editor = SPUserDetails.edit();
        editor.putString(USER_ID, ID);
        editor.apply();
    }

    public static void setUserPhone(Context context, String phone) {
        SharedPreferences SPUserDetails = context.getSharedPreferences(USER_DETAILS, MODE_PRIVATE);
        SharedPreferences.Editor editor = SPUserDetails.edit();
        editor.putString(USER_PHONE, phone);
        editor.apply();
    }

    public static void setUserSubject(Context context, String subject) {
        SharedPreferences SPUserDetails = context.getSharedPreferences(USER_DETAILS, MODE_PRIVATE);
        SharedPreferences.Editor editor = SPUserDetails.edit();
        editor.putString(USER_SUBJECT, subject);
        editor.apply();
    }
}
