package com.example.usefulbox;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class ContactActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_contact);
		ActionBar bar=getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	}

}
