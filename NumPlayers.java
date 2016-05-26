//Thor Keller
//2016-05-22
//Lets the user choose the number of players
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;
public class NumPlayers implements Clickable
{
   private static boolean done = false;
   private static BufferedImage display = loadBufferedImage();
   //------------------------------------------------------
   //Sets the number of players and creates all the players.
   //------------------------------------------------------
   public void click()
   {
      if(!done&&Mouse.mouseClicked())
      {
         int numplayers = 3;
         numplayers+=Mouse.getX()/512;
         numplayers+=2*(Mouse.getY()/387);   
         done = true;
         Color[] colors = {new Color(200,50,50),new Color(50,200,50),new Color(50,50,200), new Color(200,200,50), new Color(200,50,200), new Color(50,200,200)};
         for(int i = 0; i < numplayers; i++)
            new Player(colors[i]);
      }
   }
   //------------------------------------------------------
   //Draws it to the screen.
   //@param Graphics  object to draw Clickable object
   //------------------------------------------------------
   public void draw(Graphics g)
   {
      if(!done)
      {
         g.drawImage(display, 0, 0, null);
      }
   }
   //------------------------------------------------------
   //Loads the number of player screen
   //@return Image to draw
   //------------------------------------------------------
   public static BufferedImage loadBufferedImage()
   {
      try {
         BufferedImage image = ImageIO.read(new File("NumPlayers.png"));
         return(image);
      } 
      catch (Exception e) {
      }
      return(null);
   }
}