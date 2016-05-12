package jm.gui.graph;

import java.awt.ScrollPane;

public class LineGraph
  extends ScrollPane
{
  protected GraphCanvas graphCanvas;
  
  public LineGraph()
  {
    this(new Statistics());
  }
  
  public LineGraph(Statistics paramStatistics)
  {
    this.graphCanvas = new LineGraphCanvas(paramStatistics);
    add(this.graphCanvas);
  }
  
  public LineGraph(Statistics[] paramArrayOfStatistics)
  {
    this.graphCanvas = new LineGraphCanvas(paramArrayOfStatistics);
    add(this.graphCanvas);
  }
  
  public LineGraph(StatisticsList paramStatisticsList)
  {
    this.graphCanvas = new LineGraphCanvas(paramStatisticsList);
    add(this.graphCanvas);
  }
  
  public void addStatistics(Statistics paramStatistics)
  {
    this.graphCanvas.addStatistics(paramStatistics);
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\graph\LineGraph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */