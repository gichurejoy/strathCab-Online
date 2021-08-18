package com.example.strathcab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class DriverMain extends AppCompatActivity {

    Button profileDriver, findRideDriver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        profileDriver= findViewById(R.id.profileDriver);
        findRideDriver = findViewById(R.id.findRideDriver);

        findRideDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DriverMain.this, CustomerLogin.class));
            }
        });

        profileDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DriverMain.this, ViewProfileDriver.class));
            }
        });
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(DriverMain.this, MainActivity.class));
        finish();
    }
}