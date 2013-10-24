package com.hardsoft.asyncsubtitles.demo;

/*
 *  (c) Copyright (c) 2012 Mridang Agarwalla
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
import java.net.*;


/**
 * Class to extract the file extentions from a filename.
 */
public class FileExtention {

   /**
    * Checks is a file is a subtitle file.
    *
    * @param    strFilename   the file which to check
    * @returns                a boolean value indicating the match
    */
    public static Boolean isSubtitleFile(String strFilename) {

        try {

            for (String strExtention : ApplicationData.getProperty("filename.subtitle.types").split(", ")) {
                if (getExtention(strFilename).equalsIgnoreCase(strExtention)) {
                    return true;
                }
            }
            return false;


        } catch (Exception e) {
            return null;
        }

    }


   /**
    * Checks is a file is a video file.
    *
    * @param    strFilename   the file which to check
    * @returns                a boolean value indicating the match
    */
    public static Boolean isVideoFile(String strFilename) {

        try {

            for (String strExtention : ApplicationData.getProperty("filename.video.types").split(", ")) {
                if (getExtention(strFilename).equalsIgnoreCase(strExtention)) {
                    return true;
                }
            }
            return false;


        } catch (Exception e) {
            return null;
        }

    }


   /**
    * Extracts the file extension from a file.
    *
    * @param    strFilename   the file from which to extract the extension
    */
    public static String getExtention(String strFilename) {

        try {

            Integer intDot = strFilename.lastIndexOf(".");
            String strExtention = strFilename.substring(intDot + 1, strFilename.length());

            return strExtention;

        } catch (Exception e) {
            return null;
        }

    }


   /**
    * Extracts the file extension from a Content-Disposition header
    *
    * @param  webConnection   the connection from which to extract the header
    */
    public static String getExtention(HttpURLConnection webConnection) {

        try {

            String strContentDispositon = webConnection.getHeaderField("Content-Disposition");
            String strExtention = strContentDispositon.replaceAll(".*\\.([a-z]{3})\\..*", "$1");

            return strExtention;

        } catch (Exception e) {
            return null;
        }

    }

   /**
    * Checks is a file is an audio file.
    *
    * @param    strFilename   the file which to check
    * @returns                a boolean value indicating the match
    */
    public static Boolean isAudioFile(String strFilename) {

        try {

            for (String strExtention : ApplicationData.getProperty("filename.audio.types").split(", ")) {
                if (getExtention(strFilename).equalsIgnoreCase(strExtention)) {
                    return true;
                }
            }
            return false;

        } catch (Exception e) {
            return null;
        }

    }


   /**
    * Checks is a file is a playlist file.
    *
    * @param    strFilename   the file which to check
    * @returns                 a boolean value indicating the match
    */
    public static Boolean isPlaylistFile(String strFilename) {

        try {

            for (String strExtention : ApplicationData.getProperty("filename.playlist.types").split(", ")) {
                if (getExtention(strFilename).equalsIgnoreCase(strExtention)) {
                    return true;
                }
            }
            return false;


        } catch (Exception e) {
            return null;
        }

    }

}
