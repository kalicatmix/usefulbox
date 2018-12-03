package com.example.parser;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.entity.Word;

public class WordParser implements Parser{
    private String items[]=
    {"translation",
     "basic",
     "us-phonetic",
     "uk-phonetic",
     "explains"
    };
    private Word word;
    private String json;
    public WordParser(String json){
    	this.json=json;
    }
	@Override
	public Object getDataObject() {
		parse();
		return word;
	}
	private void parse(){
		word=new Word();
		try {
			JSONObject obj=new JSONObject(json);
			word.setTranslation(obj.getString(items[0]).replace("[","").replace("]","").replace("\"",""));
			obj=obj.getJSONObject(items[1]);
			word.setUs_phonetic(obj.getString(items[2]));
			word.setUk_phonetic(obj.getString(items[3]));
			word.setExplains(obj.getString(items[4]).replace("[","").replace("]","").replace("\"",""));
		} catch (JSONException e) {
			word=null;
		}
		
	}

}
