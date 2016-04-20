/**
 * @(#)Master1.java
 *
 * Sample Applet application
 *
 * @author
 * @version 1.00 05/04/29
 */
import java.awt.*;
import java.awt.event.*;

/**
 * This class implements a window used to launch a new game, using three
 * differrent levels of difficulty
 *
 * @author ldandrea
 */
public class MasterMindFrame extends Frame implements MouseListener {

    private int currentPosition   = 0;
    private int turnNumber          = 0;
    private int showSecret          = 0;
    private Pin[][] thePin        = new Pin[5][16];
    private Pin[] secretPin       = new Pin[5];
    private Pin[][] scorePin      = new Pin[5][16];

    private static final Color PALETTE[]
            = {
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

    private static final int HOLES  = 5,
            OFFSET                  = 54,
            MAX_NUMBER_OF_TURNS     = 16;

    private int code[]                   = new int[HOLES]; // Il codice da ricercare.

    /**
     *
     * @param frameWidth  is the width of the frame
     * @param frameHeight is the height of the frame
     * @param depth       is the number of colors used for the new game
     */
    public MasterMindFrame(int frameWidth, int frameHeight, int depth) {
        int i;
        int j;

        setTitle("Mastermind");            // set the frame title
        setSize(frameWidth, frameHeight);  // set the frame size
        setResizable(false);               // user can't resize the frame
        for (i = 0; i < HOLES; i++) {
            secretPin[i] = new Pin(12, 0);
        }
        for (i = 0; i < HOLES; i++) {
            secretPin[i].setPosition(30 + i * 40, 560);
        }

        for (j = 0; j < MAX_NUMBER_OF_TURNS; j++) {
            for (i = 0; i < HOLES; i++) {
                thePin[i][j] = new Pin(12, 0);
            }
        }
        for (j = 0; j < MAX_NUMBER_OF_TURNS; j++) {
            for (i = 0; i < HOLES; i++) {
                thePin[i][j].setPosition(30 + i * 40, 50 + j * 30);
            }
        }
        for (j = 0; j < MAX_NUMBER_OF_TURNS; j++) {
            for (i = 0; i < HOLES; i++) {
                scorePin[i][j] = new Pin(5, 0);
            }
        }
        for (j = 0; j < MAX_NUMBER_OF_TURNS; j++) {
            for (i = 0; i < HOLES; i++) {
                scorePin[i][j].setPosition(220 + i * 20, 50 + j * 30);
            }
        }

        // Random code pins.
        randomCode(depth);
        for (i = 0; i < HOLES; i++) {
            secretPin[i].setColor(code[i]);
        }

        // Listen to the windowClosing event
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        // Adds a "mouse listener" to catch the mouse events
        addMouseListener(this);

        // Shows the frame
        setVisible(true);
    }

    //	Questo metodo viene invocato automaticamente dalla JVM.
    public void paint(Graphics g) {
        // Draws the board
        drawBoard(g);
        // Draws the color selection area
        drawColors(g);
        // Draws pins of the board
        for (int j = 0; j < 16; j++) {
            for (int i = 0; i < 5; i++) {
                thePin[i][j].paint(g);
            }
        }
        // Draws the score pins
        for (int j = 0; j < 16; j++) {
            for (int i = 0; i < 5; i++) {
                scorePin[i][j].paint(g);
            }
        }
	// Draws the secret code pins, if the user asked for it.
        if (showSecret == 1) {
            for (int i = 0; i < 5; i++) {
                secretPin[i].paint(g);
            }
        }
    }

    /**
     * Draws the board
     * @param g
     */
    public void drawBoard(Graphics g) {
        // Draws the background
        g.setColor(new Color(51, 51, 102));
        g.fillRoundRect(4, 27, 264 + OFFSET, 559, 15, 15);

        // Draws the secret code area
        g.setColor(new Color(255, 204, 0));
        g.fill3DRect(10, 533, 197, 53, true);
        g.setFont(new Font("SansSerif", Font.BOLD, 26));
        g.setColor(Color.yellow);
        g.drawString("SECRET CODE", 15, 569);

        //Draws the background of the area used to select the color
        g.setColor(new Color(127, 151, 175));
        g.fill3DRect(269 + OFFSET, 27, 50, 459, true);

        // Draws the "go" button
        g.setColor(Color.green);
        g.fillOval(278 + OFFSET - 6, 436, 40, 40);
        g.setColor(Color.black);
        g.drawOval(278 + OFFSET - 6, 436, 40, 40);
        g.setColor(Color.green.darker());
        g.drawString("Go", 280 - 6 + OFFSET, 466);
    }

    //	Draws the button to select a color
    public void drawColors(Graphics g) {
        for (int i = 0; i < 8; i++) {
            g.setColor(PALETTE[i + 1]);
            g.fill3DRect(328, 38 + i * 45, 40, 30, true);
        }
    }

    /**
     * Called as soon as the user press the mouse button
     * @param e
     */
    public void mousePressed(MouseEvent e) {
        // Current mouse position
        Point mouse = new Point();

        // Get the mouse coordinates
        mouse = e.getPoint();

	// If the user clicked onto the secret code area, then the secret code
        // must be shown
        if ((mouse.y >= 533) && (mouse.x <= 251)) // is the 'SECRET CODE' area?
        {
            if (showSecret == 0) {
                showSecret = 1;
                repaint();
            }
        }
        if ((mouse.x >= 328) && (mouse.x <= 368)) {
            if (showSecret == 0) // If the secret code has been shown, there aren't
                // any reason to continue the game
            {
                if ((mouse.y >= 38) && (mouse.y <= 68)) // First color
                {
                    thePin[currentPosition][turnNumber].setColor(1);
                    currentPosition++;
                    repaint();
                }
                if ((mouse.y >= 83) && (mouse.y <= 113)) // Second color
                {
                    thePin[currentPosition][turnNumber].setColor(2);
                    currentPosition++;
                    repaint();
                }
                if ((mouse.y >= 128) && (mouse.y <= 158)) // Third color
                {
                    thePin[currentPosition][turnNumber].setColor(3);
                    currentPosition++;
                    repaint();
                }
                if ((mouse.y >= 173) && (mouse.y <= 203)) // Fourth color
                {
                    thePin[currentPosition][turnNumber].setColor(4);
                    currentPosition++;
                    repaint();
                }
                if ((mouse.y >= 218) && (mouse.y <= 248)) // Fifth color
                {
                    thePin[currentPosition][turnNumber].setColor(5);
                    currentPosition++;
                    repaint();
                }
                if ((mouse.y >= 263) && (mouse.y <= 293)) // Sixth color
                {
                    thePin[currentPosition][turnNumber].setColor(6);
                    currentPosition++;
                    repaint();
                }
                if ((mouse.y >= 308) && (mouse.y <= 338)) // Seventh color
                {
                    thePin[currentPosition][turnNumber].setColor(7);
                    currentPosition++;
                    repaint();
                }
                if ((mouse.y >= 353) && (mouse.y <= 383)) // Eighth color
                {
                    thePin[currentPosition][turnNumber].setColor(8);
                    currentPosition++;
                    repaint();
                }
                if ((mouse.y >= 436) && (mouse.y <= 476)) {
                    boolean app = false;
                    for (int i = 0; i < 5; i++) {
                        if (thePin[i][turnNumber].getColor() == 0) {
                            app = true;
                        }
                    }
                    if (app == false) {
                        Go(turnNumber);
                        repaint();
                        turnNumber = turnNumber + 1;
                        currentPosition = 0;
                    }
                }
                if (currentPosition > 4) {
                    currentPosition = 0;
                }
            }
        }
    }

    // The following four methods have to be defined, also if the they are
    // empty because the class is not abstract. (See MouseListener)
    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    /**
     * Random secret code generation, range [1..8]
     */
    void randomCode() {
        for (int x = 0; x < HOLES; x++) {
            code[x] = 1 + (int) (Math.random() * 8);
        }
    }

    /**
     * Random secret code generation, range [1..cNumber]
     */
    void randomCode(int cNumber) {
        for (int x = 0; x < HOLES; x++) {
            code[x] = 1 + (int) (Math.random() * cNumber);
        }
    }

    /**
     * Computes the score at turn currentTurn
     * @param currentTurn the turn to use to compute the score
     */
    public void Go(int currentTurn) {
        int[] auxiliaryPins = new int[HOLES];
        int[] auxiliaryCode = new int[HOLES];
        int pegCount = 0, pico = 0;
        int x;
        int y = 0;

        // Makes a clone of the current raw, so we can overwrite it.
        for (x = 0; x < HOLES; x++) {
            auxiliaryPins[x] = thePin[x][currentTurn].getColor();
            auxiliaryCode[x] = code[x];
        }

        // Right color in the right position, means a red pin
        // into the score area
        for (x = 0; x < HOLES; x++) {
            if (auxiliaryPins[x] == auxiliaryCode[x]) {
                // Update Right color in the right position
                scorePin[pegCount][currentTurn].setColor(1);
                pegCount++;
                pico++;
                auxiliaryPins[x] = 98; // avoid new comparison
                auxiliaryCode[x] = 99;
            }
        }
        // Right color in the wrong position, means a yellow pin
        for (x = 0; x < HOLES; x++) {
            for (y = 0; y < HOLES; y++) {
                if (auxiliaryPins[x] == auxiliaryCode[y]) {
                    // Update Right color in the wrong position
                    scorePin[pegCount][currentTurn].setColor(2);
                    pegCount++;
                    auxiliaryPins[x] = 98; // avoid new comparison
                    auxiliaryCode[y] = 99;
                    y = HOLES;             // force exit from loop
                }
            }
        }
        if (pico == HOLES) // check for win
        {
            showSecret = 1;
            repaint();
            this.getToolkit().beep();
            MessageBox message = new MessageBox(this, "Mastermind",
                    "You won. Great!!!");
            dispose();
        } else if (currentTurn >= 15) // check for lose
        {
            showSecret = 1;
            repaint();
            this.getToolkit().beep();
            MessageBox message = new MessageBox(this, "Mastermind",
                    "You lose. Take a look at the secret code.");
            dispose();
        }
    }
}

/**
 * Dialog box to send message to the user
 * @author ldandrea
 */
class MessageBox extends Dialog implements ActionListener {

