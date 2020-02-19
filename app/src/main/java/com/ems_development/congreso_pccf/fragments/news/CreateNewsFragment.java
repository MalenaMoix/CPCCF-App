package com.ems_development.congreso_pccf.fragments.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ems_development.congreso_pccf.R;
import com.ems_development.congreso_pccf.activities.ViewForAdminUsersActivity;
import com.ems_development.congreso_pccf.adapters.ScheduleAdapter;
import com.ems_development.congreso_pccf.models.User;


public class CreateNewsFragment extends Fragment {

    private CreateNewsViewModel createNewsViewModel;
    private EditText etTitleNews;
    private EditText etContentNews;
    private Button btnCreateNews;
    private Boolean isValidateData = true;
    private String title;
    private String content;
    private User user;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createNewsViewModel = ViewModelProviders.of(this).get(CreateNewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create_news, container, false);

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
                if(content.isEmpty()) {
                    etContentNews.setError("Se debe agregar contenido a la noticia");
                    isValidateData = false;
                }
                if(isValidateData) {
                    user = ((ViewForAdminUsersActivity)getActivity()).getCurrentUser();
                    createNewsViewModel.createNews(title,content,user);
                }
            }
        });

        title = etTitleNews.getText().toString();
        content = etContentNews.getText().toString();



        //TODO los datos del primer card se encuentran hardcodeados

        return root;
    }
}