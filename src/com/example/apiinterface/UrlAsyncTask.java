package com.example.apiinterface;

import java.io.InputStream;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.ArrayList;

import android.os.AsyncTask;

public class UrlAsyncTask extends AsyncTask<String,Integer,Object>{

	@Override
	protected Object doInBackground(String... params) {
		try {
			URL url=new URL(params[0]);
			HttpURLConnection connection=(HttpURLConnection) url.openConnection();
			connection.setRequestProperty("charset","utf8");
			ArrayList<Byte>list=new ArrayList<>();
			connection.setConnectTimeout(10000);
			connection.setFollowRedirects(true);
			connection.setDefaultRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.62 Safari/537.36");
			connection.connect();
			InputStream stream=connection.getInputStream();
			int i;
			while((i=stream.read())!=-1){
				list.add((byte) i);
			}
			stream.close();
			connection.disconnect();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}

	


	

}
