package vici.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ui.R;

import java.util.ArrayList;
import java.util.List;

import vici.ui.model.Commands;

public class ResponsesAdapter extends BaseAdapter {
          
         /*********** Declare Used Variables *********/
         private Context mContext;
         private ArrayList<Commands> data;
         private static LayoutInflater inflater=null;

         public Resources res;

         int i=0;
          
         /*************  CustomAdapter Constructor *****************/
         public ResponsesAdapter(ArrayList d,Context mContext) {
              super();
                /********** Take passed values **********/
             this.mContext=mContext;
                 this.data=d;

                 /***********  Layout inflator to call external xml layout () ***********/
                  inflater = ( LayoutInflater )this.mContext.
                                              getSystemService(Context.LAYOUT_INFLATER_SERVICE);
              
         }
      
         /******** What is the size of Passed Arraylist Size ************/
         @Override
         public int getCount() {
              
             if(data.size()<=0)
                 return 1;
             return data.size();
         }

    @Override
         public Commands getItem(int position) {
             return data.get(position);
         }

    @Override
         public long getItemId(int position) {
             return position;
         }
          
         /********* Create a holder Class to contain inflated xml file elements *********/
         public static class ViewHolder{
              
             public TextView text;

      
         }
      
         /****** Depends upon data size called for each row , Create each ListView row *****/
         @Override
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
                 String tempValues;
                 tempValues = data.get( position ).text;
                  holder.text.setText(tempValues+"");
                 holder.text.setGravity(data.get(position).direction ? Gravity.RIGHT : Gravity.LEFT);
             }
             return vi;
         }

     }