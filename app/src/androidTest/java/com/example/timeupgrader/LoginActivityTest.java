package com.example.timeupgrader;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)

public class LoginActivityTest {
    //public static final String STRING_TO_BE_TYPED = "Espresso";
    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void LoginActivityTest() {
        onView(withId(R.id.etUserEmail)).perform(typeText("yyz@qq.com"));
        onView(withId(R.id.etUserPassword)).perform(typeText("123456"));
        //onView(withId(R.id.etPassword2)).perform(typeText("123123"),closeSoftKeyboard());
        onView(withId(R.id.btnUserLogin)).perform(click());
    }

}
