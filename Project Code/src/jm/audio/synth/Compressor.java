package jm.audio.synth;

import java.io.PrintStream;
import jm.audio.AOException;
import jm.audio.AudioObject;

public final class Compressor
  extends AudioObject
{
  private float threshold = 1.0F;
  private double ratio = 1.0D;
  private float gainReduction = 1.0F;
  private float gain = 1.0F;
  
  public Compressor(AudioObject paramAudioObject)
  {
    this(paramAudioObject, 0.5D);
  }
  
  public Compressor(AudioObject paramAudioObject, double paramDouble)
  {
    this(paramAudioObject, paramDouble, 2.0D);
  }
  
  public Compressor(AudioObject paramAudioObject, double paramDouble1, double paramDouble2)
  {
    this(paramAudioObject, paramDouble1, paramDouble2, 1.5D);
  }
  
  public Compressor(AudioObject paramAudioObject, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    super(paramAudioObject, "[Compressor]");
    this.threshold = ((float)paramDouble1);
    this.ratio = paramDouble2;
    this.gain = ((float)paramDouble3);
    calcGainReduction();
  }
  
  public void build() {}
  
  private void calcGainReduction()
  {
    if (this.ratio == 1.0D)
    {
      this.gainReduction = 1.0F;
    }
    else if (this.ratio > 1.0D)
    {
      this.gainReduction = ((float)Math.min(1.0D, Math.abs(Math.log(1.0D - 1.0D / this.ratio) * 0.2D)));
    }
    else if (this.ratio > 0.0D)
    {
      this.gainReduction = ((float)(1.0D / this.ratio));
    }
    else
    {
      System.out.println("jMusic error: Compressor ratio values cannot be less than 0.0");
      System.exit(0);
    }
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    int i = this.previous[0].nextWork(paramArrayOfFloat);
    for (int j = 0; j < i; j++)
    {
      if (paramArrayOfFloat[j] > this.threshold) {
        paramArrayOfFloat[j] = (this.threshold + (paramArrayOfFloat[j] - this.threshold) * this.gainReduction);
      }
      if (paramArrayOfFloat[j] < -this.threshold) {
        paramArrayOfFloat[j] = (-this.threshold + (paramArrayOfFloat[j] + this.threshold) * this.gainReduction);
      }
      paramArrayOfFloat[j] *= this.gain;
    }
    return i;
  }
  
  public void setThreshold(double paramDouble)
  {
    this.threshold = ((float)paramDouble);
  }
  
  public void setRatio(double paramDouble)
  {
    this.ratio = paramDouble;
    calcGainReduction();
  }
  
  public void setGain(double paramDouble)
  {
    this.gain = ((float)paramDouble);
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\Compressor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */