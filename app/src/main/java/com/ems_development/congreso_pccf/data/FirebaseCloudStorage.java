package com.ems_development.congreso_pccf.data;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class FirebaseCloudStorage {

    public FirebaseCloudStorage (){
        FirebaseStorage storageInstance = FirebaseStorage.getInstance();
        StorageReference storageReference = storageInstance.getReference();
    }


}
