package com.example.testproject;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;
import androidx.annotation.Nullable;

import com.example.testproject.IdlingResouce.SimpleIdlingResource;
import com.example.testproject.model.Item;
import java.util.ArrayList;

public class DataResources {
    private static final int DELAY_MILLIS = 3000;

    final static ArrayList<Item> mItem = new ArrayList<>();

    interface DelayerCallback{
        void onDone(ArrayList<Item> items);
    }

    /**
     * This method is meant to simulate downloading a large file which has a loading time
     * delay. This could be similar to downloading a file from the internet.
     * We simulate a delay time of {@link #DELAY_MILLIS} and once the time
     * is up we return the item back to the calling activity via a {@link DelayerCallback}.
     * @param callback used to notify the caller asynchronously
     */
    static void openItem(Context context, final DelayerCallback callback,
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


        // Display a toast to let the user know the images are downloading
        String text = context.getString(R.string.loading_msg);
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        int itemOneAge = 30;
        int itemTwoAge = 33;

        mItem.add(new Item(context.getString(R.string.item_1_name),context.getString(R.string.item_1_description),itemOneAge));
        mItem.add(new Item(context.getString(R.string.item_2_name),context.getString(R.string.item_2_description),itemTwoAge));

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
                    callback.onDone(mItem);
                    if (idlingResource != null) {
                        idlingResource.setIdleState(true);
                    }
                }
            }
        }, DELAY_MILLIS);
    }
}
