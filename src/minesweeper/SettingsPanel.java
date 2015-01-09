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
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class SettingsPanel extends JPanel
{	
	private SettingsWindow parent;
	
	public SettingsPanel(SettingsWindow parent)
	{
		this.parent = parent;
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
		
		JRadioButton radBeginner = new JRadioButton("Anfänger");
		JRadioButton radIntermediate = new JRadioButton("Fortgeschrittener");
		JRadioButton radExpert = new JRadioButton("Experte");
		JRadioButton radCustom = new JRadioButton("Benutzerdefiniert");
		
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
		
		JTextField tfWidth = new JTextField();
		JTextField tfHeight = new JTextField();
		JTextField tfBombCount = new JTextField();
		
		customGrid.add(labWidth);
		customGrid.add(tfWidth);
		customGrid.add(labHeight);
		customGrid.add(tfHeight);
		customGrid.add(labBombCount);
		customGrid.add(tfBombCount);
		
		JButton butOk = new JButton("OK");
		JButton butCancel = new JButton("Abbrechen");
		
		submitGrid.add(butCancel);
		submitGrid.add(butOk);
	}	
}
