Asyncsubtitles
==============

Library to get in a easy way subtitles from www.opensubtitle.org 

This lib is based with the project http://trac.opensubtitles.org/projects/opensubtitles/wiki/XMLRPC#Instructions
It's a wrapper to use the methods of XMLRPC that opensubtitles offer.


With just few lines you can get a list of subtitles and download them.

Init the API and set the desired params:

<xmp>
  mASub = new AsyncSubtitles(this, this);
  mASub.setLanguagesArray(new String[] { "spa" });
  mASub.setNeededParamsToSearch(null, "Family guy", null, 10, 2);
  mASub.getPossibleSubtitle();
</xmp>

Implements the listener to get the list.

<xmp>
  @Override
	public void onSubtitlesListFound(List<OSubtitle> list) {
	  //Return the list of subtitles found
	  // Let's download the first item to the desired path
	  mASub.downloadSubByIdToPath(list.get(0).getIDSubtitleFile(), Environment.getExternalStorageDirectory().getAbsolutePath()+"/familyguy.srt");
	}
</xmp>	
	
Everything is asyncron so you can call it on your main thread. To see a better example look at the demo.
