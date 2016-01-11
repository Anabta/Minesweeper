package minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * This class manages the whole playingfield with all the individual fields as well as the graphics to draw the fields.
 */
public class PlayingField extends JPanel implements MouseListener
{
	private Field[][] fields;			//0=not opened ; 1=opened ; 2=flag
	private MainWindow mainWindow;
	private int fieldsToOpen;
	private long startTime;
	private float endTime;

	private BufferedImage buffer;

	/**
	 * The constructor initiates the individual parts of the playingfield with values from the settings.
	 * @param mw the main window object
	 */
	public PlayingField(MainWindow mw)
	{
		this.mainWindow = mw;
		Settings settings = Settings.getInstance();

		int w = settings.getWidth();
		int h = settings.getHeight();
		
		this.fields = new Field[w][h];
		
		this.initFields();
		this.placeBombs();
		this.setSize(new Dimension(w*settings.getScaling(),h*settings.getScaling()));
		
		buffer = new BufferedImage(w*settings.getScaling(),h*settings.getScaling(),BufferedImage.TYPE_INT_RGB);
		
		this.fieldsToOpen = (settings.getWidth()*settings.getHeight())-settings.getBombCount();

		this.endTime = 0;
		
		addMouseListener(this);
	}

	/**
	 * This method handles all the drawing in the playingfield.
	 * This method is usually called automatically by the Swing API.
	 * I use passive rendering in this project because the graphics are not too fancy and it's reasonably easier.
	 * @param g
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		Graphics bufferGraphics = buffer.getGraphics();

		Settings settings = Settings.getInstance();

		int w = settings.getWidth();
		int h = settings.getHeight();
		int s = settings.getScaling();
		
		for(int top = 0; top < h; top++)
		{
			for(int left = 0; left < w; left++)
			{
				if(fields[left][top].getFieldStatus() == Field.NOT_OPENED)
				{
					bufferGraphics.setColor(Color.DARK_GRAY);
					bufferGraphics.fillRect(left*s, top*s, s, s);
				}
				else if(fields[left][top].getFieldStatus() == Field.OPENED)
				{
					bufferGraphics.setColor(Color.GRAY);
					bufferGraphics.fillRect(left*s, top*s, s, s);
					
					bufferGraphics.setColor(Color.BLACK);
					if(fields[left][top].getBombStatus() == Field.BOMBED)
						bufferGraphics.drawString("x", left*s + s/2, top*s + s/2);
					else if(fields[left][top].getBombStatus() > 0)
						bufferGraphics.drawString(fields[left][top].getBombStatus() + "", left*s + s/2, top*s + s/2);
				}
				else if(fields[left][top].getFieldStatus() == Field.FLAGGED)
				{
					bufferGraphics.setColor(Color.RED);
					bufferGraphics.fillRect(left*s, top*s, s, s);
				}
			}
		}
		
		bufferGraphics.setColor(Color.BLACK);
		for(int i = 0; i <= w; i++)
			bufferGraphics.drawLine(i*s, 0, i*s, h*s);
		
		for(int i = 0; i <= h; i++)
			bufferGraphics.drawLine(0, i*s, w*s, i*s);
		
		if(g != null)
			g.drawImage(buffer, 0, 0, null);
	}

	/**
	 * This method initiates all fields with default values.
	 */
	private void initFields()
	{
		Settings settings = Settings.getInstance();
		for(int y = 0; y < settings.getHeight(); y++)
			for(int x = 0; x < settings.getWidth(); x++)
				this.fields[x][y] = new Field(x,y,settings,this);
		
		for(int y = 0; y < settings.getHeight(); y++)
		{
			for(int x = 0; x < settings.getWidth(); x++)
			{
				fields[x][y].top = (y > 0) ? fields[x][y-1] : null;
				fields[x][y].left = (x > 0) ? fields[x-1][y] : null;
				fields[x][y].right = (x+1 < settings.getWidth()) ? fields[x+1][y] : null;
				fields[x][y].bottom = (y+1 < settings.getHeight()) ? fields[x][y+1] : null;
			}
		}
	}

	/**
	 * This method places all the bombs in the playingfield randomly.
	 */
	private void placeBombs()
	{
		Settings settings = Settings.getInstance();
		int w = settings.getWidth();
		int h = settings.getHeight();
		
		int x,y;
		for(int i = 0; i < settings.getBombCount(); i++)
		{
			x = (int) (Math.random()*w);
			y = (int) (Math.random()*h);
			
			if(fields[x][y].getBombStatus() != Field.BOMBED)
			{
				fields[x][y].setBombStatus(Field.BOMBED);
				for(Direction d : Direction.values())
					if(fields[x][y].getNeighbour(d) != null)
						if(fields[x][y].getNeighbour(d).getBombStatus() != Field.BOMBED)
							fields[x][y].getNeighbour(d).incBombs();
			}
			else
			{
				i--;
			}
		}
	}

