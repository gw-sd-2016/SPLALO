package jm.gui.cpn;

import java.applet.Applet;
import java.awt.Image;
import java.net.URL;

public class AppletImages
  implements Images
{
  private final Image trebleClef;
  private final Image bassClef;
  private final Image semibreve;
  private final Image minimUp;
  private final Image minimDown;
  private final Image crotchetUp;
  private final Image crotchetDown;
  private final Image quaverDown;
  private final Image quaverUp;
  private final Image semiquaverDown;
  private final Image semiquaverUp;
  private final Image semibreveRest;
  private final Image minimRest;
  private final Image crotchetRest;
  private final Image quaverRest;
  private final Image semiquaverRest;
  private final Image dot;
  private final Image sharp;
  private final Image flat;
  private final Image natural;
  private final Image one;
  private final Image two;
  private final Image three;
  private final Image four;
  private final Image five;
  private final Image six;
  private final Image seven;
  private final Image eight;
  private final Image nine;
  private final Image delete;
  private final Image tieOver;
  private final Image tieUnder;
  
  public AppletImages(Applet paramApplet, URL paramURL)
  {
    this.trebleClef = paramApplet.getImage(paramURL, "trebleClef.gif");
    this.bassClef = paramApplet.getImage(paramURL, "bassClef.gif");
    this.crotchetDown = paramApplet.getImage(paramURL, "crotchetDown.gif");
    this.crotchetUp = paramApplet.getImage(paramURL, "crotchetUp.gif");
    this.quaverDown = paramApplet.getImage(paramURL, "quaverDown.gif");
    this.quaverUp = paramApplet.getImage(paramURL, "quaverUp.gif");
    this.semiquaverDown = paramApplet.getImage(paramURL, "semiquaverDown.gif");
    this.semiquaverUp = paramApplet.getImage(paramURL, "semiquaverUp.gif");
    this.minimDown = paramApplet.getImage(paramURL, "minimDown.gif");
    this.minimUp = paramApplet.getImage(paramURL, "minimUp.gif");
    this.semibreve = paramApplet.getImage(paramURL, "semibreve.gif");
    this.dot = paramApplet.getImage(paramURL, "dot.gif");
    this.semiquaverRest = paramApplet.getImage(paramURL, "semiquaverRest.gif");
    this.quaverRest = paramApplet.getImage(paramURL, "quaverRest.gif");
    this.crotchetRest = paramApplet.getImage(paramURL, "crotchetRest.gif");
    this.minimRest = paramApplet.getImage(paramURL, "minimRest.gif");
    this.semibreveRest = paramApplet.getImage(paramURL, "semibreveRest.gif");
    this.sharp = paramApplet.getImage(paramURL, "sharp.gif");
    this.flat = paramApplet.getImage(paramURL, "flat.gif");
    this.natural = paramApplet.getImage(paramURL, "natural.gif");
    this.one = paramApplet.getImage(paramURL, "one.gif");
    this.two = paramApplet.getImage(paramURL, "two.gif");
    this.three = paramApplet.getImage(paramURL, "three.gif");
    this.four = paramApplet.getImage(paramURL, "four.gif");
    this.five = paramApplet.getImage(paramURL, "five.gif");
    this.six = paramApplet.getImage(paramURL, "six.gif");
    this.seven = paramApplet.getImage(paramURL, "seven.gif");
    this.eight = paramApplet.getImage(paramURL, "eight.gif");
    this.nine = paramApplet.getImage(paramURL, "nine.gif");
    this.delete = paramApplet.getImage(paramURL, "delete.gif");
    this.tieOver = paramApplet.getImage(paramURL, "tieOver.gif");
    this.tieUnder = paramApplet.getImage(paramURL, "tieUnder.gif");
  }
  
  public Image getTrebleClef()
  {
    return this.trebleClef;
  }
  
  public Image getBassClef()
  {
    return this.bassClef;
  }
  
  public Image getSemibreve()
  {
    return this.semibreve;
  }
  
  public Image getMinimUp()
  {
    return this.minimUp;
  }
  
  public Image getMinimDown()
  {
    return this.minimDown;
  }
  
  public Image getCrotchetUp()
  {
    return this.crotchetUp;
  }
  
  public Image getCrotchetDown()
  {
    return this.crotchetDown;
  }
  
  public Image getQuaverUp()
  {
    return this.quaverUp;
  }
  
  public Image getQuaverDown()
  {
    return this.quaverDown;
  }
  
  public Image getSemiquaverUp()
  {
    return this.semiquaverUp;
  }
  
  public Image getSemiquaverDown()
  {
    return this.semiquaverDown;
  }
  
  public Image getSemibreveRest()
  {
    return this.semibreveRest;
  }
  
  public Image getMinimRest()
  {
    return this.minimRest;
  }
  
  public Image getCrotchetRest()
  {
    return this.crotchetRest;
  }
  
  public Image getQuaverRest()
  {
    return this.quaverRest;
  }
  
  public Image getSemiquaverRest()
  {
    return this.semiquaverRest;
  }
  
  public Image getDot()
  {
    return this.dot;
  }
  
  public Image getSharp()
  {
    return this.sharp;
  }
  
  public Image getFlat()
  {
    return this.flat;
  }
  
  public Image getNatural()
  {
    return this.natural;
  }
  
  public Image getOne()
  {
    return this.one;
  }
  
  public Image getTwo()
  {
    return this.two;
  }
  
  public Image getThree()
  {
    return this.three;
  }
  
  public Image getFour()
  {
    return this.four;
  }
  
  public Image getFive()
  {
    return this.five;
  }
  
  public Image getSix()
  {
    return this.six;
  }
  
  public Image getSeven()
  {
    return this.seven;
  }
  
  public Image getEight()
  {
    return this.eight;
  }
  
  public Image getNine()
  {
    return this.nine;
  }
  
  public Image getDelete()
  {
    return this.delete;
  }
  
  public Image getTieOver()
  {
    return this.tieOver;
  }
  
  public Image getTieUnder()
  {
    return this.tieUnder;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\cpn\AppletImages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */