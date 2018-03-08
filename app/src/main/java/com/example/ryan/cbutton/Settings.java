package com.example.ryan.cbutton;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import static com.example.ryan.cbutton.MainActivity.changeTime;
import static com.example.ryan.cbutton.MainActivity.getBC;
import static com.example.ryan.cbutton.MainActivity.getS;
import static com.example.ryan.cbutton.MainActivity.getTime;

public class Settings extends AppCompatActivity {
    public MediaPlayer bgm;

    String buttonColor=getBC();
    String voice = getS();
    File file = new File("settings.txt");

    public BufferedWriter outStream;

    public boolean soundEnabled(){
        if(voice.equals("Sound")){
            return true;
        }else{
            return false;
        }
    }

    public void Sound(boolean n){
        if(n){
            voice="Sound";
        }else{
            voice="noSound";
        }
    }

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

        bgm.seekTo(getTime());
        bgm.start();
        bgm.setLooping(true);

        if(!soundEnabled()){
            bgm.stop();
        }else{
            bgm.start();
        }



        Switch sw = (Switch)findViewById(R.id.switch1);
        sw.setChecked(soundEnabled());
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(soundEnabled()){
                    bgm.stop();
                    click.start();
                }else{
                    bgm.start();
                }
                Sound(!soundEnabled());
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

    private void writeToFile(){
        try {
            outStream = new BufferedWriter(new FileWriter("settings.txt"));
            outStream.newLine();
            outStream.write(buttonColor);
            outStream.newLine();
            outStream.write(voice);
            outStream.close();
        } catch (Exception e) {}
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
                if(!soundEnabled()){
                    bgm.seekTo(getTime());
                    bgm.start();
                    bgm.setLooping(true);
                }
            }catch(Exception e){

            }
        }

    }

}
