package com.example.interact;

import android.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomDialog {
  public static int TYPE_SUCCESS=0;
  public static int TYPE_FAIL=1;
  private Dialog dialog;
  public CustomDialog(Context context){
	dialog=new Dialog(context);  
  }
  public void show(String title,String alert,int type){
	  dialog.setTitle(title);
	  dialog.setContentView(com.example.usefulbox.R.layout.dialog);
	  if(type==TYPE_SUCCESS){
		  ((ImageView)dialog.findViewById(com.example.usefulbox.R.id.image)).setImageResource(com.example.usefulbox.R.drawable.success);
	  }else if(type==TYPE_FAIL){
		  ((ImageView)dialog.findViewById(com.example.usefulbox.R.id.image)).setImageResource(com.example.usefulbox.R.drawable.fail);
	  }else{
		 //throw new Exception(); 
	  }
	  ((TextView)dialog.findViewById(com.example.usefulbox.R.id.title)).setText(title);
	  ((TextView)dialog.findViewById(com.example.usefulbox.R.id.body)).setText(title);
	  dialog.show();
  }
  
}
