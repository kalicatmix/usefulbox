package com.example.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.util.JsonWriter;

public class JsonParser {
	private String json;
	private String names[];
	private final int JSON_ARRAY=0x00;
	private final int JSON_OBJECT=0x01;
	private final int JSON_STRINGFY=0x02;
	private final int KEY_UNKOWN=0x03;
   public JsonParser(String json,String[]names){
	   this.json=json;
	   this.names=names;
   }
   public HashMap<String,ArrayList> parsedData(){
	   HashMap<String,ArrayList>map=new HashMap<>();
	   try {
		JSONObject obj=new JSONObject(json);
		for(int i=0;i<names.length;i++){
			int type=checkType(obj,names[i]);
			if(type==KEY_UNKOWN){
				map.put(names[i],null);
			}else{
				ArrayList<String>list=new ArrayList<>();
				if(type==JSON_STRINGFY){
					list.add(obj.getString(names[i]));
				}else if(type==JSON_ARRAY){
				 JSONArray array=obj.getJSONArray(names[i]);
				 for(int j=0;j<array.length();j++){
					 list.add(array.getString(j));
				 }
				}else if(type==JSON_OBJECT){
					JSONObject object=obj.getJSONObject(names[i]);
				    Iterator<String>keys=object.keys();
				    while(keys.hasNext()){
				    	list.add(object.getString(keys.next()));
				    }
				}	
				
				map.put(names[i],list);
			}
		}
		return map;
		
	} catch (JSONException e) {
		e.printStackTrace();
		return null;
	}
   }
   public int checkType(JSONObject obj,String key){
	   int type=-1;
	   try{
		   obj.get(key);
	   }catch(Exception e){
		   return KEY_UNKOWN;
	   }
	   try{
		   obj.getJSONArray(key).get(0);
		   type=JSON_ARRAY;
	   }catch(Exception e){
			   try{
				  if(null!=obj.getJSONObject(key)){
					type=JSON_OBJECT;  
				  }else{
					  type=JSON_STRINGFY;
				  }
			   }catch(Exception e2){
				   
				   type=JSON_STRINGFY;
			   }
		   
	   }
	   return type;
   }
}
