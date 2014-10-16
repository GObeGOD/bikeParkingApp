package com.gobi.bickingapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CSVAdapter extends ArrayAdapter<BicycleParking>{
    Context ctx;

    
    //We must accept the textViewResourceId parameter, but it will be unused
    //for the purposes of this example.
    public CSVAdapter(Context context, int textViewResourceId ) {
            super(context, textViewResourceId);
            
          
            
            //Store a reference to the Context so we can use it to load a file from Assets.
            this.ctx = context;
            
            //Load the data.
           loadArrayFromFile();        
    }

    
    @Override
    public View getView(final int pos, View convertView, final ViewGroup parent){
        
            TextView mView = (TextView)convertView;
           

            if(null == mView){ 
                    mView = new TextView(parent.getContext());
                    mView.setTextSize(28);
            }
            
          
           mView.setText(getItem(pos).getStreet_1());
          
          
            return mView;
    }
    
    private void loadArrayFromFile(){
        try {
        	Log.i("loadArrayFromFile", "I got called");
                // Get input stream and Buffered Reader for our data file.
                InputStream is = ctx.getAssets().open("ringpost.csv"); 
             //InputStream is = ctx.getAssets().open("states.csv"); 

                
                
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                
                //Read each line
                while ((line = reader.readLine()) != null) {
                	Log.i("loadArrayFromFile", "while LOOP");
                        //Split to separate the name from the capital
                        String[] RowData = line.split(",");
                        
                        //Create a State object for this row's data.
                        BicycleParking cur = new  BicycleParking();
                   
                    	//String Post_ID;
                        //String Mid_Block_ID;
                        //String Street_1;
                        //String Street_2;
                        //String Street_3;
                    	//String Side;
                    	//String Latitude;
                    	//String Longitude;
                    	//String Core;
                    	//String Notes;
                    	
                    	Log.i("Post_ID", RowData[1]);
                    	Log.i("Mid_Block_ID", RowData[2]);
                        Log.i("Street_1", RowData[3]);
                     	Log.i("Street_2", RowData[4]);
                    	Log.i("Street_3", RowData[5]);
                    	Log.i("Side", RowData[6]);
                    	Log.i("Latitude", RowData[7]);
                    	Log.i("Longitude", RowData[8]);
                    	Log.i("Core", RowData[9]);
                    	Log.i("Notes", RowData[10]);


                    	

                    	
                    	
                    	
                        
                        cur.setPost_ID((RowData[1]));
                        cur.setMid_Block_ID((RowData[2]));
                        cur.setStreet_1((RowData[3]));
                        cur.setStreet_2((RowData[4]));
                        cur.setStreet_3((RowData[5]));
                        cur.setSide((RowData[6]));
                        cur.setLatitude((RowData[7]));
                        cur.setLongitude((RowData[8]));
                        cur.setCore((RowData[9]));
                        cur.setNotes((RowData[10]));
                        
                        
                        



                        
                        this.add(cur);
                }
        } catch (IOException e) {
                e.printStackTrace();
        }
}
    


}