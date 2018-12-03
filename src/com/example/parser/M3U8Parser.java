package com.example.parser;

import java.net.URLDecoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class M3U8Parser{
	 private String json;
	 public M3U8Parser(String json) {
		this.json=json;
	}
	 public String[] getTypeAndUrl(){
		 String temp[]=new String[2];
		 try {
			Log.e("err",json);
			JSONObject obj=new JSONObject(json);
			temp[0]=obj.getString("ext");
			temp[1]=URLDecoder.decode(obj.getString("url"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		 return temp;
	 }
}
