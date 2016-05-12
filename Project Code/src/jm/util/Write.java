package jm.util;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Vector;
import jm.JMC;
import jm.audio.Audio;
import jm.audio.Instrument;
import jm.audio.io.AudioFileOut;
import jm.midi.MidiParser;
import jm.midi.SMF;
import jm.music.data.CPhrase;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

public class Write
  implements JMC
{
  public Write() {}
  
  public static void midi(Score paramScore)
  {
    FileDialog localFileDialog = new FileDialog(new Frame(), "Save as a MIDI file ...", 1);
    localFileDialog.setFile("jMusic_composition.mid");
    localFileDialog.show();
    if (localFileDialog.getFile() != null) {
      midi(paramScore, localFileDialog.getDirectory() + localFileDialog.getFile());
    }
  }
  
  public static void midi(Score paramScore, OutputStream paramOutputStream)
  {
    SMF localSMF = new SMF();
    try
    {
      localSMF.clearTracks();
      MidiParser.scoreToSMF(paramScore, localSMF);
      localSMF.write(paramOutputStream);
    }
    catch (IOException localIOException)
    {
      System.err.println(localIOException);
    }
  }
  
  public static void midi(Score paramScore, String paramString)
  {
    SMF localSMF = new SMF();
    try
    {
      double d1 = System.currentTimeMillis();
      System.out.println("----------------------------- Writing MIDI File ------------------------------");
      localSMF.clearTracks();
      MidiParser.scoreToSMF(paramScore, localSMF);
      FileOutputStream localFileOutputStream = new FileOutputStream(paramString);
      localSMF.write(localFileOutputStream);
      double d2 = System.currentTimeMillis();
      System.out.println("MIDI file '" + paramString + "' written from score '" + paramScore.getTitle() + "' in " + (d2 - d1) / 1000.0D + " seconds.");
      System.out.println("------------------------------------------------------------------------------");
    }
    catch (IOException localIOException)
    {
      System.err.println(localIOException);
    }
  }
  
  public static void midi(Part paramPart)
  {
    midi(new Score(paramPart));
  }
  
  public static void midi(Part paramPart, String paramString)
  {
    Score localScore = new Score("Score of " + paramPart.getTitle());
    localScore.addPart(paramPart);
    midi(localScore, paramString);
  }
  
  public static void midi(Phrase paramPhrase)
  {
    midi(new Score(new Part(paramPhrase)));
  }
  
  public static void midi(Phrase paramPhrase, String paramString)
  {
    Part localPart = new Part();
    localPart.addPhrase(paramPhrase);
    Score localScore = new Score("Score of " + paramPhrase.getTitle());
    localScore.addPart(localPart);
    midi(localScore, paramString);
  }
  
  public static void midi(CPhrase paramCPhrase)
  {
    Part localPart = new Part();
    localPart.addCPhrase(paramCPhrase);
    Score localScore = new Score("Score of " + paramCPhrase.getTitle());
    localScore.addPart(localPart);
    midi(localScore, paramCPhrase.getTitle() + ".mid");
  }
  
  public static void midi(CPhrase paramCPhrase, String paramString)
  {
    Part localPart = new Part();
    localPart.addCPhrase(paramCPhrase);
    Score localScore = new Score("Score of " + paramCPhrase.getTitle());
    localScore.addPart(localPart);
    midi(localScore, paramString);
  }
  
  public static void midi(Note paramNote)
  {
    midi(paramNote, "SingleNote.mid");
  }
  
  public static void midi(Note paramNote, String paramString)
  {
    Score localScore = new Score("Score of a single note");
    Part localPart = new Part(new Phrase(paramNote));
    localScore.addPart(localPart);
    midi(localScore, paramString);
  }
  
  public static void jm(Score paramScore)
  {
    jm(paramScore, paramScore.getTitle() + ".jm");
  }
  
  public static void jm(Score paramScore, String paramString)
  {
    try
    {
      System.out.println("--------------------- Writing JM File -----------------------");
      FileOutputStream localFileOutputStream = new FileOutputStream(paramString);
      ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(localFileOutputStream);
      localObjectOutputStream.writeObject(paramScore);
      localObjectOutputStream.flush();
      localObjectOutputStream.close();
      System.out.println("JM file '" + paramString + "' written from score '" + paramScore.getTitle() + "'");
      System.out.println("-------------------------------------------------------------");
    }
    catch (IOException localIOException)
    {
      System.err.println(localIOException);
    }
  }
  
  public static void jm(Part paramPart)
  {
    Score localScore = new Score("Score of " + paramPart.getTitle());
    localScore.addPart(paramPart);
    jm(localScore, paramPart.getTitle() + ".jm");
  }
  
  public static void jm(Part paramPart, String paramString)
  {
    Score localScore = new Score("Score of " + paramPart.getTitle());
    localScore.addPart(paramPart);
    jm(localScore, paramString);
  }
  
  public static void jm(Phrase paramPhrase)
  {
    Part localPart = new Part();
    localPart.addPhrase(paramPhrase);
    Score localScore = new Score("Score of " + paramPhrase.getTitle());
    localScore.addPart(localPart);
    jm(localScore, paramPhrase.getTitle() + ".jm");
  }
  
  public static void jm(Phrase paramPhrase, String paramString)
  {
    Part localPart = new Part();
    localPart.addPhrase(paramPhrase);
    Score localScore = new Score("Score of " + paramPhrase.getTitle());
    localScore.addPart(localPart);
    jm(localScore, paramString);
  }
  
  public static void jm(CPhrase paramCPhrase)
  {
    Part localPart = new Part();
    localPart.addCPhrase(paramCPhrase);
    Score localScore = new Score("Score of " + paramCPhrase.getTitle());
    localScore.addPart(localPart);
    jm(localScore, paramCPhrase.getTitle() + ".jm");
  }
  
  public static void jm(CPhrase paramCPhrase, String paramString)
  {
    Part localPart = new Part();
    localPart.addCPhrase(paramCPhrase);
    Score localScore = new Score("Score of " + paramCPhrase.getTitle());
    localScore.addPart(localPart);
    jm(localScore, paramString);
  }
  
  public static void au(Phrase paramPhrase, Instrument paramInstrument)
  {
    au(new Part(paramPhrase), paramInstrument);
  }
  
  public static void au(Part paramPart, Instrument paramInstrument)
  {
    au(new Score(paramPart), paramInstrument);
  }
  
  public static void au(Score paramScore, Instrument paramInstrument)
  {
    Instrument[] arrayOfInstrument = { paramInstrument };
    au(paramScore, paramScore.getTitle() + ".au", arrayOfInstrument);
  }
  
  public static void au(Score paramScore, Instrument[] paramArrayOfInstrument)
  {
    au(paramScore, paramScore.getTitle() + ".au", paramArrayOfInstrument);
  }
  
  public static void au(Score paramScore, String paramString, Instrument paramInstrument)
  {
    Instrument[] arrayOfInstrument = { paramInstrument };
    au(paramScore, paramString, arrayOfInstrument);
  }
  
  public static void au(Score paramScore, String paramString, Instrument[] paramArrayOfInstrument)
  {
    double d1 = System.currentTimeMillis();
    System.out.println("------------------------------ Writing AU File --------------------------------");
    String str1 = paramString + ".jpf";
    String str2 = "jmusic.tmp";
    File localFile = new File(str2);
    if (localFile.exists()) {
      localFile.delete();
    }
    Audio.processScore(paramScore, paramArrayOfInstrument, str1);
    Audio.combine(str1, str2, paramString, true, true);
    double d2 = System.currentTimeMillis();
    System.out.println("AU file '" + paramString + "' written from score '" + paramScore.getTitle() + "' in " + (d2 - d1) / 1000.0D + " seconds.");
    System.out.println("-------------------------------------------------------------------------------");
  }
  
  public static void au(Part paramPart, Instrument[] paramArrayOfInstrument)
  {
    Score localScore = new Score("Score of " + paramPart.getTitle());
    localScore.addPart(paramPart);
    au(localScore, paramPart.getTitle() + ".au", paramArrayOfInstrument);
  }
  
  public static void au(Part paramPart, String paramString, Instrument paramInstrument)
  {
    Score localScore = new Score("Score of " + paramPart.getTitle());
    localScore.addPart(paramPart);
    Instrument[] arrayOfInstrument = { paramInstrument };
    au(localScore, paramString, arrayOfInstrument);
  }
  
  public static void au(Part paramPart, String paramString, Instrument[] paramArrayOfInstrument)
  {
    Score localScore = new Score("Score of " + paramPart.getTitle());
    localScore.addPart(paramPart);
    au(localScore, paramString, paramArrayOfInstrument);
  }
  
  public static void au(Phrase paramPhrase, Instrument[] paramArrayOfInstrument)
  {
    Part localPart = new Part();
    localPart.addPhrase(paramPhrase);
    Score localScore = new Score("Score of " + paramPhrase.getTitle());
    localScore.addPart(localPart);
    au(localScore, paramPhrase.getTitle() + ".au", paramArrayOfInstrument);
  }
  
  public static void au(Phrase paramPhrase, String paramString, Instrument[] paramArrayOfInstrument)
  {
    Part localPart = new Part();
    localPart.addPhrase(paramPhrase);
    Score localScore = new Score("Score of " + paramPhrase.getTitle());
    localScore.addPart(localPart);
    au(localScore, paramString, paramArrayOfInstrument);
  }
  
  public static void au(Phrase paramPhrase, String paramString, Instrument paramInstrument)
  {
    Part localPart = new Part();
    localPart.addPhrase(paramPhrase);
    Score localScore = new Score("Score of " + paramPhrase.getTitle());
    localScore.addPart(localPart);
    Instrument[] arrayOfInstrument = { paramInstrument };
    au(localScore, paramString, arrayOfInstrument);
  }
  
  public static void au(CPhrase paramCPhrase, Instrument[] paramArrayOfInstrument)
  {
    Part localPart = new Part();
    localPart.addCPhrase(paramCPhrase);
    Score localScore = new Score("Score of " + paramCPhrase.getTitle());
    localScore.addPart(localPart);
    au(localScore, paramCPhrase.getTitle() + ".au", paramArrayOfInstrument);
  }
  
  public static void au(CPhrase paramCPhrase, String paramString, Instrument[] paramArrayOfInstrument)
  {
    Part localPart = new Part();
    localPart.addCPhrase(paramCPhrase);
    Score localScore = new Score("Score of " + paramCPhrase.getTitle());
    localScore.addPart(localPart);
    au(localScore, paramString, paramArrayOfInstrument);
  }
  
  public static void audio(float[] paramArrayOfFloat, String paramString)
  {
    audio(paramArrayOfFloat, paramString, 1, 44100, 16);
  }
  
  public static void audio(float[] paramArrayOfFloat, String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    double d1 = System.currentTimeMillis();
    System.out.println("---------------------------- Writing Audio File -------------------------------");
    AudioFileOut localAudioFileOut = new AudioFileOut(paramArrayOfFloat, paramString, paramInt1, paramInt2, paramInt3);
    double d2 = System.currentTimeMillis();
    System.out.println("Audio file '" + paramString + "' written in " + (d2 - d1) / 1000.0D + " seconds.");
    System.out.println("Channels = " + paramInt1 + " Sample rate = " + paramInt2 + " Bit depth = " + paramInt3);
    System.out.println("-------------------------------------------------------------------------------");
  }
  
  public static void xml(Score paramScore)
  {
    xml(paramScore, paramScore.getTitle() + ".xml");
  }
  
  public static void xml(Score paramScore, String paramString)
  {
    try
    {
      PrintWriter localPrintWriter = new PrintWriter(new FileWriter(paramString));
      System.out.println("--------------------- Writing XML File -----------------------");
      String str = XMLParser.scoreToXMLString(paramScore);
      localPrintWriter.print(str);
      localPrintWriter.close();
      System.out.println("XML file '" + paramString + "' written from score '" + paramScore.getTitle() + "'");
      System.out.println("-------------------------------------------------------------");
    }
    catch (IOException localIOException)
    {
      System.err.println(localIOException);
    }
  }
  
  public static void xml(Part paramPart)
  {
    Score localScore = new Score("Score of " + paramPart.getTitle());
    localScore.addPart(paramPart);
    xml(localScore, paramPart.getTitle() + ".xml");
  }
  
  public static void xml(Part paramPart, String paramString)
  {
    Score localScore = new Score("Score of " + paramPart.getTitle());
    localScore.addPart(paramPart);
    xml(localScore, paramString);
  }
  
  public static void xml(Phrase paramPhrase)
  {
    Part localPart = new Part();
    localPart.addPhrase(paramPhrase);
    Score localScore = new Score("Score of " + paramPhrase.getTitle());
    localScore.addPart(localPart);
    xml(localScore, paramPhrase.getTitle() + ".xml");
  }
  
  public static void xml(Phrase paramPhrase, String paramString)
  {
    Part localPart = new Part();
    localPart.addPhrase(paramPhrase);
    Score localScore = new Score("Score of " + paramPhrase.getTitle());
    localScore.addPart(localPart);
    xml(localScore, paramString);
  }
  
  public static void xml(CPhrase paramCPhrase)
  {
    Part localPart = new Part();
    localPart.addCPhrase(paramCPhrase);
    Score localScore = new Score("Score of " + paramCPhrase.getTitle());
    localScore.addPart(localPart);
    xml(localScore, paramCPhrase.getTitle() + ".xml");
  }
  
  public static void xml(CPhrase paramCPhrase, String paramString)
  {
    Part localPart = new Part();
    localPart.addCPhrase(paramCPhrase);
    Score localScore = new Score("Score of " + paramCPhrase.getTitle());
    localScore.addPart(localPart);
    xml(localScore, paramString);
  }
  
  private static Score adjustTempo(Score paramScore)
  {
    Enumeration localEnumeration1 = paramScore.getPartList().elements();
    double d1 = 60.0D / paramScore.getTempo();
    while (localEnumeration1.hasMoreElements())
    {
      Part localPart = (Part)localEnumeration1.nextElement();
      double d2 = d1;
      if (localPart.getTempo() != 0.0D) {
        d2 = 60.0D / localPart.getTempo();
      }
      Enumeration localEnumeration2 = localPart.getPhraseList().elements();
      while (localEnumeration2.hasMoreElements())
      {
        Phrase localPhrase = (Phrase)localEnumeration2.nextElement();
        Enumeration localEnumeration3 = localPhrase.getNoteList().elements();
        while (localEnumeration3.hasMoreElements())
        {
          Note localNote = (Note)localEnumeration3.nextElement();
          localNote.setRhythmValue(localNote.getRhythmValue() * d2);
          localNote.setDuration(localNote.getDuration() * d2);
        }
      }
    }
    return paramScore;
  }
}
