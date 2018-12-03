package com.example.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class PoemParser implements Parser {
	private String json;
    public PoemParser(String json) {
		this.json=json;
	}
	@Override
	public Object getDataObject() {
		ArrayList<String>list=new ArrayList<>();
		try{
			JSONObject obj=new JSONObject(json);
			obj=obj.getJSONArray("result").getJSONObject(0);
			list.add(obj.getString("title"));
			list.add(obj.getString("authors"));
			list.add(obj.getString("content"));
		}catch(Exception e){
			return null;
		}
		return list;
	}

}
