package com.ems_development.congreso_pccf.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


import com.ems_development.congreso_pccf.R;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        CardView cardView = root.findViewById(R.id.card_view_home);
        cardView.setBackgroundResource(R.drawable.card_view_form_home);

        return root;
    }


}