package com.vic.villz.journalapp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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
import com.vic.villz.journalapp.Presenter.LoginPresenter;
import com.vic.villz.journalapp.R;
import com.vic.villz.journalapp.Utils.ToastGenerator;
import com.vic.villz.journalapp.ViewsInterface.Views;

public class LoginActivity extends AppCompatActivity implements Views.LoginView, View.OnClickListener{


    private AppCompatEditText mEmailTextEditText;
    private AppCompatEditText mPasswordTextEditText;
    private AppCompatTextView mRegisterEditText;
    private FloatingActionButton mFloatingActionButton;
    GoogleSignInClient mGoogleSignInClient;
    private LoginPresenter mPresenter;
    ProgressDialog mProgressDialog;
    private final int RC_SIGN_IN= 9;
    SignInButton mSignInButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        //initialize google
        initGoogle();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode == RESULT_OK && requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInAccount(task);
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.login_btn:
                performLogin();

                break;
            case R.id.register:
                RegisterActivity.start(this);
                break;

            case R.id.sign_in_button:
                signIn();
                break;

        }
    }

    //callback for login success
    @Override
    public void onLoginSuccess() {
        mProgressDialog.dismiss();
        HomePageActivity.start(this);
    }

    //callback for login failure
    @Override
    public void onLoginFailure() {
        mProgressDialog.dismiss();
        Toast.makeText(this, "An error occured, please try again", Toast.LENGTH_SHORT).show();

    }


    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    private void initViews() {
        mPresenter = new LoginPresenter(this);
        mProgressDialog = new ProgressDialog(this);

        mEmailTextEditText = findViewById(R.id.email_login);
        mPasswordTextEditText = findViewById(R.id.password_login);
        mFloatingActionButton = findViewById(R.id.login_btn);
        mRegisterEditText = findViewById(R.id.register);
        mFloatingActionButton.setOnClickListener(this);
        mRegisterEditText.setOnClickListener(this);

        mSignInButton = findViewById(R.id.sign_in_button);
        mSignInButton.setSize(SignInButton.SIZE_WIDE);
        mSignInButton.setOnClickListener(this);
    }

    private void initGoogle() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }



    private void signIn() {
        Intent signinIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signinIntent, RC_SIGN_IN);
    }






    private void handleSignInAccount(Task<GoogleSignInAccount> completedTask) {
        try {
            //contains the account
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            if(account != null){

                Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);

                startActivity(intent);
            }

        }catch (ApiException e){
            Log.w("Register Activity", "signInResult:failed code=" + e.getStatusCode());

        }
    }
    //perform login process
    private void performLogin() {

        String email = mEmailTextEditText.getText().toString();
        String password = mPasswordTextEditText.getText().toString();

        if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){

            mProgressDialog.setMessage("Validating your details...");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();

            mPresenter.SignIn(email, password);

        }else{

            ToastGenerator.GenerateToast(LoginActivity.this, "Details cannot be empty", Toast.LENGTH_SHORT);
        }
    }




}
