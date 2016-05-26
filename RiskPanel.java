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
   }
}
