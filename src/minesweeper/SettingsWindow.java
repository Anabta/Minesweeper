package minesweeper;

import java.awt.Dimension;
import java.awt.Point;
import java.security.InvalidParameterException;

import javax.swing.JFrame;

public class SettingsWindow extends JFrame
{
	public static int DIF_EASY = 1;
	public static int DIF_MEDIUM = 2;
	public static int DIF_HARD = 3;
	public static int DIF_CUSTOM = 4;
	
	
	private int sWidth;
	private int sHeight;
	private int sScaling;
	private int sBombCount;
	private int sDifficulty;		//0 = custom, 1=easy, 2=medium, 3=hard
	private int sPxLeft;
	private int sPxTop;
	
	private MainWindow mainWindow;
	private SettingsPanel sp;
	
	public SettingsWindow(int width, int height, int scaling, int bombCount, int left, int top, MainWindow mw)
	{
		this.mainWindow = mw;
		
		this.sWidth = width;
		this.sHeight = height;
		this.sScaling = scaling;
		this.sBombCount = bombCount;
		this.sPxLeft = left;
		this.sPxTop = top;
		this.sDifficulty = 0;
		this.initWindow();
	}
	
	public SettingsWindow(int diff, MainWindow mw)
	{
		this.mainWindow = mw;
		if(diff > 0 && diff < 4)
		{
			this.setSDifficulty(diff);
			this.initWindow();
		}
		else
		{
			throw new InvalidParameterException();
		}
	}
	
	private void initWindow()
	{
		this.setTitle("Settings");
		this.setLocation(new Point(100,100));
		this.setResizable(false);
		sp = new SettingsPanel(this);
		sp.setSize(new Dimension(400,150));
		this.setSize(sp.getSize());
		this.setContentPane(sp);
		this.setVisible(false);
	}

	public int getSWidth()
	{
		return sWidth;
	}

	public void setSSize(int width, int height)
	{
		this.sWidth = width;
		this.sHeight = height;
	}

	public int getSHeight()
	{
		return sHeight;
	}

	public int getSScaling()
	{
		return sScaling;
	}

	public void setSScaling(int scaling)
	{
		this.sScaling = scaling;
	}

	public int getSBombCount()
	{
		return sBombCount;
	}

	public void setSBombCount(int bombCount)
	{
		this.sBombCount = bombCount;
	}

	public int getSDifficulty()
	{
		return sDifficulty;
	}

	public void setSDifficulty(int difficulty)
	{
		if(difficulty == 0)
		{
			System.out.println("Simply change one value, the difficulty will automatically change");
			return;
		}
		else if(difficulty == DIF_EASY)		//easy
		{
			this.sWidth = 9;
			this.sHeight = 9;
			this.sScaling = 50;
			this.sBombCount = 10;
			this.sPxLeft = 200;
			this.sPxTop = 200;
		}
		else if(difficulty == DIF_MEDIUM)		//medium
		{
			this.sWidth = 16;
			this.sHeight = 16;
			this.sScaling = 40;
			this.sBombCount = 40;
			this.sPxLeft = 200;
			this.sPxTop = 200;
		}
		else if(difficulty == DIF_HARD)		//hard
		{
			this.sWidth = 30;
			this.sHeight = 16;
			this.sScaling = 30;
			this.sBombCount = 99;
			this.sPxLeft = 200;
			this.sPxTop = 200;
		}
		else if(difficulty == DIF_CUSTOM)
		{
			//parameters have to be changed elsewhere
		}
		else
		{
			System.out.println("Invalid difficulty was entered!");
			return;
		}
		
		this.sDifficulty = difficulty;
	}
	
	public void setCustomParameters(int width, int height, int bombCount)
	{
		this.sDifficulty = DIF_CUSTOM;
		this.sWidth = width;
		this.sHeight = height;
		this.sBombCount = bombCount;
	}

	public int getSPxLeft()
	{
		return sPxLeft;
	}

	public int getSPxTop()
	{
		return sPxTop;
	}
	
	public void setSPxPos(int pxLeft, int pxTop)
	{
		this.sPxLeft = pxLeft;
		this.sPxTop = pxTop;
	}
	
	public void newGame()
	{
		mainWindow.newGame();
	}
}
