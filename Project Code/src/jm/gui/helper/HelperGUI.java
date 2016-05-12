package jm.gui.helper;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.PrintStream;
import jm.JMC;
import jm.audio.Instrument;
import jm.midi.MidiSynth;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Play;
import jm.util.Read;
import jm.util.View;
import jm.util.Write;

public class HelperGUI
  extends Frame
  implements JMC, ActionListener, AdjustmentListener
{
  protected Score score = new Score();
  private Button composeBtn;
  private Button playBtn;
  private Button stopBtn;
  private Button showBtn;
  private Button sketchBtn;
  private Button histogramBtn;
  private Button printBtn;
  private Button saveBtn;
  private Button renderBtn;
  private Button notateBtn;
  private Button readMidiBtn;
  private Button audioViewBtn;
  private Button audioPlayBtn;
  private Button audioStopBtn;
  private Button xmlOpenBtn;
  private Button xmlSaveBtn;
  private Scrollbar sliderA;
  private Scrollbar sliderB;
  private Scrollbar sliderC;
  private Scrollbar sliderD;
  private Scrollbar sliderE;
  private Label labelA;
  private Label labelB;
  private Label labelC;
  private Label labelD;
  private Label labelE;
  private Label commentLabA;
  private Label commentLabB;
  private Label commentLabC;
  private Label commentLabD;
  private Label commentLabE;
  protected Instrument[] insts;
  protected int variableA;
  protected int variableB;
  protected int variableC;
  protected int variableD;
  protected int variableE;
  private MidiSynth ms = new MidiSynth();
  private boolean playing = false;
  protected String audioFileName;
  protected Panel sliders;
  
  public HelperGUI()
  {
    super("jMusic Helper GUI");
    setLayout(new BorderLayout());
    Panel localPanel1 = new Panel(new BorderLayout());
    add(localPanel1, "Center");
    this.sliders = new Panel();
    this.sliders.setLayout(new GridLayout(6, 1));
    Panel localPanel2 = new Panel(new BorderLayout());
    Panel localPanel3 = new Panel();
    Panel localPanel4 = new Panel();
    Panel localPanel5 = new Panel(new BorderLayout());
    Panel localPanel6 = new Panel();
    Panel localPanel7 = new Panel();
    Panel localPanel8 = new Panel(new BorderLayout());
    Panel localPanel9 = new Panel(new BorderLayout());
    Panel localPanel10 = new Panel(new BorderLayout());
    Label localLabel1 = new Label("Create and View");
    localLabel1.setAlignment(1);
    localPanel2.add(localLabel1, "North");
    localPanel1.add(localPanel2, "North");
    Label localLabel2 = new Label("MIDI Options");
    localLabel2.setAlignment(1);
    localPanel5.add(localLabel2, "North");
    localPanel1.add(localPanel5, "Center");
    Label localLabel3 = new Label("Audio Options");
    localLabel3.setAlignment(1);
    localPanel8.add(localLabel3, "North");
    localPanel1.add(localPanel8, "South");
    Panel localPanel11 = new Panel(new BorderLayout());
    Label localLabel4 = new Label("XML Options");
    localLabel4.setAlignment(1);
    localPanel9.add(localLabel4, "North");
    localPanel11.add(localPanel9, "North");
    Label localLabel5 = new Label("Compositional parameters");
    localLabel5.setAlignment(1);
    Panel localPanel12 = new Panel();
    localPanel12.add(localLabel5);
    localPanel10.add(localPanel12, "North");
    localPanel11.add(localPanel10, "Center");
    add(localPanel11, "South");
    Panel localPanel13 = new Panel(new GridLayout(3, 1));
    this.composeBtn = new Button("Compose");
    this.composeBtn.addActionListener(this);
    Panel localPanel14 = new Panel();
    localPanel14.add(this.composeBtn);
    localPanel13.add(localPanel14);
    this.showBtn = new Button("View.show()");
    this.showBtn.addActionListener(this);
    this.showBtn.setEnabled(false);
    localPanel3.add(this.showBtn);
    this.notateBtn = new Button("View.notate()");
    this.notateBtn.addActionListener(this);
    this.notateBtn.setEnabled(false);
    localPanel3.add(this.notateBtn);
    this.printBtn = new Button("View.print()");
    this.printBtn.addActionListener(this);
    this.printBtn.setEnabled(false);
    localPanel3.add(this.printBtn);
    localPanel13.add(localPanel3);
    this.sketchBtn = new Button("View.sketch()");
    this.sketchBtn.addActionListener(this);
    this.sketchBtn.setEnabled(false);
    localPanel4.add(this.sketchBtn);
    this.histogramBtn = new Button("View.histogram()");
    this.histogramBtn.addActionListener(this);
    this.histogramBtn.setEnabled(false);
    localPanel4.add(this.histogramBtn);
    localPanel13.add(localPanel4);
    localPanel2.add(localPanel13, "Center");
    Panel localPanel15 = new Panel(new GridLayout(2, 1));
    this.playBtn = new Button("Play.midi()");
    this.playBtn.addActionListener(this);
    this.playBtn.setEnabled(false);
    localPanel7.add(this.playBtn);
    this.stopBtn = new Button("Stop MIDI");
    this.stopBtn.addActionListener(this);
    this.stopBtn.setEnabled(false);
    localPanel7.add(this.stopBtn);
    localPanel15.add(localPanel7);
    this.saveBtn = new Button("Write.midi()");
    this.saveBtn.addActionListener(this);
    this.saveBtn.setEnabled(false);
    localPanel6.add(this.saveBtn);
    this.readMidiBtn = new Button("Read.midi()");
    this.readMidiBtn.addActionListener(this);
    this.readMidiBtn.setEnabled(true);
    localPanel6.add(this.readMidiBtn);
    localPanel15.add(localPanel6);
    localPanel5.add(localPanel15, "Center");
    Panel localPanel16 = new Panel();
    this.renderBtn = new Button("Write.au()");
    this.renderBtn.addActionListener(this);
    this.renderBtn.setEnabled(false);
    localPanel16.add(this.renderBtn);
    localPanel8.add(localPanel16, "Center");
    this.audioViewBtn = new Button("View.au()");
    this.audioViewBtn.addActionListener(this);
    this.audioViewBtn.setEnabled(false);
    localPanel16.add(this.audioViewBtn);
    this.audioPlayBtn = new Button("Play.au()");
    this.audioPlayBtn.addActionListener(this);
    this.audioPlayBtn.setEnabled(false);
    localPanel16.add(this.audioPlayBtn);
    Panel localPanel17 = new Panel();
    this.xmlOpenBtn = new Button("Read.xml()");
    this.xmlOpenBtn.addActionListener(this);
    this.xmlOpenBtn.setEnabled(true);
    localPanel17.add(this.xmlOpenBtn);
    this.xmlSaveBtn = new Button("Write.xml()");
    this.xmlSaveBtn.addActionListener(this);
    this.xmlSaveBtn.setEnabled(false);
    localPanel17.add(this.xmlSaveBtn);
    localPanel9.add(localPanel17, "Center");
    Panel localPanel18 = new Panel(new GridLayout(1, 3));
    this.labelA = new Label(" variableA = 0");
    localPanel18.add(this.labelA);
    this.sliderA = new Scrollbar(0, 0, 15, 0, 142);
    this.sliderA.addAdjustmentListener(this);
    localPanel18.add(this.sliderA);
    this.commentLabA = new Label(" No Comment ");
    localPanel18.add(this.commentLabA);
    this.sliders.add(localPanel18);
    Panel localPanel19 = new Panel(new GridLayout(1, 3));
    this.labelB = new Label(" variableB = 0");
    localPanel19.add(this.labelB);
    this.sliderB = new Scrollbar(0, 0, 15, 0, 142);
    this.sliderB.addAdjustmentListener(this);
    localPanel19.add(this.sliderB);
    this.commentLabB = new Label(" No Comment ");
    localPanel19.add(this.commentLabB);
    this.sliders.add(localPanel19);
    Panel localPanel20 = new Panel(new GridLayout(1, 3));
    this.labelC = new Label(" variableC = 0");
    localPanel20.add(this.labelC);
    this.sliderC = new Scrollbar(0, 0, 15, 0, 142);
    this.sliderC.addAdjustmentListener(this);
    localPanel20.add(this.sliderC);
    this.commentLabC = new Label(" No Comment ");
    localPanel20.add(this.commentLabC);
    this.sliders.add(localPanel20);
    Panel localPanel21 = new Panel(new GridLayout(1, 3));
    this.labelD = new Label(" variableD = 0");
    localPanel21.add(this.labelD);
    this.sliderD = new Scrollbar(0, 0, 15, 0, 142);
    this.sliderD.addAdjustmentListener(this);
    localPanel21.add(this.sliderD);
    this.commentLabD = new Label(" No Comment ");
    localPanel21.add(this.commentLabD);
    this.sliders.add(localPanel21);
    Panel localPanel22 = new Panel(new GridLayout(1, 3));
    this.labelE = new Label(" variableE = 0");
    localPanel22.add(this.labelE);
    this.sliderE = new Scrollbar(0, 0, 15, 0, 142);
    this.sliderE.addAdjustmentListener(this);
    localPanel22.add(this.sliderE);
    this.commentLabE = new Label(" No Comment ");
    localPanel22.add(this.commentLabE);
    this.sliders.add(localPanel22);
    Label localLabel6 = new Label(" ");
    this.sliders.add(localLabel6);
    localPanel10.add(this.sliders, "Center");
    pack();
    setSize(new Dimension(350, 510));
    setVisible(true);
    this.composeBtn.requestFocus();
  }
  
  public void setVariableA(int paramInt)
  {
    setVariableA(paramInt, "No Comment");
  }
  
  public void setVariableA(int paramInt, String paramString)
  {
    if ((paramInt >= 0) && (paramInt <= 127))
    {
      this.sliderA.setValue(paramInt);
      this.labelA.setText(" variableA = " + paramInt + "  ");
      this.variableA = paramInt;
    }
    if (paramString.length() > 18) {
      this.commentLabA.setText(" " + paramString.substring(0, 16) + "...");
    } else {
      this.commentLabA.setText(" " + paramString + " ");
    }
  }
  
  public void setVariableB(int paramInt)
  {
    setVariableB(paramInt, "No Comment");
  }
  
  public void setVariableB(int paramInt, String paramString)
  {
    if ((paramInt >= 0) && (paramInt <= 127))
    {
      this.sliderB.setValue(paramInt);
      this.labelB.setText(" variableB = " + paramInt + "  ");
      this.variableB = paramInt;
    }
    if (paramString.length() > 18) {
      this.commentLabB.setText(" " + paramString.substring(0, 16) + "...");
    } else {
      this.commentLabB.setText(" " + paramString + " ");
    }
  }
  
  public void setVariableC(int paramInt)
  {
    setVariableC(paramInt, "No Comment");
  }
  
  public void setVariableC(int paramInt, String paramString)
  {
    if ((paramInt >= 0) && (paramInt <= 127))
    {
      this.sliderC.setValue(paramInt);
      this.labelC.setText(" variableC = " + paramInt + "  ");
      this.variableC = paramInt;
    }
    if (paramString.length() > 18) {
      this.commentLabC.setText(" " + paramString.substring(0, 16) + "...");
    } else {
      this.commentLabC.setText(" " + paramString + " ");
    }
  }
  
  public void setVariableD(int paramInt)
  {
    setVariableD(paramInt, "No Comment");
  }
  
  public void setVariableD(int paramInt, String paramString)
  {
    if ((paramInt >= 0) && (paramInt <= 127))
    {
      this.sliderD.setValue(paramInt);
      this.labelD.setText(" variableD = " + paramInt + "  ");
      this.variableD = paramInt;
    }
    if (paramString.length() > 18) {
      this.commentLabD.setText(" " + paramString.substring(0, 16) + "...");
    } else {
      this.commentLabD.setText(" " + paramString + " ");
    }
  }
  
  public void setVariableE(int paramInt)
  {
    setVariableE(paramInt, "No Comment");
  }
  
  public void setVariableE(int paramInt, String paramString)
  {
    if ((paramInt >= 0) && (paramInt <= 127))
    {
      this.sliderE.setValue(paramInt);
      this.labelE.setText(" variableE = " + paramInt + "  ");
      this.variableE = paramInt;
    }
    if (paramString.length() > 18) {
      this.commentLabE.setText(" " + paramString.substring(0, 16) + "...");
    } else {
      this.commentLabE.setText(" " + paramString + " ");
    }
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getSource() == this.composeBtn) {
      composeScore();
    }
    if (paramActionEvent.getSource() == this.playBtn) {
      playScore();
    }
    if (paramActionEvent.getSource() == this.stopBtn) {
      stopScore();
    }
    if (paramActionEvent.getSource() == this.showBtn) {
      showScore();
    }
    if (paramActionEvent.getSource() == this.notateBtn) {
      notateScore();
    }
    if (paramActionEvent.getSource() == this.printBtn) {
      printScore();
    }
    if (paramActionEvent.getSource() == this.sketchBtn) {
      sketchScore();
    }
    if (paramActionEvent.getSource() == this.histogramBtn) {
      histogramScore();
    }
    if (paramActionEvent.getSource() == this.saveBtn) {
      saveScore();
    }
    if (paramActionEvent.getSource() == this.readMidiBtn) {
      openMidi();
    }
    if (paramActionEvent.getSource() == this.renderBtn) {
      renderScore();
    }
    if (paramActionEvent.getSource() == this.audioViewBtn) {
      viewAudio();
    }
    if (paramActionEvent.getSource() == this.audioPlayBtn) {
      playAudio();
    }
    if (paramActionEvent.getSource() == this.audioStopBtn) {
      stopAudio();
    }
    if (paramActionEvent.getSource() == this.xmlOpenBtn) {
      xmlOpen();
    }
    if (paramActionEvent.getSource() == this.xmlSaveBtn) {
      xmlSave();
    }
  }
  
  private void composeScore()
  {
    this.score = compose();
    makeBtnsVisible();
  }
  
  private void makeBtnsVisible()
  {
    this.playBtn.setEnabled(true);
    this.stopBtn.setEnabled(true);
    this.showBtn.setEnabled(true);
    this.notateBtn.setEnabled(true);
    this.printBtn.setEnabled(true);
    this.sketchBtn.setEnabled(true);
    this.histogramBtn.setEnabled(true);
    this.saveBtn.setEnabled(true);
    this.xmlSaveBtn.setEnabled(true);
    if (this.insts != null) {
      this.renderBtn.setEnabled(true);
    }
  }
  
  protected Score compose()
  {
    Phrase localPhrase = new Phrase();
    Score localScore = new Score(new Part(localPhrase));
    Note localNote = new Note(48 + (int)(Math.random() * this.variableA), 0.5D + this.variableB * 0.25D);
    localPhrase.addNote(localNote);
    return localScore;
  }
  
  private void playScore()
  {
    if (this.playing) {
      this.ms.stop();
    }
    try
    {
      this.ms.play(this.score);
      this.playing = true;
    }
    catch (Exception localException)
    {
      System.err.println("JavaSound MIDI Playback Error:" + localException);
      return;
    }
  }
  
  private void stopScore()
  {
    if (this.playing)
    {
      this.ms.stop();
      this.playing = false;
    }
  }
  
  private void showScore()
  {
    View.show(this.score, getSize().width + 15, 0);
  }
  
  private void notateScore()
  {
    View.notate(this.score, getSize().width + 15, 0);
  }
  
  private void printScore()
  {
    View.print(this.score);
  }
  
  private void histogramScore()
  {
    View.histogram(this.score, 0, getSize().width + 15, 0);
  }
  
  private void sketchScore()
  {
    View.sketch(this.score, getSize().width + 15, 0);
  }
  
  public void saveScore()
  {
    FileDialog localFileDialog = new FileDialog(this, "Save as a MIDI file...", 1);
    localFileDialog.setFile("FileName.mid");
    localFileDialog.show();
    if (localFileDialog.getFile() != null) {
      Write.midi(this.score, localFileDialog.getDirectory() + localFileDialog.getFile());
    }
  }
  
  public void openMidi()
  {
    FileDialog localFileDialog = new FileDialog(new Frame(), "Select a MIDI file to import...", 0);
    localFileDialog.show();
    String str = localFileDialog.getFile();
    if (str != null)
    {
      Read.midi(this.score, localFileDialog.getDirectory() + str);
      makeBtnsVisible();
    }
  }
  
  private void renderScore()
  {
    FileDialog localFileDialog = new FileDialog(this, "Save as an audio file...", 1);
    localFileDialog.setFile("FileName.au");
    localFileDialog.show();
    if (localFileDialog.getFile() != null)
    {
      this.audioFileName = (localFileDialog.getDirectory() + localFileDialog.getFile());
      Write.au(this.score, this.audioFileName, this.insts);
    }
    this.audioViewBtn.setEnabled(true);
    this.audioPlayBtn.setEnabled(true);
  }
  
  private void viewAudio()
  {
    View.au(this.audioFileName, getSize().width + 5, 0);
  }
  
  private void playAudio()
  {
    Play.au(this.audioFileName, false);
  }
  
  private void stopAudio() {}
  
  public void adjustmentValueChanged(AdjustmentEvent paramAdjustmentEvent)
  {
    if (paramAdjustmentEvent.getSource() == this.sliderA)
    {
      this.labelA.setText(" variableA = " + this.sliderA.getValue());
      this.variableA = new Integer(this.sliderA.getValue()).intValue();
    }
    else if (paramAdjustmentEvent.getSource() == this.sliderB)
    {
      this.labelB.setText(" variableB = " + this.sliderB.getValue());
      this.variableB = new Integer(this.sliderB.getValue()).intValue();
    }
    else if (paramAdjustmentEvent.getSource() == this.sliderC)
    {
      this.labelC.setText(" variableC = " + this.sliderC.getValue());
      this.variableC = new Integer(this.sliderC.getValue()).intValue();
    }
    else if (paramAdjustmentEvent.getSource() == this.sliderD)
    {
      this.labelD.setText(" variableD = " + this.sliderD.getValue());
      this.variableD = new Integer(this.sliderD.getValue()).intValue();
    }
    else if (paramAdjustmentEvent.getSource() == this.sliderE)
    {
      this.labelE.setText(" variableE = " + this.sliderE.getValue());
      this.variableE = new Integer(this.sliderE.getValue()).intValue();
    }
  }
  
  private void xmlSave()
  {
    FileDialog localFileDialog = new FileDialog(new Frame(), "Save as a jMusic XML file...", 1);
    localFileDialog.show();
    if (localFileDialog.getFile() != null) {
      Write.xml(this.score, localFileDialog.getDirectory() + localFileDialog.getFile());
    }
  }
  
  private void xmlOpen()
  {
    Read.xml(this.score);
    makeBtnsVisible();
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\helper\HelperGUI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */