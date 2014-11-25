package minesweeper;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class PlayingField extends JPanel
{
	private int width;
	private int height;
	
	public PlayingField(int w, int h)
	{
		width = w;
		height = h;
		this.setPreferredSize(new Dimension(w*20,h*20));
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		g.drawLine(0, 0, 400, 400);
	}
}
