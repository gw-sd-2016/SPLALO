package jm.audio.synth;

import java.io.PrintStream;
import jm.audio.AOException;
import jm.audio.AudioObject;
import jm.audio.Instrument;
import jm.music.data.Note;

public final class Pluck
  extends AudioObject
{
  private int index = 0;
  float[] kernel = null;
  private boolean primary = true;
  private float prevSample = 0.0F;
  private float feedback = 0.49F;
  private float decay = 0.5F;
  private int delay = 1;
  private float[] delayLine;
  private int delayIndex;
  
  public Pluck(Instrument paramInstrument, int paramInt1, int paramInt2)
  {
    this(paramInstrument, paramInt1, paramInt2, 0.49D);
  }
  
  public Pluck(Instrument paramInstrument, int paramInt1, int paramInt2, double paramDouble)
  {
    super(paramInstrument, paramInt1, "[Pluck]");
    this.channels = paramInt2;
    this.feedback = ((float)paramDouble);
  }
  
  public Pluck(AudioObject paramAudioObject)
  {
    this(paramAudioObject, 0.5D);
  }
  
  public Pluck(AudioObject paramAudioObject, double paramDouble)
  {
    super(paramAudioObject, "[Pluck]");
    this.primary = false;
    this.feedback = ((float)paramDouble);
  }
  
  public void setFeedback(double paramDouble)
  {
    this.feedback = ((float)paramDouble);
  }
  
  public void build()
  {
    double d = this.currentNote.getFrequency();
    int i = (int)(this.sampleRate / d);
    this.kernel = new float[i];
    for (int j = 0; j < i; j++) {
      if (this.primary) {
        this.kernel[j] = ((float)(Math.random() * 2.0D - 1.0D));
      } else {
        this.kernel[j] = 0.0F;
      }
    }
    j = (int)(this.delay / 1000.0F * this.sampleRate);
    this.delayLine = new float[j * this.channels];
    this.delayIndex = 0;
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    int i = 0;
    float f1 = 0.0F;
    int j;
    if (this.primary)
    {
      if (this.index >= this.kernel.length) {
        this.index = 0;
      }
      while (i < paramArrayOfFloat.length)
      {
        f1 = this.kernel[this.index];
        for (j = 0; j < this.channels; j++)
        {
          paramArrayOfFloat[i] = this.kernel[this.index];
          try
          {
            paramArrayOfFloat[i] += this.delayLine[this.delayIndex] * this.decay;
          }
          catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
          {
            System.err.println("jMusic Pluck audio object error: i = " + i + " delayIndex = " + this.delayIndex);
          }
          float f2 = paramArrayOfFloat[i] * -this.decay;
          float f3 = this.delayLine[this.delayIndex];
          this.delayLine[this.delayIndex] = paramArrayOfFloat[i];
          paramArrayOfFloat[i] = (f2 + f3);
          if (this.delayIndex >= this.delayLine.length) {
            this.delayIndex = 0;
          }
          i++;
        }
        this.kernel[this.index] = ((this.kernel[this.index] + this.prevSample) * this.feedback);
        this.prevSample = f1;
        this.index += 1;
        if (this.index >= this.kernel.length) {
          this.index = 0;
        }
      }
    }
    if (this.index >= this.kernel.length) {
      this.index = 0;
    }
    while (i < paramArrayOfFloat.length)
    {
      f1 = paramArrayOfFloat[i];
      this.kernel[this.index] = ((paramArrayOfFloat[i] + this.prevSample) * this.feedback);
      for (j = 0; j < this.channels; j++) {
        paramArrayOfFloat[(i++)] = this.kernel[this.index];
      }
      this.prevSample = f1;
      this.index += 1;
      if (this.index >= this.kernel.length) {
        this.index = 0;
      }
    }
    return i;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\Pluck.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */