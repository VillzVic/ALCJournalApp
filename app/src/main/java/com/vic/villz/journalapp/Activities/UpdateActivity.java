package com.vic.villz.journalapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

import com.vic.villz.journalapp.Presenter.UpdatePresenter;
import com.vic.villz.journalapp.R;
import com.vic.villz.journalapp.Utils.TimeUtil;
import com.vic.villz.journalapp.Utils.ToastGenerator;
import com.vic.villz.journalapp.ViewsInterface.Views;
import com.vic.villz.journalapp.model.JournalEntry;

public class UpdateActivity extends AppCompatActivity implements Views.EditView{

    android.support.v7.widget.Toolbar mToolbar;
    private AppCompatEditText mEditTitleEditText;
    private AppCompatEditText mEditContentEditText;
    private AppCompatButton mUpdateBtn;
    private UpdatePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mEditTitleEditText = findViewById(R.id.edit_title);
        mEditContentEditText = findViewById(R.id.edit_content);
        mUpdateBtn = findViewById(R.id.update_btn);


        Intent intent = getIntent();

        if (intent != null) {

            final String title  = intent.getStringExtra("title");
            final String description  = intent.getStringExtra("description");
            String push_id  = intent.getStringExtra("push_id");
            final String time = TimeUtil.getTime();

            mPresenter = new UpdatePresenter(this, push_id);

            mEditTitleEditText.setText(title);
            mEditContentEditText.setText(description);

            mUpdateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mPresenter.EditUser(mEditTitleEditText.getText().toString(), time, mEditContentEditText.getText().toString());
                }
            });

        }

    }

    @Override
    public void onEditSuccess() {

        ToastGenerator.GenerateToast( UpdateActivity.this, "Successfully updated details", Toast.LENGTH_SHORT);
    }

    @Override
    public void onEditFailure() {

        ToastGenerator.GenerateToast( UpdateActivity.this, "An error occurred, try again later", Toast.LENGTH_SHORT);

    }

    public static void start(Context context, JournalEntry model, String pushid) {

        if (model != null) {

            Intent starter = new Intent(context, UpdateActivity.class);
            starter.putExtra("title", model.getTitle());
            starter.putExtra("description", model.getDescription());
            starter.putExtra("push_id", pushid);
            context.startActivity(starter);

        } else {

            return;
        }
    }


}
