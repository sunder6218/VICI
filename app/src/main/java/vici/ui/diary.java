package vici.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ui.R;

public class diary extends Activity {
String geta;
	//String b;
	
	 public void onCreate(Bundle savedInstanceState) 
	    { 
	        super.onCreate(savedInstanceState);
	        // Set View to register.xml
	        setContentView(R.layout.diary1);
	       
            ////quit button
	         final EditText ed= (EditText)findViewById(R.id.editText1);
	         ed.setText(Vttlevel2.returnn);
	        Button quitbutton= (Button)findViewById(R.id.quitbutton);
	        quitbutton.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	            finish();	
	            }
	          });
	        
	        ImageView startbutton= (ImageView)findViewById(R.id.talk);
	        startbutton.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	            	geta=ed.getText().toString();
	            	// geta=geta.concat(Vttlevel2.returnn);
	            	finish();
	            	
	            	startActivity(new Intent(getApplicationContext(),Vttlevel2.class));
	            	if(geta.equalsIgnoreCase(null))
	            	{ed.append(" "+Vttlevel2.returnn); }
	            	else
	            		{
	            	ed.append(" "+Vttlevel2.returnn);
	            		}
	            }
	          });
	        
	        final EditText txt = (EditText) findViewById(R.id.editText1);
	        Button savebutton= (Button)findViewById(R.id.save);
	        savebutton.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {

	            	
	            	       // catches IOException below
	            	       final Editable TESTSTRING = txt.getText();
          
	            	       FileOutputStream fOut;
	            	       File textFile = new File( Environment.getExternalStorageDirectory()+"/" + "/diary.txt");
						try {
							fOut = new FileOutputStream(textFile);
							 OutputStreamWriter osw = new OutputStreamWriter(fOut); 

		            	       // Write the string to the file
		            	       osw.write(TESTSTRING.toString());

		            	       /* ensure that everything is
		            	        * really written out and close */
		            	       osw.flush();
		            	       osw.close();
		                   	Toast.makeText(diary.this, "File saved", Toast.LENGTH_SHORT).show();

						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            	      

	            }
	          });
	        
	        
	        
	    }
/*	 public void aaa(com.wiki.Vtt a)
     {
     	
     	//if(a.aasd.equalsIgnoreCase("contact")|| a.aasd.equalsIgnoreCase("contacts"))
        {
     	EditText bbb=	(EditText) findViewById(R.id.editText1);
     	bbb.setText(a.aasd);
        }
     }*/
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