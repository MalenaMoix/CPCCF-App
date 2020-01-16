package com.ems_development.congreso_pccf.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.ems_development.congreso_pccf.R;


public class LogIn extends AppCompatActivity {

    EditText email, password;
    TextView createAccount, forgotPassword;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        createAccount = findViewById(R.id.create_account);
        forgotPassword = findViewById(R.id.forgot_password);
        next = findViewById(R.id.button_continue);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Santiago debe llamar a la actividad registrar usuario
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailEntered = email.getText().toString();
                String passwordEntered = password.getText().toString();

                email.setError(null);
                password.setError(null);

                if (emailEntered.isEmpty()){
                    email.setError("Debe ingresar su email");
                    email.requestFocus();
                }
                else if (passwordEntered.isEmpty()){
                    password.setError("Debe ingresar su contraseña");
                    password.requestFocus();
                }
                else if (!(emailEntered.isEmpty() && passwordEntered.isEmpty())){
                    //TODO Santiago debe verificar el Sign In con Firebase
                }
                else {
                    Toast.makeText(LogIn.this, "Error, por favor intente de nuevo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
