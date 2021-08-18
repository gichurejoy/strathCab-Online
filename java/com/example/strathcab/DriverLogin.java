package com.example.strathcab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DriverLogin extends AppCompatActivity {

    Button LoginDriver;
    EditText EmailDriverLogin, PasswordDriverLogin;
    TextView ForgotPasswordDriver, CreateAccountDriver;
    FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);




        mAuth = FirebaseAuth.getInstance();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Drivers");

        if(mAuth.getCurrentUser() != null){
           Intent intent = new Intent( DriverLogin.this, DriverMain.class);
           startActivity(intent);
        }


        LoginDriver = findViewById(R.id.Login_Btn_Driver);
        EmailDriverLogin = findViewById(R.id.Email_Driver_Login);
        PasswordDriverLogin = findViewById(R.id.Password_Driver_Login);
        ForgotPasswordDriver = findViewById(R.id.Forgot_Password_Driver);
        CreateAccountDriver = findViewById(R.id.Create_Account_Driver);

        ForgotPasswordDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverLogin.this, ForgotPassword.class));
                ForgotPasswordDriver.setTextColor(Color.BLUE);
            }
        });

        CreateAccountDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverLogin.this, DriverSignup.class));
                CreateAccountDriver.setTextColor(Color.BLUE);

            }
        });

        LoginDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DriverLogin.this, "PROCESSING...", Toast.LENGTH_LONG).show();
                String email = EmailDriverLogin.getText().toString().trim();
                String password = PasswordDriverLogin.getText().toString().trim();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                checkUserExistence();
                            }else{
                                Toast.makeText(DriverLogin.this, "Couldn't login, user not found",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(DriverLogin.this, "Complete all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void checkUserExistence() {
        final String user_id = mAuth.getCurrentUser().getUid();
        mDatabaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(user_id)) {
                    Intent mainPage = new Intent(DriverLogin.this, DriverMaps.class);
                    startActivity(mainPage);
                } else {
                    Toast.makeText(DriverLogin.this, "User not Registered!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}