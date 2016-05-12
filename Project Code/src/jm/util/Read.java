package jm.util;

import java.awt.Button;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.io.StreamCorruptedException;
import javax.swing.JOptionPane;
import jm.JMC;
import jm.audio.io.AudioFileIn;
import jm.midi.MidiParser;
import jm.midi.SMF;
import jm.music.data.CPhrase;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

public class Read
  implements JMC
{
  protected Read() {}
  
  public static void midi(Score paramScore)
  {
    FileDialog localFileDialog = new FileDialog(new Frame(), "Select a MIDI file to open.", 0);
    localFileDialog.show();
    if (localFileDialog.getFile() != null) {
      midi(paramScore, localFileDialog.getDirectory() + localFileDialog.getFile());
    }
  }
  
  public static void midi(Score paramScore, String paramString)
  {
    if (paramScore == null)
    {
      System.err.println("jMusic Read.midi error: The score is not initialised! I'm doing it for you.");
      paramScore = new Score();
    }
    paramScore.empty();
    SMF localSMF = new SMF();
    localSMF.setVerbose(true);
    try
    {
      System.out.println("--------------------- Reading MIDI File ---------------------");
      FileInputStream localFileInputStream = new FileInputStream(paramString);
      localSMF.read(localFileInputStream);
      MidiParser.SMFToScore(paramScore, localSMF);
      System.out.println("MIDI file '" + paramString + "' read into score '" + paramScore.getTitle() + "' Tempo = " + paramScore.getTempo());
      System.out.println("-------------------------------------------------------------");
    }
    catch (IOException localIOException)
    {
      System.err.println(localIOException);
    }
  }
  
  public static void midi(Part paramPart)
  {
    Score localScore = new Score();
    midi(localScore);
    paramPart = localScore.getPart(0);
  }
  
  public static void midi(Part paramPart, String paramString)
  {
    Score localScore = new Score();
    midi(localScore, paramString);
    paramPart = localScore.getPart(0);
  }
  
  public static void midi(Phrase paramPhrase)
  {
    Score localScore = new Score();
    midi(localScore);
    paramPhrase = localScore.getPart(0).getPhrase(0);
  }
  
  public static void midi(Phrase paramPhrase, String paramString)
  {
    Part localPart = new Part();
    midi(localPart, paramString);
    paramPhrase = localPart.getPhrase(0);
  }
  
  public static void midi(CPhrase paramCPhrase, String paramString)
  {
    Score localScore = new Score();
    midi(localScore, paramString);
    Part localPart = new Part();
    localPart = localScore.getPart(0);
    for (int i = 0; i < localPart.size(); i++) {
      paramCPhrase.addPhrase(localPart.getPhrase(i));
    }
  }
  
  public static void jm(Score paramScore)
  {
    jm(paramScore, paramScore.getTitle() + ".jm");
  }
  
  public static void jm(Score paramScore, String paramString)
  {
    if (paramScore == null)
    {
      System.err.println("jMusic Read.jm error: The score is not initialised! I'm doing it for you.");
      paramScore = new Score();
    }
    paramScore.empty();
    try
    {
      System.out.println("--------------------- Reading .jm File ---------------------");
      FileInputStream localFileInputStream = new FileInputStream(paramString);
      ObjectInputStream localObjectInputStream = new ObjectInputStream(localFileInputStream);
      try
      {
        paramScore.addPartList(((Score)localObjectInputStream.readObject()).getPartArray());
        System.out.println("reading");
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        System.err.println(localClassNotFoundException);
      }
      System.out.println("jm file '" + paramString + "' read into score '" + paramScore.getTitle() + "'");
      System.out.println("-------------------------------------------------------------");
    }
    catch (IOException localIOException)
    {
      System.err.println(localIOException);
    }
  }
  
  public static void jm(Part paramPart, String paramString)
  {
    if (paramPart == null)
    {
      System.err.println("jMusic Read.jm error: The part is not initialised! I'm doing it for you.");
      paramPart = new Part();
    }
    paramPart.empty();
    Score localScore = new Score();
    jm(localScore, paramString);
    paramPart.addPhraseList(localScore.getPart(0).getPhraseArray());
  }
  
  public static void jm(Phrase paramPhrase, String paramString)
  {
    if (paramPhrase == null)
    {
      System.err.println("jMusic Read.jm error: The phrase is not initialised! I'm doing it for you.");
      paramPhrase = new Phrase();
    }
    paramPhrase.empty();
    Part localPart = new Part();
    jm(localPart, paramString);
    paramPhrase.addNoteList(localPart.getPhrase(0).getNoteArray());
  }
  
  public static void jm(CPhrase paramCPhrase, String paramString)
  {
    if (paramCPhrase == null)
    {
      System.err.println("jMusic Read.jm error: The CPhrase is not initialised! I'm doing it for you.");
      paramCPhrase = new CPhrase();
    }
    paramCPhrase.empty();
    Score localScore = new Score();
    jm(localScore, paramString);
    Part localPart = new Part();
    localPart = localScore.getPart(0);
    for (int i = 0; i < localPart.size(); i++) {
      paramCPhrase.addPhrase(localPart.getPhrase(i));
    }
  }
  
  public static void xml(Score paramScore)
  {
    FileDialog localFileDialog = new FileDialog(new Frame(), "Select a jMusic XML file to open.", 0);
    localFileDialog.show();
    if (localFileDialog.getFile() != null) {
      xml(paramScore, localFileDialog.getDirectory() + localFileDialog.getFile());
    }
  }
  
  public static void xml(Score paramScore, String paramString)
  {
    if (paramScore == null)
    {
      System.err.println("jMusic Read.xml error: The score is not initialised! I'm doing it for you.");
      paramScore = new Score();
    }
    paramScore.empty();
    try
    {
      System.out.println("--------------------- Reading .xml File ---------------------");
      BufferedReader localBufferedReader = new BufferedReader(new FileReader(paramString));
      try
      {
        paramScore.addPartList(XMLParser.xmlStringToScore(localBufferedReader.readLine()).getPartArray());
        System.out.println("reading");
      }
      catch (ConversionException localConversionException)
      {
        System.err.println(localConversionException);
      }
      System.out.println("xml file '" + paramString + "' read into score '" + paramScore.getTitle() + "'");
      System.out.println("-------------------------------------------------------------");
    }
    catch (IOException localIOException)
    {
      System.err.println(localIOException);
    }
  }
  
  public static void xml(Part paramPart, String paramString)
  {
    if (paramPart == null)
    {
      System.err.println("jMusic Read.xml error: The part is not initialised! I'm doing it for you.");
      paramPart = new Part();
    }
    paramPart.empty();
    Score localScore = new Score();
    xml(localScore, paramString);
    paramPart.addPhraseList(localScore.getPart(0).getPhraseArray());
  }
  
  public static void xml(Phrase paramPhrase, String paramString)
  {
    if (paramPhrase == null)
    {
      System.err.println("jMusic Read.xml error: The phrase is not initialised! I'm doing it for you.");
      paramPhrase = new Phrase();
    }
    paramPhrase.empty();
    Part localPart = new Part();
    xml(localPart, paramString);
    paramPhrase.addNoteList(localPart.getPhrase(0).getNoteArray());
  }
  
  public static void xml(CPhrase paramCPhrase, String paramString)
  {
    if (paramCPhrase == null)
    {
      System.err.println("jMusic Read.xml error: The CPhrase is not initialised! I'm doing it for you.");
      paramCPhrase = new CPhrase();
    }
    paramCPhrase.empty();
    Score localScore = new Score();
    xml(localScore, paramString);
    Part localPart = new Part();
    localPart = localScore.getPart(0);
    for (int i = 0; i < localPart.size(); i++) {
      paramCPhrase.addPhrase(localPart.getPhrase(i));
    }
  }
  
  public static float[] audio(String paramString)
  {
    System.out.println("-------------------- Reading Audio File ---------------------");
    AudioFileIn localAudioFileIn = new AudioFileIn(paramString);
    float[] arrayOfFloat = localAudioFileIn.getSampleData();
    System.out.println("File '" + paramString + "' read in. Details:");
    System.out.println("Channels = " + localAudioFileIn.getChannels() + " Samples per channel = " + localAudioFileIn.getDuration() / localAudioFileIn.getChannels() + " Sample rate = " + localAudioFileIn.getSampleRate() + " Bit depth = " + localAudioFileIn.getSampleBitDepth());
    System.out.println("-------------------------------------------------------------");
    return arrayOfFloat;
  }
  
  public static void audio(float[] paramArrayOfFloat, String paramString)
  {
    System.out.println("-------------------- Reading Audio File ---------------------");
    AudioFileIn localAudioFileIn = new AudioFileIn(paramString);
    paramArrayOfFloat = localAudioFileIn.getSampleData();
    System.out.println("Audio file '" + paramString + "' read in. Details:");
    System.out.println("Channels = " + localAudioFileIn.getChannels() + " Samples per channel = " + localAudioFileIn.getDuration() / localAudioFileIn.getChannels() + " Sample rate = " + localAudioFileIn.getSampleRate() + " Bit depth = " + localAudioFileIn.getSampleBitDepth());
    System.out.println("-------------------------------------------------------------");
  }
  
  public static Score midiOrJmWithNoMessaging(File paramFile)
  {
    return new JmMidiProcessor(paramFile).getScore();
  }
  
  public static Score midiOrJmWithNoMessaging(String paramString1, String paramString2)
  {
    return new JmMidiProcessor(paramString1, paramString2).getScore();
  }
  
  public static Score midiOrJmWithAWTMessaging(File paramFile, Frame paramFrame)
  {
    JmMidiProcessor localJmMidiProcessor = new JmMidiProcessor(paramFile);
    displayErrorDialog(paramFrame, localJmMidiProcessor.getMessage());
    return localJmMidiProcessor.getScore();
  }
  
  public static Score midiOrJmWithAWTMessaging(String paramString1, String paramString2, Frame paramFrame)
  {
    JmMidiProcessor localJmMidiProcessor = new JmMidiProcessor(paramString1, paramString2);
    displayErrorDialog(paramFrame, localJmMidiProcessor.getMessage());
    return localJmMidiProcessor.getScore();
  }
  
  private static void displayErrorDialog(Frame paramFrame, String paramString)
  {
    if (paramString == null) {
      return;
    }
    Dialog localDialog = new Dialog(paramFrame, "Not a valid MIDI or jMusic File", true);
    completeErrorDialog(localDialog, paramString);
  }
  
  private static void completeErrorDialog(Dialog paramDialog, String paramString)
  {
    paramDialog.add(new Label(paramString), "Center");
    Button localButton = new Button("OK");
    localButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        this.val$dialog.dispose();
      }
    });
    Panel localPanel = new Panel();
    localPanel.add(localButton);
    paramDialog.add(localPanel, "South");
    paramDialog.addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent paramAnonymousWindowEvent)
      {
        this.val$dialog.dispose();
      }
    });
    paramDialog.pack();
    paramDialog.show();
  }
  
  public static Score midiOrJmWithSwingMessaging(File paramFile, Component paramComponent)
  {
    JmMidiProcessor localJmMidiProcessor = new JmMidiProcessor(paramFile);
    displayErrorJDialog(paramComponent, localJmMidiProcessor.getMessage());
    return localJmMidiProcessor.getScore();
  }
  
  public static Score midiOrJmWithSwingMessaging(String paramString1, String paramString2, Component paramComponent)
  {
    JmMidiProcessor localJmMidiProcessor = new JmMidiProcessor(paramString1, paramString2);
    displayErrorJDialog(paramComponent, localJmMidiProcessor.getMessage());
    return localJmMidiProcessor.getScore();
  }
  
  private static void displayErrorJDialog(Component paramComponent, String paramString)
  {
    if (paramString == null) {
      return;
    }
    JOptionPane.showMessageDialog(paramComponent, paramString, "Not a valid MIDI or jMusic File", 0);
  }
  
  protected static class JmMidiProcessor
  {
    private String message = null;
    private Score score = new Score();
    
    public JmMidiProcessor(File paramFile)
    {
      if (paramFile == null)
      {
        this.message = "The selected file is null.  No JM/MIDI information could be imported.";
        this.score = null;
      }
      else if (paramFile.isDirectory())
      {
        this.message = "The selected file is a directory.  No JM/MIDI information could be imported.";
        this.score = null;
      }
      else
      {
        JmMidiProcessor localJmMidiProcessor = new JmMidiProcessor(paramFile.getParent() + File.separator, paramFile.getName());
        this.message = localJmMidiProcessor.getMessage();
        this.score = localJmMidiProcessor.getScore();
      }
    }
    
    public JmMidiProcessor(String paramString1, String paramString2)
    {
      if (paramString2 == null)
      {
        this.message = "The filename String is null.  No JM/MIDI information could be imported.";
        this.score = null;
        return;
      }
      try
      {
        this.score.setTitle(paramString2);
        SMF localSMF = new SMF();
        if (paramString1 == null)
        {
          localFileInputStream = new FileInputStream(paramString2);
          localSMF.read(localFileInputStream);
          MidiParser.SMFToScore(this.score, localSMF);
        }
        else
        {
          localFileInputStream = new FileInputStream(paramString1 + paramString2);
          localSMF.read(localFileInputStream);
          MidiParser.SMFToScore(this.score, localSMF);
        }
      }
      catch (IOException localIOException1)
      {
        FileInputStream localFileInputStream;
        this.message = localIOException1.getMessage();
        if (this.message == null)
        {
          this.message = "Unknown IO Exception";
          this.score = null;
          return;
        }
        if (this.message.equals("Track Started in wrong place!!!!  ABORTING"))
        {
          this.message = "The MIDI file corrupted.  Track data started in the wrong place.";
          this.score = null;
          return;
        }
        if (this.message.equals("This is NOT a MIDI file !!!"))
        {
          try
          {
            localFileInputStream = new FileInputStream(paramString1 + paramString2);
            ObjectInputStream localObjectInputStream = new ObjectInputStream(localFileInputStream);
            this.score = ((Score)localObjectInputStream.readObject());
            localObjectInputStream.close();
            localFileInputStream.close();
          }
          catch (SecurityException localSecurityException)
          {
            this.message = ("Read access not allowed to " + paramString2);
            this.score = null;
            return;
          }
          catch (ClassNotFoundException localClassNotFoundException)
          {
            this.message = ("The file " + paramString2 + " is neither a jm nor a MIDI file");
            this.score = null;
            return;
          }
          catch (ClassCastException localClassCastException)
          {
            this.message = ("The file " + paramString2 + " is neither a jm nor a MIDI file");
            this.score = null;
            return;
          }
          catch (StreamCorruptedException localStreamCorruptedException)
          {
            this.message = ("The file " + paramString2 + " is neither a jm nor a MIDI file");
            this.score = null;
            return;
          }
          catch (IOException localIOException2)
          {
            this.message = localIOException2.getMessage();
            if (this.message == null) {
              this.message = "Unknown Exception.  No musical information could be imported.";
            }
            this.score = null;
            return;
          }
        }
        else
        {
          this.score = null;
          return;
        }
      }
    }
    
    public Score getScore()
    {
      return this.score;
    }
    
    public String getMessage()
    {
      return this.message;
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\util\Read.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */