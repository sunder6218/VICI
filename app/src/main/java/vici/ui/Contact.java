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

public class Contact extends Activity implements OnClickListener {

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
    //ListView conlist = (ListView) findViewById(R.id.lv);
    
    Button cal=(Button)findViewById(R.id.call);
    cal.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
        	Intent callIntent = new Intent(Intent.ACTION_CALL);
           callIntent.setData(Uri.parse("tel:"+txt1.getText().toString()));
           startActivity(callIntent);
        	//Toast.makeText(TestcallActivity.this, asd, Toast.LENGTH_SHORT).show();

    	
        }
      });

    
  //  namee=getcontactname();
  ///  idd=getcontactid();
   
    
    btn.setOnClickListener(this);
 /*   ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this,
  		  android.R.layout.simple_list_item_1, namee);
  		  conlist.setAdapter(mAdapter);
  		  
  		conlist.setOnItemClickListener(new OnItemClickListener() {
  			 @Override
  		      public void onItemClick(AdapterView<?> parent, View view, int position,
  		              long id) {
  				Intent callIntent = new Intent(Intent.ACTION_CALL);
  	           callIntent.setData(Uri.parse("tel:"+idd[position]));
  	           startActivity(callIntent);         
  		      }
  		  });
  		
    */
}

  	 
@Override
public void onClick(View arg0) {
    if (arg0 == btn) {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, 1);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error in intent : ", e.toString());
        }
    }
}



		  
/*public String[] getcontactid() {
	int i=0;
	ContentResolver cr = getContentResolver();
    Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
    String[] id=new String[cur.getCount()];
    if (cur.getCount() > 0) {
    while (cur.moveToNext()) {
       // id[i] = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
    	 id[i] = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
         i++;
        }
    }
	   return id; 
}

public String[] getcontactname() {
	int i=0;
	ContentResolver cr = getContentResolver();
    Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
    String[] name=new String[cur.getCount()];
    if (cur.getCount() > 0) {
    while (cur.moveToNext()) {
      name[i] = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
      i++;
        }
    }
	   return name; 
}*/
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
    	String gett= GetTextFromVoice.qwe;
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
/*String temp, no1;
                do
                {    
               temp= phoneCur.getString(phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
               no1 = phoneCur.getString(phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
 
               if(temp.equals("a"))
                   {
            	   
            	        Intent callIntent = new Intent(Intent.ACTION_CALL);
            	        callIntent.setData(Uri.parse(no1));
            	        startActivity(callIntent);
                	
                }
                }
                while(phoneCur.moveToNext());
                */
                Log.e("Phone no & name :***: ", name + " : " + no);
                txt.append("Name: "+name+"\n");
                txt1.append(no);

                id = null;
                name = null;
                no = null;
                phoneCur = null;
            }
            contect_resolver = null;
            cur = null;
            //                      populateContacts();
            
            
            
            
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
