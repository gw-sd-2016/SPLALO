package jm.gui.cpn;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Vector;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Phrase;

public class PianoStave
  extends Stave
  implements JMC
{
  public PianoStave()
  {
    this.panelHeight = 160;
    setSize(this.beatWidth * this.spacingValue, this.panelHeight);
  }
  
  public PianoStave(Phrase paramPhrase)
  {
    super(paramPhrase);
    this.panelHeight = 160;
    setSize(this.beatWidth * this.spacingValue, this.panelHeight);
  }
  
  public void paint(Graphics paramGraphics)
  {
    if (this.image == null)
    {
      this.image = createImage(getSize().width, getSize().height);
      this.g = this.image.getGraphics();
    }
    this.g.setFont(this.font);
    double d = 0.0D;
    this.previouslyChromatic.removeAllElements();
    this.notePositions.removeAllElements();
    int i = 0;
    if (getDisplayTitle()) {
      this.g.drawString(this.title, this.rightMargin, this.bPos - 10);
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
        this.g.drawImage(this.sharp, this.rightMargin + this.clefWidth + j, n, this);
        this.g.drawImage(this.sharp, this.rightMargin + this.clefWidth + j, n + this.staveSpaceHeight * 7, this);
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
        this.g.drawImage(this.flat, this.rightMargin + this.clefWidth + j, n, this);
        this.g.drawImage(this.flat, this.rightMargin + this.clefWidth + j, n + this.staveSpaceHeight * 7, this);
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
    for (int m = 0; m < this.phrase.size(); m++)
    {
      n = this.phrase.getNote(m).getPitch();
      chooseImage(n, this.phrase.getNote(m).getRhythmValue(), 71, 60, 50);
      if ((n == Integer.MIN_VALUE) || (this.phrase.getNote(m).getRhythmValue() == 0.0D)) {
        i1 = this.notePosOffset[11] + this.bPos - 4 + 0 + 4;
      } else {
        i1 = this.notePosOffset[(n % 12)] + this.bPos - 4 + (5 - n / 12) * 24 + (6 - n / 12) * 4;
      }
      int i3;
      if (((n % 12 == 1) || (n % 12 == 3) || (n % 12 == 6) || (n % 12 == 8) || (n % 12 == 10)) && (n != Integer.MIN_VALUE) && (this.phrase.getNote(m).getRhythmValue() != 0.0D))
      {
        if (this.keySignature > -1)
        {
          this.g.drawImage(this.sharp, this.totalBeatWidth - 9, i1, this);
          this.previouslyChromatic.addElement(new Integer(n - 1));
        }
        else
        {
          i1 -= 4;
          this.g.drawImage(this.flat, this.totalBeatWidth - 9, i1, this);
          this.previouslyChromatic.addElement(new Integer(n + 1));
          n++;
        }
      }
      else
      {
        i2 = this.previouslyChromatic.size();
        for (i3 = 0; i3 < i2; i3++)
        {
          Integer localInteger = (Integer)this.previouslyChromatic.elementAt(i3);
          if ((localInteger.intValue() == n) && (n != Integer.MIN_VALUE) && (this.phrase.getNote(m).getRhythmValue() != 0.0D))
          {
            this.g.drawImage(this.natural, this.totalBeatWidth - 7, i1, this);
            if (i3 > i - 1) {
              this.previouslyChromatic.removeElementAt(i3);
            }
            i3 = i2;
          }
        }
      }
      this.g.drawImage(this.currImage, this.totalBeatWidth, i1, this);
      this.notePositions.addElement(new Integer(this.totalBeatWidth));
      this.notePositions.addElement(new Integer(i1));
      if (this.dottedNote)
      {
        i2 = 1;
        for (i3 = 0; i3 < this.lineNotes.length; i3++) {
          if ((this.lineNotes[i3] + 12 == n) || (this.lineNotes[i3] + 36 == n) || (this.lineNotes[i3] + 60 == n) || (this.lineNotes[i3] + 84 == n) || (this.lineNotes[i3] + 108 == n) || (n == Integer.MIN_VALUE))
          {
            this.g.drawImage(this.dot, this.totalBeatWidth + 1, i1 - 4, this);
            i2 = 0;
            i3 = this.lineNotes.length;
          }
        }
        if (i2 != 0) {
          this.g.drawImage(this.dot, this.totalBeatWidth + 1, i1, this);
        }
      }
      if ((n == 60) || ((n == 61) && (this.phrase.getNote(m).getRhythmValue() != 0.0D))) {
        this.g.drawLine(this.totalBeatWidth - 3, this.bPos + 52, this.totalBeatWidth + 12, this.bPos + 52);
      }
      if ((n <= 40) && (n > -1) && (this.phrase.getNote(m).getRhythmValue() != 0.0D)) {
        this.g.drawLine(this.totalBeatWidth - 3, this.bPos + 100, this.totalBeatWidth + 12, this.bPos + 100);
      }
      if ((n <= 37) && (n > -1) && (this.phrase.getNote(m).getRhythmValue() != 0.0D)) {
        this.g.drawLine(this.totalBeatWidth - 3, this.bPos + 108, this.totalBeatWidth + 12, this.bPos + 108);
      }
      if ((n <= 34) && (n > -1) && (this.phrase.getNote(m).getRhythmValue() != 0.0D)) {
        this.g.drawLine(this.totalBeatWidth - 3, this.bPos + 116, this.totalBeatWidth + 12, this.bPos + 116);
      }
      if ((n <= 30) && (n > -1) && (this.phrase.getNote(m).getRhythmValue() != 0.0D)) {
        this.g.drawLine(this.totalBeatWidth - 3, this.bPos + 124, this.totalBeatWidth + 12, this.bPos + 124);
      }
      if ((n <= 27) && (n > -1) && (this.phrase.getNote(m).getRhythmValue() != 0.0D)) {
        this.g.drawLine(this.totalBeatWidth - 3, this.bPos + 132, this.totalBeatWidth + 12, this.bPos + 132);
      }
      if ((n >= 81) && (n < 128) && (this.phrase.getNote(m).getRhythmValue() != 0.0D)) {
        this.g.drawLine(this.totalBeatWidth - 3, this.bPos + 4, this.totalBeatWidth + 12, this.bPos + 4);
      }
      if ((n >= 84) && (n < 128) && (this.phrase.getNote(m).getRhythmValue() != 0.0D)) {
        this.g.drawLine(this.totalBeatWidth - 3, this.bPos - 4, this.totalBeatWidth + 12, this.bPos - 4);
      }
      if ((n >= 88) && (n < 128) && (this.phrase.getNote(m).getRhythmValue() != 0.0D)) {
        this.g.drawLine(this.totalBeatWidth - 3, this.bPos - 12, this.totalBeatWidth + 12, this.bPos - 12);
      }
      if ((n >= 91) && (n < 128) && (this.phrase.getNote(m).getRhythmValue() != 0.0D)) {
        this.g.drawLine(this.totalBeatWidth - 3, this.bPos - 20, this.totalBeatWidth + 12, this.bPos - 20);
      }
      if ((n >= 95) && (n < 128) && (this.phrase.getNote(m).getRhythmValue() != 0.0D)) {
        this.g.drawLine(this.totalBeatWidth - 3, this.bPos - 28, this.totalBeatWidth + 12, this.bPos - 28);
      }
      this.totalBeatWidth += this.currBeatWidth;
      this.dottedNote = false;
      d += (int)(this.phrase.getNote(m).getRhythmValue() / 0.25D) * 0.25D;
      if ((this.metre != 0.0D) && (d % this.metre == 0.0D))
      {
        this.g.drawLine(this.totalBeatWidth, this.bPos + 12, this.totalBeatWidth, this.bPos + 44 + this.staveSpaceHeight * 6);
        if (this.barNumbers) {
          this.g.drawString("" + (int)(d / this.metre + 1.0D + this.phrase.getStartTime()), this.totalBeatWidth - 4, this.bPos);
        }
        this.totalBeatWidth += 12;
      }
    }
    int m;
    for (m = 0; m < 5; m++) {
      this.g.drawLine(this.rightMargin, this.bPos + this.imageHeightOffset - 2 * this.staveSpaceHeight + m * this.staveSpaceHeight, this.totalBeatWidth, this.bPos + this.imageHeightOffset - 2 * this.staveSpaceHeight + m * this.staveSpaceHeight);
    }
    for (m = 6; m < 11; m++) {
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
    this.g.fillRect(0, 0, getSize().width, getSize().height);
    this.g.setColor(getForeground());
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\cpn\PianoStave.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */