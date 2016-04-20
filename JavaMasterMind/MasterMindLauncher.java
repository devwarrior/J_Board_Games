import java.awt.*;
import java.awt.event.*;

//
//	Questa classe implementa un box di dialogo per inviare
//	messaggi all'utente.
//
public class MasterMindLauncher extends Frame {

    public MasterMindLauncher() {
		//	Costruisce un Box di dialogo.
        //super(f,"MasterMind Launcher" ,true);
        setTitle("MasterMind Launcher");        // set the frame title
        setSize(100, 100);                      // set the frame size
        setResizable(false);                    // user can't resize the frame
        setBackground(new Color(127, 151, 175)); // Sets the background color
        setLayout(new GridLayout(3, 1));
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

		//	Aggiunge una nuova label con il testo del messaggio.
        //add(new Label(message), BorderLayout.NORTH);
        //	Adds the level buttons
        Button easyButton = new Button("    Easy    ");
        Button mediumButton = new Button("   Medium   ");
        Button hardButton = new Button("    Hard    ");
        easyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MasterMindFrame game = new MasterMindFrame(400, 611, 2);
            }
        }
        );

        mediumButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MasterMindFrame game = new MasterMindFrame(400, 611, 4);
            }
        }
        );

        hardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MasterMindFrame game = new MasterMindFrame(400, 611, 8);
            }
        }
        );
        // Aggiunge il pulsante.
        add(easyButton, BorderLayout.EAST);
        add(mediumButton, BorderLayout.WEST);
        add(hardButton, BorderLayout.CENTER);
        pack();

        //	Mostra il box di dialogo.
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        dispose();
    }

}
