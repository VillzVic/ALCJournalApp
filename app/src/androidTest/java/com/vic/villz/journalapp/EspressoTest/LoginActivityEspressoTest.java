package com.vic.villz.journalapp.EspressoTest;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.vic.villz.journalapp.Activities.LoginActivity;
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
public class LoginActivityEspressoTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void EnsureEmail_IsNotNull(){
        //check if the login button exists
        onView(ViewMatchers.withId(R.id.login_btn)).check(matches(notNullValue()));
        onView(withId(R.id.login_btn)).perform(click());

        //This test will fail if email field is empty when you click the register button
        onView(withId(R.id.email_login)).check(matches(not(withText(""))));

    }

    @Test
    public void EnsurePassword_IsNotNull(){
        //check if the login button exists
        onView(withId(R.id.login_btn)).check(matches(notNullValue()));
        onView(withId(R.id.login_btn)).perform(click());

        //This test will fail if password field is empty when you click the login button
        onView(withId(R.id.password_login)).check(matches(not(withText(""))));

    }


    @Test
    public void testRegister_Link(){
        //check that the textview is there
        onView(withId(R.id.register)).check(matches(notNullValue()));
        onView(withId(R.id.register)).check(matches(withText("Register"))); //check that the text view has the text "Register"
        onView(withId(R.id.register)).perform(click());

        //when you click on register link, make sure that the Register Activity shows up
        onView(withId(R.id.signup_text)).check(matches(withText("SIGN UP")));
    }


}
