package com.hardsoft.asyncsubtitles;

import java.io.Serializable;

/**
 * 	This is the Main object to parse the result of a subtitle search. 
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
public class OSubtitle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8903482565859391049L;
	private String MatchedBy;
	private String IDSubMovieFile;
	private String MovieHash;
	private long MovieByteSize;
	private long MovieTimeMS;
	private String IDSubtitleFile;
	private String SubFileName;
	private String SubActualCD;
	private String SubSize;
	private String SubHash;
	private String IDSubtitle;
	private String UserID;
	private String SubLanguageID;
	private String SubFormat;
	private String SubSumCD;
	private String SubAuthorComment;
	private String SubAddDate;
	private double SubBad;
	private double SubRating;
	private String SubDownloadsCnt;
	private String MovieReleaseName;
	private String IDMovie;
	private String IDMovieImdb;
	private String MovieName;
	private String MovieNameEng;
	private String MovieYear;
	private double MovieImdbRating;
	private String ISO639;
	private String LanguageName;
	private String SubComments;
	private String UserRank;
	private String SeriesSeason;
	private String SeriesEpisode;
	private String MovieKind;
	private String SubDownloadLink;
	private String ZipDownloadLink;
	private String SubtitlesLink;
	public String getMatchedBy() {
		return MatchedBy;
	}
	public void setMatchedBy(String matchedBy) {
		MatchedBy = matchedBy;
	}
	public String getIDSubMovieFile() {
		return IDSubMovieFile;
	}
	public void setIDSubMovieFile(String iDSubMovieFile) {
		IDSubMovieFile = iDSubMovieFile;
	}
	public String getMovieHash() {
		return MovieHash;
	}
	public void setMovieHash(String movieHash) {
		MovieHash = movieHash;
	}
	public long getMovieByteSize() {
		return MovieByteSize;
	}
	public void setMovieByteSize(long movieByteSize) {
		MovieByteSize = movieByteSize;
	}
	public long getMovieTimeMS() {
		return MovieTimeMS;
	}
	public void setMovieTimeMS(long movieTimeMS) {
		MovieTimeMS = movieTimeMS;
	}
	public String getIDSubtitleFile() {
		return IDSubtitleFile;
	}
	public void setIDSubtitleFile(String iDSubtitleFile) {
		IDSubtitleFile = iDSubtitleFile;
	}
	public String getSubFileName() {
		return SubFileName;
	}
	public void setSubFileName(String subFileName) {
		SubFileName = subFileName;
	}
	public String getSubActualCD() {
		return SubActualCD;
	}
	public void setSubActualCD(String subActualCD) {
		SubActualCD = subActualCD;
	}
	public String getSubSize() {
		return SubSize;
	}
	public void setSubSize(String subSize) {
		SubSize = subSize;
	}
	public String getSubHash() {
		return SubHash;
	}
	public void setSubHash(String subHash) {
		SubHash = subHash;
	}
	public String getIDSubtitle() {
		return IDSubtitle;
	}
	public void setIDSubtitle(String iDSubtitle) {
		IDSubtitle = iDSubtitle;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getSubLanguageID() {
		return SubLanguageID;
	}
	public void setSubLanguageID(String subLanguageID) {
		SubLanguageID = subLanguageID;
	}
	public String getSubFormat() {
		return SubFormat;
	}
	public void setSubFormat(String subFormat) {
		SubFormat = subFormat;
	}
	public String getSubSumCD() {
		return SubSumCD;
	}
	public void setSubSumCD(String subSumCD) {
		SubSumCD = subSumCD;
	}
	public String getSubAuthorComment() {
		return SubAuthorComment;
	}
	public void setSubAuthorComment(String subAuthorComment) {
		SubAuthorComment = subAuthorComment;
	}
	public String getSubAddDate() {
		return SubAddDate;
	}
	public void setSubAddDate(String subAddDate) {
		SubAddDate = subAddDate;
	}
	public double getSubBad() {
		return SubBad;
	}
	public void setSubBad(double subBad) {
		SubBad = subBad;
	}
	public double getSubRating() {
		return SubRating;
	}
	public void setSubRating(double subRating) {
		SubRating = subRating;
	}
	public String getSubDownloadsCnt() {
		return SubDownloadsCnt;
	}
	public void setSubDownloadsCnt(String subDownloadsCnt) {
		SubDownloadsCnt = subDownloadsCnt;
	}
	public String getMovieReleaseName() {
		return MovieReleaseName;
	}
	public void setMovieReleaseName(String movieReleaseName) {
		MovieReleaseName = movieReleaseName;
	}
	public String getIDMovie() {
		return IDMovie;
	}
	public void setIDMovie(String iDMovie) {
		IDMovie = iDMovie;
	}
	public String getIDMovieImdb() {
		return IDMovieImdb;
	}
	public void setIDMovieImdb(String iDMovieImdb) {
		IDMovieImdb = iDMovieImdb;
	}
	public String getMovieName() {
		return MovieName;
	}
	public void setMovieName(String movieName) {
		MovieName = movieName;
	}
	public String getMovieNameEng() {
		return MovieNameEng;
	}
	public void setMovieNameEng(String movieNameEng) {
		MovieNameEng = movieNameEng;
	}
	public String getMovieYear() {
		return MovieYear;
	}
	public void setMovieYear(String movieYear) {
		MovieYear = movieYear;
	}
	public double getMovieImdbRating() {
		return MovieImdbRating;
	}
	public void setMovieImdbRating(double movieImdbRating) {
		MovieImdbRating = movieImdbRating;
	}
	public String getISO639() {
		return ISO639;
	}
	public void setISO639(String iSO639) {
		ISO639 = iSO639;
	}
	public String getLanguageName() {
		return LanguageName;
	}
	public void setLanguageName(String languageName) {
		LanguageName = languageName;
	}
	public String getSubComments() {
		return SubComments;
	}
	public void setSubComments(String subComments) {
		SubComments = subComments;
	}
	public String getUserRank() {
		return UserRank;
	}
	public void setUserRank(String userRank) {
		UserRank = userRank;
	}
	public String getSeriesSeason() {
		return SeriesSeason;
	}
	public void setSeriesSeason(String seriesSeason) {
		SeriesSeason = seriesSeason;
	}
	public String getSeriesEpisode() {
		return SeriesEpisode;
	}
	public void setSeriesEpisode(String seriesEpisode) {
		SeriesEpisode = seriesEpisode;
	}
	public String getMovieKind() {
		return MovieKind;
	}
	public void setMovieKind(String movieKind) {
		MovieKind = movieKind;
	}
	public String getSubDownloadLink() {
		return SubDownloadLink;
	}
	public void setSubDownloadLink(String subDownloadLink) {
		SubDownloadLink = subDownloadLink;
	}
	public String getZipDownloadLink() {
		return ZipDownloadLink;
	}
	public void setZipDownloadLink(String zipDownloadLink) {
		ZipDownloadLink = zipDownloadLink;
	}
	public String getSubtitlesLink() {
		return SubtitlesLink;
	}
	public void setSubtitlesLink(String subtitlesLink) {
		SubtitlesLink = subtitlesLink;
	}
	@Override
	public String toString() {
		return "OSubtitle ["
				+ (MatchedBy != null ? "MatchedBy=" + MatchedBy + ", " : "")
				+ (IDSubMovieFile != null ? "IDSubMovieFile=" + IDSubMovieFile
						+ ", " : "")
				+ (MovieHash != null ? "MovieHash=" + MovieHash + ", " : "")
				+ "MovieByteSize="
				+ MovieByteSize
				+ ", MovieTimeMS="
				+ MovieTimeMS
				+ ", "
				+ (IDSubtitleFile != null ? "IDSubtitleFile=" + IDSubtitleFile
						+ ", " : "")
				+ (SubFileName != null ? "SubFileName=" + SubFileName + ", "
						: "")
				+ (SubActualCD != null ? "SubActualCD=" + SubActualCD + ", "
						: "")
				+ (SubSize != null ? "SubSize=" + SubSize + ", " : "")
				+ (SubHash != null ? "SubHash=" + SubHash + ", " : "")
				+ (IDSubtitle != null ? "IDSubtitle=" + IDSubtitle + ", " : "")
				+ (UserID != null ? "UserID=" + UserID + ", " : "")
				+ (SubLanguageID != null ? "SubLanguageID=" + SubLanguageID
						+ ", " : "")
				+ (SubFormat != null ? "SubFormat=" + SubFormat + ", " : "")
				+ (SubSumCD != null ? "SubSumCD=" + SubSumCD + ", " : "")
				+ (SubAuthorComment != null ? "SubAuthorComment="
						+ SubAuthorComment + ", " : "")
				+ (SubAddDate != null ? "SubAddDate=" + SubAddDate + ", " : "")
				+ "SubBad="
				+ SubBad
				+ ", SubRating="
				+ SubRating
				+ ", "
				+ (SubDownloadsCnt != null ? "SubDownloadsCnt="
						+ SubDownloadsCnt + ", " : "")
				+ (MovieReleaseName != null ? "MovieReleaseName="
						+ MovieReleaseName + ", " : "")
				+ (IDMovie != null ? "IDMovie=" + IDMovie + ", " : "")
				+ (IDMovieImdb != null ? "IDMovieImdb=" + IDMovieImdb + ", "
						: "")
				+ (MovieName != null ? "MovieName=" + MovieName + ", " : "")
				+ (MovieNameEng != null ? "MovieNameEng=" + MovieNameEng + ", "
						: "")
				+ (MovieYear != null ? "MovieYear=" + MovieYear + ", " : "")
				+ "MovieImdbRating="
				+ MovieImdbRating
				+ ", "
				+ (ISO639 != null ? "ISO639=" + ISO639 + ", " : "")
				+ (LanguageName != null ? "LanguageName=" + LanguageName + ", "
						: "")
				+ (SubComments != null ? "SubComments=" + SubComments + ", "
						: "")
				+ (UserRank != null ? "UserRank=" + UserRank + ", " : "")
				+ (SeriesSeason != null ? "SeriesSeason=" + SeriesSeason + ", "
						: "")
				+ (SeriesEpisode != null ? "SeriesEpisode=" + SeriesEpisode
						+ ", " : "")
				+ (MovieKind != null ? "MovieKind=" + MovieKind + ", " : "")
				+ (SubDownloadLink != null ? "SubDownloadLink="
						+ SubDownloadLink + ", " : "")
				+ (ZipDownloadLink != null ? "ZipDownloadLink="
						+ ZipDownloadLink + ", " : "")
				+ (SubtitlesLink != null ? "SubtitlesLink=" + SubtitlesLink
						: "") + "]";
	}
	
	

}
