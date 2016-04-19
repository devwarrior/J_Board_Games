/**
 * @(#)Master1.java
 *
 * Sample Applet application
 *
 * @author
 * @version 1.00 05/04/29
 */

import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.util.Random;

//
//	La classa Board implementa il disegno della "scacchiera"
//	e il gioco del MasterMind.
//
public class MasterMindFrame extends Frame implements MouseListener
{
	private int posizioneCorrente			=	0;
	private int turno						=	0;
	private int showSecret					=	0;
	private Plug ilChiodino[][]			=	new Plug[5][16];
	private Plug chiodinoSegreto[]		=	new Plug[5];
	private Plug chiodinoPunteggio[][]	=	new Plug[5][16];
	private static final Color PALETTE[] 	=
	{
		Color.black,
		Color.red,
		Color.yellow,
		Color.green,
		Color.orange,
		Color.pink,
		Color.magenta,
		Color.blue,
		Color.white,
		Color.lightGray
	};

	private static final int
			HOLES		=	5,
			OFFSET		=	54,
			TURNIMAX	=	16;

	private int code[]	=	new int[HOLES]; // Il codice da ricercare.

	public MasterMindFrame(int frameWidth, int frameHeight, int depth)
	{
		int i;
		int j;

		setTitle("Mastermind");				// set the frame title
		setSize(frameWidth,frameHeight);	// set the frame size
		setResizable(false);				// user can't resize the frame
		for(i=0; i<HOLES; i++)
			chiodinoSegreto[i]		=	new Plug(12, 0);
		for(i=0; i<HOLES; i++)
			chiodinoSegreto[i].setPosition(30+i*40, 560);

		for(j=0; j<TURNIMAX; j++)
			for(i=0; i<HOLES; i++)
				ilChiodino[i][j]	=	new Plug(12, 0);
		for(j=0; j<TURNIMAX; j++)
			for(i=0; i<HOLES; i++)
				ilChiodino[i][j].setPosition(30+i*40, 50+j*30);
		for(j=0; j<TURNIMAX; j++)
			for(i=0; i<HOLES; i++)
				chiodinoPunteggio[i][j]	=	new Plug(5, 0);
		for(j=0; j<TURNIMAX; j++)
			for(i=0; i<HOLES; i++)
				chiodinoPunteggio[i][j].setPosition(220+i*20, 50+j*30);

		//	Generazione del codice segreto.
		randomCode(depth);
		for(i=0; i<HOLES; i++)
			chiodinoSegreto[i].setColor(code[i]);

		// Le prossime istruzioni permettono di chiudere la finestra
		// quando viene cliccato ilpulsante di chiusura.
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				dispose();
			}
		});
		addMouseListener(this);	// Aggiunge un "mouse listener"
								// per catturare gli eventi relativi al mouse
		setVisible(true);		// Rende visibile la finestra
	}

	//	Questo metodo viene invocato automaticamente dalla JVM.
	public void paint(Graphics g)
	{
		//	Disegna la "scacchiera".
		drawBoard(g);
		//	Disegna i pulsanti per la selezione dei colori.
		drawColors(g);
		//	Disegna i chiodini di gioco.
		for(int j=0; j<16; j++)
			for(int i=0; i<5; i++)
				ilChiodino[i][j].paint(g);
		//	Disegna i chiodini relativi al punteggio.
		for(int j=0; j<16; j++)
			for(int i=0; i<5; i++)
				chiodinoPunteggio[i][j].paint(g);
		//	Se e' stato richiesto di vedere il codice segreto
		//	allora ridisegna i chiodini relativi al codice.
		if(showSecret==1)
			for(int i=0; i<5; i++)
				chiodinoSegreto[i].paint(g);
	}

	//	Disegna il campo di gioco
	public void drawBoard(Graphics g)
	{
		//	Disegna lo sfondo del campo di gioco.
		g.setColor(new Color(51,51,102));
		g.fillRoundRect(4,27,264+OFFSET,559,15,15);
		//	Disegna l'area relativa al codice segreto.
		g.setColor(new Color(255,204,0));
		g.fill3DRect(10,533,197,53,true);
		g.setFont(new Font("SansSerif",Font.BOLD,26));
		g.setColor(Color.yellow);
		g.drawString("SECRET CODE",15,569);
		//	Disegna lo sfondo dell'area per la selezione dei chiodini.
		g.setColor(new Color (127,151,175));
		g.fill3DRect(269+OFFSET,27,50,459,true);
		//	Disegna il pulsante "Go"
		g.setColor(Color.green);
		g.fillOval(278+OFFSET-6,436,40,40);
		g.setColor(Color.black);
		g.drawOval(278+OFFSET-6,436,40,40);
		g.setColor(Color.green.darker());
		g.drawString("Go",280-6+OFFSET,466);
	}

	//	Disegna i pulsanti per la selezione dei colori.
	public void drawColors(Graphics g)
	{
		for(int i=0; i<8; i++)
		{
			g.setColor(PALETTE[i+1]);
			g.fill3DRect(328, 38 + i*45, 40, 30, true);
		}
	}

	// 	Questo metodo viene chiamato dalla JVM appena viene premuto il
	//	pulsante del mouse.
	public void mousePressed(MouseEvent e)
	{
		//	Contiene la posizione corrente del mouse.
		Point mouse	=	new Point();

		//	Ottiene le coordinate del mouse.
		mouse		=	e.getPoint();

		//	Se il mouse e' stato cliccato sull'area del codice segreto
		//	Allora il codice segreto viene mostrato.
		if ((mouse.y >= 533) && (mouse.x <= 251))	// is the 'SECRET CODE' area?
			if(showSecret==0)
			{
				showSecret	=	1;
				repaint();
			}
		if((mouse.x>=328)&&(mouse.x<=368))
		{
			if(showSecret==0)	//	Se il codice segreto e' stato scoperto
								//	e' inutile continuare la partita
			{
				if((mouse.y>=38)&&(mouse.y<=68))	// Colore 1
				{
					ilChiodino[posizioneCorrente][turno].setColor(1);
					posizioneCorrente++;
					repaint();
				}
				if((mouse.y>=83)&&(mouse.y<=113))	// Colore 2
				{
					ilChiodino[posizioneCorrente][turno].setColor(2);
					posizioneCorrente++;
					repaint();
				}
				if((mouse.y>=128)&&(mouse.y<=158))	// Colore 3
				{
					ilChiodino[posizioneCorrente][turno].setColor(3);
					posizioneCorrente++;
					repaint();
				}
				if((mouse.y>=173)&&(mouse.y<=203))	// Colore 4
				{
					ilChiodino[posizioneCorrente][turno].setColor(4);
					posizioneCorrente++;
					repaint();
				}
				if((mouse.y>=218)&&(mouse.y<=248))	// Colore 5
				{
					ilChiodino[posizioneCorrente][turno].setColor(5);
					posizioneCorrente++;
					repaint();
				}
				if((mouse.y>=263)&&(mouse.y<=293))	// Colore 6
				{
					ilChiodino[posizioneCorrente][turno].setColor(6);
					posizioneCorrente++;
					repaint();
				}
				if((mouse.y>=308)&&(mouse.y<=338))	// Colore 7
				{
					ilChiodino[posizioneCorrente][turno].setColor(7);
					posizioneCorrente++;
					repaint();
				}
				if((mouse.y>=353)&&(mouse.y<=383))	// Colore 8
				{
					ilChiodino[posizioneCorrente][turno].setColor(8);
					posizioneCorrente++;
					repaint();
				}
				if((mouse.y>=436)&&(mouse.y<=476))
				{
					boolean app=false;
					for(int i=0; i<5; i++)
					{
						if(ilChiodino[i][turno].getColor()==0)
							app=true;
					}
					if(app==false)
					{
						Go(turno);
						repaint();
						turno = turno+1;
						posizioneCorrente=0;
					}
				}
				if(posizioneCorrente>4)
					posizioneCorrente=0;
			}
		}
	}

	//	Questi quattro metodi non vengono utilizzati ma devono essere
	//	definiti comunque.
	public void mouseClicked(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}

	//	Genera un codice segreto con valori fino a 8.
	void randomCode()
	{
		for(int x=0; x<HOLES; x++)
			code[x]	=	1+(int)(Math.random()*8);
	}

	//	Genera un codice segreto con valori fino a cNumber.
	void randomCode(int cNumber)
	{
		for(int x=0; x<HOLES; x++)
			code[x]	=	1+(int)(Math.random()*cNumber);
	}

	//	Esegue il calcolo del punteggio.
	public void Go(int turno)
	{
		int appoggioChiodini[] = new int[HOLES], appoggioCodice[] = new int[HOLES];
		int pegCount = 0, pico = 0;
		int x;
		int y=0;

		//	Eseguiamo una copia della riga corrente e del codice segreto
		//	in modo da poterli sovrascrivere,
		for(x=0; x<HOLES; x++)
		{
			appoggioChiodini[x] = ilChiodino[x][turno].getColor();
			appoggioCodice[x] = code[x];
		}

		//	Colore esatto nella giusta posizione = un chiodino rosso nello score.
		for(x=0; x<HOLES; x++)
			if (appoggioChiodini[x]==appoggioCodice[x])
			{
				//	Aggiorna colore esatto in posizione esatta.
				chiodinoPunteggio[pegCount][turno].setColor(1);
				pegCount++;
				pico++;
				appoggioChiodini[x]	= 98; // scartiamo il valore in modo che
				appoggioCodice[x]	= 99; // non venga confrontato di nuovo.
			}
		//	Colore giusto nella posizione sbagliata = un chidino giallo nello score.
		for(x=0; x<HOLES; x++)
			for (y=0; y<HOLES; y++)
				if (appoggioChiodini[x]==appoggioCodice[y])
				{
					// Aggiorna colore esatto in posizione sbagliata.
					chiodinoPunteggio[pegCount][turno].setColor(2);
					pegCount++;
					appoggioChiodini[x] = 98; // scartiamo il valore in modo che
					appoggioCodice[y]	= 99; // non venga confrontato di nuovo.
					y = HOLES;				  // esce dal loop interno.
				}
		if(pico==HOLES)		//	Partita Vinta???
		{
			showSecret=1;
			repaint();
			this.getToolkit().beep();	//	Esegue un beep.
			MessageBox message = new MessageBox( this,"Mastermind",
				"Partita Vinta. Complimenti!!!" );
			dispose();
		}
		else
			if(turno>=15)	//	Partita persa???
			{
				showSecret=1;
				repaint();
				this.getToolkit().beep();	//	Esegue un beep.
				MessageBox message = new MessageBox( this,"Mastermind",
					"Partita Persa. Guarda il codice segreto" );
				dispose();
			}
	}
}

