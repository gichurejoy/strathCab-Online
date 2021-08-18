package com.example.strathcab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewProfileDriver extends AppCompatActivity {
    private FirebaseUser user;
    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;
    private String driverID;
    private FirebaseDatabase fstore;
    String userID;
    private TextView deleteDriver, fullNameDriver, nameDriver, emailDriver, adminNoDriver, phoneDriver;
    private Button changePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_driver);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseDatabase.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        databaseReference = fstore.getReference().child("Drivers").child(userID);
        userID = user.getUid();

        changePassword = findViewById(R.id.changePassoword);

        deleteDriver = findViewById(R.id.deleteDriver);
        fullNameDriver = findViewById(R.id.fullName);
        nameDriver = findViewById(R.id.NameDriver);
        emailDriver = findViewById(R.id.email_Driver);
        adminNoDriver = findViewById(R.id.adminNumberDriver);
        phoneDriver = findViewById(R.id.phoneNumberDriver);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("Name").getValue().toString();
                String email = snapshot.child("Email").getValue().toString();
                String admissionNumber = snapshot.child("Admission_Number").getValue().toString();
                String phone = snapshot.child("Phone_number").getValue().toString();

                nameDriver.setText(name);
                emailDriver.setText(email);
                adminNoDriver.setText(admissionNumber);
                phoneDriver.setText(phone);
                fullNameDriver.setText(name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        deleteDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogDelete = new AlertDialog.Builder(ViewProfileDriver.this);
                dialogDelete.setTitle("Are you sure?");
                dialogDelete.setMessage("Deleting your account will result in completely removing your account from the system and you won't be able to access the app");
                dialogDelete.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(ViewProfileDriver.this, "DELETING...", Toast.LENGTH_SHORT).show();
                                if(task.isSuccessful()){
                                    Toast.makeText(ViewProfileDriver.this, "Account deleted", Toast.LENGTH_LONG).show();
                                    Intent deleteCustomer = new Intent(ViewProfileDriver.this,DriverLogin.class);
                                    deleteCustomer.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    deleteCustomer.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(deleteCustomer);
                                }else{
                                    Toast.makeText(ViewProfileDriver.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
                dialogDelete.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = dialogDelete.create();
                alertDialog.show();
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewProfileDriver.this, ForgotPassword.class));
            }
        });
    }
}