package com.example.parser;

import java.util.ArrayList;

import org.json.JSONObject;

import android.util.Log;

public class IdCardParser implements Parser {
    private String json;
    private int error_code=0;
    public IdCardParser(String json) {
		this.json=json;
	}
	@Override
	public Object getDataObject() {
		ArrayList<String>list=new ArrayList<>();
		try{
			JSONObject obj=new JSONObject(json);
			error_code=obj.getInt("ErrorCode");
			obj=obj.getJSONObject("Result");			
			list.add("出生地: "+new String(obj.getString("CityName").getBytes(),"utf8"));
			list.add("星座: "+new String(obj.getString("XingZuo").getBytes(),"utf8"));
			list.add("出生年月日: "+new String(obj.getString("Birthday").getBytes(),"utf8"));
			list.add("年龄: "+new String(obj.getString("Years").getBytes(),"utf8"));
			list.add("性别: "+new String(obj.getString("Sex").getBytes(),"utf8"));
			
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return list;
	}
    public boolean isError(){
    	if(error_code==0)
    		return false;
    	else
    		return true;
    }
}
