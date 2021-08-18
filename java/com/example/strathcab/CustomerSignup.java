package com.example.strathcab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.strathcab.databinding.ActivityCustomerSignupBinding;
import com.example.strathcab.databinding.ActivityViewProfileCustomerBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomerSignup extends AppCompatActivity {

    private Button SignupCustomer;
    private EditText AdmissionNumberCustomer, EmailCustomerSignup, nameCustomer, PasswordCustomerSignup, customerNumber;
    private TextView HaveAccountCustomer;
    String userID;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;
    private DatabaseReference userDetailReference;
    private StorageReference mStorageRef;
    private FirebaseUser  currentUser;
    private Uri profileImageUri=null;
    private ImageView profileImageView;
    FirebaseStorage firebaseStorage;
    private final static int GALLERY_REQUEST_CODE=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_signup);
        firebaseStorage = FirebaseStorage.getInstance();
        mStorageRef =FirebaseStorage.getInstance().getReference().child("ProfilePhoto");
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

       // databaseRef = database.getReference().child("Customers").child(userID);
        userDetailReference = database.getReference().child("Customers");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentUser = mAuth.getCurrentUser();


        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), CustomerMain.class));
            finish();
        }


        SignupCustomer = findViewById(R.id.Signup_Customer);
        AdmissionNumberCustomer = findViewById(R.id.Admission_Number_Customer);
        EmailCustomerSignup = findViewById(R.id.Email_Customer_Signup);
        PasswordCustomerSignup = findViewById(R.id.Password_Customer_Signup);
        HaveAccountCustomer = findViewById(R.id.Have_Account_Customer);
        customerNumber = findViewById(R.id.customerNumber);
        nameCustomer = findViewById(R.id.nameCustomer);


       /* profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                //galleryIntent.setType("*image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
            }
        });*/
        HaveAccountCustomer.setOnClickListener(v -> {
            startActivity(new Intent(CustomerSignup.this, CustomerLogin.class));
            HaveAccountCustomer.setTextColor(Color.BLUE);
            finish();
        });
        SignupCustomer.setOnClickListener(v -> {
            Toast.makeText(CustomerSignup.this, "LOADING...", Toast.LENGTH_LONG).show();
            final String email = EmailCustomerSignup.getText().toString().trim();
            final String password = PasswordCustomerSignup.getText().toString().trim();
            final String admissionNumber = AdmissionNumberCustomer.getText().toString();
            final String phoneNumber = customerNumber.getText().toString().trim();
            final String nameDriver = nameCustomer.getText().toString().trim();

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
                                    Toast.makeText(CustomerSignup.this, "Registration Successful. Please check your email for verification", Toast.LENGTH_LONG).show();
                                    Intent profIntent = new Intent(CustomerSignup.this, CustomerMain.class);
                                    profIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(profIntent);
                                }else{
                                    Toast.makeText(CustomerSignup.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }

                            }
                        });

                    }
                });
            }else{
                Toast.makeText(CustomerSignup.this, "Complete all fields", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==GALLERY_REQUEST_CODE&&resultCode==RESULT_OK){
            profileImageUri=data.getData();
            profileImageView.setImageURI(profileImageUri);
        }
    }

}