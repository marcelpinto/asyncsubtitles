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
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.LongBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import android.util.Log;


/**
 * Class containing methods for reading and writing the list of ignored and
 * processed folders for internal use.
 */
public class OpenSubtitles {

   /** The logger instance that will provide all the logger functionality
    */
    private static Logger logger = Logger.getLogger("downbox");
   /** The API url of the server
    */
    private static String OSDB_SERVER = "http://api.opensubtitles.org/xml-rpc";
   /** The token returned by the server
    */
    private static String strToken = null;

   /**
    * Login to the server
    */
    protected static void logIn() throws Exception {

       logger.info("Logging in to server...");

        XmlRpcClientConfigImpl rpcConfig = new XmlRpcClientConfigImpl();
        rpcConfig.setServerURL(new URL(OSDB_SERVER));
        XmlRpcClient rpcClient = new XmlRpcClient();
        rpcClient.setConfig(rpcConfig);
        Object[] objParams = new Object[]{"", "", "eng", "OS Test User Agent"};
        HashMap<?, ?> mapResult = (HashMap<?, ?>) rpcClient.execute("LogIn", objParams);
        Log.v("MPB", "TOKEN: "+mapResult.toString());
        strToken = (String) mapResult.get("token");
        Log.v("MPB", "TOKEN: "+strToken);
       logger.fine("Done.");

    }
    
    /**
	 * Hash code is based on Media Player Classic. In natural language it
	 * calculates: size + 64bit checksum of the first and last 64k (even if they
	 * overlap because the file is smaller than 128k).
	 */
	public static class OpenSubtitlesHasher {

		/**
		 * Size of the chunks that will be hashed in bytes (64 KB)
		 */
		private static final int HASH_CHUNK_SIZE = 64 * 1024;

		public static String computeHash(File file)	throws IOException {
			long size = file.length();
			long chunkSizeForFile = Math.min(HASH_CHUNK_SIZE, size);

			FileChannel fileChannel = new FileInputStream(file).getChannel();

			try {
				long head = computeHashForChunk(fileChannel.map(
						MapMode.READ_ONLY, 0, chunkSizeForFile));
				long tail = computeHashForChunk(fileChannel.map(
						MapMode.READ_ONLY, Math.max(size - HASH_CHUNK_SIZE, 0),
						chunkSizeForFile));

				return String.format("%016x", size + head + tail);
			} finally {
				fileChannel.close();
			}
		}

		public static String computeHash(InputStream stream, long length)
				throws IOException {

			int chunkSizeForFile = (int) Math.min(HASH_CHUNK_SIZE, length);

			// buffer that will contain the head and the tail chunk, chunks will
			// overlap if length is smaller than two chunks
			byte[] chunkBytes = new byte[(int) Math.min(2 * HASH_CHUNK_SIZE,
					length)];

			DataInputStream in = new DataInputStream(stream);

			// first chunk
			in.readFully(chunkBytes, 0, chunkSizeForFile);

			long position = chunkSizeForFile;
			long tailChunkPosition = length - chunkSizeForFile;

			// seek to position of the tail chunk, or not at all if length is
			// smaller than two chunks
			while (position < tailChunkPosition
					&& (position += in.skip(tailChunkPosition - position)) >= 0)
				;

			// second chunk, or the rest of the data if length is smaller than
			// two chunks
			in.readFully(chunkBytes, chunkSizeForFile, chunkBytes.length
					- chunkSizeForFile);

			long head = computeHashForChunk(ByteBuffer.wrap(chunkBytes, 0,
					chunkSizeForFile));
			long tail = computeHashForChunk(ByteBuffer.wrap(chunkBytes,
					chunkBytes.length - chunkSizeForFile, chunkSizeForFile));

			return String.format("%016x", length + head + tail);
		}

		private static long computeHashForChunk(ByteBuffer buffer) {

			LongBuffer longBuffer = buffer.order(ByteOrder.LITTLE_ENDIAN)
					.asLongBuffer();
			long hash = 0;

			while (longBuffer.hasRemaining()) {
				hash += longBuffer.get();
			}

			return hash;
		}

	}

