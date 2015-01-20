package minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class PlayingField extends JPanel implements MouseListener
{
	private int[][] bombs;			//1-8=nearby bombs ; 9=bomb
	private int[][] fields;			//0=not opened ; 1=opened ; 2=flag
	private SettingsWindow settings;
	private MainWindow mainWindow;
	private int fieldsToOpen;
	
	public PlayingField(SettingsWindow set, MainWindow mw)
	{
		this.settings = set;
		this.mainWindow = mw;
		
		int w = settings.getSWidth();
		int h = settings.getSHeight();
		
		this.bombs = new int[w][h];
		this.fields = new int[w][h];
		
		this.placeBombs();
		this.initFields();
		this.setSize(new Dimension(w*settings.getSScaling(),h*settings.getSScaling()));
				
		this.fieldsToOpen = (settings.getSWidth()*settings.getSHeight())-settings.getSBombCount();
		
		addMouseListener(this);
	}
	
	
	@Override
	public void paintComponent(Graphics g)
	{
		int w = settings.getSWidth();
		int h = settings.getSHeight();
		int s = settings.getSScaling();
		
		for(int top = 0; top < h; top++)
		{
			for(int left = 0; left < w; left++)
			{
				if(fields[left][top] == 0)
				{
					g.setColor(Color.DARK_GRAY);
					g.fillRect(left*s, top*s, s, s);
				}
				else if(fields[left][top] == 1)
				{
					g.setColor(Color.GRAY);
					g.fillRect(left*s, top*s, s, s);
					
					g.setColor(Color.BLACK);
					if(bombs[left][top] == 9)
						g.drawString("x", left*s + s/2, top*s + s/2);
					else if(bombs[left][top] > 0)
						g.drawString(bombs[left][top] + "", left*s + s/2, top*s + s/2);
				}
				else if(fields[left][top] == 2)
				{
					g.setColor(Color.RED);
					g.fillRect(left*s, top*s, s, s);
				}
			}
		}
		
		g.setColor(Color.BLACK);
		for(int i = 0; i <= w; i++)
		{
			g.drawLine(i*s, 0, i*s, h*s);
		}
		
		for(int i = 0; i <= h; i++)
		{
			g.drawLine(0, i*s, w*s, i*s);
		}
	}
	
	private void initFields()
	{
		for(int top = 0; top < settings.getSHeight(); top++)
		{
			for(int left = 0; left < settings.getSWidth(); left++)
			{
				this.fields[left][top] = 0;
			}
		}
	}
	
	private void placeBombs()
	{
		int w = settings.getSWidth();
		int h = settings.getSHeight();
		
		int left, top;
		for(int i = 0; i < settings.getSBombCount(); )
		{
			left = (int) (Math.random()*w);
			top = (int) (Math.random()*h);
			if(bombs[left][top] != 9)
			{
				bombs[left][top] = 9;
				if(left-1 >= 0 && top-1 >= 0 && left-1 < w && top-1 < h)
					if(bombs[left-1][top-1] != 9)
						bombs[left-1][top-1]++;
				if(left >= 0 && top-1 >= 0 && left < w && top-1 < h)
					if(bombs[left][top-1] != 9)
						bombs[left][top-1]++;
				if(left+1 >= 0 && top-1 >= 0 && left+1 < w && top-1 < h)
					if(bombs[left+1][top-1] != 9)
						bombs[left+1][top-1]++;
				if(left-1 >= 0 && top >= 0 && left-1 < w && top < h)
					if(bombs[left-1][top] != 9)
						bombs[left-1][top]++;
				if(left+1 >= 0 && top >= 0 && left+1 < w && top < h)
					if(bombs[left+1][top] != 9)
						bombs[left+1][top]++;
				if(left-1 >= 0 && top+1 >= 0 && left-1 < w && top+1 < h)
					if(bombs[left-1][top+1] != 9)
						bombs[left-1][top+1]++;
				if(left >= 0 && top+1 >= 0 && left < w && top+1 < h)
					if(bombs[left][top+1] != 9)
						bombs[left][top+1]++;
				if(left+1 >= 0 && top+1 >= 0 && left+1 < w && top+1 < h)
					if(bombs[left+1][top+1] != 9)
						bombs[left+1][top+1]++;
				i++;
			}
		}
	}
	
	public void openField(int x, int y)
	{		
		fields[x][y] = 1;
		fieldsToOpen--;
		paintComponent(this.getGraphics());
		
		if(fieldsToOpen < 1)
		{
			for(int top = 0; top < settings.getSHeight(); top++)
			{
				for(int left = 0; left < settings.getSWidth(); left++)
				{
					this.fields[left][top] = 1;
				}
			}
			paintComponent(this.getGraphics());
			
			JOptionPane.showMessageDialog(null, "Sie haben gewonnen!", "Glückwunsch", JOptionPane.OK_CANCEL_OPTION);
			mainWindow.newGame();
		}
		
		if(bombs[x][y] < 1)
		{
			int w = settings.getSWidth();
			int h = settings.getSHeight();
			
			if(x-1 >= 0 && y-1 >= 0 && x-1 < w && y-1 < h)
				if(fields[x-1][y-1] == 0)
					openField(x-1,y-1);
			if(x >= 0 && y-1 >= 0 && x < w && y-1 < h)
				if(fields[x][y-1] == 0)
					openField(x,y-1);
			if(x+1 >= 0 && y-1 >= 0 && x+1 < w && y-1 < h)
				if(fields[x+1][y-1] == 0)
					openField(x+1,y-1);
			if(x-1 >= 0 && y >= 0 && x-1 < w && y < h)
				if(fields[x-1][y] == 0)
					openField(x-1,y);
			if(x+1 >= 0 && y >= 0 && x+1 < w && y < h)
				if(fields[x+1][y] == 0)
					openField(x+1,y);
			if(x-1 >= 0 && y+1 >= 0 && x-1 < w && y+1 < h)
				if(fields[x-1][y+1] == 0)
					openField(x-1,y+1);
			if(x >= 0 && y+1 >= 0 && x < w && y+1 < h)
				if(fields[x][y+1] == 0)
					openField(x,y+1);
			if(x+1 >= 0 && y+1 >= 0 && x+1 < w && y+1 < h)
				if(fields[x+1][y+1] == 0)
					openField(x+1,y+1);
		}
	}
	
	public void gameOver()
	{
		for(int top = 0; top < settings.getSHeight(); top++)
		{
			for(int left = 0; left < settings.getSWidth(); left++)
			{
				this.fields[left][top] = 1;
			}
		}
		paintComponent(this.getGraphics());
		
		JOptionPane.showMessageDialog(null, "Sie haben verloren!", "Schade", JOptionPane.OK_CANCEL_OPTION);
		mainWindow.newGame();
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(e.getClickCount() == 2)
		{
			System.out.println("Doppelklick!!");
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
		if(e.getButton() == MouseEvent.BUTTON1 && fields[mx][my] != 2)
		{
			if(bombs[mx][my] == 9)
			{
				gameOver();
			}
			if(fields[mx][my] != 1)
			{
				openField(mx,my);
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON3)
		{
			fields[mx][my] = 2;
			paintComponent(this.getGraphics());
		}
	}
}
