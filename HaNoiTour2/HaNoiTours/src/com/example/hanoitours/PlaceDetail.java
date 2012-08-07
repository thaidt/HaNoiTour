package com.example.hanoitours;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PlaceDetail extends Activity {
	
	private final String TAG= "PlaceDetail";
	private final String URL = "http://hanoitour.herokuapp.com/places/";
	
	private TextView text;
	private GetPlaceInfoTask getInfoTask;
	private GetImageTask getImageTask;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        
        String url = URL + getIntent().getStringExtra("TEST") + ".json";
        text = (TextView) findViewById(R.id.place_name);
        getInfoTask = new GetPlaceInfoTask(this);
        getInfoTask.execute(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
    
    @Override
    protected void onPause(){
    	super.onPause();
    	getInfoTask.cancel(true);
    }
    
    public void updateUI(PlaceInfo placeInfo){
        text.setText(placeInfo.name+"\n"+placeInfo.address+"\n"+placeInfo.info);
        getImageTask = new GetImageTask(this);
        getImageTask.execute(placeInfo.image);
    }

    public void updateImage(Drawable image){
    	ImageView imageView = (ImageView) findViewById(R.id.place_image);
    	imageView.setBackgroundDrawable(image);
    }
    
    public void mark(View view){
        
    }
}
