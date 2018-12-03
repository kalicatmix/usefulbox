package com.example.util;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

public class VideoNameHtmlParser {
	private final String URL="http://www.76mao.com";
	private ArrayList<String>html;
	private ArrayList<String>urls,names;
     public VideoNameHtmlParser(ArrayList<String>html) {
	  this.html=html;
	  urls=new ArrayList<>();
	  names=new ArrayList<>();
   }
     public void parse(){
    	for(int i=0;i<html.size();i++){
    	 Document doc=Jsoup.parse(html.get(i));
    	 Elements elements=doc.select(".mvname");
    	 Element element=elements.get(0);
    	 urls.add(URL+element.attr("href"));
    	 names.add(element.attr("title"));
    	} 	 
     }
     public ArrayList<String> getUrls(){
    	 return urls;
     }
     public ArrayList<String> getNames(){
    	 return names;
     }
     
     
}
