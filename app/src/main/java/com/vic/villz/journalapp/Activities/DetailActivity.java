package com.vic.villz.journalapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;

import com.vic.villz.journalapp.R;

public class DetailActivity extends AppCompatActivity {
    android.support.v7.widget.Toolbar mToolBar;
    private static final String ID = "id";
    private static final String TIME = "time";
    private static final String DESCRIPTION = "description";
    private static final String TITLE = "title";

    private AppCompatTextView mTimeViewEditText;
    private AppCompatTextView mDescriptionViewEditText;
    private AppCompatTextView mTitleViewEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mTimeViewEditText = findViewById(R.id.detail_time);
        mDescriptionViewEditText = findViewById(R.id.detail_description);
        mTitleViewEditText = findViewById(R.id.detail_title);

        Intent intent = getIntent();
        if (intent != null) {

            String title = intent.getStringExtra(TITLE);
            String time = intent.getStringExtra(TIME);
            String description = intent.getStringExtra(DESCRIPTION);

            initializeViews(title, description, time);

        }

    }



    public static void start(Context context, Bundle bundle) {

        if( bundle != null) {

            Intent starter = new Intent(context, DetailActivity.class);
            starter.putExtra("time", bundle.getString(TIME));
            starter.putExtra("description", bundle.getString(DESCRIPTION));
            starter.putExtra("title", bundle.getString(TITLE));

            context.startActivity(starter);
        } else {
            return;
        }
    }

    private void initializeViews( String title, String description, String time) {
        mTimeViewEditText.setText(time);
        mDescriptionViewEditText.setText(description);
        mTitleViewEditText.setText(title);
    }
}
