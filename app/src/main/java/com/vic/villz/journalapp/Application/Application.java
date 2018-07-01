package com.vic.villz.journalapp.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Application extends android.app.Application {
    private DatabaseReference mDatabaseReference;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate() {
        super.onCreate();

        //enable offline functionalities
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}
