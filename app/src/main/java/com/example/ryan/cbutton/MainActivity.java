package com.example.ryan.cbutton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    final String[] cs =getResources().getStringArray(R.array.comps);
    //final String[] speaks=getResources().getStringArray(R.array.speaks);

    String buttonColor="red";
    String voice="noVoice";
    File file = new File("settings.txt");
    //final MediaPlayer click=MediaPlayer.create(this, R.raw.Pling_KevanGC_1485374730);

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




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
                try {
                    FileWriter fw=new FileWriter(file);
                    fw.append(buttonColor+"\n"+voice);
                } catch (IOException e) {
                }

                TextView text=(TextView)findViewById(R.id.textView);
                int a=(int)(Math.random()*cs.length);




                text.setText(cs[a]);




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
    }
    /*public void options(View view){

    }*/
}
