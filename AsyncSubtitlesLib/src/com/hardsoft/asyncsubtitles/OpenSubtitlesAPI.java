/*
 *  Copyright 2011 daniele.belletti@gmail.com.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package com.hardsoft.asyncsubtitles;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import org.apache.ws.commons.util.Base64;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import android.util.Log;


/**
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

public class OpenSubtitlesAPI {

	private static final String USER_AGENT = "Hardsoft v1";
	private static final String END_POINT = "http://api.opensubtitles.org/xml-rpc";
	private static final String LANGUAGE = "en";
	private XmlRpcClient client;

	/**
	 * Constructor to create the XMLRpcClient and setup the needed configuration.
	 * 
	 * @throws MalformedURLException
	 */
	public OpenSubtitlesAPI() throws MalformedURLException {
		client = new XmlRpcClient();
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL(END_POINT));
		client.setConfig(config);
		client.setTypeFactory(new TypeFactory(client));
	}

	/**
	 * Login to get a valid token for the other methods
	 * @param username
	 * @param password
	 * @return
	 * @throws OpenSubtitlesException
	 */
	public String login(String username, String password) throws OpenSubtitlesException {
		List<String> params = new ArrayList<String>();
		params.add(username);
		params.add(password);
		params.add(LANGUAGE);
		params.add(USER_AGENT);
		Map<String, Object> result = executeAPI(API.LOGIN, params);
		if (result.get("token") == null) {
			throw new OpenSubtitlesException("login error: token is null");
		}
		String token = (String) result.get("token");
		return token;
	}
	
	/**
	 * Logout, be a good citizen and logout when you finished
	 * @param token
	 * @throws OpenSubtitlesException
	 */
	public void logout(String token) throws OpenSubtitlesException {
		List<String> params = new ArrayList<String>();
		params.add(token);
		executeAPI(API.LOGOUT, params);
	}

	/**
	 * @deprecated not fully implemented do not use
	 * @param token
	 * @param idimdb
	 * @return
	 * @throws OpenSubtitlesException
	 */
	public String searchOnImdb(String token, String idimdb) throws OpenSubtitlesException {
		List<String> params = new ArrayList<String>();
		params.add(token);
		params.add(idimdb);
		Map<String, Object> result = executeAPI(API.SEARCH_IMDB, params);
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		Object resObject = (Object) result.get("data");
		if (resObject instanceof Object[]) {
			Object[] data = (Object[]) resObject;
			Log.v("MPB", "is array");
			for (Object o : data) {
				results.add((Map<String, Object>) o);
			}
		} 
		return token;
	}
	
	/**
	 * Parse the result of a request searchSubtitle to an inteligible java object.
	 * @param obj the data to parse
	 * @return the OSubtitle object
	 */
	public OSubtitle convertToOSubtitle(Map<String, Object> obj) {
		// TODO Auto-generated method stub
		OSubtitle osub = new OSubtitle();
		osub.setMatchedBy((String) obj.get("MatchedBy"));
		osub.setIDSubMovieFile((String) obj.get("IDSubMovieFile"));
		osub.setMovieHash((String) obj.get("MovieHash"));
		osub.setMovieByteSize(Long.parseLong((String) obj.get("MovieByteSize")));
		osub.setMovieTimeMS(Long.parseLong((String) obj.get("MovieTimeMS")));
		osub.setIDSubtitleFile((String) obj.get("IDSubtitleFile"));
		osub.setSubFileName((String) obj.get("SubFileName"));
		osub.setSubActualCD((String) obj.get("SubActualCD"));
		osub.setSubSize((String) obj.get("SubSize"));
		osub.setSubHash((String) obj.get("SubHash"));
		osub.setIDSubtitle((String) obj.get("IDSubtitle"));
		osub.setUserID((String) obj.get("UserID"));
		osub.setSubLanguageID((String) obj.get("SubLanguageID"));
		osub.setSubFormat((String) obj.get("SubFormat"));
		osub.setSubSumCD((String) obj.get("SubSumCD"));
		osub.setSubAuthorComment((String) obj.get("SubAuthorComment"));
		osub.setSubAddDate((String) obj.get("SubAddDate"));
		osub.setSubBad(Double.parseDouble((String) obj.get("SubBad")));
		osub.setSubRating(Double.parseDouble((String) obj.get("SubRating")));
		osub.setSubDownloadsCnt((String) obj.get("SubDownloadsCnt"));
		osub.setMovieReleaseName((String) obj.get("MovieReleaseName"));
		osub.setIDMovie((String) obj.get("IDMovie"));
		osub.setIDMovieImdb((String) obj.get("IDMovieImdb"));
		osub.setMovieName((String) obj.get("MovieName"));
		osub.setMovieNameEng((String) obj.get("MovieNameEng"));
		osub.setMovieYear((String) obj.get("MovieYear"));
		osub.setMovieImdbRating(Double.parseDouble((String) obj.get("MovieImdbRating")));
		osub.setISO639((String) obj.get("ISO639"));
		osub.setLanguageName((String) obj.get("LanguageName"));
		osub.setSubComments((String) obj.get("SubComments"));
		osub.setUserRank((String) obj.get("UserRank"));
		osub.setSeriesSeason((String) obj.get("SeriesSeason"));
		osub.setSeriesEpisode((String) obj.get("SeriesEpisode"));
		osub.setMovieKind((String) obj.get("MovieKind"));
		osub.setSubDownloadLink((String) obj.get("SubDownloadLink"));
		osub.setZipDownloadLink((String) obj.get("ZipDownloadLink"));
		osub.setSubtitlesLink((String) obj.get("SubtitlesLink"));
		return osub;
	}
	
	/**
	 * Convert the limit to a data so you can add to the request
	 * @param limit
	 * @return
	 */
	public List<Object> getLimit(int limit) {
		Map<String, Object> search = new HashMap<String, Object>();
		search.put("limit", ""+limit);
		List<Object> l = new ArrayList<Object>();
		l.add(search);
		return l;
	}

	/**
	 * Given a constructed List with Objects obtained by calling the methods getSearchBy* and a valid token
	 * this call executes a full search and return and array of subtitles found
	 * @param token
	 * @param searchArray
	 * @return List of subtitles found, empty if not found
	 * @throws OpenSubtitlesException if something bad occurs
	 */
	public ArrayList<OSubtitle> executeSearch(String token, List<Object> searchArray) throws OpenSubtitlesException {
		ArrayList<OSubtitle> results = new ArrayList<OSubtitle>();
		try {
			List<Object> params = new ArrayList<Object>();
			params.add(token);
			params.add(searchArray);
			Map<String, Object> temp = executeAPI(API.SEARCH, params);
			Log.v("MPB", temp.toString());
			Object resObject = (Object) temp.get("data");
			if (resObject instanceof Object[]) {
				Object[] data = (Object[]) resObject;
				for (Object o : data) {
					results.add(convertToOSubtitle((Map<String, Object>) o));
				}
			} else if (resObject instanceof Boolean) {
				Boolean result = (Boolean) resObject;
				if (!result) {
					Log.v("MPB", "No results found");
				}
			} else {
				throw new Exception("Not know object type: "+ resObject.getClass());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new OpenSubtitlesException("search error", e);
		}
		return results;
	}
	
	/**
	 * Given a movie file and a desired languages, return the needed object to make a request using searchSubtitle method
	 * @param movie
	 * @param languages
	 * @return
	 * @throws IOException
	 */
	public Object getSearchByHash(File movie, String languages) throws IOException {
		Map<String, Object> search = new HashMap<String, Object>();
		search.put("sublanguageid", languages);
		search.put("moviehash", OpenSubtitlesHasher.computeHash(movie));
		search.put("moviebytesize", Double.valueOf(movie.length()));
		return search;
	}
	
	/**
	 * Given a query and a desired languages (optional a season and episode) return the needed object to make a request using searchSubtitle method.
	 * @param query
	 * @param languages
	 * @param season set to -1 if you don't want to set it
	 * @param episode same as season
	 * @return
	 * @throws IOException
	 */
	public Object getSearchByQuery(String query, String languages, int season, int episode) throws IOException {
		Map<String, Object> search = new HashMap<String, Object>();
		search.put("query", query);
		if (season > 0 && episode > 0) {
			search.put("season", ""+season);
			search.put("episode", ""+episode);
		}
		search.put("sublanguageid", languages);
		return search;
	}
	
	
	/**
	 * Given a imdbid and a desired languages, return de needed object to make a request using searchSubtitle method
	 * @param id
	 * @param languages
	 * @return
	 * @throws IOException
	 */
	public Object getSearchByImdbId(String id, String languages) throws IOException {
		Map<String, Object> search = new HashMap<String, Object>();
		search.put("imdbid", id);
		search.put("sublanguageid", languages);
		return search;
	}
	
	/**
	 * Given a valid token and a valid subtitle id found with executeSearch return an array of bytes of the subtitle
	 * @param token
	 * @param id
	 * @return
	 * @throws OpenSubtitlesException
	 */
	public byte[] downloadSubtitle(String token, int id) throws OpenSubtitlesException {
		Map<Integer, byte[]> result = new HashMap<Integer, byte[]>();
		List<Object> params = new ArrayList<Object>();
		List<Integer> ids = new ArrayList<Integer>();
		ids.addAll(Arrays.asList(id));
		params.add(token);
		params.add(ids);
		Map<String, Object> temp = executeAPI(API.DOWNLOAD, params);
		Object[] data = (Object[]) temp.get("data");
		try {
			for (Object o : data) {
				temp = (Map<String, Object>) o;
				byte[] decodedBytes = base64decode((String) temp.get("data"));
				return gunzip(decodedBytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new OpenSubtitlesException("download error", e);
		}
		return null;
	}


	/**
	 * Gived a valid token and an array of id (can be only one id) return 
	 * @param token
	 * @param id
	 * @return
	 * @throws OpenSubtitlesException
	 */
	public Map<Integer, byte[]> downloadArrayOfSubtitles(String token, Integer... id) throws OpenSubtitlesException {
		Map<Integer, byte[]> result = new HashMap<Integer, byte[]>();
		List<Object> params = new ArrayList<Object>();
		List<Integer> ids = new ArrayList<Integer>();
		ids.addAll(Arrays.asList(id));
		params.add(token);
		params.add(ids);
		Map<String, Object> temp = executeAPI(API.DOWNLOAD, params);
		Object[] data = (Object[]) temp.get("data");
		try {
			for (Object o : data) {
				temp = (Map<String, Object>) o;
				byte[] decodedBytes = base64decode((String) temp.get("data"));
				byte[] subtitle = gunzip(decodedBytes);
				result.put(Integer.parseInt((String) temp.get("idsubtitlefile")),subtitle);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new OpenSubtitlesException("download error", e);
		}
		return result;
	}

	
///////////////////////// CODE FOR INTERNAL PROCCESSING ////////////////////////////////////	
	
	private byte[] base64decode(String encoded) throws IOException {
		byte[] output = Base64.decode(encoded);
		return output;
	}

	private byte[] gunzip(byte[] compressed) throws IOException {
		GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(
				compressed));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStream out = new BufferedOutputStream(baos);
		byte[] buffer = new byte[1024];
		while (true) {
			synchronized (buffer) {
				int amountRead = gis.read(buffer);
				if (amountRead == -1) {
					break;
				}
				out.write(buffer, 0, amountRead);
			}
		}
		out.flush();
		out.close();
		return baos.toByteArray();
	}

	private Map<String, Object> executeAPI(API api, List<?> params)
			throws OpenSubtitlesException {
		Map<String, Object> result = null;
		try {
			result = (Map) getClient().execute(api.toString(), params);
			String statusString = (String) result.get("status");
			OSStatus status = OSStatus.fromCode(getCode(statusString));
			if (!status.isSuccess()) {
				throw new OpenSubtitlesException(statusString);
			}
		} catch (Exception ex) {
			throw new OpenSubtitlesException("excuteAPI error: " + api, ex);
		}
		return result;
	}

	private String getCode(String statusMessage) {
		return statusMessage.split(" ", 2)[0];
	}

	/**
	 * @return the client
	 */
	private XmlRpcClient getClient() {
		return client;
	}

	private enum API {

		LOGIN("LogIn"), SEARCH("SearchSubtitles"), DOWNLOAD("DownloadSubtitles"), SEARCH_IMDB(
				"SearchMoviesOnIMDB"), LOGOUT("LogOut");
		private String functionName;

		API(String functionName) {
			this.functionName = functionName;
		}

		@Override
		public String toString() {
			return functionName;
		}
	}
}
