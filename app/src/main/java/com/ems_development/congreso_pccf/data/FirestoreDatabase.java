package com.ems_development.congreso_pccf.data;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;

import com.ems_development.congreso_pccf.models.Chat;
import com.ems_development.congreso_pccf.models.Lecturer;
import com.ems_development.congreso_pccf.models.News;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;


public class FirestoreDatabase {
    private static final String TAG = "DATABASE CLASS";
    private static final String CHATS = "chats";
    private static final String START_DATE = "startDate";
    private static final String END_DATE = "endDate";
    private static final String LECTURER = "lecturer";
    private static final String GENERAL_NEWS = "generalNews";
    private static final String LECTURERS_NEWS = "news";
    private static final String ADMINS = "admins";

    public static final int ERROR_GETTING_ALL_CHATS = -1;
    public static final int ERROR_GETTING_ALL_LECTURERS = -2;
    public static final int ERROR_GETTING_ALL_GENERAL_NEWS = -3;
    public static final int ERROR_GETTING_ALL_LECTURERS_NEWS = -4;
    public static final int ERROR_SAVING_NEWS = -5;
    public static final int ERROR_GETTING_ADMINS = -6;
    public static final int ERROR_SAVING_CHAT = -7;
    public static final int SUCCESS_GETTING_ALL_CHATS = 10;
    public static final int SUCCESS_GETTING_ALL_LECTURERS = 11;
    public static final int SUCCESS_GETTING_ALL_GENERAL_NEWS = 12;
    public static final int SUCCESS_GETTING_ALL_LECTURERS_NEWS = 13;
    public static final int SUCCESS_SAVING_NEWS = 14;
    public static final int SUCCESS_GETTING_ADMINS = 15;
    public static final int SUCCESS_SAVING_CHAT = 16;

    private FirebaseFirestore firestoreInstance;
    private List<QueryDocumentSnapshot> collectionChats = new ArrayList<>();
    private List<QueryDocumentSnapshot> allLecturers = new ArrayList<>();
    private List<QueryDocumentSnapshot> allGeneralNews = new ArrayList<>();
    private List<QueryDocumentSnapshot> allLecturersNews = new ArrayList<>();
    private List<QueryDocumentSnapshot> admins = new ArrayList<>();

    public FirestoreDatabase(){
        firestoreInstance = FirebaseFirestore.getInstance();
    }


    public void getAllChats(final Handler handler){
        firestoreInstance.collection(CHATS).orderBy("startDate").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                }
                else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                    Message message = Message.obtain();
                    message.what = ERROR_GETTING_ALL_CHATS;
                    handler.sendMessage(message);
                }
            }
        });
    }

    public void getAllLecturers (final Handler handler){
        firestoreInstance.collectionGroup(LECTURER).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot lecturer : task.getResult()){
                        Log.d(TAG, lecturer.getId() + " => " + lecturer.getData());
                        allLecturers.add(lecturer);
                    }
                    Message message = Message.obtain();
                    message.what = SUCCESS_GETTING_ALL_LECTURERS;
                    message.obj = allLecturers;
                    handler.sendMessage(message);
                }
                else{
                    Log.w(TAG, "Error getting lecturers.", task.getException());
                    Message message = Message.obtain();
                    message.what = ERROR_GETTING_ALL_LECTURERS;
                    handler.sendMessage(message);
                }
            }
        });
    }

    public void getAllGeneralNews (final Handler handler){
        firestoreInstance.collection(GENERAL_NEWS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot generalNew : task.getResult()) {
                        Log.d(TAG, generalNew.getId() + " => " + generalNew.getData());
                        allGeneralNews.add(generalNew);
                    }
                    Message message = Message.obtain();
                    message.what = SUCCESS_GETTING_ALL_GENERAL_NEWS;
                    message.obj = allGeneralNews;
                    handler.sendMessage(message);
                }
                else {
                    Log.w(TAG, "Error getting general news.", task.getException());
                    Message message = Message.obtain();
                    message.what = ERROR_GETTING_ALL_GENERAL_NEWS;
                    handler.sendMessage(message);
                }
            }
        });
    }

    public void getAllLecturersNews (final Handler handler){
        firestoreInstance.collectionGroup(LECTURERS_NEWS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot lecturerNew : task.getResult()){
                        Log.d(TAG, lecturerNew.getId() + " => " + lecturerNew.getData());
                        allLecturersNews.add(lecturerNew);
                    }
                    Message message = Message.obtain();
                    message.what = SUCCESS_GETTING_ALL_LECTURERS_NEWS;
                    message.obj = allLecturersNews;
                    handler.sendMessage(message);
                }
                else{
                    Log.w(TAG, "Error getting lecturers news.", task.getException());
                    Message message = Message.obtain();
                    message.what = ERROR_GETTING_ALL_LECTURERS_NEWS;
                    handler.sendMessage(message);
                }
            }
        });
    }

    public void saveNews (final Handler handler, News news){
        firestoreInstance.collection(GENERAL_NEWS).document().set(news).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Message message = Message.obtain();
                    message.what = SUCCESS_SAVING_NEWS;
                    handler.sendMessage(message);
                }
                else {
                    Message message = Message.obtain();
                    message.what = ERROR_SAVING_NEWS;
                    handler.sendMessage(message);
                }
            }
        });
    }

    public void getAdmins (final Handler handler){
        firestoreInstance.collection(ADMINS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot admin : task.getResult()) {
                        Log.d(TAG, admin.getId() + " => " + admin.getData());
                        admins.add(admin);
                    }
                    Message message = Message.obtain();
                    message.what = SUCCESS_GETTING_ADMINS;
                    message.obj = admins;
                    handler.sendMessage(message);
                }
                else {
                    Log.w(TAG, "Error getting admins.", task.getException());
                    Message message = Message.obtain();
                    message.what = ERROR_GETTING_ADMINS;
                    handler.sendMessage(message);
                }
            }
        });
    }

    public void saveChat (final Handler handler, final Chat newChat, final List<Lecturer> lecturers) {
        // TODO ver por que guarda null en lastName y universityDegrees de los lescturers, eso hace que se rompa todo cuando se intentan
        //  ver las charlas
        // TODO ademas al estar agregando mas disertantes los cuando se los busque (ya que busca a absolutamente todos) no se muestren
        //  repetidos cuando se pida seleccionar disertantes al crear una charla

        final CollectionReference chatsReference = firestoreInstance.collection(CHATS);

        chatsReference.document().set(newChat).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Message message = Message.obtain();
                    message.what = SUCCESS_SAVING_CHAT;
                    handler.sendMessage(message);
                }
                else {
                    Message message = Message.obtain();
                    message.what = ERROR_SAVING_CHAT;
                    handler.sendMessage(message);
                }
            }
        });

        chatsReference.whereEqualTo("name", newChat.getName()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String idChatDocument = "";
                Log.d(TAG, "Se ha encontrado el documento con el nombre especificado");
                for (DocumentSnapshot documentChatSnapshot : queryDocumentSnapshots){
                    idChatDocument = documentChatSnapshot.getId();
                }
                for (Lecturer lecturer : lecturers){
                    chatsReference.document(idChatDocument).collection(LECTURER).document().set(lecturer).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "El disertante se ha agregado con exito");
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "No se encontro el documento con el nombre especificado");
            }
        });
    }
}