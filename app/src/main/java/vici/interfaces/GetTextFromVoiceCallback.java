package vici.interfaces;

import java.io.Serializable;

/**
 * Created by sunders on 27/01/16.
 */
public interface GetTextFromVoiceCallback extends Serializable{

    void onTextReceived(String resultText);

}
