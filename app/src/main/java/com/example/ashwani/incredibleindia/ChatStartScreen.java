package com.example.ashwani.incredibleindia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class ChatStartScreen extends AppCompatActivity {

    private CardView card1,card2,card3,card4,card5,card6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_start_screen);

        card1 = (CardView) findViewById(R.id.chatScreenCard1);
        card2 = (CardView) findViewById(R.id.chatScreenCard2);
        card3 = (CardView) findViewById(R.id.chatScreenCard3);
        card4 = (CardView) findViewById(R.id.chatScreenCard4);
        card5 = (CardView) findViewById(R.id.chatScreenCard5);
        card6 = (CardView) findViewById(R.id.chatScreenCard6);
        final Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mytran);

        card1.startAnimation(myanim);
        card2.startAnimation(myanim);
        card3.startAnimation(myanim);
        card4.startAnimation(myanim);
        card5.startAnimation(myanim);
        card6.startAnimation(myanim);



        Thread t = new Thread(){
            public void run(){
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    startActivity(new Intent(ChatStartScreen.this,ChatBox.class));
                    finish();
                }
            }
        };
        t.start();
    }
}
