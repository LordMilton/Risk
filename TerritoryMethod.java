//Thor Keller
//2016-05-22
//Lets the user choose whether the territories are randomly assigned or chosen.
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;
public class TerritoryMethod implements Clickable
{
   private static boolean done = false;
   private static BufferedImage display = loadBufferedImage();
   private int tally = 42;
   //stores the total number of units based on the number of players in a game.
   private static int[] numbers = {105,120,125,120};
   //------------------------------------------------------
   //Sets the number of players and creates all the players.
   //------------------------------------------------------
   public void click()
   {
      if(!done&&Mouse.mouseClicked())
      {
         done = true;
         if(Mouse.getX()>512)
         {
            tally = 0;
         }
         else
         {
            Map.randomizeTerritories();
         }
      }
      if(Player.numPlayers()>0)
         if(tally<numbers[Player.numPlayers()-3])
         {
            if(Map.getSelectedTerritory()!=null)
               if(tally<42&&Map.getSelectedTerritory().getOwner()==null)
               {
                  Map.getSelectedTerritory().setOwner(Player.getPlayer(tally%Player.numPlayers()));
                  Map.miniUpdate(Map.getSelectedTerritory());
                  tally++;
               }
               else if(tally >= 42 && Map.getSelectedTerritory().getOwner()==Player.getPlayer(tally%Player.numPlayers()))
               {
                  Map.getSelectedTerritory().addUnits(1);
                  tally++;
                  if(tally==numbers[Player.numPlayers()-3])
                     Player.incrementCurrPhase();
               }
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
      else if(tally<numbers[Player.numPlayers()-3])
      {
         g.setColor(Color.BLACK);
         g.drawString("Next Player:",20,50);
         g.setColor(Player.getPlayer(tally%Player.numPlayers()).getColor());
         g.fillOval(20,60,50,50);
      }
   }
   //------------------------------------------------------
   //Loads the number of player screen
   //@return Image to draw
   //------------------------------------------------------
   public static BufferedImage loadBufferedImage()
   {
      try {
         BufferedImage image = ImageIO.read(new File("TerritoryMethod.png"));
         return(image);
      } 
      catch (Exception e) {
      }
      return(null);
   }
}