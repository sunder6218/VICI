package vici.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ui.R;

import java.util.ArrayList;
import java.util.List;

public class ResponsesAdapter extends BaseAdapter {
          
         /*********** Declare Used Variables *********/
         private Context mContext;
         private ArrayList<String> data;
         private static LayoutInflater inflater=null;

         public Resources res;

         int i=0;
          
         /*************  CustomAdapter Constructor *****************/
         public ResponsesAdapter(ArrayList d,Context mContext) {
              
                /********** Take passed values **********/
             this.mContext=mContext;
                 this.data=d;

                 /***********  Layout inflator to call external xml layout () ***********/
                  inflater = ( LayoutInflater )mContext.
                                              getSystemService(Context.LAYOUT_INFLATER_SERVICE);
              
         }
      
         /******** What is the size of Passed Arraylist Size ************/
         public int getCount() {
              
             if(data.size()<=0)
                 return 1;
             return data.size();
         }
      
         public String getItem(int position) {
             return data.get(position);
         }
      
         public long getItemId(int position) {
             return position;
         }
          
         /********* Create a holder Class to contain inflated xml file elements *********/
         public static class ViewHolder{
              
             public TextView text;

      
         }
      
         /****** Depends upon data size called for each row , Create each ListView row *****/
         public View getView(int position, View convertView, ViewGroup parent) {
              
             View vi = convertView;
             ViewHolder holder;
              
             if(convertView==null){
                  
                 /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
                 vi = inflater.inflate(R.layout.item_listview, null);
                  
                 /****** View Holder Object to contain tabitem.xml file elements ******/
 
                 holder = new ViewHolder();
                 holder.text = (TextView) vi.findViewById(R.id.response);

                  
                /************  Set holder with LayoutInflater ************/
                 vi.setTag( holder );
             }
             else 
                 holder=(ViewHolder)vi.getTag();
              
             if(data.size()<=0)
             {
                 holder.text.setText("");
                  
             }
             else
             {
                 String tempValues=null;
                 tempValues = data.get( position );
                  holder.text.setText(tempValues);

             }
             return vi;
         }

     }