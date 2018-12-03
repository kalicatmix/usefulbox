package com.example.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class IPParser implements Parser {
	private String json;
    public IPParser(String json) {
	this.json=json;
}
	@Override
	public Object getDataObject() {
		ArrayList<String>list=new ArrayList<>();
		try{
			JSONArray array=new JSONArray(json);
			list.add("国家："+array.getString(0));
			list.add("地区："+array.getString(1));
			list.add("城市："+array.getString(2));
			list.add("运营商："+array.getString(4));			
		}catch(Exception e){
			return null;
		}
		return list;
	}

}
