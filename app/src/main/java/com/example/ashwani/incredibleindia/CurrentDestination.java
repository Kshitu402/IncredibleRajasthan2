package com.example.ashwani.incredibleindia;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class CurrentDestination extends AppCompatActivity implements View.OnClickListener {

    private ImageView currentDestinationImage;
    private TextView currentDestinationImageDetail;
    private SeekBar seekBar;
    private ImageView btnPlayPause;
    private MediaPlayer mediaPlayer;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_destination);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Main Gate");
        initView();
    }

    private void initView() {
        currentDestinationImage = (ImageView) findViewById(R.id.currentDestinationImage);
        currentDestinationImageDetail = (TextView) findViewById(R.id.currentDestinationImageDetails);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setClickable(false);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        btnPlayPause = (ImageView) findViewById(R.id.btnPlayPause);
        btnPlayPause.setOnClickListener(this);

        String s = "Referred to as Darwaza-i-Rauza or \"gate of the mausoleum\" by the architect Ustad Ahmad Lahauri himself, the main gateway to Taj Mahal is indeed a worthy counterpart to the mausoleum in every sense of the phrase. No doubt that Taj looks splendid when seen from distance, but it's the child-like enthusiasm to adore it from touching distance that takes the anticipations to an all new level. And just when one reaches the open square before the main gateway, the majestic view of Taj disappears completely, only to manifest itself in an altogether glorious way when one stands right in the doorway itself, the doorway to the main mausoleum. The concept of Taj emerging out of the shadows and slowly growing on you seems even more praiseworthy if one dives into the abstract interpretation that suggests a transition from the outer physical world to the inner spiritual world.";

        currentDestinationImageDetail.setText(s);

        handler = new Handler();
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.first);

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(mediaPlayer.getDuration());
                playCycle();
                mediaPlayer.start();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });
    }

    public void playCycle(){
        seekBar.setProgress(mediaPlayer.getCurrentPosition());

        if(mediaPlayer.isPlaying()){
            runnable = new Runnable() {
                @Override
                public void run() {
                    playCycle();
                }
            };
            handler.postDelayed(runnable,1000);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    public void onClick(View v) {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            btnPlayPause.setImageResource(R.drawable.play);
        }else{
            mediaPlayer.start();
            btnPlayPause.setImageResource(R.drawable.pause);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        handler.removeCallbacks(runnable);
        startActivity(new Intent(CurrentDestination.this,NextDestination.class).putExtra("activity","current"));
        finish();
    }
}
