package com.example.testproject;

import android.app.Instrumentation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.app.Activity.RESULT_OK;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class ItemActivityIntentPickImageTest {

    @Rule
    public ActivityTestRule<ItemActivity> mActivityRule = new ActivityTestRule<>(
            ItemActivity.class);

    @Before
    public void setUp() throws Exception{
        Intents.init();
    }

    @Test
    public void _test_e_testComposeWithAttachments() {

        Matcher<Intent> expectedIntent =  allOf(
                hasAction(Intent.ACTION_PICK),
                hasData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        );
        Instrumentation.ActivityResult activityResult = createGalleryPickActivityResultStub();
        intending(expectedIntent).respondWith(activityResult);

        onView(withId(R.id.image_view)).perform(click());
        intended(expectedIntent);
    }

    private Instrumentation.ActivityResult createGalleryPickActivityResultStub() {
        Resources resources = InstrumentationRegistry.getInstrumentation().getContext().getResources();
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(R.mipmap.ic_launcher) + '/' +
                resources.getResourceTypeName(R.mipmap.ic_launcher) + '/' +
                resources.getResourceEntryName(R.mipmap.ic_launcher));
        Intent resultIntent = new Intent();
        resultIntent.setData(imageUri);
        return new Instrumentation.ActivityResult(RESULT_OK,resultIntent);
    }

    @After
    public void tearDown() throws Exception{
        Intents.release();
    }
}
