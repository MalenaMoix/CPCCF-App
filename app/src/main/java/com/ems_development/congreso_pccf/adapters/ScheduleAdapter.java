package com.ems_development.congreso_pccf.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ems_development.congreso_pccf.R;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.List;


public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleViewHolder> {

    private List<QueryDocumentSnapshot> chats;
    private QueryDocumentSnapshot document;

    public ScheduleAdapter (List<QueryDocumentSnapshot> chatsObtained){
        chats = chatsObtained;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_schedule, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        document = chats.get(position);
        holder.chatName.setText(document.get("name").toString());
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }
}
