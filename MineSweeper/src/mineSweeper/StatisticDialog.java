package mineSweeper;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.awt.event.ActionEvent;

public class StatisticDialog extends JDialog 
{
	/*
	 * this dialog shows statistics information.
	 */
	private JDialog dlg;
	private JPanel pnLevel;
	private JPanel pnBestTime;
	private JPanel pnStatistics;
	private JScrollPane scrollPane;
	private JLabel lblLevel;
	private JLabel lblBeginner;
	private JList<String> list;
	private DefaultListModel<String> levelList;
	private JLabel lblBestTimes;
	private JTextField txtSec;
	private JLabel lblGamePlayed;
	private JLabel lblGameWin;
	private JLabel lblWinPercentage;
	private JLabel lblWinningStreak;
	private JLabel lblLosingStreak;
	private JLabel lblCurrentStreak;
	private JButton btnReset;
	private JButton btnClose;
	
	private String txtGamesPlayed;
	private String txtGamesWin;
	private String txtWinPercentage;
	private String txtWinningStreak;
	private String txtLosingStreak;
	private String txtCurrentStreak;
	
	/**
	 * Create the dialog.
	 */
	public StatisticDialog() 
	{
		super();
		getContentPane().setBackground(Color.GREEN);
		dlg = this;
		this.setTitle("Statistics");
		
		this.setSize(500, 260);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(0, 3, 0, 0));
		
