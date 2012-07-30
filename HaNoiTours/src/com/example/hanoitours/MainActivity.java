package com.example.hanoitours;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

public class MainActivity extends MapActivity {
	
	private final String URl_LIST_PLACE = "http://hanoitour.herokuapp.com/places.json";
	
	List<Overlay> mapOverlays;
	MapView map;
	MyLocationOverlay location;
	PlaceList itemizedoverlay;

	private class TrackLocation implements Runnable{
		public TrackLocation() {
		}
		
		public void run(){
			map.getController().animateTo(location.getMyLocation());
		}
    }
	public void changeMap(String area){
		MapView mapView =(MapView) findViewById(R.id.mapview);
		MapController mc = mapView.getController();
		
		GeoPoint myLocation = null;
		double lat = 0;
		double lng = 0;
		try{
			Geocoder g = new Geocoder(this, Locale.getDefault());
			java.util.List<android.location.Address> result=g.getFromLocationName(area , 1);
			if(result.size() > 0){
				Toast.makeText(MainActivity.this, "Country: " + String.valueOf(result.get(0).getCountryName()) , Toast.LENGTH_SHORT).show();
				lat= result.get(0).getLatitude();
				lng= result.get(0).getLongitude();
			}
			else{
				Toast.makeText(MainActivity.this, "not found", Toast.LENGTH_SHORT).show();
			}
		}
		catch(IOException io){
			Toast.makeText(MainActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
		}
		myLocation = new GeoPoint(
				(int) (lat * 1E6),
				(int) (lng * 1E6)
				);
		mc.animateTo(myLocation);
		mc.setZoom(12);
		mapView.invalidate();
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        map = (MapView) findViewById(R.id.mapview);
        map.setBuiltInZoomControls(true);
       
        configMap(map);
            
        mapOverlays = map.getOverlays();

        Drawable drawable = this.getResources().getDrawable(R.drawable.androidmarker);
        itemizedoverlay = new PlaceList(drawable, this);
        mapOverlays.add(itemizedoverlay);

        GeoPoint point = new GeoPoint(21631899,105297546);
        //Place overlayitem = new Place("aaa", "bbb", point);
        //itemizedoverlay.addOverlay(overlayitem);
        
        location = new MyLocationOverlay(this, map);
        mapOverlays.add(location);
        
        map.getController().setCenter(point);
        map.getController().setZoom(10);
        (new DownloadListTask(itemizedoverlay)).execute(URl_LIST_PLACE);
        Button SearchButton = (Button) findViewById(R.id.button1);
        SearchButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText textSearch = (EditText) findViewById(R.id.editText1);
				String area = textSearch.getText().toString();
				MainActivity.this.changeMap(area);
			}
		});
        
        Button M =(Button) findViewById(R.id.button2);
        M.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),SettingActivity.class));
			}
		});
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
        case R.id.menu_settings:
        	Intent intent = new Intent(this, SettingActivity.class);
        	startActivity(intent);
            return true;
        default:
            return super.onOptionsItemSelected(item);
    	}    
    }
    
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
    
    @Override
    protected void onResume (){
    	super.onResume();
    	MapView map = (MapView) findViewById(R.id.mapview);
    	configMap(map);
    	location.enableCompass();
    	location.enableMyLocation();
    }
    
    @Override
    protected void onPause(){
    	super.onPause();
    	location.disableCompass();
    	location.disableMyLocation();
    }
    
    private void configMap(MapView map) {
		map.setSatellite(Setting.satelline);
		map.setTraffic(Setting.traffic);		
	}
    
    public void getLocation(View view){
        location.runOnFirstFix(new TrackLocation());
    }
}