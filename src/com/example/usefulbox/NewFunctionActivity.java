package com.example.usefulbox;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class NewFunctionActivity extends Activity {
	private ImageView image;
	private AnimationDrawable drawable;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.about_function);
		ActionBar bar=getActionBar();
		bar.setDisplayShowTitleEnabled(false);
		bar.setDisplayShowCustomEnabled(true);
		bar.setDisplayUseLogoEnabled(false);
		bar.setIcon(null);
		bar.setLogo(null);
		bar.setCustomView(R.layout.bar_back);
		image=(ImageView)findViewById(R.id.cat);
	    drawable=(AnimationDrawable) image.getBackground();
		LinearLayout layout=(LinearLayout)findViewById(R.id.scroll);
		layout.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				drawable.start();
				return true;
			}
		});
	    
	}
	
}
