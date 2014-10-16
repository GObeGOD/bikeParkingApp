package com.gobi.bickingapp;

import java.io.File;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	// Database Version
	// upgrade DATABASE_VERSION to drop old table and recreate using new data
	private static final int DATABASE_VERSION = 1;

	// Database Name
	public static final String DATABASE_NAME = "AnimeOpandEdManager";

	// Contacts table name
	public static final String TABLE_ParkingBike = "ParkingBike";

	// Contacts Table Columns names
	public static final String KEY_ID = "id";
	public static final String KEY_Street = "street";
	public static final String KEY_Latitude = "latitude";
	public static final String KEY_Longitude = "longitude";



	private static final String CREAT_ANIMEOPANDED_TABLE = "CREATE TABLE "
			+ TABLE_ParkingBike + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_Street + " TEXT," + KEY_Latitude + " TEXT," + KEY_Longitude  + " TEXT" + ")";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("creating DB");

		db.execSQL(CREAT_ANIMEOPANDED_TABLE);
		Log.i("DB created: ", CREAT_ANIMEOPANDED_TABLE);

	}

	public boolean doesDatabaseExist(ContextWrapper context, String dbName) {
		File dbFile = context.getDatabasePath(dbName);
		return dbFile.exists();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ParkingBike);
		Log.i("onUpgrade", "DROPED TABLE!!");
		// Create tables again
		onCreate(db);
	}

}
