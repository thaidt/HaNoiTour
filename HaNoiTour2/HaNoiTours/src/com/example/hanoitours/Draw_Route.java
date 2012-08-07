package com.example.hanoitours;
import java.util.ArrayList;

import java.util.ArrayList;

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

public class Draw_Route extends MapActivity {
	private MapController mMapController;
	private MapView mapView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_route);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mMapController = mapView.getController();
        mMapController.setZoom(18);
        //two points
        GeoPoint point1= new GeoPoint(19240000,-99120000);
        GeoPoint point2 = new GeoPoint(19241000,-99121000);
        GeoPoint point3 = new GeoPoint(19245000,-99121000);
        GeoPoint point4 = new GeoPoint(19241000,-99125000);
        
        ArrayList<GeoPoint> listGeo = new ArrayList<GeoPoint>();
        //listGeo.add(point1);
        //listGeo.add(point2);
        //listGeo.add(point3);
        //listGeo.add(point4);
        
        mMapController.setCenter(point1);
        
        GeoPoint input[]=new GeoPoint[2];
        input[0]=new GeoPoint(21027149,105849298);
        input[1]=new GeoPoint(21017054,105828526);
        
        GetDirectionsTask direction = new GetDirectionsTask(this);
        direction.execute(input);
        
        //Pass the geoPoints to the overlay class
        //MapOverlay mapOverlay[] = new MapOverlay [4];
        
        /*for(int i=0;i<number-1;i++){
        	mapOverlay[i] = new MapOverlay (listGeo.get(i),listGeo.get(i+1));
        	mapView.getOverlays().add(mapOverlay[i]);
        }*/
        //MapOverlay mapOverlay1 = new MapOverlay(listGeo);
        //mapView.getOverlays().add(mapOverlay1);
        
    }
    public void updateUI(ArrayList<GeoPoint> listGeo){
    	MapOverlay mapOverlay1 = new MapOverlay(listGeo);
        mapView.getOverlays().add(mapOverlay1);
    }
    ///////////////////////////////////////////////////////
    public class MapOverlay extends com.google.android.maps.Overlay{
    	private GeoPoint mGpt1;
    	private GeoPoint mgpt2;
    	private GeoPoint myGeoPoint [];
    	public MapOverlay(GeoPoint gp1, GeoPoint gp2){
    		mGpt1=gp1;
    		mgpt2=gp2;
    	}
    	public MapOverlay(ArrayList<GeoPoint> listGeoPoints){
    		
    		myGeoPoint= new GeoPoint [listGeoPoints.size()];
    		for (int i=0;i<listGeoPoints.size();i++){
    			myGeoPoint[i]=listGeoPoints.get(i);
    		}
    	}
    	
    	public boolean draw(Canvas canvas, MapView mapView,boolean shadow,long when){
    		super.draw(canvas, mapView, shadow);
    		Paint paint;
    		paint = new Paint();
    		paint.setColor(Color.RED);
    		paint.setAntiAlias(true);
    		paint.setStyle(Style.STROKE);
    		paint.setStrokeWidth(3);
    		
    		//Point pt1 = new Point();
    		//Point pt2 = new Point();
    		
    		Point listPoints [] = new Point[myGeoPoint.length];
    		for(int i=0;i<myGeoPoint.length;i++){
    			listPoints[i] = new Point();
    		}
    		Projection projection = mapView.getProjection();
    		//projection.toPixels(mGpt1, pt1);
    		//projection.toPixels(mgpt2, pt2);
    		for(int i=0;i<myGeoPoint.length;i++){
    			projection.toPixels(myGeoPoint[i], listPoints[i]);
    		}
    		for(int i=0;i<myGeoPoint.length-1;i++){
    			canvas.drawLine(listPoints[i].x, listPoints[i].y, listPoints[i+1].x, listPoints[i+1].y, paint);
    		}
    		//canvas.drawLine(pt1.x, pt1.y, pt2.x, pt2.y, paint);
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
