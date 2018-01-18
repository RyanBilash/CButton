package com.example.ryan.cbutton;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import static com.example.ryan.cbutton.MainActivity.changeTime;
import static com.example.ryan.cbutton.MainActivity.getTime;

public class Settings extends AppCompatActivity {
    public MediaPlayer bgm;

    public void bgmE(){
        bgm = MediaPlayer.create(this, R.raw.bgm);
        bgm.setVolume(0.2f,0.2f);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        bgmE();


        bgm.seekTo(getTime());
        bgm.start();
        bgm.setLooping(true);

    }
    protected void onPause(){
        super.onPause();
        if(bgm.isPlaying()){
            try{
                bgm.pause();
            }catch(Exception e){

            }
        }
        changeTime(bgm.getCurrentPosition());
    }

    protected void onResume(){
        super.onResume();
        if(!bgm.isPlaying()){
            try{
                bgm.seekTo(getTime());
                bgm.start();
                bgm.setLooping(true);
            }catch(Exception e){

            }
        }

    }

}
