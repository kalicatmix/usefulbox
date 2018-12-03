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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ToolStudyActivity extends Activity {
    private ListView list;
    private String items[]={"单词查询","古诗查询"};
    public static int TYPE_WORD=0x00;
    public static int TYPE_POEM=0x01;
    private int drawable=R.drawable.tb_study;
    private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar bar=getActionBar();
		bar.setTitle("学习工具");
		setContentView(R.layout.tool_study);
		intent=new Intent(this, ToolStudyFunctionActivity.class);
		list=(ListView)findViewById(R.id.study_list);
		List<HashMap<String,Object>>data=new ArrayList<>();
		for(int i=0;i<items.length;i++){
			HashMap<String,Object>map=new HashMap<>();
			map.put("title",items[i]);
			map.put("img", drawable);
			data.add(map);
		}
		SimpleAdapter adapter=new SimpleAdapter(ToolStudyActivity.this,data,R.layout.tool_list_item,new String[]{"title","img"},new int[]{R.id.tool_title,R.id.tool_img});
	    list.setAdapter(adapter);
	    list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position==0){
					intent.putExtra("name",items[0]);
					intent.putExtra("type",TYPE_WORD);	
				}
				if(position==1){
					intent.putExtra("name",items[1]);
					intent.putExtra("type", TYPE_POEM);
				}
				startActivity(intent);
			}
		});
	}
}
