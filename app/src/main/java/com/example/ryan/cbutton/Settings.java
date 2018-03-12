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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final MediaPlayer click = MediaPlayer.create(this, R.raw.click);

        bgmE();

        bgm.seekTo(getTime()); //goes to current time in the music
        bgm.start(); //starts music
        bgm.setLooping(true); //loops music

        if(!soundEnabled()){
            bgm.pause();
        }else{
            bgm.start();
        } //turn sound on or off



        Switch sw = (Switch)findViewById(R.id.switch1);//Switch the sound on or off
        sw.setChecked(soundEnabled());
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Sound(!soundEnabled());//Change the sound setting
                if(!soundEnabled()){
                    bgm.pause();
                    click.start();
                }else{
                    bgm.start();
                }

                writeToFile();

            }
        });
        ImageButton red = (ImageButton)findViewById(R.id.ButtonR);//The red button
        ImageButton blu = (ImageButton)findViewById(R.id.ButtonB);//The blue button
        ImageButton pur = (ImageButton)findViewById(R.id.ButtonP);//The purple button
        ImageButton gre = (ImageButton)findViewById(R.id.ButtonG);//The green button

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonColor = "red";
                if(soundEnabled()){
                    click.start();
                }
                writeToFile();
                //Sets button to red
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
                //Sets button to blue
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
                //Sets button to green
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
                //Sets button to purple
            }
        });

    }

    public void writeToFile(){
        try{
            FileOutputStream out = openFileOutput("settings.txt",MODE_PRIVATE);
            OutputStreamWriter ow = new OutputStreamWriter(out);
            //Writes to the settings file with the new settings passed through the method
            ow.write(buttonColor+"\n"+voice);
            ow.close();
        }catch(Exception e){}
    }

    protected void onPause(){ //pause music
        super.onPause();
        if(bgm.isPlaying()){
            try{
                bgm.pause();
            }catch(Exception e){

            }
        }
         changeTime(bgm.getCurrentPosition());
    }

    protected void onResume(){ //resume music
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
