package jm.gui.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

public class LineGraphCanvas
  extends GraphCanvas
{
  protected boolean hasMusicChanged = true;
  protected Dimension preferredSize = new Dimension(800, 800);
  
  public LineGraphCanvas() {}
  
  public LineGraphCanvas(Statistics paramStatistics)
  {
    super(paramStatistics);
  }
  
  public LineGraphCanvas(Statistics[] paramArrayOfStatistics)
  {
    super(paramArrayOfStatistics);
  }
  
  public LineGraphCanvas(StatisticsList paramStatisticsList)
  {
    super(paramStatisticsList);
  }
  
  public Dimension getPreferredSize()
  {
    return this.preferredSize;
  }
  
  public void paintBuffer()
  {
    int i = 1000;
    double d = 1000.0D;
    for (int j = 0; j < this.statsList.size(); j++)
    {
      if (this.statsList.get(j).size() > i) {
        i = this.statsList.get(j).size();
      }
      if ((this.statsList.get(j).largestValue() != Double.POSITIVE_INFINITY) && (this.statsList.get(j).largestValue() > d)) {
        d = this.statsList.get(j).largestValue();
      }
    }
    this.image = createImage(i, (int)d);
    this.graphics = this.image.getGraphics();
    for (j = 0; j < this.statsList.size(); j++)
    {
      this.graphics.setColor(new Color((float)Math.random(), (float)Math.random(), (float)Math.random()));
      for (int k = 1; k < this.statsList.get(j).size(); k++) {
        if (this.statsList.get(j).largestValue() != Double.POSITIVE_INFINITY) {
          if (j == 2) {
            this.graphics.drawLine((int)((k - 1) * 0.5D), (int)(this.statsList.get(j).get(k - 1) * 10000.0D), (int)(k * 0.5D), (int)(this.statsList.get(j).get(k) * 10000.0D));
          } else {
            this.graphics.drawLine((int)((k - 1) * 0.5D), (int)(this.statsList.get(j).get(k - 1) * 300.0D), (int)(k * 0.5D), (int)(this.statsList.get(j).get(k) * 300.0D));
          }
        }
      }
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\graph\LineGraphCanvas.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */