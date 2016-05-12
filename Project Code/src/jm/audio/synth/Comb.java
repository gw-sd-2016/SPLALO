package jm.audio.synth;

import jm.audio.AOException;
import jm.audio.AudioObject;

public final class Comb
  extends AudioObject
{
  private float decay;
  private int delay;
  private float[] delayLine;
  private int delayIndex;
  
  public Comb(AudioObject paramAudioObject, int paramInt)
  {
    this(paramAudioObject, paramInt, 0.5D);
  }
  
  public Comb(AudioObject paramAudioObject, int paramInt, double paramDouble)
  {
    super(paramAudioObject, "[Comb]");
    this.decay = ((float)paramDouble);
    this.delay = paramInt;
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    int i = this.previous[0].nextWork(paramArrayOfFloat);
    for (int j = 0; j < i; j++)
    {
      float f = this.delayLine[this.delayIndex];
      paramArrayOfFloat[j] += f * this.decay;
      this.delayLine[(this.delayIndex++)] = paramArrayOfFloat[j];
      if (this.delayIndex >= this.delayLine.length) {
        this.delayIndex = 0;
      }
    }
    return j;
  }
  
  public void build()
  {
    int i = (int)(this.delay / 1000.0F * this.sampleRate);
    this.delayLine = new float[i * this.channels];
    this.delayIndex = 0;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\Comb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */