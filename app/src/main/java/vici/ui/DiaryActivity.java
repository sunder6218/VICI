package vici.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.skyfishjy.library.RippleBackground;
import com.ui.R;

import vici.interfaces.GetTextFromVoiceCallback;


public class DiaryActivity extends Activity {

	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	EditText ed;
	private String LOG_TAG= DiaryActivity.class.getSimpleName();
	private GetTextFromVoiceCallback getTextFromVoiceCallback;
	//String b;
	
	 public void onCreate(Bundle savedInstanceState) 
	    { 
	        super.onCreate(savedInstanceState);
	        // Set View to register.xml
	        setContentView(R.layout.diary1);
	       
            ////quit button
			ed= (EditText)findViewById(R.id.editText1);
	         //ed.setText(Vttlevel2.returnn);
	        Button quitbutton= (Button)findViewById(R.id.quitbutton);
			quitbutton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
	            finish();	
	            }
	          });






	        ImageView startbutton= (ImageView)findViewById(R.id.talk);
	        startbutton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					promptSpeechInput();
				}
			});
	        

	        Button savebutton= (Button)findViewById(R.id.save);
	        savebutton.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {

	            	
	            	       // catches IOException below
	            	       final Editable TESTSTRING = ed.getText();
					       saveFileToDisk(TESTSTRING);


	            }
	          });
	        
	        
	        
	    }

	private void promptSpeechInput() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
				getString(R.string.speech_prompt));
		try {
			startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
		} catch (ActivityNotFoundException a) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.speech_not_supported),
					Toast.LENGTH_SHORT).show();
		}
	}

	public void saveFileToDisk(Editable TESTSTRING)
	{
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
			Toast.makeText(DiaryActivity.this, "File saved", Toast.LENGTH_SHORT).show();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	 @Override
	    protected void onPause() {
	    	// TODO Auto-generated method stub
		 final Editable TESTSTRING = ed.getText();
		 saveFileToDisk(TESTSTRING);
	    	super.onPause();
	    }

	    @Override
	    protected void onDestroy() {
	    	// TODO Auto-generated method stub
			final Editable TESTSTRING = ed.getText();
			saveFileToDisk(TESTSTRING);
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
			final Editable TESTSTRING = ed.getText();
			saveFileToDisk(TESTSTRING);
	    }

	/**
	 * Receiving speech input
	 * */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
			case VOICE_RECOGNITION_REQUEST_CODE: {

				if (resultCode == RESULT_OK && null != data) {

					ArrayList<String> result = data
							.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
					///start receiver
					String resultText = result.get(0);
					ed.append(" "+resultText);
				}
			}
			break;

			default:
				break;
		}


	}
}