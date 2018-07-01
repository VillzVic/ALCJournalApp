package com.vic.villz.journalapp.Presenter;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vic.villz.journalapp.ViewsInterface.Views;

import java.util.HashMap;
import java.util.Map;

public class UpdatePresenter {
    Views.EditView mEditView;
    private DatabaseReference mdbReference;
    private String itemid;

    public UpdatePresenter(Views.EditView mEditView, String id) {
        this.mEditView = mEditView;
        this.itemid = id;
        mdbReference = FirebaseDatabase.getInstance().getReference().child("Entries");
        mdbReference.keepSynced(true);

    }

    public void EditUser(String title, String time, String description){

        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("time", time);
        map.put("description", description);

        mdbReference.child(itemid).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    mEditView.onEditSuccess();
                } else {
                    mEditView.onEditFailure();
                }
            }
        });
    }

}
