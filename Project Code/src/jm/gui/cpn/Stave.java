package jm.gui.cpn;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintStream;
import java.util.Vector;
import jm.JMC;
import jm.music.data.CPhrase;
import jm.music.data.Phrase;
import jm.music.tools.Mod;

public abstract class Stave
  extends Panel
  implements JMC, KeyListener
{
  protected boolean requiresMoreThanOneImage = false;
  protected double excessRhythmValue = 0.0D;
  protected boolean isUp = true;
  protected boolean isNote = false;
  public Image image;
  protected Graphics g;
  protected Image trebleClef;
  protected Image bassClef;
  protected Image crotchetUp;
  protected Image crotchetDown;
  protected Image quaverDown;
  protected Image quaverUp;
  protected Image semiquaverDown;
  protected Image semiquaverUp;
  protected Image minimDown;
  protected Image minimUp;
  protected Image semibreve;
  protected Image dot;
  protected Image semiquaverRest;
  protected Image quaverRest;
  protected Image crotchetRest;
  protected Image minimRest;
  protected Image semibreveRest;
  protected Image sharp;
  protected Image flat;
  protected Image natural;
  protected Image one;
  protected Image two;
  protected Image three;
  protected Image four;
  protected Image five;
  protected Image six;
  protected Image seven;
  protected Image eight;
  protected Image nine;
  protected Image delete;
  protected Image tieOver;
  protected Image tieUnder;
  public int staveSpaceHeight = 8;
  public int rightMargin = 20;
  public int beatWidth = 43;
  public int staveWidth = this.beatWidth * 15;
  public int imageHeightOffset = 28;
  public int clefWidth = 38;
  public int timeSigWidth = 5;
  public int keySigWidth = 5;
  public int bPos = 28;
  protected Phrase phrase;
  protected Image currImage;
  protected int currBeatWidth;
  protected int totalBeatWidth;
  protected boolean dottedNote = false;
  protected int[] notePosOffset = { 24, 24, 20, 20, 16, 12, 12, 8, 8, 4, 4, 0 };
  protected double metre = 4.0D;
  protected int keySignature = 0;
  protected int[] sharps = { 77, 72, 79, 74, 69, 76, 71 };
  protected int[] flats = { 71, 76, 69, 74, 67, 72, 65 };
  protected Vector previouslyChromatic = new Vector();
  protected int[] lineNotes = { 0, 1, 4, 7, 8, 11, 14, 15, 17, 18, 21, 22 };
  public Vector notePositions = new Vector();
  protected int maxPitch = 127;
  protected int minPitch = 0;
  protected String title;
  protected boolean barNumbers = false;
  protected boolean editable = true;
  protected boolean qtOn = false;
  protected int panelHeight = 110;
  protected int staveDelta = 0;
  protected boolean displayTitle = false;
  protected Font font = new Font("Helvetica", 0, 10);
  protected int spacingValue = 70;
  
  public Stave()
  {
    this(new Phrase(), new ToolkitImages());
  }
  
  public Stave(Phrase paramPhrase)
  {
    this(paramPhrase, new ToolkitImages());
  }
  
  public Stave(Images paramImages)
  {
    this(new Phrase(), paramImages);
  }
  
  public Stave(Phrase paramPhrase, Images paramImages)
  {
    this.title = paramPhrase.getTitle();
    this.phrase = addRequiredRests(paramPhrase);
    setBackground(Color.getHSBColor(0.14F, 0.09F, 1.0F));
    setSize(this.beatWidth * this.spacingValue, this.panelHeight);
    if (getSize().width < (int)(this.phrase.getEndTime() * this.beatWidth * 1.5D)) {
      setSize((int)(this.phrase.getEndTime() * this.beatWidth * 1.5D), this.panelHeight);
    }
    StaveActionHandler localStaveActionHandler = new StaveActionHandler(this);
    addMouseListener(localStaveActionHandler);
    addMouseMotionListener(localStaveActionHandler);
    this.trebleClef = paramImages.getTrebleClef();
    this.bassClef = paramImages.getBassClef();
    this.crotchetDown = paramImages.getCrotchetDown();
    this.crotchetUp = paramImages.getCrotchetUp();
    this.quaverDown = paramImages.getQuaverDown();
    this.quaverUp = paramImages.getQuaverUp();
    this.semiquaverDown = paramImages.getSemiquaverDown();
    this.semiquaverUp = paramImages.getSemiquaverUp();
    this.minimDown = paramImages.getMinimDown();
    this.minimUp = paramImages.getMinimUp();
    this.semibreve = paramImages.getSemibreve();
    this.dot = paramImages.getDot();
    this.semiquaverRest = paramImages.getSemiquaverRest();
    this.quaverRest = paramImages.getQuaverRest();
    this.crotchetRest = paramImages.getCrotchetRest();
    this.minimRest = paramImages.getMinimRest();
    this.semibreveRest = paramImages.getSemibreveRest();
    this.sharp = paramImages.getSharp();
    this.flat = paramImages.getFlat();
    this.natural = paramImages.getNatural();
    this.one = paramImages.getOne();
    this.two = paramImages.getTwo();
    this.three = paramImages.getThree();
    this.four = paramImages.getFour();
    this.five = paramImages.getFive();
    this.six = paramImages.getSix();
    this.seven = paramImages.getSeven();
    this.eight = paramImages.getEight();
    this.nine = paramImages.getNine();
    this.delete = paramImages.getDelete();
    this.tieOver = paramImages.getTieOver();
    this.tieUnder = paramImages.getTieUnder();
  }
  
  public Phrase addRequiredRests(Phrase paramPhrase)
  {
    if (paramPhrase.getStartTime() > 0.0D)
    {
      Phrase localPhrase = new Phrase(0.0D);
      double d;
      for (d = paramPhrase.getStartTime(); d >= 4.0D; d -= 4.0D) {
        localPhrase.addNote(Integer.MIN_VALUE, 4.0D);
      }
      while (d >= 1.0D)
      {
        localPhrase.addNote(Integer.MIN_VALUE, 1.0D);
        d -= 1.0D;
      }
      localPhrase.addNote(Integer.MIN_VALUE, d);
      Mod.append(localPhrase, paramPhrase);
      paramPhrase = localPhrase;
    }
    return paramPhrase;
  }
  
  public void setPhrase(Phrase paramPhrase)
  {
    this.phrase = addRequiredRests(paramPhrase);
    this.previouslyChromatic.removeAllElements();
    repaint();
  }
  
  public void setCPhrase(CPhrase cPhrase) 
  {
	  this.phrase = addRequiredRests(cPhrase);
	  this.previouslyChromatic.removeAllElements();
	  repaint();
  }
  
  private CPhrase addRequiredRests(CPhrase paramPhrase) {
	  if (paramPhrase.getStartTime() > 0.0D)
	    {
	      CPhrase localPhrase = new CPhrase(0.0D);
	      double d;
	      for (d = paramPhrase.getStartTime(); d >= 4.0D; d -= 4.0D) {
	        localPhrase.addNote(Integer.MIN_VALUE, 4.0D);
	      }
	      while (d >= 1.0D)
	      {
	        localPhrase.addNote(Integer.MIN_VALUE, 1.0D);
	        d -= 1.0D;
	      }
	      localPhrase.addNote(Integer.MIN_VALUE, d);
	      Mod.append(localPhrase, paramPhrase);
	      paramPhrase = localPhrase;
	    }
	    return paramPhrase;
}

public Phrase getPhrase()
  {
    return this.phrase;
  }
  
  public void setTitle(String paramString)
  {
    this.title = paramString;
    if (this.phrase != null) {
      this.phrase.setTitle(paramString);
    }
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void removeTitle()
  {
    this.title = null;
  }
  
  public void setDisplayTitle(boolean paramBoolean)
  {
    this.displayTitle = paramBoolean;
    repaint();
  }
  
  public boolean getDisplayTitle()
  {
    return this.displayTitle;
  }
  
  public int getPanelHeight()
  {
    return this.panelHeight;
  }
  
  public void setMetre(double paramDouble)
  {
    this.metre = paramDouble;
  }
  
  public double getMetre()
  {
    return this.metre;
  }
  
  public int getMajorKey()
  {
    int[] arrayOfInt = { 11, 6, 1, 8, 3, 10, 5, 0, 7, 2, 9, 4, 11, 6, 1 };
    return arrayOfInt[(this.keySignature + 7)];
  }
  
  public void setKeySignature(int paramInt)
  {
    this.keySignature = paramInt;
  }
  
  public int getKeySignature()
  {
    return this.keySignature;
  }
  
  public void setBarNumbers(boolean paramBoolean)
  {
    this.barNumbers = paramBoolean;
  }
  
  public void setEditable(boolean paramBoolean)
  {
    this.editable = paramBoolean;
  }
  
  public int getMinPitch()
  {
    return this.minPitch;
  }
  
  public void setMinPitch(int paramInt)
  {
    this.minPitch = paramInt;
  }
  
  public int getMaxPitch()
  {
    return this.maxPitch;
  }
  
  public void setMaxPitch(int paramInt)
  {
    this.maxPitch = paramInt;
  }
  
  public int getTotalBeatWidth()
  {
    return this.totalBeatWidth;
  }
  
  public void setTotalBeatWidth(int paramInt)
  {
    this.totalBeatWidth = paramInt;
  }
  
  public boolean getBarNumbers()
  {
    return this.barNumbers;
  }
  
  public Dimension getPreferredSize()
  {
    return new Dimension(getSize().width, getSize().height);
  }
  
  public boolean getQtOn()
  {
    return this.qtOn;
  }
  
  public void setQtOn(boolean paramBoolean)
  {
    this.qtOn = paramBoolean;
  }
  
  public void updateChange() {}
  
  public void update(Graphics paramGraphics)
  {
    paint(paramGraphics);
  }
  
  public void paint(Graphics paramGraphics) {}
  
  public void deleteLastNote()
  {
    if (this.phrase.size() > 0)
    {
      this.phrase.removeNote(this.phrase.size() - 1);
      repaint();
      updateChange();
    }
  }
  
  protected void chooseImage(int paramInt1, double paramDouble, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt1 == Integer.MIN_VALUE)
    {
      this.isNote = false;
      if (paramDouble <= 0.0D)
      {
        this.currImage = this.delete;
        this.currBeatWidth = ((int)(this.beatWidth * 0.5D));
      }
      else if (paramDouble <= 0.2501D)
      {
        this.currImage = this.semiquaverRest;
        this.currBeatWidth = ((int)(this.beatWidth * 0.5D));
      }
      else if (paramDouble <= 0.501D)
      {
        this.currImage = this.quaverRest;
        this.currBeatWidth = ((int)(this.beatWidth * 0.6666666666666666D));
      }
      else if (paramDouble <= 0.7501D)
      {
        this.currImage = this.quaverRest;
        this.currBeatWidth = ((int)(this.beatWidth * 0.6666666666666666D));
        this.dottedNote = true;
      }
      else if (paramDouble <= 1.001D)
      {
        this.currImage = this.crotchetRest;
        this.currBeatWidth = this.beatWidth;
      }
      else if (paramDouble <= 1.2501D)
      {
        this.currImage = this.crotchetRest;
        this.currBeatWidth = this.beatWidth;
        this.requiresMoreThanOneImage = true;
        this.excessRhythmValue = (paramDouble - 1.0D);
      }
      else if (paramDouble <= 1.501D)
      {
        this.currImage = this.crotchetRest;
        this.currBeatWidth = ((int)(this.beatWidth * 1.5D));
        this.dottedNote = true;
      }
      else if (paramDouble <= 1.7501D)
      {
        this.currImage = this.crotchetRest;
        this.currBeatWidth = ((int)(this.beatWidth * 1.5D));
        this.dottedNote = true;
        this.requiresMoreThanOneImage = true;
        this.excessRhythmValue = (paramDouble - 1.5D);
      }
      else if (paramDouble <= 2.001D)
      {
        this.currImage = this.minimRest;
        this.currBeatWidth = ((int)(this.beatWidth * 1.7D));
      }
      else if (paramDouble <= 2.7501D)
      {
        this.currImage = this.minimRest;
        this.currBeatWidth = ((int)(this.beatWidth * 1.7D));
        this.requiresMoreThanOneImage = true;
        this.excessRhythmValue = (paramDouble - 2.0D);
      }
      else if (paramDouble <= 3.001D)
      {
        this.currImage = this.minimRest;
        this.currBeatWidth = ((int)(this.beatWidth * 1.9D));
        this.dottedNote = true;
      }
      else if (paramDouble <= 3.7501D)
      {
        this.currImage = this.minimRest;
        this.currBeatWidth = ((int)(this.beatWidth * 1.9D));
        this.dottedNote = true;
        this.requiresMoreThanOneImage = true;
        this.excessRhythmValue = (paramDouble - 3.0D);
      }
      else if (paramDouble <= 4.001D)
      {
        this.currImage = this.semibreveRest;
        this.currBeatWidth = ((int)(this.beatWidth * 0.5D));
      }
      else
      {
        this.currImage = this.semibreveRest;
        this.currBeatWidth = ((int)(this.beatWidth * 0.5D));
        this.requiresMoreThanOneImage = true;
        this.excessRhythmValue = (paramDouble - 4.0D);
      }
    }
    else
    {
      this.isNote = true;
      if (((paramInt1 < paramInt2) && (paramInt1 >= paramInt3)) || (paramInt1 < paramInt4))
      {
        this.isUp = true;
        if (paramDouble <= 0.001D)
        {
          this.currImage = this.delete;
          this.currBeatWidth = ((int)(this.beatWidth * 0.5D));
        }
        else if (paramDouble <= 0.2501D)
        {
          this.currImage = this.semiquaverUp;
          this.currBeatWidth = ((int)(this.beatWidth * 0.5D));
        }
        else if (paramDouble <= 0.501D)
        {
          this.currImage = this.quaverUp;
          this.currBeatWidth = ((int)(this.beatWidth * 0.6666666666666666D));
        }
        else if (paramDouble <= 0.7501D)
        {
          this.currImage = this.quaverUp;
          this.currBeatWidth = ((int)(this.beatWidth * 0.67D));
          this.dottedNote = true;
        }
        else if (paramDouble <= 1.001D)
        {
          this.currImage = this.crotchetUp;
          this.currBeatWidth = this.beatWidth;
        }
        else if (paramDouble <= 1.2501D)
        {
          this.currImage = this.crotchetUp;
          this.currBeatWidth = this.beatWidth;
          this.requiresMoreThanOneImage = true;
          this.excessRhythmValue = (paramDouble - 1.0D);
        }
        else if (paramDouble <= 1.501D)
        {
          this.currImage = this.crotchetUp;
          this.currBeatWidth = ((int)(this.beatWidth * 1.5D));
          this.dottedNote = true;
        }
        else if (paramDouble <= 1.7501D)
        {
          this.currImage = this.crotchetUp;
          this.currBeatWidth = ((int)(this.beatWidth * 1.5D));
          this.dottedNote = true;
          this.requiresMoreThanOneImage = true;
          this.excessRhythmValue = (paramDouble - 1.5D);
        }
        else if (paramDouble <= 2.001D)
        {
          this.currImage = this.minimUp;
          this.currBeatWidth = ((int)(this.beatWidth * 1.7D));
        }
        else if (paramDouble <= 2.7501D)
        {
          this.currImage = this.minimUp;
          this.currBeatWidth = ((int)(this.beatWidth * 1.7D));
          this.requiresMoreThanOneImage = true;
          this.excessRhythmValue = (paramDouble - 2.0D);
        }
        else if (paramDouble <= 3.001D)
        {
          this.currImage = this.minimUp;
          this.currBeatWidth = ((int)(this.beatWidth * 1.9D));
          this.dottedNote = true;
        }
        else if (paramDouble <= 3.7501D)
        {
          this.currImage = this.minimUp;
          this.currBeatWidth = ((int)(this.beatWidth * 1.9D));
          this.dottedNote = true;
          this.requiresMoreThanOneImage = true;
          this.excessRhythmValue = (paramDouble - 3.0D);
        }
        else if (paramDouble <= 4.001D)
        {
          this.currImage = this.semibreve;
          this.currBeatWidth = ((int)(this.beatWidth * 2.25D));
        }
        else
        {
          this.currImage = this.semibreve;
          this.currBeatWidth = ((int)(this.beatWidth * 2.25D));
          this.requiresMoreThanOneImage = true;
          this.excessRhythmValue = (paramDouble - 4.0D);
        }
      }
      else
      {
        this.isUp = false;
        if (paramDouble <= 0.001D)
        {
          this.currImage = this.delete;
          this.currBeatWidth = ((int)(this.beatWidth * 0.5D));
        }
        else if (paramDouble <= 0.2501D)
        {
          this.currImage = this.semiquaverDown;
          this.currBeatWidth = ((int)(this.beatWidth * 0.5D));
        }
        else if (paramDouble <= 0.501D)
        {
          this.currImage = this.quaverDown;
          this.currBeatWidth = ((int)(this.beatWidth * 0.6666666666666666D));
        }
        else if (paramDouble <= 0.7501D)
        {
          this.currImage = this.quaverDown;
          this.currBeatWidth = ((int)(this.beatWidth * 0.6666666666666666D));
          this.dottedNote = true;
        }
        else if (paramDouble <= 1.001D)
        {
          this.currImage = this.crotchetDown;
          this.currBeatWidth = this.beatWidth;
        }
        else if (paramDouble <= 1.2501D)
        {
          this.currImage = this.crotchetDown;
          this.currBeatWidth = this.beatWidth;
          this.requiresMoreThanOneImage = true;
          this.excessRhythmValue = (paramDouble - 1.0D);
        }
        else if (paramDouble <= 1.501D)
        {
          this.currImage = this.crotchetDown;
          this.currBeatWidth = ((int)(this.beatWidth * 1.5D));
          this.dottedNote = true;
        }
        else if (paramDouble <= 1.7501D)
        {
          this.currImage = this.crotchetDown;
          this.currBeatWidth = ((int)(this.beatWidth * 1.5D));
          this.dottedNote = true;
          this.requiresMoreThanOneImage = true;
          this.excessRhythmValue = (paramDouble - 1.5D);
        }
        else if (paramDouble <= 2.001D)
        {
          this.currImage = this.minimDown;
          this.currBeatWidth = ((int)(this.beatWidth * 1.7D));
        }
        else if (paramDouble <= 2.7501D)
        {
          this.currImage = this.minimDown;
          this.currBeatWidth = ((int)(this.beatWidth * 1.7D));
          this.requiresMoreThanOneImage = true;
          this.excessRhythmValue = (paramDouble - 2.0D);
        }
        else if (paramDouble <= 3.001D)
        {
          this.currImage = this.minimDown;
          this.currBeatWidth = ((int)(this.beatWidth * 1.9D));
          this.dottedNote = true;
        }
        else if (paramDouble <= 3.7501D)
        {
          this.currImage = this.minimDown;
          this.currBeatWidth = ((int)(this.beatWidth * 1.9D));
          this.dottedNote = true;
          this.requiresMoreThanOneImage = true;
          this.excessRhythmValue = (paramDouble - 3.0D);
        }
        else if (paramDouble <= 4.001D)
        {
          this.currImage = this.semibreve;
          this.currBeatWidth = ((int)(this.beatWidth * 2.25D));
        }
        else
        {
          this.currImage = this.semibreve;
          this.currBeatWidth = ((int)(this.beatWidth * 2.25D));
          this.requiresMoreThanOneImage = true;
          this.excessRhythmValue = (paramDouble - 4.0D);
        }
      }
    }
  }
  
  public void keyPressed(KeyEvent paramKeyEvent) {}
  
  public void keyReleased(KeyEvent paramKeyEvent) {}
  
  public void keyTyped(KeyEvent paramKeyEvent)
  {
    System.out.println(paramKeyEvent.getKeyChar());
  }


}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\cpn\Stave.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */