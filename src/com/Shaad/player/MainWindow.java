package com.Shaad.player;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	
	//All the songs path are stored in this array
	static ArrayList<File> songs ;

	public static InitPlay now;
	public static void main(String[] args) {
		
		
		/**
		 * Checking operating system as home folder is different for different OS. 
		 */
		String str =  OsCheck.getOperatingSystemType();
		String home = null;
		String messege = null;
		
		//Assign home directory to search audio songs for Linux or Windows or Mac
		if(str.equals("Linux"))
		{
			messege = "Home directory";
			home = "/home";
		}
		else if(str.equals("Windows"))
		{
			messege = "C drive";
			home = "c:\\Users";
		}
		else if(str.equals("Mac"))
		{
			messege = "User directory";
			home = "/users";
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Cannot Detect Operating System");
			System.exit(0);
		}
		
		/**
		 * This functions sets some system folder which will be avoided when searchin for songs;
		 */
		FindSongs.init();
		
		/**
		 * We provide the home directory and start a depth first search call to go to all
		 * the directory and collecting mp3 files
		 * It is done when the window is loaded.
		 */
		FindSongs.extract(new File(home));
		songs = FindSongs.songs;
		
		if(songs.size() == 0)
		{
			
			JOptionPane.showMessageDialog(null, "No Song Found in "+ messege);
			System.exit(0);
		}
		
		//Current song index
		InitPlay.cs = 0;
		
		//Launches main window
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel() {  
            public void paintComponent(Graphics g) {  
                 Image img = Toolkit.getDefaultToolkit().getImage(  
                           MainWindow.class.getResource("/Images/bg.jpg"));  
                 g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);  
            }  
		};  
		 contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));  
         contentPane.setLayout(null);
         setContentPane(contentPane);
         
         String songName;
         
         /**
          * The current playing or initialized song name is displayed using this JLabel.
          */

         JLabel curSong = new JLabel("");
         curSong.setBounds(102, 143, 241, 25);
         contentPane.add(curSong);
         
         //Loading initial song . The first song is added in display
        
         songName = songs.get(0).getName();
         curSong.setText(songName);
         
         now = new InitPlay();
 		 now.select(0,curSong);
 		 now.initialize();
 		 
 		 /**
 		  * Play and puase Button
 		  * The button name becomes "Pause" when a song is playing and
 		  * "Play" when it is paused or initialized  
 		  */
 		 
 		JButton btnPP = new JButton("Play");
        btnPP.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		String str = "Play";
        		if(btnPP.getText().equals(str))
        		{
        			now.play();
        			btnPP.setText("Pause");
        		}
        		else
        		{
        			now.pause();
        			btnPP.setText("Play");
        		}
        		
        	}
        });
        btnPP.setBackground(Color.CYAN);
        btnPP.setBounds(164, 180, 97, 25);
        contentPane.add(btnPP);
         
        /**
         * Prev Button
         * Plays the next songs. If current songs is playing , previous songs starts playing.
         * If current song is in pause mode , previous songs is loaded and does not start playing
         */
         
         JButton btnPrev = new JButton("Prev");
         btnPrev.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent arg0) {
         		String str = "Pause";
         		if(btnPP.getText().equals(str))
         		{
         			now.prevPlay();
         		}
         		else
         		{
         			now.prev();
         		}
         	}
         });
         btnPrev.setBackground(Color.CYAN);
         btnPrev.setBounds(95, 180, 81, 25);
         contentPane.add(btnPrev);
         
         /**
          * Next Button
          * Plays the next songs. If current songs is playing , next songs starts playing.
          * If current song is in pause mode , next songs is loaded and does not start playing
          */
         
         JButton btnNext = new JButton("Next");
         btnNext.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent arg0) {
         		String str = "Pause";
         		if(btnPP.getText().equals(str))
         		{
         			now.nextPlay();
         		}
         		else
         		{
         			now.next();
         		}
         		
         	}
         });
         btnNext.setBackground(Color.CYAN);
         btnNext.setBounds(246, 180, 97, 25);
         contentPane.add(btnNext);
         
         /**
          * "All Song" Button
          * Can view all the song and select any song to play
          * Creates a new window of song list
          */
         
         JButton btnList = new JButton("All Songs");
         btnList.addActionListener(new ActionListener() {
        	
         	public void actionPerformed(ActionEvent arg0) {
         		
         		SwingUtilities.invokeLater(new Runnable() {
		            @Override
		            public void run() {
		                new SelectSong(InitPlay.cs);
		            }
		        });
         		btnPP.setText("Pause");    
         	}
         });
         btnList.setBackground(Color.CYAN);
         btnList.setBounds(0, 239, 106, 25);
         contentPane.add(btnList);
         
         /**
          * Shuffle Button
          * Change the current song as it shuffles the whole playlist . 
          * Do the same as the before state(play/pause)
          */
         JButton btnShuffle = new JButton("Shuffle");
         btnShuffle.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent arg0) {
         		long seed = System.nanoTime();
         		String str = "Pause";
         		if(btnPP.getText().equals(str))
         		{
  
         			now.pause();
         			Collections.shuffle(songs,new Random(seed));
             		now.select(0,curSong);
            		now.initialize();
            		now.play();
         		}
         		else
         		{
         			Collections.shuffle(songs,new Random(seed));
             		now.select(0,curSong);
            		now.initialize();
         		}
         		
        		
         	}
         });
         btnShuffle.setBackground(Color.CYAN);
         btnShuffle.setBounds(344, 239, 106, 25);
         contentPane.add(btnShuffle);
         
         
	}
}
