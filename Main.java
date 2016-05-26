//Thor Keller
//2016-05-17
//Initializes and refreshes the game
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
public class Main
{
   //------------------------------------------------
   //Main method: initializes variable in other classes and creates and updates the panel
   //------------------------------------------------
   public static void main(String... args) throws Exception
   {
      Map.firstLoad();
      JFrame frame = new JFrame("Risk");
      frame.setSize(1000, 800);
      frame.setLocation(0,0);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      RiskPanel panel = new RiskPanel();
      panel.addMouseMotionListener(Mouse.getListener());
      panel.addMouseListener(Mouse.getListener());
      frame.getContentPane().add(panel);
      frame.setVisible(true);
      while(true)
      {
        Thread.sleep(20);
        panel.repaint();
      }
   }
}