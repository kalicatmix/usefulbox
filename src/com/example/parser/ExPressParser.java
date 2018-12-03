package com.example.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class ExPressParser implements Parser {
    private String json;
    public ExPressParser(String json) {
		this.json=json;
	}
	@Override
	public Object getDataObject() {
		ArrayList<String>list=new ArrayList<>();
		try{
			JSONObject obj=new JSONObject(json).getJSONObject("result");
			JSONArray data=obj.getJSONArray("data");
			for(int i=0;i<data.length();i++){
				JSONObject obj_temp=data.getJSONObject(i);
				String temp=obj_temp.getString("time");
			    temp +=" "+obj_temp.getString("context");
			    temp +="  "+obj_temp.getString("location");
			    list.add(temp);
			}
			return list;
		}catch(Exception e){
			
		}
		return null;
	}

}