   /**
    * Searches for a subtitle
    *
    * @param   filVideoFile   the video file which to download subtitles
    */
    public static void searchAndDownloadSubtitles(File filVideoFile) throws Exception {

        //Let's log in
        try {

            logIn();
        } catch (Exception e)  {
            throw e;
        }


        //Let's generate the hash of the movie file. This is a custom hashing
        //algorithm used by OpenSubtitles.

        
        
        // Let's search for matching subtitles
        URL urlSubtitle = null;

        try {
        	String strHash = OpenSubtitlesHasher.computeHash(filVideoFile);
           logger.info(String.format("Searching for subtiles: %s, %s", strHash, filVideoFile.length()));

            XmlRpcClientConfigImpl rpcConfig = new XmlRpcClientConfigImpl();
            rpcConfig.setServerURL(new URL(OSDB_SERVER));
            XmlRpcClient rpcClient = new XmlRpcClient();
            rpcClient.setConfig(rpcConfig);

            Map<String, Object> mapQuery = new HashMap<String, Object>();
            mapQuery.put("sublanguageid", "esp");
            mapQuery.put("moviehash", "7d9cd5def91c9432");
            mapQuery.put("moviebytesize", "735934464");
            //mapQuery.put("imdbid", "");

            Object[] objParams = new Object[]{strToken, new Object[]{mapQuery}};
            HashMap response = (HashMap) rpcClient.execute("SearchSubtitles", objParams);
            Log.v("MPB", "Response: "+response.toString());

            //urlSubtitle = new URL((String) mapResult.get("SubDownloadLink"));

           logger.fine("Done.");

        } catch (Exception e)  {
        	e.printStackTrace();
           logger.warning(String.format("Error: %s", e.toString()));
           return;
        }


        // Now that we have the URL, we can download the file. The file is in
        // the GZIP format so we have to uncompress it.
        File filSubtitleFile = new File(filVideoFile.getPath().substring(0, filVideoFile.getPath().length() - 4));

        HttpURLConnection objConnection = null;
        FileOutputStream objOutputStream = null;
        GZIPInputStream objGzipInputStream = null;

        try {

           objConnection = (HttpURLConnection)((urlSubtitle).openConnection());
           objOutputStream = new FileOutputStream(filSubtitleFile);
           objGzipInputStream = new GZIPInputStream(objConnection.getInputStream());

            logger.info(String.format("Downloading the subtitle: %s", urlSubtitle));

            if (objConnection.getResponseCode() != 200) {
                logger.finest("The server did not respond properly");
            }

            Integer intLength = 0;
            byte[] bytBuffer = new byte[1024];

            objOutputStream.close();
            filSubtitleFile.delete();
            if (objConnection.getHeaderField("Content-Disposition").isEmpty() == false) {
                filSubtitleFile = new File(filSubtitleFile.getPath() + "." + FileExtention.getExtention(objConnection));
            }

            objOutputStream.close();
            objOutputStream = new FileOutputStream(filSubtitleFile);
            while ((intLength = objGzipInputStream.read(bytBuffer)) > 0) {
                objOutputStream.write(bytBuffer, 0, intLength);
            }
            objConnection.disconnect();

           logger.fine("Downloaded.");

        } catch (Exception e) {
           logger.warning(String.format("Error: %s", e.toString()));
        } finally {
            objOutputStream.close();
            objGzipInputStream.close();
        }


        //Let's log out
        try {

            logOut();

        } catch (Exception e)  {
            return;
        }

    }


   /**
    * Logout from the the server
    */
    private static void logOut() throws Exception {

       logger.info("Logging out of server...");

        XmlRpcClientConfigImpl rpcConfig = new XmlRpcClientConfigImpl();
        rpcConfig.setServerURL(new URL(OSDB_SERVER));
        XmlRpcClient rpcClient = new XmlRpcClient();
        rpcClient.setConfig(rpcConfig);
        Object[] objParams = new Object[]{strToken};
        rpcClient.execute("LogOut", objParams);
        strToken = null;

       logger.fine("Done.");

    }

}