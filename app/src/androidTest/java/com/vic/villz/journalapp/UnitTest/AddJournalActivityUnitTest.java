package com.vic.villz.journalapp.UnitTest;


import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.vic.villz.journalapp.Activities.AddJournalActivity;
import com.vic.villz.journalapp.R;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class AddJournalActivityUnitTest {

    @Rule
    public ActivityTestRule<AddJournalActivity> mActivityRule =
            new ActivityTestRule<>(AddJournalActivity.class);


    @Test
    public void EnsureButtonIsDisplayed() {
        AddJournalActivity addJournalActivity = mActivityRule.getActivity();

        View viewById= addJournalActivity.findViewById(R.id.upload_btn);

        assertThat(viewById, notNullValue());
        assertThat(viewById, Matchers.<View>instanceOf(AppCompatButton.class));
        AppCompatButton button = (AppCompatButton) viewById;
        assertThat(button.getText().toString(), is("Add"));
    }

}
