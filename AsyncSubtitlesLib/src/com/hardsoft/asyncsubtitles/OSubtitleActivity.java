package com.hardsoft.asyncsubtitles;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.hardsoft.asyncsubtitles.AsyncSubtitles;
import com.hardsoft.asyncsubtitles.AsyncSubtitles.SubtitlesInterface;
import com.hardsoft.asyncsubtitles.ORequest;
import com.hardsoft.asyncsubtitles.OSubtitle;
import com.hardsoft.asyncsubtitles.R;
import com.hardsoft.asyncsubtitles.demo.FileUtils;

public class OSubtitleActivity extends ListActivity implements SubtitlesInterface, OnItemClickListener {

	public static final String OREQUEST_PARAM = "orequest";
	
	private AsyncSubtitles mASub;
	private ArrayList<String> listSub;
	private OSAdapter adp;
	private List<OSubtitle> mListSub;
	private ProgressDialog pgr;

	private ORequest mORequest;
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==100)
			askStartVideo();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB) {
			ActionBar aBar = this.getActionBar();
			aBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#C80000")));
		}
		setContentView(R.layout.view_subtitles);
		mORequest = (ORequest) getIntent().getExtras().getSerializable(OREQUEST_PARAM);
		getListView().setOnItemClickListener(this);
		listSub = new ArrayList<String>();
		adp = new OSAdapter (this);
		setListAdapter(adp);
		try {
			Log.v("MPB", "Param: "+mORequest.toString());
			pgr = ProgressDialog.show(this, null, "Buscando subtitulos", true);
			mASub = new AsyncSubtitles(this, this);
			mASub.setLanguagesArray(mORequest.getLanguages());
			mASub.setNeededParamsToSearch(mORequest);
			mASub.getPossibleSubtitle();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			pgr.dismiss();
		}
	}
	
	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		if (Build.VERSION.SDK_INT<Build.VERSION_CODES.HONEYCOMB)
			return true;
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		//SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		final MenuItem searchItem = menu.findItem(R.id.action_search);
		SearchView searchView = (SearchView) searchItem.getActionView();
		// 
		searchView.setIconifiedByDefault(false); 
												
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				searchItem.collapseActionView();
				pgr = ProgressDialog.show(OSubtitleActivity.this, null, "Buscando subtitulos", true);
				mASub.setLanguagesArray(mORequest.getLanguages());
				mASub.setNeededParamsToSearch(new ORequest(null, query, null, null));
				mASub.getPossibleSubtitle();
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				return false;
			}
		});

		return true;
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
		private LayoutInflater inflater;

		public OSAdapter(Context c) {
			this.context= c;
			inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView==null) {
				convertView = inflater.inflate(R.layout.item_subtitle, null);
			}
			TextView name = (TextView) convertView.findViewById(R.id.txt_subtitle_name);
			TextView lang = (TextView) convertView.findViewById(R.id.txt_subtitle_lang);
			TextView rat = (TextView) convertView.findViewById(R.id.txt_subtitle_rating);
			double rating = mListSub.get(position).getSubRating();
			String txtRating = "Rating: ";
			if (rating<1)
				rat.setVisibility(View.GONE);
			else {
				rat.setVisibility(View.VISIBLE);
				txtRating+=rating;
				rat.setText(txtRating);
			}
			
			
			name.setText("Name: "+mListSub.get(position).getSubFileName());
			lang.setText("Language: "+mListSub.get(position).getSubLanguageID().toUpperCase());
			if (mListSub.get(position).getMatchedBy().equalsIgnoreCase("moviehash"))
				convertView.findViewById(R.id.img_subtitle_perfect).setVisibility(View.VISIBLE);
			else
				convertView.findViewById(R.id.img_subtitle_perfect).setVisibility(View.GONE);
			return convertView;
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
		if(pgr!=null)
			pgr.dismiss();
		adp.notifyDataSetChanged();
	}

	@Override
	public void onSubtitleDownload(boolean path) {
		// TODO Auto-generated method stub
		if (pgr!=null)
			pgr.dismiss();
		
		if (mORequest.getFilePath().equals("") || !(new File(mORequest.getFilePath()).exists())) {
			Toast.makeText(getApplicationContext(), "Subtitle downloaded", Toast.LENGTH_LONG).show();
			return;
		}
		if (!isPackageExisted("com.mxtech.videoplayer.ad") && !isPackageExisted("com.mxtech.videoplayer.pro")) {
			askDownloadMX();
			return;
		}
		askStartVideo();
	}
	
	private boolean isPackageExisted(String targetPackage) {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(targetPackage,PackageManager.GET_META_DATA);
		} catch (NameNotFoundException e) {
			return false;
		}
		return true;
	}
	
	private void askStartVideo() {
		AlertDialog.Builder builder = new AlertDialog.Builder(OSubtitleActivity.this);
		builder
			.setMessage(R.string.txt_dialog_subtitle_download_finished)
			.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					startVideo(mORequest.getFilePath(), false);
					finish();
					dialog.dismiss();
				}
			})
			.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					finish();
					dialog.dismiss();
				}
			}).create()
			.show();
	}
	private void askDownloadMX() {
		AlertDialog.Builder builder = new AlertDialog.Builder(OSubtitleActivity.this);
		builder
			.setMessage(R.string.txt_dialog_subtitle_mx)
			.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					try {
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri.parse("market://details?id=com.mxtech.videoplayer.ad"));
						startActivityForResult(intent,100);
					} catch (Exception e) {
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.mxtech.videoplayer.ad"));
						startActivity(intent);
					}
					
				}
			})
			.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.dismiss();
					askStartVideo();
				}
			}).create()
			.show();
	}

	@Override
	public void onError(int error) {
		// TODO Auto-generated method stub
		if (pgr!=null)
			pgr.dismiss();
		AlertDialog.Builder builder = new AlertDialog.Builder(OSubtitleActivity.this);
		builder
			.setMessage(R.string.txt_dialog_subtitle_download_finished)
			.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.dismiss();
				}
			})
			.create()
			.show();
	}
	
	public Collection<File> listFileTree(File dir, boolean str) {
		Set<File> fileTree = new HashSet<File>();
		for (File entry : dir.listFiles()) {
			if (entry.isFile() && FileUtils.isStreaming(entry.getName(), str))
				fileTree.add(entry);
			else if (entry.isDirectory())
				fileTree.addAll(listFileTree(entry, str));
		}
		return fileTree;
	}

	private boolean startVideo(String path, final boolean streaming) {
		// TODO Auto-generated method stub
		Intent viewMediaIntent = new Intent();
		viewMediaIntent.setAction(android.content.Intent.ACTION_VIEW);
		Log.v("MPB", "Play file in " + path);
		File file = new File(path);
		if (file.isDirectory()) {
			List<File> list = FileUtils.collectionToArray(listFileTree(file,streaming));
			if (list == null || list.isEmpty())
				return false;
			viewMediaIntent
					.setDataAndType(Uri.fromFile(list.get(0)), "video/*");
		} else
			viewMediaIntent.setDataAndType(Uri.fromFile(file), "video/*");

		viewMediaIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(viewMediaIntent);
		return true;
	}

	private String mPath;
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1,final int arg2, long arg3) {
		// TODO Auto-generated method stub
		mPath = mORequest.getFilePath();
		if (mPath.equals("")) {
			mPath = Environment.getExternalStorageDirectory()+"/"+mListSub.get(arg2).getSubFileName();
			Toast.makeText(getApplicationContext(), "Path not found saving subtitle to\n"+mPath, Toast.LENGTH_SHORT).show();
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(OSubtitleActivity.this);
		builder.setTitle(R.string.txt_dialog_subtitle_download)
			.setMessage(mListSub.get(arg2).getSubFileName())
			.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					pgr =ProgressDialog.show(OSubtitleActivity.this,null,"Downloading subtitle "+mListSub.get(arg2).getSubFileName(),true);
					try {
						String path = mPath.substring(0,mPath.lastIndexOf('.'))+".srt";
						mASub.downloadSubByIdToPath(mListSub.get(arg2).getIDSubtitleFile(), path);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			})
			.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.dismiss();
				}
			}).create()
			.show();
	}

}
