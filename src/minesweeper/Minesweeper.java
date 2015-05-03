package minesweeper;

import javax.swing.UIManager;

/**
 * This is the entry point for the Application.
 * Here the look and feel is set and the main class is instantiated.
 *
 * @author Florian Lüdiger
 */
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
