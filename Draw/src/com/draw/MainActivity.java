package com.draw;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class MainActivity extends MapActivity {

	private MapController mMapController;
	private MapView mapView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mMapController = mapView.getController();
        mMapController.setZoom(18);
        //two points
        GeoPoint point1= new GeoPoint(19240000,-99120000);
        GeoPoint point2 = new GeoPoint(19241000,-99121000);
        mMapController.setCenter(point1);
        //Pass the geoPoints to the overlay class
        MapOverlay mapOverlay= new MapOverlay (point1,point2);
        mapView.getOverlays().add(mapOverlay);
        
    }
    ///////////////////////////////////////////////////////
    public class MapOverlay extends com.google.android.maps.Overlay{
    	private GeoPoint mGpt1;
    	private GeoPoint mgpt2;
    	public MapOverlay(GeoPoint gp1, GeoPoint gp2){
    		mGpt1=gp1;
    		mgpt2=gp2;
    		
    	}
    	
    	public boolean draw(Canvas canvas, MapView mapView,boolean shadow,long when){
    		super.draw(canvas, mapView, shadow);
    		Paint paint;
    		paint = new Paint();
    		paint.setColor(Color.RED);
    		paint.setAntiAlias(true);
    		paint.setStyle(Style.STROKE);
    		paint.setStrokeWidth(2);
    		Point pt1 = new Point();
    		Point pt2 = new Point();
    		Projection projection = mapView.getProjection();
    		projection.toPixels(mGpt1, pt1);
    		projection.toPixels(mgpt2, pt2);
    		canvas.drawLine(pt1.x, pt1.y, pt2.x, pt2.y, paint);
    		return true;
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
