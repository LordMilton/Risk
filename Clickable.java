//Bradley Dufour
//2016-05-13
//Interface that allows the Panel to have an arraylist of many types of objects
import java.awt.Graphics;

public interface Clickable
{
   //------------------------------------------------------
   //Switches the objects intrinsic boolean value clicked to it's opposite
   //------------------------------------------------------
   public void click();
   //------------------------------------------------------
   //Draws Clickable object to the screen
   //@param Graphics  object to draw Clickable object
   //PRECONDITION: Object is visible
   //POSTCONDITION: Object will be seen on screen
   //------------------------------------------------------
   public void draw(Graphics g);
}
