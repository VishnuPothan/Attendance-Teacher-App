package com.group2.attendanceteacherapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.group2.attendanceteacherapp.SharedPreference.SharedPreference;
import com.group2.attendanceteacherapp.adapter.AttendanceViewAdapter;
import com.group2.attendanceteacherapp.model.AttendancePeriodModel;
import com.group2.attendanceteacherapp.model.AttendanceStudentModel;
import com.group2.attendanceteacherapp.model.AttendanceSubjectModel;
import com.group2.attendanceteacherapp.model.AttendanceViewModel;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    AutoCompleteTextView editTextFilledExposedDropdown;
    LinearLayout viewByDateLayout;
    RecyclerView attendanceRecycler;
    AttendanceViewAdapter attendanceViewAdapter;
    FirebaseAuth mAuth;
    List<AttendanceSubjectModel> attendanceSubjectModelList;
    List<AttendanceViewModel> attendanceViewModelList;
    String dateStr;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //Initialize Firebase
        mAuth = FirebaseAuth.getInstance();

        // Bottom Navigation SetUp
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            switch (itemId) {
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), com.group2.attendanceteacherapp.HomeActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.profile:
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.message:
                    startActivity(new Intent(getApplicationContext(), MessageActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            finish();
            return false;
        });

        // Attendance View Spinner initialize
        /*String[] attendanceViewType = new String[]{"By Percentage", "By Date", "By Subject"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, attendanceViewType);
        editTextFilledExposedDropdown = findViewById(R.id.attendanceViewText);
        editTextFilledExposedDropdown.setAdapter(adapter);*/

        //findViewById(R.id.applyBtn).setOnClickListener(view -> ShowAttendanceByDate());

        //date range picker
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        final MaterialDatePicker<Long> materialDatePicker = builder.build();

        findViewById(R.id.applyBtn).setOnClickListener(v -> {
            if (!materialDatePicker.isAdded()) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                dateStr = formatter.format(selection);
                ShowAttendanceByDate();
            }
        });

        // TODO Badge code for icon
        /*BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.home);
        badgeDrawable.isVisible();
        badgeDrawable.setNumber(5);*/

    }


    private void ShowAttendanceByDate() {
        // making layout visible
        viewByDateLayout = findViewById(R.id.viewAttendance);
        viewByDateLayout.setVisibility(View.VISIBLE);

        //initialize
        attendanceRecycler = findViewById(R.id.attendanceRecyclerView);

        GetData();
    }

    private void GetData() {
        attendanceSubjectModelList = new ArrayList<>();
        attendanceViewModelList = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("attendance").child(dateStr);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // convert snapshot to attendance subject model
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String uniqueKeyStr = ds.getKey();

                        Gson gson = new Gson();
                        String json = gson.toJson(ds.getValue());

                        Type collectionType = new TypeToken<List<AttendanceStudentModel>>() {
                        }.getType();
                        List<AttendanceStudentModel> attendanceStudentModelList = gson.fromJson(json, collectionType);

                        AttendanceSubjectModel attendanceStudentModel = new AttendanceSubjectModel(uniqueKeyStr, attendanceStudentModelList);
                        attendanceSubjectModelList.add(attendanceStudentModel);
                    }

                    // convert format of list from attendance subject model to attendance view model
                    for (int i = 0; i < attendanceSubjectModelList.size(); i++) {
                        List<AttendanceStudentModel> attendanceStudentModelList = attendanceSubjectModelList.get(i).getAttendanceStudentModelList();
                        for (int j = 0; j < attendanceStudentModelList.size(); j++) {
                            String attendanceMarkStr = "X";
                            if (i == 0) {
                                if (attendanceStudentModelList.get(j).getSubject().equals(SharedPreference.getUserSubject(getApplicationContext()))) {
                                    attendanceMarkStr = attendanceStudentModelList.get(j).getAttendanceMark();
                                }
                                AttendancePeriodModel attendancePeriodModel = new AttendancePeriodModel(i, attendanceMarkStr);
                                List<AttendancePeriodModel> attendancePeriodModelList = new ArrayList<>();
                                attendancePeriodModelList.add(attendancePeriodModel);
                                AttendanceViewModel attendanceViewModel = new AttendanceViewModel(attendanceStudentModelList.get(j).getID(), attendanceStudentModelList.get(j).getName(), attendanceStudentModelList.get(j).getTime(), attendancePeriodModelList);
                                attendanceViewModelList.add(attendanceViewModel);
                                continue;
                            }

                            if (attendanceStudentModelList.get(j).getSubject().equals(SharedPreference.getUserSubject(getApplicationContext()))) {
                                attendanceMarkStr = attendanceStudentModelList.get(j).getAttendanceMark();
                            }
                            AttendancePeriodModel attendancePeriodModel = new AttendancePeriodModel(i, attendanceMarkStr);
                            for (int k = 0; k < attendanceViewModelList.size(); k++) {
                                if (attendanceViewModelList.get(k).getID().equals(attendanceStudentModelList.get(j).getID())) {
                                    List<AttendancePeriodModel> attendancePeriodModelList = attendanceViewModelList.get(k).getAttendancePeriodModelList();
                                    attendancePeriodModelList.add(attendancePeriodModel);
                                    attendanceViewModelList.get(k).setAttendancePeriodModelList(attendancePeriodModelList);
                                }
                            }
                        }
                    }

                    Gson gson = new Gson();
                    String json = gson.toJson(attendanceViewModelList);

                    Log.i("here list", json);

                    // setting up the view
                    attendanceViewAdapter = new AttendanceViewAdapter(attendanceViewModelList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    attendanceRecycler.setLayoutManager(layoutManager);
                    attendanceRecycler.setAdapter(attendanceViewAdapter);
                } else {
                    Toast.makeText(getApplicationContext(), "No attendance recorded in " + dateStr , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}