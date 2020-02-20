package com.ems_development.congreso_pccf.fragments.news;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ems_development.congreso_pccf.R;
import com.ems_development.congreso_pccf.adapters.news.NewsAdapter;

public class NewsFragment extends Fragment {

    private NewsViewModel newsViewModel;
    private RecyclerView newsRecyclerView;
    private RecyclerView.Adapter newsAdapter;
    private RecyclerView.ViewHolder newsViewHolder;
    private RecyclerView.LayoutManager layoutManager;
    private Button buttonGeneralNews;
    private Button buttonLecturerNews;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_news, container, false);

        newsRecyclerView = root.findViewById(R.id.news_recycler_view);
        newsRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(root.getContext());
        newsRecyclerView.setLayoutManager(layoutManager);
        buttonGeneralNews = root.findViewById(R.id.btn_general_new);
        buttonLecturerNews = root.findViewById(R.id.btn_lecturer_news);

        newsAdapter = new NewsAdapter(false);
        newsRecyclerView.setAdapter(newsAdapter);
        //TODO los datos del primer card se encuentran hardcodeados

        buttonGeneralNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonGeneralNews.setEnabled(false);
                buttonGeneralNews.setBackgroundResource(R.drawable.button_news_on);
                buttonGeneralNews.setTextColor(getResources().getColor(R.color.white));
                buttonLecturerNews.setBackgroundResource(R.drawable.button_lecturer_off);
                buttonLecturerNews.setTextColor(getResources().getColor(R.color.pink));
                buttonLecturerNews.setEnabled(true);
                newsAdapter = new NewsAdapter(false);
                newsRecyclerView.setAdapter(newsAdapter);
            }
        });

        buttonLecturerNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonLecturerNews.setEnabled(false);
                buttonLecturerNews.setBackgroundResource(R.drawable.button_lecturer_on);
                buttonLecturerNews.setTextColor(getResources().getColor(R.color.white));
                buttonGeneralNews.setBackgroundResource(R.drawable.button_news_off);
                buttonGeneralNews.setTextColor(getResources().getColor(R.color.pink));
                buttonGeneralNews.setEnabled(true);
                newsAdapter = new NewsAdapter(true);
                newsRecyclerView.setAdapter(newsAdapter);
            }
        });

        return root;
    }


}