//
//	Questa classe implementa un box di dialogo per inviare
//	messaggi all'utente.
//
class MessageBox extends Dialog implements ActionListener
{
	private Button okButton;

	public MessageBox(Frame f,String title,String message)
	{
		//	Costruisce un Box di dialogo.
		super(f,title,true);

		//	Imposta il colore di sfondo.
		setBackground(new Color (127,151,175));

		//	Il box non può essere ridimensionato dall'utente.
		setResizable(false);

		//	Aggiunge una nuova label con il testo del messaggio.
		add(new Label(message), BorderLayout.NORTH);

		//	Aggiunge un pulsante per chiudere il box di dialogo.
		okButton = new Button("    OK    ");
		okButton.addActionListener(this);

		// Aggiunge il pulsante.
		add(okButton, BorderLayout.EAST);
		pack();

		//	Mostra il box di dialogo.
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		//	Chiude la finestra di dialogo.
		dispose();
	}

}



//
//	Questa classe implementa il singolo chiodino.
//
class Plug
{
	private int color, x, y, radius;
	private static final Color PALETTE[] =
	{
		Color.black,
		Color.red,
		Color.yellow,
		Color.green,
		Color.orange,
		Color.pink,
		Color.magenta,
		Color.blue,
		Color.white,
		Color.lightGray
	};

