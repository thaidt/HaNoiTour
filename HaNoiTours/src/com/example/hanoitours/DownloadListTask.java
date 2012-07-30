package com.example.hanoitours;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.google.android.maps.GeoPoint;

public class DownloadListTask extends AsyncTask <String, Place, Integer>{
	
	private final int BASE = 1000000;
	private final String ID = "id";
	private final String NAME = "name";
	
	PlaceList itemizedoverlay;
	
	public DownloadListTask(PlaceList itemizedoverlay) {
		super();
		this.itemizedoverlay = itemizedoverlay;
	}

	@Override
	protected Integer doInBackground(String... url) {
		for(int i = 0; i < url.length; i++)
			download(url[i]);
		return null;
	}
	
	private void download(String url){
		HttpClient client = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response;
		try{
			response = client.execute(httpget);
			HttpEntity entity = response.getEntity();
			
			//need to be edited
			JSONArray list= new JSONArray((new BufferedReader(
					new InputStreamReader(entity.getContent()))).readLine());
			
			for(int i = 0; i < list.length(); i++){
				JSONObject place = list.getJSONObject(i);
				int x = (int)(place.getDouble("latitude") * BASE);
				int y = (int)(place.getDouble("longitude") * BASE);
				GeoPoint point = new GeoPoint(x, y); 
				itemizedoverlay.addOverlay(new Place(
						place.getString(ID), place.getString(NAME), point));
			}
		}catch(Exception e){
		}
	}
}
