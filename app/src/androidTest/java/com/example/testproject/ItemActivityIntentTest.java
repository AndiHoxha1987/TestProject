package com.example.testproject;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.app.Activity.RESULT_OK;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasType;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.testproject.ImageViewHasDrawableMatcher.hasDrawable;
import static com.example.testproject.ItemActivity.KEY_IMAGE_DATA;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class ItemActivityIntentTest {
    private static final String EMAIL_MESSAGE = "Test D1";

    @Rule
    public IntentsTestRule<ItemActivity> mActivityRule = new IntentsTestRule<>(
            ItemActivity.class);

    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal())).respondWith(
                new Instrumentation.ActivityResult(RESULT_OK, null));
    }

    @Test
    public void clickSendEmailButton_SendsEmail() {

        onView(withId(R.id.send_email)).perform(click());
        intended(allOf(
                hasAction(Intent.ACTION_SENDTO),
                hasExtra(Intent.EXTRA_TEXT, EMAIL_MESSAGE)));

    }

    @Test
    public void test_validateIntentSentToPickPackage() {
        onView(withId(R.id.image_view)).perform(click());
        intended(allOf(
                hasAction(Intent.ACTION_PICK),
                hasType("image/*")
        ));

    }

}
