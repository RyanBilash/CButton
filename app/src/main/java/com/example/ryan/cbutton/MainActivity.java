package com.example.ryan.cbutton;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String buttonColor="red";
    String voice="noVoice";
    File file = new File("settings.txt");

    public void changeButtonColor(String n){//have the thing be a spinner with the different colors listed
        buttonColor=n;
    }
    public void Voice(boolean n){
        if(n){
            voice="voice";
        }else{
            voice="noVoice";
        }
    }
    public boolean voiceEnabled(){
        if(voice.equals("voice")){
            return true;
        }else{
            return false;
        }
    }
    public String getBC(){
        return buttonColor;
    }

    public MediaPlayer bgm;

    public void bgmE(){
        bgm = MediaPlayer.create(this, R.raw.bgm);
        bgm.setVolume(0.2f,0.2f);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bgmE();



        bgm.start();
        bgm.setLooping(true);
        final MediaPlayer click = MediaPlayer.create(this, R.raw.click);

        final ArrayAdapter<String> compList = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.comps));


        boolean fileFound=true;

        try {
            BufferedReader br=new BufferedReader(new FileReader(file));
            buttonColor=br.readLine();
            voice=br.readLine();
        } catch (FileNotFoundException e) {
            fileFound=false;
        } catch (IOException e) {
            fileFound=false;
        }
        if(!fileFound){
            try {
                FileWriter fw=new FileWriter(file);
                fw.append("red\nnoVoice");
            } catch (IOException e) {
            }
        }else{

        }

        Button button =(Button)findViewById(R.id.button);
        //button.setImageResource()
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.start();
                TextView text=(TextView)findViewById(R.id.textView);
                int a=(int)(Math.random()*compList.getCount());
                                text.setText(compList.getItem(a).replace('_',' '));




                /*
                *
                *
               if(voiceEnabled()){
                    InputStream ins=getResources().openRawResource(getResources().getIdentifier(speaks[a],"raw",getPackageName()));

                    int id=getResources().getIdentifier(speaks[a],"raw",getPackageName());
                    MediaPlayer m=MediaPlayer.create(AudioPlayer);
                    AudioStream as= getResources().openRawResource(getResources().getIdentifier(speaks[a],"raw",getPackageName()));
            }
                *
                *
                *
                * */


            }
        });
        Button settings=(Button)findViewById(R.id.Settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Settings.class));
            }
        });

    }

    protected void onPause(){
        super.onPause();
        if(bgm.isPlaying()){
            try{
                bgm.pause();
            }catch(Exception e){

            }
        }
    }

    protected void onResume(){
        super.onResume();
        if(!bgm.isPlaying()){
            try{
                bgm.start();
                bgm.setLooping(true);
            }catch(Exception e){

            }
        }

    }
}
