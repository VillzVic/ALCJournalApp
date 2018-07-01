package com.vic.villz.journalapp.EspressoTest;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.vic.villz.journalapp.Activities.RegisterActivity;
import com.vic.villz.journalapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
public class RegisterActivityEspressoTest {

    @Rule
    public ActivityTestRule<RegisterActivity> mActivityRule =
            new ActivityTestRule<>(RegisterActivity.class);

    @Test
    public void EnsureUserNameField_IsNotNull(){
        onView(ViewMatchers.withId(R.id.register_btn)).check(matches(notNullValue()));
        onView(withId(R.id.register_btn)).perform(click());

        //This test will fail if username field is empty when you click the register button
        onView(withId(R.id.register_username)).check(matches(not(withText(""))));
    }
    @Test
    public void EnsureEmailField_IsNotNull(){
        onView(withId(R.id.register_btn)).check(matches(notNullValue()));
        onView(withId(R.id.register_btn)).perform(click());

        //This test will fail if email field is empty when you click the register button
        onView(withId(R.id.register_email)).check(matches(not(withText(""))));
    }

    @Test
    public void EnsurePasswordField_IsNotNull(){
        onView(withId(R.id.register_btn)).check(matches(notNullValue()));
        onView(withId(R.id.register_btn)).perform(click());

        //This test will fail if password field is empty when you click the register button
        onView(withId(R.id.register_password)).check(matches(not(withText(""))));
    }


    @Test
    public void testLogin_Link(){
        //check that the textview is there
        onView(withId(R.id.login_link)).check(matches(notNullValue()));
        onView(withId(R.id.login_link)).check(matches(withText("Login")));
        onView(withId(R.id.login_link)).perform(click());

        //when you click on login link, make sure that the Login Activity shows up
        onView(withId(R.id.signin_text)).check(matches(withText("SIGN IN")));
    }
}
