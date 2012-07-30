package com.example.hanoitours;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class PlaceList extends ItemizedOverlay<OverlayItem> {
	private ArrayList<Place> mOverlays = new ArrayList<Place>();
	Activity mContext;

	public PlaceList(Drawable defaultMarker, Activity context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}
	
	@Override
	protected boolean onTap(int index) {
		Place item = mOverlays.get(index);
    	Intent intent = new Intent(mContext, PlaceDetail.class);
    	intent.putExtra("TEST", item.id);
    	mContext.startActivity(intent);
		return true;
	}
	
	public void addOverlay(Place overlay) {
		mOverlays.add(overlay);
		populate();
	}
}
