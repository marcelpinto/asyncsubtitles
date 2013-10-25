package com.hardsoft.asyncsubtitles.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.os.Environment;

public class FileUtils {
	
	public static boolean isStreaming(String fileName, boolean str) {
		// TODO Auto-generated method stub
		boolean res = fileName.endsWith(".mpg") || fileName.endsWith(".mpeg") || fileName.endsWith(".avi") || fileName.endsWith(".mkv");
		if (!str)
			res = res || fileName.endsWith(".mp4") || fileName.endsWith(".mp3") || fileName.endsWith(".wav") || fileName.endsWith(".3gp") || fileName.endsWith(".ogv");
		return res;
				
	}
	
	public static ArrayList<String> getListFileFromDir(String dir) {
		ArrayList<String> result = new ArrayList<String>();
		File file = new File(dir);
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			result.add(files[i].getName());
		}
		return result;
	}
	
	public static Collection<File> listFileTree(File dir) {
	    Set<File> fileTree = new HashSet<File>();
	    if (dir==null) return null;
	    for (File entry : dir.listFiles()) {
	        if (entry.isFile()) fileTree.add(entry);
	        else if (entry.isDirectory()) fileTree.addAll(listFileTree(entry));
	    }
	    return fileTree;
	}
	
	public static List<File> collectionToArray(Collection<File> coll) {
		List<File> list;
		if (coll instanceof List)
		  list = (List<File>)coll;
		else
		  list = new ArrayList<File>(coll);
		Collections.sort(list); 
		return list;
	}
	
}
