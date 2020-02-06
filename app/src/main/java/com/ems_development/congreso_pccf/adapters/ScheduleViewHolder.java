package com.ems_development.congreso_pccf.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ems_development.congreso_pccf.R;


public class ScheduleViewHolder extends RecyclerView.ViewHolder {
    public TextView chatTimeBeginning, chatTimeFinish, chatLecturer, chatName, chatPlace;
    public ImageView placeHolder;

    public ScheduleViewHolder (@NonNull View base){
        super(base);
        chatLecturer = base.findViewById(R.id.editText_lecturer);
        chatName = base.findViewById(R.id.editText_chat_name);
        chatPlace = base.findViewById(R.id.editText_chat_place);
        chatTimeBeginning = base.findViewById(R.id.editText_chat_time_beginning);
        chatTimeFinish = base.findViewById(R.id.editText_chat_time_finish);
        placeHolder = base.findViewById(R.id.imageView_place_holder);
    }
}