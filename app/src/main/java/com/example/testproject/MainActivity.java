package com.example.testproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.test.espresso.IdlingResource;
import com.example.testproject.IdlingResouce.SimpleIdlingResource;
import com.example.testproject.adapter.ItemAdapter;
import com.example.testproject.model.Item;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DataResources.DelayerCallback{

    public final static String EXTRA_NAME = "com.example.testProject;.EXTRA_NAME";
    public final static String EXTRA_DESCRIPTION = "com.example.testProject;.EXTRA_DESCRIPTION";
    public final static String EXTRA_AGE = "com.example.testProject;.EXTRA_AGE";

    /** Add a SimpleIdlingResource variable that will be null in production*/
    @Nullable
    private SimpleIdlingResource mIdlingResource ;

    /**
     * Create a method that returns the IdlingResource variable. It will
     * instantiate a new instance of SimpleIdlingResource if the IdlingResource is null.
     * This method will only be called from test.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }


    /**
     * Using the method you created, get the IdlingResource variable.
     * Then call downloadImage from ImageDownloader. To ensure there's enough time for IdlingResource
     * to be initialized, remember to call downloadImage in either onStart or onResume.
     * This is because @Before in Espresso Tests is executed after the activity is created in
     * onCreate, so there might not be enough time to register the IdlingResource if the download is
     * done too early.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getIdlingResource();
    }
    /**
     * We call DataSource.openItem from onStart or onResume instead of in onCreate
     * to ensure there is enough time to register IdlingResource if the download is done
     * too early (i.e. in onCreate)
     */
    @Override
    protected void onStart() {
        super.onStart();
        DataResources.openItem(this, MainActivity.this, mIdlingResource);
    }

    /**
     * When the thread in DataResources is finished, it will return an ArrayList of Item
     * objects via the callback's onDone().
     */
    @Override
    public void onDone(ArrayList<Item> mItem) {

        /** Create an adapter, whose data source is a list of Items.
         *  The adapter know how to create grid items for each item in the list.*/

        GridView gridview = findViewById(R.id.grid_view);
        ItemAdapter adapter = new ItemAdapter(this, R.layout.item_layout, mItem);
        gridview.setAdapter(adapter);


        // Set a click listener on that View
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent mItemIntent = new Intent(MainActivity.this, ItemActivity.class);
                Item item = (Item) adapterView.getItemAtPosition(position);
                String name = item.getName();
                String description = item.getDescription();
                int age = item.getAge();
                mItemIntent.putExtra(EXTRA_NAME, name);
                mItemIntent.putExtra(EXTRA_DESCRIPTION,description);
                mItemIntent.putExtra(EXTRA_AGE, age);
                startActivity(mItemIntent);
            }
        });
    }

}