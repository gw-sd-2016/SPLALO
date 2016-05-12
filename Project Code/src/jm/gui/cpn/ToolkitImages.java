package jm.gui.cpn;

import java.awt.Image;
import java.awt.Toolkit;

public class ToolkitImages
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
  
  public ToolkitImages()
  {
    Toolkit localToolkit = Toolkit.getDefaultToolkit();
    this.trebleClef = localToolkit.getImage(Stave.class.getResource("graphics/trebleClef.gif"));
    this.bassClef = localToolkit.getImage(Stave.class.getResource("graphics/bassClef.gif"));
    this.crotchetDown = localToolkit.getImage(Stave.class.getResource("graphics/crotchetDown.gif"));
    this.crotchetUp = localToolkit.getImage(Stave.class.getResource("graphics/crotchetUp.gif"));
    this.quaverDown = localToolkit.getImage(Stave.class.getResource("graphics/quaverDown.gif"));
    this.quaverUp = localToolkit.getImage(Stave.class.getResource("graphics/quaverUp.gif"));
    this.semiquaverDown = localToolkit.getImage(Stave.class.getResource("graphics/semiquaverDown.gif"));
    this.semiquaverUp = localToolkit.getImage(Stave.class.getResource("graphics/semiquaverUp.gif"));
    this.minimDown = localToolkit.getImage(Stave.class.getResource("graphics/minimDown.gif"));
    this.minimUp = localToolkit.getImage(Stave.class.getResource("graphics/minimUp.gif"));
    this.semibreve = localToolkit.getImage(Stave.class.getResource("graphics/semibreve.gif"));
    this.dot = localToolkit.getImage(Stave.class.getResource("graphics/dot.gif"));
    this.semiquaverRest = localToolkit.getImage(Stave.class.getResource("graphics/semiquaverRest.gif"));
    this.quaverRest = localToolkit.getImage(Stave.class.getResource("graphics/quaverRest.gif"));
    this.crotchetRest = localToolkit.getImage(Stave.class.getResource("graphics/crotchetRest.gif"));
    this.minimRest = localToolkit.getImage(Stave.class.getResource("graphics/minimRest.gif"));
    this.semibreveRest = localToolkit.getImage(Stave.class.getResource("graphics/semibreveRest.gif"));
    this.sharp = localToolkit.getImage(Stave.class.getResource("graphics/sharp.gif"));
    this.flat = localToolkit.getImage(Stave.class.getResource("graphics/flat.gif"));
    this.natural = localToolkit.getImage(Stave.class.getResource("graphics/natural.gif"));
    this.one = localToolkit.getImage(Stave.class.getResource("graphics/one.gif"));
    this.two = localToolkit.getImage(Stave.class.getResource("graphics/two.gif"));
    this.three = localToolkit.getImage(Stave.class.getResource("graphics/three.gif"));
    this.four = localToolkit.getImage(Stave.class.getResource("graphics/four.gif"));
    this.five = localToolkit.getImage(Stave.class.getResource("graphics/five.gif"));
    this.six = localToolkit.getImage(Stave.class.getResource("graphics/six.gif"));
    this.seven = localToolkit.getImage(Stave.class.getResource("graphics/seven.gif"));
    this.eight = localToolkit.getImage(Stave.class.getResource("graphics/eight.gif"));
    this.nine = localToolkit.getImage(Stave.class.getResource("graphics/nine.gif"));
    this.delete = localToolkit.getImage(Stave.class.getResource("graphics/delete.gif"));
    this.tieOver = localToolkit.getImage(Stave.class.getResource("graphics/tieOver.gif"));
    this.tieUnder = localToolkit.getImage(Stave.class.getResource("graphics/tieUnder.gif"));
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


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\cpn\ToolkitImages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */