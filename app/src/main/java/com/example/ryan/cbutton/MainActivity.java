package com.example.ryan.cbutton;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    public void getFile(){
        try{
            FileInputStream in = openFileInput("settings.txt");
            InputStreamReader ir = new InputStreamReader(in);

            String s = "";

            int a = ir.read();
            while(a!=-1){
                s+=(char)a;
                a=ir.read();
            }
            ir.close();
            String q = "\n";
            String[]sa=s.split(q);
            buttonColor = sa[0];
            voice = sa[1];
        }catch(Exception e){}
    }
    static String buttonColor="red";
    static String voice="Sound";


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
    public static void Sound(boolean n){
        if(n){
            voice="Sound";
        }else{
            voice="noSound";
        }
    }
    public static boolean soundEnabled(){
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

    public ImageButton button;

    public void createButton(){
        button = (ImageButton)findViewById(R.id.button);
        if(buttonColor.equals("red")){
            button.setImageResource(R.drawable.red);
        }else if(buttonColor.equals("purple")){
            button.setImageResource(R.drawable.purple);
        }else if(buttonColor.equals("blue")){
            button.setImageResource(R.drawable.blue);
        }else if(buttonColor.equals("green")){
            button.setImageResource(R.drawable.green);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         getFile();

        bgmE();
        createButton();

        bgm.seekTo(getTime());
        bgm.start();
        bgm.setLooping(true);

        final MediaPlayer click = MediaPlayer.create(this, R.raw.click);

        final ArrayAdapter<String> compList = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.comps));

        if(!soundEnabled()){
            bgm.pause();
        }else{
            bgm.start();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soundEnabled()){click.start();}
                TextView text=(TextView)findViewById(R.id.textView);
                int a=(int)(Math.random()*compList.getCount());
                                text.setText(compList.getItem(a).replace('_',' '));


            }
        });
        ImageButton settings=(ImageButton)findViewById(R.id.Settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soundEnabled()){
                    click.start();
                }
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
                if(soundEnabled()){
                    bgm.seekTo(getTime());
                    bgm.start();
                    bgm.setLooping(true);
                }
            }catch(Exception e){

            }
        }
        if(buttonColor.equals("red")){
            button.setImageResource(R.drawable.red);
        }else if(buttonColor.equals("purple")){
            button.setImageResource(R.drawable.purple);
        }else if(buttonColor.equals("blue")){
            button.setImageResource(R.drawable.blue);
        }else if(buttonColor.equals("green")){
            button.setImageResource(R.drawable.green);
        }

    }
}
