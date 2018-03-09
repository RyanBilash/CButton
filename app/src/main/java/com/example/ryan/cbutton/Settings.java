package com.example.ryan.cbutton;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import static com.example.ryan.cbutton.MainActivity.Sound;
import static com.example.ryan.cbutton.MainActivity.buttonColor;
import static com.example.ryan.cbutton.MainActivity.changeTime;
import static com.example.ryan.cbutton.MainActivity.getTime;
import static com.example.ryan.cbutton.MainActivity.soundEnabled;
import static com.example.ryan.cbutton.MainActivity.voice;

public class Settings extends AppCompatActivity {
    public MediaPlayer bgm;

    public void bgmE(){
        bgm = MediaPlayer.create(this, R.raw.bgm);
        bgm.setVolume(0.2f,0.2f);
    }

    /*private static Context context;
    public Context getContext(){
        return context;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //context=getApplicationContext();

        final MediaPlayer click = MediaPlayer.create(this, R.raw.click);

        bgmE();

        bgm.seekTo(getTime());
        bgm.start();
        bgm.setLooping(true);

        if(!soundEnabled()){
            bgm.pause();
        }else{
            bgm.start();
        }



        Switch sw = (Switch)findViewById(R.id.switch1);
        sw.setChecked(soundEnabled());
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Sound(!soundEnabled());
                if(!soundEnabled()){
                    bgm.pause();
                    click.start();
                }else{
                    bgm.start();
                }

                writeToFile();

            }
        });
        ImageButton red = (ImageButton)findViewById(R.id.ButtonR);
        ImageButton blu = (ImageButton)findViewById(R.id.ButtonB);
        ImageButton pur = (ImageButton)findViewById(R.id.ButtonP);
        ImageButton gre = (ImageButton)findViewById(R.id.ButtonG);

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonColor = "red";
                if(soundEnabled()){
                    click.start();
                }
                writeToFile();
            }
        });
        blu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonColor = "blue";
                if(soundEnabled()){
                    click.start();
                }
                writeToFile();
            }
        });
        gre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonColor = "green";
                if(soundEnabled()){
                    click.start();
                }
                writeToFile();
            }
        });
        pur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonColor = "purple";
                if(soundEnabled()){
                    click.start();
                }
                writeToFile();
            }
        });

    }

    public void writeToFile(){
        try{
            FileOutputStream out = openFileOutput("settings.txt",MODE_PRIVATE);
            OutputStreamWriter ow = new OutputStreamWriter(out);

            ow.write(buttonColor+"\n"+voice);
            ow.close();
        }catch(Exception e){}
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
