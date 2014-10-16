package com.gobi.bickingapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ParkingDataSource {

	DatabaseHandler dbHelper;
	SQLiteDatabase dataBase;
	Context mContext;

	public ParkingDataSource(Context context) {
		mContext = context;
		dbHelper = new DatabaseHandler(context);

	}

	public void open() {
		Log.i("DataSource", "Database Opened");
		dataBase = dbHelper.getWritableDatabase();

	}

	public void close() {
		Log.i("DataSource", "Database Closed");
		dbHelper.close();
	}

	public static final String[] allcolumns = {

	DatabaseHandler.KEY_ID, DatabaseHandler.KEY_Street, DatabaseHandler.KEY_Latitude,
			DatabaseHandler.KEY_Longitude

	};

	// Adding new Anime music
	public BicycleParking addParking(BicycleParking bikeParking) {
		// SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.KEY_Street, bikeParking.getStreet_1()); // anime Name
		values.put(DatabaseHandler.KEY_Latitude,bikeParking.getLatitude()); // anime song

		values.put(DatabaseHandler.KEY_Longitude, bikeParking.getLongitude());
	
		// Inserting Row
	//	long insertid = dataBase.insert(DatabaseHandler.TABLE_ParkingBike,
	//			null, values);

		//opandEd.setID(insertid);
		// ddbHelperb.close(); // Closing database connection
		return bikeParking;
	}

	public List<BicycleParking> getAllParkingLoc() {

		// Toast.makeText(mContext,"list animeOP WORKING",
		// Toast.LENGTH_LONG).show();

		Cursor cursor = dataBase.query(DatabaseHandler.TABLE_ParkingBike,
				allcolumns, null, null, null, null, null);
		Log.i("getAllParkingLoc", "Found" + cursor.getCount() + "Locations");

		List<BicycleParking> parkingList = cursorToList(cursor);

		// return contact list
		return parkingList;

	}





	

	public List<BicycleParking> cursorToList(Cursor cursor) {
		List<BicycleParking> parkingList = new ArrayList<BicycleParking>();
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {

				BicycleParking bikeParking = new BicycleParking();
			//	bikeParking.s(cursor.getLong(cursor.getColumnIndex(DatabaseHandler.KEY_ID)));
				bikeParking.setStreet_1(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_Street)));
				bikeParking.setLatitude(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_Latitude)));
				bikeParking.setLongitude(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_Longitude)));


				// Adding contact to list
				parkingList.add(bikeParking);
			} while (cursor.moveToNext());
		}
		return parkingList;

	}

	/*public void updateAnime(AnimeOpAndEdData opandEd, String complete) {
		ContentValues values = new ContentValues();

		values.put(DatabaseHandler.KEY_COMPLETE, complete); // anime
															// Name
		dataBase.update(DatabaseHandler.TABLE_AnimeOPandEd, values,
				"complete = ?", new String[] { complete});
		Log.i("UpdateAnime", "complete: " + complete);


	}*/
	


}
