package com.hardsoft.asyncsubtitles;

import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;

/**
 * 	 Async helper to search and download subtitles using OpenSubtitlesAPI.
 *   This lib is based on the open source projecte http://trac.opensubtitles.org/projects/opensubtitles/wiki/XMLRPC#Instructions
 * 
 *   Available languages can be found http://www.opensubtitles.org/addons/export_languages.php
 * 
 *   Copyright (C) 2013  @author Marcel Pintó, Hardsoft studio
 *   
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *   
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *   
 */
public class AsyncSubtitles {

	public interface SubtitlesInterface {
		void onSubtitlesListFound(List<OSubtitle> list);

		void onSubtitleDownload(boolean b);

		void onError(int error);
	}

	public static final long SESSION_TIMEOUT = 1000 * 60 * 15; // the token
																// should be
																// valid up to
																// 15 minutes.

	private Context mContext;
	private SubtitlesInterface mCallback;
	private OpenSubtitlesAPI mOsa;
	private String mToken;
	private long mTokenTime;
	private String mLanguages;
	private File mFile;
	private String mQuert;
	private String mImdbid;
	private int mSeason;
	private int mEpisode;

	public ArrayList<OSubtitle> mResultList;

	private AsyncTask<Void, Void, ArrayList<OSubtitle>> mTask;

	private AsyncTask<String, Void, Boolean> mDownTask;

	public AsyncSubtitles(Context c, SubtitlesInterface callback)
			throws MalformedURLException {
		this.mContext = c;
		this.mCallback = callback;
		mOsa = new OpenSubtitlesAPI();
	}

	public void setLanguagesArray(String[] lang) {
		if (lang == null || lang.length == 0) {
			mLanguages = "all";
			return;
		}
		mLanguages = "";
		for (String l : lang) {
			mLanguages += "," + l;
		}
		mLanguages = mLanguages.substring(1);
	}

	/**
	 * Set the needed parmas for a search, set to null if you don't want to used
	 * 
	 * example: setNeededParamsToSearch(null, "Family guy", null, 10, 2)
	 * This will make a search when call getPossibleSubtitle using only the query "Family guy season 10 episode 2"
	 * Set episode and season to -1 if you don't want to search by season.
	 * 
	 * @param file
	 * @param query
	 * @param imdbid
	 * @param season
	 * @param episode
	 */
	public void setNeededParamsToSearch(File file, String query, String imdbid,
			int season, int episode) {
		this.mFile = file;
		this.mQuert = query;
		this.mImdbid = imdbid;
		this.mSeason = season;
		this.mEpisode = episode;
	}

	/**
	 * Start the asynctask to get a list of subtitles based on the params defined with setNeededParamsToSearch. This params must be 
	 * set before starting the task if not this will return false. Also if the task is already on progress to avoid multiple tasks
	 * @return true if the task has successfully started, false otherwise
	 */
	public boolean getPossibleSubtitle() {
		if (mFile == null && mQuert == null && mImdbid == null)
			return false;
		if (mTask == null
				|| mTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
			mTask = new GetSubList().execute();
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param subId
	 * @param path
	 *            should be the same name of the movie file with file format
	 *            .str, so the media player will get the subtitles correctly
	 */
	public void downloadSubByIdToPath(String subId, String path) {
		mDownTask = new DownloadSub().execute(new String[] { subId, path });
	}

	public void logoutOSA() {
		if (mTask != null && !mTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
			mTask.cancel(true);
		}
			
		Thread logout = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				try {
					mOsa.logout(mToken);
				} catch (OpenSubtitlesException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		logout.start();
	}

	public class DownloadSub extends AsyncTask<String, Void, Boolean> {

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mCallback.onSubtitleDownload(result);				
		}

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				byte[] arraybytes = mOsa.downloadSubtitle(mToken,
						Integer.parseInt(params[0]));
				File sub = new File(params[1]);
				if (sub.exists()) {
					sub.delete();
				}

				try {
					FileOutputStream fos = new FileOutputStream(sub.getPath());
					fos.write(arraybytes);
					fos.flush();
					fos.close();
					return true;
				} catch (java.io.IOException e) {
					e.printStackTrace();
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OpenSubtitlesException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}

	}

	public class GetSubList extends AsyncTask<Void, Void, ArrayList<OSubtitle>> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mResultList = new ArrayList<OSubtitle>();
		}

		@Override
		protected void onPostExecute(ArrayList<OSubtitle> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (mResultList != null)
				mCallback.onSubtitlesListFound(mResultList);

			else
				mCallback.onError(-1);

		}

		@Override
		protected ArrayList<OSubtitle> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				if (System.currentTimeMillis() - mTokenTime > SESSION_TIMEOUT) {
					mToken = mOsa.login("", "");
					mTokenTime = System.currentTimeMillis();
				}
				List<Object> reqList = new ArrayList<Object>();

				if (mFile != null)
					reqList.add(mOsa.getSearchByHash(mFile, mLanguages));
				if (mQuert != null && !mQuert.equals(""))
					reqList.add(mOsa.getSearchByQuery(mQuert, mLanguages,
							mSeason, mEpisode));
				if (mImdbid != null && !mImdbid.equals(""))
					reqList.add(mOsa.getSearchByImdbId(mImdbid, mLanguages));

				mResultList = mOsa.executeSearch(mToken, reqList);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return mResultList;
		}

	}

}
