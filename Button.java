//Bradley Dufour
//2016-05-19
//Does certain actions that were not related to other classes when clicked
import java.awt.*;

public class Button implements Clickable
{
   private int x;
   private int y;
   private String label;
   private static Button button = new Button(20,600,"invisible");
   public Button(int x, int y, String label)
   {
      this.x = x;
      this.y = y;
      this.label = label;
   }
   //------------------------------------------------
   //@Override
   //Check and take care of actions when the card is clicked on
   //------------------------------------------------
   public void click()
   {
      if(label!="invisible")
         if(Mouse.getX() >= x && Mouse.getX() <= x+70 && Mouse.getY() >= y && Mouse.getY() <= y+30 && Mouse.mouseClicked())
            if(label.equals("Roll"))
            {
               Die.rollAllClicked();
               label = "Continue";
            }
            else if(label.equals("Continue"))
            {
               Die.hideDice();
               label = "Done";
            }
            else if(label.equals("Done")||label.equals("End"))
               Player.incrementCurrPhase();
   }
   //------------------------------------------------
   //@Override
   //Draw the card on the screen if visible.
   //------------------------------------------------
   public void draw(Graphics g)
   {
      if(label!="invisible")
      {
         g.setColor(Color.GRAY);
         if(Mouse.getX()>x&&Mouse.getX()<x+70&&Mouse.getY()>y&&Mouse.getY()<y+30)
            g.setColor(Color.DARK_GRAY);
         g.fillRect(x,y,70,30);
         g.setColor(Color.BLACK);
         g.drawString(label,x+20,y+20);
      }
   }
   //------------------------------------------------
   //Returns the button to be drawn
   //@return the Button
   //------------------------------------------------
   public static Button getButton()
   {
      return(button);
   }
   //------------------------------------------------
   //Change the label
   //@param the new label
   //------------------------------------------------
   public static void changeLabel(String label)
   {
      button.label = label;
   }
}