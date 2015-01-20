package minesweeper;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainWindow extends JFrame
{
	private PlayingField playingField;
	private SettingsWindow settings;
	
	public MainWindow()
	{
		settings = new SettingsWindow(SettingsWindow.DIF_EASY, this);
		//settings = new SettingsWindow(30,15,40,50,200,200);
		
		playingField = null;
		
		this.setTitle("Minesweeper");
		this.setResizable(false);
		this.setJMenuBar(createMenuBar());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.newGame();
		
		this.setVisible(true);
	}
	
	public void newGame()
	{
		this.setVisible(false);
		this.playingField = null;
		
		this.playingField = new PlayingField(settings,this);
		this.setSize(new Dimension((int)playingField.getSize().getWidth()+7, (int)playingField.getSize().getHeight() +51));
		this.setLocation(new Point(settings.getSPxLeft(),settings.getSPxTop()));
		this.setContentPane(playingField);
		
		this.setVisible(true);
	}
	
	private JMenuBar createMenuBar()
	{
		JMenuBar menBar = new JMenuBar();
		JMenu men = new JMenu();
		men.setText("Menü");
		JMenuItem menSettings = new JMenuItem();
		menSettings.setText("Einstellungen");
		menSettings.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e)
		{
			settings.setVisible(true);
		}});
		men.add(menSettings);
		menBar.add(men);
		return menBar;
	}
}
