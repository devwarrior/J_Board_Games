import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.image.*;

/**
 * Sample application using Frame.
 *
 * @author 
 * @version 1.00 05/09/14
 */
public class SweeperFrame extends Frame implements MouseListener
{
	private int theSecretBoard[][]	=    new int[25][25];
	private int theBoard[][]		=    new int[25][25];
	private int	theXPos, theYPos;
	private int win, lost;
	/**
	* The constructor.
	*/  
	public SweeperFrame()
	{
		initialize(40);
		MenuBar menuBar = new MenuBar();
		Menu menuFile	= new Menu();
		Menu menuHelp	= new Menu();

		MenuItem menuFileEasy	= new MenuItem();
		MenuItem menuFileMedium	= new MenuItem();
		MenuItem menuFileHard	= new MenuItem();
		MenuItem menuFileExit	= new MenuItem();
		MenuItem menuHelpAbout	= new MenuItem();

		menuFile.setLabel("File");
		menuHelp.setLabel("?");
		menuHelpAbout.setLabel("Informazioni...");
		menuFileEasy.setLabel("Easy");
		menuFileMedium.setLabel("Medium");
		menuFileHard.setLabel("Hard");
		menuFileExit.setLabel("Exit");

		// Add action listener.for the menu button
		menuFileEasy.addActionListener
        (
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					initialize(40);
				}
			}
		);
		menuFileMedium.addActionListener
        (
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					initialize(80);
				}
			}
		);
		menuFileHard.addActionListener
        (
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					initialize(120);
				}
			}
		);
		menuFileExit.addActionListener
        (
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					SweeperFrame.this.windowClosed();
				}
			}
		);
		menuHelpAbout.addActionListener
        (
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					about();
				}
			}
		);
		menuFile.add(menuFileEasy);
		menuFile.add(menuFileMedium);
		menuFile.add(menuFileHard);
		menuFile.add(menuFileExit);
		menuHelp.add(menuHelpAbout);
		
		menuBar.add(menuFile);
		menuBar.add(menuHelp);

		setTitle("Sweeper");
		setMenuBar(menuBar);
		setSize(new Dimension(510, 548));

		// Add window listener.
		this.addWindowListener
		(
			new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					SweeperFrame.this.windowClosed();
				}
			}
		);
		addMouseListener(this);
	}

	public void about()
	{
		MessageBox message = new MessageBox( this,"Sweeper",
				"Copyright 2005 xAppSoftware","Written by Luigi D'Andrea" );
	}
	public void initialize(int theBombNumber)
	{
		int i, j;
		int x, y;
		int contatore=0;
		win		=	0;
		lost	=	0;
		for(i=0; i<25; i++)
		{
			for(j=0; j<25; j++)
			{
				theSecretBoard[i][j]	=	0;
				theBoard[i][j]			=	0;
			}
		}
		//	Builds the secret board
		while(contatore<theBombNumber)
		{
			x	=	(int)(Math.random()*25);
			y	=	(int)(Math.random()*25);
			if(theSecretBoard[x][y]!=-1)
			{
				contatore++;
				theSecretBoard[x][y]	=	-1;
			}
		}
	}

    public void paint(Graphics g)
	{
		int i, j;
		g.setColor(Color.gray);
		for(i=0; i<25; i++)
		for(j=0; j<25; j++)
		{
			g.fill3DRect(i*20+5, j*20+43, 20, 20, true);
		}
	}

	public void update(Graphics g)
	{
		String gigi="";
		int i, j;
		g.setColor(Color.gray);
		for(i=0; i<25; i++)
		for(j=0; j<25; j++)
		{
			if(theBoard[i][j]==9)
			{
				g.fill3DRect(i*20+5, j*20+43, 20, 20, true);
				g.setColor(Color.red);
				g.fillOval(i*20+5+5, j*20+43+5, 10, 10);
				g.setColor(Color.gray);
			}
			else if(theBoard[i][j]==10)
			{
				g.fill3DRect(i*20+5, j*20+43, 20, 20, false);
			}
			else if(theBoard[i][j]>0&&theBoard[i][j]<9)
			{
				g.fill3DRect(i*20+5, j*20+43, 20, 20, false);
				g.setColor(Color.white);
				g.drawString(gigi+theBoard[i][j], i*20+11, j*20+57);
				g.setColor(Color.gray);
			}
			else if(theBoard[i][j]==110)
			{
				g.fill3DRect(i*20+5, j*20+43, 20, 20, false);
				g.setColor(Color.yellow);
				g.fillOval(i*20+5+5, j*20+43+5, 10, 10);
				g.setColor(Color.gray);
			}
			else if(theBoard[i][j]==111)
			{
				g.fill3DRect(i*20+5, j*20+43, 20, 20, false);
				g.setColor(Color.yellow);
				g.fillOval(i*20+5+5, j*20+43+5, 10, 10);
				g.setColor(Color.red);
				g.drawLine(i*20+5,j*20+43,i*20+20+5,j*20+20+43);
				g.drawLine(i*20+20+5,j*20+43,i*20+5,j*20+20+43);
				g.setColor(Color.gray);
			}
			else if(lost==1&&theSecretBoard[i][j]==-1)
			{
				g.fill3DRect(i*20+5, j*20+43, 20, 20, false);
				g.setColor(Color.blue);
				g.fillOval(i*20+5+5, j*20+43+5, 10, 10);
				g.setColor(Color.gray);
			}
			else
				g.fill3DRect(i*20+5, j*20+43, 20, 20, true);
		}
		
	}

	public void mousePressed(MouseEvent e)
	{
		int	result=0;
		if(win==1)
			return;
		Point mouse	=	new Point();
		mouse		=	e.getPoint();
		//	Position on the board  
		theXPos	=	(int)(mouse.x-5)/20;
		theYPos	=	(int)(mouse.y-43)/20;

		if((e.getModifiers() & e.BUTTON3_MASK)!=0)	// Right click handling
		{
			if(theBoard[theXPos][theYPos]==9)
				theBoard[theXPos][theYPos]=0;
			else
				theBoard[theXPos][theYPos]=9;
			this.getToolkit().beep();
		}
		else										// Left click handling
		{
			if(theSecretBoard[theXPos][theYPos]==-1)
			{
				for(int i=0; i<25; i++)
					for(int j=0; j<25; j++)
					{
						if(theBoard[i][j]==9&&theSecretBoard[i][j]!=-1)
						{
							theBoard[i][j]=111;
						}
						else if(theBoard[i][j]==9&&theSecretBoard[i][j]==-1)
							theBoard[i][j]=theSecretBoard[i][j]+111;
						else
							;
					}
				lost	=	1;
				win		=	1;
				repaint();
				this.getToolkit().beep();
				return;
			}
			else
				result	=	checkForBombs(theXPos, theYPos);
			if(result==0)
			{
				theBoard[theXPos][theYPos]=10;
				checkForNearZeros(theXPos, theYPos); // Discovers and cleans all zero cells
				checkForValuesNearZeros();
			}
			else
				theBoard[theXPos][theYPos]=result;
			win	=	checkForWin();
			if(win==1)
			{
				repaint();
				MessageBox message = new MessageBox( this,"Sweeper",
				"You won!!!","  GREAT      " );
			}
		}
		repaint();
	}
	public void mouseClicked(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){repaint();}
	public void mouseExited(MouseEvent e){}

	public int checkForWin()
	{
		int i, j;
		for(i=0; i<25; i++)
		{
			for(j=0; j<25; j++)
			{
				if(theSecretBoard[i][j]==-1)
				{
					if(theBoard[i][j]!=9)
					{
						return(0);
					}
				}
			}
		}
		return(1);
	}
	public void checkForNearZeros(int x, int y)
	{
		int posx, posy;
		int	result=0;
		posx	=	x-1;
		posy	=	y-1;
		if(posx>=0&&posy>=0)
		{
			result	=	checkForBombs(posx, posy);
			if(result==0&&theBoard[posx][posy]!=10)
			{
				theBoard[posx][posy]=10;
				checkForNearZeros(posx, posy);// Discovers and cleans all zero cells
			}
		}
		
		posx	=	x;
		posy	=	y-1;
		if(posy>=0)
		{
			result	=	checkForBombs(posx, posy);
			if(result==0&&theBoard[posx][posy]!=10)
			{
				theBoard[posx][posy]=10;
				checkForNearZeros(posx, posy); // Discovers and cleans all zero cells
			}
		}

		posx	=	x+1;
		posy	=	y-1;
		if(posy>=0&&posx<=24)
		{
			result	=	checkForBombs(posx, posy);
			if(result==0&&theBoard[posx][posy]!=10)
			{
				theBoard[posx][posy]=10;
				checkForNearZeros(posx, posy); // Discovers and cleans all zero cells
			}
		}

		posx	=	x-1;
		posy	=	y;
		if(posx>=0)
		{
			result	=	checkForBombs(posx, posy);
			if(result==0&&theBoard[posx][posy]!=10)
			{
				theBoard[posx][posy]=10;
				checkForNearZeros(posx, posy); // Discovers and cleans all zero cells
			}
		}

		posx	=	x+1;
		posy	=	y;
		if(posx<=24)
		{
			result	=	checkForBombs(posx, posy);
			if(result==0&&theBoard[posx][posy]!=10)
			{
				theBoard[posx][posy]=10;
				checkForNearZeros(posx, posy); // Discovers and cleans all zero cells
			}
		}

		posx	=	x-1;
		posy	=	y+1;
		if(posx>=0&&posy<=24)
		{
			result	=	checkForBombs(posx, posy);
			if(result==0&&theBoard[posx][posy]!=10)
			{
				theBoard[posx][posy]=10;
				checkForNearZeros(posx, posy); // Discovers and cleans all zero cells
			}
		}

		posx	=	x;
		posy	=	y+1;
		if(posy<=24)
		{
			result	=	checkForBombs(posx, posy);
			if(result==0&&theBoard[posx][posy]!=10)
			{
				theBoard[posx][posy]=10;
				checkForNearZeros(posx, posy); // Discovers and cleans all zero cells
			}
		}

		posx	=	x+1;
		posy	=	y+1;
		if(posx<=24&&posy<=24)
		{
			result	=	checkForBombs(posx, posy);
			if(result==0&&theBoard[posx][posy]!=10)
			{
				theBoard[posx][posy]=10;
				checkForNearZeros(posx, posy); // Discovers and cleans all zero cells
			}
		}
	}

	public void checkForValuesNearZeros()
	{
		int	i, j, value;
		int	posx, posy;
		int	result	=	0;
		for(i=0; i<25; i++)
		{
			for(j=0; j<25; j++)
			{
				if(theBoard[i][j]==10)
				{
					posx	=	i-1;
					posy	=	j-1;
					if(posx>=0&&posy>=0)
					{
						if(theBoard[posx][posy]==0)
							theBoard[posx][posy]	=	checkForBombs(posx, posy);
					}
					posx	=	i+1;
					posy	=	j+1;
					if(posx<=24&&posy<=24)
					{
						if(theBoard[posx][posy]==0)
							theBoard[posx][posy]	=	checkForBombs(posx, posy);
					}
					posx	=	i-1;
					posy	=	j+1;
					if(posx>=0&&posy<=24)
					{
						if(theBoard[posx][posy]==0)
							theBoard[posx][posy]	=	checkForBombs(posx, posy);
					}
					posx	=	i+1;
					posy	=	j-1;
					if(posx<=24&&posy>=0)
					{
						if(theBoard[posx][posy]==0)
							theBoard[posx][posy]	=	checkForBombs(posx, posy);
					}
					posx	=	i-1;
					posy	=	j;
					if(posx>=0)
					{
						if(theBoard[posx][posy]==0)
							theBoard[posx][posy]	=	checkForBombs(posx, posy);
					}
					posx	=	i+1;
					posy	=	j;
					if(posx<=24)
					{
						if(theBoard[posx][posy]==0)
							theBoard[posx][posy]	=	checkForBombs(posx, posy);
					}
					posx	=	i;
					posy	=	j+1;
					if(posy<=24)
					{
						if(theBoard[posx][posy]==0)
							theBoard[posx][posy]	=	checkForBombs(posx, posy);
					}
					posx	=	i;
					posy	=	j-1;
					if(posy>=0)
					{
						if(theBoard[posx][posy]==0)
							theBoard[posx][posy]	=	checkForBombs(posx, posy);
					}
				}
			}
		}
	}

	public int checkForBombs(int x, int y)
	{
		int counter=0;
		if(x==0&&y==0)
		{
			if(theSecretBoard[0][1]==-1)
				counter++;
			if(theSecretBoard[1][1]==-1)
				counter++;
			if(theSecretBoard[1][0]==-1)
				counter++;
		}
		else if(x==24&&y==24)
		{
			if(theSecretBoard[23][24]==-1)
				counter++;
			if(theSecretBoard[23][23]==-1)
				counter++;
			if(theSecretBoard[24][23]==-1)
				counter++;
		}
		else if(x==0&&y==24)
		{
			if(theSecretBoard[0][24]==-1)
				counter++;
			if(theSecretBoard[1][23]==-1)
				counter++;
			if(theSecretBoard[1][24]==-1)
				counter++;
		}
		else if(x==24&&y==0)
		{
			if(theSecretBoard[24][0]==-1)
				counter++;
			if(theSecretBoard[23][1]==-1)
				counter++;
			if(theSecretBoard[24][1]==-1)
				counter++;
		}
		else if(x==0)
		{
			if(theSecretBoard[x][y-1]==-1)
				counter++;
			if(theSecretBoard[x][y+1]==-1)
				counter++;
			if(theSecretBoard[x+1][y-1]==-1)
				counter++;
			if(theSecretBoard[x+1][y]==-1)
				counter++;
			if(theSecretBoard[x+1][y+1]==-1)
				counter++;
		}
		else if(x==24)
		{
			if(theSecretBoard[x][y-1]==-1)
				counter++;
			if(theSecretBoard[x][y+1]==-1)
				counter++;
			if(theSecretBoard[x-1][y-1]==-1)
				counter++;
			if(theSecretBoard[x-1][y]==-1)
				counter++;
			if(theSecretBoard[x-1][y+1]==-1)
				counter++;
		}
		else if(y==0)
		{
			if(theSecretBoard[x-1][y]==-1)
				counter++;
			if(theSecretBoard[x+1][y]==-1)
				counter++;
			if(theSecretBoard[x-1][y+1]==-1)
				counter++;
			if(theSecretBoard[x][y+1]==-1)
				counter++;
			if(theSecretBoard[x+1][y+1]==-1)
				counter++;
		}
		else if(y==24)
		{
			if(theSecretBoard[x-1][y]==-1)
				counter++;
			if(theSecretBoard[x+1][y]==-1)
				counter++;
			if(theSecretBoard[x-1][y-1]==-1)
				counter++;
			if(theSecretBoard[x][y-1]==-1)
				counter++;
			if(theSecretBoard[x+1][y-1]==-1)
				counter++;
		}
		else
		{
			if(theSecretBoard[x-1][y-1]==-1)
				counter++;
			if(theSecretBoard[x-1][y+1]==-1)
				counter++;
			if(theSecretBoard[x-1][y]==-1)
				counter++;
			if(theSecretBoard[x+1][y-1]==-1)
				counter++;
			if(theSecretBoard[x+1][y+1]==-1)
				counter++;
			if(theSecretBoard[x+1][y]==-1)
				counter++;
			if(theSecretBoard[x][y-1]==-1)
				counter++;
			if(theSecretBoard[x][y+1]==-1)
				counter++;
		}
		return(counter);
	}
	/*
	* Shutdown procedure when run as an application.
	*/
	protected void windowClosed()
	{
		System.exit(0);
	}
}


//
//	This class implements a dialog box to show messages to the user
//
class MessageBox extends Dialog implements ActionListener
{
	private Button okButton;

	public MessageBox(Frame f,String title,String message, String message1)
	{
		// Builds a dialog box.
		super(f,title,true);

		// Sets the background color
		setBackground(new Color (255, 255, 255));
		setSize(new Dimension(200, 200));

		// The dialog box cannot be resized
		setResizable(false);

		// Adds a label with the text of the message
		add(new TextField(message+""+message1), BorderLayout.CENTER);

		// Adds a button to close the dialog box
		okButton = new Button("    OK    ");
		okButton.addActionListener(this);

		// adds the button
		add(okButton, BorderLayout.SOUTH);
		pack();

		// Shows the dialog box
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		// Disposes the dialog box
		dispose();
	}

}

