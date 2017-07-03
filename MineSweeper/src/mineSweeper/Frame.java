package mineSweeper;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;

import javax.accessibility.AccessibleContext;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.LookAndFeel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.SecondaryLoop;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Date;
import java.util.Random;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel;

/*
 * 2017 - 1 Java Application Programming
 * Mine Sweeper Project.
 * 12123215 Yu Sang Hyeon
 * 12161538 Kim Min Kyu
 */
public class Frame extends JFrame 
{
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnGame;
	private JMenu mnHelp;
	private JMenuItem mntmNewGame;
	private JMenuItem mntmSaveGame;
	private JMenuItem mntmLoadGame;
	private JMenuItem mntmStatistics;
	private JMenuItem mntmOptions;
	private JMenuItem mntmExit;
	private JMenuItem mntmAbout;
	private JMenuItem mntmShortcut;
	private JMenuItem mntmPause;
	private JPanel pnBar;
	private JLabel lblMine;
	private JLabel lblTimer;
	private JButton btnRestart;
	private JPanel pnMineZone;
	private Frame frame;
	
	
	/*
	 * about game information
	 * "enabledZoneCount" is that in current, how many zones can click.
	 * if this value goes to 0 without clicking mines, user WINs.
	 * to win, there is one more condition that "qsMarkCount" = 0.
	 * it only matters when ALLOW QSMARK option. 
	 * "isGameOver" is current game state.
	 * "zone" is made by size of (width + 2) * (height + 2).
	 * "remainedFlag" is (the number of mines - the number of flags in field(zone)). 
	 * "counter" is time counter.
	 * "difficulty" is current difficulty.(Beginner = 1, Intermediate = 2, Advanced = 3, Custom = 4)
	 * if "difficulty" is 4, user selects custom width, height, the number of mines. 
	 */
	private int enabledZoneCount;
	private int qsMarkCount;
	
	private static Zone[][] zone;
	private static int width;
	private static int height;
	private static int mineCount;
	private static int remainedFlag;
	private static int counter;
	
	public static boolean isGameOver;
	public static int difficulty;
	public static int CUSTOM_WIDTH;
	public static int CUSTOM_HEIGHT;
	public static int CUSTOM_MINES;
	
	/*
	 * saveFile handles all about saving that includes current game information.
	 */
	public static JFileChooser saveFile;
	
	/*
	 * "timer" handles time. every 1 second, "counter" = "counter" + 1.
	 * and timer is started when user clicks any "zone". 
	 * when user executes this application, "firstClick" is TRUE at first.
	 * if any click is detected, "firstClick" goes to FALSE and timer will start.  
	 */
	public static Timer timer;
	private boolean firstClick;
	
	/*
	 * "OptionDialog" handles all about option dialog(panel).
	 * there are more information about this dialog and panel in [OptionDialog.java, OptionPanel.java]. 
	 * "StaticticDialog" handles all about statistic dialog(panel).
	 * also there are more information in [StatisticDialog.java].
	 * "PauseDialog" handles pause state(extra).
	 * "ShortcutListDialog" handles all about shortcut dialog(dialog).
	 */
	private OptionDialog optionDialog;
	private StatisticDialog statisticDialog;
	private PauseDialog pauseDialog;
	private ShortcutListDialog shortcutDialog;
	
	/*
	 * option lists.
	 */
	public static boolean saveGameOnExit;
	public static boolean continueSavedGames;
	public static boolean allowQsMarks;
	
	/*
	 * about statistics.
	 * each value is used to update statistics dialog. 
	 */
	
	private String[] statisticsInformation;
	public static int beginnerBestTime;
	public static int intermediateBestTime;
	public static int advancedBestTime;
	public static int customBestTime;
	
	public static int gamesPlayed;
	public static int gamesWin;
	public static double winPercentage;
	public static int winningStreak;
	public static int losingStreak;
	public static int currentStreak;
	private boolean isWinningStreak;
	private JMenu mnTheme;
	private JMenuItem mntmBasicTheme;
	private JMenuItem mntmNimbusTheme;
	private JMenuItem mntmBlackStarTheme;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					
					Frame frame = new Frame();
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	
	
