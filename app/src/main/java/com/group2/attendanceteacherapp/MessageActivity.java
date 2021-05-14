package com.group2.attendanceteacherapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.group2.attendanceteacherapp.SharedPreference.SharedPreference;
import com.group2.attendanceteacherapp.adapter.MessageAdapter;
import com.group2.attendanceteacherapp.model.RequestDetailsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MessageActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    RecyclerView requestsRecyclerView;
    private DatabaseReference mDatabase;
    List<RequestDetailsModel> requestDetailsModelList;
    MessageAdapter messageAdapter;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //Initialize Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Bottom Navigation SetUp
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.message);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            switch (itemId) {
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), com.group2.attendanceteacherapp.HomeActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.profile:
                    startActivity(new Intent(getApplicationContext(), com.group2.attendanceteacherapp.ProfileActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.message:
                    startActivity(new Intent(getApplicationContext(), com.group2.attendanceteacherapp.MessageActivity.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            finish();
            return false;
        });

        ShowAttendanceByDate();
    }

    private void ShowAttendanceByDate() {
        //initialize
        requestsRecyclerView = findViewById(R.id.requestsRecyclerView);

        //data fetch
        GetData();
    }

    private void GetData() {
        requestDetailsModelList = new ArrayList<>();

        mDatabase.child("request").orderByChild("teacherID").equalTo(SharedPreference.getUserID(getApplicationContext())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    RequestDetailsModel requestDetailsModel = ds.getValue(RequestDetailsModel.class);
                    requestDetailsModelList.add(requestDetailsModel);
                }
                // setting up the recycler view
                messageAdapter = new MessageAdapter(requestDetailsModelList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                requestsRecyclerView.setLayoutManager(layoutManager);
                requestsRecyclerView.setAdapter(messageAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void ShowRequestDetails(int position) {
        Intent intent = new Intent(getApplicationContext(), RequestProcess.class);
        String detailsStr = new Gson().toJson(requestDetailsModelList.get(position));
        intent.putExtra("HOUR_DETAILS", detailsStr);
        startActivity(intent);
    }
}