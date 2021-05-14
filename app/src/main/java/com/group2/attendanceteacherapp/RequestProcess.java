package com.group2.attendanceteacherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.group2.attendanceteacherapp.model.RequestDetailsModel;

import java.util.Objects;

public class RequestProcess extends AppCompatActivity {
    TextView subjectNameText, timeText, attendanceText, dateText, teacherNameText, messageText;
    RequestDetailsModel requestDetailsModel;
    ProgressBar progressBar;
    Button approveBtn, cancelBtn;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_process);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //get intent values
        String requestDetailsModelStr = getIntent().getStringExtra("HOUR_DETAILS");
        Gson gson = new Gson();
        requestDetailsModel = gson.fromJson(requestDetailsModelStr, RequestDetailsModel.class);

        // initialize views
        subjectNameText = findViewById(R.id.subjectNameText);
        timeText = findViewById(R.id.timeText);
        attendanceText = findViewById(R.id.attendanceText);
        messageText= findViewById(R.id.messageText);
        dateText = findViewById(R.id.dateText);
        teacherNameText = findViewById(R.id.teacherNameText);
        progressBar = findViewById(R.id.progressBar);
        approveBtn = findViewById(R.id.approveBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

        dateText.setText(requestDetailsModel.getDate());
        subjectNameText.setText(requestDetailsModel.getSubject());
        attendanceText.setText(requestDetailsModel.getAttendanceMark().equals("P") ? "Present" : "Absent");

        if (!requestDetailsModel.getAttendanceMark().equals("A")) {
            timeText.setVisibility(View.VISIBLE);
            timeText.setText(requestDetailsModel.getTime());
        }

        teacherNameText.setText(requestDetailsModel.getStudentID());
        messageText.setText(requestDetailsModel.getMessage());

        //database reference
        mDatabase = FirebaseDatabase.getInstance().getReference().child("request").child(requestDetailsModel.getPeriodID()+requestDetailsModel.getStudentID()).child("status");

        approveBtn.setOnClickListener(view -> {
            approveBtn.setEnabled(false);
            cancelBtn.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            mDatabase.setValue("Approved").addOnSuccessListener(aVoid -> {
                Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                finish();
            }).addOnFailureListener(e -> {
                approveBtn.setEnabled(true);
                cancelBtn.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Some error happened. Try Again!!!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            });
        });

        cancelBtn.setOnClickListener(view -> {
            approveBtn.setEnabled(false);
            cancelBtn.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            mDatabase.setValue("cancelled").addOnSuccessListener(aVoid -> {
                Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                finish();
            }).addOnFailureListener(e -> {
                approveBtn.setEnabled(true);
                cancelBtn.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Some error happened. Try Again!!!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            });
        });
    }
}