	public Plug()
	{
		x		=	0;
		y		=	0;
		radius	=	0;
		color	=	0;
	}

	public Plug(int radius, int color)
	{
		x			=	0;
		y			=	0;
		this.radius	=	radius;
		this.color	=	color;
	}

	public void paint(Graphics g)
	{
		int ulX	=	x-radius ;	// Ascissa dell'angolo in alto a sinistra.
		int ulY	=	y-radius ;	// Ordinata dell'angolo in alto a sinistra.
		//	d14 e d34 servono per dare la tridimensionalità.
		int d14	=	radius >>1;	// un quarto di diametro.
		int d34	=	radius+d14;	// tre quarti di diametro.
		if(color>0)
		{
			g.setColor(PALETTE[color].darker() );
			g.fillOval(ulX,ulY,2*radius,2*radius );
			g.setColor(PALETTE[color] );
			g.fillOval(ulX+d14-1,ulY+d14-1,d34,d34 );
			g.setColor(Color.white );
			g.fillOval(x,y,d14,d14);
		}
		else
		{
			g.setColor( Color.lightGray );
			g.drawOval( ulX,ulY,2*radius-1,2*radius-1 );
		}
		g.setColor( Color.black );
		g.drawOval( ulX,ulY,2*radius,2*radius );
	}

	//	Questo metodo permette di cambiare posizione al chiodino.
	public void setPosition( int newX,int newY )
	{
		x = newX ;
		y = newY ;
	}

	//	Questo metodo permette di cambiare raggio al chiodino.
	public void setRadius( int newR )
	{
		radius = newR;
	}

	//	Questo metodo permette di cambiare colore al chiodino.
	public void setColor(int newC)
	{
		color = newC;
	}

	//	Questo metodo permette di ottenere il colore attuale del chiodino.
	public int getColor()
	{
		return color;
	}

}
