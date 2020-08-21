/**
 * The main idea of this class is taken from 
https://stackoverflow.com/questions/12057214/jlayer-pause-and-resume-song/23937027
 */

package com.Shaad.player;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.JLabel;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.Player;

public class SongPlayer {

    private final static int NOTSTARTED = 0;
    private final static int PLAYING = 1;
    private final static int PAUSED = 2;
    private final static int FINISHED = 3;

    // the player actually doing all the work
    private final Player player;

    // locking object used to communicate with player thread
    private final Object playerLock = new Object();

    // status variable what player thread is doing/supposed to do
    private int playerStatus = NOTSTARTED;

    public SongPlayer(final InputStream inputStream) throws JavaLayerException  {
        this.player = new Player(inputStream);
    }

   
    /**
     * Starts playback (resumes if paused)
     * Receive JLabel as argument to change to song name and playing status
     */
    public void play(JLabel curSong)  {
        synchronized (playerLock) {
            switch (playerStatus) {
                case NOTSTARTED:
                    final Runnable r = new Runnable() {
                        public void run() {
                            playInternal(curSong);
                        }
                    };
                    final Thread t = new Thread(r);
                    t.setDaemon(true);
                    t.setPriority(Thread.MAX_PRIORITY);
                    playerStatus = PLAYING;    
                    t.start();
                    break;
                case PAUSED:
                    resume();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Pauses playback. Returns true if new state is PAUSED.
     */
    public boolean pause() {
        synchronized (playerLock) {
            if (playerStatus == PLAYING) {
                playerStatus = PAUSED;
            }
            return playerStatus == PAUSED;
        }
    }

    /**
     * Resumes playback. Returns true if the new state is PLAYING.
     */
    public boolean resume() {
        synchronized (playerLock) {
            if (playerStatus == PAUSED) {
                playerStatus = PLAYING;
                playerLock.notifyAll();
            }
            return playerStatus == PLAYING;
        }
    }

    /**
     * Stops playback. If not playing, does nothing
     */
    public void stop() {
        synchronized (playerLock) {
            playerStatus = FINISHED;
            playerLock.notifyAll();
        }
    }
    /**
     * Receive a JLabel argument to display the current song name
     * 
     */
    private void playInternal(JLabel curSong) {
        while (playerStatus != FINISHED) {
            try {
            	String songName = MainWindow.songs.get(InitPlay.cs).getName();
            	curSong.setText("Now Playing : "+songName);
                if (!player.play(1)) {
                	/**
                	 * Current song is finished . Now start the next song
                	 */
                	close();
                	InitPlay.cs++;
                	if(InitPlay.cs >= MainWindow.songs.size())InitPlay.cs = 0;
                	String path = MainWindow.songs.get(InitPlay.cs).getPath();
                	try {
            			FileInputStream here = new FileInputStream(path);
            			InitPlay.now = new SongPlayer(here);
            		} catch (final Exception e) {
            			e.printStackTrace();
            		}
                	
                	InitPlay.now.play(curSong);
                	
                    break;
                }
            } catch (final JavaLayerException e) {
                break;
            }
            // check if paused or terminated
            synchronized (playerLock) {
                while (playerStatus == PAUSED) {
                    try {
                    	String songName = MainWindow.songs.get(InitPlay.cs).getName();
                    	curSong.setText(songName);
                        playerLock.wait();
                    } catch (final InterruptedException e) {
                        // terminate player
                        break;
                    }
                }
            }
        }
        
        close();
        
    }

    /**
     * Closes the player, regardless of current state.
     */
    public void close() {
        synchronized (playerLock) {
            playerStatus = FINISHED;
        }
        try {
            player.close();
        } catch (final Exception e) {
            // ignore, we are terminating anyway
        }
    }

   

}
