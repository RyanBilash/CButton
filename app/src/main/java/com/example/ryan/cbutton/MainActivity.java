package com.example.ryan.cbutton;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    public static File file = new File("settings.txt");
    public static String getFile(int a){
        try{
            Scanner sc = new Scanner(new FileReader(file));
            String s;
            if(a==1){
                s=sc.nextLine();
                sc.close();
                return s;
            }else{
                sc.nextLine();
                s=sc.nextLine();
                return s;
            }
        }catch(Exception e){}
        if(a==1){
            return "red";
        }else{
            return "Sound";
        }
    }


    static String buttonColor=getFile(1);
    static String voice=getFile(2);


    public static int time = 0;
    public static void changeTime(int i){
        time = i;
    }
    public static int getTime(){
        return time;
    }

    public void changeButtonColor(String n){//have the thing be a spinner with the different colors listed
        buttonColor=n;
    }
    public void Sound(boolean n){
        if(n){
            voice="Sound";
        }else{
            voice="noSound";
        }
    }
    public boolean soundEnabled(){
        if(voice.equals("Sound")){
            return true;
        }else{
            return false;
        }
    }
    public static String getBC(){
        return buttonColor;
    }
    public static String getS(){
        return voice;
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

        bgm.seekTo(getTime());
        bgm.start();
        bgm.setLooping(true);



        final MediaPlayer click = MediaPlayer.create(this, R.raw.click);

        final ArrayAdapter<String> compList = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.comps));


        boolean fileFound=true;

        try {
            BufferedReader br=new BufferedReader(new FileReader(file));
            buttonColor=br.readLine();
            voice=br.readLine();
        } catch (Exception e) {
            fileFound=false;
        }
        if(!fileFound){
            try {
                FileWriter fw=new FileWriter(file);
                fw.append("red\nSound");
            } catch (IOException e) {
            }
        }else{

        }

        if(!soundEnabled()){
            bgm.stop();
        }else{
            bgm.start();
        }

        ImageButton button =(ImageButton)findViewById(R.id.button);
        //button.setImageResource()

        if(buttonColor.equals("red")){
            button.setImageResource(R.drawable.red);
        }else if(buttonColor.equals("purple")){
            button.setImageResource(R.drawable.purple);
        }else if(buttonColor.equals("blue")){
            button.setImageResource(R.drawable.blue);
        }else if(buttonColor.equals("green")){
            button.setImageResource(R.drawable.green);
        }

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
                click.start();
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
