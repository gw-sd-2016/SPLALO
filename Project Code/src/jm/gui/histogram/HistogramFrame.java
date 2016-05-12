package jm.gui.histogram;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import jm.JMC;
import jm.music.data.Score;
import jm.util.Read;

public class HistogramFrame
  extends Frame
  implements WindowListener, ActionListener, JMC
{
  private Score score = new Score();
  private MenuItem showPitch;
  private MenuItem showRhythm;
  private MenuItem showDynamic;
  private MenuItem showPan;
  private MenuItem open;
  private MenuItem openXml;
  private MenuItem saveAs;
  private MenuItem quit;
  private Histogram histo;
  
  public HistogramFrame()
  {
    this(new Score(), 0);
  }
  
  public HistogramFrame(Score paramScore)
  {
    this(paramScore, 0);
  }
  
  public HistogramFrame(Score paramScore, int paramInt)
  {
    this(paramScore, paramInt, 0, 0);
  }
  
  public HistogramFrame(Score paramScore, int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramScore.getTitle());
    this.score = paramScore;
    setTitle(paramInt1);
    setBackground(Color.white);
    setSize(350, 558);
    this.histo = new Histogram(paramScore, paramInt1, 0, 0, getSize().width);
    setLocation(paramInt2, paramInt3);
    add(this.histo);
    addWindowListener(this);
    MenuBar localMenuBar = new MenuBar();
    Menu localMenu = new Menu("Histogram", true);
    this.showPitch = new MenuItem("Pitch", new MenuShortcut(80));
    this.showPitch.addActionListener(this);
    localMenu.add(this.showPitch);
    this.showRhythm = new MenuItem("Rhythm", new MenuShortcut(82));
    this.showRhythm.addActionListener(this);
    localMenu.add(this.showRhythm);
    this.showDynamic = new MenuItem("Dynamic", new MenuShortcut(68));
    this.showDynamic.addActionListener(this);
    localMenu.add(this.showDynamic);
    this.showPan = new MenuItem("Pan", new MenuShortcut(80, true));
    this.showPan.addActionListener(this);
    localMenu.add(this.showPan);
    MenuItem localMenuItem1 = new MenuItem("-");
    localMenu.add(localMenuItem1);
    this.open = new MenuItem("Open MIDI file...", new MenuShortcut(79));
    this.open.addActionListener(this);
    localMenu.add(this.open);
    this.openXml = new MenuItem("Open jMusic XML file...");
    this.openXml.addActionListener(this);
    localMenu.add(this.openXml);
    this.saveAs = new MenuItem("Save data as...", new MenuShortcut(83));
    this.saveAs.addActionListener(this);
    localMenu.add(this.saveAs);
    MenuItem localMenuItem2 = new MenuItem("-");
    localMenu.add(localMenuItem2);
    this.quit = new MenuItem("Quit", new MenuShortcut(81));
    this.quit.addActionListener(this);
    localMenu.add(this.quit);
    localMenuBar.add(localMenu);
    setMenuBar(localMenuBar);
    setVisible(true);
  }
  
  private void setTitle(int paramInt)
  {
    if (paramInt == 0) {
      setTitle("jMusic Pitch Histogram: '" + this.score.getTitle() + "'");
    }
    if (paramInt == 1) {
      setTitle("jMusic Rhythm Histogram: '" + this.score.getTitle() + "'");
    }
    if (paramInt == 2) {
      setTitle("jMusic Dynamic Histogram: '" + this.score.getTitle() + "'");
    }
    if (paramInt == 3) {
      setTitle("jMusic Pan Histogram: '" + this.score.getTitle() + "'");
    }
  }
  
  private void changeDataType(int paramInt)
  {
    setTitle(paramInt);
    this.histo.setType(paramInt);
    repaint();
  }
  
  public void windowClosing(WindowEvent paramWindowEvent)
  {
    dispose();
  }
  
  public void windowActivated(WindowEvent paramWindowEvent) {}
  
  public void windowClosed(WindowEvent paramWindowEvent) {}
  
  public void windowDeactivated(WindowEvent paramWindowEvent) {}
  
  public void windowIconified(WindowEvent paramWindowEvent) {}
  
  public void windowDeiconified(WindowEvent paramWindowEvent) {}
  
  public void windowOpened(WindowEvent paramWindowEvent) {}
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getSource() == this.showPitch) {
      changeDataType(0);
    }
    if (paramActionEvent.getSource() == this.showRhythm) {
      changeDataType(1);
    }
    if (paramActionEvent.getSource() == this.showDynamic) {
      changeDataType(2);
    }
    if (paramActionEvent.getSource() == this.showPan) {
      changeDataType(3);
    }
    if (paramActionEvent.getSource() == this.open) {
      openMIDIFile();
    }
    if (paramActionEvent.getSource() == this.openXml) {
      openXMLFile();
    }
    if (paramActionEvent.getSource() == this.saveAs) {
      this.histo.saveData();
    }
    if (paramActionEvent.getSource() == this.quit) {
      System.exit(0);
    }
  }
  
  public void openMIDIFile()
  {
    FileDialog localFileDialog = new FileDialog(new Frame(), "Select a MIDI file to display.", 0);
    localFileDialog.show();
    String str = localFileDialog.getFile();
    if (str != null)
    {
      Score localScore = new Score();
      Read.midi(localScore, localFileDialog.getDirectory() + str);
      this.score = localScore;
      this.histo.setScore(localScore);
      changeDataType(0);
    }
  }
  
  public void openXMLFile()
  {
    FileDialog localFileDialog = new FileDialog(new Frame(), "Select a jMusic XML file to display.", 0);
    localFileDialog.show();
    String str = localFileDialog.getFile();
    if (str != null)
    {
      Score localScore = new Score();
      Read.xml(localScore, localFileDialog.getDirectory() + str);
      this.score = localScore;
      this.histo.setScore(localScore);
      changeDataType(0);
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\histogram\HistogramFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */