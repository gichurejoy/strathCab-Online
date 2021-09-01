package com.example.strathdriver;

import androidx.annotation.NonNull;
import org.jetbrains.annotations.NotNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class ForgotPassword extends AppCompatActivity {
    FirebaseAuth mAuth;
    ProgressBar resetProgressBar;
    TextView resetState;
    EditText emailReset;
    Button resetPasswordBtn, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();
        emailReset = findViewById(R.id.Email_Reset_Customer);
        resetState = findViewById(R.id.resetText);
        resetPasswordBtn = findViewById(R.id.reset_Request_btn);
        resetProgressBar = findViewById(R.id.progressBarResetPassword);
        backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetProgressBar.setVisibility(View.VISIBLE);
                mAuth.fetchSignInMethodsForEmail(emailReset.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<SignInMethodQueryResult> task) {
                        if(task.getResult().getSignInMethods().isEmpty()) {
                            resetProgressBar.setVisibility(View.GONE);
                            resetState.setText("This is not a registered account, create a new account");
                        }else{
                            mAuth.sendPasswordResetEmail(emailReset.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    resetProgressBar.setVisibility(View.GONE);
                                    if(task.isSuccessful()){
                                        resetState.setText("An email has been sent to your email address");
                                    }else{
                                        resetState.setText(task.getException().getMessage());
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}