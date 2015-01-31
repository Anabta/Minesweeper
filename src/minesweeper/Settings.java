package minesweeper;

import java.security.InvalidParameterException;

public class Settings
{
	public static int DIF_EASY = 1;
	public static int DIF_MEDIUM = 2;
	public static int DIF_HARD = 3;
	public static int DIF_CUSTOM = 4;
	
	
	private int width;
	private int height;
	private int scaling;
	private int bombCount;
	private int difficulty;		//0 = custom, 1=easy, 2=medium, 3=hard
	private int pxLeft;
	private int pxTop;
	private boolean animations;
	
	public Settings(int width, int height, int scaling, int bombCount, int left, int top)
	{
		this.width = width;
		this.height = height;
		this.scaling = scaling;
		this.bombCount = bombCount;
		this.pxLeft = left;
		this.pxTop = top;
		this.difficulty = 0;
		this.animations = false;
	}
	
	public Settings(int diff)
	{
		if(diff > 0 && diff < 4)
			this.setDifficulty(diff);
		else
			throw new InvalidParameterException();
		
		this.animations = false;
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
		if(difficulty == 0)
		{
			System.out.println("Simply change one value, the difficulty will automatically change");
			return;
		}
		else if(difficulty == DIF_EASY)		//easy
		{
			this.width = 9;
			this.height = 9;
			this.scaling = 60;
			this.bombCount = 10;
			this.pxLeft = 300;
			this.pxTop = 100;
		}
		else if(difficulty == DIF_MEDIUM)		//medium
		{
			this.width = 16;
			this.height = 16;
			this.scaling = 50;
			this.bombCount = 40;
			this.pxLeft = 300;
			this.pxTop = 20;
		}
		else if(difficulty == DIF_HARD)		//hard
		{
			this.width = 30;
			this.height = 16;
			this.scaling = 40;
			this.bombCount = 99;
			this.pxLeft = 200;
			this.pxTop = 20;
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
		
		this.difficulty = difficulty;
	}
	
	public void setCustomParameters(int width, int height, int bombCount)
	{
		this.difficulty = DIF_CUSTOM;
		this.width = width;
		this.height = height;
		this.bombCount = bombCount;
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
	
	public void setAnimation(boolean a)
	{
		this.animations = a;
	}
	
	public boolean getAnimation()
	{
		return this.animations;
	}
	
}
