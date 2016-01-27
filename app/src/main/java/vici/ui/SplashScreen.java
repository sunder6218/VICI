package vici.ui;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ui.R;

public class SplashScreen extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ////call intro page
        setContentView(R.layout.intro);
      /// thread for intro page
        Thread t1=new Thread(){
	          	 public void run(){
	          		 try{
	          			 int timer=0;
	          			 while(timer<2000)
	          			 {sleep(100);
	          			 timer=timer+100;}
	          			  startActivity(new Intent(getApplicationContext(),HomeScreen.class));
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
    ///inbuilt override methods for exceptions
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    }

    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    }

    @Override
    protected void onRestart() {
    	// TODO Auto-generated method stub
    	super.onRestart();
    }

    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    }

    @Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    }

    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    }
	}