		pnLevel = new JPanel();
		pnLevel.setPreferredSize(new Dimension(100, 10));
		getContentPane().add(pnLevel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		GroupLayout gl_pnLevel = new GroupLayout(pnLevel);
		gl_pnLevel.setHorizontalGroup(
			gl_pnLevel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnLevel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_pnLevel.setVerticalGroup(
			gl_pnLevel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnLevel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(79, Short.MAX_VALUE))
		);
		
		lblLevel = new JLabel(" Level");
		lblLevel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.BLACK, null, null, null));
		lblLevel.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 15));
		scrollPane.setColumnHeaderView(lblLevel);
		
		
		levelList = new DefaultListModel<String>();
		levelList.addElement(" Beginner");
		levelList.addElement(" Intermediate");
		levelList.addElement(" Advanced");
		levelList.addElement(" Custom");
		list = new JList<String>(levelList);
		list.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e) 
			{
				if (!e.getValueIsAdjusting()) // prevent double selection.
				{
					/*
					 * update each level's best time when user selects each list field.
					 */
					if(list.getSelectedValue().toString().equals(" Beginner"))
					{
						txtSec.setText(Frame.beginnerBestTime + " sec");
						lblGamePlayed.setText(txtGamesPlayed + Frame.gamesPlayed);
						lblGameWin.setText(txtGamesWin + Frame.gamesWin);
						lblWinPercentage.setText(txtWinPercentage + (int)Frame.winPercentage + "%");
						lblWinningStreak.setText(txtWinningStreak + Frame.winningStreak);
						lblLosingStreak.setText(txtLosingStreak + Frame.losingStreak);
						lblCurrentStreak.setText(txtCurrentStreak + Frame.currentStreak);
						System.out.println("losingstreak : " + Frame.losingStreak);
						System.out.println("winningstreak : " + Frame.winningStreak);
						System.out.println("currentstreak : " + Frame.currentStreak);
						System.out.println("beginner best time : " + Frame.beginnerBestTime);
						System.out.println("inter best time : " + Frame.intermediateBestTime);
						System.out.println("advanced best time : " + Frame.advancedBestTime);
					}
					else if(list.getSelectedValue().toString().equals(" Intermediate"))
					{
						txtSec.setText(Frame.intermediateBestTime + " sec");
						lblGamePlayed.setText(txtGamesPlayed + Frame.gamesPlayed);
						lblGameWin.setText(txtGamesWin + Frame.gamesWin);
						lblWinPercentage.setText(txtWinPercentage + (int)Frame.winPercentage + "%");
						lblWinningStreak.setText(txtWinningStreak + Frame.winningStreak);
						lblLosingStreak.setText(txtLosingStreak + Frame.losingStreak);
						lblCurrentStreak.setText(txtCurrentStreak + Frame.currentStreak);
						System.out.println("gameplayed : " + Frame.gamesPlayed);
						lblGamePlayed.setText(txtGamesPlayed + Frame.gamesPlayed);
					}
					else if(list.getSelectedValue().toString().equals(" Advanced"))
					{
						txtSec.setText(Frame.advancedBestTime + " sec");
						lblGamePlayed.setText(txtGamesPlayed + Frame.gamesPlayed);
						lblGameWin.setText(txtGamesWin + Frame.gamesWin);
						lblWinPercentage.setText(txtWinPercentage + (int)Frame.winPercentage + "%");
						lblWinningStreak.setText(txtWinningStreak + Frame.winningStreak);
						lblLosingStreak.setText(txtLosingStreak + Frame.losingStreak);
						lblCurrentStreak.setText(txtCurrentStreak + Frame.currentStreak);
						System.out.println("advanced : " + Frame.currentStreak);
					}
					else
					{
						txtSec.setText(Frame.customBestTime + " sec");
						lblGamePlayed.setText(txtGamesPlayed + Frame.gamesPlayed);
						lblGameWin.setText(txtGamesWin + Frame.gamesWin);
						lblWinPercentage.setText(txtWinPercentage + (int)Frame.winPercentage + "%");
						lblWinningStreak.setText(txtWinningStreak + Frame.winningStreak);
						lblLosingStreak.setText(txtLosingStreak + Frame.losingStreak);
						lblCurrentStreak.setText(txtCurrentStreak + Frame.currentStreak);
					}
				}
			}
		});
		list.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
		list.setBackground(Color.CYAN);
		list.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.BLACK, null, null, null));
		scrollPane.setViewportView(list);
		
		
		pnLevel.setLayout(gl_pnLevel);
		
		pnBestTime = new JPanel();
		getContentPane().add(pnBestTime);
		
		lblBestTimes = new JLabel("Best Times:");
		lblBestTimes.setFont(new Font("Berlin Sans FB", Font.PLAIN, 15));
		
		txtSec = new JTextField();
		txtSec.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 19));
		txtSec.setHorizontalAlignment(JTextField.CENTER);
		txtSec.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.BLACK, null, null, null));
		txtSec.setText("20 sec");
		txtSec.setEditable(false);
		txtSec.setColumns(10);
		GroupLayout gl_pnBestTime = new GroupLayout(pnBestTime);
		gl_pnBestTime.setHorizontalGroup(
			gl_pnBestTime.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnBestTime.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnBestTime.createParallelGroup(Alignment.LEADING)
						.addComponent(txtSec, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
						.addComponent(lblBestTimes, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_pnBestTime.setVerticalGroup(
			gl_pnBestTime.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnBestTime.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblBestTimes)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtSec, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(79, Short.MAX_VALUE))
		);
		pnBestTime.setLayout(gl_pnBestTime);
		
		pnStatistics = new JPanel();
		getContentPane().add(pnStatistics);
		
		lblGamePlayed = new JLabel("Game Played: ");
		lblGamePlayed.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 11));
		
		lblGameWin = new JLabel("Games Win: ");
		lblGameWin.setForeground(Color.BLUE);
		lblGameWin.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 11));
		
		lblWinPercentage = new JLabel("Win Percentage: ");
		lblWinPercentage.setForeground(Color.BLUE);
		lblWinPercentage.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		
		lblWinningStreak = new JLabel("Best Winning Streak: ");
		lblWinningStreak.setForeground(Color.BLUE);
		lblWinningStreak.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
		
		lblLosingStreak = new JLabel("Worst Losing Streak: ");
		lblLosingStreak.setForeground(Color.RED);
		lblLosingStreak.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 11));
		
		lblCurrentStreak = new JLabel("Current Streak: ");
		lblCurrentStreak.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		
		txtGamesPlayed = lblGamePlayed.getText();
		txtGamesWin = lblGameWin.getText();
		txtWinPercentage = lblWinPercentage.getText();
		txtWinningStreak = lblWinningStreak.getText();
		txtLosingStreak = lblLosingStreak.getText();
		txtCurrentStreak = lblCurrentStreak.getText();
		
		/*
		 * reset record.
		 */
		btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					/*
					 * reset current executing application's variable.
					 */
					Frame.gamesPlayed = 0;
					Frame.gamesWin = 0;
					Frame.winPercentage = 0.0;
					Frame.winningStreak = 0;
					Frame.losingStreak = 0;
					Frame.currentStreak = 0;
					Frame.beginnerBestTime = 0;
					Frame.intermediateBestTime = 0;
					Frame.advancedBestTime = 0;
					
					lblGamePlayed.setText(txtGamesPlayed);
					lblGameWin.setText(txtGamesWin);
					lblWinPercentage.setText(txtWinPercentage);
					lblWinningStreak.setText(txtWinningStreak);
					lblLosingStreak.setText(txtLosingStreak);
					lblCurrentStreak.setText(txtCurrentStreak);
					txtSec.setText("0 sec");
					
					/*
					 * and reset statistics.txt file's data by default value.
					 */
					FileWriter fw = new FileWriter("statistics.txt");
					BufferedWriter bw = new BufferedWriter(fw);
				
					String modifiedStatistics = "0\n0\n0\n0\n0\n0\n0\n0\n0";
					
					bw.write(modifiedStatistics);
					bw.close();
					fw.close();
				}
				catch(Exception exc)
				{
					System.out.println("statistic reset error.");
				}
			}
		});
		btnReset.setFont(new Font("Arial Black", Font.BOLD, 11));
		
		btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Frame.timer.start();
				dlg.dispose();
			}
		});
		btnClose.setFont(new Font("Arial Black", Font.BOLD, 11));
		GroupLayout gl_pnStatistics = new GroupLayout(pnStatistics);
		gl_pnStatistics.setHorizontalGroup(
			gl_pnStatistics.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnStatistics.createSequentialGroup()
					.addGroup(gl_pnStatistics.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnStatistics.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_pnStatistics.createParallelGroup(Alignment.LEADING)
								.addComponent(lblGamePlayed)
								.addComponent(lblGameWin)
								.addComponent(lblWinPercentage)
								.addComponent(lblWinningStreak)
								.addComponent(lblCurrentStreak)
								.addComponent(lblLosingStreak)))
						.addGroup(Alignment.TRAILING, gl_pnStatistics.createSequentialGroup()
							.addContainerGap(65, Short.MAX_VALUE)
							.addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_pnStatistics.createSequentialGroup()
							.addGap(65)
							.addComponent(btnReset, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_pnStatistics.setVerticalGroup(
			gl_pnStatistics.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnStatistics.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblGamePlayed)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblGameWin)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblWinPercentage)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblWinningStreak)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblLosingStreak)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblCurrentStreak)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnReset)
					.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
					.addComponent(btnClose)
					.addContainerGap())
		);
		pnStatistics.setLayout(gl_pnStatistics);
		
	}
}
