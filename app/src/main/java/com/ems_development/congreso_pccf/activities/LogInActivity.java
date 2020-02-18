package com.ems_development.congreso_pccf.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.ems_development.congreso_pccf.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LogInActivity extends AppCompatActivity implements
        View.OnClickListener {


    private FirebaseAuth mAuth;

    private static final String TAG = "EmailPassword";

    EditText emailField, passwordField;
    TextView createAccount, forgotPassword;
    private Button next;
    static final int RESET_PASSWORD = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
//        createAccount = findViewById(R.id.create_account);
//        forgotPassword = findViewById(R.id.forgot_password);
//        next = findViewById(R.id.button_continue);

        findViewById(R.id.button_continue).setOnClickListener(this);
        findViewById(R.id.forgot_password).setOnClickListener(this);
        findViewById(R.id.create_account).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

//        createAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //TODO Santiago debe llamar a la actividad registrar usuario
//                    startActivityForResult(new Intent(LogInActivity.this, SingUpActivity.class),1);
//
//            }
//        });

//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String emailEntered = email.getText().toString();
//                String passwordEntered = password.getText().toString();
//
//                email.setError(null);
//                password.setError(null);
//
//                if (emailEntered.isEmpty()){
//                    emailField.setError("Debe ingresar su email");
//                    email.requestFocus();
//                }
//                else if (passwordEntered.isEmpty()){
//                    password.setError("Debe ingresar su contraseña");
//                    password.requestFocus();
//                }
//                else if (!(emailEntered.isEmpty() && passwordEntered.isEmpty())){
//                    //TODO Santiago debe verificar el Sign In con Firebase
//                }
//                else {
//                    Toast.makeText(LogInActivity.this, "Error, por favor intente de nuevo", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


//        forgotPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivityForResult(new Intent(LogInActivity.this, ResetPasswordActivity.class), RESET_PASSWORD);
//            }
//        });
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
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

//        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                            startActivityForResult(new Intent(LogInActivity.this, BottomNavigationViewActivity.class),1);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LogInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
//                            mStatusTextView.setText(R.string.auth_failed);

                        }
//                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
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
