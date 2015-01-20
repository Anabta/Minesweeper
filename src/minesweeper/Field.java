package minesweeper;

public class Field
{
	public static final int TOP_LEFT = 1;
	public static final int TOP = 2;
	public static final int TOP_RIGHT = 3;
	public static final int LEFT = 4;
	public static final int RIGHT = 5;
	public static final int BOT_LEFT = 6;
	public static final int BOT = 7;
	public static final int BOT_RIGHT = 8;
	
	public static final int NOT_OPENED = 0;
	public static final int OPENED = 1;
	public static final int FLAGGED = 2;
	
	public static final int BOMBED = 9;
	
	private SettingsWindow settings;
	
	private int fieldStatus;
	private int bombStatus;
	private int x;
	private int y;
	private boolean topRow;
	private boolean bottomRow;
	private boolean leftCol;
	private boolean rightCol;
	
	public Field(int x, int y, SettingsWindow set)
	{
		this.settings = set;
		this.x = x;
		this.y = y;
		
		fieldStatus = NOT_OPENED;
		bombStatus = 0;
		
		leftCol = (x == 0) ? true : false;
		rightCol = (x < settings.getSWidth()-1) ? false : true;
		topRow = (y == 0) ? true : false;
		bottomRow = (y < settings.getSHeight()-1) ? false : true;
	}
	
	public int getFieldStatus()
	{
		return fieldStatus;
	}
	
	public void setFieldStatus(int stat)
	{
		if(stat >= NOT_OPENED && stat <=FLAGGED)
			this.fieldStatus = stat;
	}
	
	public int getBombStatus()
	{
		return bombStatus;
	}
	
	public void setBombStatus(int stat)
	{
		if(stat >= 0 && stat <=9)
			this.bombStatus = stat;
	}
	
	public void incBombs()
	{
		this.bombStatus++;
	}
	
	public boolean checkBounds(int pos)
	{
		if(topRow == false && bottomRow == false && leftCol == false && rightCol == false)
			return true;
		
		switch (pos)
		{
			case TOP_LEFT: return (topRow || leftCol) ? false : true;
			case TOP: return (topRow) ? false : true;
			case TOP_RIGHT: return (topRow || rightCol) ? false : true;
			case LEFT: return (leftCol) ? false : true;
			case RIGHT: return (rightCol) ? false : true;
			case BOT_LEFT: return (bottomRow || leftCol) ? false : true;
			case BOT: return (bottomRow) ? false : true;
			case BOT_RIGHT: return (bottomRow || rightCol) ? false : true;
		}
		
		return false;
	}
}
