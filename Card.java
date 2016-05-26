//Thor Keller
//2016-05-19
//Represents the cards.
import java.awt.image.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
public class Card implements Clickable
{
   private int type;
   private boolean clicked;
   private Territory territory;
   private int x;
   private int y;
   private boolean visible;
   private static BufferedImage[] images = getImages();
   public Card(int type, Territory territory)
   {
      this.type = type;
      this.territory = territory;
   }
   //------------------------------------------------------
   //Returns whether the card is selected
   //@return  clicked
   //------------------------------------------------------
   public boolean isClicked()
   {
      return(clicked);
   }
   //------------------------------------------------------
   //Sets the card to be visible
   //@param x the x coordinate to display the card at
   //@param y the y coordinate to display the card at
   //------------------------------------------------------
   public void setVisible(int x, int y)
   {
      visible = true;
      this.x = x;
      this.y = y;
   }
   //------------------------------------------------------
   //Sets the card to be invisible
   //------------------------------------------------------
   public void setInvisible()
   {
      visible = false;
   }
   //------------------------------------------------
   //@Override
   //Check and take care of actions when the card is clicked on
   //------------------------------------------------
   public void click()
   {
      if(visible)
      {
         if(Mouse.getX() > x && Mouse.getX() < x + images[type].getWidth() && Mouse.getY() > y && Mouse.getY() < y + images[type].getHeight()&&Mouse.mouseClicked())
         {
            clicked = !clicked;
         }
      }
   }
   //------------------------------------------------
   //@Override
   //Draw the card on the screen if visible.
   //------------------------------------------------
   public void draw(Graphics g)
   {
      if(visible)
      {
         g.drawImage(images[type], x, y, null);
         g.setColor(Color.BLACK);
         if(territory!=null)
            g.drawString(territory.getName(),x+65-2*territory.getName().length(),y+200);
         if(Mouse.getX() > x && Mouse.getX() < x + images[type].getWidth() && Mouse.getY() > y && Mouse.getY() < y + images[type].getHeight())
         {
            g.setColor(new Color(100, 100, 100, 100));
            g.fillRect(x,y,images[type].getWidth(), images[type].getHeight());
         }
         if(clicked)
         {
            g.setColor(Color.YELLOW);
            for(int i = 0; i < 6; i++)
               g.drawRect(x+i,y+i,images[type].getWidth()-2*i, images[type].getHeight()-2*i);
         }
      }
   }
   //------------------------------------------------
   //Loads the static images
   //@return the images of the cards
   //------------------------------------------------
   private static BufferedImage[] getImages()
   {
      BufferedImage[] bufferedimages = new BufferedImage[4];
      try
      {
         for(int i = 1; i <= 4; i++)
            bufferedimages[i-1] = ImageIO.read(new File("Card"+i+".png"));
      }
      catch(Exception e)
      {e.printStackTrace();}
      return(bufferedimages);
   }
}