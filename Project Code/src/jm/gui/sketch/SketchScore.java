package jm.gui.sketch;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Vector;
import jm.midi.MidiSynth;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Read;
import jm.util.Write;

public class SketchScore
  extends Frame
  implements WindowListener, ActionListener
{
  private static int maxWidth;
  private static int maxParts;
  protected static Score score;
  protected double beatWidth = 10.0D;
  private Panel pan;
  private SketchScoreArea sketchScoreArea;
  private SketchRuler ruler;
  private MenuItem play;
  private MenuItem speedUp;
  private MenuItem slowDown;
  private MenuItem clear;
  private MenuItem saveMIDI;
  private MenuItem quit;
  private MenuItem openMIDI;
  private MenuItem openXML;
  private MenuItem saveXML;
  
  public SketchScore(Score paramScore)
  {
    this(paramScore, 0, 0);
  }
  
  public SketchScore(Score paramScore, int paramInt1, int paramInt2)
  {
    super("jMusic Sketch: '" + paramScore.getTitle() + "'");
    score = paramScore;
    getWidthAndParts();
    addWindowListener(this);
    this.pan = new Panel();
    this.pan.setLayout(new BorderLayout());
    this.sketchScoreArea = new SketchScoreArea(paramScore, maxWidth, this.beatWidth);
    this.sketchScoreArea.setSketchScore(this);
    this.pan.add("Center", this.sketchScoreArea);
    this.ruler = new SketchRuler(this);
    this.pan.add("South", this.ruler);
    ScrollPane localScrollPane = new ScrollPane(1);
    localScrollPane.getHAdjustable().setUnitIncrement(20);
    localScrollPane.add(this.pan);
    add(localScrollPane);
    MenuBar localMenuBar = new MenuBar();
    Menu localMenu = new Menu("Sketch", true);
    this.play = new MenuItem("Play @ " + paramScore.getTempo() + " bpm", new MenuShortcut(80));
    this.play.addActionListener(this);
    localMenu.add(this.play);
    this.speedUp = new MenuItem("Speed Up");
    this.speedUp.addActionListener(this);
    localMenu.add(this.speedUp);
    this.slowDown = new MenuItem("Slow Down");
    this.slowDown.addActionListener(this);
    localMenu.add(this.slowDown);
    this.clear = new MenuItem("Clear notes");
    this.clear.addActionListener(this);
    localMenu.add(this.clear);
    MenuItem localMenuItem = new MenuItem("-");
    localMenu.add(localMenuItem);
    this.openMIDI = new MenuItem("Open a MIDI file...", new MenuShortcut(79));
    this.openMIDI.addActionListener(this);
    localMenu.add(this.openMIDI);
    this.openXML = new MenuItem("Open a jMusic XML file...");
    this.openXML.addActionListener(this);
    localMenu.add(this.openXML);
    this.saveMIDI = new MenuItem("Save as MIDI file", new MenuShortcut(83));
    this.saveMIDI.addActionListener(this);
    localMenu.add(this.saveMIDI);
    this.saveXML = new MenuItem("Save as a jMusic XML file");
    this.saveXML.addActionListener(this);
    localMenu.add(this.saveXML);
    this.quit = new MenuItem("Quit", new MenuShortcut(81));
    this.quit.addActionListener(this);
    localMenu.add(this.quit);
    localMenuBar.add(localMenu);
    setMenuBar(localMenuBar);
    setSize(650, this.sketchScoreArea.getHeight() + this.ruler.getHeight());
    setLocation(paramInt1, paramInt2);
    show();
  }
  
  public SketchScoreArea getSketchScoreArea()
  {
    return this.sketchScoreArea;
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
  
  public void update()
  {
    this.sketchScoreArea.setScore(score);
    this.pan.repaint();
    this.sketchScoreArea.setSize((int)Math.round(score.getEndTime() * this.beatWidth), this.sketchScoreArea.getHeight());
    this.sketchScoreArea.setBeatWidth(this.beatWidth);
    this.sketchScoreArea.repaint();
    this.ruler.repaint();
    setSize(getSize().width, this.sketchScoreArea.getHeight() + this.ruler.getHeight());
    pack();
  }
  
  private void getWidthAndParts()
  {
    Enumeration localEnumeration1 = score.getPartList().elements();
    while (localEnumeration1.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration1.nextElement();
      maxParts += 1;
      Enumeration localEnumeration2 = localPart.getPhraseList().elements();
      while (localEnumeration2.hasMoreElements())
      {
        Phrase localPhrase = (Phrase)localEnumeration2.nextElement();
        Enumeration localEnumeration3 = localPhrase.getNoteList().elements();
        Note localNote;
        for (maxWidth = (int)(localPhrase.getStartTime() * this.beatWidth); localEnumeration3.hasMoreElements(); maxWidth += (int)(localNote.getRhythmValue() * this.beatWidth)) {
          localNote = (Note)localEnumeration3.nextElement();
        }
      }
    }
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getSource() == this.play) {
      playScore();
    }
    if (paramActionEvent.getSource() == this.speedUp) {
      speedItUp();
    }
    if (paramActionEvent.getSource() == this.slowDown) {
      slowItDown();
    }
    if (paramActionEvent.getSource() == this.clear) {
      clearNotes();
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
  }
  
  private void playScore()
  {
    MidiSynth localMidiSynth = new MidiSynth();
    try
    {
      localMidiSynth.play(score);
    }
    catch (Exception localException)
    {
      System.err.println("MIDI Playback Error:" + localException);
      return;
    }
  }
  
  private void speedItUp()
  {
    double d = score.getTempo() + 10.0D;
    if (d > 250.0D) {
      d = 250.0D;
    }
    score.setTempo(d);
    this.play.setLabel("Play @ " + d + " bpm");
  }
  
  private void slowItDown()
  {
    double d = score.getTempo() - 10.0D;
    if (d < 20.0D) {
      d = 20.0D;
    }
    score.setTempo(d);
    this.play.setLabel("Play @ " + d + " bpm");
  }
  
  private void clearNotes()
  {
    score.removeAllParts();
    this.sketchScoreArea.repaint();
  }
  
  public void saveMidi()
  {
    FileDialog localFileDialog = new FileDialog(this, "Save score as a MIDI file ...", 1);
    localFileDialog.setFile("FileName.mid");
    localFileDialog.show();
    if (localFileDialog.getFile() != null) {
      Write.midi(score, localFileDialog.getDirectory() + localFileDialog.getFile());
    }
  }
  
  public void saveXMLFile()
  {
    FileDialog localFileDialog = new FileDialog(new Frame(), "Save as a jMusic XML file...", 1);
    localFileDialog.setFile("FileName.xml");
    localFileDialog.show();
    if (localFileDialog.getFile() != null) {
      Write.xml(score, localFileDialog.getDirectory() + localFileDialog.getFile());
    }
  }
  
  public void openMidi()
  {
    FileDialog localFileDialog = new FileDialog(new Frame(), "Select a MIDI file to display.", 0);
    localFileDialog.show();
    String str = localFileDialog.getFile();
    if (str != null)
    {
      Score localScore = new Score();
      Read.midi(localScore, localFileDialog.getDirectory() + str);
      score = localScore;
      update();
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
      score = localScore;
      update();
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\sketch\SketchScore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */