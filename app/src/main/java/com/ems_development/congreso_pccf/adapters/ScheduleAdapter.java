package com.ems_development.congreso_pccf.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ems_development.congreso_pccf.R;
import com.ems_development.congreso_pccf.data.FirestoreDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import android.os.Handler;
import java.util.logging.LogRecord;


public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleViewHolder> {

    private static final String TAG = "SCHEDULE ADAPTER";
    private static final String NAME = "name";
    private static final String START_DATE = "startDate";
    private static final String END_DATE = "endDate";
    private static final String CHAT_ROOM = "chatRoom";
    private static final String NUMBER_ROOM = "number";
    private static final String LECTURER = "lecturer";
    private List<QueryDocumentSnapshot> chats = new ArrayList<>();
    private QueryDocumentSnapshot chatBeingTreaten;
    private CollectionReference chatRoomCollection;
    private CollectionReference lecturerCollection;
    private FirestoreDatabase firestoreDatabase;
    private List<DocumentSnapshot> chatRoomDocument = new ArrayList<>();



    private final Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case FirestoreDatabase.SUCCESS_GETTING_ALL_CHATS:
                    Log.d(TAG, "Se recuperaron todas las charlas.");
                    chats = (List<QueryDocumentSnapshot>) msg.obj;
                    notifyDataSetChanged();
                    break;
                case FirestoreDatabase.ERROR_GETTING_ALL_CHATS:
                    Log.w(TAG, "Error al recuperar todas las charlas.");
                    break;
            }
        }
    };


    public ScheduleAdapter (){
        firestoreDatabase = new FirestoreDatabase();
        firestoreDatabase.getAllChats(handler);
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_schedule, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        chatBeingTreaten = chats.get(position);
        chatRoomCollection = chatBeingTreaten.getReference().collection(CHAT_ROOM);
        lecturerCollection = chatBeingTreaten.getReference().collection(LECTURER);

        holder.chatName.setText(chatBeingTreaten.get(NAME).toString());
        holder.chatTimeBeginning.setText(chatBeingTreaten.get(START_DATE).toString());
        holder.chatTimeFinish.setText(chatBeingTreaten.get(END_DATE).toString());
        //setChatRoom(holder, chatRoomCollection);
        //setLecturers(holder, lecturerCollection);
    }



    private void setChatRoom (@NonNull ScheduleViewHolder holder, CollectionReference chatRoom){
        //TODO no encuentra nada
        //chatRoomDocument = firestoreDatabase.getChatRoomByChatId(chatBeingTreaten.getId());
        for (DocumentSnapshot documentSnapshot : chatRoomDocument){
            holder.chatPlace.setText(documentSnapshot.get(NUMBER_ROOM).toString());
        }
    }

    private void setLecturers (@NonNull ScheduleViewHolder holder, CollectionReference lecturers){
        //TODO
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }
}