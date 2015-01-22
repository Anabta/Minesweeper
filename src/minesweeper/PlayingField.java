package minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class PlayingField extends JPanel implements MouseListener
{
	private Field[][] fields;			//0=not opened ; 1=opened ; 2=flag
	private SettingsWindow settings;
	private MainWindow mainWindow;
	private int fieldsToOpen;
	
	private BufferedImage buffer;
	
	public PlayingField(SettingsWindow set, MainWindow mw)
	{
		this.settings = set;
		this.mainWindow = mw;
		
		int w = settings.getSWidth();
		int h = settings.getSHeight();
		
		this.fields = new Field[w][h];
		
		this.initFields();
		this.placeBombs();
		this.setSize(new Dimension(w*settings.getSScaling(),h*settings.getSScaling()));
		
		buffer = new BufferedImage(w*settings.getSScaling(),h*settings.getSScaling(),BufferedImage.TYPE_INT_RGB);
		
		this.fieldsToOpen = (settings.getSWidth()*settings.getSHeight())-settings.getSBombCount();
		
		addMouseListener(this);
	}
	
	
	@Override
	public void paintComponent(Graphics g)
	{
		Graphics bufferGraphics = buffer.getGraphics();
		
		int w = settings.getSWidth();
		int h = settings.getSHeight();
		int s = settings.getSScaling();
		
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
		{
			bufferGraphics.drawLine(i*s, 0, i*s, h*s);
		}
		
		for(int i = 0; i <= h; i++)
		{
			bufferGraphics.drawLine(0, i*s, w*s, i*s);
		}
		
		g.drawImage(buffer, 0, 0, null);
	}
	
	private void initFields()
	{
		for(int top = 0; top < settings.getSHeight(); top++)
			for(int left = 0; left < settings.getSWidth(); left++)
				this.fields[left][top] = new Field(left,top,settings);
	}
	
	private void placeBombs()
	{
		int w = settings.getSWidth();
		int h = settings.getSHeight();
		
		int x, y;
		for(int i = 0; i < settings.getSBombCount(); )
		{
			x = (int) (Math.random()*w);
			y = (int) (Math.random()*h);
			if(fields[x][y].getBombStatus() != 9)
			{
				fields[x][y].setBombStatus(Field.BOMBED);
				if(fields[x][y].checkBounds(Field.TOP_LEFT))
					if(fields[x-1][y-1].getBombStatus() != Field.BOMBED)
						fields[x-1][y-1].incBombs();
				if(fields[x][y].checkBounds(Field.TOP))
					if(fields[x][y-1].getBombStatus() != Field.BOMBED)
						fields[x][y-1].incBombs();
				if(fields[x][y].checkBounds(Field.TOP_RIGHT))
					if(fields[x+1][y-1].getBombStatus() != Field.BOMBED)
						fields[x+1][y-1].incBombs();
				if(fields[x][y].checkBounds(Field.LEFT))
					if(fields[x-1][y].getBombStatus() != Field.BOMBED)
						fields[x-1][y].incBombs();
				if(fields[x][y].checkBounds(Field.RIGHT))
					if(fields[x+1][y].getBombStatus() != Field.BOMBED)
						fields[x+1][y].incBombs();
				if(fields[x][y].checkBounds(Field.BOT_LEFT))
					if(fields[x-1][y+1].getBombStatus() != Field.BOMBED)
						fields[x-1][y+1].incBombs();
				if(fields[x][y].checkBounds(Field.BOT))
					if(fields[x][y+1].getBombStatus() != Field.BOMBED)
						fields[x][y+1].incBombs();
				if(fields[x][y].checkBounds(Field.BOT_RIGHT))
					if(fields[x+1][y+1].getBombStatus() != Field.BOMBED)
						fields[x+1][y+1].incBombs();
				i++;
			}
		}
	}
	
	public void openField(int x, int y)
	{
		this.openField(x, y, false);
	}
	
	public void openField(int x, int y, boolean recursive)
	{
		if(fields[x][y].getBombStatus() == Field.BOMBED)
		{
			gameOver();
			return;
		}
		if(fields[x][y].getFieldStatus() == Field.OPENED)
			return;
		
		fields[x][y].setFieldStatus(Field.OPENED);
		fieldsToOpen--;
		
		if(fieldsToOpen < 1)
		{
			youWin();
		}
		
		if(fields[x][y].getBombStatus() == 0)
		{			
			if(fields[x][y].checkBounds(Field.TOP_LEFT))
				if(fields[x-1][y-1].getFieldStatus() == Field.NOT_OPENED)
					openField(x-1,y-1, true);
			if(fields[x][y].checkBounds(Field.TOP))
				if(fields[x][y-1].getFieldStatus() == Field.NOT_OPENED)
					openField(x,y-1, true);
			if(fields[x][y].checkBounds(Field.TOP_RIGHT))
				if(fields[x+1][y-1].getFieldStatus() == Field.NOT_OPENED)
					openField(x+1,y-1, true);
			if(fields[x][y].checkBounds(Field.LEFT))
				if(fields[x-1][y].getFieldStatus() == Field.NOT_OPENED)
					openField(x-1,y, true);
			if(fields[x][y].checkBounds(Field.RIGHT))
				if(fields[x+1][y].getFieldStatus() == Field.NOT_OPENED)
					openField(x+1,y, true);
			if(fields[x][y].checkBounds(Field.BOT_LEFT))
				if(fields[x-1][y+1].getFieldStatus() == Field.NOT_OPENED)
					openField(x-1,y+1, true);
			if(fields[x][y].checkBounds(Field.BOT))
				if(fields[x][y+1].getFieldStatus() == Field.NOT_OPENED)
					openField(x,y+1, true);
			if(fields[x][y].checkBounds(Field.BOT_RIGHT))
				if(fields[x+1][y+1].getFieldStatus() == Field.NOT_OPENED)
					openField(x+1,y+1, true);
		}
		
		if(recursive == false || settings.getSAnimation() == true)
			paintComponent(this.getGraphics());			
	}
	
	public void gameOver()
	{
		for(int top = 0; top < settings.getSHeight(); top++)
		{
			for(int left = 0; left < settings.getSWidth(); left++)
			{
				this.fields[left][top].setFieldStatus(Field.OPENED);
			}
		}
		paintComponent(this.getGraphics());
		
		JOptionPane.showMessageDialog(null, "Sie haben verloren!", "Schade", JOptionPane.OK_CANCEL_OPTION);
		mainWindow.newGame();
	}
	
	public void youWin()
	{
		for(int top = 0; top < settings.getSHeight(); top++)
		{
			for(int left = 0; left < settings.getSWidth(); left++)
			{
				this.fields[left][top].setFieldStatus(Field.OPENED);
			}
		}
		paintComponent(this.getGraphics());
		
		JOptionPane.showMessageDialog(null, "Sie haben gewonnen!", "Glückwunsch", JOptionPane.OK_CANCEL_OPTION);
		mainWindow.newGame();
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(e.getClickCount() == 2)
		{
			int x = e.getX()/settings.getSScaling();
			int y = e.getY()/settings.getSScaling();
			int markedNeighbours = 0;
			
			if(fields[x][y].getBombStatus() > 0 && fields[x][y].getBombStatus() < Field.BOMBED)
			{
				if(fields[x][y].checkBounds(Field.TOP_LEFT))
					if(fields[x-1][y-1].getFieldStatus() == Field.FLAGGED)
						markedNeighbours++;
				if(fields[x][y].checkBounds(Field.TOP))
					if(fields[x][y-1].getFieldStatus() == Field.FLAGGED)
						markedNeighbours++;
				if(fields[x][y].checkBounds(Field.TOP_RIGHT))
					if(fields[x+1][y-1].getFieldStatus() == Field.FLAGGED)
						markedNeighbours++;
				if(fields[x][y].checkBounds(Field.LEFT))
					if(fields[x-1][y].getFieldStatus() == Field.FLAGGED)
						markedNeighbours++;
				if(fields[x][y].checkBounds(Field.RIGHT))
					if(fields[x+1][y].getFieldStatus() == Field.FLAGGED)
						markedNeighbours++;
				if(fields[x][y].checkBounds(Field.BOT_LEFT))
					if(fields[x-1][y+1].getFieldStatus() == Field.FLAGGED)
						markedNeighbours++;
				if(fields[x][y].checkBounds(Field.BOT))
					if(fields[x][y+1].getFieldStatus() == Field.FLAGGED)
						markedNeighbours++;
				if(fields[x][y].checkBounds(Field.BOT_RIGHT))
					if(fields[x+1][y+1].getFieldStatus() == Field.FLAGGED)
						markedNeighbours++;
				
				if(markedNeighbours == fields[x][y].getBombStatus())
				{
					if(fields[x][y].checkBounds(Field.TOP_LEFT))
						if(fields[x-1][y-1].getFieldStatus() == Field.NOT_OPENED)
							openField(x-1,y-1);
					if(fields[x][y].checkBounds(Field.TOP))
						if(fields[x][y-1].getFieldStatus() == Field.NOT_OPENED)
							openField(x,y-1);
					if(fields[x][y].checkBounds(Field.TOP_RIGHT))
						if(fields[x+1][y-1].getFieldStatus() == Field.NOT_OPENED)
							openField(x+1,y-1);
					if(fields[x][y].checkBounds(Field.LEFT))
						if(fields[x-1][y].getFieldStatus() == Field.NOT_OPENED)
							openField(x-1,y);
					if(fields[x][y].checkBounds(Field.RIGHT))
						if(fields[x+1][y].getFieldStatus() == Field.NOT_OPENED)
							openField(x+1,y);
					if(fields[x][y].checkBounds(Field.BOT_LEFT))
						if(fields[x-1][y+1].getFieldStatus() == Field.NOT_OPENED)
							openField(x-1,y+1);
					if(fields[x][y].checkBounds(Field.BOT))
						if(fields[x][y+1].getFieldStatus() == Field.NOT_OPENED)
							openField(x,y+1);
					if(fields[x][y].checkBounds(Field.BOT_RIGHT))
						if(fields[x+1][y+1].getFieldStatus() == Field.NOT_OPENED)
							openField(x+1,y+1);
				}
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


	@Override
	public void mouseReleased(MouseEvent e)
	{
		int mx = e.getX()/settings.getSScaling();
		int my = e.getY()/settings.getSScaling();
		if(e.getButton() == MouseEvent.BUTTON1 && fields[mx][my].getFieldStatus() != Field.FLAGGED)
		{
			openField(mx,my);
		}
		else if(e.getButton() == MouseEvent.BUTTON3)
		{
			if(fields[mx][my].getFieldStatus() == Field.NOT_OPENED)
				fields[mx][my].setFieldStatus(Field.FLAGGED);
			else if(fields[mx][my].getFieldStatus() == Field.FLAGGED)
				fields[mx][my].setFieldStatus(Field.NOT_OPENED);
			paintComponent(this.getGraphics());
		}
	}
}
