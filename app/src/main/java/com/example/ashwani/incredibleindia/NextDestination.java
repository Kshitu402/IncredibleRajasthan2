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

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class NextDestination extends AppCompatActivity {

    private ImageView nextDestinationImage;
    private Button btnNextDestinationQrScan;
    private Intent callIntent;
    private static final int REQUEST_CODE = 12546;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_destination);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();

        String activity = bundle.getString("activity");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:7291924104"));
                if (ActivityCompat.checkSelfPermission(NextDestination.this,
                        android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(NextDestination.this,new String[]{android.Manifest.permission.CALL_PHONE},REQUEST_CODE);
                }else {
                    startActivity(callIntent);
                }
            }
        });

        nextDestinationImage = (ImageView) findViewById(R.id.nextDestinationImage);
        btnNextDestinationQrScan = (Button) findViewById(R.id.btnNextDestinationScanQr);
        btnNextDestinationQrScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NextDestination.this,PlaceQrScan.class));
                finish();
            }
        });
        if(activity.trim().equals("current")){
            nextDestinationImage.setImageResource(R.drawable.minarates);
            setTitle("Minarets");
        }else{
            setTitle("Main Gate");
        }
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

}