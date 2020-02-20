package com.ems_development.congreso_pccf.adapters.news;

import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ems_development.congreso_pccf.R;
import com.ems_development.congreso_pccf.data.FirestoreDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;
import android.os.Handler;
import android.widget.ProgressBar;


public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private static final String TAG = "NEWS ADAPTER";
    private Boolean isLecturerNews;
    private ProgressBar loadingPanel;
    private FirestoreDatabase firestoreDatabase;
    private DocumentSnapshot newsBeingTreaten;
    private List<QueryDocumentSnapshot> allGeneralNews = new ArrayList<>();
    private List<QueryDocumentSnapshot> allLecturersNews = new ArrayList<>();

    private final Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case FirestoreDatabase.SUCCESS_GETTING_ALL_GENERAL_NEWS:
                    Log.d(TAG, "Se recuperaron todas las noticias generales.");
                    allGeneralNews = (List<QueryDocumentSnapshot>) msg.obj;
                    notifyDataSetChanged();
                    break;
                case FirestoreDatabase.ERROR_GETTING_ALL_GENERAL_NEWS:
                    Log.w(TAG, "Error al recuperar todas las noticias generales.");
                    break;
                case FirestoreDatabase.SUCCESS_GETTING_ALL_LECTURERS_NEWS:
                    Log.d(TAG, "Se recuperaron todas las noticias de los disertantes.");
                    allLecturersNews = (List<QueryDocumentSnapshot>) msg.obj;
                    notifyDataSetChanged();
                    loadingPanel.setVisibility(View.GONE);
                    break;
                case FirestoreDatabase.ERROR_GETTING_ALL_LECTURERS_NEWS:
                    Log.w(TAG, "Error al recuperar todas las noticias de los disertantes.");
                    break;
            }
        }
    };

    public NewsAdapter(Boolean isLecturerNews, ProgressBar progressBar){
        this.isLecturerNews = isLecturerNews;
        this.loadingPanel = progressBar;

        firestoreDatabase = new FirestoreDatabase();
        firestoreDatabase.getAllGeneralNews(handler);
        firestoreDatabase.getAllLecturersNews(handler);
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (isLecturerNews) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_lecturers_news, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_general_news, parent, false);
        }
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        if (isLecturerNews){
            newsBeingTreaten = allLecturersNews.get(position);
        }
        else {
            newsBeingTreaten = allGeneralNews.get(position);
        }

        holder.txtTitle.setText(newsBeingTreaten.get("title").toString());
        holder.txtContent.setText(newsBeingTreaten.get("content").toString());
    }

    @Override
    public int getItemCount() {
        if (isLecturerNews){
            return allLecturersNews.size();
        }
        else {
            return allGeneralNews.size();
        }
    }
}
