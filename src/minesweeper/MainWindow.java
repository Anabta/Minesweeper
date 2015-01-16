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
		settings = new SettingsWindow(SettingsWindow.DIF_EASY);
		//settings = new SettingsWindow(30,15,40,50,200,200);
		Dimension prefSize = new Dimension();
		playingField = new PlayingField(settings);
		this.setResizable(false);
		this.setContentPane(playingField);
		prefSize.setSize(playingField.getPreferredSize().getWidth()+7, playingField.getPreferredSize().getHeight() +53);
		this.setPreferredSize(prefSize);
		this.setTitle("Minesweeper");
		this.setLocation(new Point(settings.getSPxLeft(),settings.getSPxTop()));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setJMenuBar(createMenuBar());
		
		this.pack();
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
