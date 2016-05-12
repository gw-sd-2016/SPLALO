package jm.music.rt;

import java.io.PrintStream;
import jm.audio.AOException;
import jm.audio.AudioChainListener;
import jm.audio.Instrument;
import jm.audio.RTMixer;
import jm.music.data.Note;

public abstract class RTLine
  implements AudioChainListener
{
  protected Instrument[] inst;
  protected boolean clear = false;
  private double localCounter = 0.0D;
  private boolean newNote = true;
  private double tempo = 60.0D;
  private double testPos;
  private double size;
  Note note = null;
  double scorePos = 0.0D;
  double temp = 1.0D;
  
  public RTLine(Instrument[] paramArrayOfInstrument)
  {
    this.inst = paramArrayOfInstrument;
    for (int i = 0; i < paramArrayOfInstrument.length; i++) {
      paramArrayOfInstrument[i].addRTLine(this);
    }
    i = paramArrayOfInstrument[0].getSampleRate();
    int j = paramArrayOfInstrument[0].getChannels();
    for (int k = 0; k < paramArrayOfInstrument.length; k++)
    {
      if (paramArrayOfInstrument[k].getSampleRate() != i)
      {
        System.err.println("jMusic RTLine error: All instruments must have the same sample rate.");
        System.exit(0);
      }
      if (paramArrayOfInstrument[k].getChannels() != j)
      {
        System.err.println("jMusic RTLine error: All instruments must have the same number of channels.");
        System.exit(0);
      }
    }
    this.size = (i * j);
  }
  
  public Instrument[] getInstrument()
  {
    return this.inst;
  }
  
  public int getNumLines()
  {
    return this.inst.length;
  }
  
  public void setTempo(double paramDouble)
  {
    this.tempo = paramDouble;
  }
  
  public void setBufferSize(int paramInt)
  {
    for (int i = 0; i < this.inst.length; i++) {
      this.inst[i].setBufSize(paramInt);
    }
  }
  
  public int getSampleRate()
  {
    return this.inst[0].getSampleRate();
  }
  
  public int getChannels()
  {
    return this.inst[0].getChannels();
  }
  
  public void externalAction(Object paramObject, int paramInt) {}
  
  public synchronized void controlChange(float[] paramArrayOfFloat, int paramInt, boolean paramBoolean) {}
  
  public void instNote(Instrument paramInstrument, long paramLong)
  {
    this.scorePos = (paramLong / this.size);
    this.temp = (60.0D / this.tempo);
    if (this.scorePos > this.testPos)
    {
      this.note = getNextNote().copy();
      this.note.setRhythmValue(this.note.getRhythmValue() * this.temp);
      this.note.setDuration(this.note.getDuration() * this.temp);
      this.testPos += this.note.getRhythmValue();
    }
    else
    {
      this.note = new Note(Integer.MIN_VALUE, this.testPos - this.scorePos);
      this.note.setRhythmValue(this.note.getRhythmValue() * this.temp);
      this.note.setDuration(this.note.getRhythmValue());
    }
    paramInstrument.renderNote(this.note, this.scorePos);
  }
  
  public void start(RTMixer paramRTMixer)
  {
    for (int i = 0; i < this.inst.length; i++) {
      try
      {
        if (!this.inst[i].getInitialised())
        {
          this.inst[i].createChain();
          this.inst[i].setInitialised(true);
        }
        this.inst[i].addAudioChainListener(paramRTMixer);
        this.inst[i].start();
      }
      catch (AOException localAOException)
      {
        System.err.println("jMusic RTLine start error: Perhpas a jMusic instrument was being reused.");
        localAOException.printStackTrace();
      }
    }
  }
  
  public void pause()
  {
    for (int i = 0; i < this.inst.length; i++) {
      this.inst[i].pause();
    }
  }
  
  public void unPause()
  {
    for (int i = 0; i < this.inst.length; i++) {
      this.inst[i].unPause();
    }
  }
  
  public void stop()
  {
    for (int i = 0; i < this.inst.length; i++) {
      this.inst[i].stop();
    }
  }
  
  public abstract Note getNextNote();
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\music\rt\RTLine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */