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

import java.io.FileInputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    public void getFile(){
        try{
            //Get the settings from settings.txt
            FileInputStream in = openFileInput("settings.txt");
            InputStreamReader ir = new InputStreamReader(in);//Get the file

            String s = "";

            int a = ir.read();
            while(a!=-1){
                s+=(char)a;
                a=ir.read();
            }//Convert the bytes of the file to a readable string
            ir.close();
            String q = "\n";
            String[] sa = s.split(q);//Split the string at new lines
            buttonColor = sa[0]; //set color
            voice = sa[1]; //enables or disables sound
        }catch(Exception e){}
    }
    static String buttonColor="red";
    static String voice="Sound";


    public static int time = 0;//Time for the background music to start at
    public static void changeTime(int i){time = i;}//Changes where the background music should start/stop
    public static int getTime(){
        return time;
    }//Gets the time for the background music

    public static void Sound(boolean n){//changes the "sound" setting
        if(n){
            voice="Sound";
        }else{
            voice="noSound";
        }
    }
    public static boolean soundEnabled(){//returns the state of sound, on or off
        if(voice.equals("Sound")){
            return true;
        }else{
            return false;
        }
    }

    public MediaPlayer bgm;//Background music

    public void bgmE(){//Create the background music
        bgm = MediaPlayer.create(this, R.raw.bgm);
        bgm.setVolume(0.2f,0.2f);
    }

    public ImageButton button;

    public void createButton(){//Change the color of the button
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
        getFile();//Gets the desired settings for the app

        bgmE();//Sets background music to the file, this is the same for all classes
        createButton();//Sets the button image to the desired color

        bgm.seekTo(getTime());//Goes to current time, same for all classes
        bgm.start();
        bgm.setLooping(true);//Loops the background music, same for all classes

        final MediaPlayer click = MediaPlayer.create(this, R.raw.click);
        //Click sound effect is created

        final ArrayAdapter<String> compList = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.comps));
        //Get all compliments from arrays.xml

        if(!soundEnabled()){
            bgm.pause();
            //If settings for sound is off, doesn't play music, this is the same for all classes
        }else{
            bgm.start();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soundEnabled()){click.start();}
                TextView text=(TextView)findViewById(R.id.textView);//Get the textView
                int a=(int)(Math.random()*compList.getCount());//Get a random number for the amount of compliments in the array
                text.setText(compList.getItem(a).replace('_',' '));//Set the textView to be the selected compliment


            }
        });
        ImageButton settings=(ImageButton)findViewById(R.id.Settings);//When this button is clicked, go to the settings page
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soundEnabled()){
                    click.start();
                }
                startActivity(new Intent(MainActivity.this, Settings.class));
            }
        });

        Button help = (Button)findViewById(R.id.help);//When this button is clicked, go to the help page
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soundEnabled()){
                    click.start();
                }
                startActivity(new Intent(MainActivity.this, Help.class));
            }
        });
        Button about = (Button)findViewById(R.id.about);//When this button is clicked, go to the about page
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soundEnabled()){
                    click.start();
                }
                startActivity(new Intent(MainActivity.this, About.class));
            }
        });

    }

    protected void onPause(){
        super.onPause();
        //When the view is paused, stop the music and change the set time of the music, this is done on all classes
        if(bgm.isPlaying()){
            try{
                bgm.pause();
            }catch(Exception e){}
        }
        changeTime(bgm.getCurrentPosition());
    }

    protected void onResume(){
        super.onResume();
        //When the view resumes, resume the music and change the button color, this is also done in all classes
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
