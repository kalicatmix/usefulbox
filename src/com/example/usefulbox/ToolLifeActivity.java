package com.example.usefulbox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ToolLifeActivity extends Activity {
	private ListView list;
    private String items[]={"电视频道","快递查询","天气查询","电话归属地","身份证查询","万年历查询"};
    public static int TYPE_VIDEO=0x00;
    public static int TYPE_EXPRESS=0x01;
    public static int TYPE_WEATHER=0x02;
    public static int TYPE_PHONE=0x03;
    public static int TYPE_IDCARD=0x04;
    public static int TYPE_Calendar=0x05;
    private int drawable=R.drawable.tb_life;
    private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar bar=getActionBar();
		bar.setTitle("生活工具");
		setContentView(R.layout.tool_life);
		list=(ListView)findViewById(R.id.life_list);
		List<HashMap<String,Object>>data=new ArrayList<>();
		for(int i=0;i<items.length;i++){
			HashMap<String,Object>map=new HashMap<>();
			map.put("title",items[i]);
			map.put("img", drawable);
			data.add(map);
		}
		SimpleAdapter adapter=new SimpleAdapter(ToolLifeActivity.this,data,R.layout.tool_list_item,new String[]{"title","img"},new int[]{R.id.tool_title,R.id.tool_img});
	    list.setAdapter(adapter);
	    intent=new Intent(this, ToolLifeFunctionActivity.class);
	    list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String name="";
				int type=-1;
				if(position==0){
					name=items[0];
					type=TYPE_VIDEO;
				}else if(position==1){
					name=items[1];
					type=TYPE_EXPRESS;
				}else if(position==2){
					name=items[2];
					type=TYPE_WEATHER;
				}else if(position==3){
					name=items[3];
					type=TYPE_PHONE;
				}else if(position==4){
					name=items[4];
					type=TYPE_IDCARD;
				}else if(position==5){
					name=items[5];
					type=TYPE_Calendar;
				}
				intent.putExtra("name",name);
				intent.putExtra("type",type);
				startActivity(intent);
			}
		});
	}
}
