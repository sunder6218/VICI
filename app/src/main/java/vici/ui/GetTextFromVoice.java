/* 

 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vici.ui;

//import com.example.android.apis.R;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;




/**
 * Sample code that invokes the speech recognition intent API.
 */
public class GetTextFromVoice extends Activity {
    
	String aasd;
	static String qwe;
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    AudioManager audMangr;
  
    
 

    /**
     * Called with the activity is first created.
     * @return 
     */
  
    public void onCreate(Bundle savedInstanceState) 
  {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.);
        // Check to see if a recognition activity is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) {
           // speakButton.setOnClickListener(this);
        	 startVoiceRecognitionActivity();
        }
        else
        {Toast.makeText(GetTextFromVoice.this, "audio manager absent", Toast.LENGTH_SHORT).show();}
    }
   

    /**
     * Handle the click on the start recognition button.
     */
   

    /**
     * Fire an intent to start the speech recognition activity.
     */
   public void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    
   }
    /**
     * Handle the results from the recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            ListView lv = new ListView(this);
            lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                    matches));
           setContentView(lv);
        	//String matches = data.getStringExtra(RecognizerIntent.EXTRA_RESULTS);
            aasd=matches.get(0);
        	Toast.makeText(GetTextFromVoice.this, matches.get(0), Toast.LENGTH_SHORT).show();
            if(matches.get(0).equalsIgnoreCase("music")|| matches.get(0).equalsIgnoreCase("music player"))
            {
            	//Toast.makeText(Vtt.this, matches.get(0), Toast.LENGTH_SHORT).show();
            	//Intent musicp = new Intent("android.intent.action.MUSIC_PLAYER");
            	finish();
            	Intent musicp = new Intent(getApplicationContext(),Music.class);
            	startActivity(musicp);
            	
            }
         
            else if(matches.get(0).startsWith("call"))
            {//int index=aasd.indexOf("call");
            	if(aasd.length()>4)
            	{qwe= aasd.substring(5);
            	 Toast.makeText(GetTextFromVoice.this, qwe, Toast.LENGTH_LONG).show();
            	}
            	else
            	{ Toast.makeText(GetTextFromVoice.this,"say the name", Toast.LENGTH_LONG).show();}
            }
            else if(matches.get(0).equalsIgnoreCase("contact")|| matches.get(0).equalsIgnoreCase("contacts"))
            {
            	finish();
            	Intent contactss = new Intent(getApplicationContext(),Contact.class);
     	        startActivity(contactss);
            }
            
            else if(matches.get(0).equalsIgnoreCase("diary")||matches.get(0).equalsIgnoreCase("diary"))
            {
            	finish();
            	Intent contactss = new Intent(getApplicationContext(),diary.class);
     	        startActivity(contactss);
            }
         
            else if(matches.get(0).equalsIgnoreCase("quit")|| matches.get(0).equalsIgnoreCase("exit"))
            {
            	finish();
            	finish();
            }
            else if(matches.get(0).equalsIgnoreCase("hi")|| matches.get(0).equalsIgnoreCase("hello"))
            {  
            	//finish();
            	  Thread t1=new Thread(){
     	          	 public void run(){
     	          		 try{
     	          			startActivity(new Intent("Speaker.class"));
     	          			 int timer=0;
     	          			 while(timer<3000)
     	          			 {sleep(100);
     	          			 timer=timer+100;}
     	          			
     	          		 }
     	          		 
     	          		 catch (InterruptedException e) {
     	          			// TODO Auto-generated catch block
     	          			e.printStackTrace();
     	          		}
     	          		 finally
     	          		 {finish();}
     	          	 }
     	            };
     	            
     	            ///start thread
     	              t1.start();
             
	          			
	          			            	
            }
            
            else if(matches.get(0).equalsIgnoreCase("silent")|| matches.get(0).equalsIgnoreCase("silence"))
            {
            	finish();
                       
            audMangr= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);

            //For Normal mode
          //  audMangr.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

            //For Silent mode
          //  audMangr.setRingerMode(AudioManager.RINGER_MODE_SILENT);

            //For Vibrate mode
            audMangr.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            }
            else if(matches.get(0).equalsIgnoreCase("normal")|| matches.get(0).equalsIgnoreCase("normal mode"))
            {
            	finish();
            	 audMangr= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);

                 //For Normal mode
                 audMangr.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }
        
            else
            {
            	Toast.makeText(GetTextFromVoice.this, "no such command", Toast.LENGTH_LONG).show();
            	finish();
            	
            }
         
        

        super.onActivityResult(requestCode, resultCode, data);
      //  finish();
        System.out.println(""+data);
    }
    
   
}
   
}
