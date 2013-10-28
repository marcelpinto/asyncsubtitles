package com.hardsoft.asyncsubtitles.demo;

import com.hardsoft.asyncsubtitles.ORequest;
import com.hardsoft.asyncsubtitles.OSubtitleActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class DemoORequest extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		/*
		 * Do whatever you want on this activity and when you would like to ask for subtitles 
		 * start a new activity like this
		 */
		
		//If you have a file you should pass a correct PATH of the file, if not pass a path where to save the file with srt extension
		ORequest req = new ORequest("/sdcard/familyguy.srt", "Family guy", null, new String[]{"spa","eng"});
		Intent i = new Intent(this, OSubtitleActivity.class);
		Bundle extras = new Bundle();
		extras.putSerializable(OSubtitleActivity.OREQUEST_PARAM, req);
		i.putExtras(extras);
		startActivity(i);
	}
	
}
