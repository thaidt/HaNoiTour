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
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class GetPlaceInfoTask extends AsyncTask <String, Integer, PlaceInfo>{

	private String TAG ="GetPlaceInfoTask";
	private String NAME = "name";
	private String ADDRESS = "address";
	private String IMAGE = "image";
	private String INFO = "info";
	
	private PlaceDetail currentActivity;
	
	public GetPlaceInfoTask(PlaceDetail activity) {
		super();
		this.currentActivity = activity;
	}

	@Override
	protected PlaceInfo doInBackground(String... arg0) {
		return download(arg0[0]);
	}
	
	@Override
	protected void onPostExecute(PlaceInfo result) {
		currentActivity.updateUI(result);
//		(new LoadImageTask(currentActivity)).execute(result.image);
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
	
	private PlaceInfo download(String url){
		HttpClient client = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response;
		try{
			response = client.execute(httpget);
			HttpEntity entity = response.getEntity();
			
			//need to be edited
			JSONObject place = new JSONObject(
					this.streamToString(entity.getContent()));
			return new PlaceInfo(
					place.getString(NAME), place.getString(ADDRESS),
					place.getString(IMAGE), place.getString(INFO));
		}catch(JSONException e){
			Log.e(TAG, "JSONException " + e);
		}catch(IOException e){
			Log.e(TAG, "IOException " + e);			
		}
		return null;
	}
}
