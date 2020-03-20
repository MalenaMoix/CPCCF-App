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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
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
    private static final int RC_SIGN_IN = 2;
    private static int isAdmin = 0;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirestoreDatabase firestoreDatabase;
    private EditText emailField, passwordField;
    SignInButton signInButton;
    GoogleSignInOptions signInOptions;
    GoogleSignInClient signInClient;
    GoogleSignInAccount account;
    private List<QueryDocumentSnapshot> admins = new ArrayList<>();

    //TODO no trae los admin?

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
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        findViewById(R.id.button_continue).setOnClickListener(this);
        findViewById(R.id.forgot_password).setOnClickListener(this);
        findViewById(R.id.create_account).setOnClickListener(this);
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        subscribeToTopicToReceivePushNotifications();

        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        signInClient = GoogleSignIn.getClient(this, signInOptions);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESET_PASSWORD){
            if (resultCode == Activity.RESULT_OK){
                Toast.makeText(LogInActivity.this, "Chequee su email", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_account:
                startActivityForResult(new Intent(LogInActivity.this, SignUpActivity.class),1);
                break;
            case R.id.button_continue:
                signIn(emailField.getText().toString(), passwordField.getText().toString());
                break;
            case R.id.forgot_password:
                startActivityForResult(new Intent(LogInActivity.this, ResetPasswordActivity.class), RESET_PASSWORD);
                break;
            case R.id.sign_in_button:
                googleSignIn();
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        emailField.requestFocus();
        emailField.setText(null);
        passwordField.setText(null);
    }

    @Override
    public void onStart() {
        super.onStart();
        //account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);
    }


    private void signIn(String email, String password) {
        isAdmin = 0;

        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    user = mAuth.getCurrentUser();

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


    private void googleSignIn() {
        Intent signInIntent = signInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        isAdmin = 0;
        if (account != null){
            user = mAuth.getCurrentUser();

            for (DocumentSnapshot admin : admins){
                if (admin.get("email").equals(account.getEmail())){
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

    private void subscribeToTopicToReceivePushNotifications(){
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
}