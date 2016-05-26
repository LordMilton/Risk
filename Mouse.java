//Thor Keller
//2016-05-14
//Keeps track of what the mouse does
import java.awt.event.*;
public class Mouse implements MouseListener, MouseMotionListener
{
   private static Mouse listener = new Mouse();
   private static boolean pressed;
   private static boolean clicked;
   private static int x;
   private static int y;
   private static int clickedx;
   private static int clickedy;
   //------------------------------------------------------
   //Gets the static Mouse to add to the Panel so it can read events
   //@return the static MouseListener
   //------------------------------------------------------
   public static Mouse getListener()
   {
      return(listener);
   }
   //------------------------------------------------------
   //Returns if the mouse button is down
   //@return whether the mouse button is down
   //------------------------------------------------------
   public static boolean mousePressed()
   {
      return(pressed);
   }
   //------------------------------------------------------
   //Returns whether the mouse has been clicked and not moved since it was clicked
   //Sets clicked to false.
   //@return whether the mouse has been clicked
   //------------------------------------------------------
   public static boolean mouseClicked()
   {
      boolean r = clicked;
      clicked = false;
      return(r);
   }
   //------------------------------------------------------
   //Returns the x coordinate of the mouse in the panel
   //@return the mouse's x coordinate
   //------------------------------------------------------
   public static int getX()
   {
      return(x);
   }
   //------------------------------------------------------
   //Returns the y coordinate of the mouse in the panel
   //@return the mouse's y coordinate
   //------------------------------------------------------
   public static int getY()
   {
      return(y);
   }
   //------------------------------------------------------
   //@override
   //sets the clicked to be true
   //------------------------------------------------------
   public void mouseClicked(MouseEvent e)
   {
      clicked = true;
   }
   //------------------------------------------------------
   //@override
   //sets pressed to be true
   //------------------------------------------------------
   public void mousePressed(MouseEvent e)
   {
      pressed = true;
      clickedx = x;
      clickedy = y;
   }
   //------------------------------------------------------
   //@override
   //sets pressed to be false
   //------------------------------------------------------
   public void mouseReleased(MouseEvent e)
   {
      pressed = false;
      if(Math.abs(clickedx-e.getX())+Math.abs(clickedy-e.getY()) < 25)
         clicked = true;
   }
   //------------------------------------------------------
   //@override
   //updates the x and y coordinate
   //------------------------------------------------------
   public void mouseDragged(MouseEvent e)
   {
      clicked = false;
      x = e.getX();
      y = e.getY();
   }
   //------------------------------------------------------
   //@override
   //updates the x and y coordinate
   //------------------------------------------------------
   public void mouseMoved(MouseEvent e)
   {
      clicked = false;
      x = e.getX();
      y = e.getY();
   }
   //------------------------------------------------------
   //@override
   //these methods do nothing. Are just here so we can use the mouseListener.
   //------------------------------------------------------
   public void mouseEntered(MouseEvent e)
   {}
   public void mouseExited(MouseEvent e)
   {}
}