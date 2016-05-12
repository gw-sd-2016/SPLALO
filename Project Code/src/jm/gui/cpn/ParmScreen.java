package jm.gui.cpn;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.PrintStream;
import java.util.StringTokenizer;
import java.util.Vector;
import jm.music.data.Note;
import jm.music.data.Phrase;

public class ParmScreen
  extends Dialog
  implements ActionListener, WindowListener
{
  private List instrumentList;
  private List volumeList;
  private List tempoList;
  private Button instrumentButton;
  private Button volumeButton;
  private Button tempoButton;
  private Button closeButton;
  private Label instrumentLabel;
  private Label volumeLabel;
  private Label tempoLabel;
  private Phrase phrase;
  
  public ParmScreen(Frame paramFrame)
  {
    super(paramFrame, "Set Music Parameters", true);
    initializeLists();
    initializeButtons();
    initializeLabels();
    setSize(500, 400);
    placeControls();
    addWindowListener(this);
    setVisible(false);
    pack();
  }
  
  public void windowOpened(WindowEvent paramWindowEvent) {}
  
  public void windowClosing(WindowEvent paramWindowEvent)
  {
    if (paramWindowEvent.getSource() == this) {
      dispose();
    }
  }
  
  public void windowClosed(WindowEvent paramWindowEvent) {}
  
  public void windowIconified(WindowEvent paramWindowEvent) {}
  
  public void windowDeiconified(WindowEvent paramWindowEvent) {}
  
  public void windowActivated(WindowEvent paramWindowEvent) {}
  
  public void windowDeactivated(WindowEvent paramWindowEvent) {}
  
  public void getParms(Phrase paramPhrase, int paramInt1, int paramInt2)
  {
    this.phrase = paramPhrase;
    setLocation(paramInt1, paramInt2);
    show();
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getSource() == this.tempoButton)
    {
      System.out.print("Adjusting Tempo ");
      System.out.print(this.tempoList.getSelectedItem());
      double d1 = this.phrase.getTempo();
      if (d1 < 10.0D) {
        d1 = 60.0D;
      }
      double d2 = getTempo(this.tempoList.getSelectedItem());
      this.phrase.setTempo(d2);
      multiplyTimesBy(d1 / d2);
    }
    else if (paramActionEvent.getSource() == this.volumeButton)
    {
      setVolume(getVolume(this.volumeList.getSelectedItem()));
    }
    else if (paramActionEvent.getSource() == this.instrumentButton)
    {
      this.phrase.setInstrument(getInstrument(this.instrumentList.getSelectedItem()));
    }
    else if (paramActionEvent.getSource() == this.closeButton)
    {
      dispose();
    }
  }
  
  private void initializeLists()
  {
    initializeInstrumentList();
    initializeVolumeList();
    initializeTempoList();
  }
  
  private void initializeInstrumentList()
  {
    this.instrumentList = new List();
    this.instrumentList.add("Accordion             21");
    this.instrumentList.add("Applausen            126");
    this.instrumentList.add("Bandneon              23");
    this.instrumentList.add("Banjo                105");
    this.instrumentList.add("Bagpipes             109");
    this.instrumentList.add("Bass   (Acoustic)     32");
    this.instrumentList.add("Bass   (Fingerd)      33");
    this.instrumentList.add("Bass   (Fretless)     35");
    this.instrumentList.add("Bass   (Picked)       34");
    this.instrumentList.add("Bass   (Slap)         36");
    this.instrumentList.add("Bass   (Synth)        38");
    this.instrumentList.add("Bass   (Synth)        38");
    this.instrumentList.add("Bassoon               70");
    this.instrumentList.add("Bottle                76");
    this.instrumentList.add("Brass  (Synthetic)    62");
    this.instrumentList.add("Calliope              82");
    this.instrumentList.add("Celeste                8");
    this.instrumentList.add("Cello                 42");
    this.instrumentList.add("Charang               84");
    this.instrumentList.add("Choir                 52");
    this.instrumentList.add("Clarinet              71");
    this.instrumentList.add("Clavinet               7");
    this.instrumentList.add("Contrabass            43");
    this.instrumentList.add("English Horn          69");
    this.instrumentList.add("Fiddle               110");
    this.instrumentList.add("French Horn           60");
    this.instrumentList.add("Flute                 73");
    this.instrumentList.add("Glockenspiel           9");
    this.instrumentList.add("Guitar (Clean)        27");
    this.instrumentList.add("Guitar (Distorted)    30");
    this.instrumentList.add("Guitar Harmonics      31");
    this.instrumentList.add("Guitar (Jazz)         26");
    this.instrumentList.add("Guitar (Muted)        28");
    this.instrumentList.add("Guitar (Nylon)        24");
    this.instrumentList.add("Guitar (Overdrive)    29");
    this.instrumentList.add("Guitar (Steel)        25");
    this.instrumentList.add("Harmonica             22");
    this.instrumentList.add("Harp                  46");
    this.instrumentList.add("Harpsichord           76");
    this.instrumentList.add("Marimba               12");
    this.instrumentList.add("Music Box             10");
    this.instrumentList.add("Oboe                  68");
    this.instrumentList.add("Ocarina               79");
    this.instrumentList.add("Orchestra Hit         55");
    this.instrumentList.add("Organ                 16");
    this.instrumentList.add("Organ (Church)        19");
    this.instrumentList.add("Organ (Reed)          20");
    this.instrumentList.add("Pan Flute             75");
    this.instrumentList.add("Piano                  0");
    this.instrumentList.add("Piano (Electric)       4");
    this.instrumentList.add("Piano (Honkeytonk)     3");
    this.instrumentList.add("Piccolo               72");
    this.instrumentList.add("Recorder              74");
    this.instrumentList.add("Saxophone (Alto)      65");
    this.instrumentList.add("Saxophone (Soprano)   64");
    this.instrumentList.add("Saxophone (Tenor)     66");
    this.instrumentList.add("Saxophone (Baritone)  67");
    this.instrumentList.add("Shakuhachi            77");
    this.instrumentList.add("Steel Drums          114");
    this.instrumentList.add("Strings               48");
    this.instrumentList.add("Strings (Pizzicato)   45");
    this.instrumentList.add("Strings (Slow)        51");
    this.instrumentList.add("Strings (Synth)       50");
    this.instrumentList.add("Strings (Tremolo)     44");
    this.instrumentList.add("Tom-Tom              119");
    this.instrumentList.add("Trombone              57");
    this.instrumentList.add("Trumpet               56");
    this.instrumentList.add("Trumpet (Muted)       59");
    this.instrumentList.add("Tuba                  58");
    this.instrumentList.add("Tubular Bell          14");
    this.instrumentList.add("Timpani               47");
    this.instrumentList.add("Vibraphone            11");
    this.instrumentList.add("Viola                 41");
    this.instrumentList.add("Violin                40");
    this.instrumentList.add("Voice                 53");
    this.instrumentList.add("Vox                   56");
    this.instrumentList.add("Whistle               78");
    this.instrumentList.add("Wood Block           115");
    this.instrumentList.add("Xylophone             13");
  }
  
  private void initializeVolumeList()
  {
    this.volumeList = new List();
    int i = 7;
    int j = 256;
    int k = 6;
    int m = i;
    while (m <= j)
    {
      this.volumeList.add(new Integer(m).toString());
      m += k;
    }
  }
  
  private void initializeTempoList()
  {
    this.tempoList = new List();
    double d;
    for (d = 36.0D; d < 143.0D; d += 2.0D) {
      this.tempoList.add(new Double(d).toString());
    }
    for (d = 144.0D; d < 250.0D; d += 4.0D) {
      this.tempoList.add(new Double(d).toString());
    }
    for (d = 256.0D; d < 404.0D; d += 8.0D) {
      this.tempoList.add(new Double(d).toString());
    }
  }
  
  private void initializeButtons()
  {
    this.instrumentButton = new Button("Apply");
    this.volumeButton = new Button("Apply");
    this.tempoButton = new Button("Apply");
    this.closeButton = new Button("Close");
  }
  
  private void initializeLabels()
  {
    this.instrumentLabel = new Label("Instrument");
    this.volumeLabel = new Label("Volume");
    this.tempoLabel = new Label("Tempo");
  }
  
  private void placeControls()
  {
    GridBagLayout localGridBagLayout = new GridBagLayout();
    GridBagConstraints localGridBagConstraints = new GridBagConstraints();
    setLayout(localGridBagLayout);
    localGridBagConstraints.fill = 1;
    localGridBagConstraints.weightx = 0.5D;
    localGridBagConstraints.gridwidth = 1;
    localGridBagConstraints.gridheight = 1;
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.gridheight = 3;
    localGridBagLayout.setConstraints(this.instrumentLabel, localGridBagConstraints);
    add(this.instrumentLabel);
    localGridBagConstraints.gridx = 1;
    localGridBagLayout.setConstraints(this.instrumentList, localGridBagConstraints);
    add(this.instrumentList);
    localGridBagConstraints.gridwidth = 0;
    localGridBagConstraints.gridx = 2;
    Panel localPanel1 = new Panel();
    localPanel1.add(this.instrumentButton);
    localGridBagLayout.setConstraints(localPanel1, localGridBagConstraints);
    add(localPanel1);
    localGridBagConstraints.gridheight = 1;
    localGridBagConstraints.gridwidth = 1;
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 3;
    localGridBagLayout.setConstraints(this.volumeLabel, localGridBagConstraints);
    add(this.volumeLabel);
    localGridBagConstraints.gridx = 1;
    localGridBagLayout.setConstraints(this.volumeList, localGridBagConstraints);
    add(this.volumeList);
    localGridBagConstraints.gridwidth = 0;
    localGridBagConstraints.gridx = 2;
    Panel localPanel2 = new Panel();
    localPanel2.add(this.volumeButton);
    localGridBagLayout.setConstraints(localPanel2, localGridBagConstraints);
    add(localPanel2);
    localGridBagConstraints.gridwidth = 1;
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 4;
    localGridBagLayout.setConstraints(this.tempoLabel, localGridBagConstraints);
    add(this.tempoLabel);
    localGridBagConstraints.gridx = 1;
    localGridBagLayout.setConstraints(this.tempoList, localGridBagConstraints);
    add(this.tempoList);
    localGridBagConstraints.gridwidth = 0;
    localGridBagConstraints.gridx = 2;
    Panel localPanel3 = new Panel();
    localPanel3.add(this.tempoButton);
    localGridBagLayout.setConstraints(localPanel3, localGridBagConstraints);
    add(localPanel3);
    localGridBagConstraints.gridwidth = 1;
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 5;
    localGridBagLayout.setConstraints(this.closeButton, localGridBagConstraints);
    add(this.closeButton);
    this.instrumentButton.addActionListener(this);
    this.volumeButton.addActionListener(this);
    this.tempoButton.addActionListener(this);
    this.closeButton.addActionListener(this);
  }
  
  private static double getTempo(String paramString)
  {
    return new Double(pullFirst(paramString)).doubleValue();
  }
  
  private static int getVolume(String paramString)
  {
    return new Integer(pullLast(paramString)).intValue();
  }
  
  private static int getInstrument(String paramString)
  {
    return new Integer(pullLast(paramString)).intValue();
  }
  
  private void setVolume(int paramInt)
  {
    Vector localVector = this.phrase.getNoteList();
    for (int i = 0; i < localVector.size(); i++) {
      if (this.phrase.getNote(i).getDynamic() != 0) {
        this.phrase.getNote(i).setDynamic(paramInt);
      }
    }
  }
  
  private void multiplyTimesBy(double paramDouble)
  {
    Vector localVector = this.phrase.getNoteList();
    System.out.println(paramDouble);
    for (int i = 0; i < localVector.size(); i++) {}
  }
  
  private static String pullFirst(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString);
    String str;
    for (str = ""; (str == "") && (localStringTokenizer.hasMoreTokens()); str = localStringTokenizer.nextToken()) {}
    return str;
  }
  
  private static String pullLast(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString);
    Object localObject = "";
    String str = "";
    while (localStringTokenizer.hasMoreTokens())
    {
      str = localStringTokenizer.nextToken();
      if (str != "") {
        localObject = str;
      }
    }
    return (String)localObject;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\cpn\ParmScreen.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */