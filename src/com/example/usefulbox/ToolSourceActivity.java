package com.example.usefulbox;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Member;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HandshakeCompletedListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.example.apiinterface.MessageEnum;
import com.example.apiinterface.UrlAsyncTask;
import com.example.apiinterface.UrlPostAsyncTask;
import com.example.browser.MyWebViewClient;
import com.example.parser.M3U8Parser;
import com.example.util.ArrayListToByteArray;
import com.example.util.Base64AndURlDecoder;
import com.example.util.VID;
import com.example.util.VIDParser;
import com.example.util.VideoItemHtmlParser;
import com.example.util.VideoNameHtmlParser;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.ScaleAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ToolSourceActivity extends Activity implements Handler.Callback{
	private ListView list;
    private String items[]={"百度云搜索","电影搜索","小说搜索","音乐搜索","电影电视剧资源"};
    private int drawable=R.drawable.tb_resource;
    private PopupWindow popupWindow;
    private EditText text;
    private Intent intent;
    private int TYPE_VIDEO=-1;
    private int url_id=0;
    private Handler handler;
    private String urls[]={"http://m.panduoduo.net/s/name/","http://m.pipi.cn/search.html?q=","http://m.biqukan.com/s.php?q=","https://music.163.com/#/search/m/?s="};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar bar=getActionBar();
		bar.setTitle("资源搜索工具");
		setContentView(R.layout.tool_source);
		intent=new Intent();
		intent.setAction(android.content.Intent.ACTION_VIEW);
		popupWindow=new PopupWindow(this);

		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup));
		popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		handler=new Handler(this);
		View contentView=getLayoutInflater().inflate(R.layout.search_popup,null);
		text=(EditText)contentView.findViewById(R.id.search_input);
		ImageView image=(ImageView)contentView.findViewById(R.id.search_img);
		text.setWidth(getWindowManager().getDefaultDisplay().getWidth()/5*4);
		image.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String padding=text.getText().toString();
				if(url_id>=0){
				intent.setData(Uri.parse(urls[url_id]+padding));
				startActivity(intent);
				}else{
					if(url_id==TYPE_VIDEO){
						initMiniVideo(padding);
					}
					
				}
			}
		});
		popupWindow.setContentView(contentView);
		popupWindow.setFocusable(true);
		popupWindow.setAnimationStyle(android.R.anim.cycle_interpolator);
		
		list=(ListView)findViewById(R.id.source_list);
		List<HashMap<String,Object>>data=new ArrayList<>();
		for(int i=0;i<items.length;i++){
			HashMap<String,Object>map=new HashMap<>();
			map.put("title",items[i]);
			map.put("img", drawable);
			data.add(map);
		}
		SimpleAdapter adapter=new SimpleAdapter(ToolSourceActivity.this,data,R.layout.tool_list_item,new String[]{"title","img"},new int[]{R.id.tool_title,R.id.tool_img});
	    list.setAdapter(adapter);
	    list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				   if(position==0)url_id=0;
				   else if(position==1)url_id=1;
				   else if(position==2)url_id=2;
				   else if(position==3)url_id=3;
				   else if(position==4)url_id=TYPE_VIDEO;
				    ScaleAnimation animation=new ScaleAnimation(1,1.1f,1,1.1f);
			        animation.setDuration(500);
				    view.startAnimation(animation);
				    popupWindow.showAtLocation(list, Gravity.CENTER,0,0);
				   
			}
		});
	}
	
	
	private ProgressDialog mini_progress;
	private ListView name_list,item_list;
	private AlertDialog name_list_dialog,item_list_dialog,item_player;
	private String formdata;
	private ArrayList<String>mini_names,mini_urls;
	private String item_url,item_url_vid;
	private ArrayList<String>item_names,item_urls;
	private String item_vid;
	private WebView mini_player;
	private String VID,VID_PlAYER_URL;
	public void initMiniVideo(String form){
	this.formdata=form;
	mini_progress=new ProgressDialog(this);
	mini_progress.setCancelable(false);
	mini_progress.setTitle("加载中");
	View name_view=getLayoutInflater().inflate(R.layout.mini_video,null,false);
	View item_view=getLayoutInflater().inflate(R.layout.mini_video_item,null,false);
	View video_view=getLayoutInflater().inflate(R.layout.mini_player,null,false);
	name_list=(ListView) name_view.findViewById(R.id.mini_video_list);
	item_list=(ListView)item_view.findViewById(R.id.mini_video_item_list);
	mini_player=(WebView)video_view.findViewById(R.id.mini_videoplayer);
	mini_player.setWebViewClient(new MyWebViewClient());
	WebSettings setting=mini_player.getSettings();
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
	
	AlertDialog.Builder builder=new AlertDialog.Builder(this);
	name_list_dialog=builder.setTitle("请选择名字").setView(name_view).setPositiveButton("关闭",new AlertDialog.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
		 name_list_dialog.dismiss();
		}
	}).setCancelable(false).create();
	item_list_dialog=builder.setTitle("请选择剧集").setView(item_view).setPositiveButton("关闭",new AlertDialog.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
		item_list_dialog.dismiss();
		}
	}).setCancelable(false).create();
	item_player=builder.setTitle("观看中").setNegativeButton("关闭",new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
		item_player.dismiss();	
		}
	}).setPositiveButton("浏览器打开",new OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
		 Intent intent=new Intent();
		 intent.setAction(android.content.Intent.ACTION_VIEW);
		 intent.setData(Uri.parse(VID_PlAYER_URL));
		 startActivity(intent);
		}
	}).setView(video_view).setCancelable(false).create();
	mini_progress.show();
	new Thread(new Runnable() {
		
		@Override
		public void run() {
			try{
		    String url="http://www.76mao.com/Search.asp";
		    String data="keyword="+URLEncoder.encode(formdata,"gbk")+"&Submit=%CB%D1%CB%F7";
		    ArrayList list=(ArrayList)(new UrlPostAsyncTask(data).execute(url).get());
		    ArrayList<String>html=new ArrayList<>();
		   /*
		    * 字符串太长，丢失部分数据
		    */
		    for(int i=0;i<list.size()/1024;i++){
		     byte[]buffer=ArrayListToByteArray.listTobyteArray(list.subList(i*1024,(i+1)*1024));
		     String temp=new String(buffer,"gbk");
		     if(temp.contains("mvinfo")){
		    	html.add(temp);
		     }
			}
		   VideoNameHtmlParser parser=new VideoNameHtmlParser(html);
		   parser.parse();
		   mini_names=parser.getNames();
		   mini_urls=parser.getUrls();
		   handler.sendEmptyMessage(MessageEnum.MSG_MINIVEDEO_SUCCESS);
			}catch(Exception e){
				e.printStackTrace();
				handler.sendEmptyMessage(MessageEnum.MSG_MINIVEDEO_ERROR);
			}
		}
	}).start();
	
	}
	@Override
	public boolean handleMessage(Message msg) {
		if(msg.what==MessageEnum.MSG_MINIVEDEO_SUCCESS){
			ArrayAdapter adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,mini_names);
			name_list.setAdapter(adapter);
			name_list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long id) {
					item_url=mini_urls.get(position);
					mini_progress.show();
					new Thread(new Runnable() {
						
						@Override
						public void run() {
						try{
							ArrayList list=(ArrayList) new UrlAsyncTask().execute(item_url).get();
							 ArrayList<String>html=new ArrayList<>();
							   /*
							    * 字符串太长，丢失部分数据
							    */
							    for(int i=0;i<list.size()/1024;i++){
							     byte[]buffer=ArrayListToByteArray.listTobyteArray(list.subList(i*1024,(i+1)*1024));
							     String temp=new String(buffer,"gbk");
							     if(temp.contains("<a")){
							    	html.add(temp);
							
							     }
								}
							     VideoItemHtmlParser parser=new VideoItemHtmlParser(html);
								   parser.parse();
								   item_names=parser.getNames();
								   item_urls=parser.getUrls();
							
							handler.sendEmptyMessage(MessageEnum.MSG_MINIVEDEO_ITEM_SUCCESS);
						}catch(Exception e){
							e.printStackTrace();
							handler.sendEmptyMessage(MessageEnum.MSG_MINIVEDEO_ITEM_ERROR);
						}
							
						}
					}).start();
				}
			});	
			mini_progress.dismiss();
			name_list_dialog.show();
		}else if(msg.what==MessageEnum.MSG_MINIVEDEO_ERROR){
			Toast.makeText(this, "获取失败",2000).show();
			mini_progress.dismiss();
		}else if(msg.what==MessageEnum.MSG_MINIVEDEO_ITEM_SUCCESS){
			mini_progress.dismiss();
			ArrayAdapter adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,item_names);
			item_list.setAdapter(adapter);
			
			
			item_list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long id) {
					item_url_vid=item_urls.get(position);
					/*
					mini_player.loadUrl(item_url_vid);
					item_player.show();
				    */
					new Thread(new Runnable() {
						
						@Override
						public void run() {
						try{
							ArrayList list=(ArrayList) new UrlAsyncTask().execute(item_url_vid).get();
							Log.e("size",list.size()+"");
							    ArrayList<String>html=new ArrayList<>();
							     byte[]buffer=ArrayListToByteArray.listTobyteArray(list.subList(3000,list.size()-1));
							     String temp=new String(buffer,"gbk");
							     temp=new VIDParser(temp).parsedString();
							     temp=Base64AndURlDecoder.decode(temp);
							     VID=new VID(temp).getVid();
							    ArrayList vid_json_list=(ArrayList) new UrlPostAsyncTask("id="+VID).execute("http://cdn.76long.com/cs1/url.php").get();
							    String vid_json=new String(ArrayListToByteArray.listTobyteArray(vid_json_list));
							    String type_url[]=new M3U8Parser(vid_json).getTypeAndUrl();
							    VID_PlAYER_URL=type_url[1];
							handler.sendEmptyMessage(MessageEnum.MSG_MINIVEDEO_ITEM_VID_SUCCESS);
						}catch(Exception e){
							e.printStackTrace();
							handler.sendEmptyMessage(MessageEnum.MSG_MINIVEDEO_ITEM_VID_ERROR);
						}
							
						}
					}).start();
					
					
				}
			}
			);
			item_list_dialog.show();
		}else if(msg.what==MessageEnum.MSG_MINIVEDEO_ITEM_ERROR){
			Toast.makeText(this, "获取失败",2000).show();
			mini_progress.dismiss();
			
		}else if(msg.what==MessageEnum.MSG_MINIVEDEO_ITEM_VID_SUCCESS){
			mini_progress.dismiss();
			mini_player.loadUrl(VID_PlAYER_URL);
			item_player.show();
		}else if(msg.what==MessageEnum.MSG_MINIVEDEO_ITEM_VID_ERROR){
			Toast.makeText(this, "获取失败",2000).show();
			mini_progress.dismiss();
		}
		return false;
	}
}
