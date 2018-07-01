package com.vic.villz.journalapp.EspressoTest;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.vic.villz.journalapp.Activities.SplashScreen;
import com.vic.villz.journalapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(AndroidJUnit4.class)
public class SplashScreenEspressoTest {

    @Rule
    public ActivityTestRule<SplashScreen> mActivityRule =
            new ActivityTestRule<>(SplashScreen.class);

    @Test
    public void testSplashScreenVisibility(){
        onView(withId(R.id.journal_logo)).check(matches(notNullValue()));
        onView(withId(R.id.journal_text_splash_screen)).check(matches(notNullValue()));
    }
}
