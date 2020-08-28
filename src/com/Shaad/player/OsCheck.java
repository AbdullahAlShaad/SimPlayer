package com.Shaad.player;

import java.util.Locale;

public final class OsCheck {
	/**
	 * Thins functions check operating system and return the name of operating system
	 * @return
	 */
  
  public static String getOperatingSystemType() {
    
      String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
      
      if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0))
      {
           return "Mac";
      } 
      else if (OS.indexOf("win") >= 0)
      {
    	  	return "Windows";
      } 
      else if (OS.indexOf("nux") >= 0) 
      {
    	  	return "Linux";
      } 
      else
      {
            return "Other";
      }
   
  }
}