package com.example.ashwani.incredibleindia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        iv = (ImageView) findViewById(R.id.imageIcon);
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mytran);
        iv.startAnimation(myanim);

        Thread t = new Thread(){
            public void run(){
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    startActivity(new Intent(SplashScreen.this,Login.class));
                    finish();
                }
            }
        };
        t.start();
    }
}
