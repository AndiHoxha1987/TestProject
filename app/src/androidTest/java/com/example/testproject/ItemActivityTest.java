package com.example.testproject;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ItemActivityTest {
    @Rule
    public ActivityTestRule<ItemActivity> mActivityTestRule =
            new ActivityTestRule<>(ItemActivity.class);

    @Test
    public void clickButton() {

        onView((withId(R.id.test))).check(matches(withText(R.string.age)));

        onView((withId(R.id.click_button))).perform(click());

        onView(withId(R.id.test)).check(matches(withText(R.string.button_test)));
    }
}
