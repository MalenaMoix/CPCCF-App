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
import com.ems_development.congreso_pccf.adapters.ScheduleAdapter;


public class CreateNewsFragment extends Fragment {

    private CreateNewsViewModel createNewsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createNewsViewModel = ViewModelProviders.of(this).get(CreateNewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create_news, container, false);

        //TODO los datos del primer card se encuentran hardcodeados

        return root;
    }
}