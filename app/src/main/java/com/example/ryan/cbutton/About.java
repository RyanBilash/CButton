package com.example.ryan.cbutton;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import static com.example.ryan.cbutton.MainActivity.changeTime;
import static com.example.ryan.cbutton.MainActivity.getTime;
import static com.example.ryan.cbutton.MainActivity.soundEnabled;

public class About extends AppCompatActivity {
    public MediaPlayer bgm;

    public void bgmE(){
        bgm = MediaPlayer.create(this, R.raw.bgm);
        bgm.setVolume(0.2f,0.2f);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        bgmE();

        bgm.seekTo(getTime());
        bgm.start();
        bgm.setLooping(true);

        if(!soundEnabled()){
            bgm.pause();
        }else{
            bgm.start();
        }
        //This is simply the about page, and as such doesn't have much code
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
                if(soundEnabled()){
                    bgm.seekTo(getTime());
                    bgm.start();
                    bgm.setLooping(true);
                }
            }catch(Exception e){

            }
        }

    }

}
