package jm.gui.sketch;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import jm.music.data.Score;

public class SketchRuler
  extends Canvas
  implements MouseListener, MouseMotionListener, KeyListener
{
  private int startX;
  private int height = 15;
  private int barNumbCount = 2;
  private SketchScore sketchScore;
  private Font font = new Font("Helvetica", 0, 10);
  
  public SketchRuler(SketchScore paramSketchScore)
  {
    this.sketchScore = paramSketchScore;
    setSize((int)(SketchScore.score.getEndTime() * paramSketchScore.beatWidth), this.height);
    setBackground(Color.lightGray);
    addMouseListener(this);
    addMouseMotionListener(this);
    addKeyListener(this);
    setCursor(new Cursor(13));
  }
  
  public int getHeight()
  {
    return this.height;
  }
  
  public void paint(Graphics paramGraphics)
  {
    double d = this.sketchScore.beatWidth;
    paramGraphics.setFont(this.font);
    for (int i = 0; i < this.sketchScore.getSketchScoreArea().getNewWidth(); i++)
    {
      int j = (int)Math.round(i * d);
      if (i % this.barNumbCount == 0)
      {
        paramGraphics.drawLine(j, 0, j, this.height);
        if (d > 15.0D) {
          paramGraphics.drawString("" + i, j + 2, this.height - 2);
        }
      }
      else
      {
        paramGraphics.drawLine(j, this.height / 2, j, this.height);
      }
    }
  }
  
  public void mousePressed(MouseEvent paramMouseEvent)
  {
    setCursor(new Cursor(10));
    this.startX = paramMouseEvent.getX();
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {}
  
  public void mouseEntered(MouseEvent paramMouseEvent) {}
  
  public void mouseExited(MouseEvent paramMouseEvent) {}
  
  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    setCursor(new Cursor(13));
    repaint();
  }
  
  public void mouseMoved(MouseEvent paramMouseEvent) {}
  
  public void mouseDragged(MouseEvent paramMouseEvent)
  {
    double d = this.sketchScore.beatWidth;
    d += (paramMouseEvent.getX() - this.startX) / 5.0D;
    if (d < 1.0D) {
      d = 1.0D;
    }
    if (d > 256.0D) {
      d = 256.0D;
    }
    this.sketchScore.beatWidth = d;
    this.startX = paramMouseEvent.getX();
    this.sketchScore.update();
  }
  
  public void keyPressed(KeyEvent paramKeyEvent) {}
  
  public void keyReleased(KeyEvent paramKeyEvent) {}
  
  public void keyTyped(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getKeyChar() == '\b') {
      repaint();
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\sketch\SketchRuler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */