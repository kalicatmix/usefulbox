package com.example.usefulbox;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HandshakeCompletedListener;

import com.example.apiinterface.MessageEnum;
import com.example.apiinterface.UrlAsyncTask;
import com.example.entity.Word;
import com.example.parser.PoemParser;
import com.example.parser.WordParser;
import com.example.util.ArrayListToByteArray;
import com.example.util.JsonParser;

import android.R.anim;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.RenderScript.RSErrorHandler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ToolStudyFunctionActivity extends Activity implements Handler.Callback {
    private TextView result;
    private EditText search;
    private Button confirm;
    private String word;
    private String URL;
	private Handler hander;
	private Word wordobj;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		String name=getIntent().getStringExtra("name");
		int type=getIntent().getIntExtra("type",-1);
		hander=new Handler(this);
		getActionBar().setTitle(name);
		if(type==ToolStudyActivity.TYPE_WORD){
		setContentView(R.layout.word_search);
		result=(TextView)findViewById(R.id.word_result);
		search=(EditText)findViewById(R.id.word_search);
		URL="http://fanyi.youdao.com/openapi.do?keyfrom=youdao111&key=60638690&type=data&doctype=json&version=1.1&q=";
		confirm=(Button)findViewById(R.id.word_search_btn);
		initWord();
		}
		if(type==ToolStudyActivity.TYPE_POEM){
			setContentView(R.layout.word_search);
			result=(TextView)findViewById(R.id.word_result);
			search=(EditText)findViewById(R.id.word_search);
			confirm=(Button)findViewById(R.id.word_search_btn);
			URL="https://api.apiopen.top/searchPoetry?name=";
		   initPoem(); 
		}
		}
   public void initWord(){
	confirm.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			word=search.getText().toString();
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Object obj=new UrlAsyncTask().execute(URL+word).get();
					    ArrayList list=(ArrayList)obj;
					    if(list!=null){
					    byte[] buffer=ArrayListToByteArray.listTobyteArray(list);
					    String json=new String(buffer);
					    wordobj=(Word) new WordParser(json).getDataObject();
					    hander.sendEmptyMessage(MessageEnum.MSG_SPEECH_SUCCESS);
					    }
					    else{
					    	
					    }
					} catch (Exception e) {
						hander.sendEmptyMessage(MessageEnum.MSG_SPEECH_FAIL);
					}
					
				}
			}).start();
			
		}
	});
	     
   }
    String results;
   public void initPoem(){
	   confirm.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			word=search.getText().toString();
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Object obj=new UrlAsyncTask().execute(URL+URLEncoder.encode(word,"utf8")).get();
					    ArrayList list=(ArrayList)obj;
					    if(list!=null){
					    byte[] buffer=ArrayListToByteArray.listTobyteArray(list);
					    String  json=new String(buffer);
			            
					    list=(ArrayList) new PoemParser(json).getDataObject();
					    results="";
					    if(list !=null){
					    for(int i=0;i<list.size();i++){
					    	results+=list.get(i)+"\n";
					    }
					    }
					    Message message=new Message();
					    message.what=MessageEnum.MSG_POEM_SUCCESS;
					    Bundle bundle=new Bundle();
					    bundle.putString("result",results);
					    message.setData(bundle);
					    hander.sendMessage(message);
					    }
					    else{
					    hander.sendEmptyMessage(MessageEnum.MSG_POEM_ERROR)	;
					    }
					} catch (Exception e) {
					
						hander.sendEmptyMessage(MessageEnum.MSG_POEM_ERROR)	;
					}
					
				}
			}).start();
			
		}
	});
   }
	@Override
	public boolean handleMessage(Message arg0) {
		if(arg0.what==MessageEnum.MSG_SPEECH_SUCCESS){
			if(wordobj!=null){
			String res="汉语解释:  "+wordobj.getTranslation();
			res+="\n美式发音:  "+wordobj.getUs_phonetic();
			res+="\n英式发音:  "+wordobj.getUk_phonetic();
			res+="\n更多：  "+wordobj.getExplains();
			result.setText(res);
			}else{
				result.setText("未找到结果");
			}
		   
		}else if(arg0.what==MessageEnum.MSG_POEM_SUCCESS){
			result.setText(arg0.getData().getString("result"));
		}else if(arg0.what==MessageEnum.MSG_POEM_ERROR||arg0.what==MessageEnum.MSG_SPEECH_FAIL){
			result.setText("错误请求");
		}
		result.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
		
		return true;
	}
	
}
