package minesweeper;

import javax.swing.UIManager;

public class Minesweeper
{

	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		MainWindow game = new MainWindow();
	}

}
