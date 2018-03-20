package com.example.ashwani.incredibleindia;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class TouristPlace extends AppCompatActivity implements View.OnClickListener {

    private TextView touristPlaceImageLocation,touristPlaceImageDetail,touristPlaceKnowMore;
    private Button btnStartTour;
    private ImageView touristPlaceImage;
    private Intent callIntent;
    private static final int REQUEST_CODE = 12546;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_place);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:7042254504"));
            if (ActivityCompat.checkSelfPermission(TouristPlace.this,
                    android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(TouristPlace.this,new String[]{android.Manifest.permission.CALL_PHONE},REQUEST_CODE);
            }else {
                startActivity(callIntent);
            }
            }
        });
        setTitle("Hawa Mahal");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult!=null){
            if(intentResult.getContents() != null){
                startActivity(callIntent);
            }
        }else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    private void initView() {
        touristPlaceImage = (ImageView) findViewById(R.id.touristPlaceImage);
        touristPlaceImageDetail = (TextView) findViewById(R.id.touristPlaceImageDetail);
        touristPlaceImageLocation = (TextView) findViewById(R.id.touristPlaceImageLocation);
        touristPlaceKnowMore = (TextView) findViewById(R.id.touristPlaceKnowMore);
        btnStartTour = (Button) findViewById(R.id.btnStartTour);
        btnStartTour.setOnClickListener(this);
        touristPlaceKnowMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStartTour:
                startActivity(new Intent(TouristPlace.this,TouristPlaceWelcomeScreen.class));
                break;
            case R.id.touristPlaceKnowMore:
                startActivity(new Intent(TouristPlace.this,TouristPlaceKnowMore.class));
                break;
        }
    }
}
