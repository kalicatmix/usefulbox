package com.example.util;

import java.net.URLDecoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class VID {
  private String html;
  public VID(String html) {
	this.html=html;
}
  public String getVid(){
	   Document doc=Jsoup.parse(html);
	   String src=doc.select("iframe").attr("src");
	   int begin=src.indexOf("?u=");
	   int end=src.indexOf("=&m");
	   try{
		   src=src.substring(begin+3,end);
	   }catch(Exception e){
		   return null;
	   }
	  return src;
  }
}
