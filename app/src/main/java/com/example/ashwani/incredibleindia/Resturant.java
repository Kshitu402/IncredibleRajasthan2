package com.example.ashwani.incredibleindia;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class Resturant extends AppCompatActivity {

    private RecyclerView rvitem;
    private TextView restname;
    private TextView resttime;
    private TextView restspecial;
    private TextView restinfo;

    private ArrayList<Reviews> list = new ArrayList<>();
    private Intent callIntent;
    private static final int REQUEST_CODE = 12546;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:7291924104"));
                if (ActivityCompat.checkSelfPermission(Resturant.this,
                        android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Resturant.this,new String[]{android.Manifest.permission.CALL_PHONE},REQUEST_CODE);
                }else {
                    startActivity(callIntent);
                }
            }
        });
        Bundle bb=getIntent().getExtras();
        restname=(TextView)findViewById(R.id.restname);
        resttime=(TextView)findViewById(R.id.resttime);
        //restspecial=(TextView)findViewById(R.id.restspecial);
        restinfo=(TextView)findViewById(R.id.restinfo);
        restname.setText(bb.getCharSequence("restaurantname"));
        resttime.setText(bb.getCharSequence("restaurantspecial"));
        restinfo.setText("The cuisine consists of both vegetarian and non-vegetarian dishes of different varieties. Being a large state, the cuisine of Uttar Pradesh shares a lot of dishes and recipes with the neighboring states");
        rvitem = (RecyclerView) findViewById(R.id.rvitem);

        rvitem.setHasFixedSize(true);

        GridLayoutManager manager = new GridLayoutManager(this, 1);
        rvitem.setLayoutManager(manager);
        set_data();
        ItemAdapter adapter = new ItemAdapter(getApplicationContext(), list);
        rvitem.setAdapter(adapter);
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


    float p=1.0f;
    private void set_data()
    {

        for (int i = 0; i < 10; i++) {
            if(i%2==0)
            {
                p++;
            }
            else
            {
                p--;
            }
            Reviews item = new Reviews();
            item.reviewperson = "Adam K Muller";
            item.reviewcountry = "GER";
            item.reviewrate = 3.0f+p;
            item.reviewinfo = "Lovely place to hang out";
            list.add(item);
        }
    }

}
