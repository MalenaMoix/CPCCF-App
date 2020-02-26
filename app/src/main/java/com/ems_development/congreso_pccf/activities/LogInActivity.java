package com.ems_development.congreso_pccf.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.ems_development.congreso_pccf.R;
import com.ems_development.congreso_pccf.data.FirestoreDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import java.util.ArrayList;
import java.util.List;


public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Log In Activity";
    public static final int RESET_PASSWORD = 1;
    private static int isAdmin = 0;
    private FirebaseAuth mAuth;
    private FirestoreDatabase firestoreDatabase;
    private EditText emailField, passwordField;
    private List<QueryDocumentSnapshot> admins = new ArrayList<>();

    private Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case FirestoreDatabase.SUCCESS_GETTING_ADMINS:
                    Log.d(TAG, "Se recuperaron todos los admins");
                    admins = (List<QueryDocumentSnapshot>) msg.obj;
                    break;
                case FirestoreDatabase.ERROR_GETTING_ADMINS:
                    Log.w(TAG, "Error al recuperar los admins");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firestoreDatabase = new FirestoreDatabase();
        firestoreDatabase.getAdmins(handler);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);

        findViewById(R.id.button_continue).setOnClickListener(this);
        findViewById(R.id.forgot_password).setOnClickListener(this);
        findViewById(R.id.create_account).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        FirebaseMessaging.getInstance().subscribeToTopic("general").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Log.d("FIREBASE MESSAGING", "Subscripcion exitosa.");
                }
                else{
                    Log.w("FIREBASE MESSAGING", "Fallo en la subscripcion");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isAdmin = 0;
        emailField.requestFocus();
        emailField.setText(null);
        passwordField.setText(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent resultIntent) {
        super.onActivityResult(requestCode, resultCode, resultIntent);
        if (requestCode == RESET_PASSWORD){
            if (resultCode == Activity.RESULT_OK){
                Toast.makeText(LogInActivity.this, "Chequee su email", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();

                    for (DocumentSnapshot admin : admins){
                        if (admin.get("email").equals(user.getEmail())){
                            isAdmin = 1;
                        }
                    }

                    if (isAdmin == 1){
                        startActivity(new Intent(LogInActivity.this, ViewForAdminUsersActivity.class));
                    }
                    else {
                        startActivity(new Intent(LogInActivity.this, BottomNavigationViewActivity.class));
                    }

                }
                else {
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(LogInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = emailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailField.setError("Required.");
            valid = false;
        } else {
            emailField.setError(null);
        }

        String password = passwordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordField.setError("Required.");
            valid = false;
        } else {
            passwordField.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.create_account) {
            startActivityForResult(new Intent(LogInActivity.this, SingUpActivity.class),1);
        } else if (i == R.id.button_continue) {
            signIn(emailField.getText().toString(), passwordField.getText().toString());
        } else if (i == R.id.forgot_password) {
            startActivityForResult(new Intent(LogInActivity.this, ResetPasswordActivity.class), RESET_PASSWORD);
        }
    }
}