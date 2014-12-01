package minesweeper;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainWindow extends JFrame implements ActionListener
{
	public PlayingField pf;
	
	public MainWindow()
	{
		Dimension prefSize = new Dimension();
		pf = new PlayingField(30, 15, 40, 20);
		this.setResizable(false);
		this.setContentPane(pf);
		prefSize.setSize(pf.getPreferredSize().getWidth()+7, pf.getPreferredSize().getHeight() +53);
		this.setPreferredSize(prefSize);
		this.setTitle("Minesweeper");
		this.setLocation(new Point(200,200));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setJMenuBar(createMenuBar());
		
		this.pack();
		this.setVisible(true);
	}
	
	private JMenuBar createMenuBar()
	{
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu();
		menu.setText("Menü");
		JMenuItem settings = new JMenuItem();
		settings.setText("Einstellungen");
		settings.addActionListener(this);
		menu.add(settings);
		menuBar.add(menu);
		return menuBar;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand() == "Einstellungen")
		System.out.println("Einstellungen wurde gedrückt!!");
	}
}
