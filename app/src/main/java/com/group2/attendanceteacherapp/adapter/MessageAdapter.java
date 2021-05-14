package com.group2.attendanceteacherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.group2.attendanceteacherapp.MessageActivity;
import com.group2.attendanceteacherapp.R;
import com.group2.attendanceteacherapp.model.RequestDetailsModel;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    List<RequestDetailsModel> requestDetailsModelList;
    Context context;
    public MessageAdapter(List<RequestDetailsModel> requestDetailsModelList) {
        this.requestDetailsModelList = requestDetailsModelList;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_card, parent, false);
        return new MessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        RequestDetailsModel requestDetailsModel = requestDetailsModelList.get(position);

        holder.dateText.setText(requestDetailsModel.getDate());
        holder.subjectText.setText(requestDetailsModel.getSubject());
        holder.statusText.setText(requestDetailsModel.getStatus());
        holder.studentIDText.setText(requestDetailsModel.getStudentID());
        holder.cardRequest.setCardBackgroundColor(requestDetailsModel.getStatus().equals("Pending") ? context.getResources().getColor(R.color.Gray) : context.getResources().getColor(R.color.ivory));

        //onClick of the card
        MessageActivity homeActivity = (MessageActivity) context;
        holder.cardRequest.setOnClickListener(view -> homeActivity.ShowRequestDetails(position));
    }

    @Override
    public int getItemCount() {
        return requestDetailsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateText, subjectText, statusText, studentIDText;
        CardView cardRequest;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.dateHourText);
            subjectText = itemView.findViewById(R.id.subjectText);
            statusText = itemView.findViewById(R.id.statusText);
            studentIDText = itemView.findViewById(R.id.studentID);
            cardRequest = itemView.findViewById(R.id.cardRequest);
        }
    }
}
