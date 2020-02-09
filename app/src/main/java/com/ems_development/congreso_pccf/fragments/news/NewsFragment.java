package com.ems_development.congreso_pccf.fragments.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_news, container, false);

        newsRecyclerView = root.findViewById(R.id.news_recycler_view);
        newsRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(root.getContext());
        newsRecyclerView.setLayoutManager(layoutManager);

        newsAdapter = new NewsAdapter();
        newsRecyclerView.setAdapter(newsAdapter);
        //TODO los datos del primer card se encuentran hardcodeados

        return root;
    }
}