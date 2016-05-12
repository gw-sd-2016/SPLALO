package jm.audio.synth;

import jm.audio.AOException;
import jm.audio.AudioObject;
import jm.music.data.Note;

public final class ReSample
  extends AudioObject
{
  private double baseFreq = 0.0D;
  private double newFreq = 0.0D;
  private boolean noteFreq = true;
  
  public ReSample(AudioObject paramAudioObject, double paramDouble)
  {
    super(paramAudioObject, "[ReSample]");
    this.baseFreq = paramDouble;
  }
  
  public void build()
  {
    if (this.noteFreq) {
      this.newFreq = this.currentNote.getFrequency();
    }
    this.finished = true;
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    double d1 = this.newFreq / this.baseFreq;
    double d2 = -1.0D / ((1.0D - d1) / d1);
    double d3 = 0.0D;
    int i = 0;
    if (d2 < 0.0D)
    {
      d2 = -1.0D / d2;
      i = 1;
    }
    if (d2 == 0.0D)
    {
      i = 2;
      d2 = 1.0D;
    }
    float[] arrayOfFloat = new float[(int)(paramArrayOfFloat.length * d1 + 0.5D) + 1];
    int j = this.previous[0].nextWork(arrayOfFloat);
    float f1 = 0.0F;
    int k = 0;
    int m = 0;
    for (int n = 0;; n++)
    {
      if (i == 0)
      {
        m++;
        if (m >= (int)(d2 + d3))
        {
          d3 = (d2 + d3) % 1.0D;
          m = 0;
          continue;
        }
        if (k >= paramArrayOfFloat.length) {
          break;
        }
        paramArrayOfFloat[(k++)] = arrayOfFloat[n];
      }
      else if (i == 1)
      {
        if (d2 + d3 >= 1.0D)
        {
          float f2 = (arrayOfFloat[n] - f1) / (float)(d2 + d3);
          for (int i1 = 0; i1 < (int)(d2 + d3); i1++)
          {
            paramArrayOfFloat[(k++)] = (f1 + f2 * i1);
            if (k == paramArrayOfFloat.length) {
              break;
            }
          }
        }
        if (k == paramArrayOfFloat.length) {
          break;
        }
        paramArrayOfFloat[(k++)] = arrayOfFloat[n];
        f1 = arrayOfFloat[n];
        d3 = (d2 + d3) % 1.0D;
      }
      else
      {
        paramArrayOfFloat[(k++)] = arrayOfFloat[n];
      }
      if (k == paramArrayOfFloat.length) {
        break;
      }
    }
    return paramArrayOfFloat.length;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\ReSample.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */