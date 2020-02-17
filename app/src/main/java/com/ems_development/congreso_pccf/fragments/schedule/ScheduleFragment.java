package com.ems_development.congreso_pccf.fragments.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ems_development.congreso_pccf.R;
import com.ems_development.congreso_pccf.adapters.ScheduleAdapter;
import com.ems_development.congreso_pccf.data.FirestoreDatabase;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;


public class ScheduleFragment extends Fragment {

    //TODO ver tema de que cuando se agreguen muchas charlas, la ultima que aarezca no se encuentre tapada por el bottom nav bar

    private static final String TAG = "SCHEDULE FRAGMENT";
    private ScheduleViewModel scheduleViewModel;
    private RecyclerView scheduleRecyclerView;
    private RecyclerView.Adapter scheduleAdapter;
    private RecyclerView.ViewHolder scheduleViewHolder;
    private RecyclerView.LayoutManager layoutManager;
    private FirestoreDatabase firestoreDatabase = new FirestoreDatabase();
    private List<QueryDocumentSnapshot> chats = new ArrayList<>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        scheduleViewModel = ViewModelProviders.of(this).get(ScheduleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_schedule, container, false);

        scheduleRecyclerView = root.findViewById(R.id.schedule_recycler_view);
        scheduleRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(root.getContext());
        scheduleRecyclerView.setLayoutManager(layoutManager);

        scheduleAdapter = new ScheduleAdapter();
        scheduleRecyclerView.setAdapter(scheduleAdapter);

        //TODO los datos del primer card se encuentran hardcodeados

        return root;
    }
}