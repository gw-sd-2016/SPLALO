package jm.audio.synth;

import jm.audio.AOException;
import jm.audio.AudioObject;

public final class FGTR
  extends AudioObject
{
  private float[][] FGTArray;
  private int bCounter;
  private int gCounter;
  private int gDuration;
  private int grainsPerBuffer;
  private float bandwidthTop;
  private float bandwidthBottom;
  private float spatial;
  private float highestAmp;
  private float frequency;
  private float grainDuration;
  
  public FGTR(AudioObject paramAudioObject)
  {
    super(paramAudioObject, "[FGTR]");
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    for (this.gCounter = 0; this.gCounter < this.grainsPerBuffer; this.gCounter += 1)
    {
      this.bCounter = ((int)this.FGTArray[this.gCounter][0]);
      this.gDuration = ((int)this.FGTArray[this.gCounter][1]);
      this.bandwidthTop = this.FGTArray[this.gCounter][2];
      this.bandwidthBottom = this.FGTArray[this.gCounter][3];
      this.spatial = this.FGTArray[this.gCounter][4];
      this.highestAmp = this.FGTArray[this.gCounter][5];
      this.frequency = ((float)((this.bandwidthTop + this.bandwidthBottom) * 0.5D));
      for (int i = 0; i < this.grainDuration; i++)
      {
        paramArrayOfFloat[this.bCounter] += (float)(Math.sin(6.283185307179586D * i * (this.frequency * paramArrayOfFloat.length / (this.sampleRate * this.channels))) * (Math.sin(3.141592653589793D * (i / this.grainDuration)) * this.highestAmp));
        this.bCounter += 1;
      }
    }
    return 0;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\FGTR.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */