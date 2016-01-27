package vici.ui;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ui.R;


public class ContactActivity extends Activity implements OnClickListener {

private Button btn = null;
private TextView txt = null;
private TextView txt1 = null;
//private String[] namee;
//private String[] idd;

@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.contacts);

    btn = (Button) findViewById(R.id.button1);
    txt = (TextView) findViewById(R.id.textView1);
    txt1 = (TextView) findViewById(R.id.textView3);

}

  	 
@Override
public void onClick(View arg0) {

    switch (arg0.getId())
    {
        case R.id.button1:
            try {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 1);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Error in intent : ", e.toString());
            }

            break;

        case R.id.call:
            if(!txt1.getText().equals("")|| txt1.getText()!=null) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + txt1.getText().toString()));
                startActivity(dialIntent);
                //Toast.makeText(TestcallActivity.this, asd, Toast.LENGTH_SHORT).show();
            }

            break;


    }

}



		  

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

@Override
public void onActivityResult(int reqCode, int resultCode, Intent data) {
    super.onActivityResult(reqCode, resultCode, data);

    try {
    	//String gett= GetTextFromVoice.qwe;
        if (resultCode == Activity.RESULT_OK) {
            Uri contactData = data.getData();
            Cursor cur = managedQuery(contactData, null, null, null, null);
            ContentResolver contect_resolver = getContentResolver();

            if (cur.moveToFirst()) {
                String id = cur.getString(cur.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                String name = "";
                String no = "";

                Cursor phoneCur = contect_resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { id }, null);

                if (phoneCur.moveToFirst()) {
                    name = phoneCur.getString(phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    no = phoneCur.getString(phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }

                Log.v("Phone no & name :***: ", name + " : " + no);
                txt.append("Name: "+name+"\n");
                txt1.append(no);

                id = null;
                name = null;
                no = null;
                phoneCur.close();
            }
            
        }
    } catch (IllegalArgumentException e) {
        e.printStackTrace();
        Log.v("IllegalArgumentException :: ", e.toString());
    } catch (Exception e) {
        e.printStackTrace();
        Log.e("Error :: ", e.toString());
    }


}
}
