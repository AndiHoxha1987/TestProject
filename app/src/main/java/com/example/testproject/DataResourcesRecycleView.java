package com.example.testproject;


import android.content.Context;
import android.os.Handler;

import androidx.annotation.Nullable;
import com.example.testproject.IdlingResouce.SimpleIdlingResource;

import java.util.ArrayList;

public class DataResourcesRecycleView {
    private static final int DELAY_MILLIS = 1000;
    private static final int DATA_SET_COUNT = 50;
    private final static ArrayList<String> mList = new ArrayList<>();

    interface DelayerCallbackRecycle{

        void onDoneString(String text);
        void onDoneList(ArrayList<String> list);
    }

    /**
     * This method is meant to simulate downloading a large file which has a loading time
     * delay. This could be similar to downloading a file from the internet.
     * We simulate a delay time of {@link #DELAY_MILLIS} and once the time
     * is up we return the item back to the calling activity via a {@link DataResourcesMain.DelayerCallback}.
     * @param callback used to notify the caller asynchronously
     */
    static void openItemList(Context context, final DataResourcesRecycleView.DelayerCallbackRecycle callback,
                         @Nullable final SimpleIdlingResource idlingResource) {

        /**
         * The IdlingResource is null in production as set by the @Nullable annotation which means
         * the value is allowed to be null.
         *
         * If the idle state is true, Espresso can perform the next action.
         * If the idle state is false, Espresso will wait until it is true before
         * performing the next action.
         */
        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }

        if(mList.size()==0){
            for (int i = 0; i < DATA_SET_COUNT; i++) {
                mList.add(context.getString(R.string.item_element_text) + i);
            }
        }

        /**
         * {@link postDelayed} allows the {@link Runnable} to be run after the specified amount of
         * time set in DELAY_MILLIS elapses. An object that implements the Runnable interface
         * creates a thread. When this thread starts, the object's run method is called.
         *
         * After the time elapses, if there is a callback we return the image resource ID and
         * set the idle state to true.
         */
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onDoneList(mList);
                    if (idlingResource != null) {
                        idlingResource.setIdleState(true);
                    }
                }
            }
        }, DELAY_MILLIS);
    }

    /**
     * Takes a String and returns it after {@link #DELAY_MILLIS} via a {@link DataResourcesMain.DelayerCallback}.
     * @param message the String that will be returned via the callback
     * @param callback used to notify the caller asynchronously
     */
    static void processMessage(final String message, final DataResourcesRecycleView.DelayerCallbackRecycle callback,
                               @Nullable final SimpleIdlingResource idlingResource) {
        // The IdlingResource is null in production.
        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }

        // Delay the execution, return message via callback.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onDoneString(message);
                    if (idlingResource != null) {
                        idlingResource.setIdleState(true);
                    }
                }
            }
        }, DELAY_MILLIS);
    }
}
