package com.vic.villz.journalapp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.vic.villz.journalapp.Presenter.UploadPresenter;
import com.vic.villz.journalapp.R;
import com.vic.villz.journalapp.Utils.TimeUtil;
import com.vic.villz.journalapp.Utils.ToastGenerator;
import com.vic.villz.journalapp.ViewsInterface.Views;

public class AddJournalActivity extends AppCompatActivity  implements Views.UploadView{
    Toolbar toolbar;
    private AppCompatEditText mAddtitleEditText;
    private AppCompatEditText mAddContentEditText;
    private AppCompatButton uploadButton;
    ProgressDialog mProgressDialog;
    UploadPresenter mUploadPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initViews();

    }

    @Override
    public void onUploadSuccess() {

        mAddtitleEditText.setText(" ");
        mAddContentEditText.setText(" ");

        mProgressDialog.dismiss();
        ToastGenerator.GenerateToast(AddJournalActivity.this, "Input recorded", Toast.LENGTH_SHORT);
    }

    @Override
    public void onUploadFailure() {

        mAddtitleEditText.setText(" ");
        mAddContentEditText.setText(" ");

        mProgressDialog.dismiss();
        ToastGenerator.GenerateToast(AddJournalActivity.this, "SomeThing went wrong", Toast.LENGTH_SHORT);
    }


    public static void start(Context context) {
        Intent starter = new Intent(context, AddJournalActivity.class);
        context.startActivity(starter);
    }

    private void initViews() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mProgressDialog = new ProgressDialog(this);

        mUploadPresenter = new UploadPresenter(this);

        mAddtitleEditText = findViewById(R.id.add_title);
        mAddContentEditText = findViewById(R.id.add_content);
        uploadButton = findViewById(R.id.upload_btn);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveData();
            }
        });
    }


    private void retrieveData() {
        mProgressDialog.setMessage("Uploading content...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

        if (!TextUtils.isEmpty(mAddtitleEditText.getText().toString()) && !TextUtils.isEmpty(mAddContentEditText.getText().toString())) {
            String title = mAddtitleEditText.getText().toString();
            String description = mAddContentEditText.getText().toString();
            String date = TimeUtil.getTime();

            mUploadPresenter.uploadToFirebase(title, description, date);
        } else {
            ToastGenerator.GenerateToast(AddJournalActivity.this, "Fields must not be Empty", Toast.LENGTH_SHORT);
        }
    }





}