//Bradley Dufour
//2016-05-14
//Represents the Dice
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class Die implements Clickable
{
   private Color color;
   private boolean clicked;
   private int number;
   private int x;
   private int y;
   private static Image[] sides = fillSides();
   private static Die[] dice = {new Die(Color.RED,-100,-100),new Die(Color.RED,-100,-100),new Die(Color.RED,-100,-100),new Die(Color.WHITE,-100,-100),new Die(Color.WHITE,-100,-100)};
   //-----------------------------------------------
   //Fills the static Image[] sides with the .png files created for Die.java
   //PRECONDITION: Files [1-6].png exist
   //POSTCONDITION: sides contains .png files
   //-----------------------------------------------
   public static Image[] fillSides()
   {
      try
      {
         Image[] sides = new Image[6];
         sides[0] = ImageIO.read(new File("1.png"));
         sides[1] = ImageIO.read(new File("2.png"));
         sides[2] = ImageIO.read(new File("3.png"));
         sides[3] = ImageIO.read(new File("4.png"));
         sides[4] = ImageIO.read(new File("5.png"));
         sides[5] = ImageIO.read(new File("6.png"));
         return(sides);
      }
      catch(Exception e)
      {
         System.err.println("Failed to fill sides in Die.java with .jpg files");
      }
      return(null);
   }
   public Die(Color color, int x, int y)
   {
      this.color = color;
      clicked = false;
      number = 6;
      this.x = x;
      this.y = y;
   }
   //------------------------------------------------
   //rollAllClicked Rolls all currently clicked dice on screen
   //POSTCONDITION: Dies clicked will be rolled
   //------------------------------------------------
   public static void rollAllClicked()
   {
      for(Die die:dice)
      {
         if(die.x>0&&die.clicked)
            die.roll();
      }
   }
   //------------------------------------------------
   //roll Rolls the die
   //POSTCONDITION: number is changed
   //------------------------------------------------
   private void roll()
   {
      number = (int)(Math.random()*6+1);
   }
   //------------------------------------------------------
   //Checks to see if Die object is currently clicked
   //Accessor method
   //@return boolean clicked
   //------------------------------------------------------
   public boolean isClicked()
   {
      return clicked;
   }
   //------------------------------------------------
   //getRoll Returns the number currently rolled on the die
   //@return number
   //------------------------------------------------
   public int getRoll()
   {
      return number;
   }
   //------------------------------------------------
   //Refer to click() from Clickable
   //POSTCONDITION: clicked will change
   //------------------------------------------------
   public void click()
   {
      if(((Mouse.getX() >= x && Mouse.getX() <= x+60) && (Mouse.getY() >= y && Mouse.getY() <= y+60)) && Mouse.mouseClicked()&&this!=dice[0]&&this!=dice[3])
         clicked = !clicked;
   }
   //------------------------------------------------
   //Refer to draw(Graphics g) from Clickable
   //------------------------------------------------
   public void draw(Graphics g)
   {
      if(clicked)
         g.setColor(color);
      else
         g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 90));
      g.fillRoundRect(x, y, 60, 60, 5, 5);
      g.drawImage(sides[number-1],x,y,x+60,y+60,0,0,244,244,null);
   }
   //------------------------------------------------
   //Gets the die at the index given
   //@param index - the index  you want
   //@return the die at that index
   //------------------------------------------------
   public static Die getDie(int index)
   {
      return(dice[index]);
   }
   //------------------------------------------------
   //Places the allowed dice on the screen
   //@param red the number of units in the attacking city
   //@param white the number of units in the defending city
   //PRECONDITION: Attacking Territory must contain more than 1 unit
   //POSTCONDITION: "Roll" button will be drawn
   //------------------------------------------------
   public static void showDice(int red, int white)
   {
      if(dice[0].x<0)
      {
         red--;
         for(int i = 0; i < 3; i++)
            dice[i].clicked = (i < red);
         for(int i = 3; i < 5; i++)
            dice[i].clicked = (i < (white+3))
         for(int i = 0; i < red && i < 3; i++)
         {
            dice[i].x = 200 + (i*75);
            dice[i].y = 300;
         }
         for(int i = 0; i < white && i < 2; i++)
         {
            dice[i+3].x = 200 + (i*75);
            dice[i+3].y = 375;
         }
         Button.changeLabel("Roll");
      }
   }
   //------------------------------------------------
   //Hides the dice
   //------------------------------------------------
   public static void hideDice()
   {
      int[][] high = {{-1,-1},{-1,-1}};
      int selected = 0;
      for(int i = 0; i < 3; i++)
         if(dice[i].x>0&&dice[i].clicked)
         {
            selected++;
            if(dice[i].number>high[0][0])
            {
               high[0][1] = high[0][0];
               high[0][0] = dice[i].number;
            }
            else if(dice[i].number>high[0][1])
               high[0][1] = dice[i].number;
         }
      for(int i = 3; i < 5; i++)
         if(dice[i].x>0&&dice[i].clicked)
         {
            if(dice[i].number>high[1][0])
            {
               high[1][1] = high[1][0];
               high[1][0] = dice[i].number;
            }
            else if(dice[i].number>high[1][1])
               high[1][1] = dice[i].number;
         }
      int[] losses = {0,0};
      if(high[0][0]>high[1][0])
         losses[1]++;
      else
         losses[0]++;
      if(high[0][1]>=0&&high[1][1]>=0)
         if(high[0][1]>high[1][1])
            losses[1]++;
         else
            losses[0]++;
      for(int i = 0; i < 5; i++)
      {
         dice[i].x = -100;
         dice[i].y = -100;
      }
      Player.resetSelected(losses[0],losses[1],selected);
   }
}
