package mineSweeper;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * this class is used for Menu->Option click.
 */
public class OptionDialog extends JDialog
{
	/*
	 * fill this dialog to OptionPanel(JPanel). 
	 */
	private OptionPanel panel;
	private JDialog dlg;
	
	
	public OptionDialog(JFrame parent, Timer timer) 
	{
		super();
		parent.setEnabled(false);
		this.setTitle("Options");
		this.setSize(500, 500);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setBackground(Color.black);
		dlg = this;
		
		/*
		 * if this dialog is toggled, this dialog always goes on top.
		 * and if this dialog is closed,
		 * set main frame enable and if game is not GAMEOVER state,
		 * restart timer.
		 */
		this.addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowOpened(WindowEvent e) 
			{
				dlg.setAlwaysOnTop(true);
			}
			@Override
			public void windowClosed(WindowEvent e) 
			{
				parent.setEnabled(true);
				parent.setAlwaysOnTop(true);
				parent.setAlwaysOnTop(false);
				if(!Frame.isGameOver)
					timer.start();
				System.out.println("CLOSED");
			}
		});
	    
	    panel = new OptionPanel();
	    panel.btnCancel.addActionListener(new ActionListener() 
	    {
			
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				/*
				 * Cancel option.
				 * just close the dialog.
				 */
				
				dlg.dispose();
				
			}
		});
	    panel.btnOK.addActionListener(new ActionListener() 
	    {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				parent.setEnabled(true);
				timer.start();
				if(Frame.difficulty == panel.difficulty && panel.difficulty != 4)
				{
					/*
					 * difficulty is not changed. just close dialog. 
					 */
					
					dlg.dispose();
				}
				else if(Frame.difficulty != panel.difficulty || panel.difficulty == 4 ) // difficulty is changed
				{
					if(panel.difficulty == 4 && panel.textFieldWidth.getText() != null && 
						panel.textFieldHeight.getText() != null && panel.textFieldMines.getText() != null)
					{
						/*
						 * if difficulty is 4 ( Custom ),
						 * width, height, mines should not be null.
						 * if just one of them is null, show warning dialog.
						 * or, set each value to the actual width, height, mineCount and close the dialog.
						 */
						try
						{
							Frame.CUSTOM_WIDTH = Integer.parseInt(panel.textFieldWidth.getText());
							Frame.CUSTOM_HEIGHT = Integer.parseInt(panel.textFieldHeight.getText());
							Frame.CUSTOM_MINES = Integer.parseInt(panel.textFieldMines.getText());
							Frame.difficulty = panel.difficulty;
							
							dlg.dispose();
						}
						catch(NumberFormatException ex)
						{
							JOptionPane.showMessageDialog(null, "width, height, mines must be a number.", "NumberFormatException", JOptionPane.WARNING_MESSAGE);
						}
						
					}
					else // case difficulty : 1 or 2 or 3
					{
						/*
						 * just change actual difficulty.
						 * because in difficulty 1, 2, 3,
						 * width, height, the number of mines are automatically set.
						 */
						Frame.difficulty = panel.difficulty;
						System.out.println("difficulty is set : " + Frame.difficulty);
						
						dlg.dispose();
					}
				}
				else
				{
					System.out.println("exception");
					
					dlg.dispose();
				}
			}
		});
	    this.add(panel);
	  
	    System.out.println("option");
	    
	}
}
