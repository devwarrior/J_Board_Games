/**
 * @(#) Fifteen.java
 *
 * Fifteen game in Java using AWT
 *
 * @author Luigi D'Andrea
 * @version 1.00 05/04/24
 * @copyright 2005 xAppSoftware
 */
 
import java.awt.*;
import java.applet.*;
import java.applet.Applet;
import java.awt.event.*;
import java.util.Random;

public class Fifteen
{
    
	public static void main(String[] args)
	{
		// Create application frame.
		Board frame = new Board();
		// Show frame
		frame.setVisible(true);
	}
}

class Board extends Frame implements MouseListener
{
	private FifteenGame theGame =  new FifteenGame(30);
	private final int   offset  =  30;

	public Board()
	{
		theGame.shuffle();
		setVisible(true);
		setSize(250, 250);
		setBackground(new Color(127,151,175));
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{ 
				dispose();
			}
		});
		addMouseListener(this);	// Adds a mouse listener to the frame
	}

	public void paint(Graphics g)
	{
		theGame.paint(g);
		g.setColor(Color.green);
		g.fill3DRect(180, 30, 60, 30, true);
		g.setColor(Color.black);
		g.drawString("Shuffle", 186, 48);
	}

	public void mousePressed(MouseEvent e)
	{
		Point mouse	=	new Point();
		mouse		=	e.getPoint();

		if(mouse.x>180&&mouse.x<240&&mouse.y>30&&mouse.y<60)
		{
			theGame.shuffle();
			repaint();
		}
		if(mouse.x>offset&&mouse.x<offset+120&&mouse.y>offset&&mouse.y<offset+120)
		{
			theGame.muovi(mouse);
			repaint();
		}
	}

	public void mouseClicked(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
}

class FifteenGame
{
	private int      board[][]   =  new int[4][4];
	private int      offset;
	private boolean  win         =  false;

	public FifteenGame(int offset)
	{
		int i, j;
		this.offset           =   offset;
		int counter           =   1;
		for(i=0; i<4; i++)
		{
			for(j=0; j<4; j++)
			{
				board[i][j]   =   counter;
				counter++;
			}
		}
		board[3][3]           =   0;
	}
	
	public void paint(Graphics g)
	{
		int i, j;
		for(j=0; j<4; j++)
			for(i=0; i<4; i++)
			{
				if(board[i][j]<=4 && board[i][j]>0 || 
					board[i][j]<=12 && board[i][j]>8)
				{
					if(board[i][j]%2==0)
						g.setColor(Color.red);
					else
						g.setColor(Color.blue);
				}
				else
				{
					if(board[i][j]%2==1)
						g.setColor(Color.red);
					else
						g.setColor(Color.blue);
				}	
				if(board[i][j]==0)
					g.setColor(Color.white);
				g.fill3DRect(offset+30*j, offset+30*i, 30, 30, true);
				g.setColor(Color.black);
				if(board[i][j]!=0)
					g.drawString(""+board[i][j], offset+30*j+12, offset+30*i+18);
			}
		if(win==true)
		{
			g.drawString("You have won", 200, 30);
		}
	}
	
	public void muovi(Point mouse)
	{
		int  posX, posY, i;
		
		posX	=	((mouse.x-offset)/30);
		posY	=	((mouse.y-offset)/30);
		if(posY-1>=0)
		{
			if(board[posY-1][posX]==0)
			{
				board[posY-1][posX]	=	board[posY][posX];
				board[posY][posX]		=	0;
				checkWin();
			}
		}
		if(posY+1<4)
		{
			if(board[posY+1][posX]==0)
			{
				board[posY+1][posX]	=	board[posY][posX];
				board[posY][posX]		=	0;
				checkWin();
			}
		}
		if(posX-1>=0)
		{
			if(board[posY][posX-1]==0)
			{
				board[posY][posX-1]	=	board[posY][posX];
				board[posY][posX]		=	0;
				checkWin();
			}
		}
		if(posX+1<4)
		{
			if(board[posY][posX+1]==0)
			{
				board[posY][posX+1]	=	board[posY][posX];
				board[posY][posX]		=	0;
				checkWin();
			}
		}
	}

	public void checkWin()
	{
		int      i, j;
		boolean  flag    =   true;
		int      counter =   1;

		for(i=0; i<4; i++)
		{
			for(j=0; j<4; j++)
			{
				if(board[i][j]!=counter)
					flag	=	false;
				counter++;
				if(counter==16)
					counter=0;
			}
		}
		if(flag==true)
			win=true;
	}

	public void shuffle()
	{
		int    aux[]      =	new int[16];
		int    i, j;
		int    counter    = 0;

		for(i=0; i<16; i++)
		{
			aux[i]	=	(int)(Math.random()*15);
		}
		for(i=0; i<16; i++)
		{
			for(j=0; j<i; j++)
			{
				if(aux[i]==aux[j])
				{
					aux[i]	=	(int)(Math.random()*16);
					j=-1;
				}
			}
		}
		for(i=0; i<4; i++)
		{
			for(j=0; j<4; j++)
			{
				board[i][j]	=	aux[counter];
				counter++;
			}
		}
	}
}

