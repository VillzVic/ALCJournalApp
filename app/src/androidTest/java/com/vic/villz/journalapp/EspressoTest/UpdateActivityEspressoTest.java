package com.vic.villz.journalapp.EspressoTest;


import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.vic.villz.journalapp.Activities.UpdateActivity;
import com.vic.villz.journalapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNull.notNullValue;


@RunWith(AndroidJUnit4.class)
public class UpdateActivityEspressoTest {

    @Rule
    public IntentsTestRule<UpdateActivity> mActivityRule =
            new IntentsTestRule<>(UpdateActivity.class);

    @Test
    public void EnsureUpdateButtonIsPresent(){

        //when the update button is clicked, verify that the input fields are not null
        onView(withId(R.id.update_btn)).check(matches(notNullValue()));
        onView(withId(R.id.update_btn)).check(matches(withText("UPDATE")));
    }





}
