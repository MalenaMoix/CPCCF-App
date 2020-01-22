package com.ems_development.congreso_pccf.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems_development.congreso_pccf.R;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleViewHolder> {

    public ScheduleAdapter (){}

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_schedule, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
