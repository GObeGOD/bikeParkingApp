package com.gobi.bickingapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;

public class SaveListActivity extends ListActivity {
	ParkingDataSource dataSource;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.savelist);
	    View view = this.getWindow().getDecorView();
	    view.setBackgroundColor(Color.WHITE);
		dataSource = new ParkingDataSource(this);
		dataSource.open();

	

	    
	    List<BicycleParking> parkingloc = dataSource.getAllParkingLoc();
	    if (parkingloc.isEmpty()) {
			Log.d("parkingloc!!",  "parkingloc empty");

			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			// Add the buttons
		
				builder.setTitle("No saves").setMessage(
						"No location saved -- Click a marker than the save icon to save");

	
			builder.setPositiveButton("Got it",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// User clicked OK button
							Log.i("alertDialog", "OKAY cLICKED");
						
						}
					});
			builder.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// User cancelled the dialog
							Log.i("alertDialog", "CANCEL cLICKED");

						}
					});
			// Set other dialog properties

			// Create the AlertDialog
			AlertDialog alertDialog = builder.create();
			alertDialog.show();
			
			
			
			
			
			
		}else{
			  final ArrayList<String> list = new ArrayList<String>();
			  Geocoder gcd = new Geocoder(this, Locale.getDefault());


	    for (int i = 0; i < parkingloc.size(); ++i) {
	    	
	    	if (parkingloc.get(i).getLatitude()  != null && parkingloc.get(i).getLongitude() != null) {
				
	    		double lat = Double.parseDouble(parkingloc.get(i).getLatitude());

		        double lng = Double.parseDouble(parkingloc.get(i).getLongitude());
		    	
		        try {
					List<android.location.Address> addresses = gcd.getFromLocation(lat, lng, 1);
			   // 	Log.i("JKASDAS", addresses.get(0).getPostalCode());
					if (addresses.get(0).getAddressLine(1) != null) {
					      list.add(parkingloc.get(i).Street_1 +" -- " + addresses.get(0).getAddressLine(1));

					}


				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	    	
	    //  list.add(parkingloc.get(i).Street_1 );
	      
	      
	      
	    }	  
	    
		Log.d("666HEY!!",  "Adding parks to list");
	    // use the SimpleCursorAdapter to show the
	    // elements in a ListView
		
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	        android.R.layout.simple_list_item_1,list);
	    
	    
	    
	    setListAdapter(adapter);
			
			
		}
	  


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
	

}
