package com.example.testproject;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {ItemActivityTest.class,
        ItemActivityIntentTest.class,
        RecycleViewAndEditTextTest.class,
        IdlingResourcesMainActivityTest.class,
        ItemActivityTestCameraIntent.class,
        MainActivityTestDialogAndToast.class}
)
public class ActivityTestSuite {
}
