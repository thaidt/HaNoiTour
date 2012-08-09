package com.example.hanoitours;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.maps.GeoPoint;

public class GetDirectionsTask extends AsyncTask <ArrayList<GeoPoint>, Integer, ArrayList<GeoPoint>>{

	private String TAG ="GetPlaceInfoTask";
	
	private String URL = "http://maps.googleapis.com/maps/api/directions/json?origin=";
	private String STEPS = "steps";
	private String LEGS = "legs";
	private int BASE = 1000000;
	public ArrayList<GeoPoint> listGeoPoints;
	
	private DrawRoute DrawActivity;
	
	public GetDirectionsTask(DrawRoute activity) {
		super();
		this.DrawActivity = activity;
	}

	@Override
	protected ArrayList<GeoPoint> doInBackground(ArrayList<GeoPoint>... arg0) {
		return getDirections(requestDirections(makeUrl(arg0[0])));
	}
	
	@Override
	protected void onPostExecute(ArrayList<GeoPoint> result) {
		/*GeoPoint buffer;
		for(int i=0;i<result.size();i++){
			buffer=result.get(i);
			listGeoPoints.add(buffer);
		}*/
		//DrawActivity.Draw(result);
	}
	
	private String streamToString(InputStream input){
		BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
        String content = "";
        String temp = null;
        try {
            while ((temp = buffer.readLine()) != null) {
                content = content + temp;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
	}
	
	private double reFloatE6(int x){
		return ((double)x)/BASE;
	}
	
	private String pointToString(GeoPoint point){
		String result = "";
		result = result + reFloatE6(point.getLatitudeE6())
				+ ", " + reFloatE6(point.getLongitudeE6());
		return result;
	}
	
	private String makeUrl(ArrayList<GeoPoint> arg0){
		if (arg0.size() < 2)
				return null;
		String url = URL + pointToString(arg0.get(0))
				+ "&destination=" + pointToString(arg0.get(1))
				+ "&waypoints=optimize:true";
		for(int i = 2; i < arg0.size(); i++)
			url = url + "|" + pointToString(arg0.get(i));
		url = url + "&sensor=true&mode=driving";
		return url;
	}
	
	private JSONObject requestDirections(String url){
		HttpClient client = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response;
		try{
			response = client.execute(httpget);
			HttpEntity entity = response.getEntity();
			
			//need to be edited
			JSONObject directions = new JSONObject(
					this.streamToString(entity.getContent()));
			return directions;
		}catch(JSONException e){
			Log.e(TAG, "JSONException " + e);
		}catch(IOException e){
			Log.e(TAG, "IOException " + e);			
		}
		return null;
	}
	
	private GeoPoint JSONToGeoPoint(JSONObject step, String key){
		try{
			double x = step.getJSONObject(key).getDouble("lat");
			double y = step.getJSONObject(key).getDouble("lng");
			return new GeoPoint((int) x * BASE,
					(int) y * BASE);
		}catch(Exception e){
			
		}
		return null;
	}
	
	private ArrayList<GeoPoint> getDirections(JSONObject direction){
		if(direction == null)
			return null;
		try{
			ArrayList<GeoPoint> result= new ArrayList<GeoPoint>();
			JSONArray legs = direction.getJSONArray(LEGS);
			
			for(int i = 0; i < legs.length(); i++){
				
				JSONArray steps = legs.getJSONObject(i).getJSONArray(STEPS);
				
				for(int j = 0; j < steps.length(); j++){
					result.add(JSONToGeoPoint(steps.getJSONObject(j),"start_location"));
					result.add(JSONToGeoPoint(steps.getJSONObject(j),"end_location"));
				}
			}
			return result;
		}catch(Exception e){
			
		}
		return null;
	}
}
