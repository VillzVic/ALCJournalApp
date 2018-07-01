package com.vic.villz.journalapp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.vic.villz.journalapp.Presenter.RegisterPresenter;
import com.vic.villz.journalapp.R;
import com.vic.villz.journalapp.Utils.ToastGenerator;
import com.vic.villz.journalapp.ViewsInterface.Views;

public class RegisterActivity extends AppCompatActivity implements Views.RegisterView, View.OnClickListener{
    private AppCompatEditText mUsernameEditText;
    private AppCompatEditText mEmailAddressEditText;
    private AppCompatEditText mPasswordEditText;
    FloatingActionButton mFloatingActionButton;
    private AppCompatTextView mLoginTextView;
    ProgressDialog mProgressDialog;
    RegisterPresenter mRegisterPresenter;
    GoogleSignInClient mGoogleSignInClient;
    SignInButton mSignInButton;
    private final int RC_SIGN_IN= 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initialize all views
        init();

        //initialize google
        initGoogle();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInAccount(task);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_btn:
                register();
                break;
            case R.id.sign_in_button:
                signIn();
                break;

            case R.id.login_link:
                LoginActivity.start(this);
                break;
        }
    }


    @Override
    public void onRegisterSuccess() {

        mProgressDialog.dismiss();
        HomePageActivity.start(this);
    }

    @Override
    public void onRegisterFailure() {

        mProgressDialog.dismiss();
        Toast.makeText(this, "An error occured, please try again", Toast.LENGTH_SHORT).show();

    }

    private void init(){
        mRegisterPresenter = new RegisterPresenter(this);
        mProgressDialog = new ProgressDialog(this);

        mUsernameEditText = findViewById(R.id.register_username);
        mEmailAddressEditText = findViewById(R.id.register_email);
        mPasswordEditText = findViewById(R.id.register_password);

        mFloatingActionButton = findViewById(R.id.register_btn);
        mLoginTextView = findViewById(R.id.login_link);

        mSignInButton = findViewById(R.id.sign_in_button);
        mSignInButton.setSize(SignInButton.SIZE_WIDE);
        mSignInButton.setOnClickListener(this);

        mFloatingActionButton.setOnClickListener(this);
        mLoginTextView.setOnClickListener(this);
    }




    //helper method to start activity
    public static void start(Context context) {
        Intent starter = new Intent(context, RegisterActivity.class);
        context.startActivity(starter);
    }



    private void signIn() {
        Intent signinIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signinIntent, RC_SIGN_IN);
    }

    //perform google sign in
    private void initGoogle() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }


    private void handleSignInAccount(Task<GoogleSignInAccount> completedTask) {
        try {
            //contains the account
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            if (account != null) {

                Intent intent = new Intent(RegisterActivity.this, HomePageActivity.class);

                startActivity(intent);
            }

        }catch (ApiException e){
            Log.w("Register Activity", "signInResult:failed code=" + e.getStatusCode());

        }
    }

    private void register() {

        String user = mUsernameEditText.getText().toString();
        String email = mEmailAddressEditText.getText().toString();
        String userPassword = mPasswordEditText.getText().toString();

        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(userPassword) ) {

            mProgressDialog.setMessage("Creating Account...");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();

            mRegisterPresenter.Register(user, email, userPassword);

        } else {

            ToastGenerator.GenerateToast(RegisterActivity.this, "Details cannot be empty", Toast.LENGTH_SHORT);
        }
    }


}
