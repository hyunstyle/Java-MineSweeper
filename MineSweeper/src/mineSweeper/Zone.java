package mineSweeper;
import java.awt.Color;

import javax.swing.JButton;

public class Zone extends JButton
{
	/*
	 * each zone information.
	 * flag is current zone's flagged statement.
	 * can access by calling isFlag() method.
	 * qsmark is current zone's QSMARKed statement.
	 * can access by calling isQsMark() method.
	 * value is this zone's value.
	 * -----------------
	 * value =  -1  -> this zone is mine.
	 * value =   0  -> this zone is empty.
	 * value = 1~8  -> this zone's adjacent area has some mines.
	 * value =  10  -> this zone is BORDER. this zone is not added to main frame's panel.
	 */
	private int rowPosition;
	private int colPosition;
	private boolean flag;
	private boolean qsMark;
	private int value; // mine, blank, number, 가장자리 : 10
	
	
	public Zone(int x, int y) 
	{
		// initialization.
		super();
		this.value = 0;
		this.rowPosition = x;
		this.colPosition = y;
		this.setBackground(new Color(107, 102, 255));
		flag = false;
		qsMark = false;
		
	}
	
	public void setValue(int val)
	{
		this.value = val;
	}
	public int getValue()
	{
		return this.value;
	}
	
	public void setFlag(boolean isFlag)
	{
		this.flag = isFlag;
	}
	
	public boolean isFlag()
	{
		return flag;
	}
	
	public void setQsMark(boolean isQsMark)
	{
		this.qsMark = isQsMark;
	}
	public boolean isQsMark()
	{
		return qsMark;
	}
	
	public int getRowPosition()
	{
		return rowPosition;
	}
	
	public int getColPosition()
	{
		return colPosition;
	}

}
