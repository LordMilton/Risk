//Thor Keller
//2016-05-14
//Represents the map and keeps track of all territories
import java.awt.image.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.*;
public class Map implements Clickable
{
   static BufferedImage map;
   static int[][] image;
   //x and y of the pixel in the top left corner
   int x0;
   int y0;
   //x and y of the mouse after the last update
   int xmouse;
   int ymouse;
   private static Territory[] territories = loadTerritories();
   private static Territory selected = null;
   
   //------------------------------------------------
   //@Override
   //Check and take care of actions when the map is clicked on
   //------------------------------------------------
   public void click()
   {
      if(Mouse.getX()>100)
      {
         update();
         Player.processTurn();
         selected = null;
         if(x0<0 || y0<0)
            System.exit(0);
         if(Mouse.mouseClicked()&&image[(Mouse.getX()+x0)%image.length][(Mouse.getY()+y0)%image[0].length]>=0&&image[(Mouse.getX()+x0)%image.length][(Mouse.getY()+y0)%image[0].length]<territories.length)
            selected = territories[image[(Mouse.getX()+x0)%image.length][(Mouse.getY()+y0)%image[0].length]];
      }
   }
   //------------------------------------------------
   //@Override
   //Draws the map
   //@Param Graphics object to draw on
   //------------------------------------------------
   public void draw(Graphics g)
   {
      int xtemp = x0;
      int ytemp = y0;
      g.drawImage(map, -xtemp, -ytemp, null);
      g.drawImage(map, map.getWidth()-xtemp, -ytemp, null);
      int col = image[(Mouse.getX()+x0)%map.getWidth()][(Mouse.getY()+y0)%map.getHeight()];
      g.setColor(new Color(map.getRGB((Mouse.getX()+x0)%map.getWidth(),(Mouse.getY()+y0)%map.getHeight())).darker());
      if(col>=0&&col<=45)
      {
         for(int i = 0; i < 1500; i++)
            for(int j = 0; j < 1000; j++)
               if(image[(i+x0)%map.getWidth()][(j+y0)%map.getHeight()]==col)
                  g.fillRect(i,j,1,1);
      }
      for(int i = 0; i < 1500; i++)
         for(int j = 0; j < 1000; j++)
            if(image[(i+x0)%map.getWidth()][(j+y0)%map.getHeight()]>=0&&image[(i+x0)%map.getWidth()][(j+y0)%map.getHeight()]<territories.length)
               if(territories[image[(i+x0)%map.getWidth()][(j+y0)%map.getHeight()]].getSelected())
               {
                  g.setColor(territories[image[(i+x0)%map.getWidth()][(j+y0)%map.getHeight()]].getOwner().getColor().brighter());
                  g.fillRect(i,j,1,1);
               }
      if(col>=0&&col<=45)
      {
         String cityName = territories[col].getName();
         int x = Mouse.getX();
         int y = Mouse.getY();
         int width = g.getFontMetrics().stringWidth(cityName);
         int height = g.getFontMetrics().getHeight();
         g.setColor(Color.WHITE);
         g.fillRect((int)x + 2, (int)y + 2,width+20,height*2+20);
         g.setColor(Color.BLACK);
         g.drawRect((int)x + 2, (int)y + 2,width+20,height*2+20);
         g.drawString(cityName,(int)x + 12, (int)y + height + 12);
         int width2 = g.getFontMetrics().stringWidth(territories[col].getUnits()+" Units");
         g.drawString(territories[col].getUnits()+" Units",(int)x + 12 + width/2 - width2/2, (int)y + height*2 + 12);
      }
      g.setColor(Color.WHITE);
      g.fillRect(0,0,100,1000);
   }
   //------------------------------------------------------
   //Updates the x0 and y0 values if the map moved
   //------------------------------------------------------
   public void update()
   {
      int x = Mouse.getX();
      int y = Mouse.getY();
      if(Mouse.mousePressed())
      {
         int xa = x0-(x-xmouse);
         int ya = y0-(y-ymouse);
         if(xa<0)
            xa+=map.getWidth();
         if(xa>map.getWidth())
            xa-=map.getWidth();
         if(ya<0)
            ya = 0;
         if(ya>map.getHeight()-800)
            ya = map.getHeight()-800;
         x0 = xa;
         y0 = ya;
      }
      xmouse = x;
      ymouse = y;
   }
   //------------------------------------------------------
   //Redraws the map; call when big changes
   //------------------------------------------------------
   public static void updateMap()
   {
      for (int y = 0; y < map.getHeight(); y++) {
         for (int x = 0; x < map.getWidth(); x++) {
            if(image[x][y]<0)
               map.setRGB(x, y, Color.BLACK.getRGB());
            else if(image[x][y]<43)
            {
               if(territories[image[x][y]].getOwner()!=null)
                  map.setRGB(x, y, territories[image[x][y]].getOwner().getColor().getRGB());
               else
                  map.setRGB(x,y,Color.GRAY.getRGB());
            }
            else
               map.setRGB(x,y,Color.BLUE.getRGB());
         }
      }
   }
   //------------------------------------------------------
   //Redraws the map; call after a single city is captured
   //------------------------------------------------------
   public static void miniUpdate(Territory t)
   {
      for (int y = 0; y < map.getHeight(); y++) {
         for (int x = 0; x < map.getWidth(); x++) {
            if(image[x][y]>=0&&image[x][y]<43&&territories[image[x][y]]==t)
               map.setRGB(x, y, t.getOwner().getColor().getRGB());
         }
      }
   }
   //------------------------------------------------------
   //Loads the map.
   //Private because it should only be called in this class. Othewise, bad things happen.
   //------------------------------------------------------
   private static void loadMap()
   {
      
      try {
         map = ImageIO.read(new File("Map.png"));
      } 
      catch (Exception e) {
      }
   }
   //------------------------------------------------------
   //Loads the map indexes. Only needs to be called once.
   //------------------------------------------------------
   public static void firstLoad()
   {
      try {
         BufferedImage img = ImageIO.read(new File("Map.png"));
         image = new int[img.getWidth()][img.getHeight()];
         for(int x = 0; x < img.getWidth(); x++)
            for(int y = 0; y < img.getHeight(); y++)
            {
               int rgb = img.getRGB(x,y);
               int red = (rgb >> 16) & 0x000000FF;
               int green = (rgb >>8 ) & 0x000000FF;
               int blue = (rgb) & 0x000000FF;
               image[x][y] = (blue/85) + (4*green/85)+ (16*red/85) - 1;
            }
         loadMap();
         updateMap();
      } 
      catch (Exception e) {
         e.printStackTrace();
         System.exit(0);
      }
   }
   //------------------------------------------------------
   //Loads the territories
   //@return the territories
   //------------------------------------------------------
   public static Territory[] loadTerritories()
   {
      Territory[] tempTerritories = new Territory[42];
      try
      {
         Scanner scan = new Scanner(new File("Territories.txt"));
         Deck deck = new Deck();
         for(int i = 0; i < tempTerritories.length; i++)
         {
            tempTerritories[i] = new Territory(scan.nextLine());
            deck.addCard(new Card(i/14,tempTerritories[i]));
         }
         for(int i = 0; i < 2; i++)
            deck.addCard(new Card(3,(Territory)null));
         Deck.setDeck(deck);
      }
      catch(Exception e)
      {}
      territories = tempTerritories;
      loadBorders();
      return(territories);
   }
   //------------------------------------------------------
   //Loads the borders
   //------------------------------------------------------
   public static void loadBorders()
   {
      try
      {
         Scanner scan = new Scanner(new File("Borders.txt"));
         while(scan.hasNextLine())
         {
            String[] parts = scan.nextLine().split("\\|");
            getTerritory(parts[0]).addBorder(getTerritory(parts[1]));
         }
      }
      catch(Exception e)
      {}
   }
   //------------------------------------------------------
   //Returns the territory from it's name.
   //@param the name of the territory you want to find
   //@return the territory with that name, or null if it is not found
   //------------------------------------------------------
   public static Territory getTerritory(String territory)
   {
      for(Territory t: territories)
         if(t.getName().equals(territory))
            return(t);
      return(null);
   }
   //------------------------------------------------------
   //Returns the territory that was clicked on during the last frame refresh
   //@return the territory selected
   //@return null if no territory was last selected
   //------------------------------------------------------
   public static Territory getSelectedTerritory()
   {
      return(selected);
   }
   //------------------------------------------------------
   //Randomizes the owners of the territories
   //------------------------------------------------------
   public static void randomizeTerritories()
   {
      ArrayList<Integer> players = new ArrayList<Integer>();
      for(int i = 0; i < territories.length; i++)
      {
         players.add(i%Player.numPlayers());
      }
      for(int i = 0; i < territories.length; i++)
      {
         territories[i].setOwner(Player.getPlayer(players.remove((int)(players.size()*Math.random()))));
      }
      updateMap();
   }
   //------------------------------------------------------
   //Sets whether a province is glowing
   //------------------------------------------------------
   public static void set()
   {
      ArrayList<Integer> players = new ArrayList<Integer>();
      for(int i = 0; i < territories.length; i++)
      {
         players.add(i%Player.numPlayers());
      }
      for(int i = 0; i < territories.length; i++)
      {
         territories[i].setOwner(Player.getPlayer(players.remove((int)(players.size()*Math.random()))));
      }
      updateMap();
   }
}