package jm.audio.synth;

import jm.audio.AOException;
import jm.audio.AudioObject;

public final class Granulator
  extends AudioObject
{
  private int grainDuration = 1323;
  private int envelopeType = 1;
  private int nog;
  private float cfm;
  private int cgd;
  private float[] grain;
  private float[] newbuf;
  private int grainCnt = 0;
  private int grainsPerSecond = 10;
  private float[] tailBuf;
  private float freqMod = 1.0F;
  private float[] inBuffer = null;
  private boolean inBufActive = false;
  private boolean ri = false;
  private boolean rgd = false;
  private int rdist = 0;
  private int rdisttemp = 0;
  private int gdb = 1000;
  private int gdt = 1000;
  private boolean rf = false;
  private float rfb = 0.99F;
  private float rft = 1.01F;
  private float[] durationArray;
  private float[] gpsArray;
  private float[] freqArray;
  private boolean premapped = false;
  
  public Granulator(AudioObject paramAudioObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super(paramAudioObject, "[Granulator]");
    this.grainDuration = paramInt3;
    this.grainsPerSecond = paramInt4;
    this.cgd = 0;
    this.grain = new float[this.grainDuration];
    this.tailBuf = new float[0];
    this.sampleRate = paramInt1;
    this.channels = paramInt2;
  }
  
  public Granulator(AudioObject paramAudioObject, int paramInt1, int paramInt2, float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, float[] paramArrayOfFloat3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt3)
  {
    super(paramAudioObject, "[Granulator]");
    this.durationArray = paramArrayOfFloat1;
    this.gpsArray = paramArrayOfFloat2;
    this.freqArray = paramArrayOfFloat3;
    this.grain = new float[(int)this.durationArray[0]];
    this.ri = paramBoolean1;
    this.rgd = paramBoolean2;
    this.rf = paramBoolean3;
    this.tailBuf = new float[0];
    this.rdist = paramInt3;
    this.premapped = true;
    this.sampleRate = paramInt1;
    this.channels = paramInt2;
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    if (this.inBuffer == null)
    {
      this.newbuf = new float[paramArrayOfFloat.length];
      this.previous[0].nextWork(this.newbuf);
    }
    else
    {
      this.newbuf = new float[paramArrayOfFloat.length];
      for (i = 0; (i < this.inBuffer.length) && (i < this.newbuf.length); i++) {
        this.newbuf[i] = this.inBuffer[i];
      }
      this.inBuffer = null;
    }
    if (this.grainsPerSecond <= 0) {
      this.grainsPerSecond = 1;
    }
    this.nog = ((int)(this.newbuf.length / (this.sampleRate * this.channels / this.grainsPerSecond)));
    if (this.nog <= 0) {
      this.nog = 1;
    }
    int i = this.newbuf.length / this.nog;
    for (int j = 0; (j < paramArrayOfFloat.length) && (j < this.tailBuf.length); j++) {
      paramArrayOfFloat[j] += this.tailBuf[j];
    }
    this.tailBuf = new float[this.newbuf.length];
    this.inBufActive = true;
    for (j = 0; j < this.nog; j++)
    {
      if (this.rdist > 0) {
        this.rdisttemp = ((int)(Math.random() * this.rdist));
      } else {
        this.rdisttemp = 0;
      }
      int k = j * i + this.rdisttemp;
      setGrain(k - this.rdisttemp);
      for (int m = 0; m < this.grain.length; m++)
      {
        if (k >= paramArrayOfFloat.length) {
          this.tailBuf[(k - paramArrayOfFloat.length)] += this.grain[m];
        } else {
          paramArrayOfFloat[k] += this.grain[m];
        }
        k++;
        this.grainCnt += 1;
      }
    }
    this.inBufActive = false;
    return paramArrayOfFloat.length;
  }
  
  public void setFreqMod(float paramFloat)
  {
    this.freqMod = paramFloat;
  }
  
  public void setGrainDuration(int paramInt)
  {
    this.grainDuration = paramInt;
  }
  
  public void setGrainsPerSecond(int paramInt)
  {
    this.grainsPerSecond = paramInt;
  }
  
  public void setEnvelopeType(int paramInt)
  {
    this.envelopeType = paramInt;
  }
  
  public void setRandomGrainDuration(boolean paramBoolean)
  {
    this.rgd = paramBoolean;
  }
  
  public void setRandomGrainBottom(int paramInt)
  {
    this.gdb = paramInt;
  }
  
  public void setRandomGrainTop(int paramInt)
  {
    this.gdt = paramInt;
  }
  
  public void setRandomIndex(boolean paramBoolean)
  {
    this.ri = paramBoolean;
  }
  
  public void setRandomFreq(boolean paramBoolean)
  {
    this.rf = paramBoolean;
  }
  
  public void setRandomDist(int paramInt)
  {
    this.rdist = paramInt;
  }
  
  public void setRandomFreqBottom(float paramFloat)
  {
    this.rfb = paramFloat;
  }
  
  public void setRandomFreqTop(float paramFloat)
  {
    this.rft = paramFloat;
  }
  
  private void setGrain(int paramInt)
    throws AOException
  {
    if (this.ri) {
      paramInt = (int)(Math.random() * this.newbuf.length);
    }
    float[] arrayOfFloat = this.newbuf;
    if (this.rgd) {
      this.cgd = (this.gdb + (int)(Math.random() * this.gdt));
    } else {
      this.cgd = this.grainDuration;
    }
    this.cfm = this.freqMod;
    if (this.rf) {
      this.cfm = ((float)(this.rfb + Math.random() * (this.rft - this.rfb)));
    }
    if (this.inBufActive)
    {
      this.inBuffer = new float[this.newbuf.length];
      i = this.previous[0].nextWork(this.inBuffer);
      this.inBufActive = false;
    }
    this.grain = new float[this.cgd];
    int i = 0;
    float f1 = 0.0F;
    double d1 = -1.0D / ((1.0F - this.cfm) / this.cfm);
    double d2 = 0.0D;
    int j = 0;
    if (d1 < 0.0D)
    {
      d1 = -1.0D / d1;
      j = 1;
    }
    if (d1 == 0.0D) {
      j = 2;
    }
    int k = 0;
    for (int m = paramInt;; m++)
    {
      if (m == arrayOfFloat.length)
      {
        m = 0;
        arrayOfFloat = this.inBuffer;
      }
      if (j == 0)
      {
        k++;
        if (k >= (int)(d1 + d2))
        {
          d2 = (d1 + d2) % 1.0D;
          k = 0;
          continue;
        }
        if (i >= this.cgd) {
          break;
        }
        this.grain[(i++)] = arrayOfFloat[m];
      }
      else if (j == 1)
      {
        if (d1 + d2 >= 1.0D)
        {
          float f2 = (f1 - arrayOfFloat[m]) / ((int)d1 + 1);
          for (int n = 0; n < (int)(d1 + d2); n++)
          {
            this.grain[(i++)] = (f2 * n + arrayOfFloat[m]);
            if (i == this.cgd) {
              break;
            }
          }
        }
        if (i == this.cgd) {
          break;
        }
        this.grain[(i++)] = arrayOfFloat[m];
        f1 = arrayOfFloat[m];
        d2 = (d1 + d2) % 1.0D;
      }
      else
      {
        this.grain[(i++)] = arrayOfFloat[m];
      }
      if (i == this.cgd) {
        break;
      }
    }
    if (this.envelopeType <= 1)
    {
      for (m = 0; m < this.cgd; m++) {
        this.grain[m] *= (float)(0.5D - 0.5D * Math.cos(6.283185307179586D * m / this.cgd));
      }
    }
    else if (this.envelopeType == 3)
    {
      for (m = 0; m < this.cgd / 2; m++) {
        this.grain[m] *= 2.0F;
      }
      for (m = this.cgd / 2; m < this.cgd; m++) {
        this.grain[m] = (this.grain[m] * -2.0F + 2.0F);
      }
    }
    else
    {
      for (m = 0; m < this.cgd; m++) {
        this.grain[m] *= (float)Math.sin(3.141592653589793D * m / this.cgd);
      }
    }
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\Granulator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */