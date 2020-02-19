package com.ems_development.congreso_pccf.fragments.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.ems_development.congreso_pccf.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;


public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        CardView cardView = root.findViewById(R.id.card_view_home);
        cardView.setBackgroundResource(R.drawable.card_view_form_home);

        //TODO colocar esto en la pantalla de inicio de la aplicacion y personalizarla
        FirebaseMessaging.getInstance().subscribeToTopic("general").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.d("FIREBASE MESSAGING", "Subscripcion exitosa.");
                }
                else{
                    Log.w("FIREBASE MESSAGING", "Fallo en la subscripcion");
                }
            }
        });

        return root;
    }
}