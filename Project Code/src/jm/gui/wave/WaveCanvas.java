package jm.gui.wave;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

public class WaveCanvas
  extends Canvas
{
  private float[] data;
  private int segmentSize = 0;
  private int resolution = 125;
  private int height = 200;
  private int amplitude = 1;
  public Image image = null;
  protected Graphics g;
  private boolean resized = false;
  private Color waveColor = Color.darkGray;
  private Color backgroundColor = Color.white;
  private int sampleStart = 0;
  private int waveSize;
  private boolean fastDraw = false;
  
  public WaveCanvas() {}
  
  public void setData(float[] paramArrayOfFloat)
  {
    this.data = paramArrayOfFloat;
    this.segmentSize = paramArrayOfFloat.length;
  }
  
  public void setResolution(int paramInt)
  {
    if (paramInt > 0) {
      this.resolution = paramInt;
    }
    setFastDraw(true);
    repaint();
    setFastDraw(false);
    repaint();
  }
  
  public void setHeight(int paramInt)
  {
    if (paramInt > 0) {
      this.height = paramInt;
    }
    setSize(new Dimension(600, this.height + 1));
    this.resized = true;
    repaint();
  }
  
  public void setFastDraw(boolean paramBoolean)
  {
    this.fastDraw = paramBoolean;
  }
  
  public void setAmplitude(int paramInt)
  {
    if (paramInt > 0) {
      this.amplitude = paramInt;
    }
    repaint();
  }
  
  public void setWaveSize(int paramInt)
  {
    this.waveSize = paramInt;
  }
  
  public void setResized(boolean paramBoolean)
  {
    this.resized = paramBoolean;
    setFastDraw(true);
    repaint();
    setFastDraw(false);
    repaint();
  }
  
  public void toggleColor()
  {
    if (this.waveColor == Color.darkGray)
    {
      this.waveColor = Color.green;
      this.backgroundColor = Color.darkGray;
    }
    else
    {
      this.waveColor = Color.darkGray;
      this.backgroundColor = Color.white;
    }
    repaint();
  }
  
  public void paint(Graphics paramGraphics)
  {
    setCursor(new Cursor(3));
    if ((this.image == null) || (this.resized))
    {
      this.image = createImage(getSize().width, getSize().height);
      this.g = this.image.getGraphics();
      this.resized = false;
    }
    clearImage(this.g);
    int i = this.height / 2 - 1;
    this.g.setColor(Color.black);
    this.g.drawLine(0, i, getSize().width, i);
    int j = getSize().width * this.resolution;
    int k = this.data.length - this.resolution;
    this.g.setColor(this.waveColor);
    int m;
    float f3;
    if (this.resolution == 1)
    {
      for (m = 0; (m < j) && (m < k); m++)
      {
        f3 = this.data[m];
        this.g.drawLine(m, (int)(i - f3 * i * this.amplitude), m, (int)(i - f3 * i * this.amplitude));
      }
    }
    else
    {
      m = 0;
      float f4 = 0.0F;
      float f5 = 0.0F;
      int n = 0;
      while ((n < j) && (n < k))
      {
        f3 = this.data[n];
        if (this.fastDraw)
        {
          this.g.drawLine(m++, (int)(i - f3 * i * this.amplitude), m, (int)(i - f3 * i * this.amplitude));
        }
        else
        {
          f4 = 0.0F;
          f5 = 0.0F;
          for (int i1 = 0; i1 < this.resolution; i1++)
          {
            if (this.data[(n + i1)] > f4) {
              f4 = this.data[(n + i1)];
            }
            if (this.data[(n + i1)] < f5) {
              f5 = this.data[(n + i1)];
            }
          }
          if (this.resolution > 8)
          {
            float f1 = Math.max(f3, this.data[(n + this.resolution)]);
            float f2 = Math.min(f3, this.data[(n + this.resolution)]);
            if (f4 > 0.0F) {
              this.g.drawLine(m, (int)(i - f1 * i * this.amplitude), m, (int)(i - f4 * i * this.amplitude));
            }
            if (f5 < 0.0F) {
              this.g.drawLine(m, (int)(i - f2 * i * this.amplitude), m, (int)(i - f5 * i * this.amplitude));
            }
          }
          this.g.drawLine(m++, (int)(i - f3 * i * this.amplitude), m, (int)(i - this.data[(n + this.resolution)] * i * this.amplitude));
        }
        n += this.resolution;
      }
    }
    this.g.setColor(Color.lightGray);
    this.g.drawLine(0, this.height, getSize().width, this.height);
    paramGraphics.drawImage(this.image, 0, 0, null);
    clearImage(this.g);
    setCursor(new Cursor(0));
  }
  
  private void clearImage(Graphics paramGraphics)
  {
    paramGraphics.setColor(this.backgroundColor);
    paramGraphics.fillRect(0, 0, getSize().width, getSize().height);
    paramGraphics.setColor(this.waveColor);
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\wave\WaveCanvas.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */