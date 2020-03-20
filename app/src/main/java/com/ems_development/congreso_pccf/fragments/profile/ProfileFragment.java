package com.ems_development.congreso_pccf.fragments.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.ems_development.congreso_pccf.R;
import com.ems_development.congreso_pccf.activities.LogInActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;


public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";
    private Button logOut;
    private ImageView profilePicture;
    private GoogleSignInClient client;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        logOut = root.findViewById(R.id.button_log_out);
        profilePicture = root.findViewById(R.id.profile_picture);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        return root;
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), LogInActivity.class));
    }
}