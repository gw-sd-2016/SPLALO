package jm.audio.synth;

import jm.JMC;
import jm.audio.AOException;
import jm.audio.AudioObject;
import jm.audio.Instrument;
import jm.music.data.Note;

public class ADSR
  extends AudioObject
  implements JMC
{
  private EnvPoint[] graphPoints;
  private float[] graphShape;
  private boolean primary;
  private int attack;
  private int decay;
  private int release;
  private double sustain;
  private int totalSamples;
  private int sampleCounter = 0;
  private int position = 0;
  private double attackSamps;
  private double decaySamps;
  private double releaseSamps;
  private double prevRV = 0.0D;
  int maxAttackCount;
  int maxDecayCount;
  
  public ADSR(Instrument paramInstrument, int paramInt1, int paramInt2, int paramInt3, int paramInt4, double paramDouble, int paramInt5)
  {
    super(paramInstrument, paramInt1, "[ADSR]");
    this.channels = paramInt2;
    this.attack = paramInt3;
    this.decay = paramInt4;
    this.sustain = paramDouble;
    this.release = paramInt5;
    this.primary = true;
    this.finished = false;
    calcSamps();
  }
  
  public ADSR(AudioObject paramAudioObject, int paramInt1, int paramInt2, double paramDouble, int paramInt3)
  {
    super(paramAudioObject, "[ADSR]");
    this.attack = paramInt1;
    this.decay = paramInt2;
    this.sustain = paramDouble;
    this.release = paramInt3;
    this.primary = false;
    this.finished = false;
  }
  
  public int work(float[] paramArrayOfFloat)
    throws AOException
  {
    if (this.sampleCounter > this.totalSamples) {
      this.finished = true;
    }
    int k;
    if (this.primary)
    {
      i = paramArrayOfFloat.length;
      j = 0;
      while (j < i)
      {
        for (k = 0; k < this.channels; k++) {
          try
          {
            paramArrayOfFloat[(j + k)] = this.graphShape[this.position];
          }
          catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException1)
          {
            paramArrayOfFloat[(j + k)] = 0.0F;
          }
        }
        this.position += 1;
        j += this.channels;
      }
      this.sampleCounter += paramArrayOfFloat.length;
      return i;
    }
    int i = this.previous[0].nextWork(paramArrayOfFloat);
    int j = 0;
    while (j < paramArrayOfFloat.length)
    {
      for (k = 0; k < this.channels; k++) {
        try
        {
          if (this.sampleCounter < this.maxAttackCount) {
            paramArrayOfFloat[(j + k)] *= (float)(this.sampleCounter * 1.0D / this.maxAttackCount);
          } else if (this.sampleCounter < this.maxDecayCount + this.maxAttackCount) {
            paramArrayOfFloat[(j + k)] *= (float)(1.0D - (this.sampleCounter - this.maxAttackCount) * (1.0D - this.sustain) / this.maxDecayCount);
          } else if (this.sampleCounter < this.numOfSamples) {
            paramArrayOfFloat[(j + k)] *= (float)this.sustain;
          } else if (this.sampleCounter < this.totalSamples) {
            paramArrayOfFloat[(j + k)] *= (float)(this.sustain - (this.sampleCounter - this.numOfSamples) * this.sustain / this.releaseSamps);
          } else {
            paramArrayOfFloat[(j + k)] = 0.0F;
          }
        }
        catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException2)
        {
          paramArrayOfFloat[(j + k)] = 0.0F;
        }
      }
      this.sampleCounter += 1;
      this.position += 1;
      j += this.channels;
    }
    return i;
  }
  
  private void calcSamps()
  {
    this.attackSamps = getSamps(this.attack);
    this.decaySamps = getSamps(this.decay);
    this.releaseSamps = getSamps(this.release);
  }
  
  private double getSamps(int paramInt)
  {
    return paramInt / 1000.0D * this.sampleRate;
  }
  
  public void build()
  {
    this.sampleCounter = 0;
    this.position = 0;
    if (this.numOfSamples == 0) {
      return;
    }
    if (this.currentNote.getRhythmValue() == this.prevRV) {
      return;
    }
    calcSamps();
    this.totalSamples = (this.numOfSamples + (int)this.releaseSamps);
    this.graphShape = new float[this.totalSamples];
    this.maxAttackCount = Math.min((int)this.attackSamps, this.numOfSamples);
    double d1 = 1.0D / this.maxAttackCount;
    for (int i = 0; i < this.maxAttackCount; i++) {
      this.graphShape[i] = ((float)(d1 * i));
    }
    this.maxDecayCount = this.maxAttackCount;
    if (this.sustain < 1.0D)
    {
      this.maxDecayCount = Math.min((int)this.attackSamps + (int)this.decaySamps, this.numOfSamples);
      double d2 = (1.0D - this.sustain) / (this.maxDecayCount - this.maxAttackCount);
      for (int k = this.maxAttackCount; k < this.maxDecayCount; k++) {
        this.graphShape[k] = ((float)(1.0D - d2 * (k - this.maxAttackCount)));
      }
    }
    for (int j = this.maxDecayCount; j < this.numOfSamples; j++) {
      this.graphShape[j] = ((float)this.sustain);
    }
    double d3 = this.graphShape[(this.numOfSamples - 1)];
    double d4 = d3 / this.releaseSamps;
    for (int m = this.numOfSamples; m < this.totalSamples; m++) {
      this.graphShape[m] = ((float)(d3 - d4 * (m - this.numOfSamples)));
    }
    this.finished = false;
  }
}


/* Location:              C:\Users\Kweku Emuze\Downloads\jMusic1.6.4.jar!\jm\audio\synth\ADSR.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */