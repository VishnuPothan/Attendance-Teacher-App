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
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mDatabase.orderByChild("phone").equalTo(phoneNumberStr).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.i("here", "Already a user..");
                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    SharedPreference.setUserVerified(getApplicationContext(), true);

                    //getting existing user details and store in shared preference
                    mDatabase.child(Objects.requireNonNull(mAuth.getUid())).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String userName = Objects.requireNonNull(snapshot.child("userName").getValue()).toString();
                                SharedPreference.setUserName(getApplicationContext(), userName);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    startActivity(mainIntent);
                } else {
                    Log.i("here", "New user...");
                    SharedPreference.setUserVerified(getApplicationContext(), true);
                    Intent createAccountIntent = new Intent(getApplicationContext(), MainActivity.class);
                    createAccountIntent.putExtra("PhoneNumber", phoneNumberStr);
                    startActivity(createAccountIntent);
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