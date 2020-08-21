package com.Shaad.player;


import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;

public class FindSongs {
	static ArrayList<File> songs = new ArrayList();
	
	
	/**
	 * Given home directory as argument , this runs a dfs search over 
	 * all the directoires and find mp3 songs. Store those
	 * mp3 songs in ArrayList as File type
	 * 
	 * @param dir
	 * @return
	 */
	public static ArrayList<File> extract(File dir) {
			
			
		    File listFiles[] = dir.listFiles();
		    
		    if (listFiles == null) {
		        //Some error in the file system
		    	JOptionPane.showMessageDialog(null, "Could not Find Songs");
		        return songs;
		        
		    }
		    
	
		    for (File cur : listFiles) {
		    	String name = cur.getName();
		    
		    	if(name.startsWith("."))
		    	{
		    		//Most likely system directory
		    		//System ditectories starts with .
		    		continue;
		    	}
		    	// If cur is directory , we go to its child(folders and files inside it)
		        if (cur.isDirectory() )
		        {
		            extract(cur);
		        }
		        if (cur.isHidden() || !cur.canRead()) 
		        {
		        	//Most likely system directory
		        	continue;
		        }
		        // If song name ends with .mp3 or .wav , we add it in the list
		        else if (name.endsWith(".mp3") || name.endsWith(".wav"))
		        {
		            songs.add(cur);
		        }
		    }
		    return songs;
	
		}
		

}
