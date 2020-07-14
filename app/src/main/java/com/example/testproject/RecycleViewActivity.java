package com.example.testproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingResource;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.testproject.IdlingResouce.SimpleIdlingResource;
import com.example.testproject.adapter.RecycleViewAdapter;

import java.util.ArrayList;

public class RecycleViewActivity extends AppCompatActivity implements DataResourcesRecycleView.DelayerCallbackRecycle{

    // The TextView used to display the message inside the Activity.
    private TextView testTextInput;
    // The EditText where the user types the message.
    private EditText mEditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

        testTextInput = findViewById(R.id.test_text_input);
        mEditText = findViewById(R.id.edit_text_user_input);

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
        DataResourcesRecycleView.openItemList(this, RecycleViewActivity.this, mIdlingResource);
    }

    public void changeTextButton(View view){
        // Get the text from the EditText view.
        final String text = mEditText.getText().toString();

        if (view.getId() == R.id.change_text_bt) {
            // Set a temporary text.
            testTextInput.setText(R.string.waiting_msg);
            // Submit the message to the delayer.
            DataResourcesRecycleView.processMessage(text, this, mIdlingResource);
        }
    }

    @Override
    public void onDoneString(String text) {
        // The delayer notifies the activity via a callback.
        testTextInput.setText(text);
    }

    @Override
    public void onDoneList(ArrayList<String> list) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        RecycleViewAdapter adapter = new RecycleViewAdapter(list, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
}