    private Button okButton;

    public MessageBox(Frame f, String title, String message) {

        super(f, title, true);

        // Sets the background color
        setBackground(new Color(127, 151, 175));

        // the box cannot be resized
        setResizable(false);

        // Add a new label with the text of the message to show
        add(new Label(message), BorderLayout.NORTH);

        // Add a button to close the dialog window
        okButton = new Button("    OK    ");
        okButton.addActionListener(this);
        add(okButton, BorderLayout.EAST);
        pack();

        // Sows the dialog box
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        // Close the dialog box
        dispose();
    }

}

/**
 * Simple Pin class
 * @author ldandrea
 */
class Pin {
    private int color, x, y, radius;
    private static final Color PALETTE[]
            = {
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

    /**
     * Default Pin constructur Pos = (0,0), radius = 0, color = 0
     */
    public Pin() {
        x      = 0;
        y      = 0;
        radius = 0;
        color  = 0;
    }

    /**
     * Pin constructor Pos = (0,0)
     * @param radius the radius for the pin
     * @param color  the color for the pin
     */
    public Pin(int radius, int color) {
        x = 0;
        y = 0;
        this.radius = radius;
        this.color = color;
    }

    /**
     * Paint method for the pin
     * @param g
     */
    public void paint(Graphics g) {
        int ulX = x - radius;	// x for the top left point.
        int ulY = y - radius;	// y for the top left point.

        // d14 e d34 are used to give a pseudo 3d effect.
        int d14 = radius >> 1;	// diameter/4.
        int d34 = radius + d14;	// three fourth of diameter.
        if (color > 0) {
            g.setColor(PALETTE[color].darker());
            g.fillOval(ulX, ulY, 2 * radius, 2 * radius);
            g.setColor(PALETTE[color]);
            g.fillOval(ulX + d14 - 1, ulY + d14 - 1, d34, d34);
            g.setColor(Color.white);
            g.fillOval(x, y, d14, d14);
        } else {
            g.setColor(Color.lightGray);
            g.drawOval(ulX, ulY, 2 * radius - 1, 2 * radius - 1);
        }
        g.setColor(Color.black);
        g.drawOval(ulX, ulY, 2 * radius, 2 * radius);
    }

    /**
     * Move the pin to the new coordinates
     * @param newX
     * @param newY
     */
    public void setPosition(int newX, int newY) {
        x = newX;
        y = newY;
    }

    /**
     * Change the pin radius with a new value
     * @param newR
     */
    public void setRadius(int newR) {
        radius = newR;
    }

    /**
     * Change the pin color with a new value
     * @param newC
     */
    public void setColor(int newC) {
        color = newC;
    }

    /**
     * Retrieve the current color of the pin
     * @return
     */
    public int getColor() {
        return color;
    }

}
