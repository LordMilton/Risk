//Thor Keller
//2016-05-14
//Represents the territories
import java.util.*;
public class Territory
{
   private int units;
   private Player owner;
   private String name;
   private ArrayList<Territory> borders = new ArrayList<Territory>();
   private boolean selected;
   public Territory(String name)
   {
      this.name = name;
   }
   //------------------------------------------------------
   //returns the name of the Territory
   //@return the territory's name
   //------------------------------------------------------
   public String getName()
   {
      return(name);
   }
   //------------------------------------------------------
   //Adds the territory to the borders list and this to the other territory's border list
   //@param the territory to add
   //------------------------------------------------------
   public void addBorder(Territory toAdd)
   {
      borders.add(toAdd);
      toAdd.borders.add(this);
   }
   //------------------------------------------------------
   //Checks if the territory is in the borders list
   //@param the territory to check
   //@return whether the territory is in borders
   //------------------------------------------------------
   public boolean borders(Territory toCheck)
   {
      return(borders.contains(toCheck));
   }
   //------------------------------------------------------
   //Sets the owner of the territory
   //@param Player who is the new owner
   //------------------------------------------------------
   public void setOwner(Player p)
   {
      if(owner!=null)
         owner.getTerritories().remove(this);
      else
         units = 1;
      owner = p;
      p.getTerritories().add(this);
   }
   //------------------------------------------------------
   //Returns the owner of the territory
   //@return Player who is the current owner
   //------------------------------------------------------
   public Player getOwner()
   {
      return owner;
   }
   //------------------------------------------------------
   //Subtracts the specified number of units from the Territory's unit count
   //@param toRemove How many units to remove
   //------------------------------------------------------
   public void removeUnits(int toRemove)
   {
      units -= toRemove;
   }
   //------------------------------------------------------
   //Adds the specified number of units to the Territory's unit count
   //------------------------------------------------------
   public void addUnits(int additional)
   {
      units += additional;
   }
   //------------------------------------------------------
   //Returns the number of units stored in the Territory
   //@return units Number of units stored in the Territory
   //------------------------------------------------------
   public int getUnits()
   {
      return units;
   }
   //------------------------------------------------------
   //Moves the specified number of units from 'this' Territory to the specified Territory
   //@param toTerritory Territory to move the units to
   //@param units Number of units to transfer
   //------------------------------------------------------
   public void fortify(Territory toTerritory, int units)
   {
      this.removeUnits(units);
      toTerritory.addUnits(units);
   }
   //------------------------------------------------------
   //Sets whether the Territory should be selected.
   //@param The new value of selected
   //------------------------------------------------------
   public void setSelected(boolean selected)
   {
      this.selected = selected;
   }
   //------------------------------------------------------
   //Returns whether the territory is selected.
   //@return whether it was selected
   //------------------------------------------------------
   public boolean getSelected()
   {
      return(selected);
   }
}