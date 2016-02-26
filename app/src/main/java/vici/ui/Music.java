package vici.ui;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.ui.R;

public class Music extends Activity {

	private MediaPlayer mMediaPlayer;
	private String[] mMusicList;
	private String[] pathlist;
	static int index=0;
/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
	
  super.onCreate(savedInstanceState);
  setContentView(R.layout.music);
 int confirm=1;
 int temp=0;
  mMediaPlayer = new MediaPlayer();

  ListView mListView = (ListView) findViewById(R.id.PhoneMusicList);
 

  mMusicList = getMusic();
  pathlist=getpath();
 // Toast.makeText(Music.this, pathlist[2]+" "+pathlist[3], Toast.LENGTH_SHORT).show();
  
 
  ImageView talkbutton= (ImageView)findViewById(R.id.talk);
  
  talkbutton.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
    	  mMediaPlayer.stop();
    	  startActivity(new Intent(getApplicationContext(),Vttmusic.class));

      }
    });
//  Toast.makeText(Music.this, "verifier "+Vttmusic.verifier, Toast.LENGTH_LONG).show();

if(Vttmusic.verifier==1)
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

if(Vttmusic.verifier==0)
{
	  mMediaPlayer.stop();
}

if(Vttmusic.verifier==-1)
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

if(Vttmusic.verifier==2)
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
/*  MediaPlayer mp = new MediaPlayer();
  mp.setDataSource();
  mp.prepare();
  mp.start();

*/
 

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
	  
	  Toast.makeText(Music.this, "no songs", Toast.LENGTH_SHORT).show();
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
		  
		  Toast.makeText(Music.this, "no songs", Toast.LENGTH_SHORT).show();
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
//  String extStorageDirectory = Environment.getExternalStorageDirectory()
//  .toString();

 // path = extStorageDirectory + File.separator + path;

  mMediaPlayer.reset();
  mMediaPlayer.setDataSource(path);
  mMediaPlayer.prepare();
  mMediaPlayer.start();
}



}

