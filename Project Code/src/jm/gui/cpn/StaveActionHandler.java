package jm.gui.cpn;

import java.awt.Cursor;
import java.awt.Frame;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Phrase;

public class StaveActionHandler
  implements JMC, MouseListener, MouseMotionListener, ActionListener, KeyListener
{
  private Stave theApp;
  private int selectedNote = -1;
  private boolean topTimeSelected = false;
  private boolean keySelected = false;
  private int clickedPosY;
  private int clickedPosX;
  private int storedPitch = 72;
  private double[] rhythmValues = { 104.0D, 103.0D, 102.0D, 101.5D, 101.0D, 100.75D, 100.5D, 100.25D, 0.0D, 0.25D, 0.5D, 0.75D, 1.0D, 1.5D, 2.0D, 3.0D, 4.0D };
  private boolean button1Down = false;
  private PopupMenu noteContextMenu;
  private MenuItem editNote;
  private MenuItem repeatNote;
  private MenuItem makeRest;
  private MenuItem deleteNote;
  
  StaveActionHandler(Stave paramStave)
  {
    this.theApp = paramStave;
    this.noteContextMenu = new PopupMenu();
    this.editNote = new MenuItem("Edit Note");
    this.editNote.addActionListener(this);
    this.noteContextMenu.add(this.editNote);
    this.repeatNote = new MenuItem("Repeat Note");
    this.repeatNote.addActionListener(this);
    this.noteContextMenu.add(this.repeatNote);
    this.makeRest = new MenuItem("Change to Rest");
    this.makeRest.addActionListener(this);
    this.noteContextMenu.add(this.makeRest);
    this.deleteNote = new MenuItem("Delete Note");
    this.deleteNote.addActionListener(this);
    this.noteContextMenu.add(this.deleteNote);
    this.theApp.add(this.noteContextMenu);
  }
  
  boolean inNoteArea(MouseEvent paramMouseEvent)
  {
    Integer localInteger;
    if (this.theApp.notePositions.size() < 2) {
      localInteger = new Integer(this.theApp.getTotalBeatWidth());
    } else {
      localInteger = (Integer)this.theApp.notePositions.elementAt(this.theApp.notePositions.size() - 2);
    }
    return (paramMouseEvent.getX() <= localInteger.intValue() + 15) && (paramMouseEvent.getX() < this.theApp.getTotalBeatWidth() + 50);
  }
  
  private void searchForSelectedNote(MouseEvent paramMouseEvent)
  {
    for (int i = 0; i < this.theApp.notePositions.size(); i += 2)
    {
      Integer localInteger1 = (Integer)this.theApp.notePositions.elementAt(i);
      Integer localInteger2 = (Integer)this.theApp.notePositions.elementAt(i + 1);
      if ((paramMouseEvent.getX() > localInteger1.intValue()) && (paramMouseEvent.getX() < localInteger1.intValue() + 15) && (paramMouseEvent.getY() + this.theApp.staveDelta > localInteger2.intValue() + 22) && (paramMouseEvent.getY() + this.theApp.staveDelta < localInteger2.intValue() + 35))
      {
        this.selectedNote = (i / 2);
        this.clickedPosY = (paramMouseEvent.getY() + this.theApp.staveDelta);
        this.clickedPosX = paramMouseEvent.getX();
        i = this.theApp.notePositions.size();
      }
    }
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent)
  {
    if (((paramMouseEvent.getModifiers() & 0x8) != 0) && (inNoteArea(paramMouseEvent)))
    {
      searchForSelectedNote(paramMouseEvent);
      if ((this.selectedNote >= 0) && (this.selectedNote < this.theApp.getPhrase().size())) {
        this.noteContextMenu.show(this.theApp, paramMouseEvent.getX(), paramMouseEvent.getY());
      }
    }
  }
  
  public void mouseEntered(MouseEvent paramMouseEvent) {}
  
  public void mouseExited(MouseEvent paramMouseEvent) {}
  
  public void mouseMoved(MouseEvent paramMouseEvent) {}
  
  public void mousePressed(MouseEvent paramMouseEvent)
  {
    if ((paramMouseEvent.isPopupTrigger()) || ((paramMouseEvent.getModifiers() & 0x8) != 0) || (!this.theApp.editable)) {
      return;
    }
    this.button1Down = true;
    Object localObject1;
    Object localObject2;
    if (!inNoteArea(paramMouseEvent))
    {
      i = 85 - (paramMouseEvent.getY() + this.theApp.staveDelta - this.theApp.bPos) / 2;
      localObject1 = new int[] { 1, 3, 6, 8, 10 };
      int k = 1;
      for (int n = 0; n < localObject1.length; n++) {
        if (i % 12 == localObject1[n]) {
          i--;
        }
      }
      Note localNote1 = new Note(i, 1.0D);
      localObject2 = this.theApp.getPhrase();
      ((Phrase)localObject2).addNote(localNote1);
      this.theApp.repaint();
      this.theApp.setCursor(new Cursor(12));
      this.selectedNote = (((Phrase)localObject2).size() - 1);
      this.clickedPosY = (paramMouseEvent.getY() + this.theApp.staveDelta);
      this.clickedPosX = paramMouseEvent.getX();
    }
    else
    {
      searchForSelectedNote(paramMouseEvent);
      this.theApp.setCursor(new Cursor(13));
    }
    if (this.selectedNote < 0) {
      for (i = 0; i < this.theApp.notePositions.size() - 2; i += 2)
      {
        localObject1 = (Integer)this.theApp.notePositions.elementAt(i);
        Integer localInteger = (Integer)this.theApp.notePositions.elementAt(i + 2);
        if ((paramMouseEvent.getX() > ((Integer)localObject1).intValue() + 15) && (paramMouseEvent.getX() < localInteger.intValue()))
        {
          this.theApp.setCursor(new Cursor(12));
          int i1 = 85 - (paramMouseEvent.getY() + this.theApp.staveDelta - this.theApp.bPos) / 2;
          localObject2 = new int[] { 1, 3, 6, 8, 10 };
          int i2 = 1;
          for (int i3 = 0; i3 < localObject2.length; i3++) {
            if (i1 % 12 == localObject2[i3]) {
              i1--;
            }
          }
          Note localNote2 = new Note(i1, 1.0D);
          Phrase localPhrase = this.theApp.getPhrase();
          localPhrase.getNoteList().insertElementAt(localNote2, i / 2 + 1);
          this.theApp.repaint();
          this.selectedNote = (i / 2 + 1);
          this.clickedPosY = (paramMouseEvent.getY() + this.theApp.staveDelta);
          this.clickedPosX = paramMouseEvent.getX();
          i = this.theApp.notePositions.size();
        }
      }
    }
    int i = this.theApp.rightMargin + this.theApp.clefWidth + this.theApp.keySigWidth;
    if ((paramMouseEvent.getX() > i) && (paramMouseEvent.getX() < i + 10))
    {
      this.theApp.setCursor(new Cursor(13));
      this.topTimeSelected = true;
      this.clickedPosY = (paramMouseEvent.getY() + this.theApp.staveDelta);
      this.clickedPosX = paramMouseEvent.getX();
    }
    int j = this.theApp.rightMargin + this.theApp.clefWidth;
    int m = 10;
    if (this.theApp.keySigWidth > m) {
      m = this.theApp.keySigWidth;
    }
    if ((paramMouseEvent.getX() > j - 10) && (paramMouseEvent.getX() < j + m))
    {
      this.theApp.setCursor(new Cursor(13));
      this.keySelected = true;
      this.clickedPosY = (paramMouseEvent.getY() + this.theApp.staveDelta);
      this.clickedPosX = paramMouseEvent.getX();
    }
  }
  
  public void mouseDragged(MouseEvent paramMouseEvent)
  {
    if ((!this.button1Down) || (!this.theApp.editable)) {
      return;
    }
    if (this.selectedNote >= 0)
    {
      Phrase localPhrase = this.theApp.getPhrase();
      Note localNote = localPhrase.getNote(this.selectedNote);
      if ((paramMouseEvent.getY() + this.theApp.staveDelta > this.clickedPosY + 2) && (this.theApp.getPhrase().getNote(this.selectedNote).getPitch() != Integer.MIN_VALUE))
      {
        localNote.setPitch(localNote.getPitch() - 1);
        if (localNote.getPitch() < this.theApp.getMinPitch()) {
          localNote.setPitch(this.theApp.getMinPitch());
        }
        this.clickedPosY += 2;
        this.theApp.repaint();
        this.storedPitch = localNote.getPitch();
      }
      if ((paramMouseEvent.getY() + this.theApp.staveDelta < this.clickedPosY - 2) && (this.theApp.getPhrase().getNote(this.selectedNote).getPitch() != Integer.MIN_VALUE))
      {
        localNote.setPitch(localNote.getPitch() + 1);
        if (localNote.getPitch() > this.theApp.getMaxPitch()) {
          localNote.setPitch(this.theApp.getMaxPitch());
        }
        this.clickedPosY -= 2;
        this.theApp.repaint();
        this.storedPitch = localNote.getPitch();
      }
      double d;
      int i;
      int j;
      int k;
      if (paramMouseEvent.getX() > this.clickedPosX + 6)
      {
        d = localNote.getRhythmValue();
        i = localNote.getPitch();
        if (i == Integer.MIN_VALUE) {
          d += 100.0D;
        }
        j = this.rhythmValues.length;
        for (k = 0; k < this.rhythmValues.length - 1; k++) {
          if (d == this.rhythmValues[k])
          {
            localNote.setRhythmValue(this.rhythmValues[(k + 1)]);
            localNote.setDuration(localNote.getRhythmValue() * 0.9D);
          }
        }
        this.clickedPosX = paramMouseEvent.getX();
        if (localNote.getRhythmValue() > 100.0D)
        {
          localNote.setPitch(Integer.MIN_VALUE);
          localNote.setRhythmValue(localNote.getRhythmValue() - 100.0D);
          localNote.setDuration(localNote.getRhythmValue() * 0.9D);
        }
        else if (i == Integer.MIN_VALUE)
        {
          localNote.setPitch(this.storedPitch);
        }
        this.theApp.repaint();
      }
      if (paramMouseEvent.getX() < this.clickedPosX - 6)
      {
        d = localNote.getRhythmValue();
        i = localNote.getPitch();
        if (i == Integer.MIN_VALUE) {
          d += 100.0D;
        }
        j = 0;
        for (k = 0; k < this.rhythmValues.length; k++) {
          if (d == this.rhythmValues[k]) {
            j = k;
          }
        }
        if (j > 0)
        {
          localNote.setRhythmValue(this.rhythmValues[(j - 1)]);
          localNote.setDuration(localNote.getRhythmValue() * 0.9D);
          this.clickedPosX = paramMouseEvent.getX();
          if (localNote.getRhythmValue() > 100.0D)
          {
            localNote.setPitch(Integer.MIN_VALUE);
            localNote.setRhythmValue(localNote.getRhythmValue() - 100.0D);
            localNote.setDuration(localNote.getRhythmValue() * 0.9D);
          }
          else if (i == Integer.MIN_VALUE)
          {
            localNote.setPitch(this.storedPitch);
          }
          this.theApp.repaint();
        }
      }
    }
    if (this.topTimeSelected)
    {
      if (paramMouseEvent.getY() + this.theApp.staveDelta < this.clickedPosY - 4)
      {
        this.theApp.setMetre(this.theApp.getMetre() + 1.0D);
        if (this.theApp.getMetre() > 9.0D) {
          this.theApp.setMetre(9.0D);
        }
        if (this.theApp.getMetre() < 1.0D) {
          this.theApp.setMetre(1.0D);
        }
        this.theApp.getPhrase().setNumerator(new Double(Math.round(this.theApp.getMetre())).intValue());
        this.clickedPosY -= 4;
        this.theApp.repaint();
        this.theApp.updateChange();
      }
      if (paramMouseEvent.getY() + this.theApp.staveDelta > this.clickedPosY + 4)
      {
        this.theApp.setMetre(this.theApp.getMetre() - 1.0D);
        if (this.theApp.getMetre() < 1.0D) {
          this.theApp.setMetre(1.0D);
        }
        if (this.theApp.getMetre() > 9.0D) {
          this.theApp.setMetre(9.0D);
        }
        this.theApp.getPhrase().setNumerator(new Double(Math.round(this.theApp.getMetre())).intValue());
        this.clickedPosY += 4;
        this.theApp.repaint();
        this.theApp.updateChange();
      }
    }
    if (this.keySelected)
    {
      if (paramMouseEvent.getY() + this.theApp.staveDelta < this.clickedPosY - 4)
      {
        this.theApp.setKeySignature(this.theApp.getKeySignature() + 1);
        if (this.theApp.getKeySignature() > 7) {
          this.theApp.setKeySignature(7);
        }
        this.clickedPosY -= 4;
        this.theApp.repaint();
        this.theApp.updateChange();
      }
      if (paramMouseEvent.getY() + this.theApp.staveDelta > this.clickedPosY + 4)
      {
        this.theApp.setKeySignature(this.theApp.getKeySignature() - 1);
        if (this.theApp.getKeySignature() < -7) {
          this.theApp.setKeySignature(-7);
        }
        this.clickedPosY += 4;
        this.theApp.repaint();
        this.theApp.updateChange();
      }
    }
  }
  
  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    this.button1Down = false;
    if (!this.theApp.editable) {
      return;
    }
    for (int i = 0; i < this.theApp.getPhrase().getNoteList().size(); i++) {
      if (this.theApp.getPhrase().getNote(i).getRhythmValue() == 0.0D) {
        this.theApp.getPhrase().getNoteList().removeElementAt(i);
      }
    }
    this.theApp.repaint();
    this.theApp.updateChange();
    this.selectedNote = -1;
    this.topTimeSelected = false;
    this.keySelected = false;
    this.theApp.setCursor(new Cursor(0));
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    Phrase localPhrase = this.theApp.getPhrase();
    Note localNote = localPhrase.getNote(this.selectedNote);
    Object localObject;
    if (paramActionEvent.getSource() == this.editNote)
    {
      localObject = new Frame("Edit this Note");
      ((Frame)localObject).setSize(400, 400);
      ((Frame)localObject).setResizable(true);
      NoteEditor localNoteEditor = new NoteEditor((Frame)localObject);
      localNoteEditor.editNote(localNote, 20, 20);
    }
    else if (paramActionEvent.getSource() == this.repeatNote)
    {
      localObject = localNote.copy();
      localPhrase.getNoteList().insertElementAt(localObject, this.selectedNote);
    }
    else if (paramActionEvent.getSource() == this.makeRest)
    {
      localNote.setFrequency(-2.147483648E9D);
    }
    else if (paramActionEvent.getSource() == this.deleteNote)
    {
      localPhrase.getNoteList().removeElementAt(this.selectedNote);
    }
    this.selectedNote = -1;
    this.theApp.repaint();
  }
  
  public void keyPressed(KeyEvent paramKeyEvent) {}
  
  public void keyReleased(KeyEvent paramKeyEvent) {}
  
  public void keyTyped(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getKeyChar() == '\b') {
      this.theApp.deleteLastNote();
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\gui\cpn\StaveActionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */