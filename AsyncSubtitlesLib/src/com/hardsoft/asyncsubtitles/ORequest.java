package com.hardsoft.asyncsubtitles;

import java.io.Serializable;
import java.util.Arrays;

public class ORequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2397879775083845172L;
	private String filePath;
	private String query;
	private String imdbid;
	private String[] languages;
	
	public ORequest(String filePath, String query, String imdbid,
			String[] languages) {
		super();
		this.filePath = filePath;
		this.query = query;
		this.imdbid = imdbid;
		this.languages = languages;
	}
	
	public String getFilePath() {
		if (filePath==null)
			return "";
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getQuery() {
		if (query==null)
			return "";
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getImdbid() {
		if (imdbid==null)
			return "";
		return imdbid;
	}

	public void setImdbid(String imdbid) {
		this.imdbid = imdbid;
	}

	public String[] getLanguages() {
		if (languages==null || languages.length<=0)
			return new String[]{"all"};
		return languages;
	}
	public void setLanguages(String[] languages) {
		this.languages = languages;
	}

	@Override
	public String toString() {
		return "ORequest ["
				+ (filePath != null ? "filePath=" + filePath + ", " : "")
				+ (query != null ? "query=" + query + ", " : "")
				+ (imdbid != null ? "imdbid=" + imdbid + ", " : "")
				+ (languages != null ? "languages="
						+ Arrays.toString(languages) : "") + "]";
	}
	
	

}
