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

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.testproject.ImageViewHasDrawableMatcher.hasDrawable;
import static com.example.testproject.ItemActivity.KEY_IMAGE_DATA;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class ItemActivityTestCameraIntent {

    @Rule
    public IntentsTestRule<ItemActivity> mActivityRule = new IntentsTestRule<>(
            ItemActivity.class);

    @Before
    public void stubCameraIntent() {
        Instrumentation.ActivityResult result = createImageCaptureActivityResultStub();

        // Stub the Intent.
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(result);
    }

    @Test
    public void test_takePhoto_drawableIsApplied() {
        // Check that the ImageView doesn't have a drawable applied.
        onView(withId(R.id.image_view)).check(matches(not(hasDrawable())));

        // Click on the button that will trigger the stubbed intent.
        onView(withId(R.id.take_photo)).perform(click());

        // With no user interaction, the ImageView will have a drawable.
        onView(withId(R.id.image_view)).check(matches(hasDrawable()));
    }

    private Instrumentation.ActivityResult createImageCaptureActivityResultStub(){
        Bundle bundle = new Bundle();
        bundle.putParcelable(
                KEY_IMAGE_DATA, BitmapFactory.decodeResource(
                        mActivityRule.getActivity().getResources(),
                        R.drawable.ic_launcher_background
                )
        );
        Intent resultData = new  Intent();
        resultData.putExtras(bundle);
        return new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
    }
}
