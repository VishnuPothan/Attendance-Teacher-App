package com.group2.attendanceteacherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.group2.attendanceteacherapp.SharedPreference.SharedPreference;

import java.util.Objects;

public class EditProfile extends AppCompatActivity {
    TextInputEditText nameText, phoneText;
    Button saveButton;
    ProgressBar progressBar;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // initialize view
        nameText = findViewById(R.id.nameText);
        phoneText = findViewById(R.id.mobileNumberTextInput);
        progressBar = findViewById(R.id.progressBar);
        saveButton = findViewById(R.id.saveBtn);

        //Initialize Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // add value to view
        nameText.setText(SharedPreference.getUserName(getApplicationContext()));
        phoneText.setText(SharedPreference.getUserPhone(getApplicationContext()));

        saveButton.setOnClickListener(view -> {
            if (String.valueOf(nameText.getText()).isEmpty()) {
                nameText.setError("Enter valid name");
                nameText.requestFocus();
                return;
            }
            if (String.valueOf(phoneText.getText()).length() != 10) {
                phoneText.setError("Enter valid mobile number");
                phoneText.requestFocus();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            saveButton.setEnabled(false);
            UploadDetails();
        });
    }

    private void UploadDetails() {
        mDatabase.child("teacher").child(SharedPreference.getUserID(getApplicationContext())).child("name").setValue(String.valueOf(nameText.getText()))
                .addOnSuccessListener(aVoid -> {
                    SharedPreference.setUserName(getApplicationContext(), String.valueOf(nameText.getText()));
                    Toast.makeText(getApplicationContext(), "Name Updated Successful", Toast.LENGTH_SHORT).show();
                    mDatabase.child("teacher").child(SharedPreference.getUserID(getApplicationContext())).child("phone").setValue(String.valueOf(phoneText.getText()))
                            .addOnSuccessListener(aVoid1 -> {
                                SharedPreference.setUserPhone(getApplicationContext(), String.valueOf(phoneText.getText()));
                                Toast.makeText(getApplicationContext(), "Phone Updated Successful", Toast.LENGTH_SHORT).show();
                                finish();
                            }).addOnFailureListener(e -> {
                        progressBar.setVisibility(View.GONE);
                        saveButton.setEnabled(true);
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }).addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    saveButton.setEnabled(true);
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

        );
    }
}