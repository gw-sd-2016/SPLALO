package jm.gui.show;

import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.Read;
import jm.util.Write;

public class ShowScore
  extends Frame
  implements WindowListener, ActionListener
{
  private Panel pan;
  private Score score = new Score();
  private MenuItem saveMIDI;
  private MenuItem quit;
  private MenuItem size7;
  private MenuItem size2;
  private MenuItem size3;
  private MenuItem size4;
  private MenuItem size5;
  private MenuItem size6;
  private MenuItem size8;
  private MenuItem thin;
  private MenuItem medium;
  private MenuItem thick;
  private MenuItem play;
  private MenuItem saveXML;
  private MenuItem openXML;
  private MenuItem openMIDI;
  private ShowPanel sp;
  
  public ShowScore(Score paramScore)
  {
    this(paramScore, 0, 0);
  }
  
  public ShowScore(Score paramScore, int paramInt1, int paramInt2)
  {
    super("jMusic Show: '" + paramScore.getTitle() + "'");
    this.score = paramScore;
    addWindowListener(this);
    this.sp = new ShowPanel(this, paramScore);
    setSize(650, this.sp.getHeight() + 25);
    add(this.sp);
    MenuBar localMenuBar = new MenuBar();
    Menu localMenu = new Menu("Show", true);
    this.size2 = new MenuItem("Size 2");
    this.size2.addActionListener(this);
    localMenu.add(this.size2);
    this.size3 = new MenuItem("Size 3");
    this.size3.addActionListener(this);
    localMenu.add(this.size3);
    this.size4 = new MenuItem("Size 4");
    this.size4.addActionListener(this);
    localMenu.add(this.size4);
    this.size5 = new MenuItem("Size 5");
    this.size5.addActionListener(this);
    localMenu.add(this.size5);
    this.size6 = new MenuItem("Size 6");
    this.size6.addActionListener(this);
    localMenu.add(this.size6);
    this.size7 = new MenuItem("Size 7");
    this.size7.addActionListener(this);
    localMenu.add(this.size7);
    this.size8 = new MenuItem("Size 8");
    this.size8.addActionListener(this);
    localMenu.add(this.size8);
    MenuItem localMenuItem1 = new MenuItem("-");
    localMenu.add(localMenuItem1);
    this.thin = new MenuItem("Thin notes");
    this.thin.addActionListener(this);
    localMenu.add(this.thin);
    this.medium = new MenuItem("Medium notes");
    this.medium.addActionListener(this);
    localMenu.add(this.medium);
    this.thick = new MenuItem("Thick notes");
    this.thick.addActionListener(this);
    localMenu.add(this.thick);
    MenuItem localMenuItem2 = new MenuItem("-");
    localMenu.add(localMenuItem2);
    this.play = new MenuItem("Play MIDI", new MenuShortcut(80));
    this.play.addActionListener(this);
    localMenu.add(this.play);
    this.openMIDI = new MenuItem("Open a MIDI file...", new MenuShortcut(79));
    this.openMIDI.addActionListener(this);
    localMenu.add(this.openMIDI);
    this.openXML = new MenuItem("Open a jMusic XML file...");
    this.openXML.addActionListener(this);
    localMenu.add(this.openXML);
    this.saveMIDI = new MenuItem("Save as MIDI file...", new MenuShortcut(83));
    this.saveMIDI.addActionListener(this);
    localMenu.add(this.saveMIDI);
    this.saveXML = new MenuItem("Save as jMusic XML file...");
    this.saveXML.addActionListener(this);
    localMenu.add(this.saveXML);
    this.quit = new MenuItem("Quit", new MenuShortcut(81));
    this.quit.addActionListener(this);
    localMenu.add(this.quit);
    localMenuBar.add(localMenu);
    setMenuBar(localMenuBar);
    pack();
    setLocation(paramInt1, paramInt2);
    show();
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
    if (paramActionEvent.getSource() == this.play) {
      playBackMidi();
    }
    if (paramActionEvent.getSource() == this.quit) {
      System.exit(0);
    }
    if (paramActionEvent.getSource() == this.saveMIDI) {
      saveMidi();
    }
    if (paramActionEvent.getSource() == this.openMIDI) {
      openMidi();
    }
    if (paramActionEvent.getSource() == this.saveXML) {
      saveXMLFile();
    }
    if (paramActionEvent.getSource() == this.openXML) {
      openXMLFile();
    }
    if (paramActionEvent.getSource() == this.size2) {
      resize(2);
    }
    if (paramActionEvent.getSource() == this.size3) {
      resize(3);
    }
    if (paramActionEvent.getSource() == this.size4) {
      resize(4);
    }
    if (paramActionEvent.getSource() == this.size5) {
      resize(5);
    }
    if (paramActionEvent.getSource() == this.size6) {
      resize(6);
    }
    if (paramActionEvent.getSource() == this.size7) {
      resize(7);
    }
    if (paramActionEvent.getSource() == this.size8) {
      resize(8);
    }
    if (paramActionEvent.getSource() == this.thin) {
      this.sp.getShowArea().setThinNote(2);
    }
    if (paramActionEvent.getSource() == this.medium) {
      this.sp.getShowArea().setThinNote(1);
    }
    if (paramActionEvent.getSource() == this.thick) {
      this.sp.getShowArea().setThinNote(0);
    }
  }
  
  private void resize(int paramInt)
  {
    this.sp.getShowArea().setNoteHeight(paramInt);
    setSize(getSize().width, this.sp.getHeight() + 25);
    pack();
  }
  
  public void saveMidi()
  {
    Write.midi(this.score);
  }
  
  public void saveXMLFile()
  {
    FileDialog localFileDialog = new FileDialog(new Frame(), "Save as a jMusic XML file...", 1);
    localFileDialog.show();
    if (localFileDialog.getFile() != null) {
      Write.xml(this.score, localFileDialog.getDirectory() + localFileDialog.getFile());
    }
  }
  
  public void openMidi()
  {
    FileDialog localFileDialog = new FileDialog(new Frame(), "Select a MIDI file to display...", 0);
    localFileDialog.show();
    String str = localFileDialog.getFile();
    if (str != null)
    {
      Score localScore = new Score();
      Read.midi(localScore, localFileDialog.getDirectory() + str);
      this.score = localScore;
      this.sp.setScore(localScore);
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
      this.sp.setScore(localScore);
    }
  }
  
  private void playBackMidi()
  {
    Play.midi(this.score, false);
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\show\ShowScore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */