package jm.gui.show;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Enumeration;
import java.util.Vector;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

public class ShowArea
  extends Canvas
{
  private int oldX;
  private int maxColours = 8;
  private float[][] theColours = new float[this.maxColours][3];
  private int noteHeight = 5;
  private int w = 2 * this.noteHeight;
  private int ePos = 5 * this.noteHeight;
  private int e = this.ePos + this.noteHeight * 33;
  private int areaHeight = 77 * this.noteHeight;
  private int[] noteOffset = { 0, 0, this.noteHeight, this.noteHeight, this.noteHeight * 2, this.noteHeight * 3, this.noteHeight * 3, this.noteHeight * 4, this.noteHeight * 4, this.noteHeight * 5, this.noteHeight * 5, this.noteHeight * 6 };
  private Font font = new Font("Helvetica", 0, 10);
  private ShowPanel sp;
  private double beatWidth;
  private int thinNote = 2;
  
  public ShowArea(ShowPanel paramShowPanel)
  {
    this.sp = paramShowPanel;
    setSize((int)(paramShowPanel.score.getEndTime() * paramShowPanel.beatWidth), this.areaHeight);
    for (int i = 0; i < this.maxColours; i++)
    {
      this.theColours[i][0] = ((float)(Math.random() / this.maxColours / 2.0D) + (float)(1.0D / this.maxColours * i));
      this.theColours[i][1] = ((float)(Math.random() / this.maxColours) + (float)(1.0D / this.maxColours * i));
    }
  }
  
  private void reInit()
  {
    this.w = (2 * this.noteHeight);
    this.ePos = (5 * this.noteHeight);
    this.e = (this.ePos + this.noteHeight * 33);
    this.areaHeight = (77 * this.noteHeight);
    this.noteOffset = new int[] { 0, 0, this.noteHeight, this.noteHeight, this.noteHeight * 2, this.noteHeight * 3, this.noteHeight * 3, this.noteHeight * 4, this.noteHeight * 4, this.noteHeight * 5, this.noteHeight * 5, this.noteHeight * 6 };
    setSize(new Dimension(getSize().width, this.areaHeight));
    this.sp.updatePanelHeight();
  }
  
  public int getHeight()
  {
    return this.areaHeight;
  }
  
  public void setNoteHeight(int paramInt)
  {
    this.noteHeight = paramInt;
    reInit();
    repaint();
  }
  
  public void setThinNote(int paramInt)
  {
    if (paramInt >= 0) {
      this.thinNote = paramInt;
    }
    repaint();
  }
  
  public int getThinNote()
  {
    return this.thinNote;
  }
  
  public void paint(Graphics paramGraphics)
  {
    paramGraphics.setColor(Color.white);
    paramGraphics.fillRect(0, 0, getSize().width, getSize().height);
    paramGraphics.setFont(this.font);
    paramGraphics.setColor(Color.black);
    this.beatWidth = this.sp.beatWidth;
    int n = (int)Math.round(this.sp.score.getEndTime() * this.beatWidth);
    paramGraphics.drawLine(0, this.e, n, this.e);
    paramGraphics.drawLine(0, this.e - this.w, n, this.e - this.w);
    paramGraphics.drawLine(0, this.e - this.w * 2, n, this.e - this.w * 2);
    paramGraphics.drawLine(0, this.e - this.w * 3, n, this.e - this.w * 3);
    paramGraphics.drawLine(0, this.e - this.w * 4, n, this.e - this.w * 4);
    paramGraphics.drawLine(0, this.e + this.w * 2, n, this.e + this.w * 2);
    paramGraphics.drawLine(0, this.e + this.w * 3, n, this.e + this.w * 3);
    paramGraphics.drawLine(0, this.e + this.w * 4, n, this.e + this.w * 4);
    paramGraphics.drawLine(0, this.e + this.w * 5, n, this.e + this.w * 5);
    paramGraphics.drawLine(0, this.e + this.w * 6, n, this.e + this.w * 6);
    paramGraphics.setColor(Color.lightGray);
    paramGraphics.drawLine(0, this.e - this.w * 7, n, this.e - this.w * 7);
    paramGraphics.drawLine(0, this.e - this.w * 8, n, this.e - this.w * 8);
    paramGraphics.drawLine(0, this.e - this.w * 9, n, this.e - this.w * 9);
    paramGraphics.drawLine(0, this.e - this.w * 10, n, this.e - this.w * 10);
    paramGraphics.drawLine(0, this.e - this.w * 11, n, this.e - this.w * 11);
    paramGraphics.drawLine(0, this.e + this.w * 9, n, this.e + this.w * 9);
    paramGraphics.drawLine(0, this.e + this.w * 10, n, this.e + this.w * 10);
    paramGraphics.drawLine(0, this.e + this.w * 11, n, this.e + this.w * 11);
    paramGraphics.drawLine(0, this.e + this.w * 12, n, this.e + this.w * 12);
    paramGraphics.drawLine(0, this.e + this.w * 13, n, this.e + this.w * 13);
    for (int i1 = 0; i1 < n; i1 += 10)
    {
      paramGraphics.drawLine(i1, this.e + this.w, i1 + 1, this.e + this.w);
      paramGraphics.drawLine(i1, this.e - this.w * 5, i1 + 1, this.e - this.w * 5);
      paramGraphics.drawLine(i1, this.e - this.w * 6, i1 + 1, this.e - this.w * 6);
      paramGraphics.drawLine(i1, this.e - this.w * 12, i1 + 1, this.e - this.w * 12);
      paramGraphics.drawLine(i1, this.e - this.w * 13, i1 + 1, this.e - this.w * 13);
      paramGraphics.drawLine(i1, this.e - this.w * 14, i1 + 1, this.e - this.w * 14);
      paramGraphics.drawLine(i1, this.e - this.w * 15, i1 + 1, this.e - this.w * 15);
      paramGraphics.drawLine(i1, this.e - this.w * 16, i1 + 1, this.e - this.w * 16);
      paramGraphics.drawLine(i1, this.e - this.w * 17, i1 + 1, this.e - this.w * 17);
      paramGraphics.drawLine(i1, this.e - this.w * 18, i1 + 1, this.e - this.w * 18);
      paramGraphics.drawLine(i1, this.e + this.w * 7, i1 + 1, this.e + this.w * 7);
      paramGraphics.drawLine(i1, this.e + this.w * 8, i1 + 1, this.e + this.w * 8);
      paramGraphics.drawLine(i1, this.e + this.w * 14, i1 + 1, this.e + this.w * 14);
      paramGraphics.drawLine(i1, this.e + this.w * 15, i1 + 1, this.e + this.w * 15);
      paramGraphics.drawLine(i1, this.e + this.w * 16, i1 + 1, this.e + this.w * 16);
      paramGraphics.drawLine(i1, this.e + this.w * 17, i1 + 1, this.e + this.w * 17);
      paramGraphics.drawLine(i1, this.e + this.w * 18, i1 + 1, this.e + this.w * 18);
    }
    Enumeration localEnumeration1 = this.sp.score.getPartList().elements();
    for (int i2 = 0; localEnumeration1.hasMoreElements(); i2++)
    {
      Part localPart = (Part)localEnumeration1.nextElement();
      Enumeration localEnumeration2 = localPart.getPhraseList().elements();
      while (localEnumeration2.hasMoreElements())
      {
        Phrase localPhrase = (Phrase)localEnumeration2.nextElement();
        Enumeration localEnumeration3 = localPhrase.getNoteList().elements();
        double d = localPhrase.getStartTime();
        this.oldX = ((int)Math.round(d * this.beatWidth));
        int i = this.oldX;
        int j = 100000;
        int k = this.oldX;
        int m = 0;
        while (localEnumeration3.hasMoreElements())
        {
          Note localNote = (Note)localEnumeration3.nextElement();
          int i3 = -1;
          if (!localNote.getPitchType()) {
            i3 = localNote.getPitch();
          } else {
            i3 = Note.freqToMidiPitch(localNote.getFrequency());
          }
          if ((i3 <= 127) && (i3 >= 0))
          {
            int i4 = this.noteHeight * 7;
            int i5 = (10 - i3 / 12) * i4 + this.ePos - this.noteOffset[(i3 % 12)];
            int i6 = (int)Math.round(localNote.getDuration() * this.beatWidth);
            int i7 = (int)Math.round(localNote.getRhythmValue() * this.beatWidth);
            if (i6 < 1) {
              i6 = 1;
            }
            if (i5 < j) {
              j = i5;
            }
            if (i5 > m) {
              m = i5;
            }
            paramGraphics.setColor(Color.getHSBColor(this.theColours[(i2 % this.maxColours)][0], this.theColours[(i2 % this.maxColours)][1], (float)(0.7D - localNote.getDynamic() * 0.004D)));
            if (!localNote.getPitchType())
            {
              paramGraphics.fillRect(this.oldX, i5 - this.noteHeight + this.thinNote, i6, this.noteHeight * 2 - 2 * this.thinNote);
            }
            else
            {
              int i8 = 7;
              for (int i9 = this.oldX; i9 < this.oldX + i6 - 4; i9 += 4)
              {
                paramGraphics.drawLine(i9, i5 - this.noteHeight + i8, i9 + 2, i5 - this.noteHeight + i8 - 3);
                paramGraphics.drawLine(i9 + 2, i5 - this.noteHeight + i8 - 3, i9 + 4, i5 - this.noteHeight + i8);
              }
            }
            paramGraphics.setColor(Color.getHSBColor(this.theColours[(i2 % this.maxColours)][0], this.theColours[(i2 % this.maxColours)][1], 0.4F));
            paramGraphics.drawRect(this.oldX, i5 - this.noteHeight + this.thinNote, i7, this.noteHeight * 2 - 2 * this.thinNote);
            if ((i3 % 12 == 1) || (i3 % 12 == 3) || (i3 % 12 == 6) || (i3 % 12 == 8) || (i3 % 12 == 10))
            {
              paramGraphics.setColor(Color.getHSBColor(this.theColours[(i2 % this.maxColours)][0], this.theColours[(i2 % this.maxColours)][1], 0.3F));
              paramGraphics.drawString("#", this.oldX - 7, i5 + 5);
            }
          }
          d += localNote.getRhythmValue();
          this.oldX = ((int)Math.round(d * this.beatWidth));
          k = this.oldX - i;
        }
        paramGraphics.setColor(Color.getHSBColor(this.theColours[(i2 % this.maxColours)][0], this.theColours[(i2 % this.maxColours)][1], 0.9F));
        paramGraphics.drawRect(i - 1, j - this.noteHeight - 1, k + 1, m - j + this.noteHeight * 2 + 2);
      }
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\show\ShowArea.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */