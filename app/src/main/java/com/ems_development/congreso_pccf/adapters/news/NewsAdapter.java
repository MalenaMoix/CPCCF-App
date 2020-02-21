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


public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "NEWS ADAPTER";
    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String UNIVERSITY_DEGREES = "universityDegrees";
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

    @Override
    public int getItemViewType(int positionViewType) {
        if (isLecturerNews){
            positionViewType = 2;
        }
        else {
            positionViewType = 0;
        }
        return positionViewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (isLecturerNews) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_lecturers_news, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_general_news, parent, false);
        }

        if (viewType == 0){
            return new GeneralNewsViewHolder(view);
        }
        else {
            return new LecturersNewsViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (isLecturerNews){
            newsBeingTreaten = allLecturersNews.get(position);
        }
        else {
            newsBeingTreaten = allGeneralNews.get(position);
        }

        switch (holder.getItemViewType()){
            case 0:
                GeneralNewsViewHolder generalNews = (GeneralNewsViewHolder) holder;
                generalNews.txtTitle.setText(newsBeingTreaten.get(TITLE).toString());
                generalNews.txtContent.setText(newsBeingTreaten.get(CONTENT).toString());
                break;
            case 2:
                LecturersNewsViewHolder lecturerNews = (LecturersNewsViewHolder) holder;
                lecturerNews.txtTitle.setText(newsBeingTreaten.get(TITLE).toString());
                lecturerNews.txtContent.setText(newsBeingTreaten.get(CONTENT).toString());
                lecturerNews.txtUniversityDegrees.setText(newsBeingTreaten.get(UNIVERSITY_DEGREES).toString());
                break;
        }
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