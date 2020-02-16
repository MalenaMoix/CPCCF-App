package com.ems_development.congreso_pccf.data;

import android.os.SystemClock;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;


public class FirestoreDatabase {
    private static final String TAG = "DATABASE CLASS";
    private FirebaseFirestore firestoreInstance;
    private List<QueryDocumentSnapshot> collectionChats = new ArrayList<>();

    public FirestoreDatabase(){
        firestoreInstance = FirebaseFirestore.getInstance();
    }


    //TODO agregar todos los metodos para insertar u obtener datos de Firestore
    public List<QueryDocumentSnapshot> getAllChats(){
        firestoreInstance.collection("chats").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        collectionChats.add(document);
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });

        //TODO manejar la devolucion de datos de manera asincrona con un Handler, ahora esta hecho asi solo para probar si se obtenia o no el documento
        SystemClock.sleep(5000);
        return collectionChats;
    }
}