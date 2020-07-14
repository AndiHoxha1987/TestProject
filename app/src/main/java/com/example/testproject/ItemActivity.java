package com.example.testproject;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ItemActivity extends AppCompatActivity {

    private ImageView imageView,cameraView;
    private int galleryRequestCode = 438;
    private int REQUEST_IMAGE_CAPTURE = 1234;

    @VisibleForTesting
    protected static String KEY_IMAGE_DATA = "data";

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
        cameraView = findViewById(R.id.camera_view);
        imageView = findViewById(R.id.image_view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhoto();
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

    public void takePhoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void pickPhoto(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, galleryRequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if (requestCode == galleryRequestCode && data != null && data.getData() != null) {
                Uri fileUri = data.getData();
                Glide.with(this)
                        .load(fileUri)
                        .into(imageView);
            }else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                assert data != null;
                Bundle extras = data.getExtras();
                if (extras == null || !extras.containsKey(KEY_IMAGE_DATA)) {
                    return;
                }
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
            }
        }
    }
}