	/**
	 * Create the frame.
	 */
	public Frame() 
	{
		frame = this;
		
		/*
		 * when user executes this application, at first the difficulty is 1(Beginner),
		 * which has width*height = 9*9.
		 * every variable is initialized.
		 */
		isGameOver = false;
		firstClick = true;
		difficulty = 1;
		width = 9;
		height = 9;
		enabledZoneCount = width * height;
		qsMarkCount = 0;
		saveGameOnExit = false;
		continueSavedGames = false;
		allowQsMarks = false;
		beginnerBestTime = 0;
		intermediateBestTime = 0;
		advancedBestTime = 0;
		customBestTime = 0;
		gamesPlayed = 0;
		gamesWin = 0;
		winPercentage = 0.0;
		currentStreak = 0;
		isWinningStreak = false;
		
		optionSetting(0);
		/*
		 * make basic frame items.
		 */
		setTitle("MineSweeper");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		
		menuBar = new JMenuBar();
		menuBar.setFont(new Font("Arial", Font.PLAIN, 15));
		setJMenuBar(menuBar);
		
		/*
		 * shortcut is accomplished by 2 way.
		 * 1. using setMnemonic
		 * 2. using KeyStroke
		 */
		mnGame = new JMenu("Game");
		mnGame.setMnemonic(KeyEvent.VK_G);
		mnGame.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 14));
		menuBar.add(mnGame);
		/*
		 * set each menu icon. 
		 * to adjust image scale, used .getScaledInstance method. 
		 */
		ImageIcon menuImage = new ImageIcon(getClass().getResource("/image/menu.png"));
		Image mnimage = menuImage.getImage();
		mnimage = mnimage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		mnGame.setIcon(new ImageIcon(mnimage));
		
		/*
		 * new game menu item.
		 */
		mntmNewGame = new JMenuItem("New Game");
		KeyStroke newGameShortcut = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
		mntmNewGame.setAccelerator(newGameShortcut);
		mntmNewGame.setFont(new Font("Berlin Sans FB Demi", Font.ITALIC, 15));
		mntmNewGame.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				btnRestart.doClick();
			}
		});
		mnGame.add(mntmNewGame);
		mntmNewGame.setIcon(new ImageIcon(mnimage));
		
		/*
		 * pause menu item.
		 */
		mntmPause = new JMenuItem("Pause");
		KeyStroke pauseGameShortcut = KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK);
		mntmPause.setAccelerator(pauseGameShortcut);
		mntmPause.setFont(new Font("Berlin Sans FB Demi", Font.ITALIC, 15));
		mntmPause.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				timer.stop();
				pauseDialog = new PauseDialog(frame);
			}
		});
		mnGame.add(mntmPause);
		ImageIcon pauseImage = new ImageIcon(getClass().getResource("/image/pause.png"));
		Image psimage = pauseImage.getImage();
		psimage = psimage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		mntmPause.setIcon(new ImageIcon(psimage));
		
		/*
		 * Save Game.
		 */
		addWindowListener(new WindowAdapter() 
		{
			/*
			 * if Always Save Game On exit option is true,
			 * when user wants to exit application, stop the timer and automatically execute the save dialog.
			 */
			@Override
			public void windowClosing(WindowEvent e)
			{
				if(saveGameOnExit)
					mntmSaveGame.doClick();
			}
		});
		
		mntmSaveGame = new JMenuItem("Save Game");
		KeyStroke saveGameShortcut = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK);
		mntmSaveGame.setAccelerator(saveGameShortcut);
		mntmSaveGame.setFont(new Font("Berlin Sans FB Demi", Font.ITALIC, 15));
		mntmSaveGame.addActionListener(new ActionListener() 
		{
			
			public void actionPerformed(ActionEvent e) 
			{
				/*
				 * when user clicked Menu->Save, this method is called.
				 */
				if (!isGameOver) 
				{
					try 
					{
						timer.stop();
						saveFile = new JFileChooser();
						saveFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						System.out.println("component : " + saveFile.getComponentCount());
						/*
						 * Actually, JFileChooser consists of 4 main component.
						 * and the final component(in the South) handles about
						 * file path. so we made arbitrary component "com" and
						 * assigned to that component(saveFile.getComponent(3)).
						 * because, with only JFileChooser, we can't set user's
						 * own file name by input text field. so for arbitrary
						 * file name, we manipulate saveFile.getComponent(3).
						 */
						Component com = saveFile.getComponent(3);

						/*
						 * "saveFileName" is a JPanel. for manipulation,
						 * saveFile.getComponent(3) is added inside this JPanel.
						 * and manipulated JPanel(saveFileName) is added, and
						 * the old panel(saveFile.getComponent(3)) is replaced
						 * with new one. this new panel contains textfield, so
						 * finally user can set file name arbitrarily.
						 */
						JPanel saveFileName = new SaveName();
						saveFileName.add(com, BorderLayout.CENTER);
						saveFile.add(saveFileName, BorderLayout.SOUTH);

						/*
						 * JFileChooser is finally set. so open the save dialog.
						 */
						int isApproved = saveFile.showSaveDialog(frame);

						/*
						 * and if user enters the file name, path and clicks the
						 * "Save" button, this if statement is executed.
						 */
						if (isApproved == JFileChooser.APPROVE_OPTION) 
						{
							/*
							 * saveFile.getSelectFile() is the path where to
							 * save, SaveName.getFileName() is file name
							 * textfield which is made arbitrarily in the
							 * SaveName.java
							 */
							File file = new File(saveFile.getSelectedFile() + "/" + SaveName.getFileName() + ".txt");
							FileWriter fw;
							BufferedWriter bw;
					
							try 
							{
								fw = new FileWriter("lastSavedGamePath.txt");
								bw = new BufferedWriter(fw);
								String modifiedPath = saveFile.getSelectedFile().toString();
								modifiedPath = modifiedPath.replace('\\', '/');
								
								bw.write(modifiedPath + "/" + SaveName.getFileName() + ".txt");
								bw.close();
							}
							catch(FileNotFoundException fnfe)
							{
								System.out.println("lastSavedGamePath.txt doesn't exist. now automatically made.");
								File recoverLSGP = new File("lastSavedGamePath.txt");
								
								try
								{
									fw = new FileWriter(recoverLSGP);
									bw = new BufferedWriter(fw);
									bw.write(saveFile.getSelectedFile() + "/" + SaveName.getFileName() + ".txt");
									bw.close();
								}
								catch(IOException ioe)
								{
									
								}
							}
							catch (IOException e1) 
							{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							/*
							 * file is created. so next, we must write current
							 * game state. we decided save format as follow.
							 * 
							 * --------------- 1.difficulty 2.width 3.height
							 * 4.mineCount 5.remainedFlag 6.counter 7.zone value
							 * 8.already clicked zone TODO 9.flagged zone
							 * ------------------------
							 * 
							 * each format is discriminated with new line
							 * character, "\n".
							 */
							fw = new FileWriter(file);
							bw = new BufferedWriter(fw);

							/*
							 * saveState() method returns String format of
							 * current game state with decided format. after
							 * write, flush buffer and close. (save finished)
							 */
							bw.write(saveState());
							bw.flush();
							bw.close();
							
							timer.start();
						} 
						else 
						{
							System.out.println("cancel");
							timer.start();
						}

					} 
					catch (Exception e1) 
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Gameover state can't save.", "WARNING!", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		mnGame.add(mntmSaveGame);
		ImageIcon saveImage = new ImageIcon(getClass().getResource("/image/save.png"));
		Image svimage = saveImage.getImage();
		svimage = svimage.getScaledInstance(17, 17, Image.SCALE_SMOOTH);
		mntmSaveGame.setIcon(new ImageIcon(svimage));

		
		/*
		 * Load Game.
		 */
		mntmLoadGame = new JMenuItem("Load Game");
		KeyStroke loadGameShortcut = KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_MASK);
		mntmLoadGame.setAccelerator(loadGameShortcut);
		mntmLoadGame.setFont(new Font("Berlin Sans FB Demi", Font.ITALIC, 15));
		mntmLoadGame.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				/*
				 * this method is called when user clicked Menu->Load.
				 * 
				 * we need to know which file to load.
				 * so user selects the file which is already existing from JFileChooser.
				 */
				
				timer.stop();
				
				JFileChooser fChooser = new JFileChooser();
				
				int openedFile = fChooser.showOpenDialog(fChooser);
				
				if(openedFile == JFileChooser.APPROVE_OPTION)
				{
					/*
					 * if selection is done and user clicks "OPEN", read the saved game state. 
					 */
					System.out.println("open!!");
					
					loadFile(fChooser.getSelectedFile());
					timer.start();
				}
				else
				{
					timer.start();
				}
				
			}
		});
		mnGame.add(mntmLoadGame);
		ImageIcon loadImage = new ImageIcon(getClass().getResource("/image/load.png"));
		Image ldimage = loadImage.getImage();
		ldimage = ldimage.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
		mntmLoadGame.setIcon(new ImageIcon(ldimage));
		
		/*
		 * statistics menu item.
		 */
		mntmStatistics = new JMenuItem("Statistics");
		KeyStroke statisticsShortcut = KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0);
		mntmStatistics.setAccelerator(statisticsShortcut);
		mntmStatistics.setFont(new Font("Berlin Sans FB Demi", Font.ITALIC, 15));
		mntmStatistics.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				/*
				 * open the statistics dialog when user clicks the menu or enters shortcut.
				 */
				timer.stop();
				statisticDialog = new StatisticDialog();
			}
			
		});
		mnGame.add(mntmStatistics);
		ImageIcon statisticsImage = new ImageIcon(getClass().getResource("/image/statistics.png"));
		Image stimage = statisticsImage.getImage();
		stimage = stimage.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
		mntmStatistics.setIcon(new ImageIcon(stimage));
		
		/*
		 * option menu item.
		 */
		mntmOptions = new JMenuItem("Options");
		KeyStroke optionShortcut = KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0);
		mntmOptions.setAccelerator(optionShortcut);
		mntmOptions.setFont(new Font("Berlin Sans FB Demi", Font.ITALIC, 15));
		
		mntmOptions.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				/*
				 * open option menu. same logic as statistics menu. 
				 */
				timer.stop();
				if(difficulty == 4)
				{
					CUSTOM_WIDTH = width;
					CUSTOM_HEIGHT = height;
					CUSTOM_MINES = mineCount;
					System.out.println(CUSTOM_WIDTH + " " + CUSTOM_HEIGHT + " " + CUSTOM_MINES);
				}
				optionDialog = new OptionDialog(frame, timer);
				
			}
		});
		mnGame.add(mntmOptions);
		ImageIcon optionImage = new ImageIcon(getClass().getResource("/image/option.png"));
		Image opimage = optionImage.getImage();
		opimage = opimage.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
		mntmOptions.setIcon(new ImageIcon(opimage));
		
		/*
		 * exit menu item.
		 */
		mntmExit = new JMenuItem("Exit");
		KeyStroke exitShortcut = KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK);
		mntmExit.setAccelerator(exitShortcut);
		mntmExit.setFont(new Font("Berlin Sans FB Demi", Font.ITALIC, 15));
		mntmExit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				/*
				 * there is one condition.
				 * if SAVE GAME ON EXIT option is checked state,
				 * application must toggle save game menu.
				 * this is possible by using doClick method.
				 */
				if(saveGameOnExit)
				{
					mntmSaveGame.doClick();
				}
				System.exit(0);
			}
		});
		mnGame.add(mntmExit);
		ImageIcon exitImage = new ImageIcon(getClass().getResource("/image/exit.png"));
		Image eximage = exitImage.getImage();
		eximage = eximage.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
		mntmExit.setIcon(new ImageIcon(eximage));
		
		
		
		mnTheme = new JMenu("Theme");
		mnTheme.setMnemonic(KeyEvent.VK_T);
		ImageIcon themeImage = new ImageIcon(getClass().getResource("/image/theme.png"));
		Image thmimage = themeImage.getImage();
		thmimage = thmimage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		mnTheme.setIcon(new ImageIcon(thmimage));
		mnTheme.setFont(new Font("Berlin Sans FB Demi", Font.PLAIN, 14));
		menuBar.add(mnTheme);
		
		/*
		 * theme menu.
		 */
		mntmBasicTheme = new JMenuItem("Basic Theme");
		mntmBasicTheme.setHorizontalAlignment(SwingConstants.CENTER);
		mntmBasicTheme.setFont(new Font("Berlin Sans FB Demi", Font.ITALIC, 15));
		mntmBasicTheme.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					/*
					 * basic look and feel.
					 */
					UIManager.setLookAndFeel(new MetalLookAndFeel());
					SwingUtilities.updateComponentTreeUI(frame);
				} 
				catch (UnsupportedLookAndFeelException e1) 
				{
					System.out.println("UI convert error!");
					e1.printStackTrace();
				}
			}
		});
		mnTheme.add(mntmBasicTheme);
		
		mntmNimbusTheme = new JMenuItem("Nimbus Theme");
		mntmNimbusTheme.setHorizontalAlignment(SwingConstants.CENTER);
		mntmNimbusTheme.setFont(new Font("Berlin Sans FB Demi", Font.ITALIC, 15));
		mntmNimbusTheme.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					/*
					 * basic look and feel 2
					 */
					UIManager.setLookAndFeel(new NimbusLookAndFeel());
					SwingUtilities.updateComponentTreeUI(frame);
				} 
				catch (UnsupportedLookAndFeelException e1) 
				{
					System.out.println("UI convert error!");
					e1.printStackTrace();
				}
				
			}
		});
		mnTheme.add(mntmNimbusTheme);
		
		mntmBlackStarTheme = new JMenuItem("BlackStar Theme");
		mntmBlackStarTheme.setHorizontalAlignment(SwingConstants.CENTER);
		mntmBlackStarTheme.setFont(new Font("Berlin Sans FB Demi", Font.ITALIC, 15));
		mntmBlackStarTheme.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					UIManager.setLookAndFeel(new SyntheticaBlackStarLookAndFeel());
					SwingUtilities.updateComponentTreeUI(frame);
				} 
				catch (Exception e1) 
				{
					System.out.println("UI convert error!");
				}
			}
		});
		mnTheme.add(mntmBlackStarTheme);
		
		/*
		 * help menu bar.
		 */
		mnHelp = new JMenu("Help");
		mnHelp.setMnemonic(KeyEvent.VK_H);
		mnHelp.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 14));
		menuBar.add(mnHelp);

		ImageIcon helpImage = new ImageIcon(getClass().getResource("/image/question.png"));
		Image mnHelpImage = helpImage.getImage();
		mnHelpImage = mnHelpImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		mnHelp.setIcon(new ImageIcon(mnHelpImage));
		
		/*
		 * about mine sweeper menu item.
		 */
		mntmAbout = new JMenuItem("About MineSweeper");
		mntmAbout.setFont(new Font("Berlin Sans FB Demi", Font.ITALIC, 15));
		mntmAbout.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					/*
					 * when user clicks About MineSweeper menu,
					 * link will be opened.
					 * that link is explaining how to play mine sweeper in detail. 
					 * all possible exception is handled.
					 */
					URI uri = new URI("http://www.wikihow.com/Play-Minesweeper");
					if (Desktop.isDesktopSupported()) 
					{
						try 
						{
					        Desktop.getDesktop().browse(uri);
					    } 
						catch (IOException exc) 
						{ 
							System.out.println("Link is corrupted.");
						}
					}
					else
					{
						System.out.println("Desktop is corrupted.");
					}
				} 
				catch (URISyntaxException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		mnHelp.add(mntmAbout);
		
		/*
		 * shortcut menu item.
		 */
		mntmShortcut = new JMenuItem("Shortcuts List");
		mntmShortcut.setFont(new Font("Berlin Sans FB Demi", Font.ITALIC, 15));
		KeyStroke toggleShortcut = KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_MASK);
		mntmShortcut.setAccelerator(toggleShortcut);
		mntmShortcut.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				/*
				 * open shortcut dialog.
				 */
				timer.stop();
				shortcutDialog = new ShortcutListDialog(frame);
			}
		});
		mnHelp.add(mntmShortcut);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		pnBar = new JPanel();
		pnBar.setPreferredSize(new Dimension(10, 50));
		contentPane.add(pnBar, BorderLayout.NORTH);
		
		lblMine = new JLabel(" 000");
		lblMine.setFont(new Font("Segoe Script", Font.BOLD, 18));
		
		lblTimer = new JLabel("000 ", SwingConstants.RIGHT);
		lblTimer.setFont(new Font("Segoe Script", Font.BOLD, 18));
		
		/*
		 * Restart Button (Smile Button)
		 */
		btnRestart = new JButton();
		/*
		 * below 3 line makes JButton transparent.
		 * after that, icon looks better.
		 */
		btnRestart.setFocusPainted(false);
		btnRestart.setBorderPainted(false);
		btnRestart.setContentAreaFilled(false);
		
		/*
		 * icon initialization.
		 */
		ImageIcon smileImage = new ImageIcon(getClass().getResource("/image/smile.png"));
		ImageIcon rolloverImage = new ImageIcon(getClass().getResource("/image/rollover.png"));
		ImageIcon smileClickImage = new ImageIcon(getClass().getResource("/image/smileClick.png"));
		
		/*
		 * smile.png is the basic icon.
		 */
		Image image = smileImage.getImage();
		image = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		btnRestart.setIcon(new ImageIcon(image));
		/*
		 * if rolls over, image changes to rollover.png. 
		 */
		image = rolloverImage.getImage();
		image = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		btnRestart.setRolloverIcon(new ImageIcon(image));
		/*
		 * if clicks, changes to smileClick.png. 
		 */
		image = smileClickImage.getImage();
		image = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		btnRestart.setPressedIcon(new ImageIcon(image));
		
		/*
		 * Restart button action.
		 */
		btnRestart.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				/*
				 * this method is called when user clicks smile button(restart button).
				 * we should set the icon smile.png because new game will start.
				 * (because if game is over, icon changes to gameover.png.)
				 */
				ImageIcon smileImage = new ImageIcon(getClass().getResource("/image/smile.png"));
				Image image = smileImage.getImage();
				image = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
				btnRestart.setIcon(new ImageIcon(image));
				/*
				 * new game will start. so assign "isGameOver" = false (initial value)
				 * and for timer, add "firstClick" handling. 
				 */
				isGameOver = false;
				if(firstClick)
				{
					firstClick = !firstClick;
					timer.start();
				}
				
				if(!timer.isRunning())
					timer.start();
				
				/*
				 * remove all component.
				 */
				pnMineZone.removeAll();
				pnMineZone.validate();
				/*
				 * after .removeAll() and .validate() call,
				 * if there is no frame size change, components don't see accurately.
				 * so probably below 2 lines are useless code, but unavoidably added. 
				 */
				frame.setBounds(100, 100, width*49, height*49); // if same frame size, can't see the change.
				frame.setBounds(100, 100, width*55, height*55);
				
				/*
				 * re-initialize frame.
				 */
				init();
				
				/*
				 * timer re-initialization and add mouseListener to all zones. (Restart finished)
				 */
				counter = 0;
				addZoneListener(zone);
			}
		});
		
		pnBar.setLayout(new GridLayout(1, 3, 0, 0));
		pnBar.add(lblMine);
		pnBar.add(btnRestart);
		pnBar.add(lblTimer);
		
		/*
		 * Timer.
		 */
		counter = 0;
		timer = new Timer(1000, new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				/*
				 * for each second(1000ms), this method is called.
				 * according to counter value, timer label text is changed. 
				 */
				if(counter < 10)
					lblTimer.setText("00" + counter + " ");
				else if(counter >= 10 || counter<100)
					lblTimer.setText("0" + counter + " ");
				else
					lblTimer.setText("" + counter + " ");
				
				/*
				 * after setting label, counter value + 1.
				 */
				counter++;
			}
		});
		
		pnMineZone = new JPanel();
		contentPane.add(pnMineZone, BorderLayout.CENTER);
		
		/*
		 * when started, if CONTINUE SAVED GAMES option is already checked,
		 * we must load the last saved game.
		 * the codes in below are that process. 
		 */
		if(continueSavedGames)
		{
			try
			{
				/*
				 * open the last saved game
				 */
				FileReader frd = new FileReader("lastSavedGamePath.txt");
				BufferedReader brd = new BufferedReader(frd);
				
				String lsgPath;
				
				try
				{
					/*
					 * "lsgPath" is the last saved game's PATH.
					 * so convert String path to real Path,
					 * and call loadFile method by that path's file. 
					 */
					lsgPath = brd.readLine();
					brd.close();
					Path path = Paths.get(lsgPath);
					loadFile(path.toFile());
				}
				catch(Exception except)
				{
					/*
					 * if the last saved game file is corrupted or have a problem, this line is executed.
					 * re-make lastSavedGamePath.txt file and initialize mine sweeper by basic option.
					 */
					System.out.println("lastSavedGamePath.txt has not valid format. please save again.");
					File newLSGP = new File("lastSavedGamePath.txt");
					init();
					addZoneListener(zone);
				}
				
			}
			catch(FileNotFoundException fnfe)
			{
				System.out.println("there is no save game. basic game starts.");
				File newLSGP = new File("lastSavedGamePath.txt");
				init();
				addZoneListener(zone);
			}
		}
		else
		{
			/*
			 * the first initialization.
			 */
			init();
			addZoneListener(zone);
		}
		
		/*
		 * load statistics information.
		 * loadStatistics() method contains the information of total information in one String.
		 */
		String loaded = loadStatistics();
		if(loaded != null)
		{
			/*
			 * to use each information,
			 * .split method is used. 
			 */
			statisticsInformation = loaded.split(",");
			try
			{
				/*
				 * assign statistics information.
				 * the array's order is pre-defined.
				 */
				gamesPlayed = Integer.parseInt(statisticsInformation[0]);
				gamesWin = Integer.parseInt(statisticsInformation[1]);
				winPercentage = Double.parseDouble(statisticsInformation[2]);
				winningStreak = Integer.parseInt(statisticsInformation[3]);
				losingStreak = Integer.parseInt(statisticsInformation[4]);
				currentStreak = Integer.parseInt(statisticsInformation[5]);
				beginnerBestTime = Integer.parseInt(statisticsInformation[6]);
				intermediateBestTime = Integer.parseInt(statisticsInformation[7]);
				advancedBestTime = Integer.parseInt(statisticsInformation[8]);
				customBestTime = Integer.parseInt(statisticsInformation[9]);
			}
			catch(NumberFormatException e)
			{
				System.out.println("statistics.txt is corrupted!!");
			}
		}
	}
	
	/*
	 * initialization method.
	 */
	public void init() 
	{
		/*
		 * set width, height, mineCount, frame size according to difficulty.
		 */
		switch(difficulty)
		{
			case 1:
				width = 9;
				height = 9;
				mineCount = 10;
				this.setBounds(100, 100, 450, 450);
				break;
			case 2:
				width = 16;
				height = 16;
				mineCount = 40;
				this.setBounds(100, 100, 800, 900);
				break;
			case 3:
				width = 16;
				height = 30;
				mineCount = 99;
				this.setBounds(100, 100, width*90, height*30);
				break;
			case 4:
				width = CUSTOM_WIDTH;
				height = CUSTOM_HEIGHT;
				mineCount = CUSTOM_MINES;
				
				if(width <= 16 && height <= 16)
					this.setBounds(100, 100, width*50, height*50);
				else
					this.setBounds(100, 100, width*80, height*60);
				
				break;
			default:
				break;
		}
		
		/*
		 * set layout size with width * height, and make zone object.
		 * at first, remainedFlag is equal to the number of mines.
		 */
		pnMineZone.setLayout(new GridLayout(width, height, 0, 0));
		zone = new Zone[width + 2][height + 2];
		remainedFlag = mineCount;
		enabledZoneCount = width * height;
		lblMine.setText(" 0" + remainedFlag);
		
		/*
		 * each zone initialization.
		 */
		for(int i = 0; i<width + 2; i++)
		{
			for(int j = 0; j<height + 2; j++)
			{
				zone[i][j] = new Zone(i,j);
				
				/*
				 * edge zone value set to 10.
				 */
				if(i == 0 || j == 0 || i == width + 1 || j == height + 1) // 가장자리
				{
					zone[i][j].setValue(10);
				}
				else // not edge. so add to the panel.
				{
					pnMineZone.add(zone[i][j]);
				}
				
			}
		}
		
		/*
		 * random mine setting.
		 */
		Random rand = new Random();
		int mineRow;
		int mineCol;
		int finishedMine = 0;
		/*
		 * until mineCount, repeat while loop.
		 */
		while(finishedMine < mineCount)
		{
			mineRow = rand.nextInt(width) + 1;
			mineCol = rand.nextInt(height) + 1;
			if(zone[mineRow][mineCol].getValue() == -1)
				continue;
			else
			{
				zone[mineRow][mineCol].setValue(-1); // mine
				finishedMine++;
			}
			
		}
		
		/*
		 * confirm the map size, number of mines.
		 */
		System.out.println("map size : " + (width) + " X " + (height) + ",  the number of mines : " + mineCount);
		
		/*
		 * setting zone[i][j] value, which is not mine.
		 */
		
		int countAdjacentBombs = 0; // counting variable
		for(int i = 1; i<width + 1; i++)
		{
			for(int j = 1; j<height + 1; j++)
			{
				if(zone[i][j].getValue() == -1)
				{
					//if mine, do nothing. continue,
					continue;
				}
				
				/*
				 * this simple 8 side ++ is possible because 
				 * we created zone to zone[width + 2][height + 2] ( not zone[width][height] )
				 * ArrayIndexOutOfBoundsException doesn't occur!!
				 */
				if(zone[i+1][j].getValue() == -1)
					countAdjacentBombs++;
				if(zone[i+1][j+1].getValue() == -1)
					countAdjacentBombs++;
				if(zone[i][j+1].getValue() == -1)
					countAdjacentBombs++;
				if(zone[i-1][j+1].getValue() == -1)
					countAdjacentBombs++;
				if(zone[i-1][j].getValue() == -1)
					countAdjacentBombs++;
				if(zone[i-1][j-1].getValue() == -1)
					countAdjacentBombs++;
				if(zone[i][j-1].getValue() == -1)
					countAdjacentBombs++;
				if(zone[i+1][j-1].getValue() == -1)
					countAdjacentBombs++;
				
				/*
				 * set zone[i][j] value.
				 */
				zone[i][j].setValue(countAdjacentBombs);
				
				countAdjacentBombs = 0;
			}
		}
		
	    // init() finished.
	}
	
	/*
	 * if user clicks empty zone, ( zone[i][j].getValue() == 0 )
	 * we need to reveal adjacent zone according to the adjacent zone's value.
	 */
	private boolean revealAdjacentArea(Zone curZone)
	{
		if(curZone.getValue() == 0)
		{
			/*
			 * change clicked zone's image.
			 * just adjust background and setEnabled(false). 
			 */
			curZone.setBackground(new Color(92, 209, 229));
			curZone.setEnabled(false);
			enabledZoneCount--;
			
			/*
			 * this 2 for loop executes 9 times.
			 * (i, j) -> (-1, -1), (-1, 0), (-1, 1)
			 * 			 ( 0, -1), ( 0, 0), ( 0, 1)
			 * 			 ( 1, -1), ( 1, 0), ( 1, 1)
			 * 
			 * it means each adjacent zones. center zone (0, 0) is current zone.
			 */
			for(int i = -1; i<=1; i++)
			{
				for(int j = -1; j<=1; j++)
				{
					/*
					 * 0, 0 is current zone. so do nothing.
					 */
					if(i == 0 && j == 0)
                        continue;
					/*
					 * (spareX, spareY) is each adjacent location.
					 * if spareX or spareY's value exceed width or height, do nothing and continue.
					 */
                    int spareX = curZone.getRowPosition() + i;
                    if(spareX < 0 || spareX > width)
                        continue;
                    int spareY = curZone.getColPosition() + j;
                    if(spareY < 0 || spareY > height)
                        continue;
                    /*
                     * if spareX and spareY doesn't exceed width and height
                     * and is not flagged and is not QSmark, recursively execute this method by new position.
                     */
                    if(zone[spareX][spareY].isEnabled() && !zone[spareX][spareY].isFlag() && !zone[spareX][spareY].isQsMark())
                    	revealAdjacentArea(zone[spareX][spareY]);
				}
			}
		}
		
		/*
		 * -1 means this zone is mine. so do not reveal this zone.
		 */
		if(curZone.getValue() == - 1)
		{
			return false;
		}
		
		/*
		 * if zone value > 0, reveal zone and stop recurrence.
		 * value = 10 means the "EDGE"
		 */
		if(curZone.getValue() > 0 && curZone.getValue() != 10)
		{
			curZone.setText(String.valueOf(curZone.getValue()));
			curZone.setBackground(Color.black);
			curZone.setEnabled(false);
			enabledZoneCount--;
			return false;
		}
		
		return true;
		
		// reveal method finished.
	}
	
	/*
	 * Save state method.
	 */
	private String saveState()
	{
		String valueString = "";
		String isClickedString = "";
		String isFlaggedString = "";
		

		/*
		 * add zone value string.
		 * notice the MINE ZONE.
		 * mine zone's value is actually -1.
		 * but "-1" is 2 bit string, so it is uncomfortable for loading.
		 * so change "-1" to "-" and store.
		 */
		for (int i = 1; i < width + 1; i++)
		{
			for (int j = 1; j < height + 1; j++) 
			{
				if (zone[i][j].getValue() == -1) 
				{
					valueString += "-";
				} 
				else 
				{
					valueString += ("" + zone[i][j].getValue());
				}
			}
			valueString += "\n";
		}

		/*
		 * clicked zone information string.
		 * if not clicked yet, "1"
		 * clicked, "0" is stored.
		 */
		for (int i = 1; i < width + 1; i++)
		{
			for (int j = 1; j < height + 1; j++) 
			{
				if (zone[i][j].isEnabled()) 
				{
					isClickedString += "1";
				} 
				else 
				{
					isClickedString += "0";
				}
			}
			isClickedString += "\n";
		}
		/*
		 * flagged zone information string.
		 * if flagged, "1"
		 * not flagged, "0"
		 */
		for (int i = 1; i < width + 1; i++)
		{
			for (int j = 1; j < height + 1; j++) 
			{
				if(zone[i][j].isFlag())
				{
					isFlaggedString += "1";
				}
				else
				{
					isFlaggedString += "0";
				}
			}
			isFlaggedString += "\n";
		}

		/*
		 * this is the format of current state information.
		 * can discriminate each information by "\n".
		 */
		return (difficulty + "\n" + width + "\n" + height + "\n" + mineCount + "\n" + remainedFlag + "\n"
				+ counter + "\n" + valueString + "\n" + isClickedString + "\n" + isFlaggedString);
	}
	
	/*
	 * mouse listener
	 */
	private void addZoneListener(Zone[][] zone)
	{
		for(int i = 1; i<width + 1; i++)
		{
			for(int j = 1; j<height + 1; j++)
			{
				zone[i][j].addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) 
					{
						
						/*
						 * if clicked zone is enabled state and not GAMEOVER state,
						 * execute if statement.
						 */
						if(((Zone)e.getSource()).isEnabled() && !isGameOver)
						{
							/*
							 * handle timer.
							 */
							if(firstClick)
							{
								firstClick = !firstClick;
								timer.start();
							}
							
							/*
							 * left click handling.
							 * if left clicked zone is flagged zone, do nothing.
							 */
							if(SwingUtilities.isLeftMouseButton(e) && !((Zone)e.getSource()).isFlag()
									&& !((Zone)e.getSource()).isQsMark()) 
							{
								System.out.println("enabled zone counter : " + (enabledZoneCount - 1));
								/*
								 * "currentZone" is actual clicked zone.
								 * "zoneValue" is actual clicked zone's value.
								 */
								Zone currentZone = (Zone)e.getSource();
								int zoneValue = currentZone.getValue();
								System.out.println(zoneValue);
								
								/*
								 * "zoneValue" = -1 means, this zone is mine.
								 * so show GameOver message dialog, and stop timer.
								 * reset "firstClick" and change "isGameOver" to true.
								 */
								if(zoneValue == -1)
								{
									JOptionPane.showMessageDialog(null, "You Lost.", "LOST", JOptionPane.DEFAULT_OPTION);
									
									timer.stop();
									firstClick = true;
									isGameOver = true;
									gamesPlayed++;
									winPercentage = (double)gamesWin / gamesPlayed * 100;
									
									/*
									 * update the statistics information.
									 */
									if(!isWinningStreak)
										currentStreak++;
									else
									{
										isWinningStreak = false;
										currentStreak = 1;
									}
									
									if(currentStreak >= losingStreak)
									{
										losingStreak = currentStreak;
									}
									
									try
									{
										/*
										 * and update statistics file using modified information.
										 */
										FileWriter fw = new FileWriter("statistics.txt");
										BufferedWriter bw = new BufferedWriter(fw);
										
										String modifiedStatistics = "";
										modifiedStatistics += gamesPlayed + "\n" + gamesWin + "\n" + winPercentage
												+ "\n" + winningStreak + "\n" + losingStreak + "\n" + currentStreak
												+ "\n" + beginnerBestTime + "\n" + intermediateBestTime
												+ "\n" + advancedBestTime + "\n" + customBestTime;
										
										bw.write(modifiedStatistics);
										bw.close();
										fw.close();
									}
									catch(Exception exc)
									{
										exc.getStackTrace();
									}
									
									/*
									 * expose all mines.
									 * mine image is mine.png. 
									 */
									ImageIcon mineImage = new ImageIcon(getClass().getResource("/image/mine.png"));
									ImageIcon foundImage = new ImageIcon(getClass().getResource("/image/foundmine.jpg"));
									Image mImage = mineImage.getImage();
									Image fImage = foundImage.getImage();
									mImage = mImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
									fImage = fImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
									for(int i = 1; i<width + 1; i++)
									{
										for(int j = 1; j<height + 1; j++)
										{
											if(zone[i][j].getValue() == -1)
											{
												if(zone[i][j].isFlag())
												{
													zone[i][j].setIcon(new ImageIcon(fImage));
												}
												else
												{
													zone[i][j].setIcon(new ImageIcon(mImage));
												}
											}
											else
											{
												zone[i][j].setEnabled(false);
											}
										}
									}
									
									/*
									 * smile icon changes to frown icon(gameover.png).
									 */
									ImageIcon gameoverImage = new ImageIcon(getClass().getResource("/image/gameover.png"));
									Image image = gameoverImage.getImage();
									image = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
									btnRestart.setIcon(new ImageIcon(image));
									// GAMEOVER finished.
								}
								else if(zoneValue == 0)
								{
									/*
									 * "zoneValue" = 0 means it is empty zone.
									 * change the background image and setEnabled(false).
									 * and for revealing adjacent area, call revealAdjacentArea() method.	
									 */
									currentZone.setBackground(new Color(92, 209, 229));
									//currentZone.setOpaque(false);
									currentZone.setEnabled(false);
									revealAdjacentArea(currentZone);
									
									/*
									 * win condition.
									 */
									if(enabledZoneCount == 0 && qsMarkCount == 0)
									{
										/*
										 * if this record is the best, update the best time.
										 */
										if(difficulty == 1 && (beginnerBestTime == 0 || counter < beginnerBestTime))
										{
											beginnerBestTime = counter;
										}
										else if(difficulty == 2 && (intermediateBestTime == 0 || counter < intermediateBestTime))
										{
											intermediateBestTime = counter;
										}
										else if(difficulty == 3 && (advancedBestTime == 0 || counter < advancedBestTime))
										{
											advancedBestTime = counter;
										}
										else if(difficulty == 4 && (customBestTime == 0 || counter < customBestTime))
										{
											customBestTime = counter;
										}
										
										timer.stop();
										
										/*
										 * update statistics information.
										 */
										gamesPlayed++;
										gamesWin++;
										winPercentage = (double)gamesWin / gamesPlayed * 100;
										
										if(isWinningStreak)
										{
											currentStreak++;
										}
										else
										{
											isWinningStreak = true;
											currentStreak = 1;
										}
										
										
										if(currentStreak >= winningStreak)
										{
											winningStreak = currentStreak;
										}
										
										try
										{
											/*
											 * and update statistics file using modified information.
											 */
											FileWriter fw = new FileWriter("statistics.txt");
											BufferedWriter bw = new BufferedWriter(fw);
											
											String modifiedStatistics = "";
											modifiedStatistics += gamesPlayed + "\n" + gamesWin + "\n" + winPercentage
													+ "\n" + winningStreak + "\n" + losingStreak + "\n" + currentStreak
													+ "\n" + beginnerBestTime + "\n" + intermediateBestTime
													+ "\n" + advancedBestTime;
											
											bw.write(modifiedStatistics);
											bw.close();
											fw.close();
										}
										catch(Exception exc)
										{
											exc.getStackTrace();
										}
										JOptionPane.showMessageDialog(null, "You Win!!", "WIN", JOptionPane.PLAIN_MESSAGE);
									}
								}
								else // surround mine exists, but this zone is not mine.
								{
									/*
									 * reveal this zone's value and adjust background color.
									 * + setEnabled(false)
									 */
									currentZone.setText(String.valueOf(zoneValue));
									currentZone.setForeground(Color.BLUE);
									currentZone.setBackground(Color.BLACK);
								
									currentZone.setEnabled(false);
									enabledZoneCount--;
									
									/*
									 * win condition 2
									 */
									if(enabledZoneCount == 0 && qsMarkCount == 0)
									{
										/*
										 * same logic
										 */
										if(difficulty == 1 && (beginnerBestTime == 0 || counter < beginnerBestTime))
										{
											beginnerBestTime = counter;
										}
										else if(difficulty == 2 && (intermediateBestTime == 0 || counter < intermediateBestTime))
										{
											intermediateBestTime = counter;
										}
										else if(difficulty == 3 && (advancedBestTime == 0 || counter < advancedBestTime))
										{
											advancedBestTime = counter;
										}
										else if(difficulty == 4 && (customBestTime == 0 || counter < customBestTime))
										{
											customBestTime = counter;
										}
										
										timer.stop();
										
										gamesPlayed++;
										gamesWin++;
										winPercentage = (double)gamesWin / gamesPlayed * 100;
										
										if(isWinningStreak)
										{
											currentStreak++;
										}
										else
										{
											isWinningStreak = true;
											currentStreak = 1;
										}
										
										
										if(currentStreak >= winningStreak)
										{
											winningStreak = currentStreak;
										}
										
										try
										{
										
											FileWriter fw = new FileWriter("statistics.txt");
											BufferedWriter bw = new BufferedWriter(fw);
											
											String modifiedStatistics = "";
											modifiedStatistics += gamesPlayed + "\n" + gamesWin + "\n" + winPercentage
													+ "\n" + winningStreak + "\n" + losingStreak + "\n" + currentStreak
													+ "\n" + beginnerBestTime + "\n" + intermediateBestTime
													+ "\n" + advancedBestTime + "\n" + customBestTime;
											
											bw.write(modifiedStatistics);
											bw.close();
											fw.close();
										}
										catch(Exception exc)
										{
											exc.getStackTrace();
										}
										
										JOptionPane.showMessageDialog(null, "You Win!!", "WIN", JOptionPane.PLAIN_MESSAGE);
									}
								}
								// left click finished.
							}
							else if(SwingUtilities.isRightMouseButton(e) 
									&& !((Zone)e.getSource()).isFlag() && !((Zone)e.getSource()).isQsMark()) // right click handling
                            {
                            	Zone currentZone = (Zone)e.getSource();
                            	
                            	if(!currentZone.isFlag() && remainedFlag > 0) // it is not flagged zone.
                            	{
                            		/*
                            		 * flag this zone and remainedFlag - 1.
                            		 * flag icon is flag.png.
                            		 */
                            		remainedFlag--;
                            		enabledZoneCount--;
                            		
                            		ImageIcon flagImage = new ImageIcon(getClass().getResource("/image/flag.png"));
									Image image = flagImage.getImage();
									image = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
									currentZone.setIcon(new ImageIcon(image));
                            		currentZone.setFlag(true);
                            		System.out.println("flagged");
                            		
                            		/*
                            		 * win condition 3 ( last win condition )
                            		 */
                            		if(enabledZoneCount == 0 && qsMarkCount == 0)
									{
                            			/*
                            			 * same logic
                            			 */
                            			if(difficulty == 1 && (beginnerBestTime == 0 || counter < beginnerBestTime))
										{
											beginnerBestTime = counter;
										}
										else if(difficulty == 2 && (intermediateBestTime == 0 || counter < intermediateBestTime))
										{
											intermediateBestTime = counter;
										}
										else if(difficulty == 3 && (advancedBestTime == 0 || counter < advancedBestTime))
										{
											advancedBestTime = counter;
										}
										else if(difficulty == 4 && (customBestTime == 0 || counter < customBestTime))
										{
											customBestTime = counter;
										}
                            			
										timer.stop();
										
										gamesPlayed++;
										gamesWin++;
										winPercentage = (double)gamesWin / gamesPlayed * 100;
										
										if(isWinningStreak)
										{
											currentStreak++;
										}
										else
										{
											isWinningStreak = true;
											currentStreak = 1;
										}
										
										
										if(currentStreak >= winningStreak)
										{
											winningStreak = currentStreak;
										}
										
										try
										{
										
											FileWriter fw = new FileWriter("statistics.txt");
											BufferedWriter bw = new BufferedWriter(fw);
											
											String modifiedStatistics = "";
											modifiedStatistics += gamesPlayed + "\n" + gamesWin + "\n" + winPercentage
													+ "\n" + winningStreak + "\n" + losingStreak + "\n" + currentStreak
													+ "\n" + beginnerBestTime + "\n" + intermediateBestTime
													+ "\n" + advancedBestTime + "\n" + customBestTime;
											
											bw.write(modifiedStatistics);
											bw.close();
											fw.close();
										}
										catch(Exception exc)
										{
											exc.getStackTrace();
										}
										JOptionPane.showMessageDialog(null, "You Win!!", "WIN", JOptionPane.PLAIN_MESSAGE);
									}
                                }
                            	else if(currentZone.isFlag() && remainedFlag < mineCount) // it is flagged zone.
                            	{
                            		/*
                            		 * relieving flag because it is already flagged.
                            		 * remainedFlag + 1 and change icon to basic one.
                            		 */
                            		remainedFlag++;
                            		enabledZoneCount++;
                            		currentZone.setIcon(null); // go to normal icon.
                                    currentZone.setFlag(false);
                                    System.out.println("go normal");
                                }
                            	
                            	/*
                            	 * set label text to the number of rest mine.
                            	 */
                            	if(remainedFlag < 10)
                            	{
                            		lblMine.setText("00" + remainedFlag);
                            	}
                            	else if(remainedFlag >= 10 || remainedFlag < 100)
                            	{
                            		lblMine.setText("0" + remainedFlag);
                            	}
                            	else
                            	{
                            		lblMine.setText("" + remainedFlag);
                            	}
                            }
							else if(!allowQsMarks && SwingUtilities.isRightMouseButton(e) 
									&& ((Zone)e.getSource()).isFlag() && !((Zone)e.getSource()).isQsMark()) 
							{
								Zone currentZone = (Zone)e.getSource();
								/*
                        		 * relieving flag because it is already flagged.
                        		 * remainedFlag + 1 and change icon to basic one.
                        		 */
                        		remainedFlag++;
                        		enabledZoneCount++;
                        		currentZone.setIcon(null); // go to normal icon.
                                currentZone.setFlag(false);
                                System.out.println("go normal");
                                
                                /*
                            	 * set label text to the number of rest mine.
                            	 */
                            	if(remainedFlag < 10)
                            	{
                            		lblMine.setText("00" + remainedFlag);
                            	}
                            	else if(remainedFlag >= 10 || remainedFlag < 100)
                            	{
                            		lblMine.setText("0" + remainedFlag);
                            	}
                            	else
                            	{
                            		lblMine.setText("" + remainedFlag);
                            	}
							}
							else if(allowQsMarks && SwingUtilities.isRightMouseButton(e) 
									&& ((Zone)e.getSource()).isFlag())
							{
								/*
								 * this else statement is executed when user allows QS MARK option.
								 * very similar algorithm. just added QSMARK icon.
								 */
								qsMarkCount++;
								System.out.println("qsmark count : " + qsMarkCount);
								Zone currentZone = (Zone)e.getSource();
								ImageIcon questionImage = new ImageIcon(getClass().getResource("/image/qsmark.png"));
								Image qsimage = questionImage.getImage();
								qsimage = qsimage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
								currentZone.setIcon(new ImageIcon(qsimage));
								currentZone.setFlag(false);
								currentZone.setQsMark(true);
								
								/*
                            	 * set label text to the number of rest mine.
                            	 */
                            	if(remainedFlag < 10)
                            	{
                            		lblMine.setText("00" + remainedFlag);
                            	}
                            	else if(remainedFlag >= 10 || remainedFlag < 100)
                            	{
                            		lblMine.setText("0" + remainedFlag);
                            	}
                            	else
                            	{
                            		lblMine.setText("" + remainedFlag);
                            	}
								
							}
							else if(allowQsMarks && SwingUtilities.isRightMouseButton(e) 
									&& !((Zone)e.getSource()).isFlag() && ((Zone)e.getSource()).isQsMark())
							{
								Zone currentZone = (Zone)e.getSource();
								remainedFlag++;
                        		enabledZoneCount++;
                        		qsMarkCount--;
                        		
                        		System.out.println("qsmark : " + qsMarkCount);
								currentZone.setIcon(null);
								currentZone.setQsMark(false);
								System.out.println("go normal");
								
								if(remainedFlag < 10)
                            	{
                            		lblMine.setText("00" + remainedFlag);
                            	}
                            	else if(remainedFlag >= 10 || remainedFlag < 100)
                            	{
                            		lblMine.setText("0" + remainedFlag);
                            	}
                            	else
                            	{
                            		lblMine.setText("" + remainedFlag);
                            	}
							}
                            else // exception
                            {
                                System.out.println("unvalid Click!!");
                            }
                        }
						
					}
				});
			}
		}
	}
	
	/*
	 * additional listener
	 * due to using .doClick() method.
	 */
	private void clickedZoneLoad(Zone z)
	{
		z.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				/*
				 * same value handling algorithm.
				 */
				if(z.getValue() == 0)
				{
					z.setBackground(new Color(92, 209, 229));
					z.setEnabled(false);
					enabledZoneCount--;
				}
				else // case surround mines exist
				{
					z.setText(String.valueOf(z.getValue()));
					z.setForeground(Color.YELLOW);
					z.setBackground(Color.BLACK);
					z.setEnabled(false);
					enabledZoneCount--;
				}
			}
		});
	}
	
	private void loadFile(File f)
	{
		FileReader fr;
		BufferedReader br;
		
		try 
		{
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			/*
			 * "cline" is each line which FileReader reads.
			 * every line is added in "string" and after all line is read,
			 * make "string" to array using .split method.
			 * so after that, array consists of something like below.
			 * array[0] = selected file's difficulty
			 * array[1] = selected file's width
			 * array[2] = selected file's height
			 * array[3] = selected file's mineCount
			 * array[4] = selected file's remainedFlag
			 * array[5] = selected file's counter
			 * array[6] ~ array[6 + height] = selected file's zone value
			 * (one array[i] has 1 set of width zone value)
			 * array[6 + height + 1] ~ array[6 + 2*height + 1] = selected file's already selected or not value.
			 * array[6 + 2*height + 2] ~ = selected file's flagged zone state
			 */
			String cline = "";
			String string = "";
			String[] array;
			
			while((cline = br.readLine()) != null)
			{
				string = string + cline + ",";
			}
			System.out.println("End");
			array = string.split(",");
			
			System.out.println("arrayLength : " + array.length);
			
			for(int i = 0; i<array.length; i++)
			{
				System.out.println(array[i]);
			}
			try
			{
				/*
				 * so assign the array value to current frame state.
				 */
				difficulty = Integer.valueOf(array[0]);
				width = Integer.valueOf(array[1]);
				height = Integer.valueOf(array[2]);
				mineCount = Integer.valueOf(array[3]);
				remainedFlag = Integer.valueOf(array[4]);
				counter = Integer.valueOf(array[5]);
				enabledZoneCount = width * height;
			}
			catch(NumberFormatException exc)
			{
				System.out.println("Line 241, NumberFormatException occured.");
			}
			
			/*
			 * confirm the state.
			 */
			System.out.println("difficulty : " + difficulty);
			System.out.println("width : " + width);
			System.out.println("height : " + height);
			System.out.println("mineCount : " + mineCount);
			System.out.println("remainedFlag : " + remainedFlag);
			System.out.println("counter : " + counter);
			
			/*
			 * clean the zone.
			 */
			pnMineZone.removeAll();
			pnMineZone.validate();
			
			/*
			 * re-initialize zone by loaded file information.
			 */
			pnMineZone.setLayout(new GridLayout(width, height, 0, 0));
			/*
			 * why make zone bigger than width, height?
			 * the answer is that it is easy to calculate adjacent mine.
			 * (prevent ArrayIndexOutOfBoundsException)
			 */
			zone = new Zone[width + 2][height + 2];
			
			/*
			 * zone initialization.
			 */
			for(int i = 0; i<width + 2; i++)
			{
				for(int j = 0; j<height + 2; j++)
				{
					/*
					 * initialize each zone and border's value.
					 * the maximum number of adjacent mine is 8, so
					 * set border's value by 10, which is impossible value if zone is not border.
					 */
					zone[i][j] = new Zone(i,j);
					
					if(i == 0 || j == 0 || i == width + 1 || j == height + 1) // edge
					{
						zone[i][j].setValue(10);
					}
					else // if zone[i][j] is not border, than add in the panel.
					{
						pnMineZone.add(zone[i][j]);
					}
					
				}
			}
			
			/*
			 * set each zone's value.
			 *   -1 : mine
			 *    0 : nothing
			 *  1~8 : the number of adjacent mine
			 */
			int substringCounter = 0;
			int arrayCounter = 7;
			
			for(int i = 1; i<width + 1; i++)
			{
				for(int j = 1; j<height + 1; j++)
				{
					/*
					 * if .txt file's value is "-", it means that zone is mine.
					 * so set value -1.
					 */
					if(array[6 + (i - 1)].substring(substringCounter, substringCounter + 1).equals("-")) // mine
					{
						zone[i][j].setValue(-1);
					}
					else // 0 ~ 8
					{
						zone[i][j].setValue(Integer.valueOf(array[6 + (i - 1)].substring(substringCounter, substringCounter + 1)));
					}
					substringCounter++;
				}
				substringCounter = 0;
				arrayCounter++;
			}
			/*
			 * value setting is done.
			 * and we need to add each zone(JButton)'s listener.
			 * addZoneListener method do this.
			 */
			addZoneListener(zone);
			
			/*
			 * after that, we need to load already clicked zone.
			 * for this, doClick method is used.
			 */
			for(int i = 1; i<width + 1; i++)
			{
				for(int j = 1; j<height + 1; j++)
				{
					/*
					 * if current index value is "0", it is already clicked value.
					 * else, it is not clicked yet.
					 */
					if(array[arrayCounter + (i - 1)].substring(substringCounter, substringCounter + 1).equals("0")) // 
					{
						/*
						 * problem is, "mouseListener" doesn't response at .doClick() method.
						 * we added "mouseListener" to each zone because we should handle not only
						 * left click, but also right click(flagging). 
						 * but doClick() method doesn't work. 
						 * so, although probably it is not the best choice,
						 * additionally implemented "addActionListener" to already selected zone.
						 * (clickedZoneLoad() method)
						 * (can I "CLICK" JButton without using .doClick() method?)
						 * 
						 * anyway, after that, we can use doClick() method because that zone[i][j] has
						 * actionListener!
						 * so we can load each zone with clicked state.
						 */
						clickedZoneLoad(zone[i][j]);
						zone[i][j].doClick();
					}
					else
					{
						// do nothing because zone[i][j] is not clicked state.
					}
					
					substringCounter++;
					
					/*
					 * i == width, j == height means, it is the last loop.
					 * in this for loop, we don't increase arrayCounter yet.
					 * but arrayCounter must increase because array still has more information 
					 * that should load to the each zone.
					 * so, assign arrayCounter to the newest line location.
					 */
					if(i == width && j == height) 
					{
						arrayCounter = arrayCounter + (i + 1);
					}
				}
				substringCounter = 0;
			}
			
			/*
			 * next, we need to know about whether each zone is flagged or not.
			 * this information is stored in from current array[arrayCount]. 
			 * if flagged, 1.
			 * not flagged, 0.
			 */
			for(int i = 1; i<width + 1; i++)
			{
				for(int j = 1; j<height + 1; j++)
				{
					/*
					 * so, if substring value is "1", that zone is already flagged.
					 * change the icon to the flag.png and setFlag(true).
					 */
					if(array[arrayCounter + (i - 1)].substring(substringCounter, substringCounter + 1).equals("1")) // 
					{	
                    	zone[i][j].setIcon(new ImageIcon(getClass().getResource("/image/flag.png")));
                    	zone[i][j].setFlag(true);
                   		System.out.println("flagged");
					}
					else
					{
						// not flagged. do nothing.
					}
					substringCounter++;
				}
				substringCounter = 0;
			}
			
			/*
			 * finally, all array information is loaded!
			 * the rest things are text setting, icon setting, frame size setting.
			 * it is pretty simple things. (load fihished)
			 */
			if(remainedFlag < 10)
        	{
        		lblMine.setText("00" + remainedFlag);
        	}
        	else if(remainedFlag >= 10 || remainedFlag < 100)
        	{
        		lblMine.setText("0" + remainedFlag);
        	}
        	else
        	{
        		lblMine.setText("" + remainedFlag);
        	}
			
			ImageIcon smileImage = new ImageIcon(getClass().getResource("/image/smile.png"));
			Image image = smileImage.getImage();
			image = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
			btnRestart.setIcon(new ImageIcon(image));
			isGameOver = false;
			
			switch(difficulty)
			{
				case 1:
					frame.setBounds(100, 100, 449, 449);
					frame.setBounds(100, 100, 450, 450);
					break;
				case 2:
					frame.setBounds(100, 100, 799, 899);
					frame.setBounds(100, 100, 800, 900);
					break;
				case 3:
					frame.setBounds(100, 100, width*89, height*29);
					frame.setBounds(100, 100, width*90, height*30);
					break;
				case 4:
					if(width <= 16 && height <= 16)
					{
						frame.setBounds(100, 100, width*49, height*49);
						frame.setBounds(100, 100, width*50, height*50);
					}
					else
					{
						frame.setBounds(100, 100, width*79, height*59);
						frame.setBounds(100, 100, width*80, height*60);
					}
					break;
				default:
					break;
			}
			
		}
		catch(Exception ex)
		{
			System.out.println("file path is illegal access, or maybe lastSavedGamePath.txt is damaged.");
			System.out.println("alternatively, basic form game starts.");
			pnMineZone.removeAll();
			pnMineZone.validate();
			pnMineZone.repaint();
			init();
			addZoneListener(zone);
		}
	}
	
	public static String optionSetting(int option)
	{
		/*
		 * option = 0 : read all state
		 * option = 1 : "Always Save On Exit" state changed
		 * option = 2 : "Always Continue Saved Game" state changed
		 * option = 3 : "Allow Question Mark" state changed
		 */
		FileReader fReader;
		BufferedReader bReader;
		
		String[] cline = new String[3];
		
		try 
		{
			/*
			 * according to the "option", 
			 * read "setting.txt" and modify if need.
			 */
			fReader = new FileReader("setting.txt");
			bReader = new BufferedReader(fReader);
			
			if(option == 0)
			{
				for(int i = 0; i < 3; i++)
				{
					cline[i] = bReader.readLine();
				}
			}
			else if(option == 1)
			{
				for(int i = 0; i < 3; i++)
				{
					if(i+1 == option)
					{
						cline[i] = bReader.readLine();
						if(cline[i].equals("1"))
							cline[i] = "0";
						else
							cline[i] = "1";
					}
					else
					{
						cline[i] = bReader.readLine();
					}
				}
			}
			else if(option == 2)
			{
				for(int i = 0; i < 3; i++)
				{
					if(i+1 == option)
					{
						cline[i] = bReader.readLine();
						if(cline[i].equals("1"))
							cline[i] = "0";
						else
							cline[i] = "1";
					}
					else
					{
						cline[i] = bReader.readLine();
					}
				}
			}
			else if(option == 3)
			{
				for(int i = 0; i < 3; i++)
				{
					if(i+1 == option)
					{
						cline[i] = bReader.readLine();
						if(cline[i].equals("1"))
							cline[i] = "0";
						else
							cline[i] = "1";
					}
					else
					{
						cline[i] = bReader.readLine();
					}
				}
			}
			
			System.out.println("Always save game on exit : " + cline[0]);
			System.out.println("Always continue saved games : " + cline[1]);
			System.out.println("Allow question marks : " + cline[2]);
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println("setting.txt doesn't exist. now basic setting.txt is made automatically.");
			
			File recoverSettingFile = new File("setting.txt");
			FileWriter fw;
			BufferedWriter bw;
	
			try 
			{
				fw = new FileWriter(recoverSettingFile);
				bw = new BufferedWriter(fw);
				
				bw.write("0\n0\n0");
				bw.close();
			} 
			catch (IOException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try
			{
				fReader = new FileReader("setting.txt");
				bReader = new BufferedReader(fReader);
				
				for(int i = 0; i < 3; i++)
				{
					cline[i] = bReader.readLine();
				}
				
				bReader.close();
			}
			catch(IOException ioe)
			{
				System.out.println("io exception.");
			}
			
			System.out.println("Always save game on exit : " + cline[0]);
			System.out.println("Always continue saved games : " + cline[1]);
			System.out.println("Allow question marks : " + cline[2]);
		}
		catch(Exception exception)
		{
			System.out.println("General exception");
		}
		finally
		{
			if(cline[0].equals("1"))
				saveGameOnExit = true;
			else
				saveGameOnExit = false;
			
			if(cline[1].equals("1"))
				continueSavedGames = true;
			else
				continueSavedGames = false;
			
			if(cline[2].equals("1"))
				allowQsMarks = true;
			else
				allowQsMarks = false;
			
			
		}
		
		return cline[0] + "\n" + cline[1] + "\n" + cline[2];
	}
	
	/*
	 * load statistics information.
	 */
	public String loadStatistics()
	{
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		try
		{
			fileReader = new FileReader("statistics.txt");
			bufferedReader = new BufferedReader(fileReader);
			
			int lineCnt = 0;
			String line = "";
			String loadedInfo = "";
			while((line = bufferedReader.readLine()) != null)
			{
				loadedInfo = loadedInfo + line + ",";
				lineCnt++;
			}
			
			/*
			 * valid information format file have 10 line.
			 * so if "lineCnt" is not 10, it is illegal statement.
			 * in that case, re-make statistics.txt and
			 * set the file by default value and return null.
			 * if this method's return value is null,
			 * then basic game will be started.
			 */
			if(lineCnt != 10)
			{
				System.out.println("statistics.txt file is corrupted. re-initialize statistics.txt file.");
				FileWriter fileWriter;
				BufferedWriter bufferedWriter;
				try 
				{
					fileWriter = new FileWriter("statistics.txt");
					bufferedWriter = new BufferedWriter(fileWriter);
					
					bufferedWriter.write("0\n0\n0\n0\n0\n0\n0\n0\n0\n0");
					bufferedWriter.close();
					fileWriter.close();
				}
				catch(Exception ex)
				{
					System.out.println("file write error");
				}
				
				bufferedReader.close();
				fileReader.close();
				
				return null;
			}
			
			System.out.println("Line : " + lineCnt);
			bufferedReader.close();
			fileReader.close();
			
			return loadedInfo;
		}
		catch(Exception e)
		{
			System.out.println("no statistics file");
			File file = new File("statistics.txt");
			
			FileWriter fileWriter;
			BufferedWriter bufferedWriter;
			try 
			{
				fileWriter = new FileWriter(file);
				bufferedWriter = new BufferedWriter(fileWriter);
				
				bufferedWriter.write("0\n0\n0\n0\n0\n0\n0\n0\n0\n0");
				bufferedWriter.close();
				fileWriter.close();
			}
			catch(Exception ex)
			{
				System.out.println("file write error");
			}

			return null;
		}
		
		
	}
}
