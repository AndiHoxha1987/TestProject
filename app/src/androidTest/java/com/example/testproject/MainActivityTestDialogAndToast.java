package com.example.testproject;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTestDialogAndToast {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void test_showDialogAndToastAndPerformClickButtons(){
        //launch mainActivity, test views
        onView(withId(R.id.activity_main)).check(matches(isDisplayed()));
        onView(withId(R.id.button_launch_dialog)).check(matches(isDisplayed()));

        //click button to show dialog
        onView(withId(R.id.button_launch_dialog)).perform(click());

        //check is dialog displayed
        onView(withText(R.string.text_title_of_dialog)).check(matches(isDisplayed()));
        onView(withText(R.string.cancel)).check(matches(isDisplayed()));
        onView(withText(R.string.text_ok)).check(matches(isDisplayed()));

        //click positive button of dialog
        onView(withText(R.string.text_ok)).perform(click());

        // make sure dialog is gone
        onView(withText(R.string.text_title_of_dialog)).check(doesNotExist());
        onView(withText(R.string.cancel)).check(doesNotExist());


        //check are views displayed with data from dialog
        onView(withText(R.string.text_ok_toast)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withId(R.id.text_name)).check(matches(withText(R.string.text_ok)));
        onView(withId(R.id.button_launch_dialog)).check(matches(isDisplayed()));
        onView(withId(R.id.button_launch_dialog)).perform(click());

        //check is dialog displayed
        onView(withText(R.string.text_title_of_dialog)).check(matches(isDisplayed()));
        onView(withText(R.string.cancel)).check(matches(isDisplayed()));
        onView(withText(R.string.text_ok)).check(matches(isDisplayed()));

        //click negative button of dialog
        onView(withText(R.string.cancel)).perform(click());

        // make sure dialog is gone
        onView(withText(R.string.text_title_of_dialog)).check(doesNotExist());
        onView(withText(R.string.text_ok)).check(doesNotExist());

        //check are views displayed with data from dialog
        onView(withText(R.string.cancel_toast)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withId(R.id.text_name)).check(matches(withText(R.string.cancel)));
        onView(withId(R.id.button_launch_dialog)).check(matches(isDisplayed()));

    }
}
