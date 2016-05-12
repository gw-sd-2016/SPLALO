package jm.audio.synth;

import jm.audio.AOException;
import jm.audio.AudioObject;

public final class Smooth
  extends AudioObject
{
  private float[] prevSampleValues;
  
  public Smooth(AudioObject paramAudioObject)
  {
    super(paramAudioObject, "[Smooth]");
  }
  
  public void build()
  {
    this.prevSampleValues = new float[this.channels];
    for (int i = 0; i < this.prevSampleValues.length; i++) {
      this.prevSampleValues[i] = 0.0F;
    }
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    int i = this.previous[0].nextWork(paramArrayOfFloat);
    int j = 0;
    while (j < i)
    {
      for (int k = 0; k < this.channels; k++)
      {
        paramArrayOfFloat[(j + k)] = (paramArrayOfFloat[(j + k)] * 0.5F + this.prevSampleValues[k] * 0.5F);
        this.prevSampleValues[k] = paramArrayOfFloat[(j + k)];
      }
      j += this.channels;
    }
    return i;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\Smooth.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */