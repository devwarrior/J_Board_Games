import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
/**
 * Sample application using Frame.
 *
 * @author 
 * @version 1.00 05/09/21
 */
public class JudokuFrame extends Frame implements MouseListener
{
	private int theBoard[][]			=	new int[9][9];
	private int initBoard[][]			=	new int[9][9];
	private static final int yOff		=	103;
	private static final int xOff		=	45;
	public int theClick					=	0;
	public int globalxPos				=	0;
	public int globalyPos				=	0;
	private int win						=	0;
    private JFileChooser mFileChooser	=	new JFileChooser(".");
    /**
     * The constructor.
     */  
	public JudokuFrame()
	{
		/*
		try
		{
			readFromFile("1.txt");
		}
		catch(IOException ioe)
		{
			System.out.println("C'e' stato un errore nella lettura del file");
		}
		*/
		initialize();
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu();
		MenuItem menuFileOpen	= new MenuItem();
		MenuItem menuFileEmpty	= new MenuItem();
		MenuItem menuFileSave	= new MenuItem();
		MenuItem menuFileExit	= new MenuItem();

		menuFile.setLabel("File");
		menuFileOpen.setLabel("Open");
		menuFileEmpty.setLabel("Empty");
		menuFileSave.setLabel("Save as");
		menuFileExit.setLabel("Exit");
        
		// Add action listener.for the menu button
		menuFileExit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JudokuFrame.this.windowClosed();
			}
		}
		);
		// Add action listener.for the menu button
		menuFileSave.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				saveFileAction(e);
			}
		}
		); 
		// Add action listener.for the menu button
		menuFileOpen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				openFileAction(e);
			}
		}
		); 
		// Add action listener.for the menu button
		menuFileEmpty.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				emptyFileAction(e);
			}
		}
		); 
		menuFile.add(menuFileOpen);
		menuFile.add(menuFileEmpty);
		menuFile.add(menuFileSave);
		menuFile.add(menuFileExit);
		menuBar.add(menuFile);

		setTitle("Judoku");
		setMenuBar(menuBar);
		setSize(new Dimension(460, 500));

		// Add window listener.
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				JudokuFrame.this.windowClosed();
			}
		}
		);
		addMouseListener(this);	// Aggiunge un "mouse listener" 
	}

    private void emptyFileAction(ActionEvent evt)
    {
    	int i, j;
    	for(i=0; i<9; i++)
    		for(j=0; j<9; j++)
    		{
    			theBoard[i][j]	=	0;
    			initBoard[i][j]	=	0;
    		}
    	initialize();
    	repaint();
    }

    private void saveFileAction(ActionEvent evt)
    {
    	int i, j;
    	String s="ciao";
		int retval = mFileChooser.showSaveDialog(this);
		if (retval == JFileChooser.APPROVE_OPTION)
		{
			File f = mFileChooser.getSelectedFile();
			try 
			{
				FileWriter writer = new FileWriter(f);
				for(i=0; i<9; i++)
				{
					for(j=0; j<9; j++)
					{
						writer.write(theBoard[i][j]+48);
					}
					writer.write(13);
				}
				writer.close();
			}
			catch(IOException ioex)
			{
				System.out.println(evt);
				System.exit(1);
			}
			
		}
    	repaint();
    }

    private void openFileAction(ActionEvent evt)
    {                                         
        int retval = mFileChooser.showOpenDialog(this);
        if (retval == JFileChooser.APPROVE_OPTION) 
        {
            File f = mFileChooser.getSelectedFile();
            try
            {
                FileReader reader = new FileReader(f);
				BufferedReader in = new BufferedReader (reader);
				int count	=	0;
				while (true)
				{
	    			String s = in.readLine();
					if (s == null)
	    				break;
					for(int j=0; j<9; j++)
					{
						switch(s.charAt(j))
						{
							case '0':
								theBoard[count][j]=0;
								break;
							case '1':
								theBoard[count][j]=1;
								break;
							case '2':
								theBoard[count][j]=2;
								break;
							case '3':
								theBoard[count][j]=3;
								break;
							case '4':
								theBoard[count][j]=4;
								break;
							case '5':
								theBoard[count][j]=5;
								break;
							case '6':
								theBoard[count][j]=6;
								break;
							case '7':
								theBoard[count][j]=7;
								break;
							case '8':
								theBoard[count][j]=8;
								break;
							case '9':
								theBoard[count][j]=9;
								break;
							default:
								break;
						}
					}
					count++;
				}
            }
            catch (IOException ioex)
            {
                System.exit(1);
            }
        }
        initialize();
        repaint();
    }       
	public void initialize()
	{
		theClick			=	0;
		globalxPos			=	0;
		globalyPos			=	0;
		win					=	0;
		for(int i=0; i<9; i++)
		{
			for(int j=0; j<9; j++)
			{
				initBoard[i][j]=theBoard[i][j];
			}
		}
	}

    public void readFromFile (String filename) throws FileNotFoundException, IOException
    {
		FileReader fileReader = new FileReader (filename);
		BufferedReader in = new BufferedReader (fileReader);
		int count	=	0;
		while (true)
		{
	    	String s = in.readLine();
	    	if (s == null)
	    		break;
			for(int j=0; j<9; j++)
			{
				switch(s.charAt(j))
				{
					case '0':
						theBoard[count][j]=0;
						break;
					case '1':
						theBoard[count][j]=1;
						break;
					case '2':
						theBoard[count][j]=2;
						break;
					case '3':
						theBoard[count][j]=3;
						break;
					case '4':
						theBoard[count][j]=4;
						break;
					case '5':
						theBoard[count][j]=5;
						break;
					case '6':
						theBoard[count][j]=6;
						break;
					case '7':
						theBoard[count][j]=7;
						break;
					case '8':
						theBoard[count][j]=8;
						break;
					case '9':
						theBoard[count][j]=9;
						break;
					default:
						break;
				}
			}
			count++;
		}
		repaint();
    }
	/**
	 * Shutdown procedure when run as an application.
	 */
	protected void windowClosed()
	{
		// Exit application.
		System.exit(0);
	}

	public void mouseClicked(MouseEvent e){}
	//	Questi quattro metodi non vengono utilizzati ma devono essere
	//	definiti comunque.
	public void mousePressed(MouseEvent e)
	{
		Point mouse	=	new Point();
		mouse		=	e.getPoint();
		
		if(win==1)
			return;
		if(theClick==0)	//	Check for board position
		{
			int xPos, yPos;
			xPos	=	mouse.x-xOff;
			yPos	=	mouse.y-yOff;
			xPos	=	xPos/40;
			yPos	=	yPos/40;
			if(xPos<9&&xPos>=0&&yPos<9&&yPos>=0&&initBoard[yPos][xPos]==0)
			{
				theClick	= 1;
				globalxPos	=	xPos;
				globalyPos	=	yPos;
				repaint();
			}
		}
		else	//	Check for KeyPad position
		{
			int result=0;
			int xPos, yPos;
			xPos	=	mouse.x-xOff+10-globalxPos*40;
			yPos	=	mouse.y-yOff+10-globalyPos*40;
			xPos	=	xPos/20;
			yPos	=	yPos/20;
			if(xPos<3&&xPos>=0&&yPos<3&&yPos>=0)
			{
				result=xPos*3;
				result=result+yPos+1;
				theBoard[globalyPos][globalxPos]=result;
				
			}
			else
				theBoard[globalyPos][globalxPos]=0;
			win	=	checkForWin();
			theClick=0;
			repaint();

		}
	}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}

    public void paint(Graphics g)
	{
		drawBoard(g);
	}

	public void update(Graphics g)
	{
		drawBoard(g);
	}

	public void drawBoard(Graphics g)
	{
		Font theFont, theWinFont;
		int i, j;
		g.setColor(Color.white);
		g.fillRect(0,0,460,103);
		g.fillRect(0,463,460,500);
		g.fillRect(0,0,45,500);
		g.fillRect(405,0,460,500);
		theFont	   =	new Font("Courier", Font.BOLD, 24);
		theWinFont =	new Font("Times", Font.BOLD, 72);
		g.setFont(theFont);
		for(i=0; i<9; i++)
		for(j=0; j<9; j++)
		{
			g.setColor(Color.lightGray);
			g.fill3DRect(i*40+xOff, j*40+yOff, 40, 40, true);
			if(theBoard[j][i]>0&&theBoard[j][i]<=9)
			{
				String s	=	"";
				if(initBoard[j][i]>0&&initBoard[j][i]<=9)
				{
					g.setColor(Color.black);
				}
				else
					g.setColor(Color.red);
				g.drawString(s+theBoard[j][i], i*40+xOff+12, j*40+yOff+27);
				g.drawString(s+theBoard[j][i], i*40+xOff+12, j*40+yOff+27);
			}
		}
		g.setColor(Color.black);
		g.drawLine(45, 103, 45, 463);
		g.drawLine(165, 103, 165, 463);
		g.drawLine(285, 103, 285, 463);
		g.drawLine(405, 103, 405, 463);
		g.drawLine(45, 103, 405, 103);
		g.drawLine(45, 223, 405, 223);
		g.drawLine(45, 343, 405, 343);
		g.drawLine(45, 463, 405, 463);
		if(theClick==1)
		{
			drawKeyPad(g, globalxPos, globalyPos);
		}
		if(win==1)
		{
			g.setColor(Color.blue);
			g.setFont(theWinFont);
			g.drawString("Hai Vinto", 100, 100);
			return;
		}
	}
	
	public void drawKeyPad(Graphics g, int x, int y)
	{
		Font theFont;
		theFont	=	new Font("Courier", Font.BOLD, 14);
		g.setFont(theFont);
		String	s	=	"";
		int i, j, counter=1;
		for(i=0; i<3; i++)
		for(j=0; j<3; j++)
		{
			g.setColor(Color.yellow);
//			g.fill3DRect(i*20+xOff-10+i+x*40, j*20+yOff-10+j+y*40, 20, 20, true);
			g.fillOval(i*20+xOff-10+i+x*40, j*20+yOff-10+j+y*40, 17, 17);
			g.setColor(Color.black);
			g.drawOval(i*20+xOff-10+i+x*40, j*20+yOff-10+j+y*40, 17, 17);
			g.drawString(s+counter, i*20+xOff-10+i+x*40+6, j*20+yOff-10+j+y*40+15);
			counter++;
		}
	}

	public static String readerToString(Reader is)throws IOException
	{
		StringBuffer sb = new StringBuffer();
		char b[]		= new char[40];
		int	n;
		while((n = is.read(b))>0)
		{
			sb.append(b, 0, n);
		}
		return sb.toString();
	}

	public int checkForWin()
	{
		int i, j, k, l, m;

		//	Check horizontal lines
		for(i=0; i<9; i++)
		{
			for(j=1; j<10; j++)
			{
				if(j!=theBoard[i][0]&&j!=theBoard[i][1]&&j!=theBoard[i][2]&&j!=theBoard[i][3]&&
				j!=theBoard[i][4]&&j!=theBoard[i][5]&&j!=theBoard[i][6]&&j!=theBoard[i][7]&&
				j!=theBoard[i][8])
					return(0);
			}
		}

		//	Check vertical lines
		for(i=0; i<9; i++)
		{
			for(j=1; j<10; j++)
			{
				if(j!=theBoard[0][i]&&j!=theBoard[1][i]&&j!=theBoard[2][i]&&j!=theBoard[3][i]&&
				j!=theBoard[4][i]&&j!=theBoard[5][i]&&j!=theBoard[6][i]&&j!=theBoard[7][i]&&
				j!=theBoard[8][i])
					return(0);
			}
		}

		//	Check squares
		for(l=0; l<3; l++)
		{
			int sum=0;
			for(i=0; i<3; i++)
			{
				for(j=0+l*3; j<3+l*3; j++)
				{
					sum+=theBoard[i][j];
				}
			}
			if(sum!=45)
				return(0);
			
			sum=0;
			for(i=3; i<6; i++)
			{
				for(j=0+l*3; j<3+l*3; j++)
				{
					sum+=theBoard[i][j];
				}
			}
			if(sum!=45)
				return(0);
			
			sum=0;
			for(i=6; i<9; i++)
			{
				for(j=0+l*3; j<3+l*3; j++)
				{
					sum+=theBoard[i][j];
				}
			}
			if(sum!=45)
				return(0);
		}
		return(1);
	}
}
