package mineSweeper;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Image;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class PauseDialog extends JDialog
{

	/*
	 * this panel is about pause statement.
	 */
	private final JPanel contentPanel = new JPanel();
	private JDialog dlg;

	/**
	 * Create the dialog.
	 */
	public PauseDialog(JFrame parent) 
	{
		/*
		 * this panel is very simple.
		 * dialog logic is very similar to "OptionPanel".
		 * this dialog's main function is to STOP timer until user relieves pause state.
		 */
		dlg = this;
		parent.setEnabled(false);
		setTitle("Paused");
		setAlwaysOnTop(true);
		setBounds(100, 100, 450, 450);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		ImageIcon pauseImage = new ImageIcon(getClass().getResource("/image/pausebasic.jpg"));
		Image psimage = pauseImage.getImage();
		psimage = psimage.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
		
		
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		JButton btnPauseIcon = new JButton();
		btnPauseIcon.setFocusPainted(false);
		btnPauseIcon.setBorderPainted(false);
		btnPauseIcon.setContentAreaFilled(false);
		btnPauseIcon.setIcon(new ImageIcon(psimage));
		
		ImageIcon pauseRolloverImage = new ImageIcon(getClass().getResource("/image/pauserollover.jpg"));
		Image psroimage = pauseRolloverImage.getImage();
		psroimage = psroimage.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
		btnPauseIcon.setRolloverIcon(new ImageIcon(psroimage));
		
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
					Frame.timer.start();
				System.out.println("CLOSED");
			}
		});
		
		contentPanel.add(btnPauseIcon);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnRelievePause = new JButton("Relieve Pause State");
				btnRelievePause.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						dlg.dispose();
					}
				});
				btnRelievePause.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 18));
				btnRelievePause.setActionCommand("Cancel");
				buttonPane.add(btnRelievePause);
				
				
			}
		}
		
		
	}

}
