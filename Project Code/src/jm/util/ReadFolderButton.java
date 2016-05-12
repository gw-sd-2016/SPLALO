package jm.util;

import java.awt.Button;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import jm.music.data.Score;

public class ReadFolderButton
  extends Button
{
  private ReadListenerLinkedList readListenerList;
  
  public ReadFolderButton(final Frame paramFrame)
  {
    super("Read Folder");
    final FileDialog localFileDialog = new FileDialog(paramFrame, "Select a file to read all Midi and JMusic within that file's folder", 0);
    final ReadFilenameFilter localReadFilenameFilter = new ReadFilenameFilter();
    localFileDialog.setFilenameFilter(localReadFilenameFilter);
    addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        localFileDialog.show();
        String str = localFileDialog.getDirectory();
        if (str == null) {
          return;
        }
        String[] arrayOfString = new File(str).list(localReadFilenameFilter);
        for (int i = 0; i < arrayOfString.length; i++)
        {
          Score localScore = Read.midiOrJmWithAWTMessaging(str, arrayOfString[i], paramFrame);
          if ((localScore != null) && (ReadFolderButton.this.readListenerList != null)) {
            ReadFolderButton.this.readListenerList.scoreRead(localScore);
          }
        }
        if (ReadFolderButton.this.readListenerList != null) {
          ReadFolderButton.this.readListenerList.finishedReading();
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
