package com.example.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.util.Log;

public class VIDParser {
  private String html;
  public VIDParser(String html) {
	this.html=html;
}
   public String parsedString(){
	try{
		int i=html.indexOf("(base64decode(\"");
		int j=html.indexOf("\"))+'';");
		Log.e("err",html);
		Log.e("vid",html.substring(i, j).replace("(base64decode(\"",""));
		return html.substring(i, j).replace("(base64decode(\"","");
	}catch(Exception e){
		e.printStackTrace();
		Log.e("err",html);
		return null;
	}
   
   }
  
}
