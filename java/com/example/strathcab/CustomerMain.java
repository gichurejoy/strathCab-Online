package com.example.strathcab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class CustomerMain extends AppCompatActivity {

    private TextView logoutCustomer;
    Button signToDrive, profile, findRide;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);

        logoutCustomer = findViewById(R.id.logout);
        signToDrive =findViewById(R.id.signToDrive);
        profile = findViewById(R.id.profile);
        findRide = findViewById(R.id.findRide);

        mAuth = FirebaseAuth.getInstance();

        signToDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerMain.this, DriverSignup.class));
            }
        });

        findRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerMain.this,FindRide.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerMain.this,ViewProfileCustomer.class));
            }
        });

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(CustomerMain.this, MainActivity.class));
        finish();
    }
}