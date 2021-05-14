package com.group2.attendanceteacherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.attendanceteacherapp.R;
import com.group2.attendanceteacherapp.model.AttendanceSubjectModel;
import com.group2.attendanceteacherapp.model.AttendanceViewModel;

import java.util.List;

public class AttendanceViewAdapter extends RecyclerView.Adapter<AttendanceViewAdapter.ViewHolder>{
    List<AttendanceViewModel> attendanceViewModelList;
    Context context;

    public AttendanceViewAdapter(List<AttendanceViewModel> attendanceViewModelList) {
        this.attendanceViewModelList = attendanceViewModelList;
    }

    @NonNull
    @Override
    public AttendanceViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendace_table_row, parent, false);
        return new AttendanceViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewAdapter.ViewHolder holder, int position) {
        final AttendanceViewModel attendanceViewModel = attendanceViewModelList.get(position);

        holder.nameBtn.setText(attendanceViewModel.getName());
        // table set
        holder.hour1Btn.setText(attendanceViewModel.getAttendancePeriodModelList().get(0).getAttendanceMark());
        holder.hour2Btn.setText(attendanceViewModel.getAttendancePeriodModelList().get(1).getAttendanceMark());
        holder.hour3Btn.setText(attendanceViewModel.getAttendancePeriodModelList().get(2).getAttendanceMark());
        holder.hour4Btn.setText(attendanceViewModel.getAttendancePeriodModelList().get(3).getAttendanceMark());
        holder.hour5Btn.setText(attendanceViewModel.getAttendancePeriodModelList().get(4).getAttendanceMark());
        holder.hour6Btn.setText(attendanceViewModel.getAttendancePeriodModelList().get(5).getAttendanceMark());
        holder.hour7Btn.setText(attendanceViewModel.getAttendancePeriodModelList().get(6).getAttendanceMark());

        holder.hour1Btn.setBackgroundColor(attendanceViewModel.getAttendancePeriodModelList().get(0).getAttendanceMark().equals("A") ? context.getResources().getColor(R.color.red) : attendanceViewModel.getAttendancePeriodModelList().get(0).getAttendanceMark().equals("P") ? context.getResources().getColor(R.color.green) : context.getResources().getColor(R.color.gray));
        holder.hour2Btn.setBackgroundColor(attendanceViewModel.getAttendancePeriodModelList().get(1).getAttendanceMark().equals("A") ? context.getResources().getColor(R.color.red) : attendanceViewModel.getAttendancePeriodModelList().get(1).getAttendanceMark().equals("P") ? context.getResources().getColor(R.color.green) : context.getResources().getColor(R.color.gray));
        holder.hour3Btn.setBackgroundColor(attendanceViewModel.getAttendancePeriodModelList().get(2).getAttendanceMark().equals("A") ? context.getResources().getColor(R.color.red) : attendanceViewModel.getAttendancePeriodModelList().get(2).getAttendanceMark().equals("P") ? context.getResources().getColor(R.color.green) : context.getResources().getColor(R.color.gray));
        holder.hour4Btn.setBackgroundColor(attendanceViewModel.getAttendancePeriodModelList().get(3).getAttendanceMark().equals("A") ? context.getResources().getColor(R.color.red) : attendanceViewModel.getAttendancePeriodModelList().get(3).getAttendanceMark().equals("P") ? context.getResources().getColor(R.color.green) : context.getResources().getColor(R.color.gray));
        holder.hour5Btn.setBackgroundColor(attendanceViewModel.getAttendancePeriodModelList().get(4).getAttendanceMark().equals("A") ? context.getResources().getColor(R.color.red) : attendanceViewModel.getAttendancePeriodModelList().get(4).getAttendanceMark().equals("P") ? context.getResources().getColor(R.color.green) : context.getResources().getColor(R.color.gray));
        holder.hour6Btn.setBackgroundColor(attendanceViewModel.getAttendancePeriodModelList().get(5).getAttendanceMark().equals("A") ? context.getResources().getColor(R.color.red) : attendanceViewModel.getAttendancePeriodModelList().get(5).getAttendanceMark().equals("P") ? context.getResources().getColor(R.color.green) : context.getResources().getColor(R.color.gray));
        holder.hour7Btn.setBackgroundColor(attendanceViewModel.getAttendancePeriodModelList().get(6).getAttendanceMark().equals("A") ? context.getResources().getColor(R.color.red) : attendanceViewModel.getAttendancePeriodModelList().get(6).getAttendanceMark().equals("P") ? context.getResources().getColor(R.color.green) : context.getResources().getColor(R.color.gray));

    }

    @Override
    public int getItemCount() {
        return attendanceViewModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button nameBtn;
        Button hour1Btn, hour2Btn, hour3Btn, hour4Btn, hour5Btn, hour6Btn, hour7Btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameBtn = itemView.findViewById(R.id.nameBtn);
            hour1Btn = itemView.findViewById(R.id.hour1);
            hour2Btn = itemView.findViewById(R.id.hour2);
            hour3Btn = itemView.findViewById(R.id.hour3);
            hour4Btn = itemView.findViewById(R.id.hour4);
            hour5Btn = itemView.findViewById(R.id.hour5);
            hour6Btn = itemView.findViewById(R.id.hour6);
            hour7Btn = itemView.findViewById(R.id.hour7);
        }
    }
}
