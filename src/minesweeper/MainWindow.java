package minesweeper;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * This is the main window which coordinates all the other objects and windows.
 */
public class MainWindow extends JFrame
{
	private PlayingField playingField;
	private SettingsWindow settingsWindow;
	private Thread menuUpdateThread;
	private JMenu timeMenu;

	/**
	 * The constructor initiates all the needed objects with default values.
	 */
	public MainWindow()
	{
		settingsWindow = new SettingsWindow(this);
		
		playingField = null;
		
		this.setTitle("Minesweeper");
		this.setResizable(false);
		this.setJMenuBar(createMenuBar());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.newGame();
		
		this.setVisible(true);
	}

	/**
	 * Method to start a new game.
	 * The window will close and create a new PlayingField object and reopen again.
	 */
	public void newGame()
	{
		this.setVisible(false);
		this.playingField = null;
		if (menuUpdateThread != null) {
			menuUpdateThread.interrupt();
		}

		this.playingField = new PlayingField(this);
		this.setSize(new Dimension((int)playingField.getSize().getWidth()+7, (int)playingField.getSize().getHeight() +51));
		this.setLocation(new Point(200,20));
		this.setContentPane(playingField);

		Runnable r = new TimeUpdateThread(this.timeMenu,this.playingField);
		menuUpdateThread = new Thread(r);
		menuUpdateThread.start();
		
		this.setVisible(true);
	}

	/**
	 * Creates a menu bar for the main window.
	 * Will only be called once by the constructor.
	 * @return
	 */
	private JMenuBar createMenuBar()
	{
		JMenuBar menBar = new JMenuBar();
		JMenu men = new JMenu();
		men.setText("Menu");
		JMenuItem menSettings = new JMenuItem();
		menSettings.setText("Settings");
		menSettings.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e)
		{
			settingsWindow.setVisible(true);
		}});
		men.add(menSettings);
		menBar.add(men);
		timeMenu = new JMenu();
		timeMenu.setText("Time: 0");
		menBar.add(timeMenu);
		return menBar;
	}
}

class TimeUpdateThread implements Runnable {
	private PlayingField playingField;
	private JMenu timeMenu;
	private long lastTime;

	TimeUpdateThread(JMenu timeMenu, PlayingField playingField) {
		this.playingField = playingField;
		this.timeMenu = timeMenu;
		this.lastTime = 0;
	}

	public void run() {
		boolean sleepInterrupted = false;
		while (!Thread.currentThread().isInterrupted() && !sleepInterrupted) {
			float currentTime = playingField.getCurrentTime();
			String newText;
			if (currentTime == 0)
				newText = "Time: " + this.lastTime + "s";
			else {
				long time = Math.round(Math.floor(currentTime));
				newText = "Time: " + time + "s";
				this.lastTime = time;
			}
			if(timeMenu.getText() != newText)
				timeMenu.setText(newText);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				sleepInterrupted = true;
			}
		}
	}
}
