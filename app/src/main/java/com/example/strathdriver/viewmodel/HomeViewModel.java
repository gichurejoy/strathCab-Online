package com.example.strathdriver.viewmodel;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.ViewModel;

import com.example.strathdriver.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class HomeViewModel extends ViewModel {

    public HomeViewModel() {

    }

    public void signOut(Activity activity) {
        FirebaseAuth.getInstance().signOut();
        Intent intent=new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
