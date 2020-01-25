package com.ems_development.congreso_pccf.fragments.schedule_of_talks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.ems_development.congreso_pccf.R;

public class ScheduleOfTalks extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_schedule_of_talks, container, false);
        return root;
    }
}
