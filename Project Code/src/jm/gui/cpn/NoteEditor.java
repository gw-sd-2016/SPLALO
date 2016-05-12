package jm.gui.cpn;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;
import java.util.StringTokenizer;
import jm.music.data.Note;

public class NoteEditor
  extends Dialog
  implements ActionListener, WindowListener
{
  private Button okButton = new Button("Apply");
  private Button cancelButton = new Button("Cancel");
  private Note note;
  private List noteList;
  private List octaveList;
  private TextField durationEdit = new TextField(15);
  private TextField dynamicEdit = new TextField(15);
  private TextField rhythmEdit = new TextField(15);
  private TextField panEdit = new TextField(15);
  private TextField offsetEdit = new TextField(15);
  private Label noteLabel = new Label("Note");
  private Label dynamicLabel = new Label("Volume (1-127)");
  private Label rhythmLabel = new Label("Rhythm Value");
  private Label durationLabel = new Label("Duration Factor");
  private Label panLabel = new Label("Pan");
  private Label offsetLabel = new Label("Offset");
  private Label octaveLabel = new Label("Octave");
  private static DecimalFormat decimalFormat = new DecimalFormat("###.###########");
  
  public NoteEditor(Frame paramFrame)
  {
    super(paramFrame, "Edit Note", true);
    initializeLists();
    placeControls();
    this.okButton.addActionListener(this);
    this.cancelButton.addActionListener(this);
    addWindowListener(this);
    setVisible(false);
    pack();
  }
  
  private static String getOctaveStringValue(int paramInt)
  {
    int i = -1;
    for (int j = paramInt; j > 11; j -= 12) {
      i++;
    }
    return new Integer(i).toString();
  }
  
  private static String getPitchStringValue(int paramInt)
  {
    if (paramInt == Integer.MIN_VALUE) {
      return "Rest";
    }
    for (int i = paramInt; i >= 12; i -= 12) {
    switch (i)
    {
    case 0: 
      return "C";
    case 1: 
      return "C#";
    case 2: 
      return "D";
    case 3: 
      return "D#";
    case 4: 
      return "E";
    case 5: 
      return "F";
    case 6: 
      return "F#";
    case 7: 
      return "G";
    case 8: 
      return "G#";
    case 9: 
      return "A";
    case 10: 
      return "A#";
    case 11: 
      return "B";
    	}
    }
    return "Rest";
    
  }
  
  private static void setListToMatch(List paramList, String paramString)
  {
    for (int i = paramList.getItemCount() - 1; i >= 0; i--) {
      if (paramList.getItem(i).equals(paramString)) {
        paramList.select(i);
      }
    }
  }
  
  private void initializeNoteListValue(int paramInt)
  {
    setListToMatch(this.noteList, getPitchStringValue(paramInt));
  }
  
  private void initializeOctaveListValue(int paramInt)
  {
    setListToMatch(this.octaveList, getOctaveStringValue(paramInt));
  }
  
  private static void initializeDoubleEdit(TextField paramTextField, double paramDouble)
  {
    paramTextField.setText(decimalFormat.format(paramDouble));
  }
  
  private static void initializeIntEdit(TextField paramTextField, int paramInt)
  {
    paramTextField.setText(new Integer(paramInt).toString());
  }
  
  private void initializeData()
  {
    initializeNoteListValue(this.note.getPitch());
    initializeOctaveListValue(this.note.getPitch());
    initializeDoubleEdit(this.durationEdit, this.note.getDuration() / this.note.getRhythmValue());
    initializeDoubleEdit(this.rhythmEdit, this.note.getRhythmValue());
    initializeDoubleEdit(this.offsetEdit, this.note.getOffset());
    initializeDoubleEdit(this.panEdit, this.note.getPan());
    initializeIntEdit(this.dynamicEdit, this.note.getDynamic());
  }
  
  public void editNote(Note paramNote, int paramInt1, int paramInt2)
  {
    this.note = paramNote;
    setLocation(paramInt1, paramInt2);
    initializeData();
    show();
  }
  
  private void initializeLists()
  {
    this.noteList = new List(6);
    this.noteList.add("Rest");
    this.noteList.add("A");
    this.noteList.add("A#");
    this.noteList.add("B");
    this.noteList.add("C");
    this.noteList.add("C#");
    this.noteList.add("D");
    this.noteList.add("D#");
    this.noteList.add("E");
    this.noteList.add("F");
    this.noteList.add("F#");
    this.noteList.add("G");
    this.noteList.add("G#");
    this.octaveList = new List(6);
    this.octaveList.add("-1");
    this.octaveList.add("0");
    this.octaveList.add("1");
    this.octaveList.add("2");
    this.octaveList.add("3");
    this.octaveList.add("4");
    this.octaveList.add("5");
    this.octaveList.add("6");
    this.octaveList.add("7");
    this.octaveList.add("8");
    this.octaveList.add("9");
  }
  
  private static boolean validateFloatEdit(TextField paramTextField, double paramDouble1, double paramDouble2)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramTextField.getText());
    if (!localStringTokenizer.hasMoreElements())
    {
      paramTextField.setText("Error--No Data");
      return false;
    }
    String str = localStringTokenizer.nextToken();
    try
    {
      double d = new Double(str).doubleValue();
      if (d < paramDouble1)
      {
        paramTextField.setText("Value Too Low");
        return false;
      }
      if (d < paramDouble1)
      {
        paramTextField.setText("Value Too High");
        return false;
      }
    }
    catch (Throwable localThrowable)
    {
      paramTextField.setText("Data Error");
      return false;
    }
    if (localStringTokenizer.hasMoreElements())
    {
      paramTextField.setText("Data Error");
      return false;
    }
    return true;
  }
  
  private static double getFieldDouble(TextField paramTextField)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramTextField.getText());
    String str = localStringTokenizer.nextToken();
    return new Double(str).doubleValue();
  }
  
  private static boolean validateIntegerEdit(TextField paramTextField, int paramInt1, int paramInt2)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramTextField.getText());
    if (!localStringTokenizer.hasMoreElements())
    {
      paramTextField.setText("Error--No Data");
      return false;
    }
    String str = localStringTokenizer.nextToken();
    try
    {
      int i = new Integer(str).intValue();
      if (i < paramInt1)
      {
        paramTextField.setText("Value Too Low");
        return false;
      }
      if (i > paramInt2)
      {
        paramTextField.setText("Value Too High");
        return false;
      }
    }
    catch (Throwable localThrowable)
    {
      paramTextField.setText("Data Error");
      return false;
    }
    if (localStringTokenizer.hasMoreElements())
    {
      paramTextField.setText("Data Error");
      return false;
    }
    return true;
  }
  
  private static int getFieldInt(TextField paramTextField)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramTextField.getText());
    String str = localStringTokenizer.nextToken();
    return new Integer(str).intValue();
  }
  
  private boolean inputIsValid()
  {
    return (validateFloatEdit(this.durationEdit, 0.0D, 1.0D)) && (validateIntegerEdit(this.dynamicEdit, 0, 127)) && (validateFloatEdit(this.rhythmEdit, 1.0E-5D, 64.0D)) && (validateFloatEdit(this.panEdit, 0.0D, 1.0D)) && (validateFloatEdit(this.offsetEdit, -999.999D, 999.999D));
  }
  
  private void placeControls()
  {
    GridBagLayout localGridBagLayout = new GridBagLayout();
    GridBagConstraints localGridBagConstraints = new GridBagConstraints();
    setLayout(localGridBagLayout);
    localGridBagConstraints.fill = 1;
    localGridBagConstraints.weightx = 0.5D;
    localGridBagConstraints.gridwidth = 2;
    localGridBagConstraints.gridheight = 1;
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.gridheight = 1;
    localGridBagLayout.setConstraints(this.noteLabel, localGridBagConstraints);
    add(this.noteLabel);
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 2;
    localGridBagConstraints.gridheight = 4;
    localGridBagLayout.setConstraints(this.noteList, localGridBagConstraints);
    add(this.noteList);
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 7;
    localGridBagConstraints.gridheight = 1;
    localGridBagLayout.setConstraints(this.octaveLabel, localGridBagConstraints);
    add(this.octaveLabel);
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 8;
    localGridBagConstraints.gridheight = 4;
    localGridBagLayout.setConstraints(this.octaveList, localGridBagConstraints);
    add(this.octaveList);
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 15;
    localGridBagConstraints.gridheight = 1;
    localGridBagConstraints.gridwidth = 1;
    localGridBagLayout.setConstraints(this.rhythmLabel, localGridBagConstraints);
    add(this.rhythmLabel);
    localGridBagConstraints.gridx = 1;
    localGridBagLayout.setConstraints(this.rhythmEdit, localGridBagConstraints);
    add(this.rhythmEdit);
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 17;
    localGridBagConstraints.gridheight = 1;
    localGridBagConstraints.gridwidth = 1;
    localGridBagLayout.setConstraints(this.dynamicLabel, localGridBagConstraints);
    add(this.dynamicLabel);
    localGridBagConstraints.gridx = 1;
    localGridBagLayout.setConstraints(this.dynamicEdit, localGridBagConstraints);
    add(this.dynamicEdit);
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 19;
    localGridBagConstraints.gridheight = 1;
    localGridBagConstraints.gridwidth = 1;
    localGridBagLayout.setConstraints(this.durationLabel, localGridBagConstraints);
    add(this.durationLabel);
    localGridBagConstraints.gridx = 1;
    localGridBagLayout.setConstraints(this.durationEdit, localGridBagConstraints);
    add(this.durationEdit);
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 21;
    localGridBagConstraints.gridheight = 1;
    localGridBagConstraints.gridwidth = 1;
    localGridBagLayout.setConstraints(this.offsetLabel, localGridBagConstraints);
    add(this.offsetLabel);
    localGridBagConstraints.gridx = 1;
    localGridBagLayout.setConstraints(this.offsetEdit, localGridBagConstraints);
    add(this.offsetEdit);
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 23;
    localGridBagConstraints.gridheight = 1;
    localGridBagConstraints.gridwidth = 1;
    localGridBagLayout.setConstraints(this.panLabel, localGridBagConstraints);
    add(this.panLabel);
    localGridBagConstraints.gridx = 1;
    localGridBagLayout.setConstraints(this.panEdit, localGridBagConstraints);
    add(this.panEdit);
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 25;
    localGridBagConstraints.gridheight = 1;
    localGridBagConstraints.gridwidth = 1;
    localGridBagLayout.setConstraints(this.okButton, localGridBagConstraints);
    add(this.okButton);
    localGridBagConstraints.gridx = 1;
    localGridBagLayout.setConstraints(this.cancelButton, localGridBagConstraints);
    add(this.cancelButton);
  }
  
  private int getSelectedPitch()
  {
    String str = this.noteList.getSelectedItem();
    if (str.equals("Rest")) {
      return Integer.MIN_VALUE;
    }
    int i;
    if (str.equals("C")) {
      i = 0;
    } else if (str.equals("C#")) {
      i = 1;
    } else if (str.equals("D")) {
      i = 2;
    } else if (str.equals("D#")) {
      i = 3;
    } else if (str.equals("E")) {
      i = 4;
    } else if (str.equals("F")) {
      i = 5;
    } else if (str.equals("F#")) {
      i = 6;
    } else if (str.equals("G")) {
      i = 7;
    } else if (str.equals("G#")) {
      i = 8;
    } else if (str.equals("A")) {
      i = 9;
    } else if (str.equals("A#")) {
      i = 10;
    } else if (str.equals("A")) {
      i = 11;
    } else {
      i = 0;
    }
    for (int j = new Integer(this.octaveList.getSelectedItem()).intValue(); j > -1; j--) {
      i += 12;
    }
    return i;
  }
  
  private void updateTheNote()
  {
    this.note.setPitch(getSelectedPitch());
    this.note.setRhythmValue(getFieldDouble(this.rhythmEdit));
    this.note.setDuration(this.note.getRhythmValue() * getFieldDouble(this.durationEdit));
    this.note.setDynamic(getFieldInt(this.dynamicEdit));
    this.note.setPan(getFieldDouble(this.panEdit));
    this.note.setOffset(getFieldDouble(this.offsetEdit));
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getSource() == this.okButton)
    {
      if (inputIsValid())
      {
        updateTheNote();
        dispose();
      }
    }
    else if (paramActionEvent.getSource() == this.cancelButton) {
      dispose();
    }
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


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\cpn\NoteEditor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */