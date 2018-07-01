package com.vic.villz.journalapp.EspressoTest;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.vic.villz.journalapp.Activities.AddJournalActivity;
import com.vic.villz.journalapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;


@RunWith(AndroidJUnit4.class)
public class AddJournalActivityEspressoTest {

    @Rule
    public ActivityTestRule<AddJournalActivity> mActivityRule =
            new ActivityTestRule<>(AddJournalActivity.class);

    @Test
    public void ValidateInput_Details(){

        //type inputs to the text
        onView(withId(R.id.add_title)).perform(typeText("Journal Title"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.add_content)).perform(typeText("Journal Description"), ViewActions.closeSoftKeyboard());
        //check that the button is there
        onView(ViewMatchers.withId(R.id.upload_btn)).check(matches(notNullValue()));
        onView(withId(R.id.upload_btn)).perform(click());

        //ensure input fields are not null when the add button is clicked
        onView(withId(R.id.add_title)).check(matches(not(withText(""))));
        onView(withId(R.id.add_content)).check(matches(not(withText(""))));
    }
}
