package com.exampel.event;

import com.example.usefulbox.ToolDevelopActivity;
import com.example.usefulbox.ToolLifeActivity;
import com.example.usefulbox.ToolSourceActivity;
import com.example.usefulbox.ToolStudyActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.INotificationSideChannel.Stub;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class ToolClickListenerImp implements OnClickListener {
    private int INTENT_ID;
    public static int STUDY=0x0;
    public static int LIFE=0x1;
    public static int DEVELOP=0x2;
    public static int SOURCE=0x3;
    private Class IntentPool []={ToolStudyActivity.class,ToolLifeActivity.class,ToolDevelopActivity.class,ToolSourceActivity.class};
	private Intent intent;
	private Context context;
    public ToolClickListenerImp(Context context,int INTENT_ID){
       this.INTENT_ID=INTENT_ID; 
       this.context=context;
    }
	@Override
	public void onClick(View v) {
		if(this.INTENT_ID==STUDY){
			intent=new Intent(context,IntentPool[0]);
		}else if(this.INTENT_ID==LIFE){
			intent=new Intent(context,IntentPool[1]);
		}else if(this.INTENT_ID==DEVELOP){
			intent=new Intent(context,IntentPool[2]);
		}else if(this.INTENT_ID==SOURCE){
			intent=new Intent(context, IntentPool[3]);
		}else{
			Log.e("error","unkown intent_id");
		}
		try{
			context.startActivity(intent);
		}catch(Exception e){
			Log.e("error","intent can't start");
		}
		
	}

}
