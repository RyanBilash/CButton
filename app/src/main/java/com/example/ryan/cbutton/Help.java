package com.example.ryan.cbutton;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.ryan.cbutton.MainActivity.changeTime;
import static com.example.ryan.cbutton.MainActivity.getTime;
import static com.example.ryan.cbutton.MainActivity.soundEnabled;
import static com.example.ryan.cbutton.R.id.tmr;

public class Help extends AppCompatActivity {
    public MediaPlayer bgm;//Background Music

    public void bgmE(){
        bgm = MediaPlayer.create(this, R.raw.bgm);
        bgm.setVolume(0.2f,0.2f);
    }
    EditText et;
    public String getET(){
       return  et.getText().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        final MediaPlayer click = MediaPlayer.create(this, R.raw.click);

        et=(EditText)findViewById(R.id.editText);

        bgmE();

        bgm.seekTo(getTime());//If paused, gets the proper time to start at
        bgm.start();
        bgm.setLooping(true);//Loops the music

        if(!soundEnabled()){
            bgm.pause();

        }else{
            bgm.start();
        }

        Button button = (Button)findViewById(R.id.button2);//Send email button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soundEnabled()){
                    click.start();
                }
                //Mail suggestion to complimentbutton@gmail.com
                String s = getET();
                String[]to = {"complimentbutton@gmail.com"};


                Intent ei = new Intent(android.content.Intent.ACTION_SEND);

                ei.setType("message/rfc822");

                ei.putExtra(android.content.Intent.EXTRA_EMAIL,to);
                ei.putExtra(android.content.Intent.EXTRA_SUBJECT, "Compliment Suggestion");
                ei.putExtra(android.content.Intent.EXTRA_TEXT, s);

                try{
                   startActivity(ei);
                    finish();
                }catch(Exception E){
                    Log.i("Suggestion send failed","");//If mail send fails, display this
                }
            }
        });

        Button nshp = (Button)findViewById(R.id.nsph);
        nshp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soundEnabled()){
                    click.start();
                }
                //Open up browser with page
                Uri uriUrl = Uri.parse("https://suicidepreventionlifeline.org/");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });
        Button afsp = (Button)findViewById(R.id.afsp);
        afsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soundEnabled()){
                    click.start();
                }
                //Open up browser with page
                Uri uriUrl = Uri.parse("https://www.crisistextline.org/");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });
        Button save = (Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soundEnabled()){
                    click.start();
                }
                //Open up browser with page
                Uri uriUrl = Uri.parse("https://save.org/");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });
    }

    protected void onPause(){
        super.onPause();

        if(bgm.isPlaying()){
            try{
                bgm.pause();
            }catch(Exception e){}
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
