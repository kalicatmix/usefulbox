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
			list.add("���ң�"+array.getString(0));
			list.add("������"+array.getString(1));
			list.add("���У�"+array.getString(2));
			list.add("��Ӫ�̣�"+array.getString(4));			
		}catch(Exception e){
			return null;
		}
		return list;
	}

}
