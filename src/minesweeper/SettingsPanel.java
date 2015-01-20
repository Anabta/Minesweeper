package minesweeper;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

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
		
		JPanel panSubmit = new JPanel(new GridLayout(1,2));
		this.add(panSubmit, BorderLayout.PAGE_END);
		JPanel panOverall = new JPanel(new GridLayout(1,2));
		this.add(panOverall, BorderLayout.CENTER);
		JPanel panLeft = new JPanel(new GridLayout(3,1));
		panOverall.add(panLeft);
		JPanel panRight = new JPanel(new GridLayout(2,1));
		panOverall.add(panRight);
		JPanel panCustom = new JPanel(new GridLayout(3,2));
		
		final JRadioButton radBeginner = new JRadioButton("Anfänger");
		final JRadioButton radIntermediate = new JRadioButton("Fortgeschrittener");
		final JRadioButton radExpert = new JRadioButton("Experte");
		final JRadioButton radCustom = new JRadioButton("Benutzerdefiniert");
		
		ButtonGroup radGrp = new ButtonGroup();
		radGrp.add(radBeginner);
		radGrp.add(radIntermediate);
		radGrp.add(radExpert);
		radGrp.add(radCustom);
		
		panLeft.add(radBeginner);
		panLeft.add(radIntermediate);
		panLeft.add(radExpert);
		
		panRight.add(radCustom);
		
		panRight.add(panCustom);
		
		JLabel labWidth = new JLabel("Breite:");
		JLabel labHeight = new JLabel("Höhe:");
		JLabel labBombCount = new JLabel("Bombenzahl:");
		
		final JTextField txfWidth = new JTextField();
		final JTextField txfHeight = new JTextField();
		final JTextField txfBombCount = new JTextField();
		
		panCustom.add(labWidth);
		panCustom.add(txfWidth);
		panCustom.add(labHeight);
		panCustom.add(txfHeight);
		panCustom.add(labBombCount);
		panCustom.add(txfBombCount);
		
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
				int enteredWidth = Integer.parseInt(txfWidth.getText());
				int enteredHeight = Integer.parseInt(txfHeight.getText());
				int enteredBombCount = Integer.parseInt(txfBombCount.getText());
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
			parent.newGame();
		}});
		JButton butCancel = new JButton("Abbrechen");
		butCancel.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e)
		{
			parent.setVisible(false);	
		}});		
		panSubmit.add(butCancel);
		panSubmit.add(butOk);
	}	
}
