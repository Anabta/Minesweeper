package minesweeper;

public class Field
{
	public static final int NOT_OPENED = 0;
	public static final int OPENED = 1;
	public static final int FLAGGED = 2;
	
	public static final int BOMBED = 9;
	
	private Settings settings;
	private PlayingField playingField;
	
	private int fieldStatus;
	private int bombStatus;
	
	public Field top = null;
	public Field left = null;
	public Field right = null;
	public Field bottom = null;
	
	public Field(int x, int y, Settings set, PlayingField pf)
	{
		this.settings = set;
		this.playingField = pf;
		
		fieldStatus = NOT_OPENED;
		bombStatus = 0;
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
	
	public Field getNeighbour(Direction dir)
	{
		try
		{
			switch (dir)
			{
			case TOP_LEFT: return this.top.left;
			case TOP: return this.top;
			case TOP_RIGHT: return this.top.right;
			case LEFT: return this.left;
			case RIGHT: return this.right;
			case BOT_LEFT: return this.bottom.left;
			case BOT: return this.bottom;
			case BOT_RIGHT: return this.bottom.right;
			default: return null;
			}
		} catch (NullPointerException e)
		{
			return null;
		}
	}
	
	public void openField()
	{
		this.openField(false);
	}
	
	private void openField(boolean recursive)
	{
		if(this.getBombStatus() == Field.BOMBED)
		{
			playingField.gameOver();
			return;
		}
		
		if(this.getFieldStatus() == Field.OPENED)
			return;
		
		this.setFieldStatus(Field.OPENED);
		playingField.decFieldsToOpen();
		
		if(playingField.getFieldsToOpen() == 0)
			playingField.youWin();
		
		if(this.getBombStatus() == 0)
			for(Direction d : Direction.values())
				if(this.getNeighbour(d) != null)
					if(this.getNeighbour(d).getFieldStatus() == Field.NOT_OPENED)
						this.getNeighbour(d).openField(true);
		
		if(recursive == false || settings.getAnimation() == true)
			playingField.paintComponent(playingField.getGraphics());
	}
}
