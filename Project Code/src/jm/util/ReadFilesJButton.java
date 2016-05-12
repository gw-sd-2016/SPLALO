package jm.util;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import jm.music.data.Score;

public class ReadFilesJButton
  extends JButton
{
  public static final Mode SINGLE_FILE_MODE = new Mode("Single File", null);
  public static final Mode MULTIPLE_FILES_MODE = new Mode("Multiple Files", null);
  public static final Mode FOLDER_MODE = new Mode("Folder", null);
  private Mode mode;
  private ReadListenerLinkedList readListenerList;
  private JFileChooser chooser = new JFileChooser();
  private Component owner;
  
  public ReadFilesJButton(Component paramComponent)
  {
    this(paramComponent, MULTIPLE_FILES_MODE);
  }
  
  public ReadFilesJButton(final Component paramComponent, final Mode paramMode)
  {
    this.owner = paramComponent;
    setMode(paramMode);
    addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        Runnable local1 = new Runnable()
        {
          public void run()
          {
            ReadFilesJButton.this.readListenerList.startedReading();
            int i = ReadFilesJButton.this.chooser.showOpenDialog(ReadFilesJButton.1.this.val$owner);
            if (i != 0) {
              return;
            }
            if (ReadFilesJButton.1.this.val$mode == ReadFilesJButton.SINGLE_FILE_MODE)
            {
              ReadFilesJButton.this.processFile(ReadFilesJButton.this.chooser.getSelectedFile());
            }
            else if (ReadFilesJButton.1.this.val$mode == ReadFilesJButton.MULTIPLE_FILES_MODE)
            {
              ReadFilesJButton.this.processFiles(ReadFilesJButton.this.chooser.getSelectedFiles());
            }
            else if (ReadFilesJButton.1.this.val$mode == ReadFilesJButton.FOLDER_MODE)
            {
              File localFile = ReadFilesJButton.this.chooser.getSelectedFile();
              if (localFile.isDirectory())
              {
                String[] arrayOfString = localFile.list(new ReadFilenameFilter());
                for (int j = 0; j < arrayOfString.length; j++) {
                  ReadFilesJButton.this.processFile(new File(localFile.getAbsolutePath(), arrayOfString[j]));
                }
              }
            }
            if (ReadFilesJButton.this.readListenerList != null) {
              ReadFilesJButton.this.readListenerList.finishedReading();
            }
          }
        };
        Thread localThread = new Thread(local1, "processThread");
        localThread.start();
      }
    });
  }
  
  private void processFile(File paramFile)
  {
    Score localScore = Read.midiOrJmWithSwingMessaging(paramFile, this.owner);
    if (localScore == null) {
      return;
    }
    if (this.readListenerList != null) {
      localScore = this.readListenerList.scoreRead(localScore);
    }
  }
  
  private void processFiles(File[] paramArrayOfFile)
  {
    if (paramArrayOfFile == null) {
      return;
    }
    for (int i = 0; i < paramArrayOfFile.length; i++) {
      processFile(paramArrayOfFile[i]);
    }
  }
  
  public void setMode(Mode paramMode)
  {
    this.mode = paramMode;
    if (paramMode == SINGLE_FILE_MODE)
    {
      setText("Read File");
      this.chooser.setDialogTitle("Select a MIDI or jMusic file to import");
      this.chooser.setMultiSelectionEnabled(false);
      this.chooser.setFileSelectionMode(0);
    }
    else if (paramMode == MULTIPLE_FILES_MODE)
    {
      setText("Read Files");
      this.chooser.setDialogTitle("Select MIDI and/or jMusic files to import");
      this.chooser.setMultiSelectionEnabled(true);
      this.chooser.setFileSelectionMode(0);
    }
    else if (paramMode == FOLDER_MODE)
    {
      setText("Read Folder");
      this.chooser.setDialogTitle("Select a folder of MIDI or jMusic files to import");
      this.chooser.setMultiSelectionEnabled(false);
      this.chooser.setFileSelectionMode(1);
    }
  }
  
  public Mode getMode()
  {
    return this.mode;
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
  
  private static class Mode
  {
    private final String name;
    
    private Mode(String paramString)
    {
      this.name = paramString;
    }
  }
}