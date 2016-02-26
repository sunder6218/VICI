package vici.speechResolver;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

public class Speaker extends Activity implements
TextToSpeech.OnInitListener {
/** Called when the activity is first created. */

private TextToSpeech tts;


@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);

tts = new TextToSpeech(this, this);


}
private void speakOut(String text) {

tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
}
@Override
public void onInit(int status) {
	// TODO Auto-generated method stub
	 speakOut("hi, Iam VICKI");
	
}
}