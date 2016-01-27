package vici.ui;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.ui.R;

import vici.interfaces.GetTextFromVoiceCallback;

public class MusicActivity extends Activity implements GetTextFromVoiceCallback,RecognitionListener{

	private MediaPlayer mMediaPlayer;
	private String[] mMusicList;
	private String[] pathlist;
	private SpeechRecognizer speech = null;
	private Intent recognizerIntent;
	private String LOG_TAG= MusicActivity.class.getSimpleName();
	static int index=0;
	int confirm=1;
	int temp=0;
	ListView mListView;
	private GetTextFromVoiceCallback getTextFromVoiceCallback;

/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
	
  super.onCreate(savedInstanceState);
  setContentView(R.layout.music);

  mMediaPlayer = new MediaPlayer();
	mListView = (ListView) findViewById(R.id.PhoneMusicList);
 getTextFromVoiceCallback=this;

  mMusicList = getMusic();
  pathlist=getpath();
  ImageView talkbutton= (ImageView)findViewById(R.id.talk);

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


  talkbutton.setOnClickListener(new OnClickListener() {
	  public void onClick(View v) {
		  mMediaPlayer.stop();

		  speech.startListening(recognizerIntent);

	  }
  });

 

}
@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
    	 mMediaPlayer.stop();
    }
    return super.onKeyDown(keyCode, event);
}

@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
}

@Override
protected void onPostResume() {
	// TODO Auto-generated method stub
	super.onPostResume();
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

private String[] getMusic() {
  final Cursor mCursor = managedQuery(
  MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
  new String[] { MediaStore.Audio.Media.DISPLAY_NAME }, null, null,
  "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");

 
  
 // MediaStore.Audio.Media.DATA
  int count = mCursor.getCount();
 

  String[] songs = new String[count];
  int i = 0;
  if(mCursor.getCount()<1)
  {
	  
	  Toast.makeText(MusicActivity.this, "no songs", Toast.LENGTH_SHORT).show();
  }
  
  if (mCursor.moveToFirst()) {
    do {
      songs[i] = mCursor.getString(0);
      i++;
    } while (mCursor.moveToNext());
  }

  mCursor.close();

  return songs;
}



private String[] getpath() {
	  final Cursor mCursor = managedQuery(
	  MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
	  new String[] { MediaStore.Audio.Media.DATA }, null, null,
	  "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");

	  
	  
	 // MediaStore.Audio.Media.DATA
	  int count = mCursor.getCount();
	 

	  String[] songs = new String[count];
	  int i = 0;
	  if(mCursor.getCount()<1)
	  {
		  
		  Toast.makeText(MusicActivity.this, "no songs", Toast.LENGTH_SHORT).show();
	  }
	  
	  if (mCursor.moveToFirst()) {
	    do {
	      songs[i] = mCursor.getString(0);
	      i++;
	    } while (mCursor.moveToNext());
	  }

	  mCursor.close();

	  return songs;
	}

private void playSong(String path) throws IllegalArgumentException,
IllegalStateException, IOException {


  mMediaPlayer.reset();
  mMediaPlayer.setDataSource(path);
  mMediaPlayer.prepare();
  mMediaPlayer.start();
}



	@Override
	public void onTextReceived(String resultText) {

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

		if(resultText.equals("play")||resultText.equals("start"))
		{
			try {
				playSong(pathlist[temp]);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if(resultText.equals("stop"))
		{
			mMediaPlayer.stop();
		}

		if(resultText.equals("previous"))
		{
			if(index==0)
			{
				try {
					playSong(pathlist[index]);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else{
				try {
					playSong(pathlist[index=index-1]);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		if(resultText.equals("next"))
		{

			try {
				playSong(pathlist[index=index+1]);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}



		ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mMusicList);
		mListView.setAdapter(mAdapter);
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

