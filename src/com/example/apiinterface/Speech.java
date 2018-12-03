package com.example.apiinterface;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Speech extends Thread {
	private String word;
	private Handler handler;
	private final String URL="http://dict.youdao.com/speech?audio=";
    public Speech(String word,Handler handler){
    	this.word=word;
    	this.handler=handler;
    }
	@Override
	public void run() {
		try {
			java.net.URL url=new java.net.URL(URL+word);
			HttpURLConnection con=(HttpURLConnection) url.openConnection();
			con.connect();
			InputStream stream=con.getInputStream();
			byte buffer[]=new byte[1024*10];
			stream.read(buffer);
			Bundle bundle=new Bundle();
			bundle.putByteArray("speech", buffer);
			Message message=new Message();
			message.what=MessageEnum.MSG_SPEECH_SUCCESS;
			message.setData(bundle);
			handler.sendMessage(message);
			con.disconnect();
		} catch (Exception e) {
		    Log.e("net error", "speech");
		    handler.sendEmptyMessage(MessageEnum.MSG_SPEECH_FAIL);
		}
	}
    
}
