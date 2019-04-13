package com.example.timeupgrader;


import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SignUpActivityTest {

    @Rule
    public ActivityTestRule<SignupActivity> mActivityTestRule = new ActivityTestRule<>(SignupActivity.class);

    @Test
    public void SignUpActivityTest(){
        onView(withId(R.id.etEmail)).perform(typeText("yyz@qq.com"));
        onView(withId(R.id.etPassword)).perform(typeText("123456"), closeSoftKeyboard());//onView(withId(R.id.etPassword2)).perform(typeText("123123"),closeSoftKeyboard());
        onView(withId(R.id.btnSignup)).perform(click());

    }
}
