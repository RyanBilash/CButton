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

public class Help extends AppCompatActivity {
    public MediaPlayer bgm;

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

        bgm.seekTo(getTime());
        bgm.start();
        bgm.setLooping(true);

        if(!soundEnabled()){
            bgm.pause();
        }else{
            bgm.start();
        }

        Button button = (Button)findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soundEnabled()){
                    click.start();
                }
                String s = getET();
                String[]to = {"complimentbutton@gmail.com"};
                Intent ei = new Intent(Intent.ACTION_SEND);
                ei.setData(Uri.parse("mailto:"));
                ei.setType("text/plain");
                ei.putExtra(Intent.EXTRA_EMAIL,to);
                ei.putExtra(Intent.EXTRA_SUBJECT, "Compliment Suggestion");
                ei.putExtra(Intent.EXTRA_TEXT, s);

                try{
                   startActivity(Intent.createChooser(ei,"Sending suggestion..."));
                    finish();
                    Log.i("Suggestion sent","");
                }catch(Exception E){
                    Log.i("Suggestion send failed","");
                }
            }
        });

        //Add buttons here
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
