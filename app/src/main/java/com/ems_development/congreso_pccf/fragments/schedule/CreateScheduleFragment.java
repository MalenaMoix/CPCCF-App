package com.ems_development.congreso_pccf.fragments.schedule;

import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.ems_development.congreso_pccf.R;
import com.ems_development.congreso_pccf.data.FirestoreDatabase;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateScheduleFragment extends Fragment {

    private static final String TAG = "SCHEDULE ADAPTER";
    private List<QueryDocumentSnapshot> lecturers = new ArrayList<>();
    private FirestoreDatabase firestoreDatabase;

    private CreateScheduleViewModel createScheduleViewModel;
    private EditText etName;
    private Button btnDate;
    private EditText etDate;
    private Button btnStart;
    private EditText etStart;
    private Button btnEnd;
    private EditText etEnd;
    private int day, month, year, hour, minute;
    private Spinner spinner_lecturers;
    private List<String> listFullnameLecturers = new ArrayList<String>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createScheduleViewModel = ViewModelProviders.of(this).get(CreateScheduleViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_create_schedule, container, false);
        super.onCreate(savedInstanceState);

        firestoreDatabase = new FirestoreDatabase();
        firestoreDatabase.getAllLecturers(handler);

        etName = root.findViewById(R.id.et_name_schedule);
        btnDate = root.findViewById(R.id.btn_date_schedule);
        btnStart = root.findViewById(R.id.btn_start_schedule);
        btnEnd = root.findViewById(R.id.btn_end_schedule);
        etDate = root.findViewById(R.id.et_date_schedule);
        etStart = root.findViewById(R.id.et_start_schedule);
        etEnd = root.findViewById(R.id.et_end_schedule);
        spinner_lecturers = root.findViewById(R.id.spinner_lecturers_create_schedule);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar date = Calendar.getInstance();
                day = date.get(Calendar.DAY_OF_MONTH);
                month = date.get(Calendar.MONTH);
                year = date.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(root.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        etDate.setText(selectedDay + "/" + (selectedMonth+1) + "/" + selectedYear);
                    }
                },day,month,year);
                datePickerDialog.show();
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar date = Calendar.getInstance();
                hour = date.get(Calendar.HOUR_OF_DAY);
                minute = date.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(root.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        etStart.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });

        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar date = Calendar.getInstance();
                hour = date.get(Calendar.HOUR_OF_DAY);
                minute = date.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(root.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        etEnd.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });

        spinner_lecturers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        return root;
    }


    private final Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case FirestoreDatabase.SUCCESS_GETTING_ALL_LECTURERS:
                    Log.d(TAG, "Se recuperaron todas los disertantes.");
                    lecturers = (List<QueryDocumentSnapshot>) msg.obj;

                    for (QueryDocumentSnapshot lecturer : lecturers) {
                        listFullnameLecturers.add(lecturer.get("name") + " " + lecturer.get("lastName"));
                    }

                    spinner_lecturers = getActivity().findViewById(R.id.spinner_lecturers_create_schedule);
                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listFullnameLecturers);
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_lecturers.setAdapter(spinnerAdapter);
                    break;
                case FirestoreDatabase.ERROR_GETTING_ALL_LECTURERS:
                    Log.w(TAG, "Error al recuperar todos los disertantes.");
                    break;
            }
        }
    };
}
