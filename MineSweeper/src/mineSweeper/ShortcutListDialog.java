package mineSweeper;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ShortcutListDialog extends JDialog 
{
	/*
	 * this dialog shows shortcut list.
	 * that's it.
	 */
	private final JPanel contentPanel = new JPanel();
	private JPanel panel;
	private JLabel lblNewGame;
	private JLabel lblPause;
	private JLabel lblSaveGame;
	private JLabel lblLoadGame;
	private JLabel lblOption;
	private JLabel lblStatistics;
	private JLabel lblExit;
	private JDialog dlg;
	private JLabel lblShortcut;

	/**
	 * Create the dialog.
	 */
	public ShortcutListDialog(JFrame parent) 
	{
		dlg = this;
		parent.setEnabled(false);
		setTitle("Shortcut List");
		setAlwaysOnTop(true);
		setVisible(true);
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
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
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			panel = new JPanel();
			panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.BLUE, null, new Color(0, 0, 0), null));
			panel.setBackground(Color.CYAN);
			{
				JLabel lblShortcutList = new JLabel("< Shortcut List >");
				lblShortcutList.setForeground(Color.BLUE);
				lblShortcutList.setBackground(Color.BLUE);
				lblShortcutList.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 20));
				panel.add(lblShortcutList);
			}
		}
		{
			lblNewGame = new JLabel("New Game: F2");
			lblNewGame.setFont(new Font("Arial Black", Font.ITALIC, 15));
			lblNewGame.setHorizontalAlignment(SwingConstants.LEFT);
		}
		{
			lblPause = new JLabel("Pause: Ctrl+Q");
			lblPause.setFont(new Font("Arial Black", Font.ITALIC, 15));
			lblPause.setHorizontalAlignment(SwingConstants.LEFT);
		}
		{
			lblSaveGame = new JLabel("Save Game: Ctrl+S");
			lblSaveGame.setFont(new Font("Arial Black", Font.ITALIC, 14));
			lblSaveGame.setHorizontalAlignment(SwingConstants.LEFT);
		}
		{
			lblLoadGame = new JLabel("Load Game: Ctrl+L");
			lblLoadGame.setFont(new Font("Arial Black", Font.ITALIC, 14));
			lblLoadGame.setHorizontalAlignment(SwingConstants.LEFT);
		}
		{
			lblOption = new JLabel("Option: F3");
			lblOption.setFont(new Font("Arial Black", Font.ITALIC, 15));
			lblOption.setHorizontalAlignment(SwingConstants.LEFT);
		}
		{
			lblStatistics = new JLabel("Statistics: F4");
			lblStatistics.setFont(new Font("Arial Black", Font.ITALIC, 15));
			lblStatistics.setHorizontalAlignment(SwingConstants.LEFT);
		}
		{
			lblExit = new JLabel("Exit: Ctrl+X");
			lblExit.setHorizontalAlignment(SwingConstants.LEFT);
			lblExit.setFont(new Font("Arial Black", Font.ITALIC, 14));
		}
		lblShortcut = new JLabel("Shortcut Toggle: Ctrl+t");
		lblShortcut.setFont(new Font("Arial Black", Font.ITALIC, 14));
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 422, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblLoadGame, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addGap(30)
									.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblPause, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewGame, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)))
								.addComponent(lblSaveGame, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblOption, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblStatistics, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblExit, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblShortcut, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblOption, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
							.addGap(2)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblPause, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblStatistics)))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(64)
							.addComponent(lblNewGame, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
					.addGap(31)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSaveGame, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblShortcut))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLoadGame, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblExit, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnOK = new JButton("OK");
				btnOK.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						dlg.dispose();
					}
				});
				btnOK.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 18));
				btnOK.setActionCommand("Cancel");
				buttonPane.add(btnOK);
			}
		}
	}

}
