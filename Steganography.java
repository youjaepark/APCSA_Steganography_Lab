import java.awt.Color;
import java.util.*;
import java.awt.Point;
 
/**
 * Write a description of class Steganography here.
 * Steganography Lab A1
 * @YoujaePark
 * @23/03/15
 */
public class Steganography
{
    public static void main(String []args) {
    /*
    Picture beach = new Picture("beach.jpg");
    beach.explore();
    Picture copy = testClearLow(beach);
    copy.explore();
    Picture beach2 = new Picture("beach.jpg");
    beach2.explore();
    Picture copy2 = testSetLow(beach2 ,Color.PINK);
    copy2.explore();
    Picture copy3 = revealPicture(copy2);
    copy3.explore();
    
    Picture arch = new Picture("arch.jpg");
    Picture flower = new Picture("flower1.jpg");
    if(canHide(arch,flower)){
        Picture hidded = hidePicture(flower,arch);
        hidded.explore();
        Picture revealed = revealPicture(hidded);
        revealed.explore();
    }
    
    
    Picture beach = new Picture("beach.jpg");
    Picture robot = new Picture("robot.jpg");
    Picture flower1 = new Picture("flower1.jpg");
    beach.explore();
    Picture hidden1 = hidePicture(beach, robot , 65,208);
    Picture hidden2 = hidePicture(hidden1, flower1 , 280,110);
    hidden2.explore();
    Picture unhidden = revealPicture(hidden2);
    unhidden.explore();
    
    Picture swan = new Picture("swan.jpg");
    Picture swan2 = new Picture("swan.jpg");  
    System.out.println("Swan and swan2 are the same: "+isSame(swan,swan2));
    swan = testClearLow(swan);
    System.out.println("Swan and swan2 are the same (after clearLow run on swan): "+isSame(swan,swan2));
    
    Picture arch = new Picture("arch.jpg");
    Picture koala = new Picture("koala.jpg") ;
    Picture robot1 = new Picture("robot.jpg");
    ArrayList<Point> pointList = findDifferences(arch, arch);
    System.out.println("PointList after comparing two identical s pictures " +
    "has a size of " + pointList.size());
    pointList = findDifferences(arch, koala);
    System.out.println("PointList after comparing two different sized pictures " +
    "has a size of " + pointList.size());
    Picture arch2 = hidePicture(arch, robot1, 65, 102);
    pointList = findDifferences(arch, arch2);
    System.out.println("Pointlist after hiding a picture has a siz m e of "
    + pointList.size());
    arch.show();
    arch2.show();

    Picture hall = new Picture("femaleLionAndHall.jpg");
    Picture robot2 = new Picture("robot.jpg");
    Picture flower2 = new Picture("flower1.jpg");
    // hide pictures
    Picture hall2 = hidePicture(hall, robot2, 50, 300);
    Picture hall3 = hidePicture(hall2, flower2, 115, 275);
    hall3.explore();
    if(!isSame(hall, hall3))
    {
        Picture hall4 = showDifferentArea(hall , findDifferences(hall, hall3));
        hall4.show();
        Picture unhiddenHall3 = revealPicture(hall3);
        unhiddenHall3.show();
    }
    */
   Picture beach = new Picture("beach.jpg"); 
   Picture h1 = hideText(beach, "HELLO WORLD");
   h1.explore();
   System.out.println("The hidden text is "+revealText(h1));
   
}
public static void clearLow(Pixel p){
        p.setRed(p.getRed()/4 * 4);
        p.setGreen(p.getGreen()/4 * 4);
        p.setBlue(p.getBlue()/4 * 4);
}
public static Picture testClearLow(Picture pic){
    Picture p = new Picture(pic);
    Pixel[][] pixels = p.getPixels2D();
    for (int r =0; r< pixels.length; r++){
        for (int c = 0; c< pixels[0].length; c++){
        clearLow(pixels[r][c]);
        }    
    }
    return p;
}
public static void setLow(Pixel p, Color c) {         
    p.setRed(p.getRed() / 4 * 4 + c.getRed() / 64 * 64 / 64);         
    p.setGreen(p.getGreen() / 4 * 4 + c.getGreen() / 64 * 64 / 64);         
    p.setBlue(p.getBlue() / 4 * 4 + c.getBlue() / 64 * 64 / 64);     
}      //Run setLow for every pixel in Picture p with modifier Color c     
public static Picture testSetLow(Picture pic, Color col) {         
    Picture p = new Picture(pic);
    Pixel[][] pixels = p.getPixels2D();
    for (int r = 0; r < pixels.length; r++)
    {
        for (int c = 0; c < pixels[0].length; c++)
        {
            setLow(pixels[r][c], col);
        }
    }       
    return p;     
    } 
public static Picture revealPicture(Picture hidden){
    Picture copy = new Picture(hidden);
    Pixel[][] pixels = copy.getPixels2D();
    Pixel[][] source = hidden.getPixels2D();
    for (int r = 0; r < pixels.length; r++)
    {
        for (int c = 0; c < pixels[0].length; c++)
        {
            Color col = source[r][c].getColor();
            Pixel p = pixels[r][c];
            p.setRed(col.getRed() % 4 *64);
            p.setGreen(col.getGreen() % 4 *64);
            p.setBlue(col.getBlue() % 4 *64);
        }
    }
    return copy;
}
public static boolean canHide(Picture source, Picture secret){
    return source.getWidth() >= secret.getWidth() &&
    source.getHeight() >= secret.getHeight();

}
public static Picture hidePicture(Picture source, Picture secret){
    Picture hidden = new Picture(source);
    Pixel[][] hPixels = hidden.getPixels2D();
    Pixel[][] sPixels = secret.getPixels2D();
    for (int r = 0; r < hPixels.length; r++)
    {
        for (int c = 0; c < hPixels[0].length; c++)
        {
            Pixel s = sPixels[r][c];
            setLow(hPixels[r][c], s.getColor());
        }
    }
    return hidden;
}
public static Picture hidePicture(Picture source, Picture secret,int startRow, int startColumn){
    Picture hidden = new Picture(source);
    Pixel[][] hPixels = hidden.getPixels2D();
    Pixel[][] sPixels = secret.getPixels2D();
    int width = sPixels[0].length;
    int height = sPixels.length;
    for (int r = startRow, srow=0; r < hPixels.length && srow < height; r++,srow++)
    {
        for (int c = startColumn, scol=0; c < hPixels[0].length-1 && scol <width; c++,scol++)
        {
            Pixel s = sPixels[srow][scol];
            setLow(hPixels[r][c], s.getColor());
        }
    }
    return hidden;
}
public static boolean isSame(Picture pic1, Picture pic2){
    Pixel[][] pixels = pic1.getPixels2D();
    Pixel[][] otherPixels = pic2.getPixels2D();
    Pixel pixel =null;
    Pixel otherPixel = null;
    if( pic1.getWidth() != pic2.getWidth() || pic1.getHeight() != pic2.getHeight()){
        return false;
    }
    for (int r = 0; r < pixels.length; r++)
    {
        for (int c = 0; c < pixels[0].length; c++)
        {
            pixel = pixels[r][c];
            otherPixel = otherPixels[r][c];
            if (pixel.getRed() != otherPixel.getRed() ||
            pixel.getBlue() != otherPixel.getBlue() ||
            pixel.getGreen() != otherPixel.getGreen()){
                return false;
            }
        }
    }
    return true;
}
public static ArrayList<Point> findDifferences(Picture p1, Picture p2) {
    ArrayList<Point> ret = new ArrayList<Point>();

    Pixel[][] pixels1 = p1.getPixels2D();
    Pixel[][] pixels2 = p2.getPixels2D();

    for (int i = 0; i < pixels1.length; i++) {
        for (int j = 0; j < pixels1[0].length; j++) {
            if (!pixels1[i][j].getColor().equals(pixels2[i][j].getColor())) {
                ret.add(new Point(j, i));
            }
        }
    }

    return ret;
}
public static Picture showDifferentArea(Picture p, ArrayList<Point> points) {
    Picture copy = new Picture(p);

    int leastX = copy.getWidth(), mostX = 0, leastY = copy.getHeight(), mostY = 0;
    for (Point pt : points) {
        leastX = Math.min(pt.x, leastX);
        leastY = Math.min(pt.y, leastY);
        mostX = Math.max(pt.x, mostX);
        mostY = Math.max(pt.y, mostY);
    }

    for (int i = leastY; i < mostY; i++) {
        if (i == leastY || i == mostY - 1) {
            for (int j = leastX; j < mostX; j++) {
                copy.setBasicPixel(j, i, 0);
            }
        } else {
            copy.setBasicPixel(leastX, i, 0);
            copy.setBasicPixel(mostX, i, 0);
        }
    }

    return copy;
}
public static ArrayList<Integer> encodeString(String topSecretTextMessage)
{
    String upperCaseTopSecretTextMessage = topSecretTextMessage.toUpperCase();
    String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    ArrayList<Integer> result = new ArrayList<Integer>();
    for (int i = 0; i < upperCaseTopSecretTextMessage.length(); i++)
    {
      String aLetter = upperCaseTopSecretTextMessage.substring(i, i + 1);
      if (aLetter.equals(" "))
      {
        result.add(27);
      }
      else
      {
        result.add(alpha.indexOf(aLetter) + 1);
      }
    }
    result.add(0);
    return result;
}
public static String decodeString(ArrayList<Integer> codes)
{
    String result="";
    String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    for (int i=0; i < codes.size(); i++)
    {
        if (codes.get(i) == 27)
        {
            result = result + " ";
        }
         else
        {
             result = result + alpha.substring(codes.get(i)-1,codes.get(i));
        }
    }
    return result;
}
private static int[] getBitPairs(int num)
{
    int[] bits = new int[3];
    int code = num;
    for (int i = 0; i < 3; i++)
    {
      bits[i] = code % 4;
      code = code / 4;
    }
    return bits;
}
public static Picture hideText(Picture source, String s){
    Picture newPic = new Picture(source);
    Pixel[][] pixels = newPic.getPixels2D();
    ArrayList<Integer> encodedMessage = encodeString(s);
    boolean hiddenFullMessage = false;
    for (int r = 0; r < pixels.length && !hiddenFullMessage; r++)
    {
      for (int c = 0; c < pixels[0].length && !hiddenFullMessage; c++)
      {
        if (r + c < encodedMessage.size())
        {
          int[] bits = getBitPairs(encodedMessage.get(r + c));
          Pixel aSinglePixel = pixels[r][c];
          clearLow(aSinglePixel);

          Color anRGBColorClear6 =
            new Color(aSinglePixel.getRed() + bits[0],
                      aSinglePixel.getGreen() + bits[1],
                      aSinglePixel.getBlue() + bits[2]);
          aSinglePixel.setColor(anRGBColorClear6);
        }
        else
        {
          hiddenFullMessage = true;
        }

      }
    }
    return newPic;
  }
public static String revealText(Picture source)
{
    Pixel[][] pixels = source.getPixels2D();
    ArrayList<Integer> codes = new ArrayList<Integer>();
    boolean revealedFullMessage = false;
    for (int r = 0; r < pixels.length && !revealedFullMessage; r++)
    {
      for (int c = 0; c < pixels[0].length && !revealedFullMessage; c++)
      {
        Color col = pixels[r][c].getColor();
        int code =(col.getRed() % 4) + (col.getGreen() % 4) * 4 + (col.getBlue() % 4) * 16;
        if (code != 0)
        {
          codes.add(code);
        }
        else
        {
          revealedFullMessage = true;
        }
      }
    }  
    return decodeString(codes);
}
}

