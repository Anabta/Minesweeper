package minesweeper;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class PlayingField extends JPanel
{
	private int width;
	private int height;
	private int scaling;
	private int bombCount;
	private int[][] bombs;
	
	public PlayingField(SettingsWindow settings)
	{
		this.width = settings.getWidth();
		this.height = settings.getHeight();
		this.scaling = settings.getScaling(); 
		this.bombCount = settings.getBombCount();
		this.bombs = new int[width][height];
		
//		for(int i = 0; i < height; i++)
//			for(int j = 0; j < width; j++)
//				bombs[j][i] = 0;
//		
//		bombs[0][0] = 9;
//		bombs[14][0] = 9;
//		bombs[14][14] = 9;
		
		this.placeBombs();
		this.setPreferredSize(new Dimension(width*scaling,height*scaling));
		
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
				System.out.print(bombs[j][i] + " ");
			System.out.print("\n");
		}
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
				if(bombs[left][top] == 9)
					g.drawString("x", left*scaling + scaling/2, top*scaling + scaling/2);
				else if(bombs[left][top] > 0)
					g.drawString(bombs[left][top] + "", left*scaling + scaling/2, top*scaling + scaling/2);
			}
		}
	}
	
	public void placeBombs()
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
}
