package jm.util;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.PrintStream;
import jm.JMC;
import jm.gui.cpn.Notate;
import jm.gui.histogram.HistogramFrame;
import jm.gui.show.ShowScore;
import jm.gui.sketch.SketchScore;
import jm.gui.wave.WaveView;
import jm.music.data.CPhrase;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

public class View
  implements JMC
{
  public View() {}
  
  public static void show(Score paramScore)
  {
    show(paramScore, 0, 0);
  }
  
  public static void pianoRoll(Score paramScore)
  {
    show(paramScore, 0, 0);
  }
  
  public static void show(Score paramScore, int paramInt1, int paramInt2)
  {
    new ShowScore(paramScore, paramInt1, paramInt2);
  }
  
  public static void pianoRoll(Score paramScore, int paramInt1, int paramInt2)
  {
    new ShowScore(paramScore, paramInt1, paramInt2);
  }
  
  public static void show(Part paramPart)
  {
    show(paramPart, 0, 0);
  }
  
  public static void pianoRoll(Part paramPart)
  {
    show(paramPart, 0, 0);
  }
  
  public static void show(Part paramPart, int paramInt1, int paramInt2)
  {
    Score localScore = new Score("Part: " + paramPart.getTitle());
    localScore.addPart(paramPart);
    new ShowScore(localScore, paramInt1, paramInt2);
  }
  
  public static void pianoRoll(Part paramPart, int paramInt1, int paramInt2)
  {
    Score localScore = new Score("Part: " + paramPart.getTitle());
    localScore.addPart(paramPart);
    new ShowScore(localScore, paramInt1, paramInt2);
  }
  
  public static void show(CPhrase paramCPhrase)
  {
    show(paramCPhrase, 0, 0);
  }
  
  public static void pianoRoll(CPhrase paramCPhrase)
  {
    show(paramCPhrase, 0, 0);
  }
  
  public static void show(CPhrase paramCPhrase, int paramInt1, int paramInt2)
  {
    Score localScore = new Score("Phrase: " + paramCPhrase.getTitle());
    Part localPart = new Part();
    localPart.addCPhrase(paramCPhrase);
    localScore.addPart(localPart);
    new ShowScore(localScore, paramInt1, paramInt2);
  }
  
  public static void pianoRoll(CPhrase paramCPhrase, int paramInt1, int paramInt2)
  {
    Score localScore = new Score("Phrase: " + paramCPhrase.getTitle());
    Part localPart = new Part();
    localPart.addCPhrase(paramCPhrase);
    localScore.addPart(localPart);
    new ShowScore(localScore, paramInt1, paramInt2);
  }
  
  public static void show(Phrase paramPhrase)
  {
    show(paramPhrase, 0, 0);
  }
  
  public static void pianoRoll(Phrase paramPhrase)
  {
    show(paramPhrase, 0, 0);
  }
  
  public static void show(Phrase paramPhrase, int paramInt1, int paramInt2)
  {
    Score localScore = new Score("Phrase: " + paramPhrase.getTitle());
    Part localPart = new Part();
    localPart.addPhrase(paramPhrase);
    localScore.addPart(localPart);
    new ShowScore(localScore, paramInt1, paramInt2);
  }
  
  public static void pianoRoll(Phrase paramPhrase, int paramInt1, int paramInt2)
  {
    Score localScore = new Score("Phrase: " + paramPhrase.getTitle());
    Part localPart = new Part();
    localPart.addPhrase(paramPhrase);
    localScore.addPart(localPart);
    new ShowScore(localScore, paramInt1, paramInt2);
  }
  
  public static void notate(Phrase paramPhrase)
  {
    new Notate(paramPhrase, 0, 0);
  }
 
  public static void notate(CPhrase paramPhrase)
  {
    new Notate(paramPhrase, 0, 0);
  }
  
  public static void notation(Phrase paramPhrase)
  {
    new Notate(paramPhrase, 0, 0);
  }
  
  public static void notation(CPhrase paramPhrase)
  {
    new Notate(paramPhrase, 0, 0);
  }
  
  public static void notate(Phrase paramPhrase, int paramInt1, int paramInt2)
  {
    new Notate(paramPhrase, paramInt1, paramInt2);
  }
  
  public static void notation(Phrase paramPhrase, int paramInt1, int paramInt2)
  {
    new Notate(paramPhrase, paramInt1, paramInt2);
  }
  
  public static void notation(CPhrase paramPhrase, int paramInt1, int paramInt2)
  {
    new Notate(paramPhrase, paramInt1, paramInt2);
  }
  
  public static void notate(CPhrase paramPhrase, int paramInt1, int paramInt2)
  {
    new Notate(paramPhrase, paramInt1, paramInt2);
  }
  
  public static void notate(Part paramPart)
  {
    new Notate(paramPart.getPhrase(0), 0, 0);
  }
  
  public static void notation(Part paramPart)
  {
    new Notate(paramPart.getPhrase(0), 0, 0);
  }
  
  public static void notate(Part paramPart, int paramInt1, int paramInt2)
  {
    new Notate(paramPart.getPhrase(0), paramInt1, paramInt2);
  }
  
  public static void notation(Part paramPart, int paramInt1, int paramInt2)
  {
    new Notate(paramPart.getPhrase(0), paramInt1, paramInt2);
  }
  
  public static void notate(Score paramScore)
  {
    new Notate(paramScore, 0, 0);
  }
  
  public static void notation(Score paramScore)
  {
    new Notate(paramScore, 0, 0);
  }
  
  public static void notate(Score paramScore, int paramInt1, int paramInt2)
  {
    new Notate(paramScore, paramInt1, paramInt2);
  }
  
  public static void notation(Score paramScore, int paramInt1, int paramInt2)
  {
    new Notate(paramScore, paramInt1, paramInt2);
  }
  
  public static void sketch(Score paramScore)
  {
    sketch(paramScore, 0, 0);
  }
  
  public static void sketch(Score paramScore, int paramInt1, int paramInt2)
  {
    new SketchScore(paramScore, paramInt1, paramInt2);
  }
  
  public static void sketch(Part paramPart)
  {
    sketch(paramPart, 0, 0);
  }
  
  public static void sketch(Part paramPart, int paramInt1, int paramInt2)
  {
    Score localScore = new Score("Part: " + paramPart.getTitle());
    localScore.addPart(paramPart);
    new SketchScore(localScore, paramInt1, paramInt2);
  }
  
  public static void sketch(Phrase paramPhrase)
  {
    sketch(paramPhrase, 0, 0);
  }
  
  public static void sketch(Phrase paramPhrase, int paramInt1, int paramInt2)
  {
    Score localScore = new Score("Phrase: " + paramPhrase.getTitle());
    Part localPart = new Part();
    localPart.addPhrase(paramPhrase);
    localScore.addPart(localPart);
    new SketchScore(localScore, paramInt1, paramInt2);
  }
  
  public static void print(Note paramNote)
  {
    System.out.println(paramNote.toString());
  }
  
  public static void internal(Note paramNote)
  {
    System.out.println(paramNote.toString());
  }
  
  public static void print(Phrase paramPhrase)
  {
    System.out.println(paramPhrase.toString());
  }
  
  public static void internal(Phrase paramPhrase)
  {
    System.out.println(paramPhrase.toString());
  }
  
  public static void print(CPhrase paramCPhrase)
  {
    System.out.println(paramCPhrase.toString());
  }
  
  public static void internal(CPhrase paramCPhrase)
  {
    System.out.println(paramCPhrase.toString());
  }
  
  public static void print(Part paramPart)
  {
    System.out.println(paramPart.toString());
  }
  
  public static void internal(Part paramPart)
  {
    System.out.println(paramPart.toString());
  }
  
  public static void print(Score paramScore)
  {
    System.out.println(paramScore.toString());
  }
  
  public static void internal(Score paramScore)
  {
    System.out.println(paramScore.toString());
  }
  
  public static void histogram()
  {
    FileDialog localFileDialog = new FileDialog(new Frame(), "Select a MIDI file to display.", 0);
    localFileDialog.show();
    String str = localFileDialog.getFile();
    if (str != null)
    {
      Score localScore = new Score();
      Read.midi(localScore, localFileDialog.getDirectory() + str);
      HistogramFrame localHistogramFrame = new HistogramFrame(localScore);
    }
  }
  
  public static void histogram(Score paramScore)
  {
    histogram(paramScore, 0);
  }
  
  public static void histogram(Score paramScore, int paramInt)
  {
    histogram(paramScore, paramInt, 0, 0);
  }
  
  public static void histogram(Score paramScore, int paramInt1, int paramInt2, int paramInt3)
  {
    new HistogramFrame(paramScore, paramInt1, paramInt2, paramInt3);
  }
  
  public static void au(String paramString)
  {
    new WaveView(paramString);
  }
  
  public static void au(String paramString, int paramInt1, int paramInt2)
  {
    new WaveView(paramString, paramInt1, paramInt2);
  }
}

