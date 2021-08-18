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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class CustomerLogin extends AppCompatActivity {

    private Button LoginCustomer;
    private EditText EmailCustomerLogin, PasswordCustomerLogin;
    private TextView ForgotPasswordCustomer, CreateAccountCustomer;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Customers");


        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), CustomerMain.class));
            finish();
        }

        LoginCustomer = findViewById(R.id.Login_Btn_Customer);
        EmailCustomerLogin = findViewById(R.id.Email_Customer_Login);
        PasswordCustomerLogin = findViewById(R.id.Password_Customer_Login);
        ForgotPasswordCustomer = findViewById(R.id.Forgot_Password_Customer);
        CreateAccountCustomer = findViewById(R.id.Create_Account_Customer);

        CreateAccountCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerLogin.this, CustomerSignup.class));
                CreateAccountCustomer.setTextColor(Color.BLUE);
                finish();
            }
        });

        ForgotPasswordCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerLogin.this, ForgotPassword.class));
                ForgotPasswordCustomer.setTextColor(Color.BLUE);
                finish();
            }
        });

        LoginCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CustomerLogin.this, "PROCESSING...", Toast.LENGTH_LONG).show();
                String email = EmailCustomerLogin.getText().toString().trim();
                String password = PasswordCustomerLogin.getText().toString().trim();


                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            checkUserExistence();
                        }
                    });
                }else{
                    Toast.makeText(CustomerLogin.this, "Complete all fields", Toast.LENGTH_SHORT).show();
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
                    Intent mainPage = new Intent(CustomerLogin.this, CustomerMain.class);
                    startActivity(mainPage);
                } else {
                    Toast.makeText(CustomerLogin.this, "User not Registered!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}