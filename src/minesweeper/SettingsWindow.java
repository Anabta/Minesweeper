package minesweeper;

import java.awt.Dimension;
import java.awt.Point;
import java.security.InvalidParameterException;

import javax.swing.JFrame;

/**
 * This class creates a settings window to wrap the settings panel.
 */
public class SettingsWindow extends JFrame
{	
	private MainWindow mainWindow;
	private SettingsPanel sp;
	
	public SettingsWindow(MainWindow mw)
	{
		this.mainWindow = mw;
		
		this.initWindow();
	}

	private void initWindow()
	{
		this.setTitle("Settings");
		this.setLocation(new Point(100,100));
		this.setResizable(false);
		sp = new SettingsPanel(this);
		sp.setSize(new Dimension(400,200));
		this.setSize(sp.getSize());
		this.setContentPane(sp);
		this.setVisible(false);
	}
	
	public void newGame()
	{
		mainWindow.newGame();
	}
}
