//Thor Keller
//2016-05-17
//Draws everything to the screen
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class RiskPanel extends JPanel
{
   ArrayList<Clickable> drawables = new ArrayList<Clickable>();
   public RiskPanel()
   {
      Map map = new Map();
      map.firstLoad();
      drawables.add(map);
      drawables.add(Deck.getDeck());
      drawables.add(Button.getButton());
      for(int i = 0; i < 5; i++)
         drawables.add(Die.getDie(i));
      for(int i = 0; i < 44; i++)
         drawables.add(Deck.getGameDeckCard(i));
      drawables.add(new TerritoryMethod());
      drawables.add(new NumPlayers());
      //put things in bottom to top
   }
   //------------------------------------
   //Draws everything onto the screen
   //@Param the graphics object to draw on
   //POSTCONDITION: All objects that should be drawn on the screen will be
   //------------------------------------
   public void paint(Graphics g)
   {
      g.setColor(Color.WHITE);
      g.fillRect(0,0,2000,1000);
      for(int i = drawables.size()-1; i >=0; i--)
      {
         drawables.get(i).click();
      }
      for(int i = 0; i < drawables.size(); i++)
      {
         drawables.get(i).draw(g);
      }
      g.setColor(Color.BLACK);
      if(Player.getCurrPhase()==0)
         g.drawString("Pick Cards", 5, 10);
      else if(Player.getCurrPhase()==1)
         g.drawString("Place Units: "+ Player.getUnitsToAdd(), 5, 10);
      else if(Player.getCurrPhase()==2)
         g.drawString("Attack", 5, 10);
      else if(Player.getCurrPhase()==3)
         g.drawString("Fortify", 5, 10);
   }
}
