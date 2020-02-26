package com.ems_development.congreso_pccf.fragments.schedule;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.ems_development.congreso_pccf.R;
import com.ems_development.congreso_pccf.data.FirestoreDatabase;
import com.ems_development.congreso_pccf.fragments.home.HomeFragment;
import com.ems_development.congreso_pccf.models.Chat;
import com.ems_development.congreso_pccf.models.Lecturer;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateScheduleFragment extends Fragment {

    private static final String TAG = "SCHEDULE ADAPTER";
    private List<QueryDocumentSnapshot> lecturers = new ArrayList<>();
    private List<Lecturer> lecturersSaved = new ArrayList<>();
    private FirestoreDatabase firestoreDatabase;

    private CreateScheduleViewModel createScheduleViewModel;
    private Button btnCreateNews, ok, cancel;
    private EditText etName, etChatRoom;
    private Button btnDate;
    private EditText etDate;
    private Button btnStart;
    private EditText etStart;
    private Button btnEnd;
    private EditText etEnd;
    private int day, month, year, hourStart, hourEnd, minuteStart, minuteEnd;
    private LocalTime startDate;
    private LocalTime endDate;
    private String[] listFullnameLecturers;
    private boolean[] checkedLecturers;
    private List<Integer> selectedLecturers = new ArrayList<>();
    private Button btnLecturers;
    private EditText etSelectedLecturers;
    private Button btnCreateSchedule;
    private Boolean isValidateData = true;
    private String name, date, starthour, endHour, lecturerList, chatRoom;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createScheduleViewModel = ViewModelProviders.of(this).get(CreateScheduleViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_create_schedule, container, false);
        super.onCreate(savedInstanceState);

        firestoreDatabase = new FirestoreDatabase();
        firestoreDatabase.getAllLecturers(handler);

        etName = root.findViewById(R.id.et_name_schedule);
        etChatRoom = root.findViewById(R.id.editText_chat_room);
        btnDate = root.findViewById(R.id.btn_date_schedule);
        btnStart = root.findViewById(R.id.btn_start_schedule);
        btnEnd = root.findViewById(R.id.btn_end_schedule);
        //etDate = root.findViewById(R.id.et_date_schedule);
        etStart = root.findViewById(R.id.et_start_schedule);
        etEnd = root.findViewById(R.id.et_end_schedule);
        btnLecturers = root.findViewById(R.id.btn_select_lecturers);
        etSelectedLecturers = root.findViewById(R.id.et_selected_lecturers);
        btnCreateSchedule = root.findViewById(R.id.btn_create_schedule);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar date = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(root.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        //etDate.setText(selectedDay + "/" + (selectedMonth+1) + "/" + selectedYear);
                        //day = selectedDay;
                        //month = selectedMonth-1;
                        //year = selectedYear;
                    }
                },date.get(Calendar.DAY_OF_MONTH),date.get(Calendar.MONTH),date.get(Calendar.YEAR));
                datePickerDialog.show();
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar date = Calendar.getInstance();

                TimePickerDialog timePickerDialog = new TimePickerDialog(root.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        etStart.setText(selectedHour + ":" + selectedMinute);
                        hourStart = selectedHour;
                        minuteStart = selectedMinute;
                    }
                }, date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });

        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar date = Calendar.getInstance();

                TimePickerDialog timePickerDialog = new TimePickerDialog(root.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        etEnd.setText(selectedHour + ":" + selectedMinute);
                        hourEnd = selectedHour;
                        minuteEnd = selectedMinute;
                    }
                }, date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });

        btnLecturers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                builder.setTitle("Selecione los disertantes para la charla");
                builder.setMultiChoiceItems(listFullnameLecturers, checkedLecturers, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked) {
                            if(!selectedLecturers.contains(position)) {
                                selectedLecturers.add(position);
                            }
                        } else if (selectedLecturers.contains(position)) {
                            selectedLecturers.remove(position);
                        }
                    }
                });

                builder.setCancelable(false);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        String item = "";
                        for (int i = 0; i < selectedLecturers.size(); i++) {
                            item = (item + listFullnameLecturers[selectedLecturers.get(i)]);
                            if (i != selectedLecturers.size() - 1) {
                                item = item  + "\n";
                            }
                        }
                        etSelectedLecturers.setText(item);
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Quitar todos", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedLecturers.length; i++) {
                            checkedLecturers[i] = false;
                            selectedLecturers.clear();
                            etSelectedLecturers.setText("");
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btnCreateSchedule.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                name = etName.getText().toString();
                chatRoom = etChatRoom.getText().toString();
                //date = etDate.getText().toString();
                starthour = etStart.getText().toString();
                endHour = etEnd.getText().toString();
                lecturerList = etSelectedLecturers.getText().toString();

                if (name.isEmpty()) {
                    etName.setError("Se debe agregar un nombre a la charla");
                    isValidateData = false;
                }
                if (chatRoom.isEmpty()) {
                    etName.setError("Se debe agregar un lugar a la charla");
                    isValidateData = false;
                }
                /*if (date.isEmpty()) {
                    etDate.setError("Se debe seleccionar una fecha para la charla");
                    isValidateData = false;
                }*/
                if (starthour.isEmpty()) {
                    etStart.setError("Se debe seleccionar una hora de inicio para la charla");
                    isValidateData = false;
                }
                if (endHour.isEmpty()) {
                    etEnd.setError("Se debe seleccionar una hora de fin para la charla");
                    isValidateData = false;
                }
                if (lecturers.isEmpty()) {
                    etSelectedLecturers.setError("Se debe seleccionar algun disertante para la charla");
                    isValidateData = false;
                }
                if (isValidateData) {
                    showAlertDialogForConfirmation();
                }
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
                    listFullnameLecturers = new String[lecturers.size()];
                    for (int i = 0; i < lecturers.size(); i++) {
                        listFullnameLecturers[i] = lecturers.get(i).get("name") + " " + lecturers.get(i).get("lastName");
                    }
                    checkedLecturers = new boolean[listFullnameLecturers.length];

                    break;
                case FirestoreDatabase.ERROR_GETTING_ALL_LECTURERS:
                    Log.w(TAG, "Error al recuperar todos los disertantes.");
                    break;
            }
        }
    };

    private void showAlertDialogForConfirmation() {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_create_chat, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        ok = dialogView.findViewById(R.id.button_ok_chat);
        cancel = dialogView.findViewById(R.id.button_cancel_chat);

        for(int i = 0; i < selectedLecturers.size(); i++) {
            Lecturer lecturer = new Lecturer();
            lecturer.setName(lecturers.get(selectedLecturers.get(i)).get("name").toString());
            lecturer.setBirthDate(lecturers.get(selectedLecturers.get(i)).get("birthDate").toString());
            lecturer.setLastName(lecturers.get(selectedLecturers.get(i)).get("lastName").toString());
            lecturer.setUniversityDegrees(lecturers.get(selectedLecturers.get(i)).get("universityDegrees").toString());
            lecturer.setLocation(lecturers.get(selectedLecturers.get(i)).get("location").toString());
            lecturersSaved.add(lecturer);
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chat newSchedule = new Chat(name, starthour, endHour, chatRoom, lecturersSaved);
                firestoreDatabase.saveChat(handler, newSchedule);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_admin, new HomeFragment()).addToBackStack(null).commit();
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
