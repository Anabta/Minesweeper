package minesweeper;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;


public class CopyOfPlayingField extends JPanel implements MouseListener
{
	private int width;
	private int height;
	private int scaling;
	private int bombCount;
	private int[][] bombs;			//1-8=nearby bombs ; 9=bomb
	private int[][] fields;			//0=not opened ; 1=opened
	private SettingsWindow settings;
	
	public CopyOfPlayingField(SettingsWindow set)
	{
		this.settings = set;
		this.width = settings.getSWidth();
		this.height = settings.getSHeight();
		this.scaling = settings.getSScaling(); 
		this.bombCount = settings.getSBombCount();
		
		this.bombs = new int[width][height];
		this.fields = new int[width][height];
		
		this.placeBombs();
		this.initFields();
		this.setPreferredSize(new Dimension(width*scaling,height*scaling));
		
		addMouseListener(this);
	}
	
	
	@Override
	public void paintComponent(Graphics g)
	{
		for(int i = 0; i <= width; i++)
		{
			g.drawLine(i*scaling, 0, i*scaling, height*scaling);
		}
		
		for(int i = 0; i <= height; i++)
		{
			g.drawLine(0, i*scaling, width*scaling, i*scaling);
		}
		
		for(int top = 0; top < height; top++)
		{
			for(int left = 0; left < width; left++)
			{
				if(fields[left][top] == 1)
				{
					if(bombs[left][top] == 9)
						g.drawString("x", left*scaling + scaling/2, top*scaling + scaling/2);
					else if(bombs[left][top] > 0)
						g.drawString(bombs[left][top] + "", left*scaling + scaling/2, top*scaling + scaling/2);
				}
			}
		}
	}
	
	private void initFields()
	{
		for(int top = 0; top < height; top++)
		{
			for(int left = 0; left < width; left++)
			{
				this.fields[left][top] = 0;
			}
		}
	}
	
	private void placeBombs()
	{
		int left, top;
		for(int i = 0; i < bombCount; )
		{
			left = (int) (Math.random()*width);
			top = (int) (Math.random()*height);
			if(bombs[left][top] != 9)
			{
				bombs[left][top] = 9;
				if(left-1 >= 0 && top-1 >= 0 && left-1 < width && top-1 < height)
					if(bombs[left-1][top-1] != 9)
						bombs[left-1][top-1]++;
				if(left >= 0 && top-1 >= 0 && left < width && top-1 < height)
					if(bombs[left][top-1] != 9)
						bombs[left][top-1]++;
				if(left+1 >= 0 && top-1 >= 0 && left+1 < width && top-1 < height)
					if(bombs[left+1][top-1] != 9)
						bombs[left+1][top-1]++;
				if(left-1 >= 0 && top >= 0 && left-1 < width && top < height)
					if(bombs[left-1][top] != 9)
						bombs[left-1][top]++;
				if(left+1 >= 0 && top >= 0 && left+1 < width && top < height)
					if(bombs[left+1][top] != 9)
						bombs[left+1][top]++;
				if(left-1 >= 0 && top+1 >= 0 && left-1 < width && top+1 < height)
					if(bombs[left-1][top+1] != 9)
						bombs[left-1][top+1]++;
				if(left >= 0 && top+1 >= 0 && left < width && top+1 < height)
					if(bombs[left][top+1] != 9)
						bombs[left][top+1]++;
				if(left+1 >= 0 && top+1 >= 0 && left+1 < width && top+1 < height)
					if(bombs[left+1][top+1] != 9)
						bombs[left+1][top+1]++;
				i++;
			}
		}
	}


	@Override
	public void mouseClicked(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e)
	{
		fields[e.getX()/settings.getSScaling()][e.getY()/settings.getSScaling()] = 1;
		System.out.println("Mouse released!!");
	}
}
