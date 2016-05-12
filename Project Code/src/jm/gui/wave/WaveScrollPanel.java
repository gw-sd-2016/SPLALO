package jm.gui.wave;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class WaveScrollPanel
  extends Panel
  implements ActionListener, AdjustmentListener
{
  private Label name;
  private Label bitSize;
  private Label sampleRate;
  private Label channels;
  private Label resLable;
  private Button minus;
  private Button plus;
  private Button play;
  private Button stop;
  private WaveView viewer;
  private WaveRuler ruler = new WaveRuler();
  private Panel resizePanel;
  private Scrollbar scroll = new Scrollbar(0);
  private Font font = new Font("Helvetica", 0, 10);
  
  public WaveScrollPanel()
  {
    setBackground(Color.lightGray);
    setLayout(new BorderLayout());
    this.resizePanel = new Panel();
    this.resLable = new Label("Display Resolution: 1:0");
    this.resLable.setFont(this.font);
    this.resizePanel.add(this.resLable);
    this.minus = new Button("-");
    this.minus.addActionListener(this);
    this.resizePanel.add(this.minus);
    this.plus = new Button("+");
    this.plus.addActionListener(this);
    this.resizePanel.add(this.plus);
    add(this.resizePanel, "East");
    Panel localPanel = new Panel();
    this.play = new Button("Play");
    this.play.addActionListener(this);
    localPanel.add(this.play);
    this.stop = new Button("Stop");
    this.stop.setEnabled(false);
    this.stop.addActionListener(this);
    localPanel.add(this.stop);
    this.name = new Label();
    this.name.setFont(this.font);
    localPanel.add(this.name);
    this.bitSize = new Label();
    this.bitSize.setFont(this.font);
    localPanel.add(this.bitSize);
    this.sampleRate = new Label();
    this.sampleRate.setFont(this.font);
    localPanel.add(this.sampleRate);
    this.channels = new Label();
    this.channels.setFont(this.font);
    localPanel.add(this.channels);
    add(localPanel, "West");
    this.scroll.addAdjustmentListener(this);
    add(this.scroll, "South");
    this.ruler.setWaveScrollPanel(this);
    add(this.ruler, "North");
  }
  
  public void setViewer(WaveView paramWaveView)
  {
    this.viewer = paramWaveView;
    setResolution(paramWaveView.getResolution());
  }
  
  public void setScrollbarAttributes(int paramInt1, int paramInt2, int paramInt3)
  {
    this.scroll.setUnitIncrement(1000);
    this.scroll.setBlockIncrement(paramInt2 * paramInt3 / 2);
    this.scroll.setMinimum(0);
    this.scroll.setMaximum(paramInt1 * 2);
    this.scroll.setVisibleAmount(paramInt2 * paramInt3);
  }
  
  public void setScrollbarResolution(int paramInt)
  {
    if (this.viewer != null)
    {
      this.scroll.setVisibleAmount(this.viewer.getWidth() * paramInt);
      this.scroll.setBlockIncrement(this.viewer.getWidth() * paramInt / 2);
    }
    else
    {
      this.scroll.setVisibleAmount(204800);
      this.scroll.setBlockIncrement(102400);
    }
  }
  
  public void setScrollbarValue(int paramInt)
  {
    this.scroll.setValue(paramInt);
  }
  
  public void setResolution(int paramInt)
  {
    String str = new String("Display Resolution = 1:" + paramInt);
    if (paramInt < 1000) {
      str = str + "  ";
    }
    this.resLable.setText(str);
    this.ruler.setMarkerWidth(this.viewer.getSampleRate() / paramInt);
    repaint();
  }
  
  public void setFileName(String paramString)
  {
    this.name.setText("File = " + paramString + ". ");
    repaint();
  }
  
  public void setBitSize(int paramInt)
  {
    this.bitSize.setText("Bit Depth = " + paramInt + ". ");
    repaint();
  }
  
  public void setSampleRate(int paramInt)
  {
    this.sampleRate.setText("Sample Rate = " + paramInt + ". ");
    repaint();
  }
  
  public void setChannels(int paramInt)
  {
    this.channels.setText("Channels = " + paramInt + ". ");
    repaint();
  }
  
  public WaveRuler getWaveRuler()
  {
    return this.ruler;
  }
  
  public WaveView getWaveView()
  {
    return this.viewer;
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    int i;
    if (paramActionEvent.getSource() == this.minus)
    {
      i = this.viewer.getResolution();
      if ((i > 0) && (i <= 1024))
      {
        i *= 2;
        setResolution(i);
        this.plus.setEnabled(true);
        if (i > 1024) {
          this.minus.setEnabled(false);
        }
        this.viewer.setResolution(i);
      }
    }
    if (paramActionEvent.getSource() == this.plus)
    {
      i = this.viewer.getResolution();
      if (i > 1)
      {
        i /= 2;
        setResolution(i);
        this.minus.setEnabled(true);
        if (i < 2) {
          this.plus.setEnabled(false);
        }
        this.viewer.setResolution(i);
      }
    }
    if (paramActionEvent.getSource() == this.play)
    {
      this.stop.setEnabled(true);
      this.viewer.playFile();
    }
    if (paramActionEvent.getSource() == this.stop)
    {
      this.viewer.pauseFile();
      this.stop.setEnabled(false);
    }
  }
  
  public void adjustmentValueChanged(AdjustmentEvent paramAdjustmentEvent)
  {
    this.viewer.setStartPos(this.scroll.getValue());
    this.ruler.repaint();
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\wave\WaveScrollPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */