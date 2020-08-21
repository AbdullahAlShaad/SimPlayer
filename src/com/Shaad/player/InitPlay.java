package com.Shaad.player;
import java.io.FileInputStream;

import javax.swing.JLabel;

import javazoom.jl.decoder.JavaLayerException;

public class InitPlay {
	
	public static int cs ;
	private  String path;
	public static SongPlayer now;
	JLabel curSong;
	/*
	 * select the songs index and pass the JLabel window.
	 */
	public void select(int cur, JLabel curSong)
	{
		
		cs = cur;
		this.curSong = curSong;
	}
	
	/*
	 * Initialize the file path to the player
	 */
	public  void initialize() {
			path = MainWindow.songs.get(cs).getPath();
			String songName = MainWindow.songs.get(cs).getName();
			curSong.setText(songName);
			try {
				FileInputStream here = new FileInputStream(path);
				now = new SongPlayer(here);
			} catch (final Exception e) {
				e.printStackTrace();
			}
	}
	/*
	 * Play if a song is initialized and resume if a song is paused
	 */
	public void play() {
		
		now.play(curSong);
		
	}
	//Resume current song
	public void resume() {
		
		now.resume();
	}
	//Pause current song
	public void pause() {
		
		now.pause();
		
	}
	//Initialize and play next song
	public void nextPlay()
	{
		cs++;
		if(cs >= MainWindow.songs.size())cs = 0;
		now.close();
		initialize();
		play();
	}
	//Initialize next song
	public  void next() {
		cs++;
		if(cs >= MainWindow.songs.size())cs = 0;
		now.close();
		initialize();
		
	}
	//Initialize and play previous song
	public void prevPlay()
	{
		cs--;
		if(cs < 0) cs = MainWindow.songs.size() - 1;
		now.close();
		initialize();
		play();
	}
	//Initialize previous song
	public void prev() {
		cs--;
		if(cs < 0) cs = MainWindow.songs.size() - 1;
		now.close();
		initialize();
		//play();
		
	}
	//Initialize and play randomly selected song from song list
	
	public void playRandom(int cur) {
		// TODO Auto-generated method stub
		cs = cur;
		now.close();
		initialize();
		play();
	}
	

}
