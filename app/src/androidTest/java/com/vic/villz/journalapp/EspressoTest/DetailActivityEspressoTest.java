package com.vic.villz.journalapp.EspressoTest;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.vic.villz.journalapp.Activities.DetailActivity;
import com.vic.villz.journalapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
public class DetailActivityEspressoTest {

    @Rule
    public ActivityTestRule<DetailActivity> mActivityRule =
            new ActivityTestRule<>(DetailActivity.class);

    @Test
    public void verifyThatTheTextViews_AreNotNUll(){

        onView(ViewMatchers.withId(R.id.detail_title)).check(matches(notNullValue()));
        onView(withId(R.id.detail_time)).check(matches(notNullValue()));
        onView(withId(R.id.detail_description)).check(matches(notNullValue()));
    }
}
