package com.ems_development.congreso_pccf.fragments.location;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.ems_development.congreso_pccf.R;


public class LocationFragment extends Fragment {

    private LocationViewModel locationViewModel;
    private View root;
    //TODO luego eliminar este boton y ver la manera en la cual se accedera al mapa
    private Button verMapa;


    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        root = inflater.inflate(R.layout.fragment_location, container, false);

        verMapa = root.findViewById(R.id.button);
        verMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new MapFragment()).addToBackStack(null).commit();
            }
        });

        return root;
    }
}