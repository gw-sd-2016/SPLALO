package jm.gui.wave;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class WaveRuler
  extends Panel
  implements MouseListener, MouseMotionListener
{
  private WaveScrollPanel scrollPanel;
  private int markerWidth;
  private int startX;
  private double tempRes;
  private Font font = new Font("Helvetica", 0, 9);
  private int startSecond = 0;
  
  public WaveRuler()
  {
    setBackground(Color.lightGray);
    setSize(new Dimension(600, 20));
    addMouseListener(this);
    addMouseMotionListener(this);
  }
  
  public void setWaveScrollPanel(WaveScrollPanel paramWaveScrollPanel)
  {
    this.scrollPanel = paramWaveScrollPanel;
  }
  
  public void setMarkerWidth(int paramInt)
  {
    if (paramInt > 0)
    {
      this.markerWidth = paramInt;
      repaint();
    }
  }
  
  public void paint(Graphics paramGraphics)
  {
    paramGraphics.setColor(Color.darkGray);
    paramGraphics.setFont(this.font);
    int i = getSize().width;
    int j = -1 * this.scrollPanel.getWaveView().getStartPos();
    int k = this.markerWidth;
    int m = (int)Math.round(this.markerWidth / 10.0D);
    int n = (int)Math.round(this.markerWidth / 100.0D);
    int i1 = (int)Math.round(this.markerWidth / 1000.0D);
    int i2 = this.scrollPanel.getWaveView().getResolution();
    this.startSecond = (this.scrollPanel.getWaveView().getStartPos() / this.scrollPanel.getWaveView().getSampleRate() / this.scrollPanel.getWaveView().getChannels());
    int i3 = 0;
    paramGraphics.setColor(Color.white);
    int i5;
    int i6;
    int i7;
    if (this.markerWidth > 20000)
    {
      i4 = j / i2;
      while (i4 < i)
      {
        for (i5 = 0; i5 < 10; i5++) {
          for (i6 = 0; i6 < 10; i6++) {
            for (i7 = 0; i7 < 10; i7++)
            {
              if (i3 % 10 != 0)
              {
                int i8 = i4 + i5 * m + i6 * n + i7 * i1;
                paramGraphics.drawLine(i8, getSize().height / 8 * 7, i8, getSize().height);
                if (this.markerWidth > 40000) {
                  paramGraphics.drawString("" + (this.startSecond + i3 / 1000.0D), i8 + 2, getSize().height - 1);
                }
              }
              i3++;
            }
          }
        }
        i4 += k;
      }
    }
    i3 = 0;
    paramGraphics.setColor(Color.magenta);
    if (this.markerWidth > 1200)
    {
      i4 = j / i2;
      while (i4 < i)
      {
        for (i5 = 0; i5 < 10; i5++) {
          for (i6 = 0; i6 < 10; i6++)
          {
            if (i3 % 10 != 0)
            {
              i7 = i4 + i5 * m + i6 * n;
              paramGraphics.drawLine(i7, getSize().height / 4 * 3, i7, getSize().height);
              if (this.markerWidth > 4800) {
                paramGraphics.drawString("" + (this.startSecond + i3 / 100.0D), i7 + 2, getSize().height - 1);
              }
            }
            i3++;
          }
        }
        i4 += k;
      }
    }
    i3 = 0;
    paramGraphics.setColor(Color.blue);
    if (this.markerWidth > 150)
    {
      i4 = j / i2;
      while (i4 < i)
      {
        for (i5 = 0; i5 < 10; i5++)
        {
          if (i3 % 10 != 0)
          {
            i6 = i4 + i5 * m;
            paramGraphics.drawLine(i6, getSize().height / 2, i6, getSize().height);
            if (this.markerWidth > 300) {
              paramGraphics.drawString("" + (this.startSecond + i3 / 10.0D), i6 + 2, getSize().height - 1);
            }
          }
          i3++;
        }
        i4 += k;
      }
    }
    i3 = 0;
    paramGraphics.setColor(Color.red);
    int i4 = j / i2;
    while (i4 < i)
    {
      paramGraphics.drawLine(i4, 1, i4, getSize().height);
      if ((this.markerWidth > 20) && (this.markerWidth <= 300)) {
        paramGraphics.drawString("" + (this.startSecond + i3), i4 + 2, getSize().height - 1);
      } else if (this.markerWidth > 300) {
        paramGraphics.drawString("" + (this.startSecond + i3 / 1.0D), i4 + 2, getSize().height - 1);
      }
      i3++;
      i4 += k;
    }
  }
  
  public void mousePressed(MouseEvent paramMouseEvent)
  {
    setCursor(new Cursor(10));
    this.startX = paramMouseEvent.getX();
    this.tempRes = this.scrollPanel.getWaveView().getResolution();
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {}
  
  public void mouseEntered(MouseEvent paramMouseEvent) {}
  
  public void mouseExited(MouseEvent paramMouseEvent) {}
  
  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    setCursor(new Cursor(13));
    this.scrollPanel.getWaveView().setResolution((int)this.tempRes);
  }
  
  public void mouseMoved(MouseEvent paramMouseEvent) {}
  
  public void mouseDragged(MouseEvent paramMouseEvent)
  {
    int i = 5;
    if (paramMouseEvent.getX() > this.startX + i)
    {
      if (this.tempRes < 8.0D) {
        this.tempRes = Math.round(this.tempRes / 2.0D);
      } else {
        this.tempRes = Math.round(this.tempRes / 1.1D);
      }
      if (this.tempRes < 1.0D) {
        this.tempRes = 1.0D;
      }
      if (this.tempRes > 2048.0D) {
        this.tempRes = 2048.0D;
      }
      this.scrollPanel.setResolution((int)Math.round(this.tempRes));
      this.startX = paramMouseEvent.getX();
      repaint();
    }
    if (paramMouseEvent.getX() < this.startX - i)
    {
      if (this.tempRes < 8.0D) {
        this.tempRes = Math.round(this.tempRes * 2.0D);
      } else {
        this.tempRes = Math.round(this.tempRes * 1.1D);
      }
      if (this.tempRes < 1.0D) {
        this.tempRes = 1.0D;
      }
      if (this.tempRes > 2048.0D) {
        this.tempRes = 2048.0D;
      }
      this.scrollPanel.setResolution((int)Math.round(this.tempRes));
      this.startX = paramMouseEvent.getX();
      repaint();
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\wave\WaveRuler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */