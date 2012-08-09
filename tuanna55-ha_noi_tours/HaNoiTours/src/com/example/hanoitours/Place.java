package com.example.hanoitours;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class Place extends OverlayItem {
	
	public String name;
	public String id;
	public GeoPoint point;
	public Place(String id, String name, GeoPoint point) {
		super(point, "", "");
		this.name = name;
		this.id = id;
		this.point = point;
	}
}
