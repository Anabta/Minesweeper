package minesweeper;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.BorderLayout;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class SettingsPanel extends JPanel
{	
	private SettingsWindow parent;
	
	public SettingsPanel(SettingsWindow par)
	{
		this.parent = par;
		this.setLayout(new BorderLayout());
		
		JPanel submitGrid = new JPanel(new GridLayout(1,2));
		this.add(submitGrid, BorderLayout.PAGE_END);
		JPanel overallGrid = new JPanel(new GridLayout(1,2));
		this.add(overallGrid, BorderLayout.CENTER);
		JPanel leftGrid = new JPanel(new GridLayout(3,1));
		overallGrid.add(leftGrid);
		JPanel rightGrid = new JPanel(new GridLayout(2,1));
		overallGrid.add(rightGrid);
		JPanel customGrid = new JPanel(new GridLayout(3,2));
		
		final JRadioButton radBeginner = new JRadioButton("Anfänger");
		final JRadioButton radIntermediate = new JRadioButton("Fortgeschrittener");
		final JRadioButton radExpert = new JRadioButton("Experte");
		final JRadioButton radCustom = new JRadioButton("Benutzerdefiniert");
		
		ButtonGroup radGrp = new ButtonGroup();
		radGrp.add(radBeginner);
		radGrp.add(radIntermediate);
		radGrp.add(radExpert);
		radGrp.add(radCustom);
		
		leftGrid.add(radBeginner);
		leftGrid.add(radIntermediate);
		leftGrid.add(radExpert);
		
		rightGrid.add(radCustom);
		
		rightGrid.add(customGrid);
		
		JLabel labWidth = new JLabel("Breite:");
		JLabel labHeight = new JLabel("Höhe:");
		JLabel labBombCount = new JLabel("Bombenzahl:");
		
		final JTextField tfWidth = new JTextField();
		final JTextField tfHeight = new JTextField();
		final JTextField tfBombCount = new JTextField();
		
		customGrid.add(labWidth);
		customGrid.add(tfWidth);
		customGrid.add(labHeight);
		customGrid.add(tfHeight);
		customGrid.add(labBombCount);
		customGrid.add(tfBombCount);
		
		JButton butOk = new JButton("OK");
		butOk.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e)
		{
			if(radBeginner.isSelected())
			{
				parent.setSDifficulty(SettingsWindow.DIF_EASY);
			}
			else if(radIntermediate.isSelected())
			{
				parent.setSDifficulty(SettingsWindow.DIF_MEDIUM);
			}
			else if(radExpert.isSelected())
			{
				parent.setSDifficulty(SettingsWindow.DIF_HARD);
			}
			else if(radCustom.isSelected())
			{
				int enteredWidth = Integer.parseInt(tfWidth.getText());
				int enteredHeight = Integer.parseInt(tfHeight.getText());
				int enteredBombCount = Integer.parseInt(tfBombCount.getText());
				if(enteredWidth < 1 || enteredWidth > 100 || 
						enteredHeight < 1 || enteredHeight > 100 ||
						enteredBombCount < 1 || enteredBombCount > (enteredWidth * enteredHeight))
				{
					JOptionPane.showMessageDialog(null,"Die eingegebenen Werte sind nicht gültig!", "Fehler",JOptionPane.OK_CANCEL_OPTION);
					return;
				}
				parent.setCustomParameters(enteredWidth,enteredHeight,enteredBombCount);
			}
			parent.setVisible(false);
		}});
		JButton butCancel = new JButton("Abbrechen");
		butCancel.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e)
		{
			parent.setVisible(false);	
		}});		
		submitGrid.add(butCancel);
		submitGrid.add(butOk);
	}	
}
