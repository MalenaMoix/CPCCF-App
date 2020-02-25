package com.ems_development.congreso_pccf.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import com.ems_development.congreso_pccf.R;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.ems_development.congreso_pccf.data.FirebaseCloudStorage;
import com.ems_development.congreso_pccf.models.User;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class ViewForAdminUsersActivity extends AppCompatActivity {

    //TODO Cambiar "Novedades" por "Noticias" en el Drawer de Admin y en el Bottom Nav Bar para usuarios comunes

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private AppBarConfiguration mAppBarConfiguration;
    private ImageView profilePicture;
    private View navHeaderAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_for_admin_users);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view_admin_users);

        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_location, R.id.navigation_news,
                R.id.navigation_create_news, R.id.navigation_profile, R.id.navigation_schedule).setDrawerLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_admin);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        //TODO encontrar la imagen
        navHeaderAdmin = navigationView.getHeaderView(0);
        profilePicture = navHeaderAdmin.findViewById(R.id.image_view_current_user);

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });
    }

    private void takePicture (){
        Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (captureImage.resolveActivity(getPackageManager()) != null){
            startActivityForResult(captureImage, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap imageCaptured;
        FirebaseCloudStorage storage = new FirebaseCloudStorage();

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            imageCaptured = (Bitmap) data.getExtras().get("data");
            profilePicture.setImageBitmap(imageCaptured);
            storage.uploadProfilePictureAndSaveUri(imageCaptured);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_admin);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
}