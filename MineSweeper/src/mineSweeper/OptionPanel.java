package mineSweeper;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Dimension;

import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class OptionPanel extends JPanel
{
	/*
	 * this panel is actual Menu->Option view.
	 */
	private JLabel lblTop;
	private JPanel pnDifficulty;
	private JPanel pnOption;
	public static JCheckBox chckbxSaveGameOnExit;
	public static JCheckBox chckbxContinueSavedGame;
	public static JCheckBox chckbxAllowQsMark;
	private JRadioButton rdbtnBeginner;
	private JRadioButton rdbtnIntermediate;
	private JRadioButton rdbtnAdvanced;
	private JRadioButton rdbtnCustom;
	private JLabel lblHeight;
	private JLabel lblWidth;
	private JLabel lblMines;
	public JTextField textFieldHeight;
	public JTextField textFieldWidth;
	public JTextField textFieldMines;
	public JButton btnCancel;
	public JButton btnOK;
	
	public int difficulty; // beginner -> 1 intermediate -> 2 advanced -> 3 custom -> 4

	/**
	 * Create the panel.
	 */
	public OptionPanel() 
	{
		
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setLayout(new BorderLayout(0, 0));
		setSize(500, 500);
		lblTop = new JLabel("Difficulty:");
		lblTop.setBorder(new EmptyBorder(5, 15, 5, 0));
		lblTop.setFont(new Font("Segoe Script", Font.BOLD, 16));
		add(lblTop, BorderLayout.NORTH);
		
		pnDifficulty = new JPanel();
		
		Border border = new LineBorder(new Color(65, 105, 225), 2);
		Border margin = new EmptyBorder(15, 15, 15, 15);
		pnDifficulty.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.BLUE, null, Color.DARK_GRAY, null));
		add(pnDifficulty, BorderLayout.CENTER);
		
		/*
		 * to write MULTI-line text in radio button, 
		 * used HTML format. 
		 */
		rdbtnBeginner = new JRadioButton("<html>Beginner<br>10 mines<br>9 x 9 tiles grid</html>");
		rdbtnBeginner.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				/*
				 * difficulty can be only one.
				 * so another difficulty button must be unchecked.
				 */
				if(rdbtnBeginner.isSelected())
				{
					difficulty = 1;
					rdbtnIntermediate.setSelected(false);
					rdbtnAdvanced.setSelected(false);
					rdbtnCustom.setSelected(false);
					System.out.println("difficulty : " + difficulty);
					
					
					textFieldWidth.setEditable(false);
					textFieldHeight.setEditable(false);
					textFieldMines.setEditable(false);
				}
				else
				{
					rdbtnBeginner.setSelected(true);
					
				}
			}
		});
		rdbtnBeginner.setSelected(true);
		rdbtnBeginner.setFont(new Font("Segoe Script", Font.BOLD, 13));
		
		rdbtnIntermediate = new JRadioButton("<html>Intermediate<br>40 mines<br>16 x 16 tiles grid</html>");
		rdbtnIntermediate.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(rdbtnIntermediate.isSelected())
				{
					/*
					 * same logic
					 */
					difficulty = 2;
					rdbtnBeginner.setSelected(false);
					rdbtnAdvanced.setSelected(false);
					rdbtnCustom.setSelected(false);
					System.out.println("difficulty : " + difficulty);
					textFieldWidth.setEditable(false);
					textFieldHeight.setEditable(false);
					textFieldMines.setEditable(false);
				}
				else
				{
					rdbtnIntermediate.setSelected(true);
				
				}
			}
		});
		rdbtnIntermediate.setFont(new Font("Segoe Script", Font.BOLD, 13));
		
		rdbtnAdvanced = new JRadioButton("<html>Advanced<br>99 mines<br>16 x 30 tiles grid</html>\r\n\r\n");
		rdbtnAdvanced.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(rdbtnAdvanced.isSelected())
				{
					/*
					 * same logic.
					 */
					difficulty = 3;
					rdbtnBeginner.setSelected(false);
					rdbtnIntermediate.setSelected(false);
					rdbtnCustom.setSelected(false);
					System.out.println("difficulty : " + difficulty);
					textFieldWidth.setEditable(false);
					textFieldHeight.setEditable(false);
					textFieldMines.setEditable(false);
				}
				else
				{
					rdbtnAdvanced.setSelected(true);
					
				}
			}
		});
		rdbtnAdvanced.setFont(new Font("Segoe Script", Font.BOLD, 13));
		
		rdbtnCustom = new JRadioButton("Custom:");
		rdbtnCustom.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(rdbtnCustom.isSelected())
				{
					/*
					 * same logic.
					 */
					difficulty = 4;
					rdbtnBeginner.setSelected(false);
					rdbtnIntermediate.setSelected(false);
					rdbtnAdvanced.setSelected(false);
					System.out.println("difficulty : " + difficulty);
					
					textFieldWidth.setEditable(true);
					textFieldHeight.setEditable(true);
					textFieldMines.setEditable(true);
				}
				else
				{
					rdbtnCustom.setSelected(true);
					
				}
			}
		});
		rdbtnCustom.setFont(new Font("Segoe Script", Font.BOLD, 13));
		
		textFieldHeight = new JTextField();
		textFieldHeight.setEditable(false);
		textFieldHeight.setBorder(new LineBorder(new Color(171, 173, 179), 2));
		textFieldHeight.setColumns(10);
		
		textFieldWidth = new JTextField();
		textFieldWidth.setEditable(false);
		textFieldWidth.setBorder(new LineBorder(new Color(171, 173, 179), 2));
		textFieldWidth.setColumns(10);
		
		textFieldMines = new JTextField();
		textFieldMines.setEditable(false);
		textFieldMines.setBorder(new LineBorder(new Color(171, 173, 179), 2));
		textFieldMines.setColumns(10);
		
		/*
		 * this switch statement is for LOADED FILE.
		 * basic difficulty is set by this.
		 */
		switch(Frame.difficulty)
		{
			case 1:
				rdbtnBeginner.setSelected(true);
				rdbtnIntermediate.setSelected(false);
				rdbtnAdvanced.setSelected(false);
				rdbtnCustom.setSelected(false);
				break;
			case 2:
				rdbtnBeginner.setSelected(false);
				rdbtnIntermediate.setSelected(true);
				rdbtnAdvanced.setSelected(false);
				rdbtnCustom.setSelected(false);
				break;
			case 3:
				rdbtnBeginner.setSelected(false);
				rdbtnIntermediate.setSelected(false);
				rdbtnAdvanced.setSelected(true);
				rdbtnCustom.setSelected(false);
				break;
			case 4:
				rdbtnBeginner.setSelected(false);
				rdbtnIntermediate.setSelected(false);
				rdbtnAdvanced.setSelected(false);
				rdbtnCustom.setSelected(true);
				textFieldWidth.setText("" + Frame.CUSTOM_WIDTH);
				textFieldHeight.setText("" + Frame.CUSTOM_HEIGHT);
				textFieldMines.setText("" + Frame.CUSTOM_MINES);
				textFieldWidth.setEditable(true);
				textFieldHeight.setEditable(true);
				textFieldMines.setEditable(true);
				break;
			default:
				break;
		}
		
		lblHeight = new JLabel("Height(9-24):");
		lblHeight.setFont(new Font("Segoe Script", Font.BOLD, 13));
		
		lblWidth = new JLabel("Width(9-30):");
		lblWidth.setFont(new Font("Segoe Script", Font.BOLD, 13));
		
		lblMines = new JLabel("Mines(10-668):");
		lblMines.setFont(new Font("Segoe Script", Font.BOLD, 13));
		
		
		GroupLayout gl_pnDifficulty = new GroupLayout(pnDifficulty);
		gl_pnDifficulty.setHorizontalGroup(
			gl_pnDifficulty.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnDifficulty.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnDifficulty.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnDifficulty.createSequentialGroup()
							.addGroup(gl_pnDifficulty.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbtnBeginner, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
								.addComponent(rdbtnIntermediate))
							.addGap(16)
							.addGroup(gl_pnDifficulty.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbtnCustom)
								.addGroup(gl_pnDifficulty.createSequentialGroup()
									.addGap(24)
									.addGroup(gl_pnDifficulty.createParallelGroup(Alignment.LEADING)
										.addComponent(lblHeight)
										.addGroup(gl_pnDifficulty.createSequentialGroup()
											.addGroup(gl_pnDifficulty.createParallelGroup(Alignment.LEADING)
												.addComponent(lblMines)
												.addComponent(lblWidth))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_pnDifficulty.createParallelGroup(Alignment.LEADING, false)
												.addComponent(textFieldMines, 0, 0, Short.MAX_VALUE)
												.addComponent(textFieldHeight, 0, 0, Short.MAX_VALUE)
												.addComponent(textFieldWidth, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))))
									.addGap(20))))
						.addComponent(rdbtnAdvanced, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_pnDifficulty.setVerticalGroup(
			gl_pnDifficulty.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnDifficulty.createSequentialGroup()
					.addGap(1)
					.addGroup(gl_pnDifficulty.createParallelGroup(Alignment.BASELINE)
						.addGroup(gl_pnDifficulty.createSequentialGroup()
							.addComponent(rdbtnCustom, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addGroup(gl_pnDifficulty.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblHeight)
								.addComponent(textFieldHeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(rdbtnBeginner, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnDifficulty.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnIntermediate)
						.addGroup(gl_pnDifficulty.createSequentialGroup()
							.addGroup(gl_pnDifficulty.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblWidth)
								.addComponent(textFieldWidth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pnDifficulty.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblMines)
								.addComponent(textFieldMines, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGap(7)
					.addComponent(rdbtnAdvanced)
					.addContainerGap(66, Short.MAX_VALUE))
		);
		pnDifficulty.setLayout(gl_pnDifficulty);
		
		pnOption = new JPanel();
		pnOption.setBorder(new EmptyBorder(0, 15, 0, 15));
		pnOption.setPreferredSize(new Dimension(10, 130));
		add(pnOption, BorderLayout.SOUTH);
		
		/*
		 * this 3 option is handled by Frame.optionSetting() method.
		 */
		chckbxSaveGameOnExit = new JCheckBox("Always save game on exit");
		chckbxSaveGameOnExit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String newOptionSetting = Frame.optionSetting(1);
				settingWrite(newOptionSetting);
			}
		});
		chckbxSaveGameOnExit.setFont(new Font("Segoe Script", Font.BOLD, 12));
		/*
		 * when loading, if that option is true, start with selected state.
		 */
		if(Frame.saveGameOnExit)
			chckbxSaveGameOnExit.setSelected(true);
		
		
		chckbxContinueSavedGame = new JCheckBox("Always continue saved game");
		chckbxContinueSavedGame.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				String newOptionSetting = Frame.optionSetting(2);
				settingWrite(newOptionSetting);
			}
		});
		chckbxContinueSavedGame.setFont(new Font("Segoe Script", Font.BOLD, 12));
		if(Frame.continueSavedGames)
			chckbxContinueSavedGame.setSelected(true);
		
		
		chckbxAllowQsMark = new JCheckBox("Allow question marks (on double right-click)");
		chckbxAllowQsMark.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String newOptionSetting = Frame.optionSetting(3);
				settingWrite(newOptionSetting);
			}
		});
		chckbxAllowQsMark.setFont(new Font("Segoe Script", Font.BOLD, 12));
		
		if(Frame.allowQsMarks)
			chckbxAllowQsMark.setSelected(true);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Arial", Font.BOLD, 11));
		
		btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Frame.difficulty = difficulty;
				if(difficulty == 4)
				{
					Frame.CUSTOM_WIDTH = Integer.valueOf(textFieldWidth.getText());
					Frame.CUSTOM_HEIGHT = Integer.valueOf(textFieldHeight.getText());
					Frame.CUSTOM_MINES = Integer.valueOf(textFieldMines.getText());
				}
			}
		});
		btnOK.setFont(new Font("Arial", Font.BOLD, 11));
		GroupLayout gl_pnOption = new GroupLayout(pnOption);
		gl_pnOption.setHorizontalGroup(
			gl_pnOption.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnOption.createSequentialGroup()
					.addGroup(gl_pnOption.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(chckbxAllowQsMark, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(chckbxContinueSavedGame, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(chckbxSaveGameOnExit, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(1)
					.addComponent(btnOK, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(btnCancel)
					.addContainerGap())
		);
		gl_pnOption.setVerticalGroup(
			gl_pnOption.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnOption.createSequentialGroup()
					.addComponent(chckbxSaveGameOnExit, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chckbxContinueSavedGame, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnOption.createParallelGroup(Alignment.BASELINE)
						.addComponent(chckbxAllowQsMark, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnOK)
						.addComponent(btnCancel))
					.addGap(1))
		);
		pnOption.setLayout(gl_pnOption);

	}
	
	/*
	 * update setting.txt file by parameter's String.
	 */
	private void settingWrite(String modifiedSetting)
	{
		FileWriter fw;
		BufferedWriter bw;
		try 
		{
			fw = new FileWriter("setting.txt");
			bw = new BufferedWriter(fw);
			bw.write(modifiedSetting);
			bw.close();
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println("setting.txt doesn't exist. now automatically made.");
			File recoverSET = new File("setting.txt");
			
			try
			{
				fw = new FileWriter(recoverSET);
				bw = new BufferedWriter(fw);
				bw.write(modifiedSetting);
				bw.close();
			}
			catch(IOException ioe)
			{
				
			}
		}
		catch(IOException ioe)
		{
			
		}
	}
}
