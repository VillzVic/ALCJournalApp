package com.vic.villz.journalapp.UnitTest;


import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.vic.villz.journalapp.Activities.DetailActivity;
import com.vic.villz.journalapp.R;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class DetailActivityUnitTest {

    @Rule
    public ActivityTestRule<DetailActivity> mActivityRule =
            new ActivityTestRule<>(DetailActivity.class);

    @Test
    public void CheckIfTitleTextViewIsPresent(){
        DetailActivity detailActivity = mActivityRule.getActivity();

        View viewById = detailActivity.findViewById(R.id.detail_title);

        assertThat(viewById, notNullValue());
        assertThat(viewById, Matchers.<View>instanceOf(AppCompatTextView.class));
    }

    @Test
    public void CheckIfDescriptionTextViewIsPresent(){
        DetailActivity detailActivity = mActivityRule.getActivity();

        View viewById = detailActivity.findViewById(R.id.detail_description);

        assertThat(viewById, notNullValue());
        assertThat(viewById, Matchers.<View>instanceOf(AppCompatTextView.class));
    }


    @Test
    public void CheckIfTimeTextViewIsPresent(){
        DetailActivity detailActivity = mActivityRule.getActivity();

        View viewById = detailActivity.findViewById(R.id.detail_time);

        assertThat(viewById, notNullValue());
        assertThat(viewById, Matchers.<View>instanceOf(AppCompatTextView.class));
    }
}
