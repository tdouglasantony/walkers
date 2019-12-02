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

        //walker0.setOpponents(walker1, walker2, walker3);
        //walker1.setOpponents(walker0, walker2, walker3);
        //walker2.setOpponents(walker1, walker0, walker3);
        //walker3.setOpponents(walker1, walker2, walker0);

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
        private int[][] phase2squares = new int[GRIDSIZE][GRIDSIZE];
        private int[][] firstVisited = new int[GRIDSIZE][GRIDSIZE];
        private int[] finishedThreads = new int[4];
        private Color currentColor = Color.BLACK;
        boolean winnerFound;
        private int currentXPosition;
        private int currentYPosition;
        private int winningID;
        private int numFinished;
        private int currentID;
        public Grid(){
            JFrame frame = new JFrame();
            this.setPreferredSize(new Dimension(900,700));
            frame.setContentPane (this);
            frame.setSize(900, 700);
            frame.setVisible(true);    
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            phase2 = false;
            winnerFound = false;
            numFinished = 0;
            for (int i = 0; i < GRIDSIZE; i++)
            {
                for (int j = 0; j < GRIDSIZE; j++)
                {
                    squares[i][j] = 0;
                    phase2squares[i][j] = 0;
                }
            }
            for (int i = 0; i < 4; i++)
            {
                finishedThreads[i] = 0;
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
            if (!phase2)
            {
                drawSquare(g,currentXPosition,currentYPosition);
            }
            else
            {
                drawPhase2(g);
            }
        }
 
        public void drawSquare(Graphics g, int xposition, int yposition){
                System.out.println("Into drawSquare with id " + currentID);
                g.setColor(Color.BLACK);
                if (winnerFound == true)
                {
                    Font font = new Font("Verdana", Font.BOLD, 12);
                    g.setFont(font);
                    if (winningID == 0)
                    {
                        g.setColor(Color.RED);
                        g.drawString("RED WINS", 650, 270);
                    }
                    if (winningID == 1)
                    {
                        g.setColor(Color.GREEN);
                        g.drawString("GREEN WINS", 650, 270);
                    }
                    if (winningID == 2)
                    {
                        g.setColor(Color.BLUE);
                        g.drawString("BLUE WINS", 650, 270);
                    }
                    if (winningID == 3)
                    {
                        g.setColor(Color.YELLOW);
                        g.drawString("YELLOW WINS", 650, 270);
                    }

                }
                g.setColor(Color.RED);
                for (int i = 0; i < GRIDSIZE; i++)
                {
                    for (int j = 0; j < GRIDSIZE; j++)
                    {
                        if(squares[i][j] == 1)
                        {
                            if (firstVisited[i][j] == 0)
                                g.setColor(Color.RED);
                            if (firstVisited[i][j] == 1)
                                g.setColor(Color.GREEN);
                            if (firstVisited[i][j] == 2)
                                g.setColor(Color.BLUE);
                            if (firstVisited[i][j] == 3)
                                g.setColor(Color.YELLOW);
                        g.fillRect(50 + 50*i, 50 + 50*j, 50, 50);
                        }  
                        if(squares[i][j] == 2 || squares[i][j] == 3)
                        {
                            g.setColor(Color.WHITE);
                            g.fillRect(50 + 50*i, 50 + 50*j, 50, 50);
                        }
                        if(squares[i][j] == 4)
                        {
                            g.setColor(Color.BLACK);
                            g.fillRect(50 + 50*i, 50 + 50*j, 50, 50);
                        }

                    }
                }
        }

        public void drawPhase2(Graphics g)
        {
            if (winningID == 0)
            {
                g.setColor(Color.RED);
                g.drawString("RED WINS", 650, 270);
            }
            if (winningID == 1)
            {
                g.setColor(Color.GREEN);
                g.drawString("GREEN WINS", 650, 270);
            }
            if (winningID == 2)
            {
                g.setColor(Color.BLUE);
                g.drawString("BLUE WINS", 650, 270);
            }
            if (winningID == 3)
            {
                g.setColor(Color.YELLOW);
                g.drawString("YELLOW WINS", 650, 270);
            }
            for (int i = 0; i < GRIDSIZE; i++)
            {
                for (int j = 0; j < GRIDSIZE; j++)
                {
                    if (phase2squares[i][j] == 1)
                        g.fillRect(50 + 50*i, 50 + 50*j, 50, 50);
                }
            }
        }

        public synchronized void report(int id, int xposition, int yposition, int alreadyVisited){
            currentXPosition = xposition;
            currentYPosition = yposition;
            currentID = id;
            if (!phase2)
            {
                if (squares[xposition][yposition] == 0)
                {
                    firstVisited[xposition][yposition] = id;
                }
                if (alreadyVisited == 0)
                    squares[xposition][yposition] += 1;
            }
            else
            {
                if (alreadyVisited == 0)
                    phase2squares[xposition][yposition] += 1;
            }
            System.out.println("X Position: " + xposition);
            System.out.println("Y Position: " + yposition);
            System.out.println("In report with id " + id);
            if (winnerFound == true)
                System.out.println("THREAD " + winningID + " HAS ALREADY WON!!");
            System.out.println(numFinished + " THREADS HAVE FINISHED.");
            this.revalidate();
            this.repaint();
        }

        public synchronized void declareFinish(int id)
        {
            if (finishedThreads[id] == 0)
            {
                if (numFinished == 0)
                {
                    winnerFound = true;
                    winningID = id;
                }
                finishedThreads[id] = 1;
                numFinished++;
            }
            if (numFinished == 4)
                phase2 = true;
        }

        public boolean onPhase2()
        {
            return phase2;
        }

        public int getWinnerID()
        {
            return winningID;
        }
    }

    public static class Walker extends Thread{
        private int id;
        private int xposition;
        private int yposition;
        private Grid grd;
        private int score;
        private int[][] visitedSquares = new int[GRIDSIZE][GRIDSIZE];
        private boolean done; // indicates whether phase 1 is done for thread
        private boolean phase1done; //indicates whether ALL threads have finished phase 1
        private boolean done2; // indicates whether phase 2 is done
        private Walker[] opponents = new Walker[3];
        public Walker(int iden, Grid g){
            id = iden;
            grd = g;
            done = false;
            phase1done = false;
            done2 = false;
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
                grd.report(id, xposition, yposition, visitedSquares[xposition][yposition]);
                if (score == 100)
                {
                    grd.declareFinish(id);
                    done = true;
                }
                visitedSquares[xposition][yposition] = 1;
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
            
            //After we are done, wait for phase 2 to start
            while(!phase1done)
            {
                System.out.println("Thread " + id + " reports phase 1 not done");
                phase1done = grd.onPhase2();
                try{
                    Thread.sleep(Math.round(500*Math.random()));
                }
                catch (InterruptedException e){
                    System.out.println(e);
                }
            }

            //If thread won, do the same thing except by itself
            if (grd.getWinnerID() == id)
            { 
                // Re-initialize
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
                score = 1;
                while(!done2)
                {
                    grd.report(id, xposition, yposition, visitedSquares[xposition][yposition]);
                    if (score == 100)
                    {
                        done2 = true;
                    }   
                    visitedSquares[xposition][yposition] = 1;
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
}
