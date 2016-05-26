//Bradley Dufour
//2016-05-15
//Represents the Players in the game
//Also controls turns
import java.util.*;
import java.awt.*;

public class Player
{
   private static Territory terr1, terr2;
   private static int unitsToAdd = 0;
   private static int currPhase = 1;
   private static ArrayList<Continent> continents = getContinents();
   private static int currPlayer = 0;
   private static ArrayList<Player> players = new ArrayList<Player>();
   private static int[] cardBonuses = {4,6,8,10,12,15};
   private static int horsePos = 0;
   private LinkedList<Territory> territories;
   private Deck hand;
   private Color color;
   private static boolean handVisible = false;
   public Player(Color color)
   {
      this.color = color;
      hand = new Deck();
      territories = new LinkedList<Territory>();
      addPlayer();
   }
   //-------------------------------------------
   //Makes an ArrayList of Continents by reading from Continents.txt
   //@return ArrayList of Continents
   //-------------------------------------------
   private static ArrayList<Continent> getContinents()
   {
      try
      {
         Scanner scan = new Scanner(new java.io.File("Continents.txt"));
         ArrayList<Continent> continents = new ArrayList<Continent>();
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
   //Calculates the number of units the Player should receive
   //@return Number of units the Player should receive
   //-------------------------------------------
   public void getUnits(boolean usedCards)
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
   //-------------------------------------------
    public void viewHand()
    {
      handVisible = !handVisible;
      hand.setVisible(handVisible);
    }
    //-------------------------------------------
    //
    //-------------------------------------------
    public static void incrementCurrPhase()
    {
      currPhase++;
      if(currPhase > 3)
      {
         currPlayer = (currPlayer+1)%players.size();
         currPhase = 1;
      }
    }
   //-------------------------------------------
   //Processes the turn.
   //-------------------------------------------
    public static void processTurn()
    {
      if(currPhase == 1)
      {
         if(Map.getSelectedTerritory() != null && Map.getSelectedTerritory().getPlayer() == players.get(currPlayer))
         {
            Map.getSelectedTerritory().addUnit(1);
            unitsToAdd--;
         }
         if(unitsToAdd == 0) 
            incrementCurrPhase();
      }
      else if(currPhase == 2)
      {
         if(Map.getSelectedTerritory() != null && Map.getSelectedTerritory().getPlayer() == players.get(currPlayer))
            
      }
      else if(currPhase == 3)
      {
         
      }
    }
    public static Player getCurrentPlayer()
    {
      return(players.get());
    }
}