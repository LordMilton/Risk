//Bradley Dufour
//2016-05-14
//Represents the deck, hands, and discard
import java.util.*;
import java.awt.*;

public class Deck implements Clickable
{
   private static Deck discard = new Deck();
   private static Deck gameDeck = new Deck();
   private Stack<Card> deck;
   public Deck()
   {
      deck = new Stack<Card>();
   }
   public Deck(Stack<Card> cards)
   {
      deck = cards;
   }
   //-------------------------------------------
   //shuffle Resets deck as all cards in discard Deck
   //@param discard Stack of cards from discard Deck
   //-------------------------------------------
   public static void shuffle()
   {
      gameDeck = discard;
      discard = new Deck();
   }
   //-------------------------------------------
   //addCard Adds a certain Card object to deck
   //@param card Card object to add
   //-------------------------------------------
   public void addCard(Card card)
   {
      deck.push(card);
   }
   //-------------------------------------------
   //get Accesses the Card object at a specified index in deck
   //@param index Index of Card object to be retrieved
   //@return Card at the specified index in deck
   //-------------------------------------------
   public Card get(int index)
   {
      return(deck.get(index));
   }
   //-------------------------------------------------
   //drawCard Accesses a random card from the Deck and removes that card from the Deck
   //@return drawnCard Card drawn and removed from the Deck
   //-------------------------------------------------
   private Card drawCard()
   {
      int random = (int)(Math.random()*deck.size());
      if(!deck.isEmpty())
         return deck.remove(random);
      return null;
   }
   //-------------------------------------------
   //Refer to click() from Clickable
   //-------------------------------------------
   public void click()
   {
      if(Mouse.getX()>10&&Mouse.getX()<90)
      {
         if(Mouse.getY()>200&&Mouse.getY()<350&&Mouse.mouseClicked())
            Player.getCurrentPlayer().getHand().addCard(gameDeck.drawCard());
         else if(Mouse.getY()>400&&Mouse.getY()<550&&Mouse.mouseClicked())
            Player.getCurrentPlayer().viewHand();
      }
   }
   //-------------------------------------------
   //Refer to draw(Graphics g) from Clickable
   //-------------------------------------------
   public void draw(Graphics g)
   {
      g.setColor(Color.GRAY);
      g.fillRect(10, 200, 80, 150);
      g.setColor(Color.BLACK);
      g.drawString("Deck",30,270);
      try
      {
      g.setColor(Player.getCurrentPlayer().getColor());
      g.fillRect(10, 400, 80, 150);
      g.setColor(Color.BLACK);
      g.drawString("Hand",30,470);
      }
      catch(Exception e)
      {}
   }
   //-------------------------------------------
   //Sets the game deck.
   //@param the game deck
   //-------------------------------------------
   public static void setDeck(Deck gamedeck)
   {
      gameDeck = gamedeck;
   }
   //-------------------------------------------
   //Gets the game deck.
   //@return the game deck
   //-------------------------------------------
   public static Deck getDeck()
   {
      return(gameDeck);
   }
   //-------------------------------------------
   //Used to add the cards to the panel
   //@param the index of the card you want
   //@return the card at that position
   //-------------------------------------------
   public static Card getGameDeckCard(int index)
   {
      return(gameDeck.get(index));
   }
   //-------------------------------------------
   //Used to display the cards on the panel
   //@param whether the deck cards will be visible or invisible
   //-------------------------------------------
   public void setVisible(boolean visible)
   {
      for(int i = 0; i < deck.size(); i++)
         if(visible)
         {
            deck.get(i).setVisible((i%5)*180+110, i/5 * 265);
         }
         else
         {
            deck.get(i).setInvisible();
         }
   }
}