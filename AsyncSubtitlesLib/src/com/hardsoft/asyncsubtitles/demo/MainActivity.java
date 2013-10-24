package com.hardsoft.asyncsubtitles.demo;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hardsoft.asyncsubtitles.AsyncSubtitles;
import com.hardsoft.asyncsubtitles.AsyncSubtitles.SubtitlesInterface;
import com.hardsoft.asyncsubtitles.OSubtitle;

public class MainActivity extends ListActivity implements SubtitlesInterface, OnItemClickListener {

	private AsyncSubtitles mASub;
	private ArrayList<String> listSub;
	private OSAdapter adp;
	private List<OSubtitle> mListSub;
	private ProgressDialog pgr;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getListView().setOnItemClickListener(this);
		listSub = new ArrayList<String>();
		adp = new OSAdapter (this);
		setListAdapter(adp);
		try {
			mASub = new AsyncSubtitles(this, this);
			mASub.setLanguagesArray(new String[] { "spa" });
			mASub.setNeededParamsToSearch(null, "Family guy", "468492", 10, 2);
			mASub.getPossibleSubtitle();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (mASub!=null) {
			mASub.logoutOSA();
		}
	}

	public void setData(String[] listItems) {
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listItems));
	}

	public class OSAdapter extends BaseAdapter {
		private final Context context;

		public OSAdapter(Context c) {
			this.context= c;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView txt;
			if (convertView==null) {
				txt = new TextView(context);
				txt.setPadding(10, 10, 10, 10);
			}
			else 
				txt = (TextView) convertView;
			txt.setText(listSub.get(position));
			return txt;
		}



		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listSub.size();
		}



		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return listSub.get(position);
		}



		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
	}

	@Override
	public void onSubtitlesListFound(List<OSubtitle> list) {
		// TODO Auto-generated method stub
		mListSub = list;
		listSub = new ArrayList<String>();
		for (OSubtitle s : list) {
			listSub.add(s.getSubFileName()+ " LANG:"+s.getSubLanguageID() + " " + s.getIDSubtitleFile());
		}
		adp.notifyDataSetChanged();
	}

	@Override
	public void onSubtitleDownload(boolean path) {
		// TODO Auto-generated method stub
		if (pgr!=null)
			pgr.dismiss();
	}

	@Override
	public void onError(int error) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Log.v("MPB", "Download");
		pgr =ProgressDialog.show(MainActivity.this,null,"Downloading subtitle "+mListSub.get(arg2).getSubFileName(),true);
		mASub.downloadSubByIdToPath(mListSub.get(arg2).getIDSubtitleFile(), Environment.getExternalStorageDirectory().getAbsolutePath()+"/familyuu.srt");
	}

}
