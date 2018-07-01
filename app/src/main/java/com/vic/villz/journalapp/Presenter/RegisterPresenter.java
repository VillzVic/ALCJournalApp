package com.vic.villz.journalapp.Presenter;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vic.villz.journalapp.ViewsInterface.Views;

import java.util.HashMap;
import java.util.Map;

public class RegisterPresenter {
    Views.RegisterView mRegisterView;
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;


    public RegisterPresenter(Views.RegisterView mRegisterView) {
        this.mRegisterView = mRegisterView;
        mAuth = FirebaseAuth.getInstance();


        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseReference.keepSynced(true);
    }

    public void Register(final String username, final String email, final String password){

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    String user_id = mAuth.getCurrentUser().getUid();

                    Map<String, Object> user_details = new HashMap<>();
                    user_details.put("user_name", username);
                    user_details.put("email", email);
                    user_details.put("password", password);

                    mDatabaseReference.child(user_id).updateChildren(user_details).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                mRegisterView.onRegisterSuccess();
                            } else {
                                mRegisterView.onRegisterFailure();
                            }
                        }
                    });

                }
                else{
                    mRegisterView.onRegisterFailure();
                }
            }
        });
    }
}
