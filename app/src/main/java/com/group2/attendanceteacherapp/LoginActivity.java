package com.group2.attendanceteacherapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    Button sentOTPBtn;
    TextInputEditText mobileNumberTextInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();
            
        sentOTPBtn = findViewById(R.id.sentOTPBtn);
        //initialization
        mobileNumberTextInput = findViewById(R.id.mobileNumberTextInput);
        sentOTPBtn = findViewById(R.id.sentOTPBtn);


        //sendOtp button clicked listener
        sentOTPBtn.setOnClickListener(v -> {
            String phoneNo = mobileNumberTextInput.getText().toString();
            if (phoneNo.length() != 10) {
                mobileNumberTextInput.setError("Enter valid mobile number");
                mobileNumberTextInput.requestFocus();
                return;
            }
            Intent OTPScreenIntent = new Intent(LoginActivity.this, OTPVerificationActivity.class);
            OTPScreenIntent.putExtra("PhoneNumber", "+91" + mobileNumberTextInput.getText().toString());
            startActivity(OTPScreenIntent);
            finish();
        });
    }
}