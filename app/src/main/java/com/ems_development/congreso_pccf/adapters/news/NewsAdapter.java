package com.ems_development.congreso_pccf.adapters.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ems_development.congreso_pccf.R;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private Boolean isLecturerNews;

    public NewsAdapter(Boolean isLecturerNews){
        this.isLecturerNews = isLecturerNews;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        if(isLecturerNews) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_lecturer, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_news, parent, false);
        }
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
        //TODO retornar el size de la lista de noticias
    }
}
