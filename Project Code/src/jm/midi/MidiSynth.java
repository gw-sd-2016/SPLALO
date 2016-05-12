package jm.midi;

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Stack;
import java.util.Vector;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;

public class MidiSynth
  implements JMC, MetaEventListener
{
  private short m_ppqn;
  private Synthesizer m_synth;
  private Sequence m_seq;
  private Sequencer m_sequencer;
  private float m_currentTempo;
  private float m_masterTempo;
  private Stack m_tempoHistory;
  private double trackTempoRatio = 1.0D;
  private double elementTempoRatio = 1.0D;
  private String scoreTitle;
  private boolean isPlaying = false;
  private Boolean msCycle = Boolean.valueOf(false);
  private Boolean update = Boolean.valueOf(false);
  private Score updateScore;
  private static final int StopType = 47;
  
  public MidiSynth()
  {
    this((short)480);
  }
  
  public MidiSynth(short paramShort)
  {
    this.m_ppqn = paramShort;
    this.m_synth = null;
    this.m_tempoHistory = new Stack();
  }
  
  public boolean isPlaying()
  {
    return this.isPlaying;
  }
  
  public void play(Score paramScore)
    throws InvalidMidiDataException
  {
    if ((null == this.m_sequencer) && (!initSynthesizer())) {
      return;
    }
    this.scoreTitle = paramScore.getTitle();
    this.m_masterTempo = ((float)paramScore.getTempo());
    if (null == this.m_seq)
    {
      this.m_seq = scoreToSeq(paramScore);
    }
    else
    {
      Track[] arrayOfTrack = this.m_seq.getTracks();
      int i = arrayOfTrack.length;
      for (int j = 0; j < i; j++) {
        this.m_seq.deleteTrack(arrayOfTrack[j]);
      }
      this.m_seq = scoreToSeq(paramScore);
    }
    if (null != this.m_seq)
    {
      try
      {
        this.m_sequencer.open();
      }
      catch (MidiUnavailableException localMidiUnavailableException)
      {
        System.err.println("MIDI System Unavailable:" + localMidiUnavailableException);
        return;
      }
      this.m_sequencer.setSequence(this.m_seq);
      this.m_sequencer.addMetaEventListener(this);
      this.m_sequencer.setMicrosecondPosition(0L);
      this.m_sequencer.setTempoInBPM(this.m_masterTempo);
      this.m_sequencer.start();
      this.isPlaying = true;
    }
  }
  
  public void updateSeq(Score paramScore)
    throws InvalidMidiDataException
  {
    this.update = Boolean.valueOf(true);
    this.updateScore = paramScore;
  }
  
  public void setCycle(Boolean paramBoolean)
  {
    this.msCycle = paramBoolean;
  }
  
  private void rePlay()
  {
    if ((null == this.m_sequencer) && (!initSynthesizer())) {
      return;
    }
    if (this.update.booleanValue())
    {
      System.out.println("Updating playback sequence");
      try
      {
        this.m_seq = scoreToSeq(this.updateScore);
        if (null != this.m_seq)
        {
          try
          {
            this.m_sequencer.open();
          }
          catch (MidiUnavailableException localMidiUnavailableException)
          {
            System.err.println("MIDI System Unavailable:" + localMidiUnavailableException);
            return;
          }
          this.m_sequencer.setSequence(this.m_seq);
        }
      }
      catch (InvalidMidiDataException localInvalidMidiDataException)
      {
        System.err.println("MIDISynth updating sequence error:" + localInvalidMidiDataException);
        return;
      }
      this.update = Boolean.valueOf(false);
    }
    this.m_sequencer.setMicrosecondPosition(0L);
    this.m_sequencer.setTempoInBPM(this.m_masterTempo);
    this.m_sequencer.start();
  }
  
  protected void printSeqInfo(Sequence paramSequence)
  {
    float f = paramSequence.getDivisionType();
  }
  
  public void meta(MetaMessage paramMetaMessage)
  {
    if (paramMetaMessage.getType() == 47) {
      if (this.msCycle.booleanValue()) {
        rePlay();
      } else {
        stop();
      }
    }
  }
  
  public void stop()
  {
    this.msCycle = Boolean.valueOf(false);
    this.isPlaying = false;
    if ((this.m_sequencer != null & this.m_sequencer.isOpen())) {
      this.m_sequencer.stop();
    }
    System.out.println("jMusic MidiSynth: Stopped JavaSound MIDI playback");
  }
  
  protected static MidiEvent createNoteOnEvent(int paramInt1, int paramInt2, int paramInt3, long paramLong)
    throws InvalidMidiDataException
  {
    ShortMessage localShortMessage = new ShortMessage();
    localShortMessage.setMessage(144 + paramInt1, paramInt2, paramInt3);
    MidiEvent localMidiEvent = new MidiEvent(localShortMessage, paramLong);
    return localMidiEvent;
  }
  
  protected static MidiEvent createNoteOffEvent(int paramInt1, int paramInt2, int paramInt3, long paramLong)
    throws InvalidMidiDataException
  {
    ShortMessage localShortMessage = new ShortMessage();
    localShortMessage.setMessage(128 + paramInt1, paramInt2, paramInt3);
    MidiEvent localMidiEvent = new MidiEvent(localShortMessage, paramLong);
    return localMidiEvent;
  }
  
  protected static MidiEvent createProgramChangeEvent(int paramInt1, int paramInt2, long paramLong)
    throws InvalidMidiDataException
  {
    ShortMessage localShortMessage = new ShortMessage();
    localShortMessage.setMessage(192 + paramInt1, paramInt2, 0);
    MidiEvent localMidiEvent = new MidiEvent(localShortMessage, paramLong);
    return localMidiEvent;
  }
  
  protected static MidiEvent createCChangeEvent(int paramInt1, int paramInt2, int paramInt3, long paramLong)
    throws InvalidMidiDataException
  {
    ShortMessage localShortMessage = new ShortMessage();
    localShortMessage.setMessage(176 + paramInt1, paramInt2, paramInt3);
    MidiEvent localMidiEvent = new MidiEvent(localShortMessage, paramLong);
    return localMidiEvent;
  }
  
  protected Sequence scoreToSeq(Score paramScore)
    throws InvalidMidiDataException
  {
    Sequence localSequence = new Sequence(0.0F, this.m_ppqn);
    if (null == localSequence) {
      return null;
    }
    this.m_masterTempo = (this.m_currentTempo = new Float(paramScore.getTempo()).floatValue());
    Object localObject1 = null;
    double d1 = 0.0D;
    double d2 = 1.0D;
    Enumeration localEnumeration1 = paramScore.getPartList().elements();
    Object localObject2;
    while (localEnumeration1.hasMoreElements())
    {
      localObject2 = (Part)localEnumeration1.nextElement();
      int i = ((Part)localObject2).getChannel();
      if (i > 16) {
        throw new InvalidMidiDataException(((Part)localObject2).getTitle() + " - Invalid Channel Number: " + i);
      }
      this.m_tempoHistory.push(new Float(this.m_currentTempo));
      float f = new Float(((Part)localObject2).getTempo()).floatValue();
      if (f != -1.0D) {
        this.m_currentTempo = f;
      } else if (f < -1.0D) {
        System.out.println("jMusic MidiSynth error: Part TempoEvent (BPM) too low = " + f);
      }
      this.trackTempoRatio = (this.m_masterTempo / this.m_currentTempo);
      int j = ((Part)localObject2).getInstrument();
      if (j == -1) {
        j = 0;
      }
      Enumeration localEnumeration2 = ((Part)localObject2).getPhraseList().elements();
      double d3 = 0.0D;
      double d4 = 0.0D;
      Track localTrack = localSequence.createTrack();
      while (localEnumeration2.hasMoreElements())
      {
        localObject3 = (Phrase)localEnumeration2.nextElement();
        d4 = ((Phrase)localObject3).getStartTime();
        long l1 = (d4 * this.m_ppqn * this.trackTempoRatio);
        if (((Phrase)localObject3).getInstrument() != -1) {
          j = ((Phrase)localObject3).getInstrument();
        }
        MidiEvent localMidiEvent2 = createProgramChangeEvent(i, j, l1);
        localTrack.add(localMidiEvent2);
        this.m_tempoHistory.push(new Float(this.m_currentTempo));
        f = new Float(((Phrase)localObject3).getTempo()).floatValue();
        if (f != -1.0D) {
          this.m_currentTempo = f;
        }
        this.elementTempoRatio = (this.m_masterTempo / this.m_currentTempo);
        double d5 = -1.0D;
        int k = 0;
        Enumeration localEnumeration3 = ((Phrase)localObject3).getNoteList().elements();
        while (localEnumeration3.hasMoreElements())
        {
          localObject4 = (Note)localEnumeration3.nextElement();
          k = (int)(((Note)localObject4).getOffset() * this.m_ppqn * this.elementTempoRatio);
          int m = -1;
          if (!((Note)localObject4).getPitchType()) {
            m = ((Note)localObject4).getPitch();
          } else {
            m = Note.freqToMidiPitch(((Note)localObject4).getFrequency());
          }
          int n = ((Note)localObject4).getDynamic();
          if (m == Integer.MIN_VALUE)
          {
            l1 = (l1 + ((Note)localObject4).getRhythmValue() * this.m_ppqn * this.elementTempoRatio);
          }
          else
          {
            long l2 = l1;
            if (((Note)localObject4).getPan() != d5)
            {
              localMidiEvent2 = createCChangeEvent(i, 10, (int)(((Note)localObject4).getPan() * 127.0D), l2);
              localTrack.add(localMidiEvent2);
              d5 = ((Note)localObject4).getPan();
            }
            localMidiEvent2 = createNoteOnEvent(i, m, n, l2 + k);
            localTrack.add(localMidiEvent2);
            long l3 = (l1 + ((Note)localObject4).getDuration() * this.m_ppqn * this.elementTempoRatio);
            localMidiEvent2 = createNoteOffEvent(i, m, n, l3 + k);
            localTrack.add(localMidiEvent2);
            l1 = (l1 + ((Note)localObject4).getRhythmValue() * this.m_ppqn * this.elementTempoRatio);
            if (l3 > d1)
            {
              d1 = l3;
              localObject1 = localTrack;
            }
          }
        }
        Object localObject4 = (Float)this.m_tempoHistory.pop();
        this.m_currentTempo = ((Float)localObject4).floatValue();
      }
      Object localObject3 = (Float)this.m_tempoHistory.pop();
      this.m_currentTempo = ((Float)localObject3).floatValue();
    }
    if ((d1 > 0.0D) && (localObject1 != null))
    {
      localObject2 = new MetaMessage();
      byte[] arrayOfByte = new byte[0];
      ((MetaMessage)localObject2).setMessage(47, arrayOfByte, 0);
      MidiEvent localMidiEvent1 = new MidiEvent((MidiMessage)localObject2, d1);
      ((Track)localObject1).add(localMidiEvent1);
    }
    return localSequence;
  }
  
  private boolean initSynthesizer()
  {
    if (null == this.m_synth) {
      try
      {
        if (MidiSystem.getSequencer() == null)
        {
          System.err.println("MidiSystem Sequencer Unavailable");
          return false;
        }
        this.m_synth = MidiSystem.getSynthesizer();
        this.m_synth.open();
        this.m_sequencer = MidiSystem.getSequencer();
      }
      catch (MidiUnavailableException localMidiUnavailableException)
      {
        System.err.println("Midi System Unavailable:" + localMidiUnavailableException);
        return false;
      }
    }
    return true;
  }
  
  public void finalize()
  {
    this.m_seq = null;
    this.m_sequencer.close();
    this.m_synth.close();
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\midi\MidiSynth.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */