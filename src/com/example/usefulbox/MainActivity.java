package com.example.usefulbox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.exampel.event.ToolClickListenerImp;
import com.example.browser.MyWebChromeClient;
import com.example.browser.MyWebViewClient;
import com.example.interact.CustomDialog;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ActionBar.TabListener;
import android.app.AlertDialog.Builder;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.gesture.Gesture;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ShareCompat.IntentBuilder;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.transition.Transition;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterViewFlipper;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TableRow;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements TabListener {
    private ImageView splash;
    private GestureDetectorCompat detector;
    private boolean bootstrap=true;
    private ArrayList<Float>touch;
    private float screensize,start,end;
    private int position=0;
    private final String FILEPATH="icon/";
    private final String SPLASH[]={"s_one.png","s_two.png","s_three.png"};
	private ViewPager pager;
	private ActionBar bar;
	private Animation animation;
	private ArrayList<Fragment>views;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		//设置actionbar
	    bar=getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
		bar.setDisplayUseLogoEnabled(false);
		bar.setDisplayShowTitleEnabled(false);
		bar.setDisplayHomeAsUpEnabled(false);
		bar.setDisplayShowCustomEnabled(false);
		bar.setDisplayShowHomeEnabled(false);
		bar.addTab(bar.newTab().setText("新闻").setIcon(R.drawable.news).setTabListener(this));
		bar.addTab(bar.newTab().setText("工具").setIcon(R.drawable.tool).setTabListener(this));
		bar.addTab(bar.newTab().setText("关于").setIcon(R.drawable.about).setTabListener(this));
		screensize=(float) (getWindowManager().getDefaultDisplay().getWidth()/3);
		bootstrap=checkBootStrap();
		if(bootstrap){
			 bootStrap();
		 }else{
			 noBootStrap();
		 }
		checkNetWork();
	}
    
    /*进入主页面
     */
	private void bootStrap(){
		bar.show();
		position=0;
		setContentView(R.layout.activity_main);
		pager=(ViewPager)findViewById(R.id.pager);
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				bar.setSelectedNavigationItem(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		/*
		 *配置viewpager的fragment
		 */
		int []viewIDs={R.layout.news_fragment,R.layout.tools_fragment,R.layout.about_fragment};
		views=new ArrayList<>();
		for(int id:viewIDs){
			Log.e("id",""+id);
			views.add(new MyFragment(id));
		}
		pager.setAdapter(new MyViewAdaptor(getSupportFragmentManager()));
		pager.setDrawingCacheEnabled(true);
		pager.setPersistentDrawingCache(ViewPager.PERSISTENT_ALL_CACHES);
		pager.setOffscreenPageLimit(3);
		pager.setCurrentItem(0);
		
	}
	/*
	 *进入splash界面
	 */
	private void noBootStrap(){
		//application splash configure
		bar.hide();
		setContentView(R.layout.splash);
		splash=(ImageView)findViewById(R.id.splash);
		//animation
		animation=new ScaleAnimation(1,1.02f,1,1.02f);
		animation.setDuration(500);
		animation.setZAdjustment(Animation.ZORDER_NORMAL);
		animation.setRepeatMode(Animation.RELATIVE_TO_SELF);
		animation.setRepeatCount(2);
				/*detector=new GestureDetectorCompat(this,new com.example.gesture.GestureListener(this,splash));
		detector.setIsLongpressEnabled(true);
		*/
		
		setSplash(position);
	    splash.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==event.ACTION_DOWN){
					start=event.getX();
				}
				if(event.getAction()==event.ACTION_UP){
					end=event.getX();
					if(position>=2){
						bootStrap();
						return true;
					}
					if(start-end<-screensize){
						if(position>0){
							setSplash(position-1);
						}
					}else if(start-end>screensize){
						if(position<2){
							setSplash(position+1);
						}else{
							bootStrap();
						}
					}else{
						splash.startAnimation(animation);
					}
					
				}
				return true;
			}
		});
	}
	private void setSplash(int position){
		this.position=position;
		try {
			splash.setImageBitmap(BitmapFactory.decodeStream(getAssets().open(FILEPATH+SPLASH[position])));
		} catch (IOException e) {
			new CustomDialog(this).show("错误", "未知的错误",CustomDialog.TYPE_FAIL);
		}
	}
	/*
	 *验证是否为第一次启动
	 */
	private boolean checkBootStrap(){
		SharedPreferences share=getSharedPreferences("bootstrap",MODE_PRIVATE);
		if(share!=null){
			if(share.contains("bootstrap")){
				return true;
			}else{
				share.edit().putString("bootstrap","true").commit();
				return false;
			}
		}
		return false;
	}
	/*
	 * 
	 * (non-Javadoc)
	 * @see android.app.ActionBar.TabListener#onTabSelected(android.app.ActionBar.Tab, android.app.FragmentTransaction)
	 *响应tab选择事件
	 */
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Log.e("position:",""+tab.getPosition());
		try{
		pager.setCurrentItem(tab.getPosition());
		}catch(Exception e){
			//pass
		}
		}
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
	}
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
			
	}
   // end override TabListener method
	
	/*
	 * @class
	 *MyFragment配置viewpager的fragment
	 */
	class MyFragment extends Fragment implements View.OnClickListener{
		private int id;
		private WebView browser;
		private View view;
		private ImageButton back,forward,refresh,source,menu;
		private PopupMenu m_source,m_menu;
		private AlertDialog dialog;
		private EditText edit;
		private String urls[]={"http://news.sina.cn/?from=wap","http://m.toutiao.com/?W2atIF=1","http://xw.qq.com"};
        public MyFragment(int id){
        	this.id=id;
        }
		@Override
		public View onCreateView(LayoutInflater inflater,
				@Nullable ViewGroup container,
				@Nullable Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			view=inflater.inflate(id, container,false);
			if(id==R.layout.news_fragment)onLoadNews();
			if(id==R.layout.tools_fragment)onLoadTool();
			if(id==R.layout.about_fragment)onLoadAbout();
			if(view !=null)return view;
			//默认对象
			return inflater.inflate(R.layout.main_page,container,false);
		}
		
		//配置新闻事件
        /*
         * 	(non-Javadoc)
         * @see android.view.View.OnClickListener#onClick(android.view.View)
         */
		@Override
		public void onClick(View v) {
			if(v==back){	
				if(browser.canGoBack())browser.goBack();
				return;
			}else if(v==forward){
				if(browser.canGoForward())browser.goForward();
				return;
			}else if(v==refresh){
				try{
				if(browser.getUrl().equals("file:///android_asset/error.html"))
				browser.loadUrl(urls[0]);
				else
				browser.reload();
				}catch(Exception e){
					browser.loadUrl("file:///android_asset/error.html");
				}
				return;
			}else if(v==source){
				m_source.show();
				return;
			}else{
				m_menu.show();
				return;
			}
			
		}
		public String checkUrl(String url){
			if(url.contains("http"))return url;
			return "http://"+url;
		}
		public void  onLoadNews(){
			     //配置菜单事件
			    back=(ImageButton)view.findViewById(R.id.btn_back);
			    forward=(ImageButton)view.findViewById(R.id.btn_forward);
			    refresh=(ImageButton)view.findViewById(R.id.btn_refresh);
			    source=(ImageButton)view.findViewById(R.id.btn_source);
			    menu=(ImageButton)view.findViewById(R.id.btn_menu);
			    back.setOnClickListener(this);
			    forward.setOnClickListener(this);
			    refresh.setOnClickListener(this);
			    source.setOnClickListener(this);
			    menu.setOnClickListener(this);
			    //配置输入框
			    Builder builder=new AlertDialog.Builder(MainActivity.this);
			    builder=builder.setCancelable(true);
			    View alert=LayoutInflater.from(MainActivity.this).inflate(R.layout.search_dialog,null,false);
			    edit=(EditText)alert.findViewById(R.id.edit);
			    Button open=(Button)alert.findViewById(R.id.open);
			    open.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						dialog.hide();
						String url=checkUrl(edit.getText().toString());
						Log.e("url",url);
						browser.loadUrl(url);
					}
				});
			    builder=builder.setView(alert);
			    dialog=builder.create();
			    //配置弹出菜单
			    m_menu=new PopupMenu(MainActivity.this,menu);
			    m_source=new PopupMenu(MainActivity.this,source);
			    m_menu.inflate(R.menu.m_menu);
			    m_source.inflate(R.menu.m_source);
			    m_menu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						Log.e("id:",item.getItemId()+"");
						if(item.getItemId()==R.id.m_input){
							dialog.show();
						}
						return false;
					}
				});
			    m_source.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						Log.e("id:",item.getItemId()+"");
						if(item.getItemId()==R.id.m_m0){
							browser.loadUrl(urls[0]);
						}
						if(item.getItemId()==R.id.m_m1){
							browser.loadUrl(urls[1]);
						}
						if(item.getItemId()==R.id.m_m2){
							browser.loadUrl(urls[2]);
						}
						return false;
					}
				});
			    //配置browser
			    browser=(WebView)view.findViewById(R.id.webbrowser);
				LinearLayout menubar=(LinearLayout)view.findViewById(R.id.menubar);
				float height=getWindowManager().getDefaultDisplay().getHeight();
				float width=getWindowManager().getDefaultDisplay().getWidth();
			   /*
			    * 设置webview
			    */
				WebSettings setting=browser.getSettings();
			    setting.setAllowContentAccess(true);
			    setting.setAllowFileAccess(true);
			    setting.setAppCacheEnabled(false);
			    setting.setBuiltInZoomControls(false);
			    setting.setJavaScriptEnabled(true);
			    setting.setJavaScriptCanOpenWindowsAutomatically(true);
			    setting.setSupportMultipleWindows(true);
			    setting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
			    setting.setAppCacheMaxSize(1024*1024);
			    setting.setBlockNetworkImage(false);
			    setting.setUseWideViewPort(true);
			    setting.setLoadsImagesAutomatically(true);
			    setting.setDomStorageEnabled(true);
			    setting.setLoadWithOverviewMode(true);
			    /*
			     * 设置webclient
			     */
			    browser.setBackgroundColor(Color.WHITE);
			    browser.setWebViewClient(new MyWebViewClient());
			    browser.setWebChromeClient(new MyWebChromeClient());
			    browser.loadUrl(urls[0]);
		}
		
		//配置tool
		public void onLoadTool(){
			int width=getWindowManager().getDefaultDisplay().getWidth();
			int height=getWindowManager().getDefaultDisplay().getHeight();
			TableLayout table=(TableLayout)view.findViewById(R.id.table);
			String[]items={"学习工具","生活工具","开发者工具","资源查找"};
          	int drawables[]={R.drawable.tb_study,R.drawable.tb_life,R.drawable.tb_develop,R.drawable.tb_resource};
		    int INTENT_ID[]={0x0,0x1,0x2,0x3};
          	int tag=0;
          	for(int i=0;i<items.length/3;i++){
		    	TableRow row=new TableRow(MainActivity.this);
		    	for(int j=0;j<3;j++){
		    		LinearLayout layout=new LinearLayout(MainActivity.this);
		    		ImageView image=new ImageView(MainActivity.this);
		    		TextView text=new TextView(MainActivity.this);
		    	    image.setImageResource(drawables[j+tag]);
		    	    text.setText(items[j+tag]);
		    	    image.setAlpha(0.8f);
		    	    image.setScaleType(ScaleType.CENTER);
		    	    text.setGravity(Gravity.CENTER);
		    	    layout.setOrientation(LinearLayout.VERTICAL);
		    		layout.setBackgroundResource(R.drawable.tb_item);
		    	    layout.addView(image,width/3,height/5);
		    	    layout.addView(text,width/3,height/5);
		    	    layout.setOnClickListener(new ToolClickListenerImp(MainActivity.this,INTENT_ID[j+tag]));
		    	    row.addView(layout);
		    	}
		    	tag+=3;
		    	table.addView(row, width,height/5);
		    }
          	int remain=items.length%3;
          	TableRow row=new TableRow(MainActivity.this);
	    	for(int j=0;j<remain;j++){
	    		LinearLayout layout=new LinearLayout(MainActivity.this);
	    		ImageView image=new ImageView(MainActivity.this);
	    		TextView text=new TextView(MainActivity.this);
	    		image.setImageResource(drawables[drawables.length-remain+j]);
	    	    text.setText(items[drawables.length-remain+j]);
	    	    image.setScaleType(ScaleType.CENTER);
	    	    text.setGravity(Gravity.CENTER);
	    	    layout.setBackgroundResource(R.drawable.tb_item);
	    	    layout.setOrientation(LinearLayout.VERTICAL);
	    		layout.addView(image,width/3,height/5);
	    	    layout.addView(text,width/3,height/5);
	    	    layout.setOnClickListener(new ToolClickListenerImp(MainActivity.this,INTENT_ID[INTENT_ID.length-remain+j]));
	    	    row.addView(layout);
	    	}
	    	table.addView(row, width,height/5);
		    
		 }
		//配置关于
		public void onLoadAbout(){
			//配置listview适配器
			ListView main_list=(ListView)view.findViewById(R.id.main_list);
			ListView about_list=(ListView)view.findViewById(R.id.about_list);
		    String main[]={"新功能"};
		    String about[]={"关于","联系作者"};
			ArrayList<HashMap<String,Object>>main_data=new ArrayList<>();
		    ArrayList<HashMap<String,Object>>about_data=new ArrayList<>();
		    for(int i=0;i<main.length;i++){
		    	HashMap<String,Object>map=new HashMap<>();
		    	map.put("img", R.drawable.box);
		    	map.put("text",main[i]);
		    	main_data.add(map);
		    }
		    for(int i=0;i<about.length;i++){
		    	HashMap<String, Object>map=new HashMap<>();
		    	map.put("img", R.drawable.box);
		    	map.put("text", about[i]);
		    	about_data.add(map);
		    }
		    SimpleAdapter s_main=new SimpleAdapter(MainActivity.this,main_data,R.layout.list_item,new String[]{"img","text"},new int[]{R.id.list_image,R.id.list_title});
		    SimpleAdapter s_about=new SimpleAdapter(MainActivity.this,about_data,R.layout.list_item,new String[]{"img","text"},new int[]{R.id.list_image,R.id.list_title});
		    main_list.setAdapter(s_main);
		    about_list.setAdapter(s_about);
		    /*
		     * 添加listview响应事件
		     */
		    
		    main_list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					if(arg2==0){
						Intent intent=new Intent(MainActivity.this, NewFunctionActivity.class);
						startActivity(intent);
						
					}
					
				}
		    	
			});
		    
		    about_list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					if(arg2==0){
						Intent intent=new Intent(MainActivity.this,AboutActivity.class);
					    startActivity(intent);
					}else if(arg2==1){
						Intent intent=new Intent(MainActivity.this,ContactActivity.class);
						startActivity(intent);
					}
		            
				}
		    	
			});   
		    
		}
	}
	
	
	
	/*
	 *viewpager的adaptor
	 */
	public class MyViewAdaptor extends FragmentPagerAdapter{
		public MyViewAdaptor(FragmentManager fm) {
			super(fm);
			
		}

		@Override
		public int getCount() {
		
			return views.size();
		}

		@Override
		public Fragment getItem(int arg0) {
			Log.e("item",":"+arg0);
			return views.get(arg0);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			//super.destroyItem(container, position, object);
		}
		

	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	private void checkNetWork(){
		try{
		String text="";
		ConnectivityManager manager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info=manager.getActiveNetworkInfo();
		if(info.isAvailable()){
			info=manager.getNetworkInfo(manager.TYPE_WIFI);
			if(info!=null){
				if(info.isAvailable()){
					text="当前wifi网络,请放心使用";
				}else{
					text="移动网络访问";
				}
			}else{
				text="移动网络访问";
			}
		}else{
			text="网络不可用,部分功能无法使用";
		}
		Toast.makeText(this, text,1000).show();
		}catch(Exception e){
		Toast.makeText(this, "请授予部分权限",1000).show();	
		}
		}
	 
}
