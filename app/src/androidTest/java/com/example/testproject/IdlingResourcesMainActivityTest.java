package com.example.testproject;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/**
 * Usually Espresso syncs all view operations with the UI thread as well as AsyncTasks, but it can't
 * do so with custom resources (e.g. activity or service). For such cases, we can register the
 * custom resource and Espresso will wait for the resource to be idle before
 * executing a view operation.
 *
 * In this example, we simulate an idling situation. This test is the same as the
 * MainActivityScreenTest but with an Idling Resource to help with synchronization.
 *
 * We added an idling period from when the user clicks on a GridView item
 * in MainActivity to when corresponding order activity appears. This is to simulate potential
 * delay that could happen if this data were being retrieved from the web. Without registering the
 * custom resources, this test would fail because the test would proceed without waiting
 * for the Idling Resource.
 */
@RunWith(AndroidJUnit4.class)
public class IdlingResourcesMainActivityTest {


    /**
     * The ActivityTestRule is a rule provided by Android used for functional testing of a single
     * activity. The activity that will be tested, MenuActivity in this case, will be launched
     * before each test that's annotated with @Test and before methods annotated with @Before.
     *
     * The activity will be terminated after the test and methods annotated with @After are
     * complete. This rule allows you to directly access the activity during the test.
     */
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void test_areDataVisible() {
        onData(anything()).inAdapterView(withId(R.id.grid_view)).atPosition(0).check(matches(isDisplayed()));

    }

    @Test
    public void test_goToItemActivityWithDataAndComeBackToMainActivity() {
        //perform a click in gridView from mainActivity
        onData(anything()).inAdapterView(withId(R.id.grid_view)).atPosition(0).perform(click());

        //check if itemActivity is opened with right data
        onView(withId(R.id.activity_item)).check(matches(isDisplayed()));
        onView(withId(R.id.name_text_view)).check(matches(withText(R.string.item_1_name)));
        onView(withId(R.id.description_text_view)).check(matches(withText(R.string.item_1_description)));
        onView(withId(R.id.age_text_view)).check(matches(withText("30")));

        //presBack button to send at previous Activity and check if it is opened with data in
        pressBack();
        onView(withId(R.id.activity_main)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.grid_view)).atPosition(0).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}
