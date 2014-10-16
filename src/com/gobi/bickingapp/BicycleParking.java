package com.gobi.bickingapp;

public class BicycleParking {
	// Unique ID for each individual ring & post.
	String Post_ID;
	// Unique ID for each individual mid-block Ð also differentiated by side of the road.
	String Mid_Block_ID;
	//The street on which the ring & post is located.
	String Street_1;
	//FROM
	String Street_2;
	//TO
	String Street_3;
	//The side of the street on which the ring & post is located. Adjacent_to Ð Closest address to which the ring & post is located.
	String Side;
	//LATITUDE
	String Latitude;
	//LONGITUDE
	String Longitude;
	//Whether this is a new core or an existing core.
	String Core;
	//Notes
	String Notes;
	
	public BicycleParking(){
		
	}
	public  BicycleParking(String street1,String lat, String lon){
		
		this.Street_1 = street1;
		this.Latitude = lat;
		this.Longitude = lon;
		
	}
	
	public String getPost_ID() {
		return Post_ID;
	}
	public void setPost_ID(String post_ID) {
		Post_ID = post_ID;
	}
	public String getMid_Block_ID() {
		return Mid_Block_ID;
	}
	public void setMid_Block_ID(String mid_Block_ID) {
		Mid_Block_ID = mid_Block_ID;
	}
	public String getStreet_1() {
		return Street_1;
	}
	public void setStreet_1(String street_1) {
		Street_1 = street_1;
	}
	public String getStreet_2() {
		return Street_2;
	}
	public void setStreet_2(String street_2) {
		Street_2 = street_2;
	}
	public String getStreet_3() {
		return Street_3;
	}
	public void setStreet_3(String street_3) {
		Street_3 = street_3;
	}
	public String getSide() {
		return Side;
	}
	public void setSide(String side) {
		Side = side;
	}
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	public String getLongitude() {
		return Longitude;
	}
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	public String getCore() {
		return Core;
	}
	public void setCore(String core) {
		Core = core;
	}
	public String getNotes() {
		return Notes;
	}
	public void setNotes(String notes) {
		Notes = notes;
	}

}
