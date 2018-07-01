package com.vic.villz.journalapp.Presenter;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.vic.villz.journalapp.ViewsInterface.Views;

public class LoginPresenter {
    Views.LoginView mLoginView;
    FirebaseAuth mAuth;

    public LoginPresenter(Views.LoginView mLoginView) {
        this.mLoginView = mLoginView;
        mAuth = FirebaseAuth.getInstance();

    }

    public void SignIn(String email, String password){

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    mLoginView.onLoginSuccess();
                } else {
                    mLoginView.onLoginFailure();
                }
            }

        });
    }
}
