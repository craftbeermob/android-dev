package com.example.craftbeermob.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.example.craftbeermob.Models.RecentActivity;
import com.example.craftbeermob.R;

import java.util.Date;
import java.util.UUID;

public class BeerPicker extends AppCompatActivity {

    public static final int BEERPICKER_REQUEST_CODE = 202;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_picker);

        Intent intent = getIntent();
        Bitmap beerPhoto = intent.getParcelableExtra("beerphoto");

        ImageView imageView = (ImageView) findViewById(R.id.beerpicker_beerphoto);
        imageView.setImageBitmap(beerPhoto);

        final Spinner brewerSpinner = (Spinner) findViewById(R.id.beerpicker_brewer_spinner);
        final Spinner beerTypeSpinner = (Spinner) findViewById(R.id.beerpicker_type_spinner);
        final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        final EditText commentEt = (EditText) findViewById(R.id.beerpicker_comments_et);
        Button nextBtn = (Button) findViewById(R.id.nextBtn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String selectedBeerType = (String) beerTypeSpinner.getSelectedItem();
                String selectedBrewer = (String) brewerSpinner.getSelectedItem();
                float rating = ratingBar.getRating();
                String comments = commentEt.getText().toString();


                RecentActivity recentActivity = new RecentActivity(UUID.randomUUID().toString());
                recentActivity.setBrewery_Info(selectedBrewer);
                recentActivity.setBeerType(selectedBeerType);
                recentActivity.setRating(rating);
                recentActivity.setUserId(App.GetUserSingleton().getUserId());
                recentActivity.setComment(comments);
                recentActivity.setCreatedAt(new Date().toString());
                //TODO://change to real username later
                recentActivity.setUsername("testuser");
                Intent resultIntent = new Intent();
                resultIntent.putExtra("recentactivity", recentActivity);
                setResult(RESULT_OK, resultIntent);
                finish();


            }
        });


    }


}
