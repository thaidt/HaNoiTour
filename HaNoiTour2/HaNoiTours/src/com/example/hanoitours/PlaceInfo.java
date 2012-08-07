package com.example.hanoitours;

import android.util.Log;

public class PlaceInfo {
	
	private String TAG ="PlaceInfo";
	public String name;
	public String address;
	public String image;
	public String info;
	
	public PlaceInfo(String name, String address, String image, String info){
		if(name == null){
			Log.w(TAG, "name is null");
			name = "";
		}
		if(address == null){
			Log.w(TAG, "address is null");
			address = "";
		}
		if(image == null){
			Log.w(TAG, "image is null");
			image = "";
		}
		if(info == null){
			Log.w(TAG, "info is null");
			info = "";
		}
		this.name = name;
		this.address = address;
		this.image = image;
		this.info = info;
	}
}
