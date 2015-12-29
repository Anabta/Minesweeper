package minesweeper;

/**
 * The Field class defines all the functionality that one field on the PlayingField has.
 */
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


	/**
	 * @param x X-Position in the playingfield
	 * @param y Y-Position in the playingfield
	 * @param set settings object from the main program
	 * @param pf playingfield object which belongs to the new field
	 */
	public Field(int x, int y, Settings set, PlayingField pf)
	{
		this.settings = set;
		this.playingField = pf;
		
		fieldStatus = NOT_OPENED;
		bombStatus = 0;
	}

	/**
	 * Returns the status of the field.
	 * Field.NOT_OPENED, Field. OPENED, Field.FLAGGED
	 * @return status of this field
	 */
	public int getFieldStatus()
	{
		return fieldStatus;
	}

	/**
	 * Sets the status of the field.
	 * Field.NOT_OPENED, Field. OPENED, Field.FLAGGED
	 * @param stat The new status of this field
	 */
	public void setFieldStatus(int stat)
	{
		if(stat >= NOT_OPENED && stat <=FLAGGED)
			this.fieldStatus = stat;
	}

	/**
	 * Returns the number of nearby bombs or Field.BOMBED if the field is a bomb.
	 * @return The number of nearby bombs
	 */
	public int getBombStatus()
	{
		return bombStatus;
	}

	/**
	 * Sets the number of nearby bombs or Field.BOMBED if the field shall be a bomb.
	 * @param stat The number of nearby bombs
	 */
	public void setBombStatus(int stat)
	{
		if(stat >= 0 && stat <=9)
			this.bombStatus = stat;
	}

	/**
	 * Increments the number of nearby bombs.
	 */
	public void incBombs()
	{
		this.bombStatus++;
	}

	/**
	 * Returns the neighbor of this field whose position is in a certain direction from this field.
	 * Possible directions are all of the Direction enumeration types.
	 * If no field was found in that direction, null gets returned.
	 * @param dir Direction of the desired field
	 * @return The desired field or null
	 */
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

	/**
	 * Opens this field and all nearby fields which can be opened automatically.
	 * Calls the recursive method openField(boolean).
	 */
	public void openField()
	{
		this.openField(false);
	}

	/**
	 * Opens this field and all nearby fields which can be opened recursively.
	 * @param recursive specifies, if the method is called in a recursion or not. (default should be false)
	 */
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

		//The playingfield should be updated only when the recursion is finished.
		//This is important because otherwise the graphics get updated redundantly after every single field opening.
		if(recursive == false)
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		playingField.paintComponent(playingField.getGraphics());
	}
}
