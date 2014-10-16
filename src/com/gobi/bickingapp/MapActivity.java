package com.gobi.bickingapp;

import java.io.IOException;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements
		View.OnClickListener,GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener{
	CSVAdapter mAdapter;
	Marker currentLocation;
	ParkingDataSource dataSource;

	private static final int GPS_ERRORDIALOG_REQUEST = 7003;

	GoogleMap mMap;
	MapView mMapView;
	LocationClient mLocationClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mAdapter = new CSVAdapter(this, -1);
		dataSource = new ParkingDataSource(this);
		dataSource.open();

		// mAdapter.getItem(1);
		System.out.println("*******HERE*********");
		System.out.println("*******HERE*********");

		System.out.println("*******HERE*********");

		System.out.println("*******HERE*********");
		Log.e("Help me", "the log from mAdapter class ends here");

		// this is how I can get the latitude of items within mAdapter
		System.out.println(mAdapter.getItem(0).Latitude);

		if (servicesOK()) {
			setContentView(R.layout.activity_map);
			// mMapView = (MapView) findViewById(R.id.map);
			// mMapView.onCreate(savedInstanceState);
			if (initMap()) {
				Toast.makeText(this, "MAP READY TO USE", Toast.LENGTH_SHORT)
						.show();
				mMap.setMyLocationEnabled(true);
				
				mLocationClient = new LocationClient(this, this, this);
				mLocationClient.connect();
				// List<Address> addresses = null;
				String address = "";
				double latDouble = 0;
				double lonDouble = 0;

				for (int i = 1; i < 80; i++) {

					String lat = mAdapter.getItem(i).Latitude;
					String lon = mAdapter.getItem(i).Longitude;
					System.out.println(lat);
					address = mAdapter.getItem(i).Street_1;

					System.out.println(lon);
					// Geocoder gc = new Geocoder(this, Locale.getDefault());

					latDouble = Double.parseDouble(lat);
					lonDouble = Double.parseDouble(lon);

					MarkerOptions parkingOptions = new MarkerOptions()
							.title(address)
							// Tokyo Tower
							// 35.658801, 139.745509
							.position(new LatLng(latDouble, lonDouble))
							.icon(BitmapDescriptorFactory
									.fromResource(R.drawable.pin_bike));
					mMap.addMarker(parkingOptions);
				}

				mMap.setOnMapLongClickListener(new OnMapLongClickListener() {

					@Override
					public void onMapLongClick(LatLng ll) {
						Geocoder gc = new Geocoder(getApplicationContext());
						List<android.location.Address> list = null;

						try {
							list = gc.getFromLocation(ll.latitude,
									ll.longitude, 1);

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						android.location.Address add = list.get(0);
						gotoLocationNomovecamera(add);

					}
				});

				mMap.setOnMarkerClickListener(new OnMarkerClickListener() {

					@Override
					public boolean onMarkerClick(Marker marker) {
						Log.d("from marker", marker.getPosition().toString());

						currentLocation = marker;
						return false;
					}
				});

			} else {
				Toast.makeText(this, "Sorry no Map ", Toast.LENGTH_SHORT)
						.show();

			}

		} else {
			setContentView(R.layout.activity_main);

		}
		

	}

	public boolean servicesOK() {
		int isAvailable = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);

		if (isAvailable == ConnectionResult.SUCCESS) {
			return true;
		} else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable,
					this, GPS_ERRORDIALOG_REQUEST);
			dialog.show();
		} else {
			Toast.makeText(this, "can't connect to Google Play Services",
					Toast.LENGTH_SHORT).show();
		}
		return false;
	}

	private boolean initMap() {
		if (mMap == null) {
			SupportMapFragment mapFrap = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map);
			mMap = mapFrap.getMap();
			if (mMap != null) {
				mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
			
					@Override
					public View getInfoWindow(Marker arg0) {
						return null;
					}

					@Override
					public View getInfoContents(Marker marker) {
						View v = getLayoutInflater().inflate(
								R.layout.map_info_window, null);
						TextView tvLocality = (TextView) v
								.findViewById(R.id.tv_locality);
						TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
						TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
						TextView tvSnippet = (TextView) v
								.findViewById(R.id.tv_snippet);

						LatLng ll = marker.getPosition();

						tvLocality.setText(marker.getTitle());
						tvLat.setText("Latitude: " + ll.latitude);
						tvLng.setText("Longitude: " + ll.longitude);
						tvSnippet.setText(marker.getSnippet());

						return v;

					}
					
				});

			}

		}
		return (mMap != null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

		SearchView searchView = (SearchView) menu.findItem(R.id.search)
				.getActionView();
		if (null != searchView) {
			searchView.setSearchableInfo(searchManager
					.getSearchableInfo(getComponentName()));
			searchView.setIconifiedByDefault(false);
		}

		SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
			public boolean onQueryTextChange(String newText) {
				// this is your adapter that will be filtered
				Log.d("SEARCHnEW", newText);

				return true;
			}

			public boolean onQueryTextSubmit(String query) {

				Geocoder gc = new Geocoder(getApplicationContext());
				// List<Address> list = gc.getFromLocationName(query, 5);
				try {
					List<android.location.Address> list = gc
							.getFromLocationName(query, 1);
					android.location.Address add = list.get(0);
					// double lat = add.getLatitude();
					// double lng = add.getLongitude();

					gotoLocation(add);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.d("SEARCH", query);
				return false;
				// Here u can get the value "query" which is entered in the
				// search box.

			}
		};
		searchView.setOnQueryTextListener(queryTextListener);

		return super.onCreateOptionsMenu(menu);

	}

	public boolean onSearchClick(MenuItem view) {

		Log.d("SearchClick", "SEach Clicked");
		return false;

	}

	public boolean onSaveClick(MenuItem view) {

		if (currentLocation != null) {
			Double lat = currentLocation.getPosition().latitude;
			Double lon = currentLocation.getPosition().longitude;

			String latString = String.valueOf(lat);
			String lonString = String.valueOf(lon);

			dataSource.addParking(new BicycleParking(currentLocation.getTitle()
					.toString(), latString, lonString));

			Log.d("Save", "save Clicked");
			Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
		}

		return false;

	}

	public boolean onTwitterClick(MenuItem view) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// Add the buttons
		if (currentLocation == null) {
			builder.setTitle("Tweet Message").setMessage(
					"No location selected -- Click a marker or make one");

		} else {
			builder.setTitle("Tweet Message")
					.setMessage(
							"My bike is in "
									+ currentLocation.getTitle().toString()
									+ "\n\n"
									+ currentLocation.getPosition().toString());

		}

		builder.setPositiveButton("Send",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// User clicked OK button
						Log.i("alertDialog", "OKAY cLICKED");
						// String tweetUrl =
						// "https://twitter.com/intent/tweet?text=PUT TEXT HERE &url="
						// + "https://www.google.com";
						String tweet = "My bike is in "
								+ currentLocation.getTitle().toString() + "\n"
								+ "    \n\n"
								+ currentLocation.getPosition().toString();
						String tweetUrl = "https://twitter.com/intent/tweet?text="
								+ tweet;
						Uri uri = Uri.parse(tweetUrl);
						startActivity(new Intent(Intent.ACTION_VIEW, uri));
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
		return false;

	}


	public void gotocurrentLocation() {
		Location currentLocation = mLocationClient.getLastLocation();
		if (currentLocation == null) {
			Toast.makeText(this, "Current Locatio unavailable", Toast.LENGTH_SHORT).show();
			
		}else{
			LatLng ll = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,5);
			mMap.moveCamera(update);
		}
		
		
		
	}
	private void gotoLocation(android.location.Address add) {

		MarkerOptions gotoLoc = new MarkerOptions()
				.title(add.getAddressLine(0) + " "+ add.getAddressLine(1))
				.snippet(add.getCountryName())
				

				// Tokyo Tower
				// 35.658801, 139.745509
				.position(new LatLng(add.getLatitude(), add.getLongitude()))
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_bike));
		mMap.addMarker(gotoLoc);

		if (add.getPostalCode() != null) {
			MarkerOptions gotoLoc2 = new MarkerOptions()
			.title(add.getAddressLine(0) + add.getAddressLine(1))
			.snippet(add.getCountryName())

			// Tokyo Tower
			// 35.658801, 139.745509
			.position(new LatLng(add.getLatitude(), add.getLongitude()))
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_bike));
			mMap.addMarker(gotoLoc2);

		}
		

		LatLng latandLng = new LatLng(add.getLatitude(), add.getLongitude());
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latandLng, 15);
		mMap.moveCamera(update);
	}

	private void gotoLocationNomovecamera(android.location.Address add) {

		MarkerOptions gotoLoc = new MarkerOptions()
				.title(add.getAddressLine(0) + " "+ add.getAddressLine(1))
				.snippet(add.getCountryName())

				// Tokyo Tower
				// 35.658801, 139.745509
				.position(new LatLng(add.getLatitude(), add.getLongitude()))
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_bike));
		mMap.addMarker(gotoLoc);

		if (add.getPostalCode() != null) {
			MarkerOptions gotoLoc2 = new MarkerOptions()
			.title(add.getAddressLine(0) + " "+ add.getAddressLine(1))
			.snippet(add.getCountryName())

			// Tokyo Tower
			// 35.658801, 139.745509
			.position(new LatLng(add.getLatitude(), add.getLongitude()))
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_bike));
			mMap.addMarker(gotoLoc2);

		}
		

		// LatLng latandLng = new LatLng(add.getLatitude(), add.getLongitude());
		// CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latandLng,
		// 15);
		// mMap.moveCamera(update);
	}

	/*
	 * @Override protected void onDestroy() { super.onDestroy();
	 * mMapView.onDestroy();
	 * 
	 * }
	 * 
	 * 
	 * @Override public void onLowMemory() { super.onLowMemory();
	 * mMapView.onLowMemory(); }
	 * 
	 * 
	 * @Override protected void onPause() { super.onPause(); mMapView.onPause();
	 * }
	 * 
	 * 
	 * @Override protected void onResume() { super.onResume();
	 * mMapView.onResume(); }
	 * 
	 * 
	 * @Override protected void onSaveInstanceState(Bundle outState) {
	 * super.onSaveInstanceState(outState);
	 * mMapView.onSaveInstanceState(outState);
	 * 
	 * }
	 */

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		Log.d("onConnected","Connected to location service");
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

}
