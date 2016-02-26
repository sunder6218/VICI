package vici.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.ActivityNotFoundException;
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

public class MusicActivity extends Activity {

	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	private MediaPlayer mMediaPlayer;
	private String[] mMusicList;
	private String[] pathlist;
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

  mMusicList = getMusic();
  pathlist=getpath();
  ImageView talkbutton= (ImageView)findViewById(R.id.talk);


	ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_list_item_1, mMusicList);
	mListView.setAdapter(mAdapter);

  talkbutton.setOnClickListener(new OnClickListener() {
	  public void onClick(View v) {
		  mMediaPlayer.stop();

		  promptSpeechInput();

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

					if (resultText.equals("play") || resultText.equals("start")) {
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

					if (resultText.equals("stop")) {
						mMediaPlayer.stop();
					}

					if (resultText.equals("previous")) {
						if (index == 0) {
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

						} else {
							try {
								playSong(pathlist[index = index - 1]);
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

					if (resultText.equals("next")) {

						try {
							playSong(pathlist[index = index + 1]);
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
			}
			break;

			default:
				break;
		}

	}
}

