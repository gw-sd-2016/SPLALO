package jm.gui.cpn;

import java.awt.Adjustable;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import jm.JMC;
import jm.music.data.CPhrase;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.Read;
import jm.util.Write;

public class Notate
  extends Frame
  implements ActionListener, WindowListener, JMC
{
  private Score score;
  private Phrase[] phraseArray;
  private CPhrase[] cphraseArray;
  private Stave[] staveArray;
  private int scrollHeight = 130;
  private int locationX = 0;
  private int locationY = 0;
  private Dialog keyDialog;
  private Dialog timeDialog;
  private MenuItem keySig;
  private MenuItem open;
  private MenuItem openJmXml;
  private MenuItem openjm;
  private MenuItem play;
  private MenuItem stop;
  private MenuItem delete;
  private MenuItem clear;
  private MenuItem newStave;
  private MenuItem close;
  private MenuItem timeSig;
  private MenuItem saveJmXml;
  private MenuItem saveJM;
  private MenuItem saveMidi;
  private MenuItem quit;
  private MenuItem trebleStave;
  private MenuItem bassStave;
  private MenuItem pianoStave;
  private MenuItem grandStave;
  private MenuItem automaticStave;
  private MenuItem appendMidiFile;
  private MenuItem insertMidiFile;
  private MenuItem setParameters;
  private MenuItem playAll;
  private MenuItem playMeasure;
  private MenuItem repeatAll;
  private MenuItem repeatMeasure;
  private MenuItem stopPlay;
  private MenuItem earTrain;
  private MenuItem addNotes;
  private MenuItem adjustTiming;
  private MenuItem viewDetails;
  private MenuItem viewTitle;
  private MenuItem viewZoom;
  private MenuItem barNumbers;
  public boolean timeToStop;
  private Panel scoreBG;
  private GridBagConstraints constraints;
  private GridBagLayout layout;
  private ScrollPane scroll;
  private String lastFileName = "*.mid";
  private String lastDirectory = "";
  private String fileNameFilter = "*.mid";
  private boolean zoomed;
  private Phrase beforeZoom = new Phrase();
  private Phrase afterZoom = new Phrase();
  private int height = 0;
  private int width = 700;
  
  public Notate()
  {
    this(new Phrase(), 0, 0);
    clearZoom();
  }
  
  public Notate(int paramInt1, int paramInt2)
  {
    this(new Phrase(), paramInt1, paramInt2);
    clearZoom();
  }
  
  public Notate(Phrase paramPhrase)
  {
    this(paramPhrase, 0, 0);
    clearZoom();
  }
  
  private void clearZoom()
  {
    this.zoomed = false;
  }
  
  public Notate(Phrase paramPhrase, int paramInt1, int paramInt2)
  {
    super("CPN: " + paramPhrase.getTitle());
    clearZoom();
    this.score = new Score(new Part(paramPhrase));
    this.locationX = paramInt1;
    this.locationY = paramInt2;
    this.score = new Score(new Part(paramPhrase));
    init();
  }
  
  public Notate(CPhrase paramPhrase, int paramInt1, int paramInt2)
  {
    super("CPN: " + paramPhrase.getTitle());
    clearZoom();
    this.score = new Score(new Part(paramPhrase));
    this.locationX = paramInt1;
    this.locationY = paramInt2;
    this.score = new Score(new Part(paramPhrase));
    init();
  }
  
  public Notate(Score paramScore, int paramInt1, int paramInt2)
  {
    super("CPN: " + paramScore.getTitle());
    clearZoom();
    this.score = paramScore;
    this.locationX = paramInt1;
    this.locationY = paramInt2;
    init();
  }
  
  public void init()
  {
    addWindowListener(this);
    MenuBar localMenuBar = new MenuBar();
    Menu localMenu1 = new Menu("File", true);
    Menu localMenu2 = new Menu("Tools", true);
    Menu localMenu3 = new Menu("Play", true);
    Menu localMenu4 = new Menu("View", true);
    this.newStave = new MenuItem("New", new MenuShortcut(78));
    this.newStave.addActionListener(this);
    localMenu1.add(this.newStave);
    this.open = new MenuItem("Open MIDI file...", new MenuShortcut(79));
    this.open.addActionListener(this);
    localMenu1.add(this.open);
    this.openJmXml = new MenuItem("Open jMusic XML file...");
    this.openJmXml.addActionListener(this);
    localMenu1.add(this.openJmXml);
    this.openjm = new MenuItem("Open jm file..");
    this.openjm.addActionListener(this);
    localMenu1.add(this.openjm);
    this.close = new MenuItem("Close", new MenuShortcut(87));
    this.close.addActionListener(this);
    localMenu1.add(this.close);
    localMenu1.add("-");
    this.delete = new MenuItem("Delete last note", new MenuShortcut(68));
    this.delete.addActionListener(this);
    localMenu1.add(this.delete);
    this.clear = new MenuItem("Clear all notes", new MenuShortcut(67));
    this.clear.addActionListener(this);
    localMenu1.add(this.clear);
    localMenu1.add("-");
    this.keySig = new MenuItem("Key Signature", new MenuShortcut(75));
    this.keySig.addActionListener(this);
    localMenu1.add(this.keySig);
    this.timeSig = new MenuItem("Time Signature", new MenuShortcut(84));
    this.timeSig.addActionListener(this);
    localMenu1.add(this.timeSig);
    localMenu1.add("-");
    this.saveMidi = new MenuItem("Save as a MIDI file...", new MenuShortcut(83));
    this.saveMidi.addActionListener(this);
    localMenu1.add(this.saveMidi);
    this.saveJmXml = new MenuItem("Save as a jMusic XML file...", new MenuShortcut(83, true));
    this.saveJmXml.addActionListener(this);
    localMenu1.add(this.saveJmXml);
    this.saveJM = new MenuItem("Save as a jm file...");
    this.saveJM.addActionListener(this);
    localMenu1.add(this.saveJM);
    localMenu1.add("-");
    this.setParameters = new MenuItem("Set Parameters...");
    this.setParameters.addActionListener(this);
    localMenu2.add(this.setParameters);
    this.addNotes = new MenuItem("Add Notes by Letter");
    this.addNotes.addActionListener(this);
    localMenu2.add(this.addNotes);
    this.adjustTiming = new MenuItem("Quantize Timing");
    this.adjustTiming.addActionListener(this);
    localMenu2.add(this.adjustTiming);
    this.playAll = new MenuItem("Play All", new MenuShortcut(80));
    this.playAll.addActionListener(this);
    localMenu3.add(this.playAll);
    this.repeatAll = new MenuItem("Repeat All");
    this.repeatAll.addActionListener(this);
    localMenu3.add(this.repeatAll);
    this.playMeasure = new MenuItem("Play Last Measure");
    this.playMeasure.addActionListener(this);
    localMenu3.add(this.playMeasure);
    this.repeatMeasure = new MenuItem("Repeat Last Measure");
    this.repeatMeasure.addActionListener(this);
    localMenu3.add(this.repeatMeasure);
    this.stopPlay = new MenuItem("Stop Playback", new MenuShortcut(80, true));
    this.stopPlay.addActionListener(this);
    localMenu3.add(this.stopPlay);
    Menu localMenu5 = new Menu("Stave");
    localMenu1.add(localMenu5);
    this.trebleStave = new MenuItem("Treble");
    this.trebleStave.addActionListener(this);
    localMenu5.add(this.trebleStave);
    this.bassStave = new MenuItem("Bass");
    this.bassStave.addActionListener(this);
    localMenu5.add(this.bassStave);
    this.pianoStave = new MenuItem("Piano");
    this.pianoStave.addActionListener(this);
    localMenu5.add(this.pianoStave);
    this.grandStave = new MenuItem("Grand");
    this.grandStave.addActionListener(this);
    localMenu5.add(this.grandStave);
    this.automaticStave = new MenuItem("Automatic");
    this.automaticStave.addActionListener(this);
    localMenu5.add(this.automaticStave);
    localMenu1.add("-");
    this.quit = new MenuItem("Quit", new MenuShortcut(81));
    this.quit.addActionListener(this);
    localMenu1.add(this.quit);
    this.viewDetails = new MenuItem("Note data as text");
    this.viewDetails.addActionListener(this);
    localMenu4.add(this.viewDetails);
    this.viewZoom = new MenuItem("View phrase section", new MenuShortcut(86));
    this.viewZoom.addActionListener(this);
    localMenu4.add(this.viewZoom);
    this.barNumbers = new MenuItem("Bar Numbers", new MenuShortcut(66));
    this.barNumbers.addActionListener(this);
    localMenu4.add(this.barNumbers);
    this.viewTitle = new MenuItem("Stave Title");
    this.viewTitle.addActionListener(this);
    localMenu4.add(this.viewTitle);
    localMenuBar.add(localMenu1);
    localMenuBar.add(localMenu2);
    localMenuBar.add(localMenu3);
    localMenuBar.add(localMenu4);
    setMenuBar(localMenuBar);
    this.scroll = new ScrollPane(1);
    this.scroll.getHAdjustable().setUnitIncrement(10);
    this.scroll.getVAdjustable().setUnitIncrement(10);
    this.scoreBG = new Panel();
    this.layout = new GridBagLayout();
    this.scoreBG.setLayout(this.layout);
    this.constraints = new GridBagConstraints();
    setupConstraints();
    this.scroll.add(this.scoreBG);
    add(this.scroll);
    setupArrays();
    makeAppropriateStaves();
    pack();
    setLocation(this.locationX, this.locationY);
    show();
  }
  
  private void setupArrays()
  {
    this.phraseArray = new Phrase[this.score.size()];
    this.cphraseArray = new CPhrase[this.score.size()];
    this.staveArray = new Stave[this.score.size()];
    for (int i = 0; i < this.staveArray.length; i++)
    {
      this.cphraseArray[i] = this.score.getPart(i).getCPhrase(0);
      this.staveArray[i] = new PianoStave();
      this.staveArray[i].setKeySignature(this.score.getKeySignature());
      this.staveArray[i].setMetre(this.score.getNumerator());
      this.staveArray[i].setBarNumbers(true);
    }
  }
  
  private void setupConstraints()
  {
    this.constraints.weightx = 100.0D;
    this.constraints.weighty = 0.0D;
    this.constraints.gridx = 0;
    this.constraints.gridy = 0;
    this.constraints.gridwidth = 1;
    this.constraints.gridheight = 1;
    this.constraints.fill = 1;
  }
  
  private void calcHeight()
  {
    this.height = 0;
    for (int i = 0; i < this.staveArray.length; i++) {
      this.height += this.staveArray[i].getSize().height;
    }
  }
  
  private void makeAppropriateStaves()
  {
    Stave[] arrayOfStave = new Stave[this.staveArray.length];
    for (int i = 0; i < this.score.size(); i++)
    {
      Phrase localPhrase = this.score.getPart(i).getPhrase(0);
      arrayOfStave[i] = new PianoStave();
      if ((localPhrase.getHighestPitch() < 93) && (localPhrase.getLowestPitch() > 54)) {
        arrayOfStave[i] = new TrebleStave();
      } else if ((localPhrase.getHighestPitch() < 65) && (localPhrase.getLowestPitch() > 35)) {
        arrayOfStave[i] = new BassStave();
      } else if ((localPhrase.getHighestPitch() > 93) || (localPhrase.getLowestPitch() < 35)) {
        arrayOfStave[i] = new GrandStave();
      }
    }
    updateAllStaves(arrayOfStave);
  }
  
  private void makeTrebleStave()
  {
    Stave[] arrayOfStave = new Stave[this.score.size()];
    for (int i = 0; i < this.staveArray.length; i++) {
      arrayOfStave[i] = new TrebleStave();
    }
    updateAllStaves(arrayOfStave);
  }
  
  private void updateAllStaves(Stave[] paramArrayOfStave)
  {
    int i = 0;
    int j = 0;
    int k = 0;
    this.scoreBG.removeAll();
    for (int m = 0; m < this.staveArray.length; m++)
    {
      paramArrayOfStave[m].setKeySignature(this.staveArray[m].getKeySignature());
      paramArrayOfStave[m].setMetre(this.staveArray[m].getMetre());
      paramArrayOfStave[m].setBarNumbers(this.staveArray[m].getBarNumbers());
      //paramArrayOfStave[m].setPhrase(this.phraseArray[m]);
      paramArrayOfStave[m].setCPhrase(this.cphraseArray[m]);
      this.staveArray[m] = paramArrayOfStave[m];
      paramArrayOfStave[m] = null;
      this.constraints.gridy = i;
      if ((this.staveArray[m].getClass().isInstance(new TrebleStave())) || (this.staveArray[m].getClass().isInstance(new BassStave()))) {
        j = 1;
      } else if (this.staveArray[m].getClass().isInstance(new PianoStave())) {
        j = 2;
      } else {
        j = 3;
      }
      this.constraints.gridheight = j;
      this.scoreBG.add(this.staveArray[m], this.constraints);
      i += j;
      k += this.staveArray[m].getPanelHeight();
    }
    this.scroll.setSize(new Dimension(this.width, k));
    Toolkit localToolkit = Toolkit.getDefaultToolkit();
    Dimension localDimension = localToolkit.getScreenSize();
    setSize(new Dimension(this.width, Math.min(localDimension.height - 40, k + 40)));
    pack();
  }
  
  private void makeBassStave()
  {
    Stave[] arrayOfStave = new Stave[this.score.size()];
    for (int i = 0; i < this.staveArray.length; i++) {
      arrayOfStave[i] = new BassStave();
    }
    updateAllStaves(arrayOfStave);
  }
  
  private void makePianoStave()
  {
    Stave[] arrayOfStave = new Stave[this.score.size()];
    for (int i = 0; i < arrayOfStave.length; i++) {
      arrayOfStave[i] = new PianoStave();
    }
    updateAllStaves(arrayOfStave);
  }
  
  private void makeGrandStave()
  {
    Stave[] arrayOfStave = new Stave[this.score.size()];
    for (int i = 0; i < arrayOfStave.length; i++) {
      arrayOfStave[i] = new GrandStave();
    }
    updateAllStaves(arrayOfStave);
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getSource() == this.close)
    {
      dispose();
    }
    else if (paramActionEvent.getSource() == this.newStave)
    {
      new Notate();
    }
    else if (paramActionEvent.getSource() == this.open)
    {
      openMidi();
    }
    else if (paramActionEvent.getSource() == this.openjm)
    {
      openJM();
    }
    else if (paramActionEvent.getSource() == this.openJmXml)
    {
      openJMXML();
    }
    else
    {
      int i;
      if (paramActionEvent.getSource() == this.keySig)
      {
        for (i = 0; i < this.staveArray.length; i++) {
          if (this.staveArray[i].getKeySignature() == 0)
          {
            this.staveArray[i].setKeySignature(2);
            this.staveArray[i].repaint();
          }
          else
          {
            this.staveArray[i].setKeySignature(0);
            this.staveArray[i].repaint();
          }
        }
      }
      else if (paramActionEvent.getSource() == this.timeSig)
      {
        for (i = 0; i < this.staveArray.length; i++) {
          if (this.staveArray[i].getMetre() == 0.0D)
          {
            this.staveArray[i].setMetre(4.0D);
            this.staveArray[i].repaint();
          }
          else
          {
            this.staveArray[i].setMetre(0.0D);
            this.staveArray[i].repaint();
          }
        }
      }
      else if (paramActionEvent.getSource() == this.saveJM)
      {
        saveJM();
      }
      else if (paramActionEvent.getSource() == this.saveJmXml)
      {
        saveJMXML();
      }
      else if (paramActionEvent.getSource() == this.saveMidi)
      {
        saveMidi();
      }
      else if (paramActionEvent.getSource() == this.quit)
      {
        System.exit(0);
      }
      else if (paramActionEvent.getSource() == this.delete)
      {
        for (i = 0; i < this.staveArray.length; i++) {
          this.staveArray[i].deleteLastNote();
        }
      }
      else if (paramActionEvent.getSource() == this.clear)
      {
        for (i = 0; i < this.staveArray.length; i++)
        {
          this.staveArray[i].getPhrase().empty();
          this.staveArray[i].repaint();
        }
      }
      else if (paramActionEvent.getSource() == this.trebleStave)
      {
        setCursor(new Cursor(3));
        makeTrebleStave();
        for (i = 0; i < this.staveArray.length; i++) {
          this.staveArray[i].repaint();
        }
        setCursor(new Cursor(0));
      }
      else if (paramActionEvent.getSource() == this.bassStave)
      {
        setCursor(new Cursor(3));
        makeBassStave();
        for (i = 0; i < this.staveArray.length; i++) {
          this.staveArray[i].repaint();
        }
        setCursor(new Cursor(0));
      }
      else if (paramActionEvent.getSource() == this.pianoStave)
      {
        setCursor(new Cursor(3));
        makePianoStave();
        for (i = 0; i < this.staveArray.length; i++) {
          this.staveArray[i].repaint();
        }
        setCursor(new Cursor(0));
      }
      else if (paramActionEvent.getSource() == this.grandStave)
      {
        setCursor(new Cursor(3));
        makeGrandStave();
        for (i = 0; i < this.staveArray.length; i++) {
          this.staveArray[i].repaint();
        }
        setCursor(new Cursor(0));
      }
      else if (paramActionEvent.getSource() == this.automaticStave)
      {
        setCursor(new Cursor(3));
        makeAppropriateStaves();
        for (i = 0; i < this.staveArray.length; i++) {
          this.staveArray[i].repaint();
        }
        setCursor(new Cursor(0));
      }
      else
      {
        Object localObject;
        if (paramActionEvent.getSource() == this.setParameters)
        {
          localObject = new ParmScreen(this);
          double d = this.staveArray[0].getPhrase().getTempo();
          ((ParmScreen)localObject).getParms(this.staveArray[0].getPhrase(), 15, 15);
          repaint();
        }
        else if (paramActionEvent.getSource() == this.playAll)
        {
          Play.midi(this.score, false);
        }
        else if (paramActionEvent.getSource() == this.repeatAll)
        {
          Play.midiCycle(this.score);
        }
        else if (paramActionEvent.getSource() == this.stopPlay)
        {
          Play.stopMidi();
          Play.stopMidiCycle();
        }
        else if (paramActionEvent.getSource() == this.repeatMeasure)
        {
          Play.midiCycle(getLastMeasure());
        }
        else if (paramActionEvent.getSource() == this.playMeasure)
        {
          Play.midi(getLastMeasure(), false);
        }
        else if (paramActionEvent.getSource() == this.addNotes)
        {
          localObject = new LetterNotesEditor(this);
          ((LetterNotesEditor)localObject).getNotes(this.staveArray[0]);
          this.staveArray[0].repaint();
        }
        else if (paramActionEvent.getSource() == this.adjustTiming)
        {
          adjustTimeValues(this.staveArray[0].getPhrase());
          this.staveArray[0].repaint();
        }
        else if (paramActionEvent.getSource() == this.viewDetails)
        {
          localObject = new PhraseViewer(this);
          ((PhraseViewer)localObject).showPhrase(this.staveArray[0], this.staveArray[0].getPhrase(), 15, 15);
        }
        else if (paramActionEvent.getSource() == this.viewZoom)
        {
          if (!this.zoomed)
          {
            localObject = new CpnZoomScreen(this);
            this.beforeZoom = this.staveArray[0].getPhrase().copy();
            this.afterZoom = this.staveArray[0].getPhrase().copy();
            this.beforeZoom.empty();
            this.afterZoom.empty();
            ((CpnZoomScreen)localObject).zoomIn(this.beforeZoom, this.staveArray[0].getPhrase(), this.afterZoom);
            if (this.beforeZoom.size() + this.afterZoom.size() > 0)
            {
              this.zoomed = true;
              this.viewZoom.setLabel("View complete phrase");
              repaint();
            }
          }
          else
          {
            CpnZoomScreen.zoomOut(this.beforeZoom, this.staveArray[0].getPhrase(), this.afterZoom);
            this.zoomed = false;
            this.viewZoom.setLabel("View phrase section");
            repaint();
          }
        }
        else if (paramActionEvent.getSource() == this.barNumbers)
        {
          for (int j = 0; j < this.staveArray.length; j++)
          {
            this.staveArray[j].setBarNumbers(!this.staveArray[j].getBarNumbers());
            this.staveArray[j].repaint();
          }
        }
        else if (paramActionEvent.getSource() == this.viewTitle)
        {
          toggleDisplayTitle();
        }
      }
    }
  }
  
  public void openMidi()
  {
    Score localScore = new Score();
    FileDialog localFileDialog = new FileDialog(this, "Select a MIDI file.", 0);
    localFileDialog.setDirectory(this.lastDirectory);
    localFileDialog.setFile(this.lastFileName);
    localFileDialog.show();
    String str = localFileDialog.getFile();
    if (str != null)
    {
      this.lastFileName = str;
      this.lastDirectory = localFileDialog.getDirectory();
      Read.midi(localScore, this.lastDirectory + str);
      setNewScore(localScore);
    }
  }
  
  private void setNewScore(Score paramScore)
  {
    this.score = paramScore;
    setupArrays();
    makeAppropriateStaves();
  }
  
  public void openJM()
  {
    FileDialog localFileDialog = new FileDialog(this, "Select a jm score file.", 0);
    localFileDialog.setDirectory(this.lastDirectory);
    localFileDialog.show();
    String str = localFileDialog.getFile();
    if (str != null)
    {
      Score localScore = new Score();
      this.lastDirectory = localFileDialog.getDirectory();
      Read.jm(localScore, this.lastDirectory + str);
      setNewScore(localScore);
    }
  }
  
  public void openJMXML()
  {
    FileDialog localFileDialog = new FileDialog(this, "Select a jMusic XML score file.", 0);
    localFileDialog.setDirectory(this.lastDirectory);
    localFileDialog.show();
    String str = localFileDialog.getFile();
    if (str != null)
    {
      Score localScore = new Score();
      this.lastDirectory = localFileDialog.getDirectory();
      Read.xml(localScore, this.lastDirectory + str);
      setNewScore(localScore);
    }
  }
  
  public void saveMidi()
  {
    FileDialog localFileDialog = new FileDialog(this, "Save as a MIDI file...", 1);
    localFileDialog.show();
    if (localFileDialog.getFile() != null) {
      Write.midi(this.score, localFileDialog.getDirectory() + localFileDialog.getFile());
    }
  }
  
  public void saveJM()
  {
    FileDialog localFileDialog = new FileDialog(this, "Save as a jm file...", 1);
    localFileDialog.show();
    if (localFileDialog.getFile() != null) {
      Write.jm(this.score, localFileDialog.getDirectory() + localFileDialog.getFile());
    }
  }
  
  public void saveJMXML()
  {
    FileDialog localFileDialog = new FileDialog(this, "Save as a jMusic XML file...", 1);
    localFileDialog.show();
    if (localFileDialog.getFile() != null) {
      Write.xml(this.score, localFileDialog.getDirectory() + localFileDialog.getFile());
    }
  }
  
  public Phrase readMidiPhrase()
  {
    FileDialog localFileDialog = new FileDialog(this, "Select a MIDI file.", 0);
    localFileDialog.show();
    String str = localFileDialog.getFile();
    Phrase localPhrase = new Phrase(0.0D);
    Score localScore = new Score();
    if (str != null) {
      Read.midi(localScore, localFileDialog.getDirectory() + str);
    }
    localScore.clean();
    if ((localScore.size() > 0) && (localScore.getPart(0).size() > 0)) {
      localPhrase = localScore.getPart(0).getPhrase(0);
    }
    return localPhrase;
  }
  
  private Score getLastMeasure()
  {
    //double d1 = this.phraseArray[0].getNumerator();
	double d1 = this.cphraseArray[0].getNumerator();
	double d2 = this.score.getEndTime();
    int i = (int)(d2 / d1);
    double d3 = d1 * i;
    if (d3 == d2) {
      d3 -= d1;
    }
    Score localScore = this.score.copy(d3, d2);
    for (int j = 0; j < localScore.size(); j++) {
      localScore.getPart(j).getPhrase(0).setStartTime(0.0D);
    }
    return localScore;
  }
  
  private static double getRhythmAdjustment(double paramDouble1, double paramDouble2)
  {
    double d1 = paramDouble1 / paramDouble2;
    double d2 = 1.0E-5D;
    double d3 = 0.0D;
    double d4 = Math.floor(d1);
    while ((Math.floor(d1 + d2) > d4) && (d2 > 1.0E-14D))
    {
      d3 = d2;
      d2 /= 2.0D;
    }
    return d3 * paramDouble2;
  }
  
  private static void adjustTimeValues(Phrase paramPhrase)
  {
    double d1;
    double d2;
    int i;
    for (i = 0; i < paramPhrase.size(); i++)
    {
      d1 = paramPhrase.getNote(i).getRhythmValue();
      d2 = getRhythmAdjustment(d1, 0.00390625D);
      paramPhrase.getNote(i).setRhythmValue(d1 + d2);
    }
    double d3 = 0.0D;
    for (i = 0; i < paramPhrase.size(); i++)
    {
      d1 = paramPhrase.getNote(i).getRhythmValue();
      d3 += d1;
      d2 = getRhythmAdjustment(d3, 1.0D);
      paramPhrase.getNote(i).setRhythmValue(d1 + d2);
      d3 += d2;
    }
  }
  
  public void toggleDisplayTitle()
  {
    for (int i = 0; i < this.staveArray.length; i++) {
      this.staveArray[i].setDisplayTitle(!this.staveArray[i].getDisplayTitle());
    }
  }
  
  public void windowOpened(WindowEvent paramWindowEvent) {}
  
  public void windowClosing(WindowEvent paramWindowEvent)
  {
    if (paramWindowEvent.getSource() == this) {
      dispose();
    }
    if (paramWindowEvent.getSource() == this.keyDialog) {
      this.keyDialog.dispose();
    }
    if (paramWindowEvent.getSource() == this.timeDialog) {
      this.timeDialog.dispose();
    }
  }
  
  public void windowClosed(WindowEvent paramWindowEvent) {}
  
  public void windowIconified(WindowEvent paramWindowEvent) {}
  
  public void windowDeiconified(WindowEvent paramWindowEvent) {}
  
  public void windowActivated(WindowEvent paramWindowEvent) {}
  
  public void windowDeactivated(WindowEvent paramWindowEvent) {}
  
  class PlayRepeater
    extends Thread
  {
    JmMidiPlayer midiPlayer;
    Notate n;
    
    public PlayRepeater(String paramString, Notate paramNotate, JmMidiPlayer paramJmMidiPlayer)
    {
      super();
      this.n = paramNotate;
      this.midiPlayer = paramJmMidiPlayer;
    }
    
    public void run()
    {
      do
      {
        this.midiPlayer.play();
      } while (!this.n.timeToStop);
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\cpn\Notate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */