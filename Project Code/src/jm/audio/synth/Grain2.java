package jm.audio.synth;

import java.io.PrintStream;
import jm.audio.AOException;
import jm.audio.AudioObject;

public final class Grain2
  extends AudioObject
{
  private int grainSampSize = 1000;
  private int spaceSamp = 1000;
  private int grainCount = 0;
  private int spaceCount = 0;
  private int offset = 0;
  private boolean grainOn = true;
  
  public Grain2(AudioObject paramAudioObject, int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3, int paramInt4)
  {
    super(paramAudioObject, "[Grain]");
    this.grainSampSize = (paramInt1 * paramInt3);
    this.spaceSamp = (paramInt2 * paramInt3);
    this.grainOn = paramBoolean;
    this.offset = paramInt4;
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    int i = this.previous[0].nextWork(paramArrayOfFloat);
    int j = this.offset;
    if (this.offset > 0)
    {
      for (k = 0; k < this.offset; k++) {
        paramArrayOfFloat[k] = 0.0F;
      }
      this.offset = 0;
    }
    for (int k = j; k < i; k++)
    {
      if (this.grainOn) {
        paramArrayOfFloat[k] *= (float)Math.sin(3.141592653589793D * this.grainCount / this.grainSampSize);
      }
      if ((this.grainOn) && (this.grainCount < this.grainSampSize))
      {
        this.grainCount += 1;
      }
      else if (this.grainOn)
      {
        this.grainOn = false;
        this.grainCount = 0;
      }
      if (!this.grainOn) {
        paramArrayOfFloat[k] = 0.0F;
      }
      if ((!this.grainOn) && (this.spaceCount < this.spaceSamp))
      {
        this.spaceCount += 1;
      }
      else if (!this.grainOn)
      {
        this.grainOn = true;
        this.spaceCount = 0;
      }
    }
    return i;
  }
  
  public void setGrainDur(int paramInt)
  {
    this.grainSampSize = paramInt;
    System.out.println("Space4: " + this.grainSampSize);
  }
  
  public void setSpaceDur(int paramInt)
  {
    this.spaceSamp = paramInt;
    System.out.println("Space4: " + this.spaceSamp);
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\Grain2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */