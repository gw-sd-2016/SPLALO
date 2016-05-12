package jm.gui.cpn;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;
import java.io.PrintStream;
import java.util.Vector;
import javax.swing.JPanel;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Phrase;
import jm.music.tools.ChordAnalysis;
import jm.music.tools.PhraseAnalysis;

public class JGrandStave
  extends JPanel
  implements JMC
{
  private int tonic = 0;
  RGBImageFilter filter = new RGBImageFilter()
  {
    public int filterRGB(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      return paramAnonymousInt3 | 0xFFFF0000;
    }
  };
  public static final int[] keys = { 11, 6, 1, 8, 3, 10, 5, 0, 7, 2, 9, 4, 11, 6, 1 };
  protected int[] scale = JMC.MAJOR_SCALE;
  private boolean isNormalColor = true;
  protected KeyChangeListener keyChangeListener = null;
  public Image image;
  protected Graphics g;
  protected RedFilter crotchetUp = new RedFilter();
  protected RedFilter crotchetDown = new RedFilter();
  protected RedFilter quaverDown = new RedFilter();
  protected RedFilter quaverUp = new RedFilter();
  protected RedFilter semiquaverDown = new RedFilter();
  protected RedFilter semiquaverUp = new RedFilter();
  protected RedFilter minimDown = new RedFilter();
  protected RedFilter minimUp = new RedFilter();
  protected RedFilter semibreve = new RedFilter();
  protected RedFilter dot = new RedFilter();
  protected RedFilter semiquaverRest = new RedFilter();
  protected RedFilter quaverRest = new RedFilter();
  protected RedFilter crotchetRest = new RedFilter();
  protected RedFilter minimRest = new RedFilter();
  protected RedFilter semibreveRest = new RedFilter();
  protected RedFilter sharp = new RedFilter();
  protected RedFilter flat = new RedFilter();
  protected RedFilter natural = new RedFilter();
  protected RedFilter delete = new RedFilter();
  protected RedFilter tie = new RedFilter(Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/tie.gif")));
  protected Image trebleClef;
  protected Image bassClef;
  protected Image one;
  protected Image two;
  protected Image three;
  protected Image four;
  protected Image five;
  protected Image six;
  protected Image seven;
  protected Image eight;
  protected Image nine;
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
  protected RedFilter currImage;
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
  public static final int MAX_HEIGHT = 500;
  public static final int MAX_WIDTH = 2000;
  private double beatCounter;
  private boolean isFirstNoteInTie = true;
  private boolean isNote = false;
  private boolean firstAccidentalDisplayed = false;
  private boolean isTied = false;
  private boolean isUp = true;
  private boolean semitoneShiftUp = false;
  private boolean extraImagesUsed;
  private boolean requiresMoreThanOneImage;
  private double excessRhythmValue;
  private int savedBeatWidth;
  private int savedBeatWidth2;
  private int lastChordDisplayed = -1;
  private int lastPosition = 0;
  private int[] firstChords = new int[0];
  private int[] secondChords = new int[0];
  private String[] chordStrings = { "I", "II", "III", "IV", "V", "VI", "VII", "." };
  
  public JGrandStave()
  {
    this(new Phrase());
    this.bPos = 110;
    this.panelHeight = 310;
    setSize(this.beatWidth * 40, this.panelHeight);
  }
  
  public JGrandStave(Phrase paramPhrase)
  {
    this.phrase = paramPhrase;
    this.title = paramPhrase.getTitle();
    setBackground(Color.getHSBColor(0.14F, 0.09F, 1.0F));
    setSize(this.beatWidth * 40, this.panelHeight);
    if (getSize().width < (int)(paramPhrase.getEndTime() * this.beatWidth * 1.5D)) {
      setSize((int)(paramPhrase.getEndTime() * this.beatWidth * 1.5D), this.panelHeight);
    }
    JStaveActionHandler localJStaveActionHandler = new JStaveActionHandler(this);
    addMouseListener(localJStaveActionHandler);
    addMouseMotionListener(localJStaveActionHandler);
    try
    {
      this.trebleClef = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/trebleClef.gif"));
      this.bassClef = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/bassClef.gif"));
      this.crotchetDown.image = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/crotchetDown.gif"));
      this.crotchetUp.image = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/crotchetUp.gif"));
      this.quaverDown.image = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/quaverDown.gif"));
      this.quaverUp.image = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/quaverUp.gif"));
      this.semiquaverDown.image = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/semiquaverDown.gif"));
      this.semiquaverUp.image = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/semiquaverUp.gif"));
      this.minimDown.image = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/minimDown.gif"));
      this.minimUp.image = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/minimUp.gif"));
      this.semibreve.image = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/semibreve.gif"));
      this.dot.image = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/dot.gif"));
      this.semiquaverRest.image = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/semiquaverRest.gif"));
      this.quaverRest.image = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/quaverRest.gif"));
      this.crotchetRest.image = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/crotchetRest.gif"));
      this.minimRest.image = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/minimRest.gif"));
      this.semibreveRest.image = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/semibreveRest.gif"));
      this.sharp.image = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/sharp.gif"));
      this.flat.image = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/flat.gif"));
      this.natural.image = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/natural.gif"));
      this.one = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/one.gif"));
      this.two = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/two.gif"));
      this.three = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/three.gif"));
      this.four = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/four.gif"));
      this.five = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/five.gif"));
      this.six = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/six.gif"));
      this.seven = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/seven.gif"));
      this.eight = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/eight.gif"));
      this.nine = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/nine.gif"));
      this.delete.image = Toolkit.getDefaultToolkit().getImage(Stave.class.getResource("graphics/delete.gif"));
    }
    catch (Exception localException)
    {
      System.out.println("Error while loading pictures...");
      localException.printStackTrace();
    }
    RGBImageFilter local2 = new RGBImageFilter()
    {
      public int filterRGB(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        return paramAnonymousInt3 | 0xFFFF0000;
      }
    };
    this.crotchetDown.redImage = createImage(new FilteredImageSource(this.crotchetDown.image.getSource(), local2));
    this.crotchetUp.redImage = createImage(new FilteredImageSource(this.crotchetUp.image.getSource(), local2));
    this.quaverDown.redImage = createImage(new FilteredImageSource(this.quaverDown.image.getSource(), local2));
    this.quaverUp.redImage = createImage(new FilteredImageSource(this.quaverUp.image.getSource(), local2));
    this.semiquaverDown.redImage = createImage(new FilteredImageSource(this.semiquaverDown.image.getSource(), local2));
    this.semiquaverUp.redImage = createImage(new FilteredImageSource(this.semiquaverUp.image.getSource(), local2));
    this.minimDown.redImage = createImage(new FilteredImageSource(this.minimDown.image.getSource(), local2));
    this.minimUp.redImage = createImage(new FilteredImageSource(this.minimUp.image.getSource(), local2));
    this.semibreve.redImage = createImage(new FilteredImageSource(this.semibreve.image.getSource(), local2));
    this.dot.redImage = createImage(new FilteredImageSource(this.dot.image.getSource(), local2));
    this.semiquaverRest.redImage = createImage(new FilteredImageSource(this.semiquaverRest.image.getSource(), local2));
    this.quaverRest.redImage = createImage(new FilteredImageSource(this.quaverRest.image.getSource(), local2));
    this.crotchetRest.redImage = createImage(new FilteredImageSource(this.crotchetRest.image.getSource(), local2));
    this.minimRest.redImage = createImage(new FilteredImageSource(this.minimRest.image.getSource(), local2));
    this.sharp.redImage = createImage(new FilteredImageSource(this.sharp.image.getSource(), local2));
    this.flat.redImage = createImage(new FilteredImageSource(this.flat.image.getSource(), local2));
    this.natural.redImage = createImage(new FilteredImageSource(this.natural.image.getSource(), local2));
    this.delete.redImage = createImage(new FilteredImageSource(this.delete.image.getSource(), local2));
    this.bPos = 110;
    this.panelHeight = 310;
    setSize(this.beatWidth * 40, this.panelHeight);
  }
  
  public void paintComponent(Graphics paramGraphics)
  {
    if (this.phrase == null) {
      return;
    }
    if (this.image == null)
    {
      this.image = createImage(2000, 500);
      this.g = this.image.getGraphics();
    }
    this.beatCounter = 0.0D;
    this.previouslyChromatic.removeAllElements();
    this.notePositions.removeAllElements();
    int i = 0;
    if (this.title != null) {
      this.g.drawString(this.title, this.rightMargin, this.bPos - 50);
    }
    int j = 0;
    int k;
    int n;
    int i1;
    int i2;
    if ((this.keySignature > 0) && (this.keySignature < 8)) {
      for (k = 0; k < this.keySignature; k++)
      {
        n = this.notePosOffset[(this.sharps[k] % 12)] + this.bPos - 4 + (5 - this.sharps[k] / 12) * 24 + (6 - this.sharps[k] / 12) * 4;
        this.g.drawImage(this.sharp.image, this.rightMargin + this.clefWidth + j, n, this);
        this.g.drawImage(this.sharp.image, this.rightMargin + this.clefWidth + j, n + this.staveSpaceHeight * 7, this);
        j += 10;
        i1 = this.sharps[k] % 12;
        for (i2 = 0; i2 < 128; i2++) {
          if (i2 % 12 == i1)
          {
            this.previouslyChromatic.addElement(new Integer(i2));
            i++;
          }
        }
        this.keySigWidth = j;
      }
    } else if ((this.keySignature < 0) && (this.keySignature > -8)) {
      for (k = 0; k < Math.abs(this.keySignature); k++)
      {
        n = this.notePosOffset[(this.flats[k] % 12)] + this.bPos - 4 + (5 - this.flats[k] / 12) * 24 + (6 - this.flats[k] / 12) * 4;
        this.g.drawImage(this.flat.image, this.rightMargin + this.clefWidth + j, n, this);
        this.g.drawImage(this.flat.image, this.rightMargin + this.clefWidth + j, n + this.staveSpaceHeight * 7, this);
        j += 10;
        i1 = this.flats[k] % 12;
        for (i2 = 0; i2 < 128; i2++) {
          if (i2 % 12 == i1)
          {
            this.previouslyChromatic.addElement(new Integer(i2));
            i++;
          }
        }
      }
    }
    this.keySigWidth = (j + 3);
    if (this.metre != 0.0D)
    {
      Image[] arrayOfImage = { this.one, this.two, this.three, this.four, this.five, this.six, this.seven, this.eight, this.nine };
      this.g.drawImage(arrayOfImage[((int)this.metre - 1)], this.rightMargin + this.clefWidth + this.keySigWidth, this.bPos + 13, this);
      this.g.drawImage(arrayOfImage[((int)this.metre - 1)], this.rightMargin + this.clefWidth + this.keySigWidth, this.bPos + 13 + this.staveSpaceHeight * 6, this);
      this.g.drawImage(this.four, this.rightMargin + this.clefWidth + this.keySigWidth, this.bPos + 29, this);
      this.g.drawImage(this.four, this.rightMargin + this.clefWidth + this.keySigWidth, this.bPos + 29 + this.staveSpaceHeight * 6, this);
      this.timeSigWidth = 30;
    }
    else
    {
      this.timeSigWidth = 5;
    }
    this.totalBeatWidth = (this.rightMargin + this.clefWidth + this.keySigWidth + this.timeSigWidth);
    this.firstChords = ChordAnalysis.getFirstPassChords(this.phrase, 1.0D, this.tonic, this.scale);
    this.secondChords = ChordAnalysis.getSecondPassChords(this.phrase, 1.0D, this.tonic, this.scale);
    this.lastChordDisplayed = -1;
    for (int m = 0; m < this.phrase.size(); m++)
    {
      n = this.phrase.getNote(m).getPitch();
      if ((n == Integer.MIN_VALUE) || (this.phrase.getNote(m).getRhythmValue() == 0.0D)) {
        i1 = this.notePosOffset[11] + this.bPos - 4 + 0 + 4;
      } else {
        i1 = this.notePosOffset[(n % 12)] + this.bPos - 4 + (5 - n / 12) * 24 + (6 - n / 12) * 4;
      }
      if ((n == Integer.MIN_VALUE) || (PhraseAnalysis.isScale(this.phrase.getNote(m), this.tonic, this.scale))) {
        this.isNormalColor = true;
      } else {
        this.isNormalColor = false;
      }
      this.firstAccidentalDisplayed = false;
      this.semitoneShiftUp = false;
      this.isTied = false;
      this.isFirstNoteInTie = true;
      this.extraImagesUsed = false;
      this.savedBeatWidth = this.totalBeatWidth;
      this.savedBeatWidth2 = 0;
      double d1 = this.phrase.getNote(m).getRhythmValue();
      for (double d2 = this.metre - this.beatCounter % this.metre; d2 < d1; d2 = this.metre - this.beatCounter % this.metre)
      {
        this.isTied = true;
        drawNote(n, d2, i1, i);
        d1 -= d2;
      }
      drawNote(n, d1, i1, i);
    }
   
    int m;
    for (m = 0; m < 5; m++) {
      this.g.drawLine(this.rightMargin, this.bPos + this.imageHeightOffset - 2 * this.staveSpaceHeight + m * this.staveSpaceHeight, this.totalBeatWidth, this.bPos + this.imageHeightOffset - 2 * this.staveSpaceHeight + m * this.staveSpaceHeight);
    }
    for (m = 6; m < 11; m++) {
      this.g.drawLine(this.rightMargin, this.bPos + this.imageHeightOffset - 2 * this.staveSpaceHeight + m * this.staveSpaceHeight, this.totalBeatWidth, this.bPos + this.imageHeightOffset - 2 * this.staveSpaceHeight + m * this.staveSpaceHeight);
    }
    this.g.setColor(Color.darkGray);
    for (m = -7; m < -2; m++) {
      this.g.drawLine(this.rightMargin, this.bPos + this.imageHeightOffset - 2 * this.staveSpaceHeight + m * this.staveSpaceHeight, this.totalBeatWidth, this.bPos + this.imageHeightOffset - 2 * this.staveSpaceHeight + m * this.staveSpaceHeight);
    }
    for (m = 13; m < 18; m++) {
      this.g.drawLine(this.rightMargin, this.bPos + this.imageHeightOffset - 2 * this.staveSpaceHeight + m * this.staveSpaceHeight, this.totalBeatWidth, this.bPos + this.imageHeightOffset - 2 * this.staveSpaceHeight + m * this.staveSpaceHeight);
    }
    this.g.setColor(Color.lightGray);
    for (m = 0; m < 5; m++) {
      this.g.drawLine(this.totalBeatWidth, this.bPos + this.imageHeightOffset - 2 * this.staveSpaceHeight + m * this.staveSpaceHeight, this.totalBeatWidth + 50, this.bPos + this.imageHeightOffset - 2 * this.staveSpaceHeight + m * this.staveSpaceHeight);
    }
    for (m = 6; m < 11; m++) {
      this.g.drawLine(this.totalBeatWidth, this.bPos + this.imageHeightOffset - 2 * this.staveSpaceHeight + m * this.staveSpaceHeight, this.totalBeatWidth + 50, this.bPos + this.imageHeightOffset - 2 * this.staveSpaceHeight + m * this.staveSpaceHeight);
    }
    this.g.setColor(Color.black);
    this.g.drawImage(this.trebleClef, this.rightMargin + 7, this.bPos - 4, this);
    this.g.drawImage(this.bassClef, this.rightMargin + 7, this.bPos + this.staveSpaceHeight * 6, this);
    paramGraphics.drawImage(this.image, 0, 0, null);
    this.g.setColor(getBackground());
    this.g.fillRect(0, 0, 2000, 500);
    this.g.setColor(getForeground());
  }
  
  private void drawNote(int paramInt1, double paramDouble, int paramInt2, int paramInt3)
  {
    this.requiresMoreThanOneImage = false;
    this.excessRhythmValue = 0.0D;
    if (this.beatCounter % 1.0D == 0.0D)
    {
      int i = (int)(this.beatCounter / 1.0D);
      int j = i - this.lastChordDisplayed;
      int k = j;
      while (this.lastChordDisplayed < i)
      {
        this.lastChordDisplayed += 1;
        k--;
        this.g.drawString(this.chordStrings[this.firstChords[this.lastChordDisplayed]], (int)(this.totalBeatWidth - (this.totalBeatWidth - this.lastPosition) * (k / j)), 20);
        int m = this.secondChords[this.lastChordDisplayed];
        String str = this.chordStrings[m];
        this.g.drawString(str, (int)(this.totalBeatWidth - (this.totalBeatWidth - this.lastPosition) * (k / j)), 40);
      }
      this.lastPosition = this.totalBeatWidth;
    }
    chooseImage(paramInt1, paramDouble, 71, 60, 50);
    drawNote2(paramInt1, paramDouble - this.excessRhythmValue, paramInt2, paramInt3);
    if (this.requiresMoreThanOneImage)
    {
      drawNote(paramInt1, this.excessRhythmValue, paramInt2, paramInt3);
      this.extraImagesUsed = true;
    }
  }
  
  private void drawNote2(int paramInt1, double paramDouble, int paramInt2, int paramInt3)
  {
    int i;
    int j;
    if (((paramInt1 % 12 == 1) || (paramInt1 % 12 == 3) || (paramInt1 % 12 == 6) || (paramInt1 % 12 == 8) || (paramInt1 % 12 == 10)) && (paramInt1 != Integer.MIN_VALUE) && (paramDouble != 0.0D))
    {
      if (this.keySignature > -1)
      {
        if (!this.firstAccidentalDisplayed) {
          displayImage(this.g, this.sharp, this.totalBeatWidth - 9, paramInt2);
        }
        this.previouslyChromatic.addElement(new Integer(paramInt1 - 1));
      }
      else
      {
        paramInt2 -= 4;
        if (!this.firstAccidentalDisplayed) {
          displayImage(this.g, this.flat, this.totalBeatWidth - 9, paramInt2);
        }
        this.previouslyChromatic.addElement(new Integer(paramInt1 + 1));
        paramInt1++;
        this.semitoneShiftUp = true;
      }
    }
    else
    {
      i = this.previouslyChromatic.size();
      for (j = 0; j < i; j++)
      {
        Integer localInteger = (Integer)this.previouslyChromatic.elementAt(j);
        if ((localInteger.intValue() == paramInt1) && (paramInt1 != Integer.MIN_VALUE) && (paramDouble != 0.0D))
        {
          if (!this.firstAccidentalDisplayed) {
            displayImage(this.g, this.natural, this.totalBeatWidth - 7, paramInt2);
          }
          if (j > paramInt3 - 1) {
            this.previouslyChromatic.removeElementAt(j);
          }
          j = i;
        }
      }
    }
    this.firstAccidentalDisplayed = true;
    displayImage(this.g, this.currImage, this.totalBeatWidth, paramInt2);
    this.notePositions.addElement(new Integer(this.totalBeatWidth));
    this.notePositions.addElement(new Integer(paramInt2));
    if (this.dottedNote)
    {
      i = 1;
      for (j = 0; j < this.lineNotes.length; j++) {
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
    if ((paramInt1 == 60) || ((paramInt1 == 61) && (paramDouble != 0.0D))) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos + 52, this.totalBeatWidth + 12, this.bPos + 52);
    }
    if ((paramInt1 <= 40) && (paramInt1 > -1) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos + 100, this.totalBeatWidth + 12, this.bPos + 100);
    }
    if ((paramInt1 <= 37) && (paramInt1 > -1) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos + 108, this.totalBeatWidth + 12, this.bPos + 108);
    }
    if ((paramInt1 <= 16) && (paramInt1 > -1) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos + 156, this.totalBeatWidth + 12, this.bPos + 156);
    }
    if ((paramInt1 <= 13) && (paramInt1 > -1) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos + 164, this.totalBeatWidth + 12, this.bPos + 164);
    }
    if ((paramInt1 <= 10) && (paramInt1 > -1) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos + 172, this.totalBeatWidth + 12, this.bPos + 172);
    }
    if ((paramInt1 <= 6) && (paramInt1 > -1) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos + 180, this.totalBeatWidth + 12, this.bPos + 180);
    }
    if ((paramInt1 <= 3) && (paramInt1 > -1) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos + 188, this.totalBeatWidth + 12, this.bPos + 188);
    }
    if ((paramInt1 >= 81) && (paramInt1 < 128) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos + 4, this.totalBeatWidth + 12, this.bPos + 4);
    }
    if ((paramInt1 >= 84) && (paramInt1 < 128) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos - 4, this.totalBeatWidth + 12, this.bPos - 4);
    }
    if ((paramInt1 >= 105) && (paramInt1 < 128) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos - 52, this.totalBeatWidth + 12, this.bPos - 52);
    }
    if ((paramInt1 >= 108) && (paramInt1 < 128) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos - 60, this.totalBeatWidth + 12, this.bPos - 60);
    }
    if ((paramInt1 >= 112) && (paramInt1 < 128) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos - 68, this.totalBeatWidth + 12, this.bPos - 68);
    }
    if ((paramInt1 >= 115) && (paramInt1 < 128) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos - 76, this.totalBeatWidth + 12, this.bPos - 76);
    }
    if ((paramInt1 >= 119) && (paramInt1 < 128) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos - 84, this.totalBeatWidth + 12, this.bPos - 84);
    }
    if ((paramInt1 >= 122) && (paramInt1 < 128) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos - 92, this.totalBeatWidth + 12, this.bPos - 92);
    }
    if ((paramInt1 >= 125) && (paramInt1 < 128) && (paramDouble != 0.0D)) {
      this.g.drawLine(this.totalBeatWidth - 3, this.bPos - 100, this.totalBeatWidth + 12, this.bPos - 100);
    }
    this.savedBeatWidth2 = this.totalBeatWidth;
    if (((this.isTied) || (this.extraImagesUsed)) && (this.isNote) && (!this.isFirstNoteInTie))
    {
      Image localImage = this.tie.image;
      if (!this.isNormalColor) {
        localImage = this.tie.redImage;
      }
      j = paramInt2 + 19 - (this.semitoneShiftUp ? 4 : 0);
      if (this.isUp) {
        this.g.drawImage(localImage, this.savedBeatWidth - 3 + 9, j + 17 + localImage.getHeight(this), this.savedBeatWidth2 + 19 - 9, j + 17, 0, 0, localImage.getWidth(this), localImage.getHeight(this), this);
      } else {
        this.g.drawImage(localImage, this.savedBeatWidth - 3 + 9, j - 20, this.savedBeatWidth2 + 19 - 9, j - 20 + localImage.getHeight(this), 0, 0, localImage.getWidth(this), localImage.getHeight(this), this);
      }
    }
    if ((this.isFirstNoteInTie = true) != false) {
      this.isFirstNoteInTie = false;
    }
    this.savedBeatWidth = this.totalBeatWidth;
    this.totalBeatWidth += this.currBeatWidth;
    this.dottedNote = false;
    this.beatCounter += (int)(paramDouble / 0.25D) * 0.25D;
    if ((this.metre != 0.0D) && (this.beatCounter % this.metre == 0.0D))
    {
      this.g.drawLine(this.totalBeatWidth, this.bPos + 12 - this.staveSpaceHeight * 7, this.totalBeatWidth, this.bPos + 44 + this.staveSpaceHeight * 13);
      if (this.barNumbers) {
        this.g.drawString("" + (int)(this.beatCounter / this.metre + 1.0D), this.totalBeatWidth - 4, this.bPos - 50);
      }
      this.totalBeatWidth += 12;
    }
  }
  
  public void displayImage(Graphics paramGraphics, RedFilter paramRedFilter, int paramInt1, int paramInt2)
  {
    if (this.isNormalColor) {
      paramGraphics.drawImage(paramRedFilter.image, paramInt1, paramInt2, this);
    } else {
      paramGraphics.drawImage(paramRedFilter.redImage, paramInt1, paramInt2, this);
    }
  }
  
  public Dimension getPreferredSize()
  {
    return new Dimension(2000, 500);
  }
  
  public void setPhrase(Phrase paramPhrase)
  {
    this.phrase = paramPhrase;
    this.previouslyChromatic.removeAllElements();
    setTitle(paramPhrase.getTitle());
    repaint();
  }
  
  public Phrase getPhrase()
  {
    return this.phrase;
  }
  
  public void setTitle(String paramString)
  {
    this.title = paramString;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void removeTitle()
  {
    this.title = null;
  }
  
  public void setMetre(double paramDouble)
  {
    this.metre = paramDouble;
  }
  
  public double getMetre()
  {
    return this.metre;
  }
  
  public void setScale(int[] paramArrayOfInt)
  {
    this.scale = paramArrayOfInt;
    setTonic(this.tonic);
  }
  
  public int getTonic()
  {
    return this.tonic;
  }
  
  public void setKey(int paramInt, int[] paramArrayOfInt)
  {
    this.scale = paramArrayOfInt;
    setTonic(paramInt);
  }
  
  public void setTonic(int paramInt)
  {
    if ((paramInt < 0) || (paramInt > 11)) {
      return;
    }
    if (this.scale == JMC.MAJOR_SCALE)
    {
      this.tonic = paramInt;
    }
    else if (this.scale == JMC.NATURAL_MINOR_SCALE)
    {
      this.tonic = paramInt;
      paramInt = (paramInt + 3) % 12;
    }
    else
    {
      return;
    }
    for (int i = 0; i < keys.length; i++) {
      if (keys[i] == paramInt) {
        this.keySignature = (i - 7);
      }
    }
    repaint();
    if (this.keyChangeListener == null) {
      return;
    }
    this.keyChangeListener.keyChanged();
  }
  
  public void setKeyChangeListener(KeyChangeListener paramKeyChangeListener)
  {
    this.keyChangeListener = paramKeyChangeListener;
  }
  
  public void setKeySignature(int paramInt)
  {
    this.keySignature = (paramInt < -7 ? -7 : paramInt > 7 ? 7 : paramInt);
    if (this.keyChangeListener == null) {
      return;
    }
    this.keyChangeListener.keyChanged();
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
  
  public boolean getQtOn()
  {
    return this.qtOn;
  }
  
  public void setQtOn(boolean paramBoolean)
  {
    this.qtOn = paramBoolean;
  }
  
  public void updateChange() {}
  
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
      if ((paramDouble > 0.0D) && (paramDouble <= 0.25D))
      {
        this.currImage = this.semiquaverRest;
        this.currBeatWidth = ((int)(this.beatWidth * 0.5D));
      }
      if ((paramDouble > 0.25D) && (paramDouble <= 0.5D))
      {
        this.currImage = this.quaverRest;
        this.currBeatWidth = ((int)(this.beatWidth * 0.67D));
      }
      if ((paramDouble > 0.5D) && (paramDouble <= 0.75D))
      {
        this.currImage = this.quaverRest;
        this.currBeatWidth = ((int)(this.beatWidth * 0.67D));
        this.dottedNote = true;
      }
      if ((paramDouble > 0.75D) && (paramDouble <= 1.0D))
      {
        this.currImage = this.crotchetRest;
        this.currBeatWidth = this.beatWidth;
      }
      if ((paramDouble > 1.0D) && (paramDouble <= 1.25D))
      {
        this.currImage = this.crotchetRest;
        this.currBeatWidth = this.beatWidth;
        this.requiresMoreThanOneImage = true;
        this.excessRhythmValue = (paramDouble - 1.0D);
      }
      if ((paramDouble > 1.25D) && (paramDouble <= 1.5D))
      {
        this.currImage = this.crotchetRest;
        this.currBeatWidth = ((int)(this.beatWidth * 1.5D));
        this.dottedNote = true;
      }
      if ((paramDouble > 1.5D) && (paramDouble <= 1.75D))
      {
        this.currImage = this.crotchetRest;
        this.currBeatWidth = ((int)(this.beatWidth * 1.5D));
        this.dottedNote = true;
        this.requiresMoreThanOneImage = true;
        this.excessRhythmValue = (paramDouble - 1.5D);
      }
      if ((paramDouble > 1.75D) && (paramDouble <= 2.0D))
      {
        this.currImage = this.minimRest;
        this.currBeatWidth = ((int)(this.beatWidth * 1.7D));
      }
      if ((paramDouble > 2.0D) && (paramDouble <= 2.75D))
      {
        this.currImage = this.minimRest;
        this.currBeatWidth = ((int)(this.beatWidth * 1.7D));
        this.requiresMoreThanOneImage = true;
        this.excessRhythmValue = (paramDouble - 2.0D);
      }
      if ((paramDouble > 2.75D) && (paramDouble <= 3.0D))
      {
        this.currImage = this.minimRest;
        this.currBeatWidth = ((int)(this.beatWidth * 1.9D));
        this.dottedNote = true;
      }
      if ((paramDouble > 3.0D) && (paramDouble <= 3.75D))
      {
        this.currImage = this.minimRest;
        this.currBeatWidth = ((int)(this.beatWidth * 1.9D));
        this.dottedNote = true;
        this.requiresMoreThanOneImage = true;
        this.excessRhythmValue = (paramDouble - 3.0D);
      }
      if ((paramDouble > 3.75D) && (paramDouble <= 4.0D))
      {
        this.currImage = this.semibreveRest;
        this.currBeatWidth = ((int)(this.beatWidth * 0.5D));
      }
    }
    else
    {
      this.isNote = true;
      if (((paramInt1 < paramInt2) && (paramInt1 >= paramInt3)) || (paramInt1 < paramInt4))
      {
        if (paramDouble <= 0.0D)
        {
          this.currImage = this.delete;
          this.currBeatWidth = ((int)(this.beatWidth * 0.5D));
          this.isUp = true;
        }
        if ((paramDouble > 0.0D) && (paramDouble <= 0.25D))
        {
          this.currImage = this.semiquaverUp;
          this.currBeatWidth = ((int)(this.beatWidth * 0.5D));
          this.isUp = true;
        }
        if ((paramDouble > 0.25D) && (paramDouble <= 0.5D))
        {
          this.currImage = this.quaverUp;
          this.currBeatWidth = ((int)(this.beatWidth * 0.67D));
          this.isUp = true;
        }
        if ((paramDouble > 0.5D) && (paramDouble <= 0.75D))
        {
          this.currImage = this.quaverUp;
          this.currBeatWidth = ((int)(this.beatWidth * 0.67D));
          this.dottedNote = true;
          this.isUp = true;
        }
        if ((paramDouble > 0.75D) && (paramDouble <= 1.0D))
        {
          this.currImage = this.crotchetUp;
          this.currBeatWidth = this.beatWidth;
          this.isUp = true;
        }
        if ((paramDouble > 1.0D) && (paramDouble <= 1.25D))
        {
          this.currImage = this.crotchetUp;
          this.currBeatWidth = this.beatWidth;
          this.requiresMoreThanOneImage = true;
          this.excessRhythmValue = (paramDouble - 1.0D);
          this.isUp = true;
        }
        if ((paramDouble > 1.25D) && (paramDouble <= 1.5D))
        {
          this.currImage = this.crotchetUp;
          this.currBeatWidth = ((int)(this.beatWidth * 1.5D));
          this.dottedNote = true;
          this.isUp = true;
        }
        if ((paramDouble > 1.5D) && (paramDouble <= 1.75D))
        {
          this.currImage = this.crotchetUp;
          this.currBeatWidth = ((int)(this.beatWidth * 1.5D));
          this.dottedNote = true;
          this.requiresMoreThanOneImage = true;
          this.excessRhythmValue = (paramDouble - 1.5D);
          this.isUp = true;
        }
        if ((paramDouble > 1.75D) && (paramDouble <= 2.0D))
        {
          this.currImage = this.minimUp;
          this.currBeatWidth = ((int)(this.beatWidth * 1.7D));
          this.isUp = true;
        }
        if ((paramDouble > 2.0D) && (paramDouble <= 2.75D))
        {
          this.currImage = this.minimUp;
          this.currBeatWidth = ((int)(this.beatWidth * 1.7D));
          this.requiresMoreThanOneImage = true;
          this.excessRhythmValue = (paramDouble - 2.0D);
          this.isUp = true;
        }
        if ((paramDouble > 2.75D) && (paramDouble <= 3.0D))
        {
          this.currImage = this.minimUp;
          this.currBeatWidth = ((int)(this.beatWidth * 1.9D));
          this.dottedNote = true;
          this.isUp = true;
        }
        if ((paramDouble > 3.0D) && (paramDouble <= 3.75D))
        {
          this.currImage = this.minimUp;
          this.currBeatWidth = ((int)(this.beatWidth * 1.9D));
          this.dottedNote = true;
          this.requiresMoreThanOneImage = true;
          this.excessRhythmValue = (paramDouble - 3.0D);
          this.isUp = true;
        }
        if ((paramDouble > 3.75D) && (paramDouble <= 4.0D))
        {
          this.currImage = this.semibreve;
          this.currBeatWidth = ((int)(this.beatWidth * 2.25D));
          this.isUp = true;
        }
      }
      else
      {
        if (paramDouble <= 0.0D)
        {
          this.currImage = this.delete;
          this.currBeatWidth = ((int)(this.beatWidth * 0.5D));
          this.isUp = false;
        }
        if ((paramDouble > 0.0D) && (paramDouble <= 0.25D))
        {
          this.currImage = this.semiquaverDown;
          this.currBeatWidth = ((int)(this.beatWidth * 0.5D));
          this.isUp = false;
        }
        if ((paramDouble > 0.25D) && (paramDouble <= 0.5D))
        {
          this.currImage = this.quaverDown;
          this.currBeatWidth = ((int)(this.beatWidth * 0.67D));
          this.isUp = false;
        }
        if ((paramDouble > 0.5D) && (paramDouble <= 0.75D))
        {
          this.currImage = this.quaverDown;
          this.currBeatWidth = ((int)(this.beatWidth * 0.67D));
          this.dottedNote = true;
          this.isUp = false;
        }
        if ((paramDouble > 0.75D) && (paramDouble <= 1.0D))
        {
          this.currImage = this.crotchetDown;
          this.currBeatWidth = this.beatWidth;
          this.isUp = false;
        }
        if ((paramDouble > 1.0D) && (paramDouble <= 1.25D))
        {
          this.currImage = this.crotchetDown;
          this.currBeatWidth = this.beatWidth;
          this.requiresMoreThanOneImage = true;
          this.excessRhythmValue = (paramDouble - 1.0D);
          this.isUp = false;
        }
        if ((paramDouble > 1.25D) && (paramDouble <= 1.5D))
        {
          this.currImage = this.crotchetDown;
          this.currBeatWidth = ((int)(this.beatWidth * 1.5D));
          this.dottedNote = true;
          this.isUp = false;
        }
        if ((paramDouble > 1.5D) && (paramDouble <= 1.75D))
        {
          this.currImage = this.crotchetDown;
          this.currBeatWidth = ((int)(this.beatWidth * 1.5D));
          this.dottedNote = true;
          this.requiresMoreThanOneImage = true;
          this.excessRhythmValue = (paramDouble - 1.5D);
          this.isUp = false;
        }
        if ((paramDouble > 1.75D) && (paramDouble <= 2.0D))
        {
          this.currImage = this.minimDown;
          this.currBeatWidth = ((int)(this.beatWidth * 1.7D));
          this.isUp = false;
        }
        if ((paramDouble > 2.0D) && (paramDouble <= 2.75D))
        {
          this.currImage = this.minimDown;
          this.currBeatWidth = ((int)(this.beatWidth * 1.7D));
          this.requiresMoreThanOneImage = true;
          this.excessRhythmValue = (paramDouble - 2.0D);
          this.isUp = false;
        }
        if ((paramDouble > 2.75D) && (paramDouble <= 3.0D))
        {
          this.currImage = this.minimDown;
          this.currBeatWidth = ((int)(this.beatWidth * 1.9D));
          this.dottedNote = true;
          this.isUp = false;
        }
        if ((paramDouble > 3.0D) && (paramDouble <= 3.75D))
        {
          this.currImage = this.minimDown;
          this.currBeatWidth = ((int)(this.beatWidth * 1.9D));
          this.dottedNote = true;
          this.requiresMoreThanOneImage = true;
          this.excessRhythmValue = (paramDouble - 3.0D);
          this.isUp = false;
        }
        if ((paramDouble > 3.75D) && (paramDouble <= 4.0D))
        {
          this.currImage = this.semibreve;
          this.currBeatWidth = ((int)(this.beatWidth * 2.25D));
          this.isUp = false;
        }
      }
    }
  }
  
  class RedFilter
  {
    public Image image;
    public Image redImage;
    
    RedFilter() {}
    
    RedFilter(Image paramImage)
    {
      this.image = paramImage;
      this.redImage = JGrandStave.this.createImage(new FilteredImageSource(paramImage.getSource(), JGrandStave.this.filter));
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\cpn\JGrandStave.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */