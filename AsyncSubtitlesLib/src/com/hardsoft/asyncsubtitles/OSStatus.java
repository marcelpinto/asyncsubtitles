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

public enum OSStatus {

	OK("200", "OK"), PARTIAL_CONTENT("206", "Partial content; message"), MOVED(
			"301", "Moved (host)"), UNAUTHORIZED("401", "Unauthorized"), INVALID_FORMAT(
			"402", "Subtitles has invalid format"), HASH_NOT_MATCHING("403",
			"SubHashes (content and sent subhash) are not same!"), INVALID_LANGUAGE(
			"404", "Subtitles has invalid language!"), MISSING_PARAMETERS(
			"405", "Not all mandatory parameters was specified"), NO_SESSION(
			"406", "No session"), DOWNLOAD_LIMIT("407",
			"Download limit reached"), INVALID_PARAMETERS("408",
			"Invalid parameters"), INVALID_METHOD("409", "Method not found"), UNKNOWN_ERROR(
			"410", "Other or unknown error"), INVALID_USER_AGENT("411",
			"Empty or invalid useragent"), INVALID_FIELD_FORMAT("412",
			"%s has invalid format (reason)"), INVALID_IMDB_ID("413",
			"Invalid ImdbID"), UNKNOWN_USER_AGENT("414", "Unknown User Agent"), DISABLED_USER_AGENT(
			"415", "Disabled user agent"), SERVICE_UNAVAILABLE("503",
			"Service Unavailable");

	private String code;
	private String description;

	OSStatus(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public boolean isSuccess() {
		if (code.startsWith("2")) {
			return true;
		} else {
			return false;
		}
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return code;
	}

	public static OSStatus fromCode(String code) {
		OSStatus result = null;
		for (OSStatus s : OSStatus.values()) {
			if (s.getCode().equals(code)) {
				result = s;
				break;
			}
		}
		return result;
	}
}
