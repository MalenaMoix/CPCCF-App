package com.ems_development.congreso_pccf.data;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;


public class FirebaseCloudStorage {

    private static final String TAG = "FirebaseCloudStorage";
    private FirebaseStorage storageInstance;
    private StorageReference storageReference;
    private ByteArrayOutputStream baos;


    public FirebaseCloudStorage (){
        storageInstance = FirebaseStorage.getInstance();
    }

    public void uploadProfilePictureAndSaveUri (Bitmap imageCaptured){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        baos = new ByteArrayOutputStream();
        imageCaptured.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        storageReference = storageInstance.getReference().child("profilePictures").child(uid + ".jpeg");

        storageReference.putBytes(baos.toByteArray()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                getDownloadUrl(storageReference);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: ", e.getCause());
            }
        });
    }

    private void getDownloadUrl (StorageReference reference){
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d(TAG, "onSuccess: " + uri);
                setUserProfileUrl(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void setUserProfileUrl (Uri uri){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setPhotoUri(uri).build();
        user.updateProfile(request).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(FirebaseCloudStorage.this, "El guardado de la foto de perfil ha fallado.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}