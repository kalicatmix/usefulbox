package com.example.usefulbox;

import android.app.Activity;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SpeechActivity extends Activity{
    private MediaPlayer mediaPlayer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.speech);
	}
	public void say(View view){
		try{
		mediaPlayer.setDataSource(this,Uri.parse("http://dict.youdao.com/speech?audio=speech"));
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_SYSTEM);
		mediaPlayer.prepare();
		mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer mp) {
				// TODO Auto-generated method stub
			mp.start();	
			}
		});
		}
		catch(Exception e){
		  e.printStackTrace();
	  }
		}

}
