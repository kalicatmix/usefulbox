package com.example.usefulbox;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.example.apiinterface.MessageEnum;
import com.example.apiinterface.UrlAsyncTask;
import com.example.browser.MyWebViewClient;
import com.example.parser.DateParser;
import com.example.parser.ExPressParser;
import com.example.parser.IdCardParser;
import com.example.parser.PhoneParser;
import com.example.parser.WeatherParser;
import com.example.util.ArrayListToByteArray;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.YuvImage;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ToolLifeFunctionActivity extends Activity implements Handler.Callback{
	private Handler hander;
	private AlertDialog dialog;
	private AlertDialog.Builder builder;
	private WebView webview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		hander=new Handler(this);
		String name=getIntent().getStringExtra("name");
		int type=getIntent().getIntExtra("type",-1);
		getActionBar().setTitle(name);
		builder=new AlertDialog.Builder(this);
		if(type==ToolLifeActivity.TYPE_VIDEO){
		   setContentView(R.layout.life_video);
		   initVideo();
		}
		if(type==ToolLifeActivity.TYPE_EXPRESS){
			setContentView(R.layout.search_result_dialog);
			initExpress();
		}
		if(type==ToolLifeActivity.TYPE_WEATHER){
			setContentView(R.layout.search_result_dialog);
			initWeather();
		}
		if(type==ToolLifeActivity.TYPE_PHONE){
			setContentView(R.layout.search_result_dialog);
			initPhone();
		}
		if(type==ToolLifeActivity.TYPE_IDCARD){
			setContentView(R.layout.search_result_dialog);
			initIdCard();
		}
		if(type==ToolLifeActivity.TYPE_Calendar){
			setContentView(R.layout.date_dialog);
			initCalendar();
		}
	}
	private String video_url[]={
			"http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8",
			"http://ivi.bupt.edu.cn/hls/cctv3hd.m3u8",
			"http://ivi.bupt.edu.cn/hls/cctv5hd.m3u8",
			"http://ivi.bupt.edu.cn/hls/cctv6hd.m3u8",
			"http://ivi.bupt.edu.cn/hls/cctv8hd.m3u8",
			"http://ivi.bupt.edu.cn/hls/btv1hd.m3u8",
			"http://ivi.bupt.edu.cn/hls/hunanhd.m3u8",
			"http://ivi.bupt.edu.cn/hls/zjhd.m3u8",
			"http://ivi.bupt.edu.cn/hls/jshd.m3u8",
			"http://ivi.bupt.edu.cn/hls/dfhd.m3u8",
			"http://ivi.bupt.edu.cn/hls/ahhd.m3u8",
			"http://ivi.bupt.edu.cn/hls/lnhd.m3u8",
			"http://ivi.bupt.edu.cn/hls/hbhd.m3u8",
			"http://ivi.bupt.edu.cn/hls/sdhd.m3u8",
			"http://ivi.bupt.edu.cn/hls/cqtv.m3u8",
			"http://dlhls.cdn.zhanqi.tv/zqlive/7032_0s2qn.m3u8",
			"http://dlhls.cdn.zhanqi.tv/zqlive/35180_KUDhx.m3u8",
			"http://dlhls.cdn.zhanqi.tv/zqlive/80918_cr4oy.m3u8",
	};
	private String channel[]={
			"CCTV-1",
			"CCTV-3",
			"CCTV-5",
			"CCTV-6",
			"CCTV-8",
			"北京卫视",
			"湖南卫视",
			"浙江卫视",
			"江苏卫视",
			"东方卫视",
			"安徽卫视",
			"辽宁卫视",
			"湖北卫视",
			"山东卫视",
			"重庆卫视",
			"大片直播",
			"最新电影",
			"老片播放",
	};
	public void initVideo(){
		
		builder=builder.setCancelable(false).setPositiveButton("关闭", new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				dialog.dismiss();
			}
		});
		View view=getLayoutInflater().inflate(R.layout.video_player,null,false);
		LinearLayout layout=(LinearLayout)view.findViewById(R.id.code_browser_parent);
	    Display display=getWindowManager().getDefaultDisplay();
	    if(display!=null&&layout!=null)
		layout.setLayoutParams(new LinearLayout.LayoutParams(display.getWidth(),display.getHeight()));
		webview=(WebView)view.findViewById(R.id.videoplayer);
		WebSettings setting=webview.getSettings();
		setting.setAllowContentAccess(true);
	    setting.setAllowFileAccess(true);
	    setting.setAppCacheEnabled(false);
	    setting.setBuiltInZoomControls(false);
	    setting.setJavaScriptEnabled(true);
	    setting.setJavaScriptCanOpenWindowsAutomatically(true);
	    setting.setSupportMultipleWindows(true);
	    setting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
	    setting.setAppCacheMaxSize(1024*5);
	    setting.setBlockNetworkImage(false);
	    setting.setUseWideViewPort(true);
	    setting.setLoadsImagesAutomatically(true);
	    setting.setDomStorageEnabled(true);
	    setting.setGeolocationEnabled(true);
	    setting.setLoadWithOverviewMode(true);
		webview.setWebViewClient(new MyWebViewClient());
		dialog=builder.setView(view).create();
		ArrayAdapter<String>adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,channel);
		ListView list=(ListView) findViewById(R.id.list_video);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				dialog.setTitle(channel[arg2]);
				webview.loadUrl(video_url[arg2]);
				dialog.show();
			}
		});
	}
	
	
	
	private EditText express_input;
	private TextView express_result;
	private String express_url="https://api.apiopen.top/EmailSearch?number=";
	public void initExpress(){
		express_input=(EditText) findViewById(R.id.search_rersult_input);
		express_result=(TextView)findViewById(R.id.search_result_result);
		Button btn=(Button)findViewById(R.id.search_result_btn);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				express_url+=express_input.getText().toString();
				new Thread(new Runnable() {
					
					@Override
					public void run() {
					try {
						Message message=new Message();
						message.what=MessageEnum.MSG_EXPRESS_SUCCESS;
						ArrayList list=(ArrayList)new UrlAsyncTask().execute(express_url).get();
						String result=new String(ArrayListToByteArray.listTobyteArray(list));
					    list=(ArrayList) new ExPressParser(result).getDataObject();
					    result="";
					    for(int i=0;i<list.size();i++){
					    	result+=list.get(i)+"\n";
					    }
						Bundle bundle=new Bundle();
					    bundle.putString("result", result);
						message.setData(bundle); 
					    hander.sendMessage(message);
					} catch (Exception e) {
						e.printStackTrace();
						hander.sendEmptyMessage(MessageEnum.MSG_EXPRESS_FAIL);
					}
					
					}
				}).start();
			}
		});
	}
	
	private EditText weather_input;
	private TextView weather_result;
	private String weather_url="https://www.apiopen.top/weatherApi?city=";
	private String weather_city;
	private void initWeather(){
		weather_input=(EditText) findViewById(R.id.search_rersult_input);
		weather_result=(TextView)findViewById(R.id.search_result_result);
		Button btn=(Button)findViewById(R.id.search_result_btn);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				weather_city=weather_input.getText().toString();
				new Thread(new Runnable() {
					
					@Override
					public void run() {
					try {
						weather_url=weather_url+URLEncoder.encode(weather_city,"utf8");
						Message message=new Message();
						message.what=MessageEnum.MSG_WEATHER_SUCCESS;
						
						ArrayList list=(ArrayList)new UrlAsyncTask().execute(weather_url).get();
						String result=new String(ArrayListToByteArray.listTobyteArray(list));
						list=(ArrayList) new WeatherParser(result).getDataObject();
					    result="";
					    for(int i=0;i<list.size();i++){
					    	result+=list.get(i)+"\n";
					    }
						Bundle bundle=new Bundle();
					    bundle.putString("result", result);
						message.setData(bundle); 
					    hander.sendMessage(message);
					} catch (Exception e) {
						e.printStackTrace();
						hander.sendEmptyMessage(MessageEnum.MSG_WEATHER_ERROR);
					}
					
					}
				}).start();
			}
		});
	}
	private EditText phone_input;
	private TextView phone_result;
	private String phone_url="https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=";
	private String phone_number;
     private void initPhone(){
    	 phone_input=(EditText) findViewById(R.id.search_rersult_input);
 		phone_result=(TextView)findViewById(R.id.search_result_result);
 		Button btn=(Button)findViewById(R.id.search_result_btn);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				phone_number=phone_input.getText().toString();
				new Thread(new Runnable() {
					
					@Override
					public void run() {
					try {
						String url=phone_url+URLEncoder.encode(phone_number,"utf8");
						Message message=new Message();
						message.what=MessageEnum.MSG_PHONE_SUCCESS;
						ArrayList list=(ArrayList)new UrlAsyncTask().execute(url).get();
						String result=new String(ArrayListToByteArray.listTobyteArray(list),"gbk");
						result=result.replace("__GetZoneResult_ = ","");
						list=(ArrayList) new PhoneParser(result).getDataObject();
					    result="";
					    if(list!=null)
					    for(int i=0;i<list.size();i++){
					    	result+=list.get(i)+"\n";
					    }  
						Bundle bundle=new Bundle();
					    bundle.putString("result", result);
						message.setData(bundle); 
					    hander.sendMessage(message);
					} catch (Exception e) {
						e.printStackTrace();
						hander.sendEmptyMessage(MessageEnum.MSG_PHONE_ERROR);
					}
					
					}
				}).start();
				
			}
		});
	}
     
     private EditText card_input;
 	 private TextView card_result;
 	 private String card_url="http://api.djapi.cn/idcode/get?token={更改为你的token}&datatype=json&idnum=";
 	 private String card_number;
     public void initIdCard(){
    	 
    	 card_input=(EditText) findViewById(R.id.search_rersult_input);
  		 card_result=(TextView)findViewById(R.id.search_result_result);
  		Button btn=(Button)findViewById(R.id.search_result_btn);
 		btn.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				card_number=card_input.getText().toString();
 				new Thread(new Runnable() {
 					
 					@Override
 					public void run() {
 					try {
 						String url=card_url+URLEncoder.encode(card_number,"utf8");
 						Message message=new Message();
 						message.what=MessageEnum.MSG_CARD_SUCCESS;
 						ArrayList list=(ArrayList)new UrlAsyncTask().execute(url).get();
 						String result=new String(ArrayListToByteArray.listTobyteArray(list),"utf8");
 						IdCardParser parser=new IdCardParser(result);
 						if(!parser.isError()){
 					    list=(ArrayList)parser.getDataObject();
 					    result="";
 					    if(list!=null)
 					    for(int i=0;i<list.size();i++){
 					    	result+=list.get(i)+"\n";
 					    } 
 						Bundle bundle=new Bundle();
 					    bundle.putString("result", result);
 						message.setData(bundle); 
 					    hander.sendMessage(message);
 						}else{
 							hander.sendEmptyMessage(MessageEnum.MSG_CARD_ERROR);
 						}
 					} catch (Exception e) {
 						e.printStackTrace();
 						hander.sendEmptyMessage(MessageEnum.MSG_CARD_ERROR);
 					}
 					
 					}
 				}).start();
 				
 			}
 		});
 
     }
      
     private EditText date_year;
     private EditText date_month;
     private String date_url="https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6018&format=json&query=";
     private TextView date_result;
     private void initCalendar(){
        date_year=(EditText) findViewById(R.id.year_input);
        date_month=(EditText)findViewById(R.id.month_input);
        date_result=(TextView)findViewById(R.id.date_result);
        Button btn=(Button)findViewById(R.id.date_btn);
    	btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
					String year=date_year.getText().toString();
					String month=date_month.getText().toString();
					String url=date_url+year+"-"+month;
					try {
 						Message message=new Message();
 						message.what=MessageEnum.MSG_DATE_SUCCESS;
 						ArrayList list=(ArrayList)new UrlAsyncTask().execute(url).get();
 						String result=new String(ArrayListToByteArray.listTobyteArray(list),"gbk");
 						DateParser parser=new DateParser(result);
 					    list=(ArrayList)parser.getDataObject();
 					    result="";
 					    if(list!=null)
 					    for(int i=0;i<list.size();i++){
 					    	result+=list.get(i)+"\n";
 					    }
 						Bundle bundle=new Bundle();
 					    bundle.putString("result", result);
 						message.setData(bundle); 
 					    hander.sendMessage(message);
 					} catch (Exception e) {
 						e.printStackTrace();
 						hander.sendEmptyMessage(MessageEnum.MSG_DATE_ERROR);
 					} 	
					
					}
				}).start();
				
			}
		});
     }
	@Override
	public boolean handleMessage(Message arg0) {
        if(arg0.what==MessageEnum.MSG_EXPRESS_FAIL){
        express_result.setText("未找到结果");	
        express_url="https://api.apiopen.top/EmailSearch?number=";
        }else if(arg0.what==MessageEnum.MSG_EXPRESS_SUCCESS){
        express_result.setText(arg0.getData().getString("result"));	
        express_url="https://api.apiopen.top/EmailSearch?number=";
        }else if(arg0.what==MessageEnum.MSG_WEATHER_SUCCESS){
        	weather_result.setText(arg0.getData().getString("result"));
        	 weather_result.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_top));
        	 weather_url="https://www.apiopen.top/weatherApi?city=";
        }else if(arg0.what==MessageEnum.MSG_WEATHER_ERROR){
        	weather_result.setText("未找到结果");
        	weather_url="https://www.apiopen.top/weatherApi?city=";
        }else if(arg0.what==MessageEnum.MSG_PHONE_SUCCESS){
        	phone_result.setText(arg0.getData().getString("result"));
       	    phone_result.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_top));
        }else if(arg0.what==MessageEnum.MSG_PHONE_ERROR){
        	phone_result.setText("未找到结果");
        }else if(arg0.what==MessageEnum.MSG_CARD_SUCCESS){
        	card_result.setText(arg0.getData().getString("result"));
        	card_result.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_top));
        }else if(arg0.what==MessageEnum.MSG_CARD_ERROR){
        	card_result.setText("未找到结果");
        }else if(arg0.what==MessageEnum.MSG_DATE_SUCCESS){
        	date_result.setText(arg0.getData().getString("result"));
        	date_result.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_top));
        }else if(arg0.what==MessageEnum.MSG_DATE_ERROR){
        	date_result.setText("未找到结果");
        }
       
		return false;
	}
	

}
