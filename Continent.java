//Bradley Dufour
//2016-05-17
//Represents a continent
import java.util.ArrayList;

public class Continent
{
   private int bonus;
   String name;
   ArrayList<Territory> territories = new ArrayList<Territory>();
   public Continent(String name, int bonus)
   {
      this.name = name;
      this.bonus = bonus;
   }
   //-------------------------------------------------
   //getBonus Accesses bonus of Continent object
   //@return bonus[int]
   //-------------------------------------------------
   public int getBonus()
   {
      return bonus;
   }
   //-------------------------------------------------
   //getName Accesses name of Continent object
   //@return name[String]
   //-------------------------------------------------
   public String getName()
   {
      return name;
   }
   //-------------------------------------------------
   //getTerritories Accesses territories list of Continent object
   //@return territories[ArrayList<Territory>]
   //-------------------------------------------------
   public ArrayList<Territory> getTerritories()
   {
      return territories;
   }
   //-------------------------------------------------
   //addTerritory Adds a Territory object to the Continent's list of territories
   //@param territory Territory to add to list
   //POSTCONDITION: territories has increased in length by 1
   //-------------------------------------------------
   public void addTerritory(Territory territory)
   {
      territories.add(territory);
   }
   //-------------------------------------------------
   //playerHasAll Checks if a certain Player owns all the territories in the Continent
   //@param player Player to check if owns continent
   //@return true if Player has every territory in this Continent object, false otherwise
   //-------------------------------------------------
   public boolean playerHasAll(Player player)
   {
      for(Territory territory:territories)
      {
         boolean hasTerritory = false;
         for(Territory playerTerr:territories)
         {
            if(playerTerr.getName().equals(territory))
            {
               hasTerritory = true;
               break;
            }
         }
         if(!hasTerritory)
            return false;
      }
      return true;
   }
}
