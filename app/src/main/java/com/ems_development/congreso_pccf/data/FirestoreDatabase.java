package com.ems_development.congreso_pccf.data;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;


public class FirestoreDatabase {
    private static final String TAG = "DATABASE CLASS";
    private static final String CHATS = "chats";
    public static final int ERROR_GETTING_ALL_CHATS = -1;
    public static final int ERROR_GETTING_ALL_LECTURERS = -2;
    public static final int SUCCESS_GETTING_ALL_CHATS = 10;
    public static final int SUCCESS_GETTING_ALL_LECTURERS = 11;

    private FirebaseFirestore firestoreInstance;
    private List<QueryDocumentSnapshot> collectionChats = new ArrayList<>();

    public FirestoreDatabase(){
        firestoreInstance = FirebaseFirestore.getInstance();
    }


    public void getAllChats(final Handler handler){
        firestoreInstance.collection(CHATS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        collectionChats.add(document);
                    }

                    Message message = Message.obtain();
                    message.what = SUCCESS_GETTING_ALL_CHATS;
                    message.obj = collectionChats;
                    handler.sendMessage(message);

                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                    Message message = Message.obtain();
                    message.what = ERROR_GETTING_ALL_CHATS;
                    handler.sendMessage(message);
                }
            }
        });
    }

    public void getAllLecturers (final Handler handler){
        //TODO getAllLecturers
        CollectionReference collectionChats = firestoreInstance.collection(CHATS);

    }
}