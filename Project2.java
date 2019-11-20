import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Project2 extends JPanel{
    private static final long serialVersionUID = 1L;
    static int GRIDSIZE = 10;
    public static void main(String[] args) {
        Grid grd = new Grid();
        Walker walker0 = new Walker(0, grd);
        Walker walker1 = new Walker(1, grd);
        Walker walker2 = new Walker(2, grd);
        Walker walker3 = new Walker(3, grd);

        walker0.setOpponents(walker1, walker2, walker3);
        walker1.setOpponents(walker0, walker2, walker3);
        walker2.setOpponents(walker1, walker0, walker3);
        walker3.setOpponents(walker1, walker2, walker0);

        walker0.start();
        walker1.start();
        walker2.start();
        walker3.start();

        try{
            walker0.join();
            walker1.join();
            walker2.join();
            walker3.join();
        }
        catch (InterruptedException e) {}
    }

    public static class Grid extends JPanel{
        private boolean phase2;
        private int[][] squares = new int[GRIDSIZE][GRIDSIZE];
        private Color currentColor = Color.BLACK;
        private int currentXPosition;
        private int currentYPosition;
        private int currentID;
        public Grid(){
            JFrame frame = new JFrame();
            this.setPreferredSize(new Dimension(700,700));
            frame.setContentPane (this);
            frame.setSize(700, 700);
            frame.setVisible(true);    
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            phase2 = false;
            for (int i = 0; i < GRIDSIZE; i++)
            {
                for (int j = 0; j < GRIDSIZE; j++)
                {
                    squares[i][j] = 0;
                }
            }
        }    

        public void paintComponent (Graphics g)
        {
            super.paintComponent (g);
            System.out.println("In paintComponent with ID " + currentID);
            g.setColor(currentColor);
            for (int i = 0; i < 10; i++)
            {
                for (int j = 0; j< 10; j++)
                {
                g.drawRect(50 + 50*i, 50 + 50*j, 50, 50);
                }
            }
            drawSquare(g,currentXPosition,currentYPosition);
        }
 
        public void drawSquare(Graphics g, int xposition, int yposition){
                System.out.println("Into drawSquare with id " + currentID);
                if (currentID == 0)
                    g.setColor(Color.BLUE);
                if (currentID == 1)
                    g.setColor(Color.GREEN);
                if (currentID == 2)
                    g.setColor(Color.YELLOW);
                if (currentID == 3)
                    g.setColor(Color.RED);
                g.fillRect(50 + 50*xposition, 50 + 50*yposition,50, 50);
        }

        public synchronized void report(int id, int xposition, int yposition){
            currentXPosition = xposition;
            currentYPosition = yposition;
            currentID = id;
            System.out.println("X Position: " + xposition);
            System.out.println("Y Position: " + yposition);
            System.out.println("In report with id " + id);
            this.revalidate();
            this.repaint();
        }
    }

    public static class Walker extends Thread{
        private int id;
        private int xposition;
        private int yposition;
        private Grid grd;
        private int score;
        private int[][] visitedSquares = new int[GRIDSIZE][GRIDSIZE];
        private boolean done;
        private Walker[] opponents = new Walker[3];
        public Walker(int iden, Grid g){
            id = iden;
            grd = g;
            done = false;
            score = 1;
            for (int i = 0; i < GRIDSIZE; i++)
            {
                for (int j = 0; j < GRIDSIZE; j++)
                {
                    visitedSquares[i][j] = 0;
                }
            }
            if (id == 0){
                xposition = 0;
                yposition = 0;
            }
            if (id == 1){
                xposition = 0;
                yposition = 9;
            }
            if (id == 2){
                xposition = 9;
                yposition = 0;
            }
            if (id == 3){
                xposition = 9;
                yposition = 9;
            }
        }

        public void setOpponents(Walker w1, Walker w2, Walker w3)
        {
            opponents[0] = w1;
            opponents[1] = w2;
            opponents[2] = w3;
        }

        public void randomMove(){
            double a = Math.random();
            double b = Math.random();
            if (a <= 0.5){
                if ((0 < xposition) && (xposition < 9)){
                    if (b <= 0.5){
                        xposition++;
                    }
                    else{
                        xposition--;
                    }
                }
                else if ( xposition == 0){
                    xposition++;
                }
                else{
                    xposition--;
                }
            }
            else {
                if ((0 < yposition) && (yposition < 9)){
                    if (b <= 0.5){
                        yposition++;
                    }
                    else{
                        yposition--;
                    }
                }
                else if ( yposition == 0){
                    yposition++;
                }
                else{
                    yposition--;
                }
            }
        }

        public void run() {
            System.out.println("Thread " + id + " is running with xposition " + xposition + " and y position " + yposition);
            while(!done)
            {
                visitedSquares[xposition][yposition] = 1;
                grd.report(id, xposition, yposition);
                this.randomMove();
                if (visitedSquares[xposition][yposition] == 0)
                    score++;
                System.out.println("Thread " + id + " has " + score + " visited squares.");
                try{
                    Thread.sleep(Math.round(500*Math.random()));
                }
                catch (InterruptedException e){
                    System.out.println(e);
                }
            }
            }
        }
    
}