package com.example.usefulbox;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.client.utils.URLEncodedUtils;

import com.example.apiinterface.MessageEnum;
import com.example.apiinterface.UrlAsyncTask;
import com.example.apiinterface.UrlHttpsAsyncTask;
import com.example.browser.MyWebViewClient;
import com.example.parser.ExPressParser;
import com.example.parser.IPParser;
import com.example.util.ArrayListToByteArray;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ToolDevelopFunctionActivity extends Activity implements Handler.Callback {
	private Handler hander;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		String name=getIntent().getStringExtra("name");
		int type=getIntent().getIntExtra("type",-1);
		hander=new Handler(this);
		getActionBar().setTitle(name);
		if(type==ToolDevelopActivity.TYPE_CODE){
			setContentView(R.layout.code_language);	
			initCodeLanguage();
		}
		if(type==ToolDevelopActivity.TYPE_IP){
			setContentView(R.layout.search_result_dialog);
			initIP();
		}
		if(type==ToolDevelopActivity.TYPE_QRCODE){
			setContentView(R.layout.qrcode_layout);
			initQrCode();
		}
	}
	
	private String languages[]={
			"java",
			"python",
			"ruby",
			"go",
			"c",
			"c++",
			"perl",
			"php",
			"erlang",
			"dart",
			"lua",
			"scala",
			"groovy",
			"clojure",
			"haskell",
			"javascript",
			"c#",
			"f#",
			"vb",
			"swift",
			"objective-c",
			"R",
			"julia",
			"rust",
			"assemblely",
			"shell",
			"typescript"
			
	};
	
	private final String GITHUTAPI="https://github.com/search?o=desc&q=";
	private	final String TAG="&s=stars";
	private String request_url;
	private AlertDialog dialog;
	private AlertDialog.Builder builder;
	private WebView web;
	public void  initCodeLanguage(){
		View view=getLayoutInflater().inflate(R.layout.code_browser,null,false);
		web=(WebView)view.findViewById(R.id.codebrowser);
		WebSettings setting=web.getSettings();
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
		web.setWebViewClient(new MyWebViewClient());
		builder=new Builder(this);
		builder.setPositiveButton("关闭",new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).setView(view);
		dialog=builder.create();
		ListView list=(ListView)findViewById(R.id.list_languages);
	    ArrayList<HashMap<String,Object>>data=new ArrayList<>(); 
		for(int i=0;i<languages.length;i++){
			HashMap<String,Object>map=new HashMap<>();
			map.put("name",languages[i]);
			map.put("img", R.drawable.code);
			data.add(map);
		}
		SimpleAdapter adapter=new SimpleAdapter(this, data,R.layout.tool_list_item,new String[]{"name","img"},new int[]{R.id.tool_title,R.id.tool_img});
		list.setAdapter(adapter);	
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				 request_url=GITHUTAPI+languages[arg2]+TAG;
				 dialog.setTitle(languages[arg2]);
				 dialog.show();
				 request_url=request_url.replace("#", "sharp");
				 web.loadUrl(request_url);
			}
		});
	}
	
	private EditText ip_input;
	private TextView ip_result;
	private String ip_url="https://freeapi.ipip.net/";
	private String ip;
	private void initIP(){
		ip_input=(EditText) findViewById(R.id.search_rersult_input);
		ip_result=(TextView)findViewById(R.id.search_result_result);
		Button btn=(Button)findViewById(R.id.search_result_btn);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			ip=ip_input.getText().toString();
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						ip_url=ip_url+URLEncoder.encode(ip,"utf8");
						Message message=new Message();
						message.what=MessageEnum.MSG_IP_SUCCESS;
						ArrayList list=(ArrayList)new UrlAsyncTask().execute(ip_url).get();
						String result=new String(ArrayListToByteArray.listTobyteArray(list));
					    list=(ArrayList) new IPParser(result).getDataObject();
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
						hander.sendEmptyMessage(MessageEnum.MSG_IP_ERROR);
					}
				}
			}).start();	
			}
		});
		
	}
	private ImageView qr1,qr2,qr3,qr4,qr5,qr6;
	private String qr_url="https://cli.im/api/qrcode/code?mhid=";
	private ProgressDialog progressDialog;
	private String url,url_result;
	private AlertDialog qr_dialog;
	private WebView qr_browser;
	private EditText qr_input;
	private void initQrCode(){
	progressDialog=new ProgressDialog(this);
	progressDialog.setCancelable(false);
	progressDialog.setTitle("加载中");
	AlertDialog.Builder builder=new AlertDialog.Builder(this);
	View view=getLayoutInflater().inflate(R.layout.code_browser,null,false);
	qr_browser=(WebView)view.findViewById(R.id.codebrowser);
	WebSettings setting=qr_browser.getSettings();
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
	qr_browser.setWebViewClient(new MyWebViewClient());
	builder=builder.setTitle("二维码").setPositiveButton("使用浏览器打开",new OnClickListener() {
		
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			Intent intent=new Intent();
			intent.setAction(android.content.Intent.ACTION_VIEW);
			intent.setData(Uri.parse(url_result));
			startActivity(intent);
		}
	}).setNegativeButton("关闭",new OnClickListener() {
		
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			dialog.dismiss();
		}
	}).setView(view);
	dialog=builder.create();
	dialog.setCancelable(false);
	qr1=(ImageView)findViewById(R.id.qr1);
	qr2=(ImageView)findViewById(R.id.qr2);
	qr3=(ImageView)findViewById(R.id.qr3);
	qr4=(ImageView)findViewById(R.id.qr4);
	qr5=(ImageView)findViewById(R.id.qr5);
	qr6=(ImageView)findViewById(R.id.qr6);
	qr_input=(EditText)findViewById(R.id.qr_input);
    View.OnClickListener click=new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
	    url=qr_url;
		String tag="";
		if(arg0==qr1){
			tag="sELPDFnok80gPHovKdI";
		}else if(arg0==qr2){
			tag="vEeTCQzpns4hMHcrLtZXPK0";
		}else if(arg0==qr3){
			tag="40WVDA7ty8shMHcrLtZXPKM";
		}else if(arg0==qr4){
			tag="tUDPWAvmk5khMHcrLtZXPas";
		}else if(arg0==qr5){
			tag="tBDPC1nompghMHcrLtZXPao";
		}else if(arg0==qr6){
			tag="vErPDwjqks8hMHcrLtZXPak";
		}
		url+=tag+"&text="+URLEncoder.encode(qr_input.getText().toString());
		progressDialog.show();
	    new Thread(new Runnable() {
			
			@Override
			public void run() {
				try{
					ArrayList list=(ArrayList)new UrlAsyncTask().execute(url).get();
					String result=new String(ArrayListToByteArray.listTobyteArray(list),"utf8");
					int i=result.indexOf("<img");
					int j=result.indexOf("width: 200px;\">");
					result=result.substring(i, j);
					i=result.indexOf("//");
					j=result.indexOf("\" id");
			    	result="http:"+result.substring(i, j).replace("\"","");
			    	url_result=result;
			    	Message message=new Message();
			    	message.what=MessageEnum.MSG_QRCODE_SUCCESS;
			    	Bundle bundle=new Bundle();
			    	bundle.putString("result", result);
			    	message.setData(bundle);
					hander.sendMessage(message);
					}catch(Exception e){ 
						e.printStackTrace();
						hander.sendEmptyMessage(MessageEnum.MSG_QRCODE_ERROR);
					}
			}
		}).start();
		
		}
	};
	qr1.setOnClickListener(click);
	qr2.setOnClickListener(click);
	qr3.setOnClickListener(click);
	qr4.setOnClickListener(click);
	qr5.setOnClickListener(click);
	qr6.setOnClickListener(click);	

	}
	@Override
	public boolean handleMessage(Message arg0) {
		if(arg0.what==MessageEnum.MSG_IP_SUCCESS){
			ip_result.setText(arg0.getData().getString("result"));
			ip_result.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_top));
			ip_url="https://freeapi.ipip.net/";
		}else if(arg0.what==MessageEnum.MSG_IP_ERROR){
			ip_result.setText("未找到ip,请检查ip格式");
			ip_result.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_top));
			ip_url="https://freeapi.ipip.net/";
		}else if(arg0.what==MessageEnum.MSG_QRCODE_SUCCESS){
			progressDialog.hide();
			dialog.show();
			Log.e("url",arg0.getData().getString("result"));
			qr_browser.loadUrl(url_result);
		}else if(arg0.what==MessageEnum.MSG_QRCODE_ERROR){
			Toast.makeText(this, "请求错误",1000).show();
			progressDialog.hide();
		}
		return true;
	}
  
}
