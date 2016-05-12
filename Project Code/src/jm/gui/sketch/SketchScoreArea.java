package jm.gui.sketch;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Enumeration;
import java.util.Vector;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.music.tools.Mod;

public class SketchScoreArea
  extends Canvas
  implements JMC, KeyListener, MouseListener, MouseMotionListener
{
  private Score score;
  private int scoreChannels;
  private int currentChannel;
  private int oldY = 0;
  private Color[] theColors = new Color[10];
  private int maxWidth;
  private double beatWidth;
  private int x;
  private int y;
  private int newWidth = 650;
  private Vector drawPoints = new Vector();
  private int myHeight = 127;
  private SketchScore sc;
  
  public SketchScoreArea(Score paramScore, int paramInt, double paramDouble)
  {
    this.score = paramScore;
    this.scoreChannels = paramScore.size();
    this.currentChannel = this.scoreChannels;
    this.maxWidth = paramInt;
    this.beatWidth = paramDouble;
    setSize(paramInt, this.myHeight);
    this.score = paramScore;
    this.maxWidth = paramInt;
    addKeyListener(this);
    addMouseListener(this);
    addMouseMotionListener(this);
    setBackground(new Color(250, 250, 250));
    for (int i = 0; i < 10; i++)
    {
      Color localColor = new Color((int)(Math.random() * 256.0D), (int)(Math.random() * 256.0D), (int)(Math.random() * 256.0D));
      this.theColors[i] = localColor;
    }
  }
  
  public void setBeatWidth(double paramDouble)
  {
    this.beatWidth = paramDouble;
  }
  
  public void setScore(Score paramScore)
  {
    this.score = paramScore;
  }
  
  public int getHeight()
  {
    return this.myHeight;
  }
  
  public void setSketchScore(SketchScore paramSketchScore)
  {
    this.sc = paramSketchScore;
  }
  
  public int getNewWidth()
  {
    return this.newWidth;
  }
  
  public void paint(Graphics paramGraphics)
  {
    this.score.clean();
    Enumeration localEnumeration1 = this.score.getPartList().elements();
    int i = 0;
    while (localEnumeration1.hasMoreElements())
    {
      Object localObject = (Part)localEnumeration1.nextElement();
      paramGraphics.setColor(this.theColors[(i % 10)]);
      i++;
      Enumeration localEnumeration2 = ((Part)localObject).getPhraseList().elements();
      while (localEnumeration2.hasMoreElements())
      {
        Phrase localPhrase = (Phrase)localEnumeration2.nextElement();
        Enumeration localEnumeration3 = localPhrase.getNoteList().elements();
        Note localNote;
        for (double d = localPhrase.getStartTime(); localEnumeration3.hasMoreElements(); d += localNote.getRhythmValue())
        {
          localNote = (Note)localEnumeration3.nextElement();
          int i1 = -1;
          if (!localNote.getPitchType()) {
            i1 = localNote.getPitch();
          } else {
            i1 = Note.freqToMidiPitch(localNote.getFrequency());
          }
          if (i1 != Integer.MIN_VALUE)
          {
            int i2 = 127 - i1;
            int i3 = (int)Math.round(localNote.getDuration() * this.beatWidth);
            int i4 = (int)Math.round(d * this.beatWidth);
            paramGraphics.drawLine(i4, i2, i4 + i3, i2);
          }
        }
      }
    }
    paramGraphics.setColor(Color.black);
    Object localObject = this.drawPoints.elements();
    while (((Enumeration)localObject).hasMoreElements())
    {
      int j = ((Integer)((Enumeration)localObject).nextElement()).intValue();
      int k = ((Integer)((Enumeration)localObject).nextElement()).intValue();
      int m = ((Integer)((Enumeration)localObject).nextElement()).intValue();
      int n = ((Integer)((Enumeration)localObject).nextElement()).intValue();
      paramGraphics.drawLine(j, k, m, n);
    }
  }
  
  public void keyPressed(KeyEvent paramKeyEvent) {}
  
  public void keyReleased(KeyEvent paramKeyEvent) {}
  
  public void keyTyped(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getKeyChar() == '\b')
    {
      if (this.score.getPart(0).size() < 1) {
        return;
      }
      Vector localVector = this.score.getPartList();
      localVector.removeElementAt(localVector.size() - 1);
      if (this.sc == null) {
        repaint();
      } else {
        this.sc.update();
      }
      this.newWidth = 50;
    }
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {}
  
  public void mouseEntered(MouseEvent paramMouseEvent) {}
  
  public void mouseExited(MouseEvent paramMouseEvent) {}
  
  public void mouseMoved(MouseEvent paramMouseEvent) {}
  
  public void mousePressed(MouseEvent paramMouseEvent)
  {
    this.x = paramMouseEvent.getX();
    this.y = paramMouseEvent.getY();
  }
  
  public void mouseDragged(MouseEvent paramMouseEvent)
  {
    this.drawPoints.addElement(new Integer(this.x));
    this.drawPoints.addElement(new Integer(this.y));
    this.drawPoints.addElement(new Integer(paramMouseEvent.getX()));
    this.drawPoints.addElement(new Integer(paramMouseEvent.getY()));
    this.x = paramMouseEvent.getX();
    this.y = paramMouseEvent.getY();
    if ((paramMouseEvent.getX() > (int)(this.score.getEndTime() * this.beatWidth)) && (paramMouseEvent.getX() > this.newWidth)) {
      this.newWidth = paramMouseEvent.getX();
    }
    repaint();
  }
  
  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    if (this.drawPoints.size() > 0) {
      if (paramMouseEvent.getModifiers() == 24) {
        convertLineToPhrase(true);
      } else {
        convertLineToPhrase(false);
      }
    }
    this.drawPoints.removeAllElements();
    this.newWidth = 50;
    repaint();
  }
  
  private void convertLineToPhrase(boolean paramBoolean)
  {
    Phrase localPhrase = null;
    double[][] arrayOfDouble = new double[this.drawPoints.size() / 4][3];
    Enumeration localEnumeration = this.drawPoints.elements();
    for (int i = 0; localEnumeration.hasMoreElements(); i++)
    {
      int j = ((Integer)localEnumeration.nextElement()).intValue();
      int k = ((Integer)localEnumeration.nextElement()).intValue();
      int m = ((Integer)localEnumeration.nextElement()).intValue();
      int n = ((Integer)localEnumeration.nextElement()).intValue();
      if (paramBoolean)
      {
        for (int i1 = 0; i1 < Math.abs(k - n); i1++)
        {
          arrayOfDouble[i][0] = (((Integer)this.drawPoints.elementAt(i * 4)).intValue() / this.beatWidth + (Math.abs(m - j) / this.beatWidth / Math.abs(k - n) + 1.0D) / (Math.abs(k - n) + 1));
          if (((Integer)this.drawPoints.elementAt(i * 4)).intValue() > ((Integer)this.drawPoints.elementAt(i * 4 + 2)).intValue()) {
            arrayOfDouble[i][0] = (((Integer)this.drawPoints.elementAt(i * 4 + 2)).intValue() / this.beatWidth / Math.abs(k - n) + 1.0D);
          }
          arrayOfDouble[i][1] = (127 - k + i1);
          arrayOfDouble[i][2] = (Math.abs(m - j) / this.beatWidth / Math.abs(k - n) + 1.0D);
        }
      }
      else
      {
        arrayOfDouble[i][0] = (((Integer)this.drawPoints.elementAt(i * 4)).intValue() / this.beatWidth);
        if (((Integer)this.drawPoints.elementAt(i * 4)).intValue() > ((Integer)this.drawPoints.elementAt(i * 4 + 2)).intValue()) {
          arrayOfDouble[i][0] = (((Integer)this.drawPoints.elementAt(i * 4 + 2)).intValue() / this.beatWidth);
        }
        arrayOfDouble[i][1] = (127 - k);
        arrayOfDouble[i][2] = (Math.abs(m - j) / this.beatWidth);
      }
    }
    quickSort(arrayOfDouble, 0, arrayOfDouble.length - 1);
    if (this.drawPoints.size() > 0) {
      localPhrase = new Phrase(arrayOfDouble[0][0]);
    }
    Object localObject;
    for (int j = 0; j < arrayOfDouble.length - 1; j++)
    {
      if (arrayOfDouble[j][1] < 0.0D) {
        arrayOfDouble[j][1] = 0.0D;
      }
      if (arrayOfDouble[j][1] > 127.0D) {
        arrayOfDouble[j][1] = 127.0D;
      }
      localObject = new Note((int)arrayOfDouble[j][1], arrayOfDouble[(j + 1)][0] - arrayOfDouble[j][0]);
      if ((j > 0) && (!((Note)localObject).getPitchType()) && (!((Note)localPhrase.getNoteList().lastElement()).getPitchType()))
      {
        if ((localPhrase.size() > 0) && (((Note)localObject).getPitch() == ((Note)localPhrase.getNoteList().lastElement()).getPitch())) {
          Mod.append((Note)localPhrase.getNoteList().lastElement(), (Note)localObject);
        } else {
          localPhrase.addNote((Note)localObject);
        }
      }
      else {
        localPhrase.addNote((Note)localObject);
      }
    }
    if (arrayOfDouble[(arrayOfDouble.length - 1)][1] < 0.0D) {
      arrayOfDouble[(arrayOfDouble.length - 1)][1] = 0.0D;
    }
    if (arrayOfDouble[(arrayOfDouble.length - 1)][1] > 127.0D) {
      arrayOfDouble[(arrayOfDouble.length - 1)][1] = 127.0D;
    }
    Note localNote = new Note((int)arrayOfDouble[(arrayOfDouble.length - 1)][1], arrayOfDouble[(arrayOfDouble.length - 1)][2]);
    localNote.setDuration(arrayOfDouble[(arrayOfDouble.length - 1)][2]);
    localPhrase.addNote(localNote);
    if (localPhrase != null)
    {
      this.currentChannel += 1;
      if (this.currentChannel > 15) {
        this.currentChannel = this.scoreChannels;
      }
      localObject = new Part("Sketch Part", 0, this.currentChannel);
      ((Part)localObject).addPhrase(localPhrase);
      this.score.addPart((Part)localObject);
    }
  }
  
  private void quickSort(double[][] paramArrayOfDouble, int paramInt1, int paramInt2)
  {
    if (paramInt1 >= paramInt2) {
      return;
    }
    swap(paramArrayOfDouble, paramInt1, (int)(Math.random() * (paramInt2 - paramInt1)) + paramInt1);
    int j = paramInt1;
    for (int i = paramInt1 + 1; i <= paramInt2; i++) {
      if (paramArrayOfDouble[i][0] <= paramArrayOfDouble[paramInt1][0]) {
        swap(paramArrayOfDouble, ++j, i);
      }
    }
    swap(paramArrayOfDouble, paramInt1, j);
    quickSort(paramArrayOfDouble, paramInt1, j - 1);
    quickSort(paramArrayOfDouble, j + 1, paramInt2);
  }
  
  static void swap(double[][] paramArrayOfDouble, int paramInt1, int paramInt2)
  {
    for (int i = 0; i < 3; i++)
    {
      double d = paramArrayOfDouble[paramInt1][i];
      paramArrayOfDouble[paramInt1][i] = paramArrayOfDouble[paramInt2][i];
      paramArrayOfDouble[paramInt2][i] = d;
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\sketch\SketchScoreArea.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */