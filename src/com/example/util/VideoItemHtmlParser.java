package com.example.util;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class VideoItemHtmlParser {
	private final String URL="http://www.76mao.com";
	private ArrayList<String>html;
	private ArrayList<String>urls,names;
     public VideoItemHtmlParser(ArrayList<String>html) {
	  this.html=html;
	  urls=new ArrayList<>();
	  names=new ArrayList<>();
   }
     public void parse(){
    	for(int i=0;i<html.size();i++){
    	 Document doc=Jsoup.parse(html.get(i));
    	 Elements elements=doc.select("a");
    	 for(int j=0;j<elements.size();j++){
    	 Element element=elements.get(j);
    	 String name=element.attr("title");
    	 if(!name.trim().equals("")){
    	 String url=findUrl(element.attr("href"));
    	 if(url!=null){
    	 urls.add(URL+url);
    	 names.add(name);
    	 }
    	 }else{
    		name=element.text();
    		if(!name.trim().equals("")){
    			 String url=findUrl(element.attr("href"));
    	    	 if(url!=null){
    	    	 urls.add(URL+url);
    	    	 names.add(name);
    	    	 }	
    		}
    		 
    	 }
    	 }
    	} 	 
     }
     public ArrayList<String> getUrls(){
    	 return urls;
     }
     public ArrayList<String> getNames(){
    	 return names;
     }
     private boolean checkSame(String name){
    	 for(int i=0;i<names.size();i++){
    		if(names.get(i).equals(name)){
    			return true;
    		}
    	 }
    	 if(name.trim().equals("")){
    		 return false;
    	 }
    	 return false;
     }
     private String findUrl(String url){
    	 if(url.contains("javascript:OpenWindow3")){
    	 return url.replace("javascript:OpenWindow3('","").replace("',window)","");
    	 }else{
    		 return null;
    	 }
    	 }
}
