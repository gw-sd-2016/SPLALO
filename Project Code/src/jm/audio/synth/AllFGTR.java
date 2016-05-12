package jm.audio.synth;

import java.io.PrintStream;
import jm.audio.AOException;
import jm.audio.AudioObject;

public final class AllFGTR
  extends AudioObject
{
  private float[][] FGTArray = new float['È'][7];
  private float bandwidthTop;
  private float bandwidthBottom;
  private float frequency;
  private float spatial;
  private float highestAmp = 0.0F;
  private int grainsPerSecond;
  private int interOnset;
  private int grainDuration;
  private int bCounter = 0;
  private int gCounter = 0;
  private int dCounter = 0;
  private int grainsPerBuffer = 0;
  private int sampleRate = 44100;
  private int channels = 2;
  
  public AllFGTR(AudioObject paramAudioObject, int paramInt1, float paramFloat1, float paramFloat2, int paramInt2)
  {
    super(paramAudioObject, "[AllFGTR]");
    this.grainDuration = paramInt1;
    this.bandwidthTop = paramFloat1;
    this.bandwidthBottom = paramFloat2;
    this.grainsPerSecond = paramInt2;
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    System.out.println("Point 1");
    this.highestAmp = 0.0F;
    this.bCounter = 0;
    this.gCounter = 0;
    this.dCounter = 0;
    this.grainsPerBuffer = (this.grainsPerSecond * paramArrayOfFloat.length / (this.sampleRate * this.channels));
    this.interOnset = (paramArrayOfFloat.length / this.grainsPerBuffer);
    for (this.gCounter = 0; this.gCounter < this.grainsPerBuffer; this.gCounter += 1)
    {
      System.out.println("Point 1.1");
      this.bCounter = (this.gCounter * this.interOnset);
      this.highestAmp = 0.0F;
      System.out.println("gCounter: " + this.gCounter);
      System.out.println("grainDuration1: " + this.grainDuration);
      System.out.println("dCounter: " + this.dCounter);
      for (this.dCounter = 0; this.dCounter < this.grainDuration; this.dCounter += 1)
      {
        float f1 = paramArrayOfFloat[this.bCounter] * (float)Math.sin(3.141592653589793D * (this.dCounter / this.grainDuration));
        float f2 = f1;
        if (f1 < 0.0F) {
          f1 *= -1.0F;
        }
        if (this.highestAmp < f1) {
          this.highestAmp = f1;
        }
        this.bCounter += 1;
      }
      System.out.println("Point 2");
      this.FGTArray[this.gCounter][0] = (this.gCounter * this.interOnset);
      this.FGTArray[this.gCounter][1] = this.grainDuration;
      this.FGTArray[this.gCounter][2] = this.bandwidthTop;
      this.FGTArray[this.gCounter][3] = this.bandwidthBottom;
      this.FGTArray[this.gCounter][4] = 0.5F;
      this.FGTArray[this.gCounter][5] = this.highestAmp;
      this.FGTArray[this.gCounter][6] = this.grainsPerBuffer;
      if ((this.gCounter + 1) * this.interOnset + this.grainDuration > paramArrayOfFloat.length) {
        this.gCounter = this.grainsPerBuffer;
      }
    }
    System.out.println("Point 5");
    this.grainDuration = 0;
    this.bandwidthTop = 0.0F;
    this.bandwidthBottom = 0.0F;
    this.grainsPerBuffer = 0;
    this.interOnset = 0;
    this.highestAmp = 0.0F;
    this.bCounter = 0;
    this.gCounter = 0;
    this.dCounter = 0;
    for (int i = 0; i < paramArrayOfFloat.length; i++) {
      paramArrayOfFloat[i] = 0.0F;
    }
    System.out.println("Point 6");
    this.grainDuration = 1936;
    this.grainsPerBuffer = ((int)this.FGTArray[this.gCounter][6]);
    System.out.println("grainsPerBuffer6: " + this.grainsPerBuffer);
    System.out.println("grainDuration7: " + this.grainDuration);
    for (this.gCounter = 0; this.gCounter < this.grainsPerBuffer; this.gCounter += 1)
    {
      this.bCounter = ((int)this.FGTArray[this.gCounter][0]);
      this.grainDuration = 1936;
      this.bandwidthTop = this.FGTArray[this.gCounter][2];
      this.bandwidthBottom = this.FGTArray[this.gCounter][3];
      this.spatial = this.FGTArray[this.gCounter][4];
      this.highestAmp = this.FGTArray[this.gCounter][5];
      this.frequency = ((this.bandwidthTop + this.bandwidthBottom) * 0.5F);
      for (this.dCounter = 0; this.dCounter < this.grainDuration; this.dCounter += 1)
      {
        paramArrayOfFloat[this.bCounter] += (float)(Math.sin(6.283185307179586D * this.dCounter * (this.frequency * paramArrayOfFloat.length / (this.sampleRate * this.channels))) * (Math.sin(3.141592653589793D * (this.dCounter / this.grainDuration)) * this.highestAmp));
        this.bCounter += 1;
      }
      System.out.println("Point 9");
    }
    System.out.println("Point 10");
    return paramArrayOfFloat.length;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\AllFGTR.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */