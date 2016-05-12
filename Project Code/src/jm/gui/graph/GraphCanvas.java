package jm.gui.graph;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

public abstract class GraphCanvas
  extends Canvas
{
  protected StatisticsList statsList;
  protected Image image;
  protected Graphics graphics;
  protected Dimension preferredSize = new Dimension(1, 1);
  protected Dimension minimumSize = new Dimension(1, 1);
  
  public GraphCanvas()
  {
    this(new Statistics());
  }
  
  public GraphCanvas(Statistics paramStatistics)
  {
    this.statsList = new StatisticsList();
    this.statsList.add(paramStatistics);
  }
  
  public GraphCanvas(Statistics[] paramArrayOfStatistics)
  {
    this.statsList = new StatisticsList(paramArrayOfStatistics.length * 110 / 100);
    for (int i = 0; i < paramArrayOfStatistics.length; i++) {
      this.statsList.add(paramArrayOfStatistics[i]);
    }
  }
  
  public GraphCanvas(StatisticsList paramStatisticsList)
  {
    this.statsList = paramStatisticsList;
  }
  
  public Dimension getMinimumSize()
  {
    return this.minimumSize;
  }
  
  public Dimension getPreferredSize()
  {
    return this.preferredSize;
  }
  
  public void addStatistics(Statistics paramStatistics)
  {
    this.statsList.add(paramStatistics);
  }
  
  protected abstract void paintBuffer();
  
  public void update(Graphics paramGraphics)
  {
    paint(paramGraphics);
  }
  
  public void paint(Graphics paramGraphics)
  {
    if (this.image == null)
    {
      this.image = createImage(1, 1);
      this.graphics = this.image.getGraphics();
    }
    paintBuffer();
    paramGraphics.drawImage(this.image, 0, 0, this);
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\graph\GraphCanvas.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */