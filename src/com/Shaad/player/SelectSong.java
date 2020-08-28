package com.Shaad.player;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
 
public class SelectSong extends JFrame {
 
    private JList<String> songList;
    public String sss;
 
    public SelectSong(int cur)
    {
        //create the model and add elements
        DefaultListModel<String> songModel = new DefaultListModel<>();
        int idx = -1;
        
        for(File fl : MainWindow.songs)
        {
        	idx++;
        	if(idx < cur)continue;
        	String str = fl.getName();
        	songModel.addElement(str);
        }
        idx = 0;
        for(File fl : MainWindow.songs)
        {
        	if(idx >= cur)break;
        	String str = fl.getName();
        	songModel.addElement(str);
        	idx++;
        }
 
        //create the list
        songList = new JList<>(songModel);
        songList.addListSelectionListener(new ListSelectionListener() 
        {
            @Override
            public void valueChanged(ListSelectionEvent e) 
            {
                if (!e.getValueIsAdjusting()) {
                    final List<String> selectedSong = songList.getSelectedValuesList();
                    //This is the selected song
                    String str = selectedSong.get(0);
                    int cur = 0;
                    /*
                     * Matching the selected song in the songlist to get it's index
                     */
                    for(File fl : MainWindow.songs)
                    {
                    	String now = fl.getName();
                    	if(now.equals(str))
                    	{	
                    		break;
                    	}
                    	cur++;
                    }
                    //Song's index found. Closing this selection window and playing the selected song
                    
                    dispose();
                    MainWindow.now.playRandom(cur);
                    
                    
                    
                }
            }
        });
 
        add(new JScrollPane(songList));
       
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Click on A Song to Play");
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
    }
 
    public static void main(String[] args) 
    {
        
    }
}