package com.ems_development.congreso_pccf.fragments.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.ems_development.congreso_pccf.R;
import com.ems_development.congreso_pccf.activities.LogInActivity;
import com.ems_development.congreso_pccf.data.FirebaseCloudStorage;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Button logOut;
    private ImageView profilePicture;
    private TextView userEmail;
    private FirebaseUser user;
    private GoogleSignInAccount account;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        logOut = root.findViewById(R.id.button_log_out);
        profilePicture = root.findViewById(R.id.profile_picture);
        userEmail = root.findViewById(R.id.text_view_gmail);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            if (user.getPhotoUrl() != null){
                Glide.with(this).load(user.getPhotoUrl()).into(profilePicture);
            }
            if (user.getEmail() != null){
                userEmail.setText(user.getEmail());
            }
        } else {
            account = GoogleSignIn.getLastSignedInAccount(getContext());
            if (account != null){
                if (account.getPhotoUrl() != null){
                    Glide.with(this).load(account.getPhotoUrl()).into(profilePicture);
                }
                if (account.getEmail() != null){
                    userEmail.setText(account.getEmail());
                }
            }
        }


        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });

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

    private void takePicture (){
        Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (captureImage.resolveActivity(getActivity().getPackageManager()) != null){
            startActivityForResult(captureImage, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap imageCaptured;
        FirebaseCloudStorage storage = new FirebaseCloudStorage();

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            imageCaptured = (Bitmap) data.getExtras().get("data");
            profilePicture.setImageBitmap(imageCaptured);
            storage.uploadProfilePictureAndSaveUri(imageCaptured);
        }
    }
}