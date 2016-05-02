import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

final public class Hanoi {
    JFrame frame;
    DrawPanel drawPanel;
    Tower t1;
    Tower t2 = new Tower(0, "B");
    Tower t3 = new Tower(0, "C");
    private int depth;
    private int animationSpeed;

    public static void main(String[] args) {
        int numbers  =   3;
        int speed    =   1;

        if(args[0] != null)
        {
            numbers = Integer.parseInt(args[0]);
        }
        if(args[1] != null)
        {
            speed = Integer.parseInt(args[1]);
        }
        Hanoi game = new Hanoi(numbers, speed);
    }

    public Hanoi(int depth, int speed)
    {
        t1 = new Tower(depth, "A");
        this.depth = depth;
        this.animationSpeed = speed;
        this.go();
    }

    private void go() {
        frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawPanel = new DrawPanel();
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setSize(500, 300);
        frame.setLocation(375, 55);
        t1.setPosition(10, 200);
        t2.setPosition(160, 200);
        t3.setPosition(320, 200);
        doTowers(this.depth, 'A', 'B', 'C');
    }

    class DrawPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(Color.RED);
            g.fillRect(3, 3, this.getWidth()-6, this.getHeight()-6);
            g.setColor(Color.WHITE);
            g.fillRect(6, 6, this.getWidth()-12, this.getHeight()-12);
            t1.paint(g);
            t2.paint(g);
            t3.paint(g);
        }
    }


    private int removeDisks(char tower) {
        int retval;

        switch (tower) {
            case 'A':
                retval = this.t1.removeDisk();
                break;
            case 'B':
                retval = this.t2.removeDisk();
                break;
            case 'C':
                retval = this.t3.removeDisk();
                break;
            default:
                retval = -1;
                break;
        }
        return(retval);
    }
    private void addDisks(char tower, int val)
    {
        switch (tower) {
            case 'A':
                this.t1.addDisk(val);
                break;
            case 'B':
                this.t2.addDisk(val);
                break;
            case 'C':
                this.t3.addDisk(val);
                break;
            default:
                break;
        }
    }

    public void doTowers(int topN, char start, char auxiliary, char end) {
        int val = 0;
        try
        {
            Thread.sleep(300/this.animationSpeed);
	        if (topN == 1)
	        {
				/* Move disk from start to end */
	            val = this.removeDisks(start);
	            this.addDisks(end, val);
	            frame.repaint();
	        }
	        else
	        {
	            doTowers(topN - 1, start, end, auxiliary);
	            /* Move disk from start to end */
	            val = this.removeDisks(start);
	            this.addDisks(end, val);
	            frame.repaint();
	            doTowers(topN - 1, auxiliary, start, end);
	        }
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(Hanoi.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}


final class Tower {

    private final Color PALETTE[];
    private String TowerName = "";
    private final ArrayList<Integer> disks;
    private int xPos = 0;
    private int yPos = 0;

    public Tower(int numberOfDisks, String towerName) {
        this.PALETTE    =  new Color[]{Color.black, Color.red, Color.yellow, Color.green, Color.orange, Color.pink, Color.magenta, Color.blue, Color.white, Color.lightGray};
        this.TowerName  =  towerName;
        this.disks      =  new ArrayList();

        /* Insert the requested number of disks onto the Tower */
        while (numberOfDisks > 0) {
            this.addDisk(numberOfDisks);
            numberOfDisks--;
        }
        this.setPosition(50, 300);
    }

    public void setPosition(int x, int y) {
        this.xPos  =  x;
        this.yPos  =  y;
    }

    public void addDisk(int discVal) {
        disks.add(discVal);
    }

    public int removeDisk() {
        int lastElem =  disks.size();
        int retVal   =  0;
        if(lastElem>0)
        {
            retVal = disks.get(lastElem-1);
            //System.out.println("lastElem = "+ lastElem);
            disks.remove(lastElem-1);
        }

        return(retVal);
    }

    public void paint(Graphics g) {
        int numberOfDisks = disks.size();

        for (int i = 0; i < numberOfDisks; i++) {
            int diskVal= 0;
            try
            {
                diskVal = disks.get(i);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            int colorIndex = diskVal % 10;
            /* Draws a rounded rect for each disk in the array list */
            g.setColor(PALETTE[colorIndex].darker());

            /* Compute the upper left corner for the rect */
            int ulX, ulY;
            ulX = this.xPos;
            ulY = this.yPos - i * 20;
            /* Compute height of the disc */
            int height;
            height = diskVal * 10;
            g.fillRoundRect(ulX, ulY, height, 10, 3, 3);
            g.setColor(PALETTE[colorIndex]);
            g.fillRoundRect(ulX, ulY, height - 2, 10 - 2, 3, 3);
        }
        g.setColor(PALETTE[0]);
        g.drawRect(this.xPos, this.yPos - 190, 120, 200);
        g.drawString(this.TowerName, this.yPos - 220, this.xPos);

    }
}
