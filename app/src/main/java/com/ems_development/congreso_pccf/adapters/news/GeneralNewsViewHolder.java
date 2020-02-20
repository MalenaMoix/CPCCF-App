package com.ems_development.congreso_pccf.adapters.news;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ems_development.congreso_pccf.R;


public class GeneralNewsViewHolder extends RecyclerView.ViewHolder {
    public TextView txtTitle, txtContent;
    public ImageView imageNews;

    public GeneralNewsViewHolder(@NonNull View base){
        super(base);
        txtTitle = base.findViewById(R.id.txt_title);
        txtContent = base.findViewById(R.id.txt_content);
    }
}
