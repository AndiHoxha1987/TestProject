package com.example.testproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Intent intent = getIntent();
        String mName = intent.getStringExtra(MainActivity.EXTRA_NAME);
        String mDescription = intent.getStringExtra(MainActivity.EXTRA_DESCRIPTION);
        int mAge = intent.getIntExtra(MainActivity.EXTRA_AGE,-1);

        TextView nameTextView = findViewById(R.id.name_text_view);
        nameTextView.setText(mName);
        TextView descriptionTextView = findViewById(R.id.description_text_view);
        descriptionTextView.setText(mDescription);
        TextView ageTextView = findViewById(R.id.age_text_view);
        ageTextView.setText(String.valueOf(mAge));

        Button btn = findViewById(R.id.click_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView testTextView = findViewById(R.id.test);
                testTextView.setText(R.string.button_test);
            }
        });
    }

    public void sendEmail(View view) {

        String emailMessage = getString(R.string.item_1_description);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.intent_email_subject));
        intent.putExtra(Intent.EXTRA_TEXT, emailMessage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);

        }
    }
}
