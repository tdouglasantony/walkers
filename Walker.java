import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Walker extends Thread {
  private int id;
  private int xposition;
  private int yposition;
  private int squaresVisited; // use bit comparison?
  private Color col;
  private Graphics gr;

  Walker(int x, int y, Graphics g, Color c){
    xposition = x;
    yposition = y;
    col = c;
    gr = g;
  }

  public void run(){
    gr.setColor(col);
    gr.fillRect(50*xposition, 50*yposition, 50, 50);
  }
  
  // Function to read 
}
