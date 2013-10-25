Asyncsubtitles
==============

Library to get in a easy way subtitles from  [www.opensubtitle.org][1]

This lib is based on the project of opensubtitle [info][2]
It's a wrapper to use the methods of XMLRPC that opensubtitles offer.


With just few lines you can get a list of subtitles and download them.

Init the API and set the desired params:

-----
```java
  mASub = new AsyncSubtitles(this, this);
  mASub.setLanguagesArray(new String[] { "spa" });
  mASub.setNeededParamsToSearch(null, "Family guy", null, 10, 2);
  mASub.getPossibleSubtitle();
```
Implements the listener to get the list.

```java
  @Override
	public void onSubtitlesListFound(List<OSubtitle> list) {
	  //Return the list of subtitles found
	  // Let's download the first item to the desired path
	  mASub.downloadSubByIdToPath(list.get(0).getIDSubtitleFile(), 
	  	Environment.getExternalStorageDirectory().getAbsolutePath()+"/familyguy.srt");
	}
```
	
Everything is asyncron so you can call it on your main thread. To see a better example look at the demo.


[1]: http://www.opensubtitle.org
[2]: http://trac.opensubtitles.org/projects/opensubtitles/wiki/XMLRPC#Instructions
