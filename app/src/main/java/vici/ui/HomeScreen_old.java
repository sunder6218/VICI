package vici.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.skyfishjy.library.RippleBackground;
import com.ui.R;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vici.adapters.ResponsesAdapter;
import vici.interfaces.GetTextFromVoiceCallback;


public class HomeScreen_old extends Activity implements GetTextFromVoiceCallback,RecognitionListener {


	SpeechRecognizer speech;
	Intent recognizerIntent;
	private RippleBackground rippleBackground;
	private String LOG_TAG= HomeScreen_old.class.getSimpleName();
	private ImageView btn,instructions;
	private TextToSpeech t1;
	private ArrayList<String> viciesponses;
	private ListView listView;
	private ResponsesAdapter adapter;
	AudioManager audMangr;



	@Override
	 public void onCreate(Bundle savedInstanceState)
	    {
	        super.onCreate(savedInstanceState);
	        // Set View to register.xml
	        setContentView(R.layout.main);

			btn= (ImageView)findViewById(R.id.talk);
			listView=(ListView)findViewById(R.id.list1);
			instructions=(ImageView)findViewById(R.id.instructions);

			viciesponses= new ArrayList<>();
			adapter=new ResponsesAdapter( viciesponses, getApplicationContext() );

			t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
				@Override
				public void onInit(int status) {
					if(status != TextToSpeech.ERROR) {
						t1.setLanguage(Locale.UK);
					}
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



			rippleBackground=(RippleBackground)findViewById(R.id.content);
			rippleBackground.setVisibility(View.INVISIBLE);
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					rippleBackground.setVisibility(View.VISIBLE);
					rippleBackground.startRippleAnimation();
					speech.startListening(recognizerIntent);
				}
			});

			instructions.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(HomeScreen_old.this, "Say the following commands after tapping the speak button\nto activate the features", Toast.LENGTH_LONG).show();
					Toast.makeText(HomeScreen_old.this, "Contacts\n\nMusic\n\nDiary\n\nSilent\n\nNormal\n\n", Toast.LENGTH_LONG).show();
					Toast.makeText(HomeScreen_old.this, "Now press the mic below to get started", Toast.LENGTH_LONG).show();

					open();
				}
			});
	        
	        
	    }




	@Override
	public void onTextReceived(String resultText) {

		Toast.makeText(HomeScreen_old.this, resultText+"", Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (speech != null) {
			speech.destroy();
			rippleBackground.setVisibility(View.INVISIBLE);
			rippleBackground.stopRippleAnimation();
			Log.i(LOG_TAG, "destroy");
		}



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
		rippleBackground.setVisibility(View.INVISIBLE);
		rippleBackground.stopRippleAnimation();
		// progressBar.setIndeterminate(true);
		// toggleButton.setChecked(false);
	}

	@Override
	public void onError(int errorCode) {
		String errorMessage = getErrorText(errorCode);
		Log.d(LOG_TAG, "FAILED " + errorMessage);
		rippleBackground.setVisibility(View.INVISIBLE);
		rippleBackground.stopRippleAnimation();
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
		rippleBackground.setVisibility(View.INVISIBLE);
		rippleBackground.stopRippleAnimation();
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
//		for (String result : matches)
//			text += result + "\n";
//		Log.v(LOG_TAG, text);
		rippleBackground.setVisibility(View.INVISIBLE);
		rippleBackground.stopRippleAnimation();

		if(resultText.equalsIgnoreCase("music")|| resultText.equalsIgnoreCase("music player"))
		{
			//Toast.makeText(Vtt.this, matches.get(0), Toast.LENGTH_SHORT).show();
			//Intent musicp = new Intent("android.intent.action.MUSIC_PLAYER");
			//finish();
			Intent musicp = new Intent(getApplicationContext(),MusicActivity.class);
			startActivity(musicp);

		}

		else if(resultText.startsWith("call"))
		{//int index=aasd.indexOf("call");
			Pattern p = Pattern.compile("(?<=\\bcall\\s)(\\w+)");
			Matcher m = p.matcher(resultText);
			while (m.find())
			{
				System.out.println(m.group(1));

			}
			if(resultText.length()>4)
			{String qwe= resultText.substring(5);
				Toast.makeText(this, qwe, Toast.LENGTH_LONG).show();
			}
			else
			{ Toast.makeText(this,"say the name", Toast.LENGTH_LONG).show();}
		}
		else if(resultText.equalsIgnoreCase("contact")|| resultText.equalsIgnoreCase("contacts"))
		{
			//finish();
			Intent contactss = new Intent(getApplicationContext(),ContactActivity.class);
			startActivity(contactss);
		}

		else if(resultText.equalsIgnoreCase("diary")||resultText.equalsIgnoreCase("diary"))
		{
			//finish();
			Intent contactss = new Intent(getApplicationContext(),DiaryActivity.class);
			startActivity(contactss);
		}

		else if(resultText.equalsIgnoreCase("quit")|| resultText.equalsIgnoreCase("exit"))
		{
			finish();
			finish();
		}
		else if(resultText.equalsIgnoreCase("hi")|| resultText.equalsIgnoreCase("hello"))
		{


			t1.speak("Hi, Iam VICKI, your personal assistant", TextToSpeech.QUEUE_FLUSH, null);
			viciesponses.add("Hi, Iam VICKI, your personal assistant");
			adapter.notifyDataSetChanged();



		}

		else if(resultText.equalsIgnoreCase("silent")|| resultText.equalsIgnoreCase("silence"))
		{
			//finish();

			audMangr= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);

			//For Normal mode
			//  audMangr.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

			//For Silent mode
			//  audMangr.setRingerMode(AudioManager.RINGER_MODE_SILENT);

			//For Vibrate mode
			audMangr.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
		}
		else if(resultText.equalsIgnoreCase("normal")|| resultText.equalsIgnoreCase("normal mode"))
		{
			//finish();
			audMangr= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);

			//For Normal mode
			audMangr.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		}

		else
		{
			viciesponses.add("no such command, please try again");
			adapter.notifyDataSetChanged();
			t1.speak("no such command, please try again", TextToSpeech.QUEUE_FLUSH, null);
			//Toast.makeText(this, "no such command", Toast.LENGTH_LONG).show();
			//finish();

		}
		//speech.stopListening();
		//returnedText.setText(text);
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

	public void open(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setMessage("Project Developed by\n" +
				"Sunder\n" +
				"Umair\n" +
				"varsha\n" +
				"amruta");

//		alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface arg0, int arg1) {
//				Toast.makeText(HomeScreen.this,"You clicked yes button",Toast.LENGTH_LONG).show();
//			}
//		});
//
//		alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				finish();
//			}
//		});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

}
