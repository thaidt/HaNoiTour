package com.example.hanoitours;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

public class GetImageTask extends AsyncTask <String, Integer, Drawable>{

	private String TAG ="GetPlaceInfoTask";
	
	private PlaceDetail currentActivity;
	
	public GetImageTask(PlaceDetail activity) {
		super();
		this.currentActivity = activity;
	}

	@Override
	protected Drawable doInBackground(String... arg0) {
		return download(arg0[0]);
	}
	
	@Override
	protected void onPostExecute(Drawable result) {
		currentActivity.updateImage(result);
//		(new LoadImageTask(currentActivity)).execute(result.image);
	}
	
	private Drawable download(String url){
		HttpClient client = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response;
		try{
			response = client.execute(httpget);
			HttpEntity entity = response.getEntity();
			
			//need to be edited
			
			Drawable image = Drawable.createFromStream(
					entity.getContent(),"image");
			return image;
		}catch(IOException e){
			Log.e(TAG, "IOException " + e);			
		}
		return null;
	}
}