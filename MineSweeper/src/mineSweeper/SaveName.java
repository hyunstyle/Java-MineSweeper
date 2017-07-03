package mineSweeper;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;

public class SaveName extends JPanel
{
	/*
	 * for arbitrary save file name.
	 * this JPanel is combined with Frame.saveFile.getComponent(3).
	 */
	private JLabel lblFileName;
	public static JTextField txtFileName;
	
	private JPanel panel;

	/**
	 * Create the panel.
	 */
	public SaveName() 
	{
		panel = new JPanel();
		panel.setBorder(null);
		lblFileName = new JLabel("File Name(F): ");
		lblFileName.setVerticalAlignment(SwingConstants.TOP);
		lblFileName.setFont(new Font("Arial", Font.BOLD, 12));
		
		txtFileName = new JTextField();
		txtFileName.setPreferredSize(new Dimension(25, 24));
		txtFileName.setColumns(10);
		setLayout(new BorderLayout(0, 0));
		add(panel, BorderLayout.NORTH);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(lblFileName)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtFileName, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFileName, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(txtFileName, GroupLayout.PREFERRED_SIZE, 21, Short.MAX_VALUE)))
		);
		panel.setLayout(gl_panel);

	}
	
	public static String getFileName()
	{
		/*
		 * get arbitrary file name.
		 */
		if(txtFileName.getText() != null)
			return txtFileName.getText();
		else
		{
			JOptionPane.showMessageDialog(null, "file name is null. please set file name.", "Null Filename", JOptionPane.WARNING_MESSAGE);
			return null;
		}
	}
	
}
