package com.example.ashwani.incredibleindia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class TouristPlaceWelcomeScreen extends AppCompatActivity {

    private TextView touristPlaceWelcomePlaceName;
    private ImageView touristPlaceWelcomeImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tourist_place_welcome_screen);

        touristPlaceWelcomeImage = (ImageView) findViewById(R.id.touristPlaceWelcomeImage);
        touristPlaceWelcomePlaceName = (TextView) findViewById(R.id.touristPlaceWelcomePlaceName);



        Thread t = new Thread(){
            public void run(){
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    startActivity(new Intent(TouristPlaceWelcomeScreen.this,ConnectWifi.class));
                    finish();
                }
            }
        };
        t.start();

    }
}
