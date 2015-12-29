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

/**
 * This class offers a window panel for all the settings to be changed.
 */
public class SettingsPanel extends JPanel
{	
	private SettingsWindow parent;
	private Settings settings;
	
	public SettingsPanel(SettingsWindow par)
	{
		this.parent = par;
		this.settings = this.parent.getSettings();
		this.setLayout(new BorderLayout());
		
		JPanel panSubmit = new JPanel(new GridLayout(1,2));
		this.add(panSubmit, BorderLayout.PAGE_END);
		JPanel panOverall = new JPanel(new GridLayout(2,2));
		this.add(panOverall, BorderLayout.CENTER);
		JPanel panTopLeft = new JPanel(new GridLayout(3,1));
		panOverall.add(panTopLeft);
		JPanel panTopRight = new JPanel(new GridLayout(2,1));
		panOverall.add(panTopRight);
		JPanel panCustom = new JPanel(new GridLayout(3,2));
		JPanel panBotLeft = new JPanel(new GridLayout(2,1));
		panOverall.add(panBotLeft);
		
		final JRadioButton radBeginner = new JRadioButton("Beginner");
		final JRadioButton radIntermediate = new JRadioButton("Intermediate");
		final JRadioButton radExpert = new JRadioButton("Expert");
		final JRadioButton radCustom = new JRadioButton("Custom");
		
		ButtonGroup radDifGrp = new ButtonGroup();
		radDifGrp.add(radBeginner);
		radDifGrp.add(radIntermediate);
		radDifGrp.add(radExpert);
		radDifGrp.add(radCustom);
		
		panTopLeft.add(radBeginner);
		panTopLeft.add(radIntermediate);
		panTopLeft.add(radExpert);
		
		panTopRight.add(radCustom);
		
		panTopRight.add(panCustom);
		
		JLabel labWidth = new JLabel("Width:");
		JLabel labHeight = new JLabel("Height:");
		JLabel labBombCount = new JLabel("Number of Bombs:");
		
		final JTextField txfWidth = new JTextField();
		final JTextField txfHeight = new JTextField();
		final JTextField txfBombCount = new JTextField();
		
		panCustom.add(labWidth);
		panCustom.add(txfWidth);
		panCustom.add(labHeight);
		panCustom.add(txfHeight);
		panCustom.add(labBombCount);
		panCustom.add(txfBombCount);
		
		final JRadioButton radAnimationsOn = new JRadioButton("Animations ON");
		final JRadioButton radAnimationsOff = new JRadioButton("Animations OFF");
		
		ButtonGroup radAnimGrp = new ButtonGroup();
		radAnimGrp.add(radAnimationsOn);
		radAnimGrp.add(radAnimationsOff);
		
		panBotLeft.add(radAnimationsOn);
		panBotLeft.add(radAnimationsOff);
		
		JButton butOk = new JButton("OK");
		butOk.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e)
		{
			if(radBeginner.isSelected())
			{
				settings.setDifficulty(Settings.DIF_EASY);
			}
			else if(radIntermediate.isSelected())
			{
				settings.setDifficulty(Settings.DIF_MEDIUM);
			}
			else if(radExpert.isSelected())
			{
				settings.setDifficulty(Settings.DIF_HARD);
			}
			else if(radCustom.isSelected())
			{
				if(txfWidth.getText().equals("") || txfHeight.getText().equals("") || txfBombCount.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "The entered values are invalid!","Fehler",JOptionPane.OK_CANCEL_OPTION);
					return;
				}
				int enteredWidth = Integer.parseInt(txfWidth.getText());
				int enteredHeight = Integer.parseInt(txfHeight.getText());
				int enteredBombCount = Integer.parseInt(txfBombCount.getText());
				
				if(enteredWidth < 1 || enteredWidth > 100 || 
						enteredHeight < 1 || enteredHeight > 100 ||
						enteredBombCount < 1 || enteredBombCount > (enteredWidth * enteredHeight))
				{
					JOptionPane.showMessageDialog(null,"The entered values are invalid!", "Fehler",JOptionPane.OK_CANCEL_OPTION);
					return;
				}
				settings.setCustomParameters(enteredWidth,enteredHeight,enteredBombCount);
			}
			if(radAnimationsOn.isSelected())
				settings.setAnimation(true);
			else if(radAnimationsOff.isSelected())
				settings.setAnimation(false);
			
			parent.setVisible(false);
			parent.newGame();
		}});
		JButton butCancel = new JButton("Cancel");
		butCancel.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e)
		{
			parent.setVisible(false);	
		}});		
		panSubmit.add(butCancel);
		panSubmit.add(butOk);
	}	
}
