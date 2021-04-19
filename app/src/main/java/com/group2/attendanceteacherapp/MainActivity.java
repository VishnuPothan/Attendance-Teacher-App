package com.group2.attendanceteacherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        startBtn = findViewById(R.id.startBtn);
        mDatabase.child("record").child("request").setValue("brr");

        Log.i("here", "Called");
        mDatabase.child("record").child("start").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String startStr = String.valueOf(snapshot.getValue());
                    Log.i("here", startStr);
                    if (startStr.equals("true")) {
                        startBtn.setText("Start");
                    } else {
                        startBtn.setVisibility(View.GONE);
                    }
                } else {
                    startBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("here", String.valueOf(error));
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("record").child("request").setValue("true");
            }
        });

    }
}