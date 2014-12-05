package minesweeper;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

public class SettingsWindow extends JFrame
{
	private int width;
	private int height;
	private int scaling;
	private int bombCount;
	private int difficulty;		//0 = custom, 1=easy, 2=medium, 3=hard
	private int pxLeft;
	private int pxTop;
	private boolean isVisible;
	
	private SettingsPanel sp;
	
	public SettingsWindow(int width, int height, int scaling, int bombCount, int left, int top)
	{
		this.width = width;
		this.height = height;
		this.scaling = scaling;
		this.bombCount = bombCount;
		this.pxLeft = left;
		this.pxTop = top;
		this.difficulty = 0;
		this.isVisible = false;
		this.initWindow();
	}
	
	public SettingsWindow(int difficulty)
	{
		this.setDifficulty(difficulty);
		this.isVisible = false;
		this.initWindow();
	}
	
	private void initWindow()
	{
		sp = new SettingsPanel();
		sp.setPreferredSize(new Dimension(200,200));
		this.setContentPane(sp);
		this.setPreferredSize(sp.getPreferredSize());
		this.setTitle("Settings");
		this.setLocation(new Point(100,100));
		this.setResizable(false);
		this.pack();
		this.setVisible(false);
	}

	public int getWidth()
	{
		return width;
	}

	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	public int getHeight()
	{
		return height;
	}

	public int getScaling()
	{
		return scaling;
	}

	public void setScaling(int scaling)
	{
		this.scaling = scaling;
	}

	public int getBombCount()
	{
		return bombCount;
	}

	public void setBombCount(int bombCount)
	{
		this.bombCount = bombCount;
	}

	public int getDifficulty()
	{
		return difficulty;
	}

	public void setDifficulty(int difficulty)
	{
		if(difficulty > 3 || difficulty < 0)
		{
			System.out.println("Invalid difficulty was entered!");
			return;
		}
		else if(difficulty == 0)
		{
			System.out.println("Simply change one value, the difficulty will automatically change");
			return;
		}
		else if(difficulty == 1)		//easy
		{
			this.width = 9;
			this.height = 9;
			this.scaling = 50;
			this.bombCount = 10;
			this.pxLeft = 200;
			this.pxTop = 200;
		}
		else if(difficulty == 2)		//medium
		{
			this.width = 16;
			this.height = 16;
			this.scaling = 40;
			this.bombCount = 40;
			this.pxLeft = 200;
			this.pxTop = 200;
		}
		else							//hard
		{
			this.width = 30;
			this.height = 16;
			this.scaling = 30;
			this.bombCount = 99;
			this.pxLeft = 200;
			this.pxTop = 200;
		}
		
		this.difficulty = difficulty;
		//reload the game... 
	}

	public int getPxLeft()
	{
		return pxLeft;
	}

	public int getPxTop()
	{
		return pxTop;
	}
	
	public void setPxPos(int pxLeft, int pxTop)
	{
		this.pxLeft = pxLeft;
		this.pxTop = pxTop;
	}
	
	public void setVisible(boolean isVisible)
	{
		this.isVisible = isVisible;
		super.setVisible(isVisible);
	}
}
