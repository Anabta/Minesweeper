package minesweeper;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.security.InvalidParameterException;

/**
 * This class wraps up all the  necessary settings configuration data for the whole program.
 * This is a singleton class. You can get the instance by calling Settings.getInstance().
 */
public class Settings
{
	private static Settings instance;

	public static int DIF_EASY = 1;
	public static int DIF_MEDIUM = 2;
	public static int DIF_HARD = 3;
	public static int DIF_CUSTOM = 4;

	public static int maxScreenWidth = 1280;
	public static int maxScreenHeight = 720;
	
	
	private int width;			// width of the playingfield
	private int height;			// height of the playingfield
	private int scaling;		// size of one individual field
	private int bombCount;		// nnumber of bombs on the playingfield
	private int difficulty;		//0 = custom, 1=easy, 2=medium, 3=hard

	/**
	 * This private constructor prevents constructing an object from other places.
	 */
	private Settings () {}

	public static Settings getInstance() {
		if (Settings.instance == null) {
			Settings.instance = new Settings();
			Settings.instance.setDifficulty(DIF_EASY);
		}
		return Settings.instance;
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
			//this.scaling = 60;
			this.scaling = calculateScaling(this.width, this.height);
			this.bombCount = 10;
		}
		else if(difficulty == DIF_MEDIUM)		//medium
		{
			this.width = 16;
			this.height = 16;
			this.scaling = calculateScaling(this.width, this.height);
			this.bombCount = 40;
		}
		else if(difficulty == DIF_HARD)		//hard
		{
			this.width = 30;
			this.height = 16;
			this.scaling = calculateScaling(this.width, this.height);
			this.bombCount = 99;
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
		this.scaling = calculateScaling(width, height);
		this.bombCount = bombCount;
	}

	private static int calculateScaling(int width, int height) {
		if (maxScreenWidth / width > maxScreenHeight / height)
			return (int) Math.floor(maxScreenHeight / height);
		else
			return (int) Math.floor(maxScreenWidth / width);
	}
	
}
