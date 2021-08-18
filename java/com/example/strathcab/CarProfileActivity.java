package com.example.strathcab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CarProfileActivity extends AppCompatActivity {

    private Button doneBtn;
    private EditText numberPlate,carColor,carModel, carManufacturer;
    private ImageButton imageButton;
    private FirebaseAuth mAuth;
    private FirebaseDatabase fStore;
    private DatabaseReference carReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_profile_activity);

        mAuth = FirebaseAuth.getInstance();
        fStore =FirebaseDatabase.getInstance();
        carReference = fStore.getReference().child("Car details");

        numberPlate=findViewById(R.id.numberPlate);
        carColor=findViewById(R.id.color);
        carModel=findViewById(R.id.model);
        carManufacturer=findViewById(R.id.manufacturer);
        doneBtn=findViewById(R.id.doneBtn);

        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), CustomerMain.class));
            finish();
        }


        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String colorCar = carColor.getText().toString();
                String plateNumber = numberPlate.getText().toString();
                String manufacturer = carManufacturer.getText().toString();
                String model = carModel.getText().toString();

                if (!TextUtils.isEmpty(colorCar) && !TextUtils.isEmpty(plateNumber) && !TextUtils.isEmpty(manufacturer) && !TextUtils.isEmpty(model)){

                }
            }
        });


    }

    
}