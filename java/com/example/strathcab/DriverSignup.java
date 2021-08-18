package com.example.strathcab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class DriverSignup extends AppCompatActivity {

    private static final String TAG = "TAG";
    Button SignupDriver;
    EditText AdmissionNumberDriver, NameDriver, EmailDriverSignup, PhoneNumberDriver, PasswordDriverSignup;
    TextView HaveAccountDriver;
    FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference userDetailReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_signup);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userDetailReference = database.getReference().child("Drivers");

        if(mAuth.getCurrentUser() != null){
          startActivity(new Intent(getApplicationContext(), DriverMain.class));
           finish();
       }

        /*firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull @NotNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getInstance().getCurrentUser();
                if (user != null){
                    startActivity(new Intent(getApplicationContext(), DriverProfileActivity.class));
                    finish();
                    return;
                }
            }
        };*/


        SignupDriver = findViewById(R.id.Signup_Driver);
        AdmissionNumberDriver = findViewById(R.id.Admission_Number_Driver);
        EmailDriverSignup = findViewById(R.id.Email_Driver_Signup);
        PasswordDriverSignup = findViewById(R.id.Password_Driver_Signup);
        HaveAccountDriver = findViewById(R.id.Have_Account_Driver);
        NameDriver = findViewById(R.id.nameDriver);
        PhoneNumberDriver = findViewById(R.id.phoneDriver);

        HaveAccountDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriverSignup.this, DriverLogin.class));
                HaveAccountDriver.setTextColor(Color.BLUE);
                finish();
            }
        });

        SignupDriver.setOnClickListener(v -> {
            Toast.makeText(DriverSignup.this, "LOADING...", Toast.LENGTH_LONG).show();
            final String email = EmailDriverSignup.getText().toString().trim();
            final String password = PasswordDriverSignup.getText().toString().trim();
            final String admissionNumber = AdmissionNumberDriver.getText().toString();
            final String phoneNumber = PhoneNumberDriver.getText().toString().trim();
            final String nameDriver = NameDriver.getText().toString().trim();

            if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(nameDriver) ){


                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        final String driver_id = mAuth.getCurrentUser().getUid();


                        DatabaseReference current_user_db = userDetailReference.child(driver_id);
                        current_user_db.child("Admission_Number").setValue(admissionNumber);
                        current_user_db.child("Name").setValue(nameDriver);
                        current_user_db.child("Email").setValue(email);
                        current_user_db.child("Phone_number").setValue(phoneNumber);

                        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(DriverSignup.this, "Registration Successful. Please check your email for verification", Toast.LENGTH_LONG).show();
                                    Intent profIntent = new Intent(DriverSignup.this, DriverMain.class);
                                    profIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(profIntent);
                                }else{
                                    Toast.makeText(DriverSignup.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }

                            }
                        });

                    }
                });
            }else{
                Toast.makeText(DriverSignup.this, "Complete all fields", Toast.LENGTH_SHORT).show();
            }

        });
    }


}