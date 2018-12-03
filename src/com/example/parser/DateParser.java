package com.example.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class DateParser implements Parser {
    private String json; 
	public DateParser(String json) {
		this.json=json;
	}
	@Override
	public Object getDataObject() {
		ArrayList<String>list=new ArrayList<>();
		try{
			JSONObject obj=new JSONObject(json);
			obj=obj.getJSONArray("data").getJSONObject(0);
			JSONArray array=obj.getJSONArray("almanac");
			for(int i=0;i<array.length();i++){
				obj=array.getJSONObject(i);
				String temp="";
				temp+="ÈÕÆÚ£º"+obj.getString("date")+"\n";
				temp+="½ûÖ¹£º"+obj.getString("avoid")+"\n";
				temp+="ÔÊÐí£º"+obj.getString("suit")+"\n";
				list.add(temp);
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return list;
	}

}
