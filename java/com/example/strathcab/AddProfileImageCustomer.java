package com.example.strathcab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class AddProfileImageCustomer extends AppCompatActivity {

    private ImageView profileImage;
    private Button save, close;
    private TextView changeProfile;

    private Uri profileImageUri=null;
    private final static int GALLERY_REQUEST_CODE=1;
    private String myUri="";
    private StorageTask uploadTask;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile_image_customer);

        mAuth = FirebaseAuth.getInstance();
        final String userID=mAuth.getCurrentUser().getUid();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference("Customers").child(userID);
        mStorageRef = FirebaseStorage.getInstance().getReference().child("Profile Images");


        save = findViewById(R.id.save);
        close = findViewById(R.id.close);
        profileImage = findViewById(R.id.addprofile);
        changeProfile= findViewById(R.id.change_profile);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(profileImageUri!=null){
                   StorageReference profileImagePath=mStorageRef.child("profile_images").child(profileImageUri.getLastPathSegment());
                   profileImagePath.putFile(profileImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           if (taskSnapshot.getMetadata()!=null){
                               if (taskSnapshot.getMetadata().getReference()!=null){
                                   Task<Uri> result=taskSnapshot.getStorage().getDownloadUrl();
                                   result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                       @Override
                                       public void onSuccess(Uri uri) {
                                           final String profileImage=uri.toString();
                                           mDatabaseUser.push();
                                           mDatabaseUser.addValueEventListener(new ValueEventListener() {
                                               @Override
                                               public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                   mDatabaseUser.child("customerImage").setValue(profileImage).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<Void> task) {
                                                           if (task.isSuccessful()){
                                                               Toast.makeText(AddProfileImageCustomer.this,"Profile Updated",Toast.LENGTH_SHORT).show();
                                                               Intent login=new Intent(AddProfileImageCustomer.this,ViewProfileCustomer.class);
                                                               startActivity(login);
                                                           }
                                                       }
                                                   });
                                               }

                                               @Override
                                               public void onCancelled(@NonNull DatabaseError error) {

                                               }
                                           });
                                       }
                                   });
                               }
                           }
                       }
                   }) ;
               }

            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(AddProfileImageCustomer.this, ViewProfileCustomer.class));
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==GALLERY_REQUEST_CODE&&resultCode==RESULT_OK){
            profileImageUri=data.getData();
            profileImage.setImageURI(profileImageUri);
        }
    }

}