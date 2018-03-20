package com.example.ashwani.incredibleindia;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class SelectGuide extends AppCompatActivity implements View.OnClickListener {

    private CardView king,queen;
    private Intent callIntent;
    private static final int REQUEST_CODE = 12546;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_guide);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        king = (CardView) findViewById(R.id.kingCard);
        queen = (CardView) findViewById(R.id.queenCard);
        king.setOnClickListener(this);
        queen.setOnClickListener(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:7291924104"));
                if (ActivityCompat.checkSelfPermission(SelectGuide.this,
                        android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SelectGuide.this,new String[]{android.Manifest.permission.CALL_PHONE},REQUEST_CODE);
                }else {
                    startActivity(callIntent);
                }
            }
        });
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

    @Override
    public void onClick(View v) {
        startActivity(new Intent(SelectGuide.this,NextDestination.class).putExtra("activity","qrscan"));
        finish();
    }
}
