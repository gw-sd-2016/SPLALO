package jm.audio.synth;

import java.io.PrintStream;
import jm.audio.AOException;
import jm.audio.AudioObject;
import jm.audio.Instrument;
import jm.music.data.Note;

public final class Value
  extends AudioObject
{
  private float theValue;
  private double changeRatio = 1.0D;
  int noteAttribute = 0;
  public static final int FIXED = 0;
  public static final int NOTE_PITCH = 1;
  public static final int NOTE_DYNAMIC = 2;
  public static final int NOTE_DURATION = 3;
  public static final int NOTE_RHYTHM_VALUE = 4;
  
  public Value(Instrument paramInstrument, int paramInt1, int paramInt2, float paramFloat)
  {
    super(paramInstrument, paramInt1, "[Value]");
    this.theValue = paramFloat;
    this.channels = paramInt2;
  }
  
  public Value(Instrument paramInstrument, int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramInstrument, paramInt1, "[Value]");
    this.noteAttribute = paramInt3;
    this.channels = paramInt2;
  }
  
  public double getChangeRation()
  {
    return this.changeRatio;
  }
  
  public void setChangeRation(double paramDouble)
  {
    this.changeRatio = paramDouble;
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    for (int i = 0; i < paramArrayOfFloat.length; i++) {
      paramArrayOfFloat[i] = this.theValue;
    }
    return i;
  }
  
  public void build()
  {
    switch (this.noteAttribute)
    {
    case 0: 
      break;
    case 1: 
      this.theValue = ((float)(this.currentNote.getFrequency() * this.changeRatio));
      break;
    case 2: 
      this.theValue = (127.0F / (float)(this.currentNote.getDynamic() * this.changeRatio));
      break;
    case 3: 
      this.theValue = ((float)(this.currentNote.getDuration() * this.changeRatio));
      break;
    case 4: 
      this.theValue = ((float)(this.currentNote.getRhythmValue() * this.changeRatio));
      break;
    default: 
      System.err.println(this.name + " A value setting of " + this.theValue + " is not supported yet");
      System.exit(1);
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\Value.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */