package com.example.hanoitours;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.widget.TextView;

public class GetPlaceInfoTask extends AsyncTask <String, Integer, PlaceInfo>{

	private String NAME = "name";
	private String ADDRESS = "address";
	private String IMAGE = "image";
	private String INFO = "info";
	
	private PlaceDetail currentActivity;
	
	public GetPlaceInfoTask(PlaceDetail activity) {
		this.currentActivity = activity;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected PlaceInfo doInBackground(String... arg0) {
		return download(arg0[0]);
	}
	
	@Override
	protected void onPostExecute(PlaceInfo result) {
		currentActivity.updateUI(result);
	}
	
	private PlaceInfo download(String url){
		HttpClient client = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response;
		try{
			response = client.execute(httpget);
			HttpEntity entity = response.getEntity();
			
			//need to be edited
			JSONObject place = new JSONObject((new BufferedReader(
					new InputStreamReader(entity.getContent()))).readLine());
			return new PlaceInfo(
					place.getString(NAME), place.getString(ADDRESS),
					place.getString(IMAGE), place.getString(INFO));
		}catch(Exception e){
			int a = 0;
			a = a /a;
		}
		return null;
	}
}
