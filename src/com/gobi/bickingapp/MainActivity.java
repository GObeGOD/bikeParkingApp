package com.gobi.bickingapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class MainActivity extends Activity {
	private static final int GPS_ERRORDIALOG_REQUEST = 7003;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	    View view = this.getWindow().getDecorView();
view.setBackgroundColor(Color.WHITE);


		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void toMapClick (View view) {
		switch (view.getId()) {
		case R.id.button1:
			
			if(servicesOK()){
			Toast.makeText(this, "Ready MAP", Toast.LENGTH_SHORT).show();

			}
				Intent intent = new Intent(this, MapActivity.class);
				startActivity(intent);
  	break;
		
		case R.id.button2:
			Intent intent2 = new Intent(this, SaveListActivity.class);
			startActivity(intent2);
			
			break;
	}
		
	}
	
	
	public boolean servicesOK(){
	    int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

	    if(isAvailable == ConnectionResult.SUCCESS){
	        return true;
	    }
	    else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)){
	        Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, this,GPS_ERRORDIALOG_REQUEST);
	        dialog.show();
	    }
	    else {
	        Toast.makeText(this,"can't connect to Google Play Services",Toast.LENGTH_SHORT).show();
	    }
	    return false ;
	}
}
