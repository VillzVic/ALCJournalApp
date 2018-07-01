package com.vic.villz.journalapp.EspressoTest;

import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.vic.villz.journalapp.Activities.AddJournalActivity;
import com.vic.villz.journalapp.Activities.HomePageActivity;
import com.vic.villz.journalapp.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
public class HomePageActivityEspressoTest {

    @Rule
    public ActivityTestRule<HomePageActivity> mActivityRule =
            new ActivityTestRule<>(HomePageActivity.class);

    @Before
    public void initializeIntent(){
        Intents.init();
    }

    @Test
    public void TestAddActivity_Button(){
       //check the button is there
        onView(ViewMatchers.withId(R.id.add_journal)).check(matches(notNullValue()));
        onView(withId(R.id.add_journal)).perform(click());

        //check that all the contents of the AddJournalActivity is displayed
        onView(withId(R.id.add_thought_test)).check(matches(isDisplayed()));
        onView(withId(R.id.add_title)).check(matches(isDisplayed()));
        onView(withId(R.id.add_content)).check(matches(isDisplayed()));
        onView(withId(R.id.upload_btn)).check(matches(isDisplayed()));


    }

    @Test
    public void triggerIntentTest(){
        //check the button is there
        onView(withId(R.id.add_journal)).check(matches(notNullValue()));
        onView(withId(R.id.add_journal)).perform(click());

        //check if the AddJournalActivityActivity is in the intent
        intended(hasComponent(AddJournalActivity.class.getName()));
    }

    @After
    public void ReleaseIntent(){
        Intents.release();
    }
}
