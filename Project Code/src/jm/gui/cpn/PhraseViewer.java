package jm.gui.cpn;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.ScrollPane;
import java.awt.TextArea;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;
import jm.music.data.Note;
import jm.music.data.Phrase;

public class PhraseViewer
  extends Dialog
  implements WindowListener
{
  private ScrollPane scrollPane = new ScrollPane();
  private TextArea textArea = new TextArea(20, 120);
  private Phrase phrase;
  private Stave stave;
  private DecimalFormat decimalFormat = new DecimalFormat("#####.######");
  
  public PhraseViewer(Frame paramFrame)
  {
    super(paramFrame, "Phrase Detail Display", true);
    setSize(500, 400);
    placeControls();
    addWindowListener(this);
    setVisible(false);
    pack();
  }
  
  private void placeControls()
  {
    this.scrollPane.add(this.textArea);
    setLayout(new BorderLayout());
    add("Center", this.scrollPane);
  }
  
  public void showPhrase(Stave paramStave, Phrase paramPhrase, int paramInt1, int paramInt2)
  {
    this.stave = paramStave;
    this.phrase = paramPhrase;
    getPhraseText();
    setLocation(paramInt1, paramInt2);
    show();
  }
  
  private void getPhraseText()
  {
    getStaveText();
    this.textArea.append("Phrase has " + this.phrase.size() + " notes.\n");
    this.textArea.append("Tempo " + this.decimalFormat.format(this.phrase.getTempo()));
    this.textArea.append("    Numerator " + this.phrase.getNumerator());
    this.textArea.append("    Denominator " + this.phrase.getDenominator());
    this.textArea.append("\n");
    for (int i = 0; i < this.phrase.size(); i++) {
      getNoteText(this.phrase.getNote(i));
    }
  }
  
  private void getStaveText()
  {
    this.textArea.append("Stave " + this.stave.getTitle() + "   Metre " + this.decimalFormat.format(this.stave.getMetre()) + "\n");
  }
  
  private void getNoteText(Note paramNote)
  {
    this.textArea.append("Pitch " + paramNote.getPitch());
    this.textArea.append("   Start " + this.decimalFormat.format(paramNote.getSampleStartTime()));
    this.textArea.append("   Rhythm " + this.decimalFormat.format(paramNote.getRhythmValue()));
    this.textArea.append("   Dur " + this.decimalFormat.format(paramNote.getDuration()));
    this.textArea.append("   Offset " + this.decimalFormat.format(paramNote.getOffset()));
    this.textArea.append("   Vol " + paramNote.getDynamic());
    this.textArea.append("\n");
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


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\cpn\PhraseViewer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */