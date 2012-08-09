package com.example.hanoitours;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

public class GetPlaceListTask extends AsyncTask <String, Place, Integer>{
	
	private String TAG ="DownloadListTask";
	private final int BASE = 1000000;
	private final String ID = "id";
	private final String NAME = "name";
	
	PlaceList itemizedoverlay;
	
	public GetPlaceListTask(PlaceList itemizedoverlay) {
		super();
		this.itemizedoverlay = itemizedoverlay;
	}

	@Override
	protected Integer doInBackground(String... url) {
		for(int i = 0; i < url.length; i++)
			download(url[i]);
		return null;
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
	
	private void download(String url){
		HttpClient client = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response;
		try{
			response = client.execute(httpget);
			HttpEntity entity = response.getEntity();
			
			//need to be edited
			JSONArray list= new JSONArray(
					this.streamToString(entity.getContent()));
			
			for(int i = 0; i < list.length(); i++){
				JSONObject place = list.getJSONObject(i);
				
				if(place == null){
					Log.e(TAG, "place at " + i +" is null");
					continue;
				}
				Double xFloat = place.getDouble("latitude");
				Double yFloat = place.getDouble("longitude");
				if(xFloat == null || yFloat == null){
					Log.e(TAG, "place at " + i +" is null");
					continue;
				}
				int x = (int)( xFloat * BASE);
				int y = (int)( yFloat * BASE);
				GeoPoint point = new GeoPoint(x, y); 
				itemizedoverlay.addOverlay(new Place(
						place.getString(ID), place.getString(NAME), point));
			}
		}catch(JSONException e){
			Log.e(TAG, "JSONException " + e);
		}catch(IOException e){
			Log.e(TAG, "IOException " + e);			
		}
	}
}
