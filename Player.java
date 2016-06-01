//Bradley Dufour
//2016-05-15
//Represents the Players in the game
//Also controls turns
import java.util.*;
import java.awt.*;

public class Player
{
   private static Territory terr1, terr2; // The attacking and defending territories
   private static int unitsToAdd = 0;
   private static int currPhase = 0;
   private static ArrayList<Continent> continents = getContinents();
   private static int currPlayer = 0;
   private static ArrayList<Player> players = new ArrayList<Player>();
   private static int[] cardBonuses = {4,6,8,10,12,15};
   private static int horsePos = 0;
   private static boolean captured = false;
   private LinkedList<Territory> territories;
   private Deck hand;
   private Color color;
   private boolean alive;
   private static boolean handVisible = false;
   public Player(Color color)
   {
      alive = true;
      this.color = color;
      hand = new Deck();
      territories = new LinkedList<Territory>();
      addPlayer();
   }
   //-------------------------------------------
   //Makes an ArrayList of Continents by reading from Continents.txt
   //@return ArrayList of Continents
   //PRECONDITION: Continents.txt exists
   //-------------------------------------------
   private static ArrayList<Continent> getContinents()
   {
      ArrayList<Continent> continents = new ArrayList<Continent>();
      try
      {
         Scanner scan = new Scanner(new java.io.File("Continents.txt"));
         for(int i = 0; i < 6; i++)
         {
            String name = scan.nextLine();
            int bonus = Integer.parseInt(scan.nextLine());
            Continent newContinent = new Continent(name, bonus);
            String territory = scan.nextLine();
            do
            {
               Territory newTerr = new Territory(territory);
               newContinent.addTerritory(newTerr);
               territory = scan.nextLine();
            }
            while(!territory.equals(""));
         }
      }
      catch(Exception e)
      {
         System.err.println("Failed to read from Continents.txt");
      }
      return continents;
   }
   //-------------------------------------------
   //Checks if Player is alive, corrects 'alive' and returns the answer
   //@return true if Player still has territories, false otherwise
   //-------------------------------------------
   private boolean isPlayerDead()
   {
      if(territories.isEmpty())
         alive = false;
      return alive;
   }
   //-------------------------------------------
   //Calculates the number of units the Player should receive
   //@return Number of units the Player should receive
   //-------------------------------------------
   public int getUnits(boolean usedCards)
   {
      int units = 0;
      if(territories.size() < 9)
         units += 3;
      else
         units += territories.size()/3;
      for(Continent continent:continents)
      {
         if(continent.playerHasAll(this))
            units += continent.getBonus();
      }
      if(usedCards)
      {
         if(horsePos <= 6)
            units += cardBonuses[horsePos];
         else
            units += (15 + (horsePos - 6)*5);
         horsePos++;
      }
      return(units);
   }
   //-------------------------------------------
   //Adds a player to the list of players in the game
   //-------------------------------------------
   public void addPlayer()
   {
      players.add(this);
   }
   //-------------------------------------------
   //Returns the number of Players in players (in the game)
   //@return Size of players
   //-------------------------------------------
   public static int numPlayers()
   {
      return players.size();
   }  
   //-------------------------------------------
   //Gets the Player in the game who is at index in players
   //@param index Index to retrieve Player from in players
   //-------------------------------------------
   public static Player getPlayer(int index)
   {
      return players.get(index);
   }
   //-------------------------------------------
   //getColor Accesses color of Player object
   //@return color[Color]
   //-------------------------------------------
   public Color getColor()
   {
      return color;
   }
   //-------------------------------------------
   //getHand Accesses Deck of Player object
   //@return hand[Deck]
   //-------------------------------------------
   public Deck getHand()
   {
      return hand;
   }
   //-------------------------------------------
   //getTerritories Accesses list of territories of Player object
   //@return territories[LinkedList]
   //-------------------------------------------
   public LinkedList<Territory> getTerritories()
   {
      return territories;
   }
   //-------------------------------------------
   //viewHand Draws the cards in the Player object's hands to the play screen
   //POSTCONDITION: The entire hand will be made invisible if it was visible and visible if it was invisible
   //-------------------------------------------
   public void viewHand()
   {
      handVisible = !handVisible;
      hand.setVisible(handVisible);
   }
   //-------------------------------------------
   //Returns the current phase of the Player's turn
   //@return The current phase of the turn
   //-------------------------------------------
   public static int getCurrPhase()
   {
      return currPhase;
   }
    //-------------------------------------------
    // Sets the turn to the next phase
    // POSTCONDITION: The phase will be a valid phase in a valid player's turn
    //-------------------------------------------
   public static void incrementCurrPhase()
   {
      currPhase++;
      if(currPhase > 3)
      {
         currPlayer = (currPlayer+1)%players.size();
         currPhase = 0;
         Button.changeLabel("invisible");
      }
      if(currPhase == 3)
         Button.changeLabel("End");
      captured = false;
      if(terr1!=null)
         terr1.setSelected(false);
      if(terr2!=null)
         terr2.setSelected(false);
      terr1 = null;
      terr2 = null;
      if(currPhase == 1)
      {
         int cardsClicked = 0;
         ArrayList<Card> submitted = new ArrayList<Card>();
         for(Card card:players.get(currPlayer).getHand().getList())
         {
            if(card.isClicked())
            {
               cardsClicked++;
               submitted.add(card);
            }
         }
         boolean validCards = false;
         if(cardsClicked == 3)
         {
            for(Card card:submitted)
            {
               if(card.getType() == 3)
                  validCards = true;
            }
            if(submitted.get(0).getType() == submitted.get(1).getType() && submitted.get(0).getType() == submitted.get(2).getType() || (submitted.get(0).getType() != submitted.get(1).getType() && submitted.get(0).getType() != submitted.get(2).getType() && submitted.get(1).getType() != submitted.get(2).getType()))
            {
               validCards = true;
            }
         }
         if(validCards)
         {
            for(int i = players.get(currPlayer).getHand().getList().size() - 1; i >= 0; i--)
            {
               for(Card used:submitted)
               {
                  if(used == players.get(currPlayer).getHand().get(i))
                     players.get(currPlayer).getHand().drawCard(i);
               }
            }
               
         }
      //TODO: Allow cards to be selected before turn begins [New Phase maybe?]
         unitsToAdd = players.get(currPlayer).getUnits(validCards);
      }
   }
    //-------------------------------------------
    // Resets the selected cities.
    // POSTCONDITION: All units will be properly calculated
    //-------------------------------------------
   public static void resetSelected(int lossesAttacker, int lossesDefender, int selected)
   {
      if(terr1!=null)
         terr1.setSelected(false);
      if(terr2!=null)
         terr2.setSelected(false);
      terr1.removeUnits(lossesAttacker);
      terr2.removeUnits(lossesDefender);
      if(terr2.getUnits()==0)
      {
         terr1.removeUnits(selected);
         terr2.setOwner(terr1.getOwner());
         //check if the player is eliminated
         terr2.addUnits(selected);
         Map.miniUpdate(terr2);
         if(!captured)
         {
            //insert part to draw a card
            captured = true;
         }
      }
      terr1 = null;
      terr2 = null;
   }
   //-------------------------------------------
   //Returns the units the player has left to place
   //@return Units Player still has left to add before beginning next turn phase
   //-------------------------------------------
   public static int getUnitsToAdd()
   {
      return unitsToAdd;
   }
   //-------------------------------------------
   //Processes the turn.
   //POSTCONDITION: The user input will be read and the game will have processed events
   //-------------------------------------------
   public static void processTurn()
   {
      if(currPhase == 0)
      {
         Button.changeLabel("Done");
      }
      if(currPhase == 1)
      {
         if(Map.getSelectedTerritory() != null && Map.getSelectedTerritory().getOwner() == players.get(currPlayer))
         {
            Map.getSelectedTerritory().addUnits(1);
            unitsToAdd--;
         }
         if(unitsToAdd == 0)
         {
            incrementCurrPhase();
            Button.changeLabel("Done");
         }
      }
      else if(currPhase == 2)
      {
         if(terr1 == null && Map.getSelectedTerritory() != null && Map.getSelectedTerritory().getOwner() == players.get(currPlayer)&&Map.getSelectedTerritory().getUnits()>1)
         {
            terr1 = Map.getSelectedTerritory();
            terr1.setSelected(true);
         }
         else if (terr1 != null && Map.getSelectedTerritory() != null && terr2==null && terr1.borders(Map.getSelectedTerritory())&& Map.getSelectedTerritory().getOwner() != players.get(currPlayer))
         {
            terr2 = Map.getSelectedTerritory();
            terr2.setSelected(true);
         }
         else if (terr1 != null && Map.getSelectedTerritory() != null && Map.getSelectedTerritory()==terr1 && terr2 == null)
         {
            terr1.setSelected(false);
            terr1 = null;
         }
         if(terr1 != null && terr2 != null)
         {
            Die.showDice(terr1.getUnits(),terr2.getUnits());
         }
      }
      else if(currPhase == 3)
      {
         if(terr1 == null && Map.getSelectedTerritory() != null && Map.getSelectedTerritory().getOwner() == players.get(currPlayer) && Map.getSelectedTerritory().getUnits()>1)
         {
            terr1 = Map.getSelectedTerritory();
            terr1.setSelected(true);
         }
         else if (terr1 != null && Map.getSelectedTerritory() != null && terr2==null && terr1.borders(Map.getSelectedTerritory())&& Map.getSelectedTerritory().getOwner() == players.get(currPlayer))
         {
            terr2 = Map.getSelectedTerritory();
            terr2.setSelected(true);
         }
         else if (terr1 != null && terr1 == null && Map.getSelectedTerritory() != null && Map.getSelectedTerritory()==terr1)
         {
            terr1.setSelected(false);
            terr1 = null;
         }
         if(terr1 != null && terr2 != null && terr1.getUnits()>0 && Map.getSelectedTerritory()==terr2)
         {
            terr1.fortify(terr2,1);
         }
      }
   }
   //-------------------------------------------
   //Returns the player whose turn it is
   //@return the current player
   //-------------------------------------------
   public static Player getCurrentPlayer()
   {
      return(players.get(currPlayer));
   }
}
