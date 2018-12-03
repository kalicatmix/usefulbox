package com.example.parser;

import java.util.ArrayList;

import org.json.JSONObject;

public class PhoneParser implements Parser {
   private String json;
   public PhoneParser(String json) {
	this.json=json;
}
	@Override
	public Object getDataObject() {
		ArrayList<String>list=new ArrayList<>();
		try{
			JSONObject obj=new JSONObject(json);
			list.add("前缀: "+obj.getString("mts"));
			list.add("省市: "+obj.getString("province"));
			list.add("服务商: "+obj.getString("catName"));
		}catch(Exception e){
			return null;
		}
		return list;
	}

}
