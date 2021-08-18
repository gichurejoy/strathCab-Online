package com.example.strathcab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.ListenerRegistration;
import com.squareup.picasso.Picasso;

public class ViewProfileCustomer extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference, customerRef;
    private String userID;
    private FirebaseDatabase fstore;
    private ImageView profilePic;
    private TextView deleteAccount,fullNameCustomer, nameCustomer, emailCustomer, adminNoCustomer, phoneCustomer;
    private ImageView profileImage;
    String UserID;
    private Button changePassword, updateProfile;

    private ListenerRegistration listenery;

    private static final int GALLERY_REQUEST_CODE = 2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_customer);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseDatabase.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        user= FirebaseAuth.getInstance().getCurrentUser();

        databaseReference = fstore.getReference().child("Customers").child(userID);
        userID = user.getUid();

        profileImage=findViewById(R.id.profileImageCustomer);
        fullNameCustomer = findViewById(R.id.fullName);
        nameCustomer = findViewById(R.id.Name);
        emailCustomer = findViewById(R.id.email_Customer);
        adminNoCustomer = findViewById(R.id.adminNumber);
        phoneCustomer = findViewById(R.id.phoneNumber);
        deleteAccount= findViewById(R.id.deleteCustomer);
        changePassword=findViewById(R.id.changePassoword);
        updateProfile=findViewById(R.id.updateProfile);


        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(ViewProfileCustomer.this, updateProfile.class));
            }
        });



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("Name").getValue().toString();
                String email = snapshot.child("Email").getValue().toString();
                String admissionNumber = snapshot.child("Admission_Number").getValue().toString();
                String phone = snapshot.child("Phone_number").getValue().toString();


                nameCustomer.setText(name);
                emailCustomer.setText(email);
                adminNoCustomer.setText(admissionNumber);
                phoneCustomer.setText(phone);
                fullNameCustomer.setText(name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogDelete = new AlertDialog.Builder(ViewProfileCustomer.this);
                dialogDelete.setTitle("Are you sure?");
                dialogDelete.setMessage("Deleting your account will result in completely removing your account from the system and you won't be able to access the app");
                dialogDelete.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(ViewProfileCustomer.this, "DELETING...", Toast.LENGTH_SHORT).show();
                                if(task.isSuccessful()){
                                    Toast.makeText(ViewProfileCustomer.this, "Account deleted", Toast.LENGTH_LONG).show();
                                    Intent deleteCustomer = new Intent(ViewProfileCustomer.this,CustomerLogin.class);
                                    deleteCustomer.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    deleteCustomer.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(deleteCustomer);
                                }else{
                                    Toast.makeText(ViewProfileCustomer.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
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
               startActivity(new Intent(ViewProfileCustomer.this, ForgotPassword.class));
            }
        });


        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(new Intent(ViewProfileCustomer.this, AddProfileImageCustomer.class));
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                profileImage.setImageURI(imageUri);
            }
        }
    }
}