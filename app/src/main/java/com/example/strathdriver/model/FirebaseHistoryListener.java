package com.example.strathdriver.model;



import com.example.strathdriver.model.firebase.History;

import java.util.ArrayList;

public interface FirebaseHistoryListener {
    interface GetFirebaseHistoryListener {
        void onFirebaseHistoryRetrieved(ArrayList<History> historyList);
    }
}
