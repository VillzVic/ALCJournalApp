package com.vic.villz.journalapp.Presenter;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vic.villz.journalapp.ViewsInterface.Views;

import java.util.HashMap;
import java.util.Map;

public class UploadPresenter {
    Views.UploadView mUploadView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDbReference;

    public UploadPresenter(Views.UploadView mUploadView) {
        this.mUploadView = mUploadView;

        mAuth = FirebaseAuth.getInstance();
        mDbReference = FirebaseDatabase.getInstance().getReference().child("Entries");
        mDbReference.keepSynced(true);

    }

    public  void uploadToFirebase(String title, String  description, String date){
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("description", description);
        map.put("time", date);

        mDbReference.push().updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    mUploadView.onUploadSuccess();

                } else {
                    mUploadView.onUploadFailure();
                }
            }
        });
    }
}
