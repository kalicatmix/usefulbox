package com.example.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherParser implements Parser {
	private String json;
	private String date="date",high="high",low="low",type="type";
    public WeatherParser(String json) {
		this.json=json;
	}
	@Override
	public Object getDataObject() {
		ArrayList<String>list=new ArrayList<>();
		try{
			JSONObject obj=new JSONObject(json).getJSONObject("data");
		    list.add(obj.getString("city"));
		    list.add(obj.getString("ganmao"));
		    JSONObject yesterday=obj.getJSONObject("yesterday");
		     String temp=yesterday.getString(date)+" "+yesterday.getString(high)+" "+yesterday.getString(low)+" "+yesterday.getString(type);
		     list.add(temp);
		    JSONArray array=obj.getJSONArray("forecast");
		    for(int i=0;i<array.length();i++){
		    	JSONObject day=array.getJSONObject(i);
		    	temp=day.getString(date)+" "+day.getString(high)+" "+day.getString(low)+" "+day.getString(type);
		        list.add(temp);
		    }
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return list;
	}

}
