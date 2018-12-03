package com.example.util;

import java.net.URLDecoder;

import android.util.Base64;
import android.util.Log;

public class Base64AndURlDecoder {

	public static String decode(String url){
		try{
		Log.e("url", URLDecoder.decode(new String(Base64.decode(url.getBytes(),Base64.DEFAULT),"gbk")));
		return URLDecoder.decode(new String(Base64.decode(url.getBytes(),Base64.DEFAULT),"gbk"));
		}catch(Exception e){
			return new String(Base64.decode(url.getBytes(), Base64.DEFAULT));
		}
		}
}
