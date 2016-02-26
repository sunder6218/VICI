package vici.ui;

import android.app.Activity;
import android.content.Intent;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.skyfishjy.library.RippleBackground;
import com.ui.R;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import vici.views.VisualizerView;

/**
 * Created by sunders on 27/01/16.
 */
public class VoiceRecognizer extends Activity implements
        RecognitionListener {


    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private ImageView btn;
    private RippleBackground rippleBackground;
    private String LOG_TAG = VoiceRecognizer.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        btn= (ImageView)findViewById(R.id.talk);


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



        rippleBackground=new RippleBackground(this);
        rippleBackground.setVisibility(View.INVISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rippleBackground.setVisibility(View.VISIBLE);
                rippleBackground.startRippleAnimation();
                speech.startListening(recognizerIntent);
            }
        });



//        toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView,
//                                         boolean isChecked) {
//                if (isChecked) {
//                    progressBar.setVisibility(View.VISIBLE);
//                    progressBar.setIndeterminate(true);
//                    speech.startListening(recognizerIntent);
//                } else {
//                    progressBar.setIndeterminate(false);
//                    progressBar.setVisibility(View.INVISIBLE);
//                    speech.stopListening();
//                }
//            }
//        });

    }

//    private void setupVisualizerFxAndUI() {
//
//
//        // Create the Visualizer object and attach it to our media player.
//        mVisualizer = new Visualizer();
//        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
//        mVisualizer.setDataCaptureListener(
//                new Visualizer.OnDataCaptureListener() {
//                    public void onWaveFormDataCapture(Visualizer visualizer,
//                                                      byte[] bytes, int samplingRate) {
//                        mVisualizerView.updateVisualizer(bytes);
//                    }
//
//                    public void onFftDataCapture(Visualizer visualizer,
//                                                 byte[] bytes, int samplingRate) {
//                    }
//                }, Visualizer.getMaxCaptureRate() / 2, true, false);
//    }

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
        String text = "";
        for (String result : matches)
            text += result + "\n";
        Log.v(LOG_TAG, text);
        rippleBackground.setVisibility(View.INVISIBLE);
        rippleBackground.stopRippleAnimation();
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

}