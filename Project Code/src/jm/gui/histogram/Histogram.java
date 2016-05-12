package jm.gui.histogram;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

public class Histogram
  extends Component
  implements JMC
{
  private Score score;
  private int maxPitchValue;
  private int maxRhythmValue;
  private int maxDynamicValue;
  private int maxPanValue;
  private int[] pitchValues;
  private int[] rhythmValues;
  private int[] dynamicValues;
  private int[] panValues;
  private Font f = new Font("Helvetica", 0, 9);
  private int barWidth = 4;
  private int lableSpace = 24;
  private int type = 0;
  private String title = "Pitch Histogram";
  private int xPos = 0;
  private int yPos = 0;
  
  public Histogram(Score paramScore)
  {
    this(paramScore, 0);
  }
  
  public Histogram(Score paramScore, int paramInt)
  {
    this(paramScore, paramInt, 0, 0);
  }
  
  public Histogram(Score paramScore, int paramInt1, int paramInt2, int paramInt3)
  {
    this(paramScore, paramInt1, paramInt2, paramInt3, 200);
  }
  
  public Histogram(Score paramScore, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.score = paramScore;
    this.type = paramInt1;
    this.xPos = paramInt2;
    this.yPos = paramInt3;
    if (paramInt1 == 1) {
      this.title = "Rhythm Histogram";
    }
    if (paramInt1 == 2) {
      this.title = "Dynamic Histogram";
    }
    if (paramInt1 == 3) {
      this.title = "Pan Histogram";
    }
    setSize(paramInt4, this.barWidth * 127 + this.lableSpace);
    analysis();
  }
  
  private void analysis()
  {
    this.pitchValues = new int[''];
    this.rhythmValues = new int[66];
    this.dynamicValues = new int[127];
    this.panValues = new int[100];
    this.maxPitchValue = 0;
    this.maxRhythmValue = 0;
    this.maxDynamicValue = 0;
    this.maxPanValue = 0;
    Enumeration localEnumeration1 = this.score.getPartList().elements();
    while (localEnumeration1.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration1.nextElement();
      Enumeration localEnumeration2 = localPart.getPhraseList().elements();
      while (localEnumeration2.hasMoreElements())
      {
        Phrase localPhrase = (Phrase)localEnumeration2.nextElement();
        Enumeration localEnumeration3 = localPhrase.getNoteList().elements();
        while (localEnumeration3.hasMoreElements())
        {
          Note localNote = (Note)localEnumeration3.nextElement();
          if ((!localNote.getPitchType()) && (localNote.getPitch() != Integer.MIN_VALUE))
          {
            this.pitchValues[localNote.getPitch()] += 1;
            if (this.pitchValues[localNote.getPitch()] > this.maxPitchValue) {
              this.maxPitchValue = this.pitchValues[localNote.getPitch()];
            }
            int i = (int)(localNote.getRhythmValue() / 0.125D);
            if (i >= this.rhythmValues.length) {
              i = this.rhythmValues.length - 1;
            }
            this.rhythmValues[i] += 1;
            if (this.rhythmValues[i] > this.maxRhythmValue) {
              this.maxRhythmValue = this.rhythmValues[i];
            }
            this.dynamicValues[localNote.getDynamic()] += 1;
            if (this.dynamicValues[localNote.getDynamic()] > this.maxDynamicValue) {
              this.maxDynamicValue = this.dynamicValues[localNote.getDynamic()];
            }
            this.panValues[((int)(localNote.getPan() * 100.0D))] += 1;
            if (this.panValues[((int)(localNote.getPan() * 100.0D))] > this.maxPanValue) {
              this.maxPanValue = this.panValues[((int)(localNote.getPan() * 100.0D))];
            }
          }
        }
      }
    }
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setType(int paramInt)
  {
    this.type = paramInt;
    repaint();
  }
  
  public int getXPos()
  {
    return this.xPos;
  }
  
  public int getYPos()
  {
    return this.yPos;
  }
  
  public void setScore(Score paramScore)
  {
    this.score = paramScore;
    analysis();
    repaint();
  }
  
  public void saveData()
  {
    FileDialog localFileDialog = new FileDialog(new Frame(), "Save histogram data as...", 1);
    localFileDialog.show();
    String str = localFileDialog.getFile();
    if (str != null) {
      saveDataAs(localFileDialog.getDirectory() + str);
    }
  }
  
  public void saveDataAs(String paramString)
  {
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(paramString);
      String str1 = "Pitch value" + String.valueOf("\t") + "Pitch data" + String.valueOf("\t") + "Rhythm value" + String.valueOf("\t") + "Rhythm data" + String.valueOf("\t") + "Dynamic value" + String.valueOf("\t") + "Dynamic data" + String.valueOf("\t") + "Pan value" + String.valueOf("\t") + "Pan data" + String.valueOf("\n");
      localFileOutputStream.write(str1.getBytes());
      for (int i = 0; i < this.pitchValues.length; i++)
      {
        String str2 = String.valueOf(i) + String.valueOf("\t") + String.valueOf(this.pitchValues[i]);
        if (i < this.rhythmValues.length) {
          str2 = str2 + String.valueOf("\t") + String.valueOf(i * 0.125D) + String.valueOf("\t") + String.valueOf(this.rhythmValues[i]);
        }
        if (i < this.dynamicValues.length) {
          str2 = str2 + String.valueOf("\t") + String.valueOf(i) + String.valueOf("\t") + String.valueOf(this.dynamicValues[i]);
        }
        if (i < this.panValues.length) {
          str2 = str2 + String.valueOf("\t") + String.valueOf(i / 100.0D) + String.valueOf("\t") + String.valueOf(this.panValues[i]);
        }
        str2 = str2 + String.valueOf("\n");
        localFileOutputStream.write(str2.getBytes());
      }
      localFileOutputStream.close();
    }
    catch (IOException localIOException) {}
  }
  
  public void paint(Graphics paramGraphics)
  {
    paramGraphics.setFont(this.f);
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics(this.f);
    int i = localFontMetrics.getAscent() / 2;
    int j = 0;
    if (this.type == 0) {
      j = this.maxPitchValue;
    } else if (this.type == 1) {
      j = this.maxRhythmValue;
    } else if (this.type == 2) {
      j = this.maxDynamicValue;
    } else if (this.type == 3) {
      j = this.maxPanValue;
    }
    for (int k = 0; k < 5; k++)
    {
      paramGraphics.setColor(Color.green);
      String str = "" + j / 4 * k;
      paramGraphics.drawString(str, getSize().width / 5 * k + this.lableSpace - localFontMetrics.stringWidth(str) / 2, this.lableSpace - localFontMetrics.getAscent() / 2);
      paramGraphics.setColor(Color.gray);
      paramGraphics.drawLine(getSize().width / 5 * k + this.lableSpace, this.lableSpace, getSize().width / 5 * k + this.lableSpace, getSize().height);
    }
    switch (this.type)
    {
    case 0: 
      paintPitches(paramGraphics);
      break;
    case 1: 
      paintRhythms(paramGraphics);
      break;
    case 2: 
      paintDynamics(paramGraphics);
      break;
    case 3: 
      paintPans(paramGraphics);
    }
  }
  
  private void paintPitches(Graphics paramGraphics)
  {
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics(this.f);
    int i = localFontMetrics.getAscent() / 2;
    for (int j = 0; j < 127; j++)
    {
      if (j % 12 == 0) {
        paramGraphics.setColor(Color.red);
      } else if (j % 12 == 4) {
        paramGraphics.setColor(Color.orange);
      } else if (j % 12 == 7) {
        paramGraphics.setColor(Color.blue);
      } else {
        paramGraphics.setColor(Color.black);
      }
      paramGraphics.fillRect(this.lableSpace, j * this.barWidth + this.lableSpace, (int)(this.pitchValues[j] / this.maxPitchValue * (getSize().width - this.lableSpace)), this.barWidth - 1);
      if (j % 12 == 0)
      {
        paramGraphics.setColor(Color.red);
        paramGraphics.drawString("C" + (j / 12 - 1), 2, j * this.barWidth + i + this.lableSpace);
      }
      if (j % 12 == 4)
      {
        paramGraphics.setColor(Color.orange);
        paramGraphics.drawString("E" + (j / 12 - 1), 2, j * this.barWidth + i + this.lableSpace);
      }
      if (j % 12 == 7)
      {
        paramGraphics.setColor(Color.blue);
        paramGraphics.drawString("G" + (j / 12 - 1), 2, j * this.barWidth + i + this.lableSpace);
      }
    }
  }
  
  private void paintRhythms(Graphics paramGraphics)
  {
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics(this.f);
    int i = localFontMetrics.getAscent();
    for (int j = 1; j < 65; j++)
    {
      if (j % 8 == 0) {
        paramGraphics.setColor(Color.red);
      } else if (j % 8 == 4) {
        paramGraphics.setColor(Color.orange);
      } else if ((j % 8 == 2) || (j % 8 == 6)) {
        paramGraphics.setColor(Color.blue);
      } else {
        paramGraphics.setColor(Color.black);
      }
      paramGraphics.fillRect(this.lableSpace, j * this.barWidth * 2 + this.lableSpace, (int)(this.rhythmValues[j] / this.maxRhythmValue * (getSize().width - this.lableSpace)), this.barWidth * 2 - 1);
      if (j % 8 == 0)
      {
        paramGraphics.setColor(Color.red);
        paramGraphics.drawString("" + j / 8.0D, 2, j * this.barWidth * 2 + i + this.lableSpace);
      }
      if (j % 8 == 4)
      {
        paramGraphics.setColor(Color.orange);
        paramGraphics.drawString("" + j / 8.0D, 2, j * this.barWidth * 2 + i + this.lableSpace);
      }
      if ((j % 8 == 2) || (j % 8 == 6))
      {
        paramGraphics.setColor(Color.blue);
        paramGraphics.drawString("" + j / 8.0D, 2, j * this.barWidth * 2 + i + this.lableSpace);
      }
    }
  }
  
  private void paintDynamics(Graphics paramGraphics)
  {
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics(this.f);
    int i = localFontMetrics.getAscent() / 2;
    for (int j = 1; j < 127; j++)
    {
      if (j % 10 == 0) {
        paramGraphics.setColor(Color.red);
      } else if (j % 10 == 5) {
        paramGraphics.setColor(Color.orange);
      } else {
        paramGraphics.setColor(Color.black);
      }
      paramGraphics.fillRect(this.lableSpace, j * this.barWidth + this.lableSpace, (int)(this.dynamicValues[j] / this.maxDynamicValue * (getSize().width - this.lableSpace)), this.barWidth - 1);
      if (j % 10 == 0)
      {
        paramGraphics.setColor(Color.red);
        paramGraphics.drawString("" + j, 2, j * this.barWidth + i + this.lableSpace);
      }
      if (j % 10 == 5)
      {
        paramGraphics.setColor(Color.orange);
        paramGraphics.drawString("" + j, 2, j * this.barWidth + i + this.lableSpace);
      }
    }
  }
  
  private void paintPans(Graphics paramGraphics)
  {
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics(this.f);
    int i = localFontMetrics.getAscent() / 2;
    for (int j = 1; j < 100; j++)
    {
      if ((j % 10 == 0) && (j != 50)) {
        paramGraphics.setColor(Color.red);
      } else if ((j % 10 == 5) && (j != 50)) {
        paramGraphics.setColor(Color.orange);
      } else if (j == 50) {
        paramGraphics.setColor(Color.blue);
      } else {
        paramGraphics.setColor(Color.black);
      }
      paramGraphics.fillRect(this.lableSpace, j * this.barWidth + this.lableSpace, (int)(this.panValues[j] / this.maxPanValue * (getSize().width - this.lableSpace)), this.barWidth - 1);
      if ((j % 10 == 0) && (j != 50))
      {
        paramGraphics.setColor(Color.red);
        paramGraphics.drawString("" + j / 100.0D, 2, j * this.barWidth + i + this.lableSpace);
      }
      else if ((j % 10 == 5) && (j != 50))
      {
        paramGraphics.setColor(Color.orange);
        paramGraphics.drawString("" + j / 100.0D, 2, j * this.barWidth + i + this.lableSpace);
      }
      else if (j == 50)
      {
        paramGraphics.setColor(Color.blue);
        paramGraphics.drawString("" + j / 100.0D, 2, j * this.barWidth + i + this.lableSpace);
      }
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\histogram\Histogram.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */