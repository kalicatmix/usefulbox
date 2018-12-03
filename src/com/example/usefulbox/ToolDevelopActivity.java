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

public class ToolDevelopActivity extends Activity {
	private ListView list;
    private String items[]={"github star榜","ip查询","二维码生成"};
    public static int TYPE_CODE=0x00;
    public static int TYPE_IP=0x01;
    public static int TYPE_QRCODE=0x02;
    private int drawable=R.drawable.tb_develop;
    private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar bar=getActionBar();
		bar.setTitle("开发者工具");
		setContentView(R.layout.tool_develop);
		list=(ListView)findViewById(R.id.develop_list);
		List<HashMap<String,Object>>data=new ArrayList<>();
		intent=new Intent(this, ToolDevelopFunctionActivity.class);
		for(int i=0;i<items.length;i++){
			HashMap<String,Object>map=new HashMap<>();
			map.put("title",items[i]);
			map.put("img", drawable);
			data.add(map);
		}
		SimpleAdapter adapter=new SimpleAdapter(ToolDevelopActivity.this,data,R.layout.tool_list_item,new String[]{"title","img"},new int[]{R.id.tool_title,R.id.tool_img});
	    list.setAdapter(adapter);
	    list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position==0){
					intent.putExtra("name",items[0]);
					intent.putExtra("type",TYPE_CODE);
				}else if(position==1){
					intent.putExtra("name",items[1]);
					intent.putExtra("type", TYPE_IP);
				}else if(position==2){
					intent.putExtra("name", items[2]);
					intent.putExtra("type", TYPE_QRCODE);
				}
				startActivity(intent);
			}
		});
	}
   
}
