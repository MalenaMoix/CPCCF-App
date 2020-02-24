package com.ems_development.congreso_pccf.adapters.news;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ems_development.congreso_pccf.R;


public class LecturersNewsViewHolder extends RecyclerView.ViewHolder {
    public TextView txtTitle, txtContent, txtUniversityDegrees;
    public ImageView imageProfile;

    public LecturersNewsViewHolder(@NonNull View base){
        super(base);
        txtTitle = base.findViewById(R.id.full_name_lecturer);
        txtContent = base.findViewById(R.id.txt_lecturer_content);
        txtUniversityDegrees = base.findViewById(R.id.txt_lecturer_degrees);
    }
}