	/**
	 * This method opens the whole playingfield and shows a message that the player has lost.
	 */
	public void gameOver()
	{
		Settings settings = Settings.getInstance();
		for(int top = 0; top < settings.getHeight(); top++)
			for(int left = 0; left < settings.getWidth(); left++)
				this.fields[left][top].setFieldStatus(Field.OPENED);
		
		paintComponent(this.getGraphics());
		
		JOptionPane.showMessageDialog(null, "You lost!", "Sorry", JOptionPane.OK_CANCEL_OPTION);
		mainWindow.newGame();
	}

	/**
	 * This method opens the whole playingfield and shows a message that the player has won.
	 */
	public void youWin()
	{
		Settings settings = Settings.getInstance();
		for(int top = 0; top < settings.getHeight(); top++)
			for(int left = 0; left < settings.getWidth(); left++)
				this.fields[left][top].setFieldStatus(Field.OPENED);
		
		paintComponent(this.getGraphics());

		this.endTime = this.getCurrentTime();

		JOptionPane.showMessageDialog(null, "Congratulations, you won with a time of " + this.endTime + "s", "You Won!", JOptionPane.OK_CANCEL_OPTION);
		mainWindow.newGame();
	}

	/**
	 * Returns the number of fields to be opened.
	 * @return fields to be opened
	 */
	public int getFieldsToOpen()
	{
		return fieldsToOpen;
	}

	/**
	 * Decreases the number of fields to be opened by one.
	 */
	public void decFieldsToOpen()
	{
		fieldsToOpen--;
	}

	/**
	 * This method handles the event of a mouse double click.
	 * @param e
	 */
	@Override
	public void mouseClicked(MouseEvent e)
	{
		Settings settings = Settings.getInstance();
		if(e.getClickCount() == 2)
		{
			int x = e.getX()/settings.getScaling();
			int y = e.getY()/settings.getScaling();
			int markedNeighbours = 0;
			Field f = fields[x][y];
			
			if(f.getBombStatus() > 0 && f.getFieldStatus() == Field.OPENED)
			{
				for(Direction d : Direction.values())
					if(f.getNeighbour(d) != null)
						if(f.getNeighbour(d).getFieldStatus() == Field.FLAGGED)
							markedNeighbours++;
				
				if(markedNeighbours == fields[x][y].getBombStatus())
					for(Direction d : Direction.values())
						if(f.getNeighbour(d) != null)
							if(f.getNeighbour(d).getFieldStatus() == Field.NOT_OPENED)
								f.getNeighbour(d).openField();
			}			
		}
	}


	@Override
	public void mouseEntered(MouseEvent e)
	{
	
	}


	@Override
	public void mouseExited(MouseEvent e)
	{

	}


	@Override
	public void mousePressed(MouseEvent e)
	{

	}


	/**
	 * This method handles the event of a left or right klick.
	 * @param e
	 */
	@Override
	public void mouseReleased(MouseEvent e)
	{
		Settings settings = Settings.getInstance();
		if (this.fieldsToOpen == (settings.getWidth()*settings.getHeight())-settings.getBombCount()) {
			startTime = System.nanoTime();
		}
		int mx = e.getX()/settings.getScaling();
		int my = e.getY()/settings.getScaling();
		if(e.getButton() == MouseEvent.BUTTON1 && fields[mx][my].getFieldStatus() != Field.FLAGGED)
		{
			fields[mx][my].openField();
		}
		else if(e.getButton() == MouseEvent.BUTTON3) {
			if (fields[mx][my].getFieldStatus() == Field.NOT_OPENED)
				fields[mx][my].setFieldStatus(Field.FLAGGED);
			else if (fields[mx][my].getFieldStatus() == Field.FLAGGED)
				fields[mx][my].setFieldStatus(Field.NOT_OPENED);
			paintComponent(this.getGraphics());
		}
	}

	public float getCurrentTime() {
		if (startTime == 0 || this.endTime != 0)
			return 0.0f;
		long estimatedTime = System.nanoTime() - startTime;
		estimatedTime /= 1000000;
		return new Float(estimatedTime)/1000.0f;
	}
}
