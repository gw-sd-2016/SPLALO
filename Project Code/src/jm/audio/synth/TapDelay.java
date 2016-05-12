package jm.audio.synth;

import jm.audio.AOException;
import jm.audio.AudioObject;
import jm.audio.Instrument;

public final class TapDelay
  extends AudioObject
{
  private float decay;
  private int delay;
  private float[] delayLine;
  private int delayIndex;
  private int taps;
  private int sampleDelay;
  
  public TapDelay(AudioObject paramAudioObject, int paramInt1, int paramInt2)
  {
    this(paramAudioObject, paramInt1, paramInt2, 0.5D);
  }
  
  public TapDelay(AudioObject paramAudioObject, int paramInt1, int paramInt2, double paramDouble)
  {
    super(paramAudioObject, "[Tap Delay]");
    this.finished = false;
    this.decay = ((float)paramDouble);
    this.delay = paramInt1;
    this.taps = paramInt2;
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    int i = paramArrayOfFloat.length;
    if ((!this.inst.finishedNewData) && (this.inst.getFinished())) {
      i = this.previous[0].nextWork(paramArrayOfFloat);
    }
    int j = 0;
    float f = 0.0F;
    while (j < i)
    {
      for (int k = 1; k <= this.taps; k++)
      {
        int m = this.delayIndex + this.sampleDelay * this.channels * k;
        if (m >= this.delayLine.length) {
          m -= this.delayLine.length;
        }
        this.delayLine[m] += paramArrayOfFloat[j] * (this.decay / k);
      }
      paramArrayOfFloat[j] += this.delayLine[this.delayIndex];
      this.delayLine[this.delayIndex] = 0.0F;
      this.delayIndex += 1;
      if (this.delayIndex >= this.delayLine.length) {
        this.delayIndex = 0;
      }
      if (f < paramArrayOfFloat[j]) {
        f = paramArrayOfFloat[j];
      }
      j++;
    }
    if (this.inst.iterations <= 0 - this.delayLine.length) {
      this.finished = true;
    }
    return j;
  }
  
  public void build()
  {
    if (this.delayLine == null)
    {
      this.sampleDelay = ((int)(this.delay / 1000.0F * this.sampleRate));
      this.delayLine = new float[this.sampleDelay * this.channels * this.taps];
      this.delayIndex = 0;
    }
    this.finished = false;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\TapDelay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */