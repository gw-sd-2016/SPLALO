package jm.gui.cpn;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.PrintStream;
import javax.sound.midi.MidiUnavailableException;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Write;

public class LetterNotesEditor
  extends Dialog
  implements ActionListener, WindowListener
{
  private Button okButton;
  private Button playButton;
  private Button cancelButton;
  private Button copyButton;
  private Label inputLabel;
  private TextArea notesInput;
  private Phrase phrase;
  private Stave stave;
  private Note currentNote;
  private char currentNoteLetter = 'A';
  private int currentPitch = 69;
  private static List inputList = new List(8);
  
  public LetterNotesEditor(Frame paramFrame)
  {
    super(paramFrame, "Set Music Parameters", true);
    initializeData();
    initializeButtons();
    initializeLabels();
    setSize(500, 300);
    placeControls();
    addWindowListener(this);
    setVisible(false);
    pack();
  }
  
  private void initializeData()
  {
    this.notesInput = new TextArea("", 10, 100, 0);
  }
  
  private void initializeButtons()
  {
    this.okButton = new Button("Add Notes");
    this.playButton = new Button("Play Notes");
    this.cancelButton = new Button("Cancel");
    this.copyButton = new Button("Copy");
  }
  
  private void initializeLabels()
  {
    this.inputLabel = new Label("Enter Note Names, R for Rest");
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getSource() == this.okButton)
    {
      addNotes();
      dispose();
    }
    else if (paramActionEvent.getSource() == this.playButton)
    {
      playNotes();
    }
    else if (paramActionEvent.getSource() == this.cancelButton)
    {
      dispose();
    }
    else if ((paramActionEvent.getSource() == this.copyButton) && (inputList.getSelectedItem().length() > 0))
    {
      this.notesInput.setText(inputList.getSelectedItem());
    }
  }
  
  private void addNotes()
  {
    initializeUpdate();
    String str = this.notesInput.getText();
    for (int i = 0; i < str.length(); i++) {
      processChar(Character.toUpperCase(str.charAt(i)));
    }
    inputList.add(crunchLine(str), 0);
    while (inputList.getItemCount() > 100) {
      inputList.remove(100);
    }
  }
  
  private void playNotes()
  {
    Phrase localPhrase = this.phrase.copy();
    initializeUpdate();
    String str = this.notesInput.getText();
    int i;
    for (i = 0; i < str.length(); i++) {
      processChar(Character.toUpperCase(str.charAt(i)));
    }
    for (i = 0; i < localPhrase.size(); i++) {
      this.phrase.removeNote(0);
    }
    try
    {
      JmMidiPlayer localJmMidiPlayer = new JmMidiPlayer();
      Score localScore = new Score();
      Part localPart = new Part();
      localScore.addPart(localPart);
      localPart.addPhrase(this.phrase);
      Write.midi(localScore, localJmMidiPlayer);
      localJmMidiPlayer.play();
      localJmMidiPlayer.close();
    }
    catch (MidiUnavailableException localMidiUnavailableException)
    {
      System.out.println("Midi Not Available");
    }
    this.phrase.empty();
    for (int j = 0; j < localPhrase.size(); j++) {
      this.phrase.addNote(localPhrase.getNote(j));
    }
  }
  
  private void initializeUpdate()
  {
    if (this.phrase.size() > 0)
    {
      this.currentNote = this.phrase.getNote(this.phrase.size() - 1);
      if (this.currentNote.getPitch() >= 0)
      {
        this.currentPitch = this.currentNote.getPitch();
        this.currentNoteLetter = getNoteLetter(this.currentPitch, this.stave.getKeySignature());
      }
    }
    else
    {
      this.currentNote = null;
    }
  }
  
  private static char getNoteLetter(int paramInt1, int paramInt2)
  {
    int i = paramInt1 % 12;
    switch (i)
    {
    case 0: 
      return 'C';
    case 2: 
      return 'D';
    case 4: 
      return 'E';
    case 5: 
      return 'F';
    case 7: 
      return 'G';
    case 9: 
      return 'A';
    case 11: 
      return 'B';
    }
    if (paramInt2 >= 0) {
      return getNoteLetter(paramInt1 - 1, paramInt2);
    }
    return getNoteLetter(paramInt1 + 1, paramInt2);
  }
  
  private void processChar(char paramChar)
  {
    switch (paramChar)
    {
    case 'A': 
      addNote(69, paramChar);
      break;
    case 'B': 
      addNote(71, paramChar);
      break;
    case 'C': 
      addNote(60, paramChar);
      break;
    case 'D': 
      addNote(62, paramChar);
      break;
    case 'E': 
      addNote(64, paramChar);
      break;
    case 'F': 
      addNote(65, paramChar);
      break;
    case 'G': 
      addNote(67, paramChar);
      break;
    case 'R': 
      addNote(Integer.MIN_VALUE, paramChar);
      break;
    case '#': 
    case '+': 
      sharpenNote();
      break;
    case '-': 
      flattenNote();
      break;
    case '>': 
      addOctave();
      break;
    case '<': 
      subtractOctave();
      break;
    case '1': 
      moveToInterval(23, 36);
      break;
    case '2': 
      moveToInterval(35, 48);
      break;
    case '3': 
      moveToInterval(47, 60);
      break;
    case '4': 
      moveToInterval(59, 72);
      break;
    case '5': 
      moveToInterval(71, 84);
      break;
    case '6': 
      moveToInterval(83, 96);
      break;
    case '7': 
      moveToInterval(95, 108);
      break;
    case '8': 
      moveToInterval(107, 120);
      break;
    case '9': 
      moveToInterval(119, 127);
      break;
    case '.': 
      dotNote();
      break;
    case 'H': 
      makeHalfNote();
      break;
    case 'W': 
      makeWholeNote();
      break;
    case 'Q': 
      makeQuarterNote();
      break;
    case 'N': 
      makeEighthNote();
      break;
    case 'T': 
      makeTriplet();
      break;
    case 'X': 
      makeSixteenthNote();
      break;
    case '&': 
      tieNotes();
      break;
    }
  }
  
  static boolean pitchIsHigh(int paramInt1, int paramInt2, char paramChar1, char paramChar2)
  {
    String str = "ABCDEFGABCDEFG";
    if (paramInt1 > paramInt2 + 8) {
      return true;
    }
    if (paramInt1 < paramInt2 + 3) {
      return false;
    }
    int i = str.indexOf(paramChar2);
    int j = str.indexOf(paramChar1, i);
    return j > i + 3;
  }
  
  static boolean pitchIsLow(int paramInt1, int paramInt2, char paramChar1, char paramChar2)
  {
    String str = "ABCDEFGABCDEFG";
    if (paramInt1 < paramInt2 - 8) {
      return true;
    }
    if (paramInt1 > paramInt2 - 3) {
      return false;
    }
    int i = str.indexOf(paramChar1);
    int j = str.indexOf(paramChar2, i);
    return j > i + 3;
  }
  
  private void addNote(int paramInt, char paramChar)
  {
    int i = paramInt;
    Note localNote;
    if (this.currentNote != null) {
      localNote = this.currentNote.copy();
    } else {
      localNote = new Note();
    }
    if (paramChar != 'R')
    {
      if (i > this.currentPitch) {
        while (pitchIsHigh(i, this.currentPitch, paramChar, this.currentNoteLetter)) {
          i -= 12;
        }
      }
      while (pitchIsLow(i, this.currentPitch, paramChar, this.currentNoteLetter)) {
        i += 12;
      }
    }
    if (noteIsSharp(paramChar)) {
      i++;
    }
    if (noteIsFlat(paramChar)) {
      i--;
    }
    localNote.setPitch(i);
    this.phrase.add(localNote);
    this.currentNote = localNote;
    this.currentNoteLetter = paramChar;
    if (this.currentNoteLetter != 'R') {
      this.currentPitch = i;
    }
  }
  
  private void sharpenNote()
  {
    this.currentNote.setPitch(this.currentNote.getPitch() + 1);
    this.currentPitch = this.currentNote.getPitch();
  }
  
  private void flattenNote()
  {
    this.currentNote.setPitch(this.currentNote.getPitch() - 1);
    this.currentPitch = this.currentNote.getPitch();
  }
  
  private void addOctave()
  {
    this.currentNote.setPitch(this.currentNote.getPitch() + 12);
    this.currentPitch = this.currentNote.getPitch();
  }
  
  private void subtractOctave()
  {
    this.currentNote.setPitch(this.currentNote.getPitch() - 12);
    this.currentPitch = this.currentNote.getPitch();
  }
  
  private void moveToInterval(int paramInt1, int paramInt2)
  {
    while (this.currentNote.getPitch() > paramInt2) {
      this.currentNote.setPitch(this.currentNote.getPitch() - 12);
    }
    while (this.currentNote.getPitch() < paramInt1) {
      this.currentNote.setPitch(this.currentNote.getPitch() + 12);
    }
    if ((this.currentNote.getPitch() == paramInt1) && (this.currentNoteLetter == 'B')) {
      this.currentNote.setPitch(this.currentNote.getPitch() + 12);
    }
    if ((this.currentNote.getPitch() == paramInt2) && (this.currentNoteLetter == 'C')) {
      this.currentNote.setPitch(this.currentNote.getPitch() - 12);
    }
    this.currentPitch = this.currentNote.getPitch();
  }
  
  private void dotNote()
  {
    adjustNoteByFactor(1.5D);
  }
  
  private void makeHalfNote()
  {
    adjustNoteByFactor(2.0D / this.currentNote.getRhythmValue());
  }
  
  private void makeWholeNote()
  {
    adjustNoteByFactor(4.0D / this.currentNote.getRhythmValue());
  }
  
  private void makeQuarterNote()
  {
    adjustNoteByFactor(1.0D / this.currentNote.getRhythmValue());
  }
  
  private void makeEighthNote()
  {
    adjustNoteByFactor(0.5D / this.currentNote.getRhythmValue());
  }
  
  private void makeTriplet()
  {
    adjustNoteByFactor(0.3333333333333333D / this.currentNote.getRhythmValue());
  }
  
  private void makeSixteenthNote()
  {
    adjustNoteByFactor(0.25D / this.currentNote.getRhythmValue());
  }
  
  private void tieNotes()
  {
    if (this.phrase.size() > 1)
    {
      this.phrase.removeLastNote();
      Note localNote = this.phrase.getNote(this.phrase.size() - 1);
      localNote.setDuration(localNote.getDuration() + this.currentNote.getDuration());
      localNote.setRhythmValue(localNote.getRhythmValue() + this.currentNote.getRhythmValue());
      this.currentNote = localNote;
    }
  }
  
  private boolean noteIsSharp(char paramChar)
  {
    String str = "FCGDAEB";
    int i = this.stave.getKeySignature();
    boolean bool;
    if (paramChar == 'R') {
      bool = false;
    } else if (i > 0) {
      bool = str.indexOf(paramChar) < i;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private boolean noteIsFlat(char paramChar)
  {
    String str = "BEADGCF";
    int i = -this.stave.getKeySignature();
    boolean bool;
    if (paramChar == 'R') {
      bool = false;
    } else if (i > 0) {
      bool = str.indexOf(paramChar) < i;
    } else {
      bool = false;
    }
    return bool;
  }
  
  private void adjustNoteByFactor(double paramDouble)
  {
    this.currentNote.setRhythmValue(paramDouble * this.currentNote.getRhythmValue());
    this.currentNote.setDuration(paramDouble * this.currentNote.getDuration());
  }
  
  private void placeControls()
  {
    GridBagLayout localGridBagLayout = new GridBagLayout();
    GridBagConstraints localGridBagConstraints = new GridBagConstraints();
    setLayout(localGridBagLayout);
    localGridBagConstraints.fill = 1;
    localGridBagConstraints.weightx = 0.5D;
    localGridBagConstraints.gridwidth = 5;
    localGridBagConstraints.gridheight = 1;
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 0;
    localGridBagLayout.setConstraints(this.inputLabel, localGridBagConstraints);
    add(this.inputLabel);
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 1;
    localGridBagConstraints.gridheight = 10;
    localGridBagLayout.setConstraints(this.notesInput, localGridBagConstraints);
    add(this.notesInput);
    localGridBagConstraints.gridx = 2;
    localGridBagConstraints.gridy = 12;
    localGridBagConstraints.gridheight = 1;
    localGridBagConstraints.gridwidth = 1;
    localGridBagLayout.setConstraints(this.okButton, localGridBagConstraints);
    add(this.okButton);
    this.okButton.addActionListener(this);
    localGridBagConstraints.gridx = 3;
    localGridBagConstraints.gridy = 12;
    localGridBagConstraints.gridwidth = 1;
    localGridBagLayout.setConstraints(this.playButton, localGridBagConstraints);
    add(this.playButton);
    this.playButton.addActionListener(this);
    localGridBagConstraints.gridx = 4;
    localGridBagConstraints.gridy = 12;
    localGridBagConstraints.gridwidth = 1;
    localGridBagLayout.setConstraints(this.cancelButton, localGridBagConstraints);
    add(this.cancelButton);
    this.cancelButton.addActionListener(this);
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 15;
    localGridBagConstraints.gridwidth = 5;
    localGridBagConstraints.gridheight = 5;
    localGridBagLayout.setConstraints(inputList, localGridBagConstraints);
    add(inputList);
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 20;
    localGridBagConstraints.gridwidth = 5;
    localGridBagConstraints.gridheight = 1;
    localGridBagLayout.setConstraints(this.copyButton, localGridBagConstraints);
    add(this.copyButton);
    this.copyButton.addActionListener(this);
  }
  
  public void getNotes(Stave paramStave)
  {
    this.phrase = paramStave.getPhrase();
    this.stave = paramStave;
    setLocation(200, 50);
    show();
  }
  
  private static String crunchLine(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramString);
    int i;
    for (i = 0; i < localStringBuffer.length(); i++) {
      if (localStringBuffer.charAt(i) < ' ') {
        localStringBuffer.setCharAt(i, ' ');
      }
    }
    i = 0;
    while (i < localStringBuffer.length() - 1) {
      if (localStringBuffer.charAt(i) == ' ')
      {
        if (localStringBuffer.charAt(i + 1) == ' ') {
          localStringBuffer.deleteCharAt(i);
        } else {
          i += 2;
        }
      }
      else {
        i++;
      }
    }
    return localStringBuffer.toString();
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
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\cpn\LetterNotesEditor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */