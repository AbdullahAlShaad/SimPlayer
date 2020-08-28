package com.Shaad.player;


import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.JOptionPane;

public class FindSongs {
	static ArrayList<File> songs = new ArrayList();
	
	
	/**
	 * Given home directory as argument , this runs a dfs search over 
	 * all the directoires and find mp3 songs. Store those
	 * mp3 songs path in ArrayList as File type
	 * 
	 * @param dir
	 * @return array of string
	 */
	
	public static Set < String > set = new HashSet<String> ();
	
	//Some system directories on windows,mac and linux systems. Will not traverse them for mp3 songs
	public static void init()
	{
		set.add("lib");
		set.add("system");
		set.add("bin");
		set.add("temp");
		set.add("boot");
		set.add("etc");
		set.add("dev");
		set.add("opt");
		set.add("proc");
		set.add("usr");
		set.add("var");
		set.add("library");
		set.add("program files");
		set.add("program files (x86)");
		set.add("windows");
	}

	
	
	
	public static void extract(File dir) {
			
			
		    File listFiles[] = dir.listFiles();
		    
		    if (listFiles == null) {
		        //Some error in the file system
		    	//JOptionPane.showMessageDialog(null, dir.getAbsolutePath());
		        return ;
		        
		    }
		    
	
		    for (File cur : listFiles) {
		    	String name = cur.getName();
		    	String low = name.toLowerCase();
		    
		    	//Most likey system directory
		    	if(name.startsWith(".") || set.contains(low))
		    	{
		    		continue;
		    	}
		    	
		    	
		    	// If cur is directory , we go to its child(folders and files inside it)
		        if (cur.isDirectory() )
		        {
		            extract(cur);
		        }
		        
		        if (cur.isHidden() || !cur.canRead()) 
		        {
		        	continue;
		        }
		        // If song name ends with .mp3 or .wav , we add it in the list
		        else if (name.endsWith(".mp3") || name.endsWith(".wav"))
		        {
		            songs.add(cur);
		        }
		    }
	
		}
		

}
