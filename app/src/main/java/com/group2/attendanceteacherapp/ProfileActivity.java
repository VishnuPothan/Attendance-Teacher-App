package com.group2.attendanceteacherapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.group2.attendanceteacherapp.SharedPreference.SharedPreference;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView userNameText, roleText;
    MaterialCardView accountDetailsCard;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Bottom Navigation SetUp
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
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
                    startActivity(new Intent(getApplicationContext(), MessageActivity.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            finish();
            return false;
        });

        // initialize
        userNameText = findViewById(R.id.userNameText);
        roleText = findViewById(R.id.roleText);
        accountDetailsCard = findViewById(R.id.accountDetailsCard);

        userNameText.setText(SharedPreference.getUserName(getApplicationContext()));
        roleText.setText(SharedPreference.getUserID(getApplicationContext()));
        accountDetailsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EditProfile.class));
            }
        });
    }
}