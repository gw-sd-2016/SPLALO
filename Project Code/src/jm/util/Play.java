package jm.util;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Vector;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import jm.JMC;
import jm.audio.Instrument;
import jm.audio.RTMixer;
import jm.gui.wave.WaveFileReader;
import jm.midi.MidiSynth;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.music.rt.RTLine;
import jm.music.rt.RTPhrase;

public class Play
  implements JMC
{
  private static Vector cyclePlaying = new Vector();
  private static Thread pauseThread;
  private static Vector ms = new Vector();
  private static AudioInputStream audioInputStream;
  private static Vector mixerList = new Vector();
  private static boolean audioPlaying = false;
  private static boolean audioPaused = false;
  private static RTMixer mixer;
  private static MidiSynth midiSynth1 = new MidiSynth();
  private static MidiSynth midiSynth2 = new MidiSynth();
  private static int msCnt = 0;
  
  public Play() {}
  
  private static void msFill(int paramInt)
  {
    if (ms.size() < paramInt) {
      for (int i = ms.size(); i < paramInt; i++) {
        ms.add(new MidiSynth());
      }
    }
  }
  
  public static boolean cycleIsPlaying()
  {
    return cycleIsPlaying(0);
  }
  
  public static boolean cycleIsPlaying(int paramInt)
  {
    if (cyclePlaying.size() < paramInt + 1)
    {
      for (int i = cyclePlaying.size(); i < paramInt + 1; i++) {
        cyclePlaying.addElement(Boolean.valueOf(false));
      }
      cyclePlaying.add(paramInt, Boolean.valueOf(false));
    }
    return ((Boolean)cyclePlaying.elementAt(paramInt)).booleanValue();
  }
  
  public static void waitCycle(Score paramScore, int paramInt)
  {
    try
    {
      Thread.sleep((int)(60000.0D / paramScore.getTempo() * paramScore.getEndTime()) + paramInt);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public static void mid(String paramString)
  {
    Score localScore = new Score();
    Read.midi(localScore, paramString);
    midi(localScore);
  }
  
  public static void midi(Note paramNote)
  {
    midi(paramNote, true);
  }
  
  public static void midi(Phrase paramPhrase)
  {
    midi(paramPhrase, true);
  }
  
  public static void midi(Part paramPart)
  {
    midi(paramPart, true);
  }
  
  public static void midi(Score paramScore)
  {
    midi(paramScore, true);
  }
  
  public static void midi(Note paramNote, boolean paramBoolean)
  {
    Score localScore = new Score("One note score", 60.0D);
    localScore.addPart(new Part(new Phrase(paramNote)));
    midi(localScore, paramBoolean);
  }
  
  public static void midi(Phrase paramPhrase, boolean paramBoolean)
  {
    double d = 60.0D;
    if (paramPhrase.getTempo() != -1.0D) {
      d = paramPhrase.getTempo();
    }
    Score localScore = new Score(paramPhrase.getTitle() + " score", d);
    if (paramPhrase.getTempo() != -1.0D) {
      localScore.setTempo(paramPhrase.getTempo());
    }
    localScore.addPart(new Part(paramPhrase));
    midi(localScore, paramBoolean);
  }
  
  public static void midi(Part paramPart, boolean paramBoolean)
  {
    double d = 60.0D;
    if (paramPart.getTempo() != -1.0D) {
      d = paramPart.getTempo();
    }
    Score localScore = new Score(paramPart.getTitle() + " score", d);
    if (paramPart.getTempo() != -1.0D) {
      localScore.setTempo(paramPart.getTempo());
    }
    localScore.addPart(paramPart);
    midi(localScore, paramBoolean);
  }
  
  public static void midi(Score paramScore, boolean paramBoolean)
  {
    midi(paramScore, paramBoolean, true, 1, 0);
  }
  
  public static void midi(Score paramScore, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2)
  {
    System.out.println("jMusic Play: Playing score " + paramScore.getTitle() + " using JavaSound General MIDI soundbank.");
    msFill(paramInt1);
    MidiSynth localMidiSynth = (MidiSynth)ms.elementAt(msCnt);
    if (localMidiSynth.isPlaying()) {
      localMidiSynth.stop();
    }
    try
    {
      localMidiSynth.play(paramScore);
      if (paramBoolean2)
      {
        System.out.println("jMusic Play: Waiting for the end of " + paramScore.getTitle() + ".");
        if ((paramBoolean1) && (paramInt2 == 0)) {
          waitCycle(paramScore, 200);
        } else {
          waitCycle(paramScore, paramInt2);
        }
      }
    }
    catch (Exception localException)
    {
      System.err.println("jMusic Play: MIDI Playback Error:" + localException);
      return;
    }
    if (paramBoolean1) {
      closeAll();
    }
    msCnt = (msCnt + 1) % paramInt1;
  }
  
  public static void updateScore(Score paramScore)
  {
    updateScore(paramScore, 0);
  }
  
  public static void updateScore(Score paramScore, int paramInt)
  {
    try
    {
      ((MidiSynth)ms.elementAt(paramInt)).updateSeq(paramScore);
    }
    catch (Exception localException)
    {
      System.err.println("jMusic Play class can't update MIDI sequence:" + localException);
      return;
    }
  }
  
  public static void pauseAudio()
  {
    for (int i = 0; i < mixerList.size(); i++) {
      ((RTMixer)mixerList.elementAt(i)).pause();
    }
    audioPaused = true;
  }
  
  public static void unPauseAudio()
  {
    if (audioPaused)
    {
      for (int i = 0; i < mixerList.size(); i++) {
        ((RTMixer)mixerList.elementAt(i)).unPause();
      }
      audioPaused = false;
    }
    else
    {
      System.err.println("Play.unPauseAudio error: audio playback was not previously paused.");
    }
  }
  
  public static void stopAudio()
  {
    for (int i = 0; i < mixerList.size(); i++) {
      ((RTMixer)mixerList.elementAt(i)).stop();
    }
    audioPaused = false;
    audioPlaying = false;
  }
  
  public static void stopMidi()
  {
    for (int i = 0; i < ms.size(); i++) {
      stopMidi(i);
    }
  }
  
  public static void stopMidi(int paramInt)
  {
    System.out.println("jMusic Play: Stopping JavaSound MIDI playback of " + paramInt);
    if (cyclePlaying.size() < paramInt + 1) {
      for (int i = cyclePlaying.size(); i < paramInt + 1; i++) {
        cyclePlaying.addElement(Boolean.valueOf(false));
      }
    }
    cyclePlaying.set(paramInt, Boolean.valueOf(false));
    ((MidiSynth)ms.elementAt(paramInt)).stop();
  }
  
  public static void stopMidiCycle()
  {
    stopMidiCycle(0);
  }
  
  public static void stopMidiCycle(int paramInt)
  {
    System.out.println("jMusic Play: Stopping cycle playback at end of next sequence");
    cyclePlaying.set(paramInt, Boolean.valueOf(false));
    ((MidiSynth)ms.elementAt(paramInt)).setCycle(Boolean.valueOf(false));
  }
  
  public static void midiCycle(Note paramNote)
  {
    midiCycle(paramNote, 0);
  }
  
  public static void midiCycle(Note paramNote, int paramInt)
  {
    Score localScore = new Score("One note score");
    localScore.addPart(new Part(new Phrase(paramNote)));
    midiCycle(localScore, paramInt);
  }
  
  public static void midiCycle(Phrase paramPhrase)
  {
    midiCycle(paramPhrase, 0);
  }
  
  public static void midiCycle(Phrase paramPhrase, int paramInt)
  {
    Score localScore = new Score(paramPhrase.getTitle() + " score");
    localScore.addPart(new Part(paramPhrase));
    midiCycle(localScore, paramInt);
  }
  
  public static void midiCycle(Part paramPart)
  {
    midiCycle(paramPart, 0);
  }
  
  public static void midiCycle(Part paramPart, int paramInt)
  {
    Score localScore = new Score(paramPart.getTitle() + " score");
    localScore.addPart(paramPart);
    midiCycle(localScore, paramInt);
  }
  
  public static void midiCycle(Score paramScore)
  {
    midiCycle(paramScore, 0);
  }
  
  public static void midiCycle(Score paramScore, int paramInt)
  {
    int i;
    if (ms.size() < paramInt + 1) {
      for (i = ms.size(); i < paramInt + 1; i++) {
        ms.addElement(new MidiSynth());
      }
    }
    if (cyclePlaying.size() < paramInt + 1) {
      for (i = cyclePlaying.size(); i < paramInt + 1; i++) {
        cyclePlaying.addElement(Boolean.valueOf(false));
      }
    }
    if (((Boolean)cyclePlaying.elementAt(paramInt)).booleanValue() == true)
    {
      stopMidiCycle(paramInt);
      ((MidiSynth)ms.elementAt(paramInt)).stop();
    }
    cyclePlaying.set(paramInt, Boolean.valueOf(true));
    System.out.println("jMusic Play: Starting cycle playback");
    try
    {
      ((MidiSynth)ms.elementAt(paramInt)).play(paramScore);
      ((MidiSynth)ms.elementAt(paramInt)).setCycle(Boolean.valueOf(true));
    }
    catch (Exception localException)
    {
      System.err.println("MIDI Playback Error:" + localException);
      return;
    }
  }
  
  public static void au(String paramString)
  {
    au(paramString, true);
  }
  
  public static void au(String paramString, boolean paramBoolean)
  {
    WaveFileReader localWaveFileReader = new WaveFileReader(paramString);
    RTLine[] arrayOfRTLine = { new AudioRTLine(paramString) };
    RTMixer localRTMixer = new RTMixer(arrayOfRTLine);
    localRTMixer.begin();
    System.out.println("---------- Playing '" + paramString + "'... Sample rate = " + localWaveFileReader.getSampleRate() + " Channels = " + localWaveFileReader.getChannels() + " ----------");
    if (paramBoolean)
    {
      File localFile = new File(paramString);
      try
      {
        int i = localWaveFileReader.getBits() - 1;
        Thread.sleep((int)(localFile.length() / i / localWaveFileReader.getSampleRate() / localWaveFileReader.getChannels() * 1000.0D));
      }
      catch (InterruptedException localInterruptedException)
      {
        System.err.println("jMusic play.au error: Thread sleeping interupted");
      }
      System.out.println("-------------------- Completed Audio Playback ----------------------");
      System.exit(0);
    }
  }
  
  public static void audioFile(String paramString)
  {
    try
    {
      audioInputStream = AudioSystem.getAudioInputStream(new File(paramString));
      new AudioFilePlayThread(audioInputStream).start();
      System.out.println("Playing audio file " + paramString);
    }
    catch (IOException localIOException)
    {
      System.err.println("Play audioFile error: in playAudioFile(): " + localIOException.getMessage());
    }
    catch (UnsupportedAudioFileException localUnsupportedAudioFileException)
    {
      System.err.println("Unsupported Audio File error: in Play.audioFile():" + localUnsupportedAudioFileException.getMessage());
    }
  }
  
  public static void audio(Note paramNote, Instrument paramInstrument)
  {
    audio(new Phrase(paramNote), paramInstrument);
  }
  
  public static void audio(Phrase paramPhrase, Instrument[] paramArrayOfInstrument)
  {
    audio(new Score(new Part(paramPhrase)), paramArrayOfInstrument);
  }
  
  public static void audio(Phrase paramPhrase, Instrument paramInstrument)
  {
    Part localPart = new Part(paramPhrase);
    if (paramPhrase.getTempo() != -1.0D) {
      localPart.setTempo(paramPhrase.getTempo());
    }
    audio(localPart, new Instrument[] { paramInstrument });
  }
  
  public static void audio(Part paramPart, Instrument[] paramArrayOfInstrument)
  {
    Score localScore = new Score(paramPart);
    if (paramPart.getTempo() != -1.0D) {
      localScore.setTempo(paramPart.getTempo());
    }
    audio(localScore, paramArrayOfInstrument);
  }
  
  public static void audio(Score paramScore, Instrument[] paramArrayOfInstrument)
  {
    audioPlaying = true;
    System.out.print("Playing Score as Audio... ");
    for (int i = 0; i < paramArrayOfInstrument.length; i++) {
      paramArrayOfInstrument[i].setOutput(1);
    }
    Vector localVector = new Vector();
    for (int j = 0; j < paramScore.size(); j++)
    {
      Part localPart = paramScore.getPart(j);
      for (int m = 0; m < localPart.size(); m++)
      {
        Phrase localPhrase2 = localPart.getPhrase(m);
        if (localPhrase2.getInstrument() == -1) {
          localPhrase2.setInstrument(localPart.getInstrument());
        }
        if (localPhrase2.getTempo() == -1.0D) {
          localPhrase2.setTempo(localPart.getTempo());
        }
        localVector.addElement(localPhrase2);
      }
    }
    RTLine[] arrayOfRTLine = new RTLine[localVector.size()];
    for (int k = 0; k < localVector.size(); k++)
    {
      Phrase localPhrase1 = (Phrase)localVector.elementAt(k);
      arrayOfRTLine[k] = new RTPhrase(localPhrase1, paramArrayOfInstrument[localPhrase1.getInstrument()]);
    }
    if (mixer == null)
    {
      mixer = new RTMixer(arrayOfRTLine);
      mixer.begin();
    }
    else
    {
      mixer.addLines(arrayOfRTLine);
    }
  }
  
  private static void audioWait(Score paramScore, RTMixer paramRTMixer)
  {
    pauseThread = new Thread(new Runnable()
    {
      public void run()
      {
        try
        {
          Thread.sleep((int)(this.val$score.getEndTime() * 60.0D / this.val$score.getTempo() * 1000.0D));
        }
        catch (Exception localException)
        {
          System.out.println("jMusic Play.audioWait error in pauseThread");
        }
        System.out.println("Completed audio playback.");
        Play.access$102(true);
        try
        {
          Thread.sleep(500L);
        }
        catch (InterruptedException localInterruptedException) {}
      }
    });
    pauseThread.start();
  }
  
  public static void audioClip(String paramString)
  {
    System.out.println("-------- Playing an audio file ----------");
    System.out.println("Loading sound into memory, please wait...");
    File localFile = new File(paramString);
    try
    {
      URI localURI = localFile.toURI();
      URL localURL = localURI.toURL();
      AudioClip localAudioClip = Applet.newAudioClip(localURL);
      System.out.println("Playing '" + paramString + "' ...");
      localAudioClip.play();
    }
    catch (MalformedURLException localMalformedURLException)
    {
      System.err.println("jMusic play.au error: malformed URL or filename");
    }
    try
    {
      Thread.sleep((int)(localFile.length() / 2.0D / 44100.0D / 2.0D * 1000.0D) + 1000);
    }
    catch (InterruptedException localInterruptedException)
    {
      System.err.println("jMusic play.au error: Thread sleeping interupted");
    }
    System.out.println("-------------------- Completed Playback ----------------------");
    System.exit(0);
  }
  
  public static void closeAll()
  {
    audioInputStream = null;
    for (int i = 0; i < ms.size(); i++) {
      ((MidiSynth)ms.elementAt(i)).finalize();
    }
    ms.clear();
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\util\Play.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */