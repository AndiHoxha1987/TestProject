package com.example.testproject;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.testproject.adapter.RecycleViewAdapter;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Test class showcasing some {@link RecyclerViewActions} from Espresso.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecycleViewAndEditTextTest {

    private static final int ITEM_BELOW_THE_FOLD = 40;

    /**
     * Use {@link ActivityScenario} to create and launch the activity under test. This is a
     * replacement for {@link androidx.test.rule.ActivityTestRule}.
     */
    @Rule
    public ActivityScenarioRule<RecycleViewActivity> activityScenarioRule =
            new ActivityScenarioRule<>(RecycleViewActivity.class);

    /**
     * The ActivityTestRule is a rule provided by Android used for functional testing of a single
     * activity. The activity that will be tested, MenuActivity in this case, will be launched
     * before each test that's annotated with @Test and before methods annotated with @Before.
     *
     * The activity will be terminated after the test and methods annotated with @After are
     * complete. This rule allows you to directly access the activity during the test.
     */
    @Rule
    public ActivityTestRule<RecycleViewActivity> mActivityTestRule =
            new ActivityTestRule<>(RecycleViewActivity.class);

    private IdlingResource mIdlingResource;
    private static final String STRING_TO_BE_TYPED = "Espresso";


    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void test_changeText_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.edit_text_user_input))
                .perform(typeText(STRING_TO_BE_TYPED), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.change_text_bt)).perform(click());

        // Check that the text was changed.
        onView(withId(R.id.test_text_input)).check(matches(withText(STRING_TO_BE_TYPED)));
    }

    @Test
    public void test_scrollToItemBelowFold_checkItsText() {
        //Close the keyboard first, because editText has enabled it
        onView(withId(R.id.activity_recycle_view)).perform(ViewActions.closeSoftKeyboard());
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(ITEM_BELOW_THE_FOLD, click()));

        // Match the text in an item below the fold and check that it's displayed.
        String itemElementText = getApplicationContext().getResources().getString(
                R.string.item_element_text) + ITEM_BELOW_THE_FOLD;
        onView(withText(itemElementText)).check(matches(isDisplayed()));
    }

    @Test
    public void test_itemInMiddleOfList_hasSpecialText() {
        //Close the keyboard first, because editText has enabled it
        onView(withId(R.id.activity_recycle_view)).perform(ViewActions.closeSoftKeyboard());
        // First, scroll to the view holder using the isInTheMiddle matcher.
        onView(ViewMatchers.withId(R.id.recyclerView))
                .perform(RecyclerViewActions.scrollToHolder(isInTheMiddle()));

        // Check that the item has the special text.
        String middleElementText =
                getApplicationContext().getResources().getString(R.string.middle);
        onView(withText(middleElementText)).check(matches(isDisplayed()));
    }

    /**
     * Matches the {@link RecycleViewAdapter.ViewHolder}s in the middle of the list.
     */
    private static Matcher<RecycleViewAdapter.ViewHolder> isInTheMiddle() {
        return new TypeSafeMatcher<RecycleViewAdapter.ViewHolder>() {
            @Override
            protected boolean matchesSafely(RecycleViewAdapter.ViewHolder customHolder) {
                return customHolder.getIsInTheMiddle();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("item in the middle");
            }
        };
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}