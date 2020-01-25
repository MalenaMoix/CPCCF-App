package com.ems_development.congreso_pccf.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ems_development.congreso_pccf.R;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText email;
    Button sendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        email = findViewById(R.id.editText_email);
        sendEmail = findViewById(R.id.button_send_email);

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailEntered = email.getText().toString();
                if (emailEntered.isEmpty()){
                    email.setError("Debe ingresar su email");
                }
                else{
                    email.setError(null);
                    sendResetPasswordEmail(emailEntered);

                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        });
    }

    protected void sendResetPasswordEmail (String emailEntered){
        //TODO Santiago tiene que descmentar este codigo
        /*FirebaseAuth.getInstance().sendPasswordResetEmail(emailEntered)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent");
                        }
                    }
                });*/
    }
}
