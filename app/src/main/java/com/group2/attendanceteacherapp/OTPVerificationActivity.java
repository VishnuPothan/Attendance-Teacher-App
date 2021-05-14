package com.group2.attendanceteacherapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group2.attendanceteacherapp.SharedPreference.SharedPreference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OTPVerificationActivity extends AppCompatActivity {

    Button verifyOTPBtn;
    FirebaseAuth mAuth;
    String verificationCodeBySystem;
    String phoneNumberStr;
    PinView OTPCodePinView;
    ProgressBar progressBar;
    TextView resendOTP;
    boolean sentOTP = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_verification);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //initialization
        verifyOTPBtn = findViewById(R.id.verifyOTPBtn);
        OTPCodePinView = findViewById(R.id.pinView);
        progressBar = findViewById(R.id.progressBarOTP);
        resendOTP = findViewById(R.id.resendOTP);

        //get intent values
        phoneNumberStr = getIntent().getStringExtra("PhoneNumber");

        //Initialize Firebase
        mAuth = FirebaseAuth.getInstance();

        sendVerificationCode(phoneNumberStr);

        //verifyBtn click listener
        verifyOTPBtn.setOnClickListener(v -> {
            String code = Objects.requireNonNull(OTPCodePinView.getText()).toString();
            if (code.isEmpty() || code.length() < 6) {
                OTPCodePinView.setError("Wrong OTP..");
                OTPCodePinView.requestFocus();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            verifyCode(code);
        });

        //for resent OTP
        resendOTP.setOnClickListener(v -> {
            if (!sentOTP) {
                sendVerificationCode(phoneNumberStr);
            } else {
                String messStr = "OTP Already send to" + phoneNumberStr + "please check your phone number";
                Toast.makeText(getApplicationContext(), messStr, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verifyCode(String codeByUser) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, codeByUser);
        signInTheUserByCredential(credential);
    }

    private void signInTheUserByCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(OTPVerificationActivity.this, task -> {
            if (task.isSuccessful()) {
                checkExistingUser();
            } else {
                Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkExistingUser() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("teacher");
        mDatabase.orderByChild("phone").equalTo(phoneNumberStr.substring(3,13)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    //getting existing user details and store in shared preference
                    HashMap<String, String> hMap = (HashMap<String, String>) snapshot.getValue();
                    String pair = String.valueOf(hMap.get(hMap.keySet().toArray()[0]));
                    String[] pairs = pair.split(",");
                    String userID = String.valueOf(hMap.keySet().toArray()[0]);
                    String phoneStr = pairs[0].split("=")[1];
                    String subjectStr = pairs[1].split("=")[1];
                    String nameStr = pairs[2].split("=")[1];

                    SharedPreference.setUserID(getApplicationContext(), userID);
                    SharedPreference.setUserVerified(getApplicationContext(), true);
                    SharedPreference.setUserName(getApplicationContext(), nameStr);
                    SharedPreference.setUserPhone(getApplicationContext(), phoneStr);
                    SharedPreference.setUserSubject(getApplicationContext(), subjectStr);

                    Intent mainIntent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(mainIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Your are not registered!!!", Toast.LENGTH_SHORT).show();
                }
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                sentOTP = false;
            }
        });
    }

    private void sendVerificationCode(String phoneNo) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNo)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // Initialize phone auth callbacks
    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodeBySystem = s;
            Toast.makeText(getApplicationContext(), "OTP Send", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {
            String code = credential.getSmsCode();
            if (code != null) {
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("here", e.getMessage());
        }
    };
}