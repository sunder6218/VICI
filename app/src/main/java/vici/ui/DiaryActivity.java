package vici.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.app.Activity;
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


public class DiaryActivity extends Activity implements GetTextFromVoiceCallback,RecognitionListener{

	EditText ed;
	private SpeechRecognizer speech = null;
	private Intent recognizerIntent;
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
			speech = SpeechRecognizer.createSpeechRecognizer(this);
			speech.setRecognitionListener(this);
			recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
					"en");
			recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
					this.getPackageName());
			recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
					RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
			recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);





	        ImageView startbutton= (ImageView)findViewById(R.id.talk);
	        startbutton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					speech.startListening(recognizerIntent);
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

	@Override
	public void onTextReceived(String resultText) {
		ed.append(" "+resultText);
	}


	@Override
	public void onBeginningOfSpeech() {
		Log.i(LOG_TAG, "onBeginningOfSpeech");
		//progressBar.setIndeterminate(false);
		//progressBar.setMax(10);
	}

	@Override
	public void onBufferReceived(byte[] buffer) {
		Log.i(LOG_TAG, "onBufferReceived: " + buffer);
	}

	@Override
	public void onEndOfSpeech() {
		Log.i(LOG_TAG, "onEndOfSpeech");

		// progressBar.setIndeterminate(true);
		// toggleButton.setChecked(false);
	}

	@Override
	public void onError(int errorCode) {
		String errorMessage = getErrorText(errorCode);
		Log.d(LOG_TAG, "FAILED " + errorMessage);

		speech.stopListening();
		// returnedText.setText(errorMessage);
		// toggleButton.setChecked(false);
	}

	@Override
	public void onEvent(int arg0, Bundle arg1) {
		Log.i(LOG_TAG, "onEvent");
	}

	@Override
	public void onPartialResults(Bundle arg0) {
		Log.i(LOG_TAG, "onPartialResults");

		speech.stopListening();
	}

	@Override
	public void onReadyForSpeech(Bundle arg0) {
		Log.i(LOG_TAG, "onReadyForSpeech");
	}

	@Override
	public void onResults(Bundle results) {
		Log.i(LOG_TAG, "onResults");
		ArrayList<String> matches = results
				.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
		String resultText = matches.get(0);
//

		ed.append(" "+resultText);
	}

	@Override
	public void onRmsChanged(float rmsdB) {
		Log.v(LOG_TAG, "onRmsChanged: " + rmsdB);
		//mVisualizerView.updateVisualizer(ByteBuffer.allocate(4).putFloat(rmsdB).array());
		//progressBar.setProgress((int) rmsdB);
	}

	public static String getErrorText(int errorCode) {
		String message;
		switch (errorCode) {
			case SpeechRecognizer.ERROR_AUDIO:
				message = "Audio recording error";
				break;
			case SpeechRecognizer.ERROR_CLIENT:
				message = "Client side error";
				break;
			case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
				message = "Insufficient permissions";
				break;
			case SpeechRecognizer.ERROR_NETWORK:
				message = "Network error";
				break;
			case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
				message = "Network timeout";
				break;
			case SpeechRecognizer.ERROR_NO_MATCH:
				message = "No match";
				break;
			case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
				message = "RecognitionService busy";
				break;
			case SpeechRecognizer.ERROR_SERVER:
				message = "error from server";
				break;
			case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
				message = "No speech input";
				break;
			default:
				message = "Didn't understand, please try again.";
				break;
		}

		return message;
	}
}