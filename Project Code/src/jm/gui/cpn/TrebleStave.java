package jm.gui.cpn;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Vector;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Phrase;

public class TrebleStave
  extends Stave
  implements JMC
{
  private Style style = new TrebleStave.Style.JMusic();
  private int tonic = 0;
  protected int[] scale = JMC.MAJOR_SCALE;
  public static final int MAX_HEIGHT = 500;
  public static final int MAX_WIDTH = 2000;
  private double beatCounter;
  private boolean isFirstNoteInTie = true;
  private boolean firstAccidentalDisplayed = false;
  private boolean isTied = false;
  private boolean semitoneShiftUp = false;
  private boolean extraImagesUsed;
  private int savedBeatWidth;
  private int savedBeatWidth2;
  private int lastChordDisplayed = -1;
  private int lastPosition = 0;
  private String[] chordStrings = { "I", "II", "III", "IV", "V", "VI", "VII", "." };
  
  public void setAccidentalDisplayStyle(Style paramStyle)
  {
    if (paramStyle == Style.TRADITIONAL) {
      this.style = new TrebleStave.Style.Trad();
    } else if (paramStyle == Style.JMUSIC) {
      this.style = new TrebleStave.Style.JMusic();
    } else {
      throw new RuntimeException("Unknown Accidental Display Style");
    }
  }
  
  public TrebleStave() {}
  
  public TrebleStave(Phrase paramPhrase)
  {
    super(paramPhrase);
  }
  
  public TrebleStave(Images paramImages)
  {
    super(paramImages);
  }
  
  public TrebleStave(Phrase paramPhrase, Images paramImages)
  {
    super(paramPhrase, paramImages);
  }
  
  public void paint(Graphics paramGraphics)
  {
    if (this.image == null)
    {
      this.image = createImage(2000, 500);
      this.g = this.image.getGraphics();
    }
    this.g.setFont(this.font);
    this.beatCounter = 0.0D;
    this.notePositions.removeAllElements();
    if (getDisplayTitle()) {
      this.g.drawString(this.title, this.rightMargin, this.bPos - 10);
    }
    int i = 0;
    this.style.initialise(this.keySignature);
    int j;
    int m;
    if ((this.keySignature > 0) && (this.keySignature < 8)) {
      for (j = 0; j < this.keySignature; j++)
      {
        m = this.notePosOffset[(this.sharps[j] % 12)] + this.bPos - 4 + (5 - this.sharps[j] / 12) * 24 + (6 - this.sharps[j] / 12) * 4;
        this.g.drawImage(this.sharp, this.rightMargin + this.clefWidth + i, m, this);
        i += 10;
        this.keySigWidth = i;
      }
    } else if ((this.keySignature < 0) && (this.keySignature > -8)) {
      for (j = 0; j < Math.abs(this.keySignature); j++)
      {
        m = this.notePosOffset[(this.flats[j] % 12)] + this.bPos - 4 + (5 - this.flats[j] / 12) * 24 + (6 - this.flats[j] / 12) * 4;
        this.g.drawImage(this.flat, this.rightMargin + this.clefWidth + i, m, this);
        i += 10;
      }
    }
    this.keySigWidth = (i + 3);
    if (this.metre != 0.0D)
    {
      Image[] arrayOfImage = { this.one, this.two, this.three, this.four, this.five, this.six, this.seven, this.eight, this.nine };
      this.g.drawImage(arrayOfImage[((int)this.metre - 1)], this.rightMargin + this.clefWidth + this.keySigWidth, this.bPos + 13, this);
      this.g.drawImage(this.four, this.rightMargin + this.clefWidth + this.keySigWidth, this.bPos + 29, this);
      this.timeSigWidth = 30;
    }
    else
    {
      this.timeSigWidth = 5;
    }
    this.totalBeatWidth = (this.rightMargin + this.clefWidth + this.keySigWidth + this.timeSigWidth);
    this.lastChordDisplayed = -1;
    for (int k = 0; k < this.phrase.size(); k++)
    {
      m = this.phrase.getNote(k).getPitch();
      int n;
      if ((m == Integer.MIN_VALUE) || (this.phrase.getNote(k).getRhythmValue() == 0.0D)) {
        n = this.notePosOffset[11] + this.bPos - 4 + 0 + 4;
      } else {
        n = this.notePosOffset[(m % 12)] + this.bPos - 4 + (5 - m / 12) * 24 + (6 - m / 12) * 4;
      }
      this.firstAccidentalDisplayed = false;
      this.semitoneShiftUp = false;
      this.isTied = false;
      this.isFirstNoteInTie = true;
      this.extraImagesUsed = false;
      this.savedBeatWidth = this.totalBeatWidth;
      this.savedBeatWidth2 = 0;
      double d1 = this.phrase.getNote(k).getRhythmValue();
      for (double d2 = this.metre - this.beatCounter % this.metre; d2 < d1; d2 = this.metre - this.beatCounter % this.metre)
      {
        this.isTied = true;
        drawNote(m, d2, n);
        d1 -= d2;
      }
      drawNote(m, d1, n);
    }
    int k;
    for (k = 0; k < 5; k++) {
      this.g.drawLine(this.rightMargin, this.bPos + this.imageHeightOffset - 2 * this.staveSpaceHeight + k * this.staveSpaceHeight, this.totalBeatWidth, this.bPos + this.imageHeightOffset - 2 * this.staveSpaceHeight + k * this.staveSpaceHeight);
    }
    this.g.setColor(Color.lightGray);
    for (k = 0; k < 5; k++) {
      this.g.drawLine(this.totalBeatWidth, this.bPos + this.imageHeightOffset - 2 * this.staveSpaceHeight + k * this.staveSpaceHeight, this.totalBeatWidth + 50, this.bPos + this.imageHeightOffset - 2 * this.staveSpaceHeight + k * this.staveSpaceHeight);
    }
    this.g.setColor(Color.black);
    this.g.drawImage(this.trebleClef, this.rightMargin + 7, this.bPos - 4, this);
    paramGraphics.drawImage(this.image, 0, 0, null);
    this.g.setColor(getBackground());
    this.g.fillRect(0, 0, 2000, 500);
    this.g.setColor(getForeground());
  }
  
  private void drawNote(int paramInt1, double paramDouble, int paramInt2)
  {
    this.requiresMoreThanOneImage = false;
    this.excessRhythmValue = 0.0D;
    chooseImage(paramInt1, paramDouble, 71, 0, 71);
    drawNote2(paramInt1, paramDouble - this.excessRhythmValue, paramInt2);
    if (this.requiresMoreThanOneImage)
    {
      drawNote(paramInt1, this.excessRhythmValue, paramInt2);
      this.extraImagesUsed = true;
    }
  }
  
  private void drawNote2(int paramInt1, double paramDouble, int paramInt2)
  {
    if ((paramInt1 != Integer.MIN_VALUE) && (paramDouble != 0.0D))
    {
      Accidental localAccidental = this.style.selectAccidental(paramInt1, paramDouble);
      if (localAccidental == Accidental.SHARP)
      {
        if (!this.firstAccidentalDisplayed) {
          displayImage(this.g, this.sharp, this.totalBeatWidth - 9, paramInt2);
        }
      }
      else if (localAccidental == Accidental.FLAT)
      {
        paramInt2 -= 4;
        if (!this.firstAccidentalDisplayed) {
          displayImage(this.g, this.flat, this.totalBeatWidth - 9, paramInt2);
        }
        paramInt1++;
        this.semitoneShiftUp = true;
      }
      else if ((localAccidental == Accidental.NATURAL) && (!this.firstAccidentalDisplayed))
      {
        displayImage(this.g, this.natural, this.totalBeatWidth - 7, paramInt2);
      }
    }
    this.firstAccidentalDisplayed = true;
    displayImage(this.g, this.currImage, this.totalBeatWidth, paramInt2);
    this.notePositions.addElement(new Integer(this.totalBeatWidth));
    this.notePositions.addElement(new Integer(paramInt2));
    int i;
    if (this.dottedNote)
    {
      i = 1;
      for (int j = 0; j < this.lineNotes.length; j++) {
        if ((this.lineNotes[j] + 12 == paramInt1) || (this.lineNotes[j] + 36 == paramInt1) || (this.lineNotes[j] + 60 == paramInt1) || (this.lineNotes[j] + 84 == paramInt1) || (this.lineNotes[j] + 108 == paramInt1) || (paramInt1 == Integer.MIN_VALUE))
        {
          displayImage(this.g, this.dot, this.totalBeatWidth + 1, paramInt2 - 4);
          i = 0;
          j = this.lineNotes.length;
        }
      }
      if (i != 0) {
        displayImage(this.g, this.dot, this.totalBeatWidth + 1, paramInt2);
      }
    }
    if ((paramInt1 <= 61) && (paramInt1 > -1) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos + 52, this.totalBeatWidth + 12, this.bPos + 52);
    }
    if ((paramInt1 <= 58) && (paramInt1 > -1) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos + 60, this.totalBeatWidth + 12, this.bPos + 60);
    }
    if ((paramInt1 <= 54) && (paramInt1 > -1) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos + 68, this.totalBeatWidth + 12, this.bPos + 68);
    }
    if ((paramInt1 <= 51) && (paramInt1 > -1) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos + 76, this.totalBeatWidth + 12, this.bPos + 76);
    }
    if ((paramInt1 <= 48) && (paramInt1 > -1) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos + 84, this.totalBeatWidth + 12, this.bPos + 84);
    }
    if ((paramInt1 >= 81) && (paramInt1 < 128) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos + 4, this.totalBeatWidth + 12, this.bPos + 4);
    }
    if ((paramInt1 >= 84) && (paramInt1 < 128) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos - 4, this.totalBeatWidth + 12, this.bPos - 4);
    }
    if ((paramInt1 >= 88) && (paramInt1 < 128) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos - 12, this.totalBeatWidth + 12, this.bPos - 12);
    }
    if ((paramInt1 >= 91) && (paramInt1 < 128) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos - 20, this.totalBeatWidth + 12, this.bPos - 20);
    }
    if ((paramInt1 >= 95) && (paramInt1 < 128) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos - 28, this.totalBeatWidth + 12, this.bPos - 28);
    }
    this.savedBeatWidth2 = this.totalBeatWidth;
    if (((this.isTied) || (this.extraImagesUsed)) && (this.isNote) && (!this.isFirstNoteInTie))
    {
      i = paramInt2 + 19 - (this.semitoneShiftUp ? 4 : 0);
      if (this.isUp) {
        this.g.drawImage(this.tieUnder, this.savedBeatWidth - 3 + 9, i + 17, this.savedBeatWidth2 + 19 - 9, i + 17 + this.tieUnder.getHeight(this), 0, 0, this.tieUnder.getWidth(this), this.tieUnder.getHeight(this), this);
      } else {
        this.g.drawImage(this.tieOver, this.savedBeatWidth - 3 + 9, i - 20, this.savedBeatWidth2 + 19 - 9, i - 20 + this.tieOver.getHeight(this), 0, 0, this.tieOver.getWidth(this), this.tieOver.getHeight(this), this);
      }
    }
    if ((this.isFirstNoteInTie == true) != false) {
      this.isFirstNoteInTie = false;
    }
    this.savedBeatWidth = this.totalBeatWidth;
    this.totalBeatWidth += this.currBeatWidth;
    this.dottedNote = false;
    this.beatCounter += (int)(paramDouble / 0.25D) * 0.25D;
    if ((this.metre != 0.0D) && (this.beatCounter % this.metre == 0.0D))
    {
      this.g.drawLine(this.totalBeatWidth, this.bPos + 12, this.totalBeatWidth, this.bPos + 44);
      this.style.processBarLine();
      if (this.barNumbers) {
        this.g.drawString("" + (int)(this.beatCounter / this.metre + 1.0D + this.phrase.getStartTime()), this.totalBeatWidth - 4, this.bPos);
      }
      this.totalBeatWidth += 12;
    }
  }
  
  private void displayImage(Graphics paramGraphics, Image paramImage, int paramInt1, int paramInt2)
  {
    paramGraphics.drawImage(paramImage, paramInt1, paramInt2, this);
  }
  
  public static abstract class Style
  {
    int[] sharpPitches = { 77, 72, 79, 74, 69, 76, 71 };
    int[] flatPitches = { 71, 76, 69, 74, 67, 72, 65 };
    public static final Style TRADITIONAL = new Trad();
    public static final Style JMUSIC = new JMusic();
    private String name;
    
    Style(String paramString)
    {
      this.name = paramString;
    }
    
    public String toString()
    {
      return this.name + " of displaying accidentals";
    }
    
    abstract void initialise(int paramInt);
    
    abstract TrebleStave.Accidental selectAccidental(int paramInt, double paramDouble);
    
    abstract void processBarLine();
    
    private static final class JMusic
      extends TrebleStave.Style
    {
      private Vector chromaticallyAffectedPitches = new Vector();
      private int keySignature;
      private int keyAccidentals;
      
      public JMusic()
      {
        super("");
        initialise(0);
      }
      
      void initialise(int paramInt)
      {
        this.chromaticallyAffectedPitches = new Vector();
        this.keySignature = paramInt;
        this.keyAccidentals = 0;
        int i;
        int j;
        int k;
        if ((paramInt > 0) && (paramInt < 8)) {
          for (i = 0; i < paramInt; i++)
          {
            j = this.sharpPitches[i] % 12;
            for (k = 0; k <= 127; k++) {
              if (k % 12 == j)
              {
                this.chromaticallyAffectedPitches.addElement(new Integer(k));
                this.keyAccidentals += 1;
              }
            }
          }
        } else if ((paramInt < 0) && (paramInt > -8)) {
          for (i = 0; i > paramInt; i--)
          {
            j = this.flatPitches[(-i)] % 12;
            for (k = 0; k <= 127; k++) {
              if (k % 12 == j)
              {
                this.chromaticallyAffectedPitches.addElement(new Integer(k));
                this.keyAccidentals += 1;
              }
            }
          }
        }
      }
      
      TrebleStave.Accidental selectAccidental(int paramInt, double paramDouble)
      {
        if ((paramInt == Integer.MIN_VALUE) || (paramDouble == 0.0D)) {
          return TrebleStave.Accidental.NONE;
        }
        if ((paramInt % 12 == 1) || (paramInt % 12 == 3) || (paramInt % 12 == 6) || (paramInt % 12 == 8) || (paramInt % 12 == 10))
        {
          if (this.keySignature > -1)
          {
            this.chromaticallyAffectedPitches.addElement(new Integer(paramInt - 1));
            return TrebleStave.Accidental.SHARP;
          }
          this.chromaticallyAffectedPitches.addElement(new Integer(paramInt + 1));
          return TrebleStave.Accidental.FLAT;
        }
        int i = this.chromaticallyAffectedPitches.size();
        for (int k = 0; k < i; k++)
        {
          int j = ((Integer)this.chromaticallyAffectedPitches.elementAt(k)).intValue();
          if (j == paramInt)
          {
            if (k > this.keyAccidentals - 1) {
              this.chromaticallyAffectedPitches.removeElementAt(k);
            }
            return TrebleStave.Accidental.NATURAL;
          }
        }
        return TrebleStave.Accidental.NONE;
      }
      
      void processBarLine() {}
    }
    
    private static final class Trad
      extends TrebleStave.Style
    {
      private boolean[] accidentalRequiredByKeySignature = new boolean[12];
      private static final int[] SHARP_ACCIDENTAL_PAIRS = { 0, 0, 1, 1, 2, 3, 3, 4, 4, 5, 5, 6 };
      private static final int[] FLAT_ACCIDENTAL_PAIRS = { 0, 1, 1, 2, 2, 3, 4, 4, 5, 5, 6, 6 };
      private int[] degreeToAccidentalPair = SHARP_ACCIDENTAL_PAIRS;
      private boolean[] accidentalInEffect = new boolean[7];
      private int keySignature = 0;
      
      public Trad()
      {
        super("");
        initialise(0);
      }
      
      private void setBooleanArrayToFalse(boolean[] paramArrayOfBoolean)
      {
        for (int i = 0; i < paramArrayOfBoolean.length; i++) {
          paramArrayOfBoolean[i] = false;
        }
      }
      
      void initialise(int paramInt)
      {
        this.keySignature = paramInt;
        if (paramInt < 0) {
          this.degreeToAccidentalPair = FLAT_ACCIDENTAL_PAIRS;
        } else {
          this.degreeToAccidentalPair = SHARP_ACCIDENTAL_PAIRS;
        }
        setBooleanArrayToFalse(this.accidentalRequiredByKeySignature);
        this.accidentalRequiredByKeySignature[1] = true;
        this.accidentalRequiredByKeySignature[3] = true;
        this.accidentalRequiredByKeySignature[6] = true;
        this.accidentalRequiredByKeySignature[8] = true;
        this.accidentalRequiredByKeySignature[10] = true;
        for (int i = 0; i < Math.abs(paramInt); i++) {
          if (paramInt < 0)
          {
            this.accidentalRequiredByKeySignature[(this.flatPitches[i] % 12)] = true;
            this.accidentalRequiredByKeySignature[((this.flatPitches[i] - 1) % 12)] = false;
          }
          else
          {
            this.accidentalRequiredByKeySignature[(this.sharpPitches[i] % 12)] = true;
            this.accidentalRequiredByKeySignature[((this.sharpPitches[i] + 1) % 12)] = false;
          }
        }
        setBooleanArrayToFalse(this.accidentalInEffect);
      }
      
      TrebleStave.Accidental selectAccidental(int paramInt, double paramDouble)
      {
        if ((paramInt == Integer.MIN_VALUE) || (paramDouble == 0.0D)) {
          return TrebleStave.Accidental.NONE;
        }
        int i = paramInt % 12;
        int j = this.degreeToAccidentalPair[i];
        if ((this.accidentalRequiredByKeySignature[i] ^ this.accidentalInEffect[j]) != false)
        {
          this.accidentalInEffect[j] = (boolean) (this.accidentalInEffect[j] == false ? 1 : false);
          if ((i == 1) || (i == 3) || (i == 6) || (i == 8) || (i == 10))
          {
            if (this.keySignature > -1) {
              return TrebleStave.Accidental.SHARP;
            }
            return TrebleStave.Accidental.FLAT;
          }
          return TrebleStave.Accidental.NATURAL;
        }
        return TrebleStave.Accidental.NONE;
      }
      
      void processBarLine()
      {
        setBooleanArrayToFalse(this.accidentalInEffect);
      }
    }
  }
  
  private static final class Accidental
  {
    public static final Accidental NONE = new Accidental("none");
    public static final Accidental SHARP = new Accidental("sharp");
    public static final Accidental NATURAL = new Accidental("natural");
    public static final Accidental FLAT = new Accidental("flat");
    private String name;
    
    Accidental(String paramString)
    {
      this.name = paramString;
    }
    
    public String toString()
    {
      return this.name;
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\cpn\TrebleStave.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */