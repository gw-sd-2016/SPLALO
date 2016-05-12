package jm.gui.cpn;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.StringTokenizer;
import jm.music.data.Note;
import jm.music.data.Phrase;

public class CpnZoomScreen
  extends Dialog
  implements ActionListener, WindowListener
{
  private Phrase phrase;
  private Phrase beforeZoom;
  private Phrase afterZoom;
  private static TextField startMeasureEdit = new TextField(8);
  private static TextField measureCountEdit = new TextField(8);
  private static Label startMeasureLabel = new Label("Start at Measure");
  private static Label measureCountLabel = new Label("Number of Measures");
  private Button okButton = new Button("Update View:");
  private Button cancelButton = new Button("Cancel");
  
  public CpnZoomScreen(Frame paramFrame)
  {
    super(paramFrame, "Select the Measures to Show", true);
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
  
  public void zoomIn(Phrase paramPhrase1, Phrase paramPhrase2, Phrase paramPhrase3)
  {
    this.phrase = paramPhrase2;
    this.beforeZoom = paramPhrase1;
    this.afterZoom = paramPhrase3;
    this.beforeZoom.empty();
    this.afterZoom.empty();
    setLocation(20, 20);
    show();
  }
  
  public static void zoomOut(Phrase paramPhrase1, Phrase paramPhrase2, Phrase paramPhrase3)
  {
	  int i;
    for (i = 0; i < paramPhrase2.size(); i++) {
      paramPhrase1.addNote(paramPhrase2.getNote(i));
    }
    for (i = 0; i < paramPhrase3.size(); i++) {
      paramPhrase1.addNote(paramPhrase3.getNote(i));
    }
    paramPhrase2.empty();
    for (i = 0; i < paramPhrase1.size(); i++) {
      paramPhrase2.addNote(paramPhrase1.getNote(i));
    }
    paramPhrase1.empty();
    paramPhrase3.empty();
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
    localGridBagConstraints.gridheight = 1;
    localGridBagLayout.setConstraints(startMeasureLabel, localGridBagConstraints);
    add(startMeasureLabel);
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 0;
    localGridBagConstraints.gridheight = 1;
    localGridBagLayout.setConstraints(startMeasureEdit, localGridBagConstraints);
    add(startMeasureEdit);
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 1;
    localGridBagConstraints.gridheight = 1;
    localGridBagLayout.setConstraints(measureCountLabel, localGridBagConstraints);
    add(measureCountLabel);
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 1;
    localGridBagConstraints.gridheight = 1;
    localGridBagLayout.setConstraints(measureCountEdit, localGridBagConstraints);
    add(measureCountEdit);
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 3;
    localGridBagConstraints.gridheight = 1;
    localGridBagLayout.setConstraints(this.okButton, localGridBagConstraints);
    add(this.okButton);
    this.okButton.addActionListener(this);
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 3;
    localGridBagConstraints.gridheight = 1;
    add(this.cancelButton);
    localGridBagLayout.setConstraints(this.cancelButton, localGridBagConstraints);
    this.cancelButton.addActionListener(this);
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getSource() == this.okButton) {
      if (startFieldError())
      {
        startMeasureEdit.setText("Error");
      }
      else if (countFieldError())
      {
        measureCountEdit.setText("Error");
      }
      else
      {
        zoom();
        dispose();
      }
    }
    if (paramActionEvent.getSource() == this.cancelButton) {
      dispose();
    }
  }
  
  private double countMeasures(Phrase paramPhrase)
  {
    double d = 0.0D;
    for (int i = 0; i < paramPhrase.size(); i++) {
      d += paramPhrase.getNote(i).getRhythmValue();
    }
    return d / paramPhrase.getNumerator();
  }
  
  private static boolean intFieldError(TextField paramTextField, double paramDouble1, double paramDouble2)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramTextField.getText());
    if (!localStringTokenizer.hasMoreElements())
    {
      paramTextField.setText("Error");
      return true;
    }
    String str = localStringTokenizer.nextToken();
    try
    {
      int i = new Integer(str).intValue();
      if (i < paramDouble1)
      {
        paramTextField.setText("Error");
        return true;
      }
      if (i > paramDouble2)
      {
        paramTextField.setText("Error");
        return true;
      }
    }
    catch (Throwable localThrowable)
    {
      paramTextField.setText("Error");
      return true;
    }
    if (localStringTokenizer.hasMoreElements())
    {
      paramTextField.setText("Error");
      return true;
    }
    return false;
  }
  
  private static int getIntegerValue(TextField paramTextField)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramTextField.getText());
    String str = localStringTokenizer.nextToken();
    return new Integer(str).intValue();
  }
  
  private boolean startFieldError()
  {
    return intFieldError(startMeasureEdit, 1.0D, countMeasures(this.phrase) + 0.99D);
  }
  
  private boolean countFieldError()
  {
    return intFieldError(measureCountEdit, 1.0D, 99999.99D);
  }
  
  private void moveMeasures(Phrase paramPhrase1, Phrase paramPhrase2, double paramDouble)
  {
    double d2;
    for (double d1 = paramDouble * paramPhrase1.getNumerator(); (d1 > 0.005D) && (paramPhrase1.size() > 0); d1 -= d2)
    {
      Note localNote = paramPhrase1.getNote(0);
      d2 = localNote.getRhythmValue();
      paramPhrase2.addNote(localNote);
      paramPhrase1.removeNote(0);
    }
  }
  
  private void moveAll(Phrase paramPhrase1, Phrase paramPhrase2)
  {
    while (paramPhrase1.size() > 0)
    {
      Note localNote = paramPhrase1.getNote(0);
      paramPhrase2.addNote(localNote);
      paramPhrase1.removeNote(0);
    }
  }
  
  private void zoom()
  {
    int i = getIntegerValue(startMeasureEdit) - 1;
    int j = getIntegerValue(measureCountEdit);
    this.beforeZoom.empty();
    this.afterZoom.empty();
    moveMeasures(this.phrase, this.beforeZoom, i);
    moveAll(this.phrase, this.afterZoom);
    moveMeasures(this.afterZoom, this.phrase, i + j - countMeasures(this.beforeZoom));
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\cpn\CpnZoomScreen.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */