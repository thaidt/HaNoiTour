package com.example.hanoitours;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class PlaceDetail extends Activity {
	
	private final String URL = "http://hanoitour.herokuapp.com/places/";
	TextView text;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        
        String url = URL + getIntent().getStringExtra("TEST") + ".json";
        text = (TextView) findViewById(R.id.place_name);
        new GetPlaceInfoTask(this).execute(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_place_detail, menu);
        return true;
    }
    
    public void updateUI(PlaceInfo placeInfo){
        text.setText(placeInfo.name+"\n"+placeInfo.address+"\n"+placeInfo.info);
    }
}
