package jm.util;

import java.awt.Button;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import jm.music.data.Score;

public class ReadFileButton
  extends Button
{
  private ReadListenerLinkedList readListenerList;
  
  public ReadFileButton(final Frame paramFrame)
  {
    super("Read File");
    final FileDialog localFileDialog = new FileDialog(paramFrame, "Select a Midi or jMusic file to import", 0);
    localFileDialog.setFilenameFilter(new ReadFilenameFilter());
    addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        localFileDialog.show();
        Score localScore = Read.midiOrJmWithAWTMessaging(localFileDialog.getDirectory(), localFileDialog.getFile(), paramFrame);
        if (localScore == null) {
          return;
        }
        if (ReadFileButton.this.readListenerList != null)
        {
          localScore = ReadFileButton.this.readListenerList.scoreRead(localScore);
          ReadFileButton.this.readListenerList.finishedReading();
        }
      }
    });
  }
  
  public void addReadListener(ReadListener paramReadListener)
  {
    if (paramReadListener == null) {
      return;
    }
    if (this.readListenerList == null) {
      this.readListenerList = new ReadListenerLinkedList(paramReadListener);
    } else {
      this.readListenerList.add(paramReadListener);
    }
  }
  
  public void removeReadListener(ReadListener paramReadListener)
  {
    if (this.readListenerList == null) {
      return;
    }
    if (this.readListenerList.getListener() == paramReadListener) {
      this.readListenerList = this.readListenerList.getNext();
    }
  }
}