package jm.gui.show;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.ScrollPane;
import jm.music.data.Score;

public class ShowPanel
  extends ScrollPane
{
  public Score score;
  protected double beatWidth;
  private ShowArea sa;
  private ShowRuler ruler;
  private Panel pan;
  private Frame frame;
  private int panelHeight;
  
  public ShowPanel(Frame paramFrame, Score paramScore)
  {
    super(1);
    this.beatWidth = (650.0D / paramScore.getEndTime());
    if (this.beatWidth < 1.0D) {
      this.beatWidth = 1.0D;
    }
    if (this.beatWidth > 256.0D) {
      this.beatWidth = 256.0D;
    }
    this.frame = paramFrame;
    this.score = paramScore;
    this.pan = new Panel();
    this.pan.setLayout(new BorderLayout());
    this.sa = new ShowArea(this);
    this.pan.add("Center", this.sa);
    this.ruler = new ShowRuler(this);
    this.pan.add("South", this.ruler);
    setSize(new Dimension(650, 400));
    updatePanelHeight();
    add(this.pan);
    getHAdjustable().setUnitIncrement(50);
    getHAdjustable().setBlockIncrement(50);
    setScrollPosition(0, 0);
  }
  
  public void setScore(Score paramScore)
  {
    this.score = paramScore;
    this.beatWidth = (getSize().width / paramScore.getEndTime());
    if (this.beatWidth < 1.0D) {
      this.beatWidth = 1.0D;
    }
    if (this.beatWidth > 256.0D) {
      this.beatWidth = 256.0D;
    }
    update();
  }
  
  public void updatePanelHeight()
  {
    this.panelHeight = (this.sa.getHeight() + this.ruler.getHeight() + 25);
    setSize(new Dimension(getSize().width, this.panelHeight));
  }
  
  public int getHeight()
  {
    return this.panelHeight;
  }
  
  public ShowArea getShowArea()
  {
    return this.sa;
  }
  
  public void update()
  {
    this.pan.repaint();
    this.sa.setSize((int)Math.round(this.score.getEndTime() * this.beatWidth), this.sa.getHeight());
    this.sa.repaint();
    this.ruler.repaint();
    repaint();
    this.frame.pack();
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\show\ShowPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */