import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


/** This program is an example of a very basic application.
* It draws three squares on the screen; two of them
* are filled with selected colors.
*/
public class Geometric3 extends JPanel{
  public static void main(String[] args) {
      drawWindow();
  }
  
  public static void drawWindow(){
	  Geometric3 jp = new Geometric3();
      jp.setPreferredSize(new Dimension(700,700));
      JFrame frame = new JFrame();
      

      frame.setContentPane (jp);
      frame.setSize(700, 700);
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  
	  }

  public void paintComponent (Graphics g)
  {
      Walker thread1,thread2,thread3,thread4;
      super.paintComponent (g);
      g.setColor(Color.black);
      for (int i = 0; i < 10; i++)
      {
        for (int j = 0; j< 10; j++)
        {
          g.drawRect(50 + 50*i, 50 + 50*j, 50, 50);
        }
      }
      thread1 = new Walker(0,0, g, Color.BLUE);
      thread2 = new Walker(9, 0, g, Color.YELLOW);
      thread3 = new Walker(0,9, g, Color.RED);
      thread4 = new Walker(9,9, g, Color.GREEN);
      thread1.start();
      thread2.start();
      thread3.start();
      thread4.start();

      try{
  		thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
  		}
  		catch (InterruptedException e){}
  }
}
