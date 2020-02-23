package com.ems_development.congreso_pccf.fragments.news;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.ems_development.congreso_pccf.R;
import com.ems_development.congreso_pccf.activities.ViewForAdminUsersActivity;
import com.ems_development.congreso_pccf.data.FirestoreDatabase;
import com.ems_development.congreso_pccf.fragments.home.HomeFragment;
import com.ems_development.congreso_pccf.models.News;
import com.ems_development.congreso_pccf.models.User;


public class CreateNewsFragment extends Fragment {

    private static final String TAG = "CREATE NEWS FRAGMENT";
    private CreateNewsViewModel createNewsViewModel;
    private EditText etTitleNews;
    private EditText etContentNews;
    private Button btnCreateNews;
    private Boolean isValidateData = true;
    private String title;
    private String content;
    private FirestoreDatabase firestoreDatabase;
    private User user;
    private News newNews;

    private final Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case FirestoreDatabase.SUCCESS_SAVING_NEWS:
                    Log.d(TAG, "Successful saving news");
                    Toast.makeText(getContext(), "La noticia fue creada exitosamente", Toast.LENGTH_SHORT).show();
                    break;
                case FirestoreDatabase.ERROR_SAVING_NEWS:
                    Log.w(TAG, "Error saving news.");
                    Toast.makeText(getContext(), "Se produjo un error al intentar crear la noticia", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        firestoreDatabase = new FirestoreDatabase();

        createNewsViewModel = ViewModelProviders.of(this).get(CreateNewsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_create_news, container, false);

        etTitleNews = root.findViewById(R.id.et_title_news);
        etContentNews = root.findViewById(R.id.et_content_news);
        btnCreateNews = root.findViewById(R.id.btn_create_news);

        btnCreateNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = etTitleNews.getText().toString();
                content = etContentNews.getText().toString();

                if(title.isEmpty()) {
                    etTitleNews.setError("Se debe agregar un titulo a la noticia");
                    isValidateData = false;
                }
                else if(content.isEmpty()) {
                    etContentNews.setError("Se debe agregar contenido a la noticia");
                    isValidateData = false;
                }
                if(isValidateData) {
                    //user = ((ViewForAdminUsersActivity)getActivity()).getCurrentUser();
                    //createNewsViewModel.createNews(title,content,user);
                    showAlertDialogForConfirmation();
                }
            }
        });

        title = etTitleNews.getText().toString();
        content = etContentNews.getText().toString();

        return root;
    }


    private void showAlertDialogForConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_create_news, null));

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                newNews = new News(title, content);
                firestoreDatabase.saveNews(handler, newNews);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_admin, new HomeFragment()).addToBackStack(null).commit();
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}