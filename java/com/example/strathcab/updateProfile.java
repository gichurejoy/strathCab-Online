package com.example.strathcab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class updateProfile extends AppCompatActivity {
    private EditText updateName, updateEmail, updateAdmissionNumber, updatePhoneNumber;
    FirebaseDatabase fstore;
    private ImageView updateProfileImage;
    FirebaseUser user ;
    private FirebaseAuth fAuth;
    private DatabaseReference databaseReference, customerRef;
    private String userID;
    private Button update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        updateAdmissionNumber= findViewById(R.id.updateAdminNumber);
        updateEmail=findViewById(R.id.updateEmail);
        updateName=findViewById(R.id.UpdateName);
        updatePhoneNumber=findViewById(R.id.updatePhoneNumber);
        updateProfileImage= findViewById(R.id.profileImageUpdate);
        update = findViewById(R.id.update);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseDatabase.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        user= FirebaseAuth.getInstance().getCurrentUser();

        databaseReference = fstore.getReference().child("Customers").child(userID);
        userID = user.getUid();



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